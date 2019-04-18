package com.shequgo.shequgoweb.controller;

import base.PageModel;
import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweb.filter.UserUtil;
import base.RegimentalInfo;
import facade.RegimentalInfoFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
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

    @ApiOperation(value = "查看团长申请列表")
    @RequestMapping(value = "/regimentalInfo/list", method = RequestMethod.GET)
    public ApiResult listRegimental(Integer status,Integer page,Integer pageSize){
        try {
            UserUtil.getCurrentUserId();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
        PageModel<RegimentalInfo> regimentalInfoPage = regimentalInfoFacade.listByStatus(status, page, pageSize);
        return ApiResult.ok(regimentalInfoPage);
    }

    @ApiOperation(value = "审批团长申请列表")
    @RequestMapping(value = "/regimentalInfo/update", method = RequestMethod.POST)
    public ApiResult listRegimental(Integer regimentalInfoId ,Integer status){
        try {
            UserUtil.getCurrentUserId();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
        RegimentalInfo regimentalInfo = regimentalInfoFacade.findById(regimentalInfoId);
        regimentalInfo.setStatus(status);
        regimentalInfo = regimentalInfoFacade.save(regimentalInfo);
        return ApiResult.ok(regimentalInfo);
    }
}
