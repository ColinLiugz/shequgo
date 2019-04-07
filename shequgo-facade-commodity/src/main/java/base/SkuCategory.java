package base;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 商品类别
 * @Author: Colin
 * @Date: 2019/3/21 22:10
 */
@Entity
@Table(name = "sku_category")
@Data
public class SkuCategory extends BaseObject{
    private String categoryName;
}
