package com.shequgo.shequgoservicecommodity.repo;

import base.SkuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/23 14:16
 */
public interface SkuCategoryRepo extends JpaRepository<SkuCategory,Integer>{

    @Query("select s from SkuCategory s where s.isDel = 0")
    List<SkuCategory> listAllNotDel();
}
