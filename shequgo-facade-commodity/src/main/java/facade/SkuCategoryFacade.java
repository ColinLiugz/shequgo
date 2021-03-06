package facade;

import entity.BaseFacade;
import entity.SkuCategory;

import java.util.List;

/**
 * @Author: Colin
 * @Date: 2019/3/23 13:39
 */
public interface SkuCategoryFacade extends BaseFacade<SkuCategory> {

    List<SkuCategory> listAllNotDel();
}
