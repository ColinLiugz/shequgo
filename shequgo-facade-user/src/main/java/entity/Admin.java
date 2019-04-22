package entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * @Author: Colin
 * @Date: 2019/3/19 23:11
 */
@Entity
@Table(name = "admin")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Admin extends BaseObject{
    private String name;
    private String phone;
    private String password;
}
