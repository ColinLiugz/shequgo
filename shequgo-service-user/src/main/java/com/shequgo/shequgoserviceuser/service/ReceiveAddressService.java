package com.shequgo.shequgoserviceuser.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.shequgo.shequgoserviceuser.repo.ReceiveAddressRepo;
import entity.BaseService;
import entity.ReceiveAddress;
import facade.ReceiveAddressFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/25 23:35
 */
@Component("receiverAddressFacade")
@Service(version = "1.0.0")
public class ReceiveAddressService extends BaseService<ReceiveAddress,ReceiveAddressRepo> implements ReceiveAddressFacade{

    @Autowired
    public ReceiveAddressService(ReceiveAddressRepo repo){
        super(repo);
    }

    @Override
    public ReceiveAddress findDefault(Integer userId){
        return repo.findDefault(userId);
    }

    @Override
    public List<ReceiveAddress> listByUser(Integer userId){
        return repo.listByUser(userId);
    }
}
