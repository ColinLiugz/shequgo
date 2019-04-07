package facade;

import base.BaseFacade;
import base.PageModel;
import base.RegimentalInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:06
 */
public interface RegimentalInfoFacade extends BaseFacade<RegimentalInfo>{

    RegimentalInfo findByUserId(Integer userId);

    List<RegimentalInfo> findAllNotDel();

    PageModel<RegimentalInfo> listByStatus(Integer status, Integer page, Integer pageSize);
}
