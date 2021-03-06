package com.shequgo.shequgoweixin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweixin.util.UserUtil;
import entity.User;
import facade.IntegralRecordFacade;
import facade.UserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.RedisService;
import utils.ApiResult;
import utils.MapUtil;
import utils.Md5Util;

import java.util.Date;
import java.util.Map;

/**
 * @Author: Colin
 * @Date: 2019/2/24 23:13
 */
@Api(value = "UserController",description = "用户相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/weixin")
public class UserController {
    @Autowired
    private RedisService redisService;
    @Reference(version = "1.0.0")
    private UserFacade userFacade;
    @Reference(version = "1.0.0")
    private IntegralRecordFacade integralRecordFacade;

    private static Logger log = Logger.getLogger(UserController.class);

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult userLogin(String weixinCode,String encryptedData, String iv){
        User user = userFacade.getUserInfoByCode(weixinCode,encryptedData,iv);
        String date = new Date().toString();
        String userTab = "xiaochengxu" + user.getId() + user.getOpenid() + date;
        String md5str = "";
        try {
            md5str = Md5Util.md5(userTab,user.getId()+"xiaochengxu");
            redisService.set(md5str,user);
            Map<String,Object> userInfo = MapUtil.beanToMap(user);
            userInfo.put("Authorization",md5str);
            return ApiResult.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("登录失败！");
        }
    }

    @ApiOperation(value = "获得用户信息")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public ApiResult  getUserInfo(){
        Integer userId = UserUtil.getCurrentUserId();
        User user = userFacade.findById(userId);
        return ApiResult.ok(user);
    }
}
