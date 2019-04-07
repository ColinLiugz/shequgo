package facade;

import base.Admin;
import base.BaseFacade;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:03
 */
public interface AdminFacade extends BaseFacade<Admin>{

    Admin findByPhone(String phone);
}
