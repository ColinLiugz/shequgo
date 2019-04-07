package facade;

import base.BaseFacade;
import base.OrderInfo;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/27 7:33
 */
public interface OrderInfoFacade extends BaseFacade<OrderInfo> {
    List<OrderInfo> listOrderInfoByOrderGroupId(Integer orderGroupId);
}
