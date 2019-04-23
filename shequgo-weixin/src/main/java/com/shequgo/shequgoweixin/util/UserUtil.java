package com.shequgo.shequgoweixin.util;

import com.alibaba.dubbo.config.annotation.Reference;
import entity.User;
import facade.UserFacade;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Colin
 * @Date: 2019/3/25 22:42
 */
public class UserUtil {

    public static int getCurrentUserId(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        User currentUser = (User)request.getAttribute("currentUser");
        return currentUser.getId();
    }
}
