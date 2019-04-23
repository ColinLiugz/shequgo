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
@Component
public class UserUtil {
    @Reference(version = "1.0.0")
    private UserFacade userFacade;

    public User getUser(Integer userId){
        return userFacade.findById(userId);
    }

    public static User getCurrentUser(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        User currentUser = (User)request.getAttribute("currentUser");
        return currentUser;
    }

    public static int getCurrentUserId(){
        return getCurrentUser().getId();
    }
}
