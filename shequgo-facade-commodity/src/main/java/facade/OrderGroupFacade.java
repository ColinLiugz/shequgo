package facade;

import entity.BaseFacade;
import entity.OrderGroup;
import entity.PageModel;

/**
 * @Author: Colin
 * @Date: 2019/3/27 7:33
 */
public interface OrderGroupFacade extends BaseFacade<OrderGroup> {

    PageModel<OrderGroup> listByType(Integer logisticsStatus, Integer page, Integer pageSize);

    PageModel<OrderGroup> listByUserIdAndType(Integer userid, Integer logisticsStatus, Integer page,Integer pageSize);

    PageModel<OrderGroup> listByRegimentalIdAndType(Integer regimentalId, Integer logisticsStatus, Integer page,Integer pageSize);
}
