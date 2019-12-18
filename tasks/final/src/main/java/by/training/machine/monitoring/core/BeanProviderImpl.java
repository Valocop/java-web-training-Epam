package by.training.machine.monitoring.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BeanProviderImpl implements BeanProvider {
    private FactoryBean factoryBean = new FactoryBean();
    private Set<RegisterInfo> beanRegister = new HashSet<>();

    @Override
    public <T> void registerBean(T bean) {
        RegisterInfo info = getRegisterInfo(bean.getClass());
        info.setConcreteBean(bean);
        addRegisterInfo(info);
    }

    @Override
    public <T> void registerBean(Class<T> beanClass) {
        RegisterInfo info = getRegisterInfo(beanClass);
        final Supplier<Object> factory = createFactory(info);
        info.setFactory(factory);
        addRegisterInfo(info);
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        Bean bean = beanClass.getAnnotation(Bean.class);
        String beanName = bean != null && bean.name().trim().length() > 0 ? bean.name().trim() : null;
        Predicate<RegisterInfo> searchBean = info -> info.getName().equals(beanName) ||
                info.getClazz().equals(beanClass) ||
                info.getInterfaces().contains(beanClass);
        return getBean(searchBean);
    }

    @Override
    public <T> T getBean(String beanName) {
        Predicate<RegisterInfo> searchBean = info -> info.getName().equals(beanName) ||
                info.getClazz().getSimpleName().equals(beanName);
        return getBean(searchBean);
    }

    @SuppressWarnings("unchecked")
    private <T> T getBean(Predicate<RegisterInfo> searchBean) {
        List<RegisterInfo> registerInfoList = beanRegister.stream()
                .filter(searchBean)
                .collect(Collectors.toList());

        if (registerInfoList.size() > 1) {
            String multipleNames = registerInfoList.stream()
                    .map(RegisterInfo::getName)
                    .collect(Collectors.joining(", "));
            throw new NotUniqueBeanException("Multiple implementations found: " + multipleNames);
        } else {
            return (T) registerInfoList.stream()
                    .map(this::mapToBean)
                    .findFirst()
                    .orElse(null);
        }
    }

    @Override
    public <T> boolean removeBean(T bean) {
        RegisterInfo registerInfo = getRegisterInfo(bean.getClass());
        registerInfo.setConcreteBean(bean);
        return beanRegister.remove(registerInfo);
    }

    @Override
    public void destroy() {
        factoryBean.destroy();
        beanRegister.clear();
    }

    private Supplier<Object> createFactory(RegisterInfo info) {
        Class<?> clazz = info.getClazz();
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length > 1) {
            throw new BeanInstantiationException("More than 1 constructor is present for class " +
                    clazz.getSimpleName());
        }

        return () -> {
            Constructor<?> constructor = constructors[0];
            if (constructor.getParameterCount() > 0) {
                Parameter[] parameters = constructor.getParameters();
                Object[] args = new Object[parameters.length];

                for (int i = 0; i < parameters.length; i++) {
                    Class<?> type = parameters[i].getType();
                    BeanQualifier beanQualifier = parameters[i].getAnnotation(BeanQualifier.class);
                    if (beanQualifier != null) {
                        Predicate<RegisterInfo> searchBean = searchInfo -> searchInfo.getName().
                                equals(beanQualifier.value());
                        args[i] = getBean(searchBean);
                    } else {
                        args[i] = getBean(type);
                    }
                }
                try {
                    return constructor.newInstance(args);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    throw new BeanInstantiationException("Failed to instantiate bean", e);
                }
            } else {
                try {
                    return clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new BeanInstantiationException("Failed to instantiate bean", e);
                }
            }
        };
    }

    @SuppressWarnings("unchecked")
    private <T> T mapToBean(RegisterInfo info) {
        T service = (T) factoryBean.getBean(info);
        Set<RegisterInfo> availableInterceptors = beanRegister.stream()
                .filter(RegisterInfo::isInterceptor)
                .filter(interceptorInfo -> info.getAnnotations()
                        .stream()
                        .anyMatch(annotation -> annotation.annotationType()
                                .equals(interceptorInfo.getInterceptor().clazz())))
                .collect(Collectors.toSet());
        if (availableInterceptors.isEmpty()) {
            return service;
        } else {
            List<BeanInterceptor> interceptors = availableInterceptors.stream()
                    .map(interceptorInfo -> (BeanInterceptor) factoryBean.getBean(interceptorInfo))
                    .collect(Collectors.toList());
            return getServiceProxy(service, info, interceptors);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getServiceProxy(T service, RegisterInfo info, List<BeanInterceptor> interceptors) {
        Class<?>[] toProxy = new Class[info.getInterfaces().size()];
        Class<?>[] interfaces = info.getInterfaces().toArray(toProxy);

        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), interfaces,
                (proxy, method, args) -> {
                    try {
                        for (BeanInterceptor interceptor : interceptors) {
                            interceptor.before(proxy, service, method, args);
                        }
                        Object invoked = method.invoke(service, args);
                        for (BeanInterceptor interceptor : interceptors) {
                            interceptor.success(proxy, service, method, args);
                        }
                        return invoked;
                    } catch (Exception e) {
                        for (BeanInterceptor interceptor : interceptors) {
                            interceptor.fail(proxy, service, method, args);
                        }
                        throw new IllegalStateException("Exception during proxy invocation", e);
                    }
                });
    }

    private RegisterInfo getRegisterInfo(Class<?> beanClass) {
        Bean bean = beanClass.getAnnotation(Bean.class);
        if (bean == null) {
            throw new MissedAnnotationException(beanClass.getName() + " doesn't have @Bean annotation");
        }

        RegisterInfo info = new RegisterInfo();
        info.setClazz(beanClass);

        Class<?>[] interfaces = beanClass.getInterfaces();
        info.setInterfaces(Arrays.stream(interfaces).collect(Collectors.toSet()));

        Annotation[] annotations = beanClass.getAnnotations();
        info.setAnnotations(Arrays.stream(annotations).collect(Collectors.toSet()));

        Interceptor interceptor = beanClass.getAnnotation(Interceptor.class);
        if (interceptor != null) {
            info.setInterceptor(interceptor);
        }

        String beanName = bean.name();
        if (beanName.trim().length() > 0) {
            info.setName(beanName);
        } else if (beanClass.getInterfaces().length == 1) {
            info.setName(beanClass.getInterfaces()[0].getSimpleName());
        } else {
            info.setName(beanClass.getSimpleName());
        }
        return info;
    }

    private void addRegisterInfo(RegisterInfo info) {
        beanRegister.stream()
                .filter(registerInfo -> registerInfo.getName().equals(info.getName()))
                .findFirst()
                .ifPresent(registerInfo -> {
                    throw new NotUniqueBeanException("Bean with name " + registerInfo.getName() + " already registered");
                });
        beanRegister.add(info);
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode
    private static class RegisterInfo {
        private String name;
        private Class<?> clazz;
        private Set<Class<?>> interfaces;
        private Set<Annotation> annotations;
        private Interceptor interceptor;
        private Supplier<?> factory;
        private Object concreteBean;

        boolean isInterceptor() {
            return this.interceptor != null;
        }
    }

    private static class FactoryBean {
        private Map<RegisterInfo, Object> beans = new ConcurrentHashMap<>();

        Object getBean(RegisterInfo info) {
            if (info.getConcreteBean() != null) {
                beans.put(info, info.getConcreteBean());
            } else if (!beans.containsKey(info)) {
                final Object bean = info.getFactory().get();//попадая сюда продолжает строится
                beans.put(info, bean);
            }
            return beans.get(info);
        }

        void destroy() {
            beans.clear();
        }
    }
}
