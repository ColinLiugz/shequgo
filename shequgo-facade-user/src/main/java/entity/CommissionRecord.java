package entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 团长佣金记录(增加和提现)
 * @Author: Colin
 * @Date: 2019/3/21 22:05
 */
@Entity
@Table(name = "commission_record")
@Data
@EntityListeners(AuditingEntityListener.class)
public class CommissionRecord extends BaseObject{
    private Integer userId;
    /**
     * 记录类型 0佣金增加 1提现
     */
    private Integer type;
    @Column(columnDefinition = "DECIMAL(19,2) default 0.00")
    private BigDecimal price;
    /**
     * 0正在进行 1成功
     */
    private Integer status;
}
