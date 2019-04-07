package com.shequgo.shequgoserviceuser.service;

import base.PageModel;
import com.alibaba.dubbo.config.annotation.Service;
import com.shequgo.shequgoserviceuser.repo.IntegralRecordRepo;
import base.BaseService;
import base.IntegralRecord;
import facade.IntegralRecordFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:40
 */
@Component("integralRecordFacade")
@Service(version = "1.0.0")
public class IntegralRecordService extends BaseService<IntegralRecord,IntegralRecordRepo> implements IntegralRecordFacade {
    @Autowired
    private IntegralRecordService(IntegralRecordRepo repo){
        super(repo);
    }

    @Override
    public PageModel<IntegralRecord> listRecordByUseridAndType(Integer userid, Integer type, Integer page, Integer pageSize){
        Page<IntegralRecord> integralRecords = repo.listRecordByUseridAndType(userid,type, PageRequest.of(page-1,pageSize));
        return new PageModel<IntegralRecord>(integralRecords.getTotalElements(),integralRecords.getContent());
    }

    @Override
    public PageModel<IntegralRecord> listRecord(Integer userid,Integer page,Integer pageSize){
        Page<IntegralRecord> integralRecords = repo.listRecord(userid,PageRequest.of(page-1,pageSize));
        return new PageModel<IntegralRecord>(integralRecords.getTotalElements(),integralRecords.getContent());
    }
}
