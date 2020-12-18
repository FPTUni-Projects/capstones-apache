package com.org.api.vietin.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * CookiesUtils
 *
 *
 * @since 2020/06/13
 */
public class CookiesUtils {

    /**
     * Get cookie value by key
     *
     * @param request
     * @param key
     * @return
     */
    public static String getCookieValueByKey(HttpServletRequest request, String key) {
        String cookieVal = "";
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length != 0) {
            Optional<Cookie> optCookie = Arrays.asList(cookies).stream()
//                    .peek(c -> System.out.println(c.getName() + " - " + c.getValue()))
                    .filter(c -> c.getName().equals(key))
                    .findFirst();
            if (optCookie.isPresent()) {
                cookieVal = optCookie.get().getValue();
            }
        }

        return cookieVal;
    }

    /**
     * Add cookies
     *
     * @param response
     * @param cookieMap
     */
    public static void addCookies(HttpServletResponse response, Map<String, String> cookieMap, int expiration) {
        cookieMap.entrySet().stream().forEach(cm -> {
            Cookie cookie = new Cookie(cm.getKey(), cm.getValue());
            cookie.setMaxAge(expiration * 24 * 60 * 60);
//            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            response.addCookie(cookie);
        });
    }

    /**
     * Delete cookie by key
     *
     * @param response
     * @param key
     */
    public static void deleteCookieByKey(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setMaxAge(0);
//        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
    }
}
