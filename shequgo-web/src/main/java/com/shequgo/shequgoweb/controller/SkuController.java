package com.shequgo.shequgoweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import facade.SkuFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utils.ApiResult;

/**
 * @Author: Colin
 * @Date: 2019/3/30 23:25
 */
@Api(value = "SkuController",description = "商品管理相关接口")
@RestController
@CrossOrigin
@RequestMapping(value = "/web")
public class SkuController {
    @Reference(validation = "1.0.0")
    private SkuFacade skuFacade;

    @ApiOperation(value = "添加商品")
    @RequestMapping(value = "/sku/add", method = RequestMethod.GET)
    public ApiResult addSku(){
        return ApiResult.ok();
    }
}
