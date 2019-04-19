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

    @Query("select s from Sku s where s.isDel=0 ")
    Page<Sku> listAll(Integer page,Integer pageSize);

    @Query("select s from Sku s where s.isDel=0 and s.categoryId=?1")
    Page<Sku> listByCategoryId(Integer categoryId,Integer page,Integer pageSize);

    @Query("select s from Sku s where s.isDel=0 and s.isShow=?1")
    Page<Sku> listByIsShow(Integer isShow,Integer page,Integer pageSize);

    @Query("select s from Sku s where s.isDel=0 and s.categoryId=?1 and s.isShow=?2")
    Page<Sku> listByCategoryIdAndIsShow(Integer categoryId,Integer isShow,Integer page,Integer pageSize);

    @Query("select s from Sku s where s.categoryId=?1 and s.isGroupBuying=0  and s.isDel=0 and s.isShow =1 order by s.id desc")
    Page<Sku> listOrdinarySkuByCategoryId(Integer categoryId, Pageable pageable);

    @Query("select s from Sku s where s.isGroupBuying=0  and s.isDel=0 and s.isShow =1 order by s.id desc")
    Page<Sku> listOrdinarySku(Pageable pageable);

    @Query("select s from Sku s where s.isGroupBuying=1  and s.isDel=0 and s.isShow =1 order by s.id desc")
    Page<Sku> listGroupBuyingSku(Pageable pageable);
}
