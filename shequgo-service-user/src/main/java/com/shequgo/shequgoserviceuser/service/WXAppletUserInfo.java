package com.shequgo.shequgoserviceuser.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.HttpRequestUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author: Colin
 * @Date: 2019/3/24 15:07
 */
public class WXAppletUserInfo {

    private static final String WEIXINURL = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String APPID = "wx5ccee6156267291a";
    private static final String APPSECRET="97292134e70c59931bfaa253515da92b";

    /**
     * 获取微信小程序 session_key 和 openid
     *
     * @author zhy
     * @param code 调用微信登陆返回的Code
     * @return
     */
    public static JSONObject getSessionKeyOropenid(String code){
        //微信端登录code值
        String wxCode = code;
        Map<String,String> requestUrlParam = new HashMap<String,String>();
        requestUrlParam.put("appid", APPID);
        requestUrlParam.put("secret", APPSECRET);
        requestUrlParam.put("js_code", wxCode);
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
        String result = HttpRequestUtil.sendPost(WEIXINURL, requestUrlParam);
        System.out.println(result);
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
