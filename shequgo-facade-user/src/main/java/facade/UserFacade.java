package facade;

import entity.BaseFacade;
import entity.User;

/**
 * @Author: Colin
 * @Date: 2019/3/23 16:22
 */
public interface UserFacade extends BaseFacade<User> {
    User getUserInfoByCode(String weixinCode,  String encryptedData, String iv);
}
