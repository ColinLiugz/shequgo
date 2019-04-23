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
import utils.MapUtil;

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
    @Reference(version = "1.0.0")
    private ShoppingCartFacade shoppingCartFacade;
    @Reference(version = "1.0.0")
    private ReceiveAddressFacade receiveAddressFacade;

    @ApiOperation(value = "创建普通订单，skuKeyIdAndAmounts是 skuid@amount#skuid@amount 格式")
    @RequestMapping(value = "/ordinaryOrder/create", method = RequestMethod.POST)
    public ApiResult createOrdinaryOrder(Integer regimentalId,String skuKeyIdAndAmounts, Integer isUseIntegral, Integer logisticsType, Integer isShoppingCart) throws IOException {
        Integer userId = UserUtil.getCurrentUserId();
        User user = userFacade.findById(userId);

        if(!isSkuHasAmount(skuKeyIdAndAmounts)){
            return new ApiResult(1001,"库存不足");
        }

        OrderGroup orderGroup = new OrderGroup();
        orderGroup.setUserId(userId);
        orderGroup.setRegimentalId(regimentalId);
        orderGroup.setOrderNo(getOrderNo(userId));
        orderGroup.setLogisticsType(logisticsType);
        orderGroup.setIsUseIntegral(isUseIntegral);
        orderGroup.setReceiveAddress(MapUtil.beanToMap(receiveAddressFacade.findDefault(userId)).toString());
        orderGroup = orderGroupFacade.save(orderGroup);

        BigDecimal countPrice = new BigDecimal(0);
        Integer countAmount = 0;
        String[] skuArr = skuKeyIdAndAmounts.split("#");
        for(String skuAmount : skuArr){
            String[] skuAndAmountArr = skuAmount.split("@");
            Integer skuId = Integer.parseInt(skuAndAmountArr[0]);
            Integer amount = Integer.parseInt(skuAndAmountArr[1]);
            Sku sku = skuFacade.findById(skuId);
            sku.setSoldAmount(sku.getSoldAmount()+amount);
            sku.setSurplusAmount(sku.getSurplusAmount()-amount);
            sku = skuFacade.save(sku);

            countAmount = countAmount + amount;
            countPrice = countPrice.add(sku.getDiscountPrice().multiply(new BigDecimal(amount)));

            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderGroupId(orderGroup.getId());
            orderInfo.setSkuId(skuId);
            orderInfo.setAmount(amount);
            orderInfo.setUnitPrice(sku.getDiscountPrice());
            orderInfo.setSkuImage(JSON.json(sku));
            orderInfoFacade.save(orderInfo);

            // 删除购物车里的
            if(isShoppingCart == 1){
                ShoppingCart shoppingCart = shoppingCartFacade.findByUserIdAndRegimentalAndSkuid(userId,regimentalId,skuId);
                if(null != shoppingCart){
                    shoppingCart.setIsDel(1);
                    shoppingCartFacade.save(shoppingCart);
                }
            }
        }
        orderGroup.setCountAmount(countAmount);
        orderGroup.setCountPrice(countPrice);

        BigDecimal realPrice = countPrice;
        // 订单金额减去积分折扣
        if (isUseIntegral == 1){
            // 订单总价小于积分兑换总额时
            if(countPrice.compareTo(new BigDecimal(user.getIntegral()/100)) < 0 ){
                orderGroup.setUsedIntegral(user.getIntegral() - countPrice.multiply(new BigDecimal(100)).intValue());
                realPrice = new BigDecimal(0);
            } else {
                orderGroup.setUsedIntegral(user.getIntegral());
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
        Integer userId = UserUtil.getCurrentUserId();
        User user = userFacade.findById(userId);
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

    @ApiOperation(value = "用户查看订单列表 logisticsStatus: 1全部 2未支付 3待发货 4待收货 5已签收")
    @RequestMapping(value = "/ordinaryOrder/user/list", method = RequestMethod.GET)
    public ApiResult listOrderByType(Integer logisticsStatus,Integer page,Integer pageSize){
        Integer userId = UserUtil.getCurrentUserId();
        List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
        PageModel<OrderGroup> orderGroups = new PageModel<>();
        switch (logisticsStatus){
            case 1: {
                orderGroups = orderGroupFacade.listByUserId(userId, page,pageSize);
                break;
            }
            case 2: {
                orderGroups = orderGroupFacade.listByUserIdAndType(userId,-1, page,pageSize);
                break;
            }
            case 3: {
                orderGroups = orderGroupFacade.listByUserIdAndType(userId,0, page,pageSize);
                break;
            }
            case 4: {
                List<Integer> screen = new ArrayList<>();
                screen.add(1);
                screen.add(2);
                screen.add(3);
                screen.add(4);
                orderGroups = orderGroupFacade.listByUserIdAndTypes(userId,screen, page,pageSize);
                break;
            }
            case 5: {
                orderGroups = orderGroupFacade.listByUserIdAndType(userId,5, page,pageSize);
                break;
            }
            default: orderGroups = orderGroupFacade.listByUserId(userId, page,pageSize);
        }
        for(OrderGroup orderGroup : orderGroups.getContent()){
            List<OrderInfo> orderInfos = orderInfoFacade.listOrderInfoByOrderGroupId(orderGroup.getId());
            RegimentalInfo regimentalInfo = regimentalInfoFacade.findById(orderGroup.getRegimentalId());
            Map<String,Object> order = new HashMap<>();
            order.put("regimentalInfo",regimentalInfo);
            Map<String,Object> orderGroupInfo = MapUtil.beanToMap(orderGroup);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            orderGroupInfo.put("createData",sdf.format(orderGroup.getCreateData()));
            order.put("orderGroup",orderGroupInfo);
            order.put("orderInfoList",orderInfos);
            orderList.add(order);
        }
        Map<String,Object> orderPage = new HashMap<>();
        orderPage.put("content",orderList);
        orderPage.put("totalElements",orderGroups.getTotalElements());
        return ApiResult.ok(orderPage);
    }

    @ApiOperation(value = "团长查看订单列表 logisticsStatus: 1全部 2待接收 3派送中 4已签收")
    @RequestMapping(value = "/ordinaryOrder/regimental/list", method = RequestMethod.GET)
    public ApiResult listOrderByRegimental(Integer logisticsStatus,Integer page,Integer pageSize){
        Integer userId = UserUtil.getCurrentUserId();
        RegimentalInfo regimentalInfo = regimentalInfoFacade.findByUserId(userId);
        Integer regimentalId = regimentalInfo.getId();
        List<Map<String,Object>> orderList = new ArrayList<Map<String,Object>>();
        PageModel<OrderGroup> orderGroups = new PageModel<>();
        switch (logisticsStatus){
            case 1: {
                orderGroups = orderGroupFacade.listByRegimentalId(regimentalId, page,pageSize);
                break;
            }
            case 2: {
                orderGroups = orderGroupFacade.listByRegimentalIdAndType(regimentalId,1, page,pageSize);
                break;
            }
            case 3: {
                List<Integer> screen = new ArrayList<>();
                screen.add(3);
                screen.add(4);
                orderGroups = orderGroupFacade.listByRegimentalIdAndTypes(regimentalId, screen, page,pageSize);
                break;
            }
            case 4: {
                orderGroups = orderGroupFacade.listByRegimentalIdAndType(regimentalId,5, page,pageSize);
                break;
            }
            default: orderGroups = orderGroupFacade.listByRegimentalId(regimentalId, page,pageSize);
        }
        for(OrderGroup orderGroup : orderGroups.getContent()){
            List<OrderInfo> orderInfos = orderInfoFacade.listOrderInfoByOrderGroupId(orderGroup.getId());
            Map<String,Object> order = new HashMap<>();
            Map<String,Object> orderGroupInfo = MapUtil.beanToMap(orderGroup);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            orderGroupInfo.put("createData",sdf.format(orderGroup.getCreateData()));
            order.put("orderGroup",orderGroupInfo);
            order.put("orderInfoList",orderInfos);
            orderList.add(order);
        }
        Map<String,Object> orderPage = new HashMap<>();
        orderPage.put("content",orderList);
        orderPage.put("totalElements",orderGroups.getTotalElements());
        return ApiResult.ok(orderPage);
    }

    @ApiOperation(value = "团长修改订单状态 0未发货 1商家发货 2到达团长点 3等待自提 4团长配送中 5已签收")
    @RequestMapping(value = "/ordinaryOrder/regimental/update", method = RequestMethod.POST)
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

    @ApiOperation(value = "用户签收")
    @RequestMapping(value = "/ordinaryOrder/user/receive", method = RequestMethod.POST)
    public ApiResult listOrderByRegimental(Integer orderGroupId){
        Integer userId = UserUtil.getCurrentUserId();
        OrderGroup orderGroup = orderGroupFacade.findById(orderGroupId);
        if(orderGroup == null){
            return ApiResult.error("不存在的订单");
        }else {
            orderGroup.setLogisticsType(5);
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

    private boolean isSkuHasAmount(String skuKeyIdAndAmounts){
        String[] skuArr = skuKeyIdAndAmounts.split("#");
        for(String skuAmount : skuArr) {
            String[] skuAndAmountArr = skuAmount.split("@");
            Integer skuId = Integer.parseInt(skuAndAmountArr[0]);
            Integer amount = Integer.parseInt(skuAndAmountArr[1]);
            Sku sku = skuFacade.findById(skuId);
            if (sku.getIsDel()==1 || sku.getIsShow() == 0 || sku.getSurplusAmount() < amount) {
                return false;
            }
        }
        return true;
    }

}
