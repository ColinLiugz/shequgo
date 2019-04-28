package com.shequgo.shequgoserviceuser.repo;

import entity.CommissionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:26
 */
public interface CommissionRecordRepo extends JpaRepository<CommissionRecord,Integer>{

    @Query("select c from CommissionRecord c where c.userId=?1 and c.isDel=0 order by c.id desc")
    Page<CommissionRecord> listCommissionRecord(Integer userId, Pageable pageable);

    @Query(value = "select sum(c.price) from commission_record c where c.userId=?1 and DATE_FORMAT(c.update_data, '%Y-%m')=?2",nativeQuery = true)
    BigDecimal sumComByMonth(Integer userId, String date);
}
