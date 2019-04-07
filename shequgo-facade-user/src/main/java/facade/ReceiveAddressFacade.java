package facade;

import base.BaseFacade;
import base.ReceiveAddress;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/25 23:21
 */
public interface ReceiveAddressFacade extends BaseFacade<ReceiveAddress> {

    ReceiveAddress findDefault(Integer userId);

    List<ReceiveAddress> listByUser(Integer userId);
}
