package facade;

import entity.BaseFacade;
import entity.IntegralRecord;
import entity.PageModel;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:05
 */
public interface IntegralRecordFacade extends BaseFacade<IntegralRecord> {

    PageModel<IntegralRecord> listRecordByUseridAndType(Integer userid, Integer type, Integer page, Integer pageSize);

    PageModel<IntegralRecord> listRecord(Integer userid,Integer page,Integer pageSize);
}
