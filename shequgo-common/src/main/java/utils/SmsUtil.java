package utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Random;

/**
 * @Author: Colin
 * @Date: 2019/4/21 13:52
 */
public class SmsUtil {

    private static String REGION_ID = "cn-beijing";
    private static String  ACCESS_KEY_ID = "LTAIxwdUMVt6FWDF";
    private static String ACCESS_SECRET = "1GR8hr1pa2kRLzOHqZH1IfEBdWZetk";
    private static String CHECKCODE_TEMPLATE_CODE = "SMS_163847433";
    private static String NOTICE_TEMPLATE_CODE = "SMS_164508194";
    private static String SIGN_NAME = "社区购";

    public static String sendCheckCode(String phone){
        String checkCode = generateCheckCode();
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY_ID, ACCESS_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("TemplateCode", CHECKCODE_TEMPLATE_CODE);
        request.putQueryParameter("SignName", SIGN_NAME);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+checkCode+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return checkCode;
    }

    public static String sendNotice(String phone){
        String checkCode = generateCheckCode();
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY_ID, ACCESS_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("TemplateCode", NOTICE_TEMPLATE_CODE);
        request.putQueryParameter("SignName", "社区购通知");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return checkCode;
    }

    private static String generateCheckCode(){
        return String.valueOf((new Random().nextInt(899999) + 100000));
    }
}
