package com.shequgo.shequgoservicecommodity.service;

import base.BaseService;
import base.SkuCategory;
import com.alibaba.dubbo.config.annotation.Service;
import com.shequgo.shequgoservicecommodity.repo.SkuCategoryRepo;
import facade.SkuCategoryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/23 14:21
 */
@Component("skuCategoryFacade")
@Service(version = "1.0.0")
public class SkuCategoryService extends BaseService<SkuCategory,SkuCategoryRepo> implements SkuCategoryFacade {

    @Autowired
    public SkuCategoryService(SkuCategoryRepo repo){
        super(repo);
    }

    @Override
    public List<SkuCategory> listAllNotDel(){
        return repo.listAllNotDel();
    }
}
