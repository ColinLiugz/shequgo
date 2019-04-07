package com.shequgo.shequgoservicecommodity.repo;

import base.OrderGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: Colin
 * @Date: 2019/3/27 7:35
 */
public interface OrderGroupRepo extends JpaRepository<OrderGroup,Integer> {

    @Query("select o from OrderGroup o where o.logisticsStatus=?1 and o.isDel =0 order by o.id desc")
    Page<OrderGroup> listByType(Integer logisticsStatus, Pageable pageable);

    @Query("select o from OrderGroup o where o.userId = ?1 and o.logisticsStatus=?2 and o.isDel =0 order by o.id desc")
    Page<OrderGroup> listByUserIdAndType(Integer userid, Integer logisticsStatus, Pageable pageable);

    @Query("select o from OrderGroup o where o.regimentalId = ?1 and o.logisticsStatus=?2 and o.isDel =0 order by o.id desc")
    Page<OrderGroup> listByRegimentalIdAndType(Integer regimentalId, Integer logisticsStatus, Pageable pageable);
}
