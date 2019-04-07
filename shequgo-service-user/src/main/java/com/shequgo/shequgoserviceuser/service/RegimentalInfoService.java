package com.shequgo.shequgoserviceuser.service;

import base.PageModel;
import com.shequgo.shequgoserviceuser.repo.RegimentalInfoRepo;
import base.BaseService;
import base.RegimentalInfo;
import facade.RegimentalInfoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:45
 */
public class RegimentalInfoService extends BaseService<RegimentalInfo,RegimentalInfoRepo> implements RegimentalInfoFacade{
    @Autowired
    private RegimentalInfoService(RegimentalInfoRepo repo){
        super(repo);
    }

    @Override
    public RegimentalInfo findByUserId(Integer userId){
        return repo.findByUserId(userId);
    }

    @Override
    public List<RegimentalInfo> findAllNotDel(){
        return repo.findAllNotDel();
    }

    @Override
    public PageModel<RegimentalInfo> listByStatus(Integer status, Integer page, Integer pageSize){
        Page<RegimentalInfo> regimentalInfos = repo.listByStatus(status, PageRequest.of(page-1,pageSize));
        return new PageModel<RegimentalInfo>(regimentalInfos.getTotalElements(),regimentalInfos.getContent());
    }
}
