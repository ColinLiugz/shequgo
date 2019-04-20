package com.shequgo.shequgoweb.filter;

import entity.Admin;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Colin
 * @Date: 2019/3/25 22:42
 */
public class UserUtil {

    public static Admin getCurrentUser() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Admin currentUser = (Admin)request.getAttribute("currentUser");
        return currentUser;
    }

    public static int getCurrentUserId(){
        return getCurrentUser().getId();
    }
}
