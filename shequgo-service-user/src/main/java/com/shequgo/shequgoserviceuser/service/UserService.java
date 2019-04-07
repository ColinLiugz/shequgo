package com.shequgo.shequgoserviceuser.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.shequgo.shequgoserviceuser.repo.UserRepo;
import com.shequgo.shequgoserviceuser.util.AesCbcUtil;
import base.BaseService;
import base.User;
import facade.UserFacade;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Colin
 * @Date: 2019/3/24 14:58
 */
@Component("userFacade")
@Service(version = "1.0.0")
public class UserService extends BaseService<User,UserRepo> implements UserFacade{

    @Autowired
    public UserService(UserRepo repo){
        super(repo);
    }

    public User findByOpenid(String openid){
        return repo.findByOpenid(openid);
    }

    @Override
    public User getUserInfoByCode(String weixinCode, String encryptedData, String iv){
        JSONObject weixinResult = WXAppletUserInfo.getSessionKeyOropenid(weixinCode);
        //会话秘钥
        String sessionkey  = weixinResult.get("session_key").toString();
        //用户唯一标识
        String openid = weixinResult.get("openid").toString();
        User user = findByOpenid(openid);
        if(null == user){
            user = new User();
            user.setOpenid(openid);
        }
        try{
            //拿到用户session_key和用户敏感数据进行解密，拿到用户信息。
            String decrypts= AesCbcUtil.decrypt(encryptedData,sessionkey,iv,"utf-8");
            JSONObject jsons = (JSONObject) new JSONParser().parse(decrypts);
            user.setNickName(jsons.get("nickName").toString());
            user.setAvatarUrl(jsons.get("avatarUrl").toString());
            user.setGender(jsons.get("gender").toString());
            user.setCity(jsons.get("city").toString());
            user.setProvince(jsons.get("province").toString());
            user.setCountry(jsons.get("country").toString());
            user = repo.save(user);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
