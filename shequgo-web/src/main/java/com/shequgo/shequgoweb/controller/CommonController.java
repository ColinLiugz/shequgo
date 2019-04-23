package com.shequgo.shequgoweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import entity.Admin;
import com.shequgo.shequgoweb.filter.UserUtil;
import facade.AdminFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;
import utils.QiniuUtil;

/**
 * @Author: Colin
 * @Date: 2019/4/19 19:29
 */
@Api(value = "CommonController",description = "公共接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/web")
public class CommonController {
    @Reference(version = "1.0.0")
    private AdminFacade adminFacade;

    @ApiOperation(value = "获得七牛云token")
    @RequestMapping(value = "/get/qiniuToken", method = RequestMethod.POST)
    public ApiResult getQiniuToken(){
        Admin admin ;
        try {
            Integer userId = UserUtil.getCurrentUserId();
            admin = adminFacade.findById(userId);
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
        return ApiResult.ok(QiniuUtil.getTocken());
    }

    @ApiOperation(value = "未登陆")
    @RequestMapping(value = "/notLogin")
    public ApiResult notLogin(){
        return new ApiResult(401,"未登录");
    }

    @ApiOperation(value = "参数不能为空")
    @RequestMapping(value = "/paramsIsNull")
    public ApiResult paramsIsNull(){
        return ApiResult.error("参数不能为空！");
    }

}
