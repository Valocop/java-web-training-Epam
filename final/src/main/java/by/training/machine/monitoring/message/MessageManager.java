package by.training.machine.monitoring.message;

import by.training.machine.monitoring.core.Bean;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

@Bean
public enum MessageManager {
    EN(ResourceBundle.getBundle("i18n.AppMessages", new Locale("en", "EN"))),
    RU(ResourceBundle.getBundle("i18n.AppMessages", new Locale("ru", "RU")));

    public static MessageManager getMessageManager(String lang) {
        if (lang != null) {
            if (lang.equalsIgnoreCase("EN")) {
                return MessageManager.EN;
            } else if (lang.equalsIgnoreCase("RU")) {
                return MessageManager.RU;
            }
        }
        return MessageManager.EN;
    }

    public static MessageManager getMessageManager(HttpServletRequest request) {
        Cookie langCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equalsIgnoreCase("Language"))
                .findFirst().orElse(new Cookie("Language", "EN"));
        if (langCookie.getValue().equalsIgnoreCase("EN")) {
            return MessageManager.EN;
        } else if (langCookie.getValue().equalsIgnoreCase("RU")) {
            return MessageManager.RU;
        }
        return MessageManager.EN;
    }

    private ResourceBundle resourceBundle;

    private MessageManager(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

    public Locale getLocale() {
        return Locale.forLanguageTag(this.name());
    }
}
