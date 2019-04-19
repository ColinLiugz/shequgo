package com.shequgo.shequgoserviceuser.repo;

import entity.ReceiveAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/25 23:22
 */
public interface ReceiveAddressRepo extends JpaRepository<ReceiveAddress,Integer>{

    @Query("select r from ReceiveAddress r  where r.userId = ?1 and r.isDefault = 1")
    ReceiveAddress findDefault(Integer userId);

    @Query("select r from ReceiveAddress r where r.userId = ?1 order by r.isDefault desc,r.updateData asc ")
    List<ReceiveAddress>  listByUser(Integer userId);
}
