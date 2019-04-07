package utils;

import com.qiniu.util.Auth;

/**
 * @Author: Colin
 * @Date: 2019/3/18 23:15
 */
public class QiniuUtil {
    private static final String ACCESSKEY="";
    private static final String SECRETKRY="";
    private static final String BUCKET ="shequgo";

    public String getTocken(){
        Auth auth = Auth.create(ACCESSKEY,SECRETKRY);
        String tocken = auth.uploadToken(BUCKET);
        return tocken;




















    }
}
