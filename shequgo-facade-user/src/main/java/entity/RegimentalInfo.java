package entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 团长信息表
 * @Author: Colin
 * @Date: 2019/3/21 21:45
 */
@Entity
@Table(name = "regimental_info")
@Data
@EntityListeners(AuditingEntityListener.class)
public class RegimentalInfo extends BaseObject implements Comparable<RegimentalInfo>{
    private Integer userId;
    private String realName;
    private String phone;
    private String address;
    /**
     * 经纬度, 经度#纬度
     */
    private String loccation;
    /**
     * 状态 0正在审核 1审核通过 2审核失败
     */
    private Integer status;
    /**
     * 佣金
     */
    @Column(columnDefinition = "DECIMAL(19,2) default 0.00")
    private BigDecimal commission;
    /**
     * 跟用户的 距离，数据库里不会存，比较时使用
     */
    private Double distance;

    public int compareTo(RegimentalInfo o) {
        return Double.valueOf(this.distance - o.distance).intValue();
    }
}
