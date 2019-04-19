package com.shequgo.shequgoweb.controller;

import entity.Admin;
import com.shequgo.shequgoweb.filter.UserUtil;
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


    @ApiOperation(value = "获得七牛云token")
    @RequestMapping(value = "/get/qiniuToken", method = RequestMethod.POST)
    public ApiResult getQiniuToken(){
        Admin admin ;
        try {
            admin = UserUtil.getCurrentUser();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
        return ApiResult.ok(QiniuUtil.getTocken());
    }

}
