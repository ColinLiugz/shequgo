package facade;

import base.BaseFacade;
import base.IntegralRecord;
import base.PageModel;
import org.springframework.data.domain.Page;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:05
 */
public interface IntegralRecordFacade extends BaseFacade<IntegralRecord> {

    PageModel<IntegralRecord> listRecordByUseridAndType(Integer userid, Integer type, Integer page, Integer pageSize);

    PageModel<IntegralRecord> listRecord(Integer userid,Integer page,Integer pageSize);
}
