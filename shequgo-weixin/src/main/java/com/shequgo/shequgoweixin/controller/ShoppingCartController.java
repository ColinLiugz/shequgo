package com.shequgo.shequgoweixin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shequgo.shequgoweixin.util.UserUtil;
import entity.ShoppingCart;
import entity.Sku;
import facade.ShoppingCartFacade;
import facade.SkuFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;
import utils.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Colin
 * @Date: 2019/3/26 7:43
 */
@Api(value = "ShoppingCartController",description = "购物车相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/weixin")
public class ShoppingCartController {
    @Reference(version = "1.0.0")
    private ShoppingCartFacade shoppingCartFacade;
    @Reference(version = "1.0.0")
    private SkuFacade skuFacade;

    @ApiOperation(value = "向购物车中添加一件商品，数量为1")
    @RequestMapping(value = "/skuToShoppingCart/add", method = RequestMethod.POST)
    public ApiResult addSkuToShoppingCart(Integer skuId,Integer regimentalId){
        Integer userId = UserUtil.getCurrentUserId();
        Sku sku = skuFacade.findById(skuId);
        if(sku.getSurplusAmount()==0){
            return new ApiResult(1001,"库存不足");
        }
        ShoppingCart shoppingCart = shoppingCartFacade.findByUserIdAndRegimentalAndSkuid(userId,regimentalId,skuId);
        Integer  amount;
        if(null == shoppingCart){
            shoppingCart = new ShoppingCart();
            amount = 1;
        }else {
            amount = shoppingCart.getAmount()+1;
        }
        shoppingCart.setUserId(userId);
        shoppingCart.setRegimentalInfoId(regimentalId);
        shoppingCart.setSkuId(skuId);
        shoppingCart.setAmount(amount);
        shoppingCart.setIsDel(0);
        shoppingCart = shoppingCartFacade.save(shoppingCart);
        return ApiResult.ok(shoppingCart);
    }

    @ApiOperation(value = "对购物车中某件商品数量加一")
    @RequestMapping(value = "/shoppingCart/amount/add", method = RequestMethod.POST)
    public ApiResult addSkuToShoppingCart(Integer shoppingCartId){
        ShoppingCart shoppingCart = shoppingCartFacade.findById(shoppingCartId);
        if(shoppingCart == null){
            return ApiResult.error("不存在的购物车记录");
        }
        Sku sku = skuFacade.findById(shoppingCart.getSkuId());
        if(sku.getSurplusAmount()==0){
            return new ApiResult(1001,"库存不足");
        }
        shoppingCart.setAmount(shoppingCart.getAmount()+1);
        shoppingCart = shoppingCartFacade.save(shoppingCart);
        return ApiResult.ok(shoppingCart);
    }

    @ApiOperation(value = "对购物车中某件商品数量减一")
    @RequestMapping(value = "/shoppingCart/amount/reduce", method = RequestMethod.POST)
    public ApiResult reduceSkuToShoppingCart(Integer shoppingCartId){
        ShoppingCart shoppingCart = shoppingCartFacade.findById(shoppingCartId);
        if(shoppingCart == null){
            return ApiResult.error("不存在的购物车记录");
        }
        if(shoppingCart.getAmount() <= 1){
            shoppingCartFacade.delete(shoppingCart);
        } else {
            shoppingCart.setAmount(shoppingCart.getAmount()-1);
            shoppingCartFacade.save(shoppingCart);
        }
        return ApiResult.ok();
    }

    @ApiOperation(value = "删除购物车中某件商品")
    @RequestMapping(value = "/shoppingCart/deleteOne", method = RequestMethod.POST)
    public ApiResult delSkuToShoppingCart(Integer shoppingCartId){
        ShoppingCart shoppingCart = shoppingCartFacade.findById(shoppingCartId);
        if(shoppingCart ==null){
            return ApiResult.error("不存在的购物车记录");
        }
        shoppingCartFacade.delete(shoppingCart);
        return ApiResult.ok();
    }

    @ApiOperation(value = "查看购物车中所有商品")
    @RequestMapping(value = "/shoppingCart/list", method = RequestMethod.GET)
    public ApiResult listSkuToShoppingCart(Integer regimentalId){
        Integer userId = UserUtil.getCurrentUserId();
        List<ShoppingCart> shoppingCartList = shoppingCartFacade.listByUserAndRegimental(userId,regimentalId);
        List<Map<String,Object>> shoppingCartMapList = new ArrayList<>();
        shoppingCartList.forEach(shoppingCart -> {
            Map<String,Object> shoppingCartMap = MapUtil.beanToMap(shoppingCart);
            Integer skuId = shoppingCart.getSkuId();
            Sku sku = skuFacade.findHasAmountById(skuId);
            if(null == sku){
                shoppingCartMap.put("isSkuHas",1);
                sku = skuFacade.findById(skuId);
                shoppingCartMap.put("skuInfo",sku);
            }else{
                shoppingCartMap.put("isSkuHas",1);
                shoppingCartMap.put("skuInfo",sku);
            }
            shoppingCartMapList.add(shoppingCartMap);
        });
        return ApiResult.ok(shoppingCartMapList);
    }
}
