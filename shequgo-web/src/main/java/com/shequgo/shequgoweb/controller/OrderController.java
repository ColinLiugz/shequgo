package com.shequgo.shequgoweb.controller;

import base.PageModel;
import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweb.filter.UserUtil;
import base.OrderGroup;
import base.OrderInfo;
import facade.OrderGroupFacade;
import facade.OrderInfoFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
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

    @ApiOperation(value = "查看订单列表")
    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public ApiResult listOrder(Integer logisticsStatus,Integer page,Integer pageSize){
        try {
            UserUtil.getCurrentUserId();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
        List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
        PageModel<OrderGroup> orderGroups = orderGroupFacade.listByType(logisticsStatus,page,pageSize);
        for(OrderGroup orderGroup : orderGroups.getContent()){
            List<OrderInfo> orderInfos = orderInfoFacade.listOrderInfoByOrderGroupId(orderGroup.getId());
            Map<String,Object> order = new HashMap<>();
            order.put("orderGroup",orderGroup);
            order.put("orderInfoList",orderInfos);
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
        try {
            UserUtil.getCurrentUserId();
        } catch (Exception e) {
            return new ApiResult(401,"未登录");
        }
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
