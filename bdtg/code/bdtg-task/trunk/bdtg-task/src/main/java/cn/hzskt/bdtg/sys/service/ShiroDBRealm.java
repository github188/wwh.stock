package cn.hzskt.bdtg.sys.service;

import cn.hzskt.bdtg.sys.domain.Permission;
import cn.hzskt.bdtg.sys.domain.User;
import net.ryian.commons.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by allenwc on 14/11/12.
 */
public class ShiroDBRealm extends AuthorizingRealm
{

    @Autowired
    protected UserService userService;
    @Autowired
    protected PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        List<String> permissions = new ArrayList<String>();
        for(Permission permission : permissionService.getPermissionsByUser(shiroUser.id)) {
            String code = permission.getCode();
            if(!StringUtils.isEmpty(code)) {
                permissions.add(code);
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findUserByUserName(token.getUsername());
        if (user != null) {
            byte[] salt = Hex.decode(user.getSalt());
            return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getUserName(), user.getName()),
                    user.getPassword(), ByteSource.Util.bytes(salt), getName());
        } else {
            return null;
        }
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(User.HASH_ALGORITHM);
        matcher.setHashIterations(User.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    public AuthorizationInfo getAuthorizationInfo(Object principal) {
        PrincipalCollection principalCollection = new SimplePrincipalCollection(principal,getName());
        return this.getAuthorizationInfo(principalCollection);
    }

    public void clearCachedAuthorizationInfo(Object principal) {
        PrincipalCollection principalCollection = new SimplePrincipalCollection(principal,getName());
        this.clearCachedAuthorizationInfo(principalCollection);
    }



        /**
         * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
         */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Long id;
        public String userName;
        public String name;

        public ShiroUser(Long id, String userName, String name) {
            this.id = id;
            this.userName = userName;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return userName;
        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(userName);
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (userName == null) {
                if (other.userName != null) {
                    return false;
                }
            } else if (!userName.equals(other.userName)) {
                return false;
            }
            return true;
        }
    }

}
