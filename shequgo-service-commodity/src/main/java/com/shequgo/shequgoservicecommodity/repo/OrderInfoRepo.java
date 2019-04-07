package com.shequgo.shequgoservicecommodity.repo;

import base.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/27 7:34
 */
public interface OrderInfoRepo extends JpaRepository<OrderInfo,Integer> {

    @Query("select o from OrderInfo o where o.orderGroupId=?1 and o.isDel=0")
    List<OrderInfo> listOrderInfoByOrderGroupId(Integer orderGroupId);
}
