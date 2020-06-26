package by.training.machine.monitoring.dao;

import by.training.machine.monitoring.core.Bean;
import by.training.machine.monitoring.core.BeanInterceptor;
import by.training.machine.monitoring.core.Interceptor;
import lombok.extern.log4j.Log4j;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;

@Bean
@Log4j
@Interceptor(clazz = TransactionSupport.class)
public class TransactionInterceptor implements BeanInterceptor {
    private TransactionManager transactionManager;

    public TransactionInterceptor(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void before(Object proxy, Object service, Method method, Object[] args) {
        if (isMethodTransaction(service, method)) {
            try {
                transactionManager.beginTransaction();
            } catch (DaoException e) {
                throw new IllegalStateException("Failed to begin transaction", e);
            }
        }
    }

    @Override
    public void success(Object proxy, Object service, Method method, Object[] args) {
        if (isMethodTransaction(service, method)) {
            try {
                transactionManager.commitTransaction();
            } catch (DaoException | SQLException e) {
                throw new IllegalStateException("Failed to commit transaction", e);
            }
        }
    }

    @Override
    public void fail(Object proxy, Object service, Method method, Object[] args) {
        if (isMethodTransaction(service, method)) {
            try {
                transactionManager.rollbackTransaction();
            } catch (DaoException | SQLException e) {
                throw new IllegalStateException("Failed to rollback transaction", e);
            }
        }
    }

    private boolean isMethodTransaction(Object service, Method method) {
        boolean methodHasTransaction = method.getAnnotation(Transactional.class) != null;
        if (!methodHasTransaction) {
            methodHasTransaction = Arrays.stream(service.getClass().getDeclaredMethods())
                    .filter(serviceMethod -> serviceMethod.getName().equals(method.getName()))
                    .anyMatch(serviceMethod -> serviceMethod.getAnnotation(Transactional.class) != null);
        }
        return methodHasTransaction;
    }
}
