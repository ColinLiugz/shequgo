package base;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: Colin
 * @Date: 2019/3/19 23:15
 */
@Entity
@Table(name = "user")
@Data
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
     * 是否是团长
     */
    private Integer isRegimental;
    /**
     * 积分
     */
    private Integer integral;
}
