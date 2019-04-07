package com.shequgo.shequgoweixin.filter;

import base.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.RedisService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Colin
 * @Date: 2019/3/23 15:51
 */
@Component
public class CorsFilter implements Filter {
    @Autowired
    private RedisService redisService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(">>>>>>>>>>>>filter init<<<<<<<<<<<<");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        response.setHeader("Access-Control-Allow-Credentials" , "true");
        response.setHeader("Access-Control-Allow-Origin" , request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");

        String uri = request.getRequestURI();
        if(!"/login".equals(uri)){
            String authorization = request.getHeader("Authorization");
            if(StringUtils.isEmpty(authorization) || null == redisService.get(authorization)){
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }else {
                User user = (User)redisService.get(authorization);
                request.setAttribute("currentUser",user);
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("<<<<<<<<<<<filter destory>>>>>>>>>>>");
    }
}
