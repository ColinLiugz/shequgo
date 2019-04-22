package entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * 用户收货地址表
 * @Author: Colin
 * @Date: 2019/3/21 21:53
 */
@Entity
@Table(name = "receive_address")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ReceiveAddress extends BaseObject {
    private Integer userId;
    private String receiveName;
    private String receivePhone;
    private String address;
    private Integer isDefault;
}
