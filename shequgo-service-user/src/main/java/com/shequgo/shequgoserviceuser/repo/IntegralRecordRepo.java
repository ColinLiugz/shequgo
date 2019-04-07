package com.shequgo.shequgoserviceuser.repo;

import base.IntegralRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:27
 */
public interface IntegralRecordRepo extends JpaRepository<IntegralRecord,Integer> {

    @Query("select i from IntegralRecord i where i.userId = ?1 and i.isDel = 0 and i.type = ?2 order by i.id desc")
    Page<IntegralRecord> listRecordByUseridAndType(Integer userid, Integer type, Pageable pageable);

    @Query("select i from IntegralRecord i where i.userId=?1 and i.isDel=0 order by i.id desc")
    Page<IntegralRecord> listRecord(Integer userId,Pageable pageable);
}
