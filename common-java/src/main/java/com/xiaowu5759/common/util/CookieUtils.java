package com.xiaowu5759.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaowu
 * @date 2021/5/7 11:34 AM
 */
public class CookieUtils {
    /**
     *  * 根据名字获取cookie
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie =  cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     *  * 将cookie封装到Map里
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     *  * 设置cookie  * @param response  * @param name  cookie名字  * @param value
     * cookie值  * @param maxAge cookie生命周期  以秒为单位  
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge,
                                 String cookieDomain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setDomain(cookieDomain);
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    public static String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = getCookieByName(request, name);
        return cookie == null ? null : cookie.getValue();// 取出cookie中的用户id
    }
}
