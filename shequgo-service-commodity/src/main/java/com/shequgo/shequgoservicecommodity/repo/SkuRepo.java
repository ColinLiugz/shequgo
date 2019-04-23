package com.shequgo.shequgoservicecommodity.repo;

import entity.Sku;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: Colin
 * @Date: 2019/3/23 14:16
 */
public interface SkuRepo extends JpaRepository<Sku,Integer> {

    @Query("select s from Sku s where s.id = ?1 and s.amount > 0 and s.isDel=0")
    Sku findHasAmountById(Integer skuId);

    @Query("select s from Sku s where s.isDel=0 ")
    Page<Sku> listAll(Pageable pageable);

    @Query("select s from Sku s where s.isDel=0 and s.categoryId=?1")
    Page<Sku> listByCategoryId(Integer categoryId,Pageable pageable);

    @Query("select s from Sku s where s.isDel=0 and s.isShow=?1")
    Page<Sku> listByIsShow(Integer isShow,Pageable pageable);

    @Query("select s from Sku s where s.isDel=0 and s.categoryId=?1 and s.isShow=?2")
    Page<Sku> listByCategoryIdAndIsShow(Integer categoryId,Integer isShow,Pageable pageable);

    @Query("select s from Sku s where s.categoryId=?1 and s.surplusAmount > 0 and s.isGroupBuying=0  and s.isDel=0 and s.isShow =1 order by s.id desc")
    Page<Sku> listOrdinarySkuByCategoryId(Integer categoryId, Pageable pageable);

    @Query("select s from Sku s where s.isGroupBuying=0 and s.surplusAmount > 0 and s.isDel=0 and s.isShow =1 order by s.id desc")
    Page<Sku> listOrdinarySku(Pageable pageable);

    @Query("select s from Sku s where s.isGroupBuying=1  and s.isDel=0 and s.isShow =1 order by s.id desc")
    Page<Sku> listGroupBuyingSku(Pageable pageable);
}
