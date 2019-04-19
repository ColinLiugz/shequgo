package facade;

import entity.BaseFacade;
import entity.ShoppingCart;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/26 7:36
 */
public interface ShoppingCartFacade extends BaseFacade<ShoppingCart>{

    ShoppingCart findByUserIdAndRegimentalAndSkuid(Integer userId, Integer regimentalInfoId,Integer skuId);

    List<ShoppingCart> listByUserAndRegimental(Integer userId, Integer regimentalInfoId);
}
