package com.shequgo.shequgoweb.controller;

import entity.PageModel;
import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweb.filter.UserUtil;
import entity.RegimentalInfo;
import entity.User;
import facade.CommisionRecordFacade;
import facade.RegimentalInfoFacade;
import facade.UserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;
import utils.MapUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Reference(version = "1.0.0")
    private CommisionRecordFacade commisionRecordFacade;

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

    @ApiOperation(value = "查看团长业绩,date为2019-04格式")
    @RequestMapping(value = "/regimentalInfo/commission/list", method = RequestMethod.GET)
    public ApiResult listCommission(String date,Integer page,Integer pageSize){
        PageModel<RegimentalInfo> regimentalInfoPage = regimentalInfoFacade.listByStatus(1, page, pageSize);
        List<Map> regimentalList = new ArrayList<>();
        regimentalInfoPage.getContent().forEach((RegimentalInfo regimentalInfo) -> {
            BigDecimal count = commisionRecordFacade.sumComByMonth(regimentalInfo.getUserId(),date);
            if(count == null){
                count = new BigDecimal(0);
            }
            Map<String,Object> regimentalMap = MapUtil.beanToMap(regimentalInfo);
            regimentalMap.put("countCommission",count);
            regimentalList.add(regimentalMap);
        });
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("content",regimentalList);
        resultMap.put("totalElements",regimentalInfoPage.getTotalElements());
        return ApiResult.ok(resultMap);
    }

    @ApiOperation(value = "审批团长申请列表 1通过 2不通过")
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
