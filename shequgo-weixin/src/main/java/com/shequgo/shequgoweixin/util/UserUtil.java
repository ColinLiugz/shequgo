package com.shequgo.shequgoweixin.util;

import entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Colin
 * @Date: 2019/3/25 22:42
 */
public class UserUtil {

    public static User getCurrentUser() throws Exception {
        ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();
        HttpServletRequest request = requestHolder.get();
        User currentUser = (User)request.getAttribute("currentUser");
        if(null == currentUser){
            throw new Exception("401");
        }
        return currentUser;
    }

    public static int getCurrentUserId() throws Exception {
        return getCurrentUser().getId();
    }
}
