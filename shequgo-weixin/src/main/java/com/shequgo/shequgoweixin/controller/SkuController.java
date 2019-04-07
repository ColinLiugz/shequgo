package com.shequgo.shequgoweixin.controller;

import base.PageModel;
import base.Sku;
import base.SkuCategory;
import com.alibaba.dubbo.config.annotation.Reference;
import facade.SkuCategoryFacade;
import facade.SkuFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Colin
 * @Date: 2019/3/23 9:46
 */
@Api(value = "SkuController",description = "商品相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/weixin")
public class SkuController {
    @Reference(version = "1.0.0")
    private SkuCategoryFacade skuCategoryFacade;
    @Reference(version = "1.0.0")
    private SkuFacade skuFacade;

    @ApiOperation(value = "获得商品类别")
    @RequestMapping(value = "/list/skuCategory", method = RequestMethod.GET)
    public ApiResult listSkuCategory(){
        List<SkuCategory> skuCategories = skuCategoryFacade.listAllNotDel();
        return ApiResult.ok(skuCategories);
    }

    @ApiOperation(value = "获得商品列表，categoryId为0时显示全部")
    @RequestMapping(value = "/list/sku", method = RequestMethod.GET)
    public ApiResult listSku(Integer categoryId,Integer page,Integer pageSize){
        PageModel<Sku> skuList;
        if(categoryId == 0){
            skuList = skuFacade.listOrdinarySku(page,pageSize);
        } else {
            skuList = skuFacade.listOrdinarySkuByCategoryId(categoryId,page,pageSize);
        }
        return ApiResult.ok(skuList);
    }

    @ApiOperation(value = "获得团购商品列表")
    @RequestMapping(value = "/list/groupBuyingSku", method = RequestMethod.GET)
    public ApiResult listGroupSku(Integer page,Integer pageSize){
        PageModel<Sku> skuList = skuFacade.listGroupBuyingSku(page,pageSize);
        return ApiResult.ok(skuList);
    }

    @ApiOperation(value = "获得某个商品信息")
    @RequestMapping(value = "/getOne/sku", method = RequestMethod.GET)
    public ApiResult listSku(Integer skuId){
        Sku sku = skuFacade.findById(skuId);
        if(sku  == null){
            return ApiResult.error("不存在的商品！");
        }
        return ApiResult.ok(sku);
    }
}
