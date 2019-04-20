package com.shequgo.shequgoweixin.controller;

import entity.Admin;
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
 * @Date: 2019/4/20 16:58
 */
@Api(value = "CommonController",description = "公共接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/weixin")
public class CommonController {

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
