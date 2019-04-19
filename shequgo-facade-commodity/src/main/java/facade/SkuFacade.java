package facade;

import entity.BaseFacade;
import entity.PageModel;
import entity.Sku;

/**
 * @Author: Colin
 * @Date: 2019/3/23 13:38
 */
public interface SkuFacade extends BaseFacade<Sku>{

    PageModel<Sku> listAll(Integer page,Integer pageSize);
    PageModel<Sku> listByCategoryId(Integer categoryId,Integer page,Integer pageSize);
    PageModel<Sku> listByIsShow(Integer isShow,Integer page,Integer pageSize);
    PageModel<Sku> listByCategoryIdAndIsShow(Integer categoryId,Integer isShow,Integer page,Integer pageSize);
    PageModel<Sku> listOrdinarySkuByCategoryId(Integer categoryId, Integer page,Integer pageSize);
    PageModel<Sku> listOrdinarySku(Integer page, Integer pageSize);
    PageModel<Sku> listGroupBuyingSku(Integer page,Integer pageSize);
}
