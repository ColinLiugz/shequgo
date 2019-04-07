package base;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: Colin
 * @Date: 2019/3/19 23:11
 */
@Entity
@Table(name = "admin")
//@Data
public class Admin extends BaseObject{
    private String name;
    private String phone;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
