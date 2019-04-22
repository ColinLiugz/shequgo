package com.shequgo.shequgoserviceuser.repo;

import entity.RegimentalInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:28
 */
public interface RegimentalInfoRepo extends JpaRepository<RegimentalInfo,Integer> {

    @Query("select r from RegimentalInfo r where r.userId=?1 and r.isDel=0 ")
    RegimentalInfo findByUserId(Integer userId);

    @Query("select r from RegimentalInfo r where r.isDel=0 and r.status=1 order by r.id desc ")
    List<RegimentalInfo> findAllNotDelAndAllowed();

    @Query("select r from RegimentalInfo r where r.isDel=0")
    Page<RegimentalInfo> listAll( Pageable pageable);

    @Query("select r from RegimentalInfo r where r.status=?1 and r.isDel=0")
    Page<RegimentalInfo> listByStatus(Integer status, Pageable pageable);
}
