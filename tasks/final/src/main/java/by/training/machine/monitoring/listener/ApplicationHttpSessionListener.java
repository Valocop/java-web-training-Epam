package by.training.machine.monitoring.listener;

import by.training.machine.monitoring.app.SecurityService;
import lombok.extern.log4j.Log4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Log4j
@WebListener
public class ApplicationHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        log.info("Time of session is timeout");
        if (SecurityService.getInstance().deleteSession(httpSessionEvent.getSession())) {
            log.info("Session was deleted");
        } else {
            log.warn("Failed to delete session");
        }
    }
}
