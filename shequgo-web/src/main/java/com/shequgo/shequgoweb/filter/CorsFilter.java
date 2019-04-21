package com.shequgo.shequgoweb.filter;

import entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
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
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(200);
            return;
        }

        String uri = request.getRequestURI();
        if(isNeedCheckAuth(uri)){
            String authorization = request.getHeader("Authorization");
            if(StringUtils.isEmpty(authorization) || null == redisService.get(authorization)){
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/web/notLogin");
                requestDispatcher.forward(request, response);
                return;
            }else {
                Admin admin = (Admin)redisService.get(authorization);
                request.setAttribute("currentUser",admin);
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("<<<<<<<<<<<filter destory>>>>>>>>>>>");
    }

    private boolean isNeedCheckAuth(String uri){
        String[] notNeeds = {"/web/login","/web/send/checkCode/login"};
        if(uri.startsWith("/web/")){
            for(String notNeedUri : notNeeds){
                if(notNeedUri.equals(uri)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
