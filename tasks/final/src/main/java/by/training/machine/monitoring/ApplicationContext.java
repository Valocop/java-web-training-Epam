package by.training.machine.monitoring;

import by.training.machine.monitoring.characteristic.AddCharacteristicCommand;
import by.training.machine.monitoring.characteristic.CharacteristicDaoImpl;
import by.training.machine.monitoring.characteristic.CharacteristicServiceImpl;
import by.training.machine.monitoring.core.BeanProvider;
import by.training.machine.monitoring.core.BeanProviderImpl;
import by.training.machine.monitoring.dao.*;
import by.training.machine.monitoring.machine.*;
import by.training.machine.monitoring.manufacture.ManufactureDaoImpl;
import by.training.machine.monitoring.manufacture.ManufactureServiceImpl;
import by.training.machine.monitoring.message.MessageManager;
import by.training.machine.monitoring.model.AddModelCommand;
import by.training.machine.monitoring.model.ModelDaoImpl;
import by.training.machine.monitoring.model.ModelServiceImpl;
import by.training.machine.monitoring.role.RoleDaoImpl;
import by.training.machine.monitoring.role.RoleServiceImpl;
import by.training.machine.monitoring.user.*;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@NoArgsConstructor
public class ApplicationContext implements BeanProvider {
    private final static AtomicBoolean INITIALIZED = new AtomicBoolean(false);
    private final static Lock INITIALIZE_LOCK = new ReentrantLock();
    private static ApplicationContext INSTANCE;

    private BeanProvider beanRegistry = new BeanProviderImpl();

    public static void initialize() {
        INITIALIZE_LOCK.lock();
        try {
            if (INSTANCE != null && INITIALIZED.get()) {
                throw new IllegalStateException("Context was already initialized");
            } else {
                ApplicationContext context = new ApplicationContext();
                context.init();
                INSTANCE = context;
                INITIALIZED.set(true);
            }
        } finally {
            INITIALIZE_LOCK.unlock();
        }
    }

    public static ApplicationContext getInstance() {
        if (INSTANCE != null && INITIALIZED.get()) {
            return INSTANCE;
        } else {
            throw new IllegalStateException("Context wasn't initialized");
        }
    }

    @Override
    public void destroy() {
        PoolConnection poolConnection = PoolConnection.getInstance();
        try {
            poolConnection.destroyConnections();
        } catch (DaoSqlException e) {
            throw new IllegalStateException("Trouble with close database connections", e);
        }
        beanRegistry.destroy();
    }

    private void init() {
        registerDataSource();
        registerClasses();
    }

    private void registerDataSource() {
        PoolConnection poolConnection = PoolConnection.getInstance();
        try {
            poolConnection.init();
        } catch (DaoException e) {
            throw new IllegalStateException("Trouble with initialization database", e);
        }
        DataSource dataSource = new DataSourceImpl();
        TransactionManager transactionManager = new TransactionManagerQueue(dataSource);
        ConnectionManager connectionManager = new ConnectionManagerImpl(transactionManager, dataSource);
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(transactionManager);
        MessageManager messageManager = MessageManager.EN;
        registerBean(messageManager);
        registerBean(poolConnection);
        registerBean(dataSource);
        registerBean(transactionManager);
        registerBean(connectionManager);
        registerBean(transactionInterceptor);
    }

    private void registerClasses() {
        registerBean(RoleDaoImpl.class);
        registerBean(RoleServiceImpl.class);
        registerBean(UserDaoImpl.class);
        registerBean(UserServiceImpl.class);
        registerBean(ViewUserListCommand.class);
        registerBean(RegisterUserCommand.class);
        registerBean(LoginUserCommand.class);
        registerBean(LogoutUserCommand.class);
        registerBean(DeleteUserCommand.class);
        registerBean(UpdateUserCommand.class);
        registerBean(AddModelCommand.class);
        registerBean(ModelServiceImpl.class);
        registerBean(ModelDaoImpl.class);
        registerBean(ManufactureServiceImpl.class);
        registerBean(ManufactureDaoImpl.class);
        registerBean(AddCharacteristicCommand.class);
        registerBean(CharacteristicDaoImpl.class);
        registerBean(CharacteristicServiceImpl.class);
        registerBean(ShowAddMachineCommand.class);
        registerBean(MachineServiceImpl.class);
        registerBean(SaveMachineCommand.class);
        registerBean(MachineDaoImpl.class);
        registerBean(ShowListCarsCommand.class);
        registerBean(MachineErrorDaoImpl.class);
        registerBean(MachineLogDaoImpl.class);
        registerBean(DeleteMachineCommand.class);
    }

    @Override
    public <T> void registerBean(T bean) {
        this.beanRegistry.registerBean(bean);
    }

    @Override
    public <T> void registerBean(Class<T> beanClass) {
        this.beanRegistry.registerBean(beanClass);
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        return this.beanRegistry.getBean(beanClass);
    }

    @Override
    public <T> T getBean(String name) {
        return this.beanRegistry.getBean(name);
    }

    @Override
    public <T> boolean removeBean(T bean) {
        return this.beanRegistry.removeBean(bean);
    }
}
