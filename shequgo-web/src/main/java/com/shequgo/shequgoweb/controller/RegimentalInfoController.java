package com.shequgo.shequgoweb.controller;

import entity.PageModel;
import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweb.filter.UserUtil;
import entity.RegimentalInfo;
import entity.User;
import facade.RegimentalInfoFacade;
import facade.UserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;

/**
 * @Author: Colin
 * @Date: 2019/3/30 23:27
 */
@Api(value = "RegimentalInfoController",description = "团长管理相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/web")
public class RegimentalInfoController {
    @Reference(version = "1.0.0")
    private RegimentalInfoFacade regimentalInfoFacade;
    @Reference(version = "1.0.0")
    private UserFacade userFacade;

    @ApiOperation(value = "查看团长申请列表, status空查全部 0正在审核 1审核通过 2审核失败")
    @RequestMapping(value = "/regimentalInfo/list", method = RequestMethod.GET)
    public ApiResult listRegimental(Integer status,Integer page,Integer pageSize){
        PageModel<RegimentalInfo> regimentalInfoPage;
        if(null == status){
            regimentalInfoPage = regimentalInfoFacade.listAll(page,pageSize);
        }else {
            regimentalInfoPage = regimentalInfoFacade.listByStatus(status, page, pageSize);
        }
        return ApiResult.ok(regimentalInfoPage);
    }

    @ApiOperation(value = "审批团长申请列表")
    @RequestMapping(value = "/regimentalInfo/update", method = RequestMethod.POST)
    public ApiResult listRegimental(Integer regimentalInfoId ,Integer status){
        RegimentalInfo regimentalInfo = regimentalInfoFacade.findById(regimentalInfoId);
        regimentalInfo.setStatus(status);
        regimentalInfo = regimentalInfoFacade.save(regimentalInfo);
        if(status == 1){
            User user = userFacade.findById(regimentalInfo.getUserId());
            user.setIsRegimental(1);
            userFacade.save(user);
        }
        return ApiResult.ok(regimentalInfo);
    }
}
