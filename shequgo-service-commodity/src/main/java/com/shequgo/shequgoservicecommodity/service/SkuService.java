package com.shequgo.shequgoservicecommodity.service;

import entity.BaseService;
import entity.PageModel;
import entity.Sku;
import com.alibaba.dubbo.config.annotation.Service;
import com.shequgo.shequgoservicecommodity.repo.SkuRepo;
import facade.SkuFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * @Author: Colin
 * @Date: 2019/3/23 14:21
 */
@Component("skuFacade")
@Service(version = "1.0.0")
public class SkuService extends BaseService<Sku,SkuRepo> implements SkuFacade{

    @Autowired
    public SkuService(SkuRepo repo){
        super(repo);
    }

    @Override
    public Sku findHasAmountById(Integer skuId){
        return repo.findHasAmountById(skuId);
    }

    @Override
    public PageModel<Sku> listAll(Integer page,Integer pageSize){
        Page<Sku> skus = repo.listAll(PageRequest.of(page-1,pageSize));
        return new PageModel<Sku>(skus.getTotalElements(),skus.getContent());
    }

    @Override
    public PageModel<Sku> listByCategoryId(Integer categoryId,Integer page,Integer pageSize){
        Page<Sku> skus = repo.listByCategoryId(categoryId,PageRequest.of(page-1,pageSize));
        return new PageModel<Sku>(skus.getTotalElements(),skus.getContent());
    }

    @Override
    public PageModel<Sku> listByIsShow(Integer isShow,Integer page,Integer pageSize){
        Page<Sku> skus = repo.listByIsShow(isShow,PageRequest.of(page-1,pageSize));
        return new PageModel<Sku>(skus.getTotalElements(),skus.getContent());
    }

    @Override
    public PageModel<Sku> listByCategoryIdAndIsShow(Integer categoryId,Integer isShow,Integer page,Integer pageSize){
        Page<Sku> skus = repo.listByCategoryIdAndIsShow(categoryId,isShow,PageRequest.of(page-1,pageSize));
        return new PageModel<Sku>(skus.getTotalElements(),skus.getContent());
    }

    @Override
    public PageModel<Sku> listOrdinarySkuByCategoryId(Integer categoryId, Integer page,Integer pageSize){
        Page<Sku> skus = repo.listOrdinarySkuByCategoryId(categoryId, PageRequest.of(page-1,pageSize));
        return new PageModel<Sku>(skus.getTotalElements(),skus.getContent());
    }

    @Override
    public PageModel<Sku> listOrdinarySku(Integer page, Integer pageSize){
        Page<Sku> skus = repo.listOrdinarySku( PageRequest.of(page-1,pageSize));
        return new PageModel<Sku>(skus.getTotalElements(),skus.getContent());
    }
    @Override
    public PageModel<Sku> listGroupBuyingSku(Integer page,Integer pageSize){
        Page<Sku> skus = repo.listGroupBuyingSku( PageRequest.of(page-1,pageSize));
        return new PageModel<Sku>(skus.getTotalElements(),skus.getContent());
    }
}
