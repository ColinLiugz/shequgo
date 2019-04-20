package com.shequgo.shequgoserviceuser.repo;

import entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:20
 */
@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer>{
    @Query("select a from Admin a where a.phone =?1 and a.isDel=0")
    Admin findByPhone(String phone);

    @Query("select a from Admin a where a.isDel =0 and a.id <> ?1 order by a.id")
    List<Admin> listOthersAdimn(Integer thisAdminId);
}
