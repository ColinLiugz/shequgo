package com.shequgo.shequgoservicecommodity.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.shequgo.shequgoservicecommodity.repo.ShoppingCartRepo;
import entity.BaseService;
import entity.ShoppingCart;
import facade.ShoppingCartFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/26 7:40
 */
@Component("shoppingCartFacade")
@Service(version = "1.0.0")
public class ShoppingCartService extends BaseService<ShoppingCart,ShoppingCartRepo> implements ShoppingCartFacade {
    @Autowired
    public ShoppingCartService(ShoppingCartRepo repo){
        super(repo);
    }

    @Override
    public ShoppingCart findByUserIdAndRegimentalAndSkuid(Integer userId, Integer regimentalInfoId,Integer skuId){
        return repo.findByUserIdAndRegimentalAndSkuid(userId,regimentalInfoId,skuId);
    }

    @Override
    public List<ShoppingCart> listByUserAndRegimental(Integer userId, Integer regimentalInfoId){
        return repo.listByUserAndRegimental(userId,regimentalInfoId);
    }
}
