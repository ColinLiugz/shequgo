package facade;

import entity.BaseFacade;
import entity.OrderGroup;
import entity.PageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/27 7:33
 */
public interface OrderGroupFacade extends BaseFacade<OrderGroup> {

    PageModel<OrderGroup> listByUserId(Integer userid, Integer page,Integer pageSize);

    PageModel<OrderGroup> listByUserIdAndTypes(Integer userid, List<Integer> logisticsStatus, Integer page,Integer pageSize);

    PageModel<OrderGroup> listByUserIdAndType(Integer userid, Integer logisticsStatus, Integer page,Integer pageSize);

    PageModel<OrderGroup> listAll(Integer page,Integer pageSize);

    PageModel<OrderGroup> listByType(Integer logisticsStatus, Integer page, Integer pageSize);

    PageModel<OrderGroup> listByTypes(List<Integer> logisticsStatus, Integer page,Integer pageSize);

    PageModel<OrderGroup> listByRegimentalId(Integer regimentalId, Integer page,Integer pageSize);

    PageModel<OrderGroup> listByRegimentalIdAndTypes(Integer regimentalId, List<Integer> logisticsStatus, Integer page, Integer pageSize);

    PageModel<OrderGroup> listByRegimentalIdAndType(Integer regimentalId, Integer logisticsStatus, Integer page,Integer pageSize);
}
