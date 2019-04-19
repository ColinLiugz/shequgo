package com.shequgo.shequgoserviceuser.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.shequgo.shequgoserviceuser.repo.AdminRepo;
import entity.Admin;
import entity.BaseService;
import facade.AdminFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:32
 */
@Component("adminFacade")
@Service(version = "1.0.0")
public class AdminService extends BaseService<Admin,AdminRepo> implements AdminFacade{
    @Autowired
    public AdminService(AdminRepo repo){
        super(repo);
    }

    @Override
    public Admin findByPhone(String phone){
        return repo.findByPhone(phone);
    }
}
