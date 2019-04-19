package utils;

import com.qiniu.util.Auth;

/**
 * @Author: Colin
 * @Date: 2019/3/18 23:15
 */
public class QiniuUtil {
    private static final String ACCESSKEY="w7xWUYVujvAkBom1UFFJF4zOe8_84nGpYvw1kT0e";
    private static final String SECRETKRY="gKvq53onLqpaWwrtyAvFzY_PTzeylDednfPCe-2J";
    private static final String BUCKET ="shequgo";

    public static String getTocken(){
        Auth auth = Auth.create(ACCESSKEY,SECRETKRY);
        String tocken = auth.uploadToken(BUCKET);
        return tocken;
    }
}
