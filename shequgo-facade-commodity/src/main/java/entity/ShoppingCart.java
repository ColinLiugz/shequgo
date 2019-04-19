package entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: Colin
 * @Date: 2019/3/26 7:31
 */
@Entity
@Table(name = "shopping_cart")
@Data
public class ShoppingCart extends BaseObject{
    private Integer userId;
    /**
     * 团长id
     */
    private Integer regimentalInfoId;
    private Integer skuId;
    private Integer amount;
}
