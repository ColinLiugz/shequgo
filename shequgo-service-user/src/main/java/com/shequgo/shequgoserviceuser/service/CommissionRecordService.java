package com.shequgo.shequgoserviceuser.service;

import entity.PageModel;
import com.alibaba.dubbo.config.annotation.Service;
import com.shequgo.shequgoserviceuser.repo.CommissionRecordRepo;
import entity.BaseService;
import entity.CommissionRecord;
import facade.CommisionRecordFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:34
 */
@Component("commissionRecordFacade")
@Service(version = "1.0.0")
public class CommissionRecordService extends BaseService<CommissionRecord,CommissionRecordRepo> implements CommisionRecordFacade {
    @Autowired
    private CommissionRecordService(CommissionRecordRepo repo){
        super(repo);
    }

    @Override
    public PageModel<CommissionRecord> listCommissionRecord(Integer userId, Integer page, Integer pageSize){
        Page<CommissionRecord> commissionRecords = repo.listCommissionRecord(userId, PageRequest.of(page-1,pageSize));
        return new PageModel<CommissionRecord>(commissionRecords.getTotalElements(),commissionRecords.getContent());
    }
}
