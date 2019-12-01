package by.training.machine.monitoring.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebFilter(urlPatterns = {"/*"})
public class LanguageFilter implements Filter {
    private static final String LANGUAGE_COOKIE = "Language";
    private static final String LANG_RU = "RU";
    private static final String LANG_EN = "EN";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        setDefaultLangCookie(request, (HttpServletResponse) servletResponse);
        if (request.getParameter(LANGUAGE_COOKIE) != null) {
            if (request.getParameter(LANGUAGE_COOKIE).equalsIgnoreCase(LANG_EN)) {
                setLanguageCookie(request, (HttpServletResponse) servletResponse, LANG_EN);
            } else if (request.getParameter(LANGUAGE_COOKIE).equalsIgnoreCase(LANG_RU)) {
                setLanguageCookie(request, (HttpServletResponse) servletResponse, LANG_RU);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private void setDefaultLangCookie(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            if (Arrays.stream(request.getCookies()).noneMatch(cookie -> cookie.getName().equalsIgnoreCase(LANGUAGE_COOKIE))) {
                response.addCookie(new Cookie(LANGUAGE_COOKIE, LANG_EN));
            }
        }
    }

    private void setLanguageCookie(HttpServletRequest request, HttpServletResponse response, String lang) throws ServletException {
        response.addCookie(new Cookie(LANGUAGE_COOKIE, lang));
        String query = request.getQueryString().substring(0, request.getQueryString().indexOf(LANGUAGE_COOKIE + "=" + lang));
        try {
            if (query.isEmpty()) {
                response.sendRedirect(request.getContextPath());
            } else {
                query = query.endsWith("&") ? query.substring(0, query.length() - 1) : query;
                response.sendRedirect(request.getContextPath() + "/app?" + query);
            }
        } catch (IOException e) {
            throw new ServletException("Failed to change language", e);
        }
    }

    @Override
    public void destroy() {

    }
}
