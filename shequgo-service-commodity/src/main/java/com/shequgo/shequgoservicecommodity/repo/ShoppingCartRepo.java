package com.shequgo.shequgoservicecommodity.repo;

import base.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/26 7:37
 */
public interface ShoppingCartRepo extends JpaRepository<ShoppingCart,Integer> {

    @Query("select s from ShoppingCart s where s.userId=?1 and s.regimentalInfoId=?2 and s.skuId=?3 and s.isDel=0")
    ShoppingCart findByUserIdAndRegimentalAndSkuid(Integer userId, Integer regimentalInfoId,Integer skuId);

    @Query("select s from ShoppingCart s where s.userId=?1 and s.regimentalInfoId=?2 and s.isDel=0 order by s.updateData desc")
    List<ShoppingCart> listByUserAndRegimental(Integer userId,Integer regimentalInfoId);
}
