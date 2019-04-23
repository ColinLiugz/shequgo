package com.shequgo.shequgoservicecommodity.service;

import entity.PageModel;
import com.alibaba.dubbo.config.annotation.Service;
import com.shequgo.shequgoservicecommodity.repo.OrderGroupRepo;
import entity.BaseService;
import entity.OrderGroup;
import facade.OrderGroupFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * @Author: Colin
 * @Date: 2019/3/27 7:39
 */
@Component("orderGroupFacade")
@Service(version = "1.0.0")
public class OrderGroupService extends BaseService<OrderGroup,OrderGroupRepo> implements OrderGroupFacade {
    @Autowired
    public OrderGroupService(OrderGroupRepo repo){
        super(repo);
    }

    @Override
    public PageModel<OrderGroup> listByType(Integer logisticsStatus, Integer page,Integer pageSize){
        Page<OrderGroup> orderGroups = repo.listByType(logisticsStatus, PageRequest.of(page-1,pageSize));
        return new PageModel<OrderGroup>(orderGroups.getTotalElements(),orderGroups.getContent());
    }

    @Override
    public PageModel<OrderGroup> listByUserId(Integer userid, Integer page,Integer pageSize){
        Page<OrderGroup> orderGroups = repo.listByUserId(userid,PageRequest.of(page-1,pageSize));
        return new PageModel<OrderGroup>(orderGroups.getTotalElements(),orderGroups.getContent());
    }

    @Override
    public PageModel<OrderGroup> listByUserIdAndTypes(Integer userid, String logisticsStatus, Integer page,Integer pageSize){
        Page<OrderGroup> orderGroups = repo.listByUserIdAndTypes(userid,logisticsStatus,PageRequest.of(page-1,pageSize));
        return new PageModel<OrderGroup>(orderGroups.getTotalElements(),orderGroups.getContent());
    }

    @Override
    public PageModel<OrderGroup> listByUserIdAndType(Integer userid, Integer logisticsStatus, Integer page,Integer pageSize){
        Page<OrderGroup> orderGroups = repo.listByUserIdAndType(userid,logisticsStatus,PageRequest.of(page-1,pageSize));
        return new PageModel<OrderGroup>(orderGroups.getTotalElements(),orderGroups.getContent());
    }

    @Override
    public PageModel<OrderGroup> listAll(Integer page,Integer pageSize){
        Page<OrderGroup> orderGroups = repo.listAll(PageRequest.of(page-1,pageSize));
        return new PageModel<OrderGroup>(orderGroups.getTotalElements(),orderGroups.getContent());
    }

    @Override
    public PageModel<OrderGroup> listByTypes(String logisticsStatus, Integer page,Integer pageSize){
        Page<OrderGroup> orderGroups = repo.listByTypes(logisticsStatus,PageRequest.of(page-1,pageSize));
        return new PageModel<OrderGroup>(orderGroups.getTotalElements(),orderGroups.getContent());
    }

    @Override
    public PageModel<OrderGroup> listByRegimentalId(Integer regimentalId, Integer page,Integer pageSize){
        Page<OrderGroup> orderGroups = repo.listByRegimentalId(regimentalId,PageRequest.of(page-1,pageSize));
        return new PageModel<>(orderGroups.getTotalElements(),orderGroups.getContent());
    }

    @Override
    public PageModel<OrderGroup> listByRegimentalIdAndTypes(Integer regimentalId, String logisticsStatus, Integer page,Integer pageSize){
        Page<OrderGroup> orderGroups = repo.listByRegimentalIdAndTypes(regimentalId,logisticsStatus,PageRequest.of(page-1,pageSize));
        return new PageModel<>(orderGroups.getTotalElements(),orderGroups.getContent());
    }

    @Override
    public PageModel<OrderGroup> listByRegimentalIdAndType(Integer regimentalId, Integer logisticsStatus, Integer page,Integer pageSize){
        Page<OrderGroup> orderGroups = repo.listByRegimentalIdAndType(regimentalId,logisticsStatus,PageRequest.of(page-1,pageSize));
        return new PageModel<>(orderGroups.getTotalElements(),orderGroups.getContent());
    }
}
