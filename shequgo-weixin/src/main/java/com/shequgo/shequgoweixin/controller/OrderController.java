package com.shequgo.shequgoweixin.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweixin.util.UserUtil;
import entity.*;
import facade.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Colin
 * @Date: 2019/3/27 7:42
 */
@Api(value = "OrderController",description = "订单相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/weixin")
public class OrderController {
    @Reference(version = "1.0.0")
    private OrderInfoFacade orderInfoFacade;
    @Reference(version = "1.0.0")
    private OrderGroupFacade orderGroupFacade;
    @Reference(version = "1.0.0")
    private SkuFacade skuFacade;
    @Reference(version = "1.0.0")
    private UserFacade userFacade;
    @Reference(version = "1.0.0")
    private IntegralRecordFacade integralRecordFacade;
    @Reference(version = "1.0.0")
    private CommisionRecordFacade commisionRecordFacade;
    @Reference(version = "1.0.0")
    private RegimentalInfoFacade  regimentalInfoFacade;

    @ApiOperation(value = "创建普通订单，skuKeyIdAndAmounts是 skuid@amount#skuid@amount 格式")
    @RequestMapping(value = "/ordinaryOrder/create", method = RequestMethod.POST)
    public ApiResult createOrdinaryOrder(Integer regimentalId,String skuKeyIdAndAmounts, Integer isUseIntegral, Integer logisticsType,Integer regimentalInfoId) throws IOException {
        Integer userId = UserUtil.getCurrentUserId();
        User user = UserUtil.getCurrentUser();

        OrderGroup orderGroup = new OrderGroup();
        orderGroup.setUserId(userId);
        orderGroup.setRegimentalId(regimentalId);
        orderGroup.setOrderNo(getOrderNo(userId));
        orderGroup.setLogisticsType(logisticsType);
        orderGroup.setIsUseIntegral(isUseIntegral);
        orderGroup = orderGroupFacade.save(orderGroup);

        BigDecimal countPrice = new BigDecimal(0);
        String[] skuArr = skuKeyIdAndAmounts.split("#");
        for(String skuAmount : skuArr){
            String[] skuAndAmountArr = skuAmount.split("@");
            Integer skuId = Integer.parseInt(skuAndAmountArr[0]);
            Integer amount = Integer.parseInt(skuAndAmountArr[1]);
            Sku sku = skuFacade.findById(skuId);
            if(sku.getSurplusAmount() < amount){
                return new ApiResult(1001,"库存不足");
            }
            sku.setSoldAmount(sku.getSoldAmount()+amount);
            sku.setSurplusAmount(sku.getSurplusAmount()-amount);
            sku = skuFacade.save(sku);

            countPrice.add(sku.getPrice().multiply(new BigDecimal(amount)));

            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderGroupId(orderGroup.getId());
            orderInfo.setSkuId(skuId);
            orderInfo.setAmount(amount);
            orderInfo.setUnitPrice(sku.getDiscountPrice());
            orderInfo.setSkuImage(JSON.json(sku));
            orderInfoFacade.save(orderInfo);
        }
        orderGroup.setCountPrice(countPrice);

        BigDecimal realPrice = countPrice;
        // 订单金额减去积分折扣
        if (isUseIntegral == 1){
            // 订单总价小于积分兑换总额时
            if(countPrice.compareTo(new BigDecimal(user.getIntegral()/100)) < 0 ){
                realPrice = new BigDecimal(0);
            } else {
                realPrice = countPrice.subtract(new BigDecimal(user.getIntegral()/100));
            }
        }
        orderGroup.setRealPrice(realPrice);
        orderGroup = orderGroupFacade.save(orderGroup);

        Map<String,Object> order = new HashMap<String,Object>();
        order.put("orderGroup",orderGroup);
        order.put("orderList",orderInfoFacade.listOrderInfoByOrderGroupId(orderGroup.getId()));
        return ApiResult.ok(order);
    }

    @ApiOperation(value = "用户支付某个订单")
    @RequestMapping(value = "/order/pay", method = RequestMethod.POST)
    public ApiResult orderPay(Integer orderGroupId){
        User user = UserUtil.getCurrentUser();
        Integer userId = user.getId();
        OrderGroup orderGroup = orderGroupFacade.findById(orderGroupId);
        orderGroup.setPaymentStatus(1);
        orderGroup.setLogisticsStatus(0);
        orderGroupFacade.save(orderGroup);

        if (orderGroup.getIsUseIntegral() == 1){
            // 用户使用积分
            IntegralRecord integralRecord = new IntegralRecord();
            integralRecord.setIntegral(orderGroup.getUsedIntegral());
            integralRecord.setOrderGroupId(orderGroup.getId());
            integralRecord.setType(2);
            integralRecord.setUserId(userId);
            integralRecordFacade.save(integralRecord);
            user.setIntegral(user.getIntegral() - orderGroup.getUsedIntegral());
            user = userFacade.save(user);
        }


        IntegralRecord integralRecord = new IntegralRecord();
        integralRecord.setIntegral(orderGroup.getCountPrice().setScale(0,BigDecimal.ROUND_DOWN).intValue());
        integralRecord.setOrderGroupId(orderGroup.getId());
        integralRecord.setType(0);
        integralRecord.setUserId(userId);
        integralRecordFacade.save(integralRecord);
        user.setIntegral(user.getIntegral() + integralRecord.getIntegral() );
        userFacade.save(user);

        CommissionRecord commissionRecord = new CommissionRecord();
        commissionRecord.setUserId(userId);
        commissionRecord.setPrice(orderGroup.getCountPrice().multiply(new BigDecimal(0.2)));
        commissionRecord.setStatus(1);
        commissionRecord.setType(0);
        commisionRecordFacade.save(commissionRecord);

        RegimentalInfo regimentalInfo = regimentalInfoFacade.findById(orderGroup.getRegimentalId());
        regimentalInfo.setCommission(regimentalInfo.getCommission().add(commissionRecord.getPrice()));
        regimentalInfoFacade.save(regimentalInfo);
        return ApiResult.ok();
    }

    @ApiOperation(value = "用户查看订单列表")
    @RequestMapping(value = "/ordinaryOrder/user/list", method = RequestMethod.GET)
    public ApiResult listOrderByType(Integer logisticsStatus,Integer page,Integer pageSize){
        Integer userId = UserUtil.getCurrentUserId();
        List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
        PageModel<OrderGroup> orderGroups = orderGroupFacade.listByUserIdAndType(userId,logisticsStatus, page,pageSize);
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

    @ApiOperation(value = "团长查看订单列表")
    @RequestMapping(value = "/ordinaryOrder/regimental/list", method = RequestMethod.GET)
    public ApiResult listOrderByRegimental(Integer logisticsStatus,Integer page,Integer pageSize){
        Integer userId = UserUtil.getCurrentUserId();
        RegimentalInfo regimentalInfo = regimentalInfoFacade.findByUserId(userId);
        List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
        PageModel<OrderGroup> orderGroups = orderGroupFacade.listByRegimentalIdAndType(regimentalInfo.getId(),logisticsStatus,page,pageSize);
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

    @ApiOperation(value = "团长修改订单状态 0未发货 1商家发货 2到达团长点 3等待自提 4团长配送中 5已签收")
    @RequestMapping(value = "/ordinaryOrder/update", method = RequestMethod.POST)
    public ApiResult listOrderByRegimental(Integer orderGroupId,Integer status){
        Integer userId = UserUtil.getCurrentUserId();
        RegimentalInfo regimentalInfo = regimentalInfoFacade.findByUserId(userId);
        OrderGroup orderGroup = orderGroupFacade.findById(orderGroupId);
        if(orderGroup == null){
            return ApiResult.error("不存在的订单");
        }else {
            orderGroup.setLogisticsType(status);
            orderGroupFacade.save(orderGroup);
            return ApiResult.ok(orderGroup);
        }
    }

    public static String getOrderNo(Integer userid) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i <= 3;i++){
            result+=random.nextInt(10);
        }
        return date+(userid+1001)+result;
    }

}
