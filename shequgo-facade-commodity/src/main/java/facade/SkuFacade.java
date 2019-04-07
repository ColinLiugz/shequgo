package facade;

import base.BaseFacade;
import base.PageModel;
import base.Sku;
import org.springframework.data.domain.Page;

/**
 * @Author: Colin
 * @Date: 2019/3/23 13:38
 */
public interface SkuFacade extends BaseFacade<Sku>{

    PageModel<Sku> listOrdinarySkuByCategoryId(Integer categoryId, Integer page,Integer pageSize);
    PageModel<Sku> listOrdinarySku(Integer page, Integer pageSize);
    PageModel<Sku> listGroupBuyingSku(Integer page,Integer pageSize);
}
