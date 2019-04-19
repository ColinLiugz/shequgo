package entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户积分表
 * @Author: Colin
 * @Date: 2019/3/21 21:57
 */
@Entity
@Table(name = "integral_record")
@Data
public class IntegralRecord extends BaseObject {
    private Integer userId;
    private Integer orderGroupId;
    /**
     * 记录类型 0购物增加 1分享增加 2使用
     */
    private Integer type;
    private Integer integral;
}
