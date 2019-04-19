package com.shequgo.shequgoserviceuser.repo;

import entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:20
 */
@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer>{
    @Query("select a from Admin a where a.phone =?1")
    Admin findByPhone(String phone);
}
