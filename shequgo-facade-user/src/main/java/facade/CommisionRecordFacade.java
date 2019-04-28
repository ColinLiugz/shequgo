package facade;

import entity.BaseFacade;
import entity.CommissionRecord;
import entity.PageModel;

import java.math.BigDecimal;

/**
 * @Author: Colin
 * @Date: 2019/3/28 21:04
 */
public interface CommisionRecordFacade extends BaseFacade<CommissionRecord> {

    PageModel<CommissionRecord> listCommissionRecord(Integer userId, Integer page, Integer pageSize);

    BigDecimal sumComByMonth(Integer userId, String date);
}
