package by.training.machine.monitoring.listener;

import by.training.machine.monitoring.app.ApplicationContext;
import by.training.machine.monitoring.message.MessageManager;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Log4j
@WebListener
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext.initialize();
        log.info("Context was initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ApplicationContext.getInstance().destroy();
        log.info("Context was destroyed");
    }
}
