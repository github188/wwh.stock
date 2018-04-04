#set($symbol_pound='#')
        #set($symbol_dollar='$')
        #set($symbol_escape='\' )
        package ${package}.sys.service;

        import net.ryian.commons.CollectionUtils;
        import net.ryian.commons.DigestUtils;
        import net.ryian.commons.EncodeUtils;
        import net.ryian.core.SystemConfig;
        import net.ryian.core.domain.BaseEntity;
        import net.ryian.core.service.BaseService;
        import net.ryian.core.service.support.paging.PageInfo;
        import net.ryian.core.service.support.query.Condition;
        import net.ryian.core.service.support.query.Criteria;
        import net.ryian.core.service.support.query.Restrictions;
        import ${package}.sys.domain.User;
        import org.apache.commons.lang.StringUtils;
        import org.springframework.stereotype.Component;
        import org.springframework.util.Assert;

        import java.util.List;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class UserService extends BaseService<User> {


    public static final String HASH_ALGORITHM = "SHA-1"; // 使用的加密算法
    public static final int HASH_INTERATIONS = 1024; // 加密迭代次数
    private static final int SALT_SIZE = 8; // 加密盐的大小

    /**
     * 根据条件查询分页
     *
     * @param condition
     * @return
     */
    public PageInfo<User> queryPaged(Condition condition) {
        Assert.notNull(condition);
        Criteria c = condition.createCriteria(User.class);
        String userName = condition.get("userName");
        if (!StringUtils.isEmpty(userName))
            c.add(
                    Restrictions.like("userName", "%" + userName
                            + "%"));
        return super.queryPaged(condition);
    }

    public User findUserByUserName(String userName) {
        Condition condition = new Condition();
        condition.createCriteria(User.class).add(Restrictions.eq("userName", userName));
        List<User> users = this.query(condition);
        if (!CollectionUtils.isEmpty(users) && users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Long saveOrUpdate(BaseEntity entity) {
        User user = (User) entity;
        if (user.getId() == null) {
            user.setPassword(SystemConfig.INSTANCE.getValue("DEFAULT_PASSWORD"));
            user = encryptUserPassword(user);
        }
        return super.saveOrUpdate(user);
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    public static User encryptUserPassword(User user) {
        byte[] salt = DigestUtils.generateSalt(SALT_SIZE);
        user.setSalt(EncodeUtils.encodeHex(salt));

        byte[] hashPassword = DigestUtils.sha1(user.getPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(EncodeUtils.encodeHex(hashPassword));
        return user;
    }


}
