package com.shequgo.shequgoweb.controller;

import base.SkuCategory;
import com.alibaba.dubbo.config.annotation.Reference;
import facade.SkuCategoryFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/4/18 21:16
 */
@Api(value = "CategoryController",description = "商品分类管理相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/web")
public class CategoryController {

    @Reference(version = "1.0.0")
    private SkuCategoryFacade skuCategoryFacade;

    @ApiOperation(value = "添加商品分类")
    @RequestMapping(value = "/skuCategory/add", method = RequestMethod.POST)
    public ApiResult addCategory(String categoryName){
        SkuCategory skuCategory = new SkuCategory();
        skuCategory.setCategoryName(categoryName);
        skuCategory = skuCategoryFacade.save(skuCategory);
        return ApiResult.ok(skuCategory);
    }

    @ApiOperation(value = "修改商品分类")
    @RequestMapping(value = "/skuCategory/update", method = RequestMethod.POST)
    public ApiResult updateCategory(Integer categoryId,String categoryName){
        SkuCategory skuCategory = skuCategoryFacade.findById(categoryId);
        if(null == skuCategory){
            return ApiResult.error("不存在的商品分类");
        }
        skuCategory.setCategoryName(categoryName);
        skuCategory = skuCategoryFacade.save(skuCategory);
        return ApiResult.ok(skuCategory);
    }

    @ApiOperation(value = "删除商品分类")
    @RequestMapping(value = "/skuCategory/del", method = RequestMethod.POST)
    public ApiResult delCategory(Integer categoryId){
        SkuCategory skuCategory = skuCategoryFacade.findById(categoryId);
        if(null == skuCategory){
            return ApiResult.error("不存在的商品分类");
        }
        skuCategory.setIsDel(1);
        skuCategoryFacade.save(skuCategory);
        return ApiResult.ok(skuCategory);
    }

    @ApiOperation(value = "查看商品分类列表")
    @RequestMapping(value = "/skuCategory/list", method = RequestMethod.GET)
    public ApiResult listCategory(){
        List<SkuCategory> skuCategories = skuCategoryFacade.listAllNotDel();
        return ApiResult.ok(skuCategories);
    }

    @ApiOperation(value = "查看某个商品分类")
    @RequestMapping(value = "/skuCategory/findOne", method = RequestMethod.POST)
    public ApiResult findOneCategory(Integer categoryId){
        SkuCategory skuCategory = skuCategoryFacade.findById(categoryId);
        if(null == skuCategory){
            return ApiResult.error("不存在的商品分类");
        }
        return ApiResult.ok(skuCategory);
    }

}
