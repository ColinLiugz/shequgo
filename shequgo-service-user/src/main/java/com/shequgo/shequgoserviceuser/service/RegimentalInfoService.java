package com.shequgo.shequgoserviceuser.service;

import entity.PageModel;
import com.alibaba.dubbo.config.annotation.Service;
import com.shequgo.shequgoserviceuser.repo.RegimentalInfoRepo;
import entity.BaseService;
import entity.RegimentalInfo;
import facade.RegimentalInfoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:45
 */
@Component("regimentalInfoFacade")
@Service(version = "1.0.0")
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
    public List<RegimentalInfo> findAllNotDelAndAllowed(){
        return repo.findAllNotDelAndAllowed();
    }

    @Override
    public PageModel<RegimentalInfo> listAll(Integer page, Integer pageSize){
        Page<RegimentalInfo> regimentalInfos = repo.listAll(PageRequest.of(page-1,pageSize));
        return new PageModel<RegimentalInfo>(regimentalInfos.getTotalElements(),regimentalInfos.getContent());
    }

    @Override
    public PageModel<RegimentalInfo> listByStatus(Integer status, Integer page, Integer pageSize){
        Page<RegimentalInfo> regimentalInfos = repo.listByStatus(status, PageRequest.of(page-1,pageSize));
        return new PageModel<RegimentalInfo>(regimentalInfos.getTotalElements(),regimentalInfos.getContent());
    }
}
