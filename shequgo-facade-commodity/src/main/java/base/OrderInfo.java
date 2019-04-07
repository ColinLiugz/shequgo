package base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author: Colin
 * @Date: 2019/3/26 23:22
 */
@Entity
@Table(name = "order_info")
@Data
public class OrderInfo extends BaseObject {
    private Integer orderGroupId;
    private Integer skuId;
    private Integer amount;
    /**
     * 单价
     */
    @Column(columnDefinition = "DECIMAL(19,2) default 0.00")
    private BigDecimal unitPrice;
    private String skuImage;
}
