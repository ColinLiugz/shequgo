package entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * 商品类别
 * @Author: Colin
 * @Date: 2019/3/21 22:10
 */
@Entity
@Table(name = "sku_category")
@Data
@EntityListeners(AuditingEntityListener.class)
public class SkuCategory extends BaseObject{
    private String categoryName;
}
