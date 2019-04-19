package com.shequgo.shequgoweb.controller;

import base.Sku;
import com.alibaba.dubbo.config.annotation.Reference;
import facade.SkuFacade;
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
 * @Date: 2019/3/30 23:25
 */
@Api(value = "SkuController",description = "商品管理相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/web")
public class SkuController {
    @Reference(version = "1.0.0")
    private SkuFacade skuFacade;

    @ApiOperation(value = "添加商品")
    @RequestMapping(value = "/sku/add", method = RequestMethod.POST)
    public ApiResult addSku(Integer categoryId, String skuName, String des, String subtitle, String thumbnail,
                            String richText, String price, String discountPrice, Integer amount, Integer isShow){
        Sku sku = new Sku(categoryId,skuName,des,subtitle,thumbnail,richText,new BigDecimal(price),
                new BigDecimal(discountPrice),amount,0,amount,0,0,isShow);
        sku = skuFacade.save(sku);
        return ApiResult.ok(sku);
    }

    @ApiOperation(value = "删除商品")
    @RequestMapping(value = "/sku/del", method = RequestMethod.POST)
    public ApiResult delSku(Integer skuId){
        Sku sku = skuFacade.findById(skuId);
        if(null == sku){
            return ApiResult.error("不存在的商品！");
        }
        skuFacade.delete(sku);
        return ApiResult.ok();
    }

    @ApiOperation(value = "查看某个商品")
    @RequestMapping(value = "/sku/findOne", method = RequestMethod.POST)
    public ApiResult findOneSku(Integer skuId){
        Sku sku = skuFacade.findById(skuId);
        if(null == sku){
            return ApiResult.error("不存在的商品！");
        }
        return ApiResult.ok(sku);
    }


}
