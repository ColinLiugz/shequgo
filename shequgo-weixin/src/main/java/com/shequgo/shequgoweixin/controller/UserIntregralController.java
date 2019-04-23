package com.shequgo.shequgoweixin.controller;

import entity.PageModel;
import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweixin.util.UserUtil;
import entity.IntegralRecord;
import facade.IntegralRecordFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;
import utils.MapUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Colin
 * @Date: 2019/3/30 0:30
 */
@Api(value = "UserIntregralController",description = "用户积分相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/weixin")
public class UserIntregralController {
    @Reference(version = "1.0.0")
    private IntegralRecordFacade integralRecordFacade;

    @ApiOperation(value = "获得全部用户积分记录列表")
    @RequestMapping(value = "/userIntegralRecord/list", method = RequestMethod.GET)
    public ApiResult listIntegralRecord(Integer page,Integer pageSize){
        Integer userId = UserUtil.getCurrentUserId();
        PageModel  <IntegralRecord> integralRecords = integralRecordFacade.listRecord(userId,page,pageSize);
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String,Object>> integralRecordList = new ArrayList<>();
        integralRecords.getContent().forEach(integralRecord -> {
            Map<String,Object> integralRecordInfo = MapUtil.beanToMap(integralRecord);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            integralRecordInfo.put("createData",sdf.format(integralRecord.getCreateData()));
            integralRecordList.add(integralRecordInfo);
        });
        resultMap.put("content",integralRecordList);
        resultMap.put("totalElements",integralRecords.getTotalElements());
        return ApiResult.ok(resultMap);
    }

    @ApiOperation(value = "获得用户积分增加记录列表")
    @RequestMapping(value = "/userIntegralAddRecord/list", method = RequestMethod.GET)
    public ApiResult  listIntegralAddRecord(Integer page, Integer pageSize){
        Integer userId = UserUtil.getCurrentUserId();
        PageModel<IntegralRecord> userRecord = integralRecordFacade.listRecordByUseridAndType(userId,0, page,pageSize);
        return ApiResult.ok(userRecord);
    }

    @ApiOperation(value = "获得用户积分使用记录列表")
    @RequestMapping(value = "/userIntegralUsedRecord/list", method = RequestMethod.GET)
    public ApiResult  listIntegralUsedRecord(Integer page, Integer pageSize){
        Integer userId = UserUtil.getCurrentUserId();
        PageModel<IntegralRecord> userRecord = integralRecordFacade.listRecordByUseridAndType(userId,1, page,pageSize);
        return ApiResult.ok(userRecord);
    }
}
