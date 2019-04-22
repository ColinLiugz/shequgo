package entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author: Colin
 * @Date: 2019/3/26 23:22
 */
@Entity
@Table(name = "order_group")
@Data
@EntityListeners(AuditingEntityListener.class)
public class OrderGroup extends BaseObject {
    private Integer userId;
    private Integer regimentalId;
    /**
     * 是否使用积分
     */
    private Integer isUseIntegral;
    /**
     * 使用积分数量
     */
    private Integer usedIntegral;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 是否是团购订单 0不是 1是
     */
    private Integer isGroupBuying = 0;
    /**
     * 拼团状态  0未发起  1正在拼团  2拼团成功  3拼团失败
     */
    private Integer groupStatus = 0;
    /**
     * 支付状态 0未支付 1已支付 2退款审核中 3退款审核失败  4退款中 3已退款
     */
    private Integer paymentStatus = 0;
    /**
     * 物流状态 -1待支付 0未发货 1商家发货 2到达团长点 3等待自提 4团长配送中 5已签收
     */
    private Integer logisticsStatus = -1;
    /**
     * 物流类型 0送货上门 1自提
     */
    private Integer logisticsType;
    /**
     * 总金额
     */
    @Column(columnDefinition = "DECIMAL(19,2) default 0.00")
    private BigDecimal countPrice;
    /**
     * 实际支付金额
     */
    @Column(columnDefinition = "DECIMAL(19,2) default 0.00")
    private BigDecimal realPrice;
}
