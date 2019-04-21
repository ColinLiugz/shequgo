package com.shequgo.shequgoweixin.controller;

import entity.PageModel;
import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweixin.util.UserUtil;
import entity.CommissionRecord;
import entity.RegimentalInfo;
import facade.CommisionRecordFacade;
import facade.RegimentalInfoFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;

import java.math.BigDecimal;

/**
 * @Author: Colin
 * @Date: 2019/3/30 0:47
 */
@Api(value = "CommissionRecordController",description = "团长佣金相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/weixin")
public class CommissionRecordController {
    @Reference(version = "1.0.0")
    private CommisionRecordFacade commisionRecordFacade;
    @Reference(version = "1.0.0")
    private RegimentalInfoFacade regimentalInfoFacade;

    @ApiOperation(value = "获得全部团长佣金记录列表")
    @RequestMapping(value = "/userCommissionRecord/list", method = RequestMethod.GET)
    public ApiResult listCommisionRecord(Integer page ,Integer  pageSize){
        Integer userId ;
        try {
            userId = UserUtil.getCurrentUserId();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
        PageModel<CommissionRecord> commissionRecords = commisionRecordFacade.listCommissionRecord(userId,page,pageSize);
        return ApiResult.ok(commissionRecords);
    }

    @ApiOperation(value = "佣金提现接口")
    @RequestMapping(value = "/userCommission/withdrawal", method = RequestMethod.POST)
    public ApiResult withdrawalCommision(Float amount){
        Integer userId ;
        try {
            userId = UserUtil.getCurrentUserId();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }

        RegimentalInfo regimentalInfo = new RegimentalInfo();
        if(regimentalInfo.getCommission().compareTo(BigDecimal.valueOf(amount)) < 0){
            return ApiResult.error("余额不足");
        }
        regimentalInfo.setCommission(regimentalInfo.getCommission().subtract(BigDecimal.valueOf(amount)));
        regimentalInfo = regimentalInfoFacade.save(regimentalInfo);

        CommissionRecord commissionRecord = new CommissionRecord();
        commissionRecord.setUserId(userId);
        commissionRecord.setType(1);
        commissionRecord.setStatus(1);
        commissionRecord.setPrice(BigDecimal.valueOf(amount));
        commisionRecordFacade.save(commissionRecord);
        return ApiResult.ok(regimentalInfo);
    }
}
