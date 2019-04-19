package com.shequgo.shequgoweixin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweixin.util.UserUtil;
import entity.ReceiveAddress;
import facade.ReceiveAddressFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/25 23:38
 */
@Api(value = "ReceiveAddressController",description = "购物车相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/weixin")
public class ReceiveAddressController {
    @Reference(version = "1.0.0")
    private ReceiveAddressFacade receiveAddressFacade;

    @ApiOperation(value = "添加收货地址")
    @RequestMapping(value = "/add/receiveAddress", method = RequestMethod.GET)
    public ApiResult addReceiveAddress(String receiveName,String receivePhone,String address,Integer isDefault){
        Integer userId ;
        try {
            userId = UserUtil.getCurrentUserId();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
        if(1 == isDefault){
            ReceiveAddress defaultAddress = receiveAddressFacade.findDefault(userId);
            if(null != defaultAddress){
                defaultAddress.setIsDefault(0);
                receiveAddressFacade.save(defaultAddress);
            }
        }
        ReceiveAddress receiveAddress = new ReceiveAddress();
        receiveAddress.setUserId(userId);
        receiveAddress.setReceiveName(receiveName);
        receiveAddress.setReceivePhone(receivePhone);
        receiveAddress.setAddress(address);
        receiveAddress.setIsDefault(isDefault);
        receiveAddress= receiveAddressFacade.save(receiveAddress);
        return ApiResult.ok(receiveAddress);
    }

    @ApiOperation(value = "删除收货地址信息")
    @RequestMapping(value = "/delete/receiveAddress", method = RequestMethod.GET)
    public ApiResult delReceiveAddress(Integer addressId){
        Integer userId ;
        try {
            userId = UserUtil.getCurrentUserId();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
        ReceiveAddress receiveAddress = receiveAddressFacade.findById(addressId);
        if(receiveAddress == null){
            return ApiResult.error("不存在的收货地址！");
        }
        receiveAddressFacade.delete(receiveAddress);
        return ApiResult.ok();
    }

    @ApiOperation(value = "修改收货地址信息")
    @RequestMapping(value = "/update/receiveAddress", method = RequestMethod.GET)
    public ApiResult updateReceiveAddress(Integer addressId,String receiveName,String receivePhone,String address,Integer isDefault){
        Integer userId ;
        try {
            userId = UserUtil.getCurrentUserId();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
        if(isDefault == 1){
            ReceiveAddress defaultAddress = receiveAddressFacade.findDefault(userId);
            if(null != defaultAddress){
                defaultAddress.setIsDefault(0);
                receiveAddressFacade.save(defaultAddress);
            }
        }
        ReceiveAddress receiveAddress = receiveAddressFacade.findById(addressId);
        if(receiveAddress == null){
            return ApiResult.error("不存在的收货地址！");
        }
        receiveAddress.setUserId(userId);
        receiveAddress.setReceiveName(receiveName);
        receiveAddress.setReceivePhone(receivePhone);
        receiveAddress.setAddress(address);
        receiveAddress.setIsDefault(isDefault);
        receiveAddress= receiveAddressFacade.save(receiveAddress);
        return ApiResult.ok(receiveAddress);
    }

    @ApiOperation(value = "查看收货地址信息列表")
    @RequestMapping(value = "/list/receiveAddress", method = RequestMethod.GET)
    public ApiResult listReceiveAddress(){
        Integer userId ;
        try {
            userId = UserUtil.getCurrentUserId();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
        List<ReceiveAddress> receiveAddressList = receiveAddressFacade.listByUser(userId);
        return ApiResult.ok(receiveAddressList);
    }

}
