#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.sys.domain;
        import org.apache.ibatis.type.Alias
        import net.ryian.core.domain.BaseEntity;


/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Alias("User")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 用户名
     **/
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
}
