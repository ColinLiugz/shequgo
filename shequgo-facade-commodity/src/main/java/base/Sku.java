package base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author: Colin
 * @Date: 2019/3/21 22:09
 */
@Entity
@Table(name = "sku")
@Data
public class Sku extends BaseObject{
    /**
     * 类别id
     */
    private Integer categoryId;
    private String skuName;
    private String des;
    /**
     * 副标题
     */
    private String subtitle;
    /**
     * 缩略图
     */
    private String thumbnail;
    private String richText;
    /**
     * 价格
     */
    @Column(columnDefinition = "DECIMAL(19,2) default 0.00")
    private BigDecimal price;
    /**
     * 折后价
     */
    @Column(columnDefinition = "DECIMAL(19,2) default 0.00")
    private BigDecimal discountPrice;
    /**
     * 库存
     */
    private Integer amount;
    /**
     * 已售数量
     */
    private Integer soldAmount;
    /**
     * 剩余量
     */
    private Integer surplusAmount;
    /**
     * 是否是团购
     */
    private Integer isGroupBuying;
    /**
     * 需要多少人成团
     */
    private Integer needAmount;
    private Integer isShow;
}
