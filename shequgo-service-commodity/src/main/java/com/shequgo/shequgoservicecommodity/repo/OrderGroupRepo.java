package com.shequgo.shequgoservicecommodity.repo;

import entity.OrderGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/27 7:35
 */
public interface OrderGroupRepo extends JpaRepository<OrderGroup,Integer> {

    @Query("select o from OrderGroup o where o.userId = ?1 and o.isDel =0 order by o.id desc")
    Page<OrderGroup> listByUserId(Integer userid, Pageable pageable);

    @Query("select o from OrderGroup o where o.userId = ?1 and o.logisticsStatus in ?2 and o.isDel =0 order by o.id desc")
    Page<OrderGroup> listByUserIdAndTypes(Integer userid, List<Integer> logisticsStatus, Pageable pageable);

    @Query("select o from OrderGroup o where o.userId = ?1 and o.logisticsStatus=?2 and o.isDel =0 order by o.id desc")
    Page<OrderGroup> listByUserIdAndType(Integer userid, Integer logisticsStatus, Pageable pageable);

    @Query("select o from OrderGroup o where o.isDel =0 order by o.id desc")
    Page<OrderGroup> listAll(Pageable pageable);

    @Query("select o from OrderGroup o where o.logisticsStatus in ?1 and o.isDel =0 order by o.id desc")
    Page<OrderGroup> listByTypes(List<Integer> logisticsStatus, Pageable pageable);

    @Query("select o from OrderGroup o where o.logisticsStatus=?1 and o.isDel =0 order by o.id desc")
    Page<OrderGroup> listByType(Integer logisticsStatus, Pageable pageable);

    @Query("select o from OrderGroup o where o.regimentalId = ?1 and o.isDel =0 order by o.id desc")
    Page<OrderGroup> listByRegimentalId(Integer regimentalId, Pageable pageable);

    @Query("select o from OrderGroup o where o.regimentalId = ?1 and o.logisticsStatus in ?2 and o.isDel =0 order by o.id desc")
    Page<OrderGroup> listByRegimentalIdAndTypes(Integer regimentalId, List<Integer> logisticsStatus, Pageable pageable);

    @Query("select o from OrderGroup o where o.regimentalId = ?1 and o.logisticsStatus=?2 and o.isDel =0 order by o.id desc")
    Page<OrderGroup> listByRegimentalIdAndType(Integer regimentalId, Integer logisticsStatus, Pageable pageable);
}
