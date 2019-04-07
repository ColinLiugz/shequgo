package facade;

import base.BaseFacade;
import base.CommissionRecord;
import base.PageModel;
import org.springframework.data.domain.Page;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:04
 */
public interface CommisionRecordFacade extends BaseFacade<CommissionRecord> {

    PageModel<CommissionRecord> listCommissionRecord(Integer userId, Integer page, Integer pageSize);
}
