package com.shequgo.shequgoweixin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweixin.util.DistanceUtil;
import com.shequgo.shequgoweixin.util.UserUtil;
import entity.RegimentalInfo;
import facade.RegimentalInfoFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/30 13:26
 */
@Api(value = "RegimentalInfoController",description = "团长相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/weixin")
public class RegimentalInfoController {
    @Reference(version = "1.0.0")
    private RegimentalInfoFacade regimentalInfoFacade;

    @ApiOperation(value = "查看团长列表接口，按距离排序  location为经度#纬度格式")
    @RequestMapping(value = "/regimentalInfo/list", method = RequestMethod.GET)
    public ApiResult listNearbyRegimental(String location){
        List<RegimentalInfo> regimentalInfos = regimentalInfoFacade.findAllNotDel();
        if(StringUtils.isEmpty(location)){
            return ApiResult.ok(regimentalInfos);
        }else {
            regimentalInfos.forEach(r -> r.setDistance(DistanceUtil.getDistance(r.getLoccation(),location)));
            Collections.sort(regimentalInfos);
            return ApiResult.ok(regimentalInfos);
        }
    }

    @ApiOperation(value = "获得当前用户的团长信息")
    @RequestMapping(value = "/regimentalInfo/get", method = RequestMethod.GET)
    public ApiResult getRegimentalInfo(){
        Integer userId = UserUtil.getCurrentUserId();
        RegimentalInfo regimentalInfo = regimentalInfoFacade.findByUserId(userId);
        return ApiResult.ok(regimentalInfo);
    }

    @ApiOperation(value = "申请成为团长")
    @RequestMapping(value = "/regimentalInfo/add", method = RequestMethod.POST)
    public ApiResult addRegimentalInfo(String realName,String phone,String address,String location){
        Integer userId = UserUtil.getCurrentUserId();
        RegimentalInfo regimentalInfo = regimentalInfoFacade.findByUserId(userId);
        if(null != regimentalInfo){
            return ApiResult.error("已经是团长，不能重复申请！");
        }
        regimentalInfo = new RegimentalInfo();
        regimentalInfo.setRealName(realName);
        regimentalInfo.setPhone(phone);
        regimentalInfo.setAddress(address);
        regimentalInfo.setLoccation(location);
        regimentalInfo.setCommission(new BigDecimal(0));
        regimentalInfo.setStatus(0);
        regimentalInfo = regimentalInfoFacade.save(regimentalInfo);
        return ApiResult.ok(regimentalInfo);
    }

    @ApiOperation(value = "修改团长信息")
    @RequestMapping(value = "/regimentalInfo/update", method = RequestMethod.POST)
    public ApiResult updateRegimentalInfo(String realName,String phone,String address,String location){
        Integer userId = UserUtil.getCurrentUserId();
        RegimentalInfo regimentalInfo = regimentalInfoFacade.findByUserId(userId);
        if(null == regimentalInfo){
            return ApiResult.error("还不是团长，请先申请！");
        }
        regimentalInfo.setRealName(realName);
        regimentalInfo.setPhone(phone);
        regimentalInfo.setAddress(address);
        regimentalInfo.setLoccation(location);
        regimentalInfo.setStatus(0);
        regimentalInfo = regimentalInfoFacade.save(regimentalInfo);
        return ApiResult.ok(regimentalInfo);
    }
}
