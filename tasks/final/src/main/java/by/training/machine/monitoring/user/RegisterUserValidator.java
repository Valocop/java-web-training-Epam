package by.training.machine.monitoring.user;

import by.training.machine.monitoring.message.MessageManager;
import by.training.machine.monitoring.validator.ResultValidator;
import by.training.machine.monitoring.validator.ResultValidatorImpl;
import by.training.machine.monitoring.validator.Validator;
import lombok.extern.log4j.Log4j;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j
public class RegisterUserValidator implements Validator {
    private static final String LOGIN_REGEX = "[a-zA-Z0-9]+";
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+=?^_`\\{|\\}~-]+(?:\\." +
            "[a-z0-9!#$%&'*+=?^_`\\{|\\}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21" +
            "\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9]" +
            "(?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4]" +
            "[0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]" +
            "*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-" +
            "\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String TEL_REGEX = "^\\+*[0-9]{3}-*\\(*[0-9]{2}\\)*-*\\d{3}-*\\d{2}-*\\d{2}$";
    private static final String PASSWORD_REGEX = "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9]).{6,})\\S$";

    @Override
    public ResultValidator validate(Map<String, String> data, MessageManager messageManager) {
        ResultValidator rv = new ResultValidatorImpl();
        data.forEach((s, s2) -> {
            if (s2 == null) {
                rv.addException(s + ".null", Arrays.asList(messageManager.getMessage(s + ".null")));
            }
        });
        checkLogin(data.get("user.login"), rv, messageManager);
        checkPassword(data.get("user.password"), rv, messageManager);
        checkEmail(data.get("user.email"), rv, messageManager);
        checkName(data.get("user.name"), rv, messageManager);
        checkAddress(data.get("user.address"), rv, messageManager);
        checkTel(data.get("user.tel"), rv, messageManager);
        return rv;
    }

    private void checkLogin(String login, ResultValidator rv, MessageManager messageManager) {
        if (login != null) {
            Pattern pattern = Pattern.compile(LOGIN_REGEX);
            Matcher matcher = pattern.matcher(login);
            if (login.length() > 30 || login.isEmpty() || !matcher.matches()) {
                rv.addException("user.login.incorrect",
                        Arrays.asList(messageManager.getMessage("user.login.incorrect")));
            }
        }
    }

    private void checkPassword(String password, ResultValidator rv, MessageManager messageManager) {
        if (password != null) {
            Pattern pattern = Pattern.compile(PASSWORD_REGEX);
            Matcher matcher = pattern.matcher(password);
            if (password.trim().length() < 7 || password.trim().length() > 40 || !matcher.matches()) {
                rv.addException("user.password.incorrect",
                        Arrays.asList(messageManager.getMessage("user.password.incorrect")));
            }
        }
    }

    private void checkEmail(String email, ResultValidator rv, MessageManager messageManager) {
        if (email != null) {
            Pattern pattern = Pattern.compile(EMAIL_REGEX);
            Matcher matcher = pattern.matcher(email.trim().toLowerCase());
            if (!matcher.matches() || email.length() > 50) {
                rv.addException("user.email.incorrect",
                        Arrays.asList(messageManager.getMessage("user.email.incorrect")));
            }
        }
    }

    private void checkName(String name, ResultValidator rv, MessageManager messageManager) {
        if (name != null) {
            if (name.isEmpty() || name.trim().length() > 40) {
                rv.addException("user.name.incorrect",
                        Arrays.asList(messageManager.getMessage("user.name.incorrect")));
            }
        }
    }

    private void checkAddress(String address, ResultValidator rv, MessageManager messageManager) {
        if (address != null) {
            if (address.isEmpty() || address.trim().length() > 50) {
                rv.addException("user.address.incorrect",
                        Arrays.asList(messageManager.getMessage("user.address.incorrect")));
            }
        }
    }

    private void checkTel(String tel, ResultValidator rv, MessageManager messageManager) {
        if (tel != null) {
            Pattern pattern = Pattern.compile(TEL_REGEX);
            Matcher matcher = pattern.matcher(tel.trim());
            if (!matcher.matches() || tel.length() > 30) {
                rv.addException("user.tel.incorrect",
                        Arrays.asList(messageManager.getMessage("user.tel.incorrect")));
            }
        }
    }
}
