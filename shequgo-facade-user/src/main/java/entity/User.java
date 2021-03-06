package entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * @Author: Colin
 * @Date: 2019/3/19 23:15
 */
@Entity
@Table(name = "user")
@Data
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseObject{
    private String openid;
    private String nickName;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 性别
     */
    private String gender;
    private String province;
    private String city;
    private String country;
    /**
     * 是否是团长 0否 1是
     */
    private Integer isRegimental =0;
    /**
     * 积分
     */
    private Integer integral = 0;
}
