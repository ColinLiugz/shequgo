package com.shequgo.shequgoweb.controller;

import entity.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweb.filter.UserUtil;
import facade.OrderGroupFacade;
import facade.OrderInfoFacade;
import facade.RegimentalInfoFacade;
import facade.UserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;

import java.util.*;

/**
 * @Author: Colin
 * @Date: 2019/3/30 23:25
 */
@Api(value = "OrderController",description = "订单管理相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/web")
public class OrderController {
    @Reference(version = "1.0.0")
    private OrderGroupFacade orderGroupFacade;
    @Reference(version = "1.0.0")
    private OrderInfoFacade orderInfoFacade;
    @Reference(version = "1.0.0")
    private UserFacade userFacade;
    @Reference(version = "1.0.0")
    private RegimentalInfoFacade regimentalInfoFacade;

    @ApiOperation(value = "查看订单列表  logisticsStatus: 1全部 2未支付 3待发货 4待收货 5已签收")
    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public ApiResult listOrder(Integer logisticsStatus,Integer page,Integer pageSize){
        List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
        PageModel<OrderGroup> orderGroups = new PageModel<>();
        switch (logisticsStatus){
            case 1: {
                orderGroups = orderGroupFacade.listAll(page,pageSize);
                break;
            }
            case 2: {
                orderGroups = orderGroupFacade.listByType(-1, page,pageSize);
                break;
            }
            case 3: {
                orderGroups = orderGroupFacade.listByType(0, page,pageSize);
                break;
            }
            case 4: {
                orderGroups = orderGroupFacade.listByTypes("'1','2','3','4'", page,pageSize);
                break;
            }
            case 5: {
                orderGroups = orderGroupFacade.listByType(5, page,pageSize);
                break;
            }
            default: orderGroups = orderGroupFacade.listAll(page,pageSize);
        }
        for(OrderGroup orderGroup : orderGroups.getContent()){
            List<OrderInfo> orderInfos = orderInfoFacade.listOrderInfoByOrderGroupId(orderGroup.getId());
            User user = userFacade.findById(orderGroup.getUserId());
            RegimentalInfo regimentalInfo = regimentalInfoFacade.findById(orderGroup.getRegimentalId());
            Map<String,Object> order = new HashMap<>();
            order.put("user",user);
            order.put("regimentalInfo",regimentalInfo);
            order.put("orderInfoList",orderInfos);
            order.put("orderGroup",orderGroup);
            orderList.add(order);
        }
        Map<String,Object> orderPage = new HashMap<>();
        orderPage.put("content",orderList);
        orderPage.put("totalElements",orderGroups.getTotalElements());
        return ApiResult.ok(orderPage);
    }

    @ApiOperation(value = "修改订单状态 0未发货 1商家发货 2到达团长点 3等待自提 4团长配送中 5已签收")
    @RequestMapping(value = "/ordinaryOrder/update", method = RequestMethod.POST)
    public ApiResult listOrderByRegimental(Integer orderGroupId,Integer status){
        OrderGroup orderGroup = orderGroupFacade.findById(orderGroupId);
        if(orderGroup == null){
            return ApiResult.error("不存在的订单");
        }else {
            orderGroup.setLogisticsType(status);
            orderGroupFacade.save(orderGroup);
            return ApiResult.ok(orderGroup);
        }
    }
}
