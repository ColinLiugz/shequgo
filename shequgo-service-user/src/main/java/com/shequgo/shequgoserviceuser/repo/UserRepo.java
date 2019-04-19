package com.shequgo.shequgoserviceuser.repo;

import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: Colin
 * @Date: 2019/3/24 14:52
 */
public interface UserRepo extends JpaRepository<User,Integer> {

    @Query("select u from User u where u.openid = ?1 and u.isDel = 0")
    User findByOpenid(String openid);
}
