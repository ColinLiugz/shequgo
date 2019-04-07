package com.shequgo.shequgoservicecommodity.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.shequgo.shequgoservicecommodity.repo.OrderInfoRepo;
import base.BaseService;
import base.OrderInfo;
import facade.OrderInfoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/27 7:36
 */
@Component("orderInfoFacade")
@Service(version = "1.0.0")
public class OrderInfoService extends BaseService<OrderInfo,OrderInfoRepo> implements OrderInfoFacade {
    @Autowired
    public OrderInfoService(OrderInfoRepo repo){
        super(repo);
    }

    @Override
    public List<OrderInfo> listOrderInfoByOrderGroupId(Integer orderGroupId){
        return repo.listOrderInfoByOrderGroupId(orderGroupId);
    }

}
