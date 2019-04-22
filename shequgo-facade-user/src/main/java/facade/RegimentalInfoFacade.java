package facade;

import entity.BaseFacade;
import entity.PageModel;
import entity.RegimentalInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:06
 */
public interface RegimentalInfoFacade extends BaseFacade<RegimentalInfo>{

    RegimentalInfo findByUserId(Integer userId);

    List<RegimentalInfo> findAllNotDelAndAllowed();

    PageModel<RegimentalInfo> listAll(Integer page, Integer pageSize);

    PageModel<RegimentalInfo> listByStatus(Integer status, Integer page, Integer pageSize);
}
