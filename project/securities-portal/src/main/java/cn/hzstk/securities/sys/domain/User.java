package cn.hzstk.securities.sys.domain;

import cn.hzstk.securities.common.domain.BaseDomain;
import net.ryian.commons.DigestUtils;
import net.ryian.commons.EncodeUtils;
import net.ryian.commons.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Table(name = "sys_user")
public class User extends BaseDomain {

    private static final long serialVersionUID = 1L;

    public static final String HASH_ALGORITHM = "SHA-1"; // 使用的加密算法
    public static final int HASH_INTERATIONS = 1024; // 加密迭代次数
    private static final int SALT_SIZE = 8; // 加密盐的大小


    /**
     * 用户名
     **/
    @Column(name="user_name")
    private String userName;

    /**
     * 姓名
     **/
    private String name;

    /**
     * 密码
     **/
    private String password;

    /**
     * 盐值
     **/
    private String salt;

    @Column(name = "last_login_date")
    private Date lastLoginDate;

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void encryptUserPassword(String password) {
        if(StringUtils.isEmpty(salt)) {
            byte[] salt = DigestUtils.generateSalt(SALT_SIZE);
            this.setSalt(EncodeUtils.encodeHex(salt));
        }
        byte[] hashPassword = DigestUtils.sha1(password.getBytes(), EncodeUtils.decodeHex(salt), HASH_INTERATIONS);
        this.setPassword(EncodeUtils.encodeHex(hashPassword));
    }

}
