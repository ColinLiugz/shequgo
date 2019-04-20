package com.shequgo.shequgoweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweb.filter.UserUtil;
import entity.Admin;
import facade.AdminFacade;
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

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult userLogin(String phone,String password) throws Exception {
        log.info(phone);
        Admin admin = adminFacade.findByPhone(phone);
        log.info(admin);
        if(null == admin){
            return ApiResult.error("不存在的用户");
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
        Admin admin ;
        try {
            admin = UserUtil.getCurrentUser();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
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
    public ApiResult  getUserInfo() throws Exception {
        Integer userId = UserUtil.getCurrentUserId();
        Admin admin = adminFacade.findById(userId);
        return ApiResult.ok(admin);
    }

    @ApiOperation(value = "新增管理员")
    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public ApiResult addAdmin(String name,String phone,String password) throws Exception {
        Integer userId ;
//        try {
//            userId = UserUtil.getCurrentUserId();
//        } catch (Exception e) {
//            return new ApiResult(401,"未登录");
//        }
        Admin admin = new Admin();
        admin.setName(name);
        admin.setPhone(phone);
        admin = adminFacade.save(admin);
        admin.setPassword(Md5Util.md5(password,admin.getId()+"passWord"));
        adminFacade.save(admin);
        return ApiResult.ok(admin);
    }

    @ApiOperation(value = "修改管理员密码")
    @RequestMapping(value = "/admin/updatePassword", method = RequestMethod.POST)
    public ApiResult addAdmin(String password) throws Exception {
        Integer userId ;
        try {
            userId = UserUtil.getCurrentUserId();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
        Admin admin = adminFacade.findById(userId);
        admin.setPassword(Md5Util.md5(password,admin.getId()+"passWord"));
        adminFacade.save(admin);
        return ApiResult.ok(admin);
    }
}
