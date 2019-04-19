package com.shequgo.shequgoweb.filter;

import entity.Admin;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Colin
 * @Date: 2019/3/25 22:42
 */
public class UserUtil {

    public static Admin getCurrentUser() throws Exception {
        ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();
        HttpServletRequest request = requestHolder.get();
        Admin currentUser = (Admin)request.getAttribute("currentUser");
        if(null == currentUser){
            throw new Exception("401");
        }
        return currentUser;
    }

    public static int getCurrentUserId() throws Exception {
        return getCurrentUser().getId();
    }
}
