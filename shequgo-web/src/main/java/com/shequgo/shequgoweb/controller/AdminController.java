package com.shequgo.shequgoweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweb.filter.UserUtil;
import entity.Admin;
import facade.AdminFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.RedisService;
import utils.ApiResult;
import utils.MapUtil;
import utils.Md5Util;
import utils.SmsUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author: Colin
 * @Date: 2019/3/27 8:09
 */
@Api(value = "AdminController",description = "管理员相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/web")
public class AdminController {
    @Autowired
    private RedisService redisService;
    @Reference(version = "1.0.0")
    private AdminFacade adminFacade;

    private static Logger log = Logger.getLogger(AdminController.class);

    @ApiOperation(value = "用户登录前发送验证码")
    @RequestMapping(value = "/send/checkCode/login", method = RequestMethod.POST)
    public ApiResult sendLoginCheckCode(String phone){
        String checkCode = SmsUtil.sendCheckCode(phone);
        redisService.set(phone + "_login",checkCode,300);
        return ApiResult.ok();
    }

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult userLogin(String phone,String password,String checkCode) throws Exception {
        Admin admin = adminFacade.findByPhone(phone);
        if(null == admin){
            return ApiResult.error("不存在的用户");
        }
        String serverCheckCode = (String)redisService.get(phone + "_login");
        if(!checkCode.equals(serverCheckCode)){
            return ApiResult.error("验证码错误！");
        }
        if(!Md5Util.md5(password,admin.getId()+"passWord").equals(admin.getPassword())){
            return ApiResult.error("密码错误");
        }
        String userTab = "web" + admin.getId();
        String md5str = "";
        try {
            md5str = Md5Util.md5(userTab,admin.getId()+"web");
            redisService.set(md5str,admin);
            Map<String,Object> userInfo = MapUtil.beanToMap(admin);
            userInfo.put("Authorization",md5str);
            return ApiResult.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("登录失败！");
        }
    }

    @ApiOperation(value = "用户登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ApiResult userLogin(){
        Admin admin = UserUtil.getCurrentUser();
        String userTab = "web" + admin.getId();
        String md5str="";
        try {
            md5str = Md5Util.md5(userTab,admin.getId()+"web");
            redisService.del(md5str);
            return ApiResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.error("登出失败！");
        }
    }

    @ApiOperation(value = "获得用户信息")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public ApiResult  getUserInfo() {
        Integer userId = UserUtil.getCurrentUserId();
        Admin admin = adminFacade.findById(userId);
        return ApiResult.ok(admin);
    }

    @ApiOperation(value = "新增管理员")
    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public ApiResult addAdmin(String name,String phone,String password) throws Exception {
        if(null != adminFacade.findByPhone(phone)){
            return ApiResult.error("该手机号已经添加过！");
        }
        Admin admin = new Admin();
        admin.setName(name);
        admin.setPhone(phone);
        admin = adminFacade.save(admin);
        admin.setPassword(Md5Util.md5(password,admin.getId()+"passWord"));
        adminFacade.save(admin);
        return ApiResult.ok(admin);
    }

    @ApiOperation(value = "给当前管理员发送验证码")
    @RequestMapping(value = "/send/checkCode/admin", method = RequestMethod.POST)
    public ApiResult sendAdminCheckCode(){
        String phone = UserUtil.getCurrentUser().getPhone();
        String checkCode = SmsUtil.sendCheckCode(phone);
        redisService.set(phone + "_admin",checkCode,300);
        return ApiResult.ok();
    }

    @ApiOperation(value = "修改管理员信息")
    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public ApiResult addAdmin(String name,String phone,String password,String checkCode) throws Exception {
        String adminPhone = UserUtil.getCurrentUser().getPhone();
        String serverCheckCode = (String)redisService.get(adminPhone + "_admin");
        if(!checkCode.equals(serverCheckCode)){
            return ApiResult.error("验证码错误！");
        }
        Integer userId = UserUtil.getCurrentUserId();
        Admin admin = adminFacade.findById(userId);
        admin.setName(name);
        admin.setPhone(phone);
        admin.setPassword(Md5Util.md5(password,admin.getId()+"passWord"));
        adminFacade.save(admin);
        return ApiResult.ok(admin);
    }

    @ApiOperation(value = "获得管理员列表")
    @RequestMapping(value = "/admin/list", method = RequestMethod.POST)
    public ApiResult listAdmin(){
        Integer adminId = UserUtil.getCurrentUserId();
        Admin admin = adminFacade.findById(adminId);
        List<Admin> othersAdminList = adminFacade.listOthersAdimn(adminId);
        othersAdminList.add(0,admin);
        return ApiResult.ok(othersAdminList);
    }
}
