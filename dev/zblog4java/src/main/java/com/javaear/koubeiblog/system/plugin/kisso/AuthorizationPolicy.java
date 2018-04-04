package com.javaear.koubeiblog.system.plugin.kisso;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.SSOAuthorization;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.Token;
import com.javaear.koubeiblog.system.entity.model.PermissionModel;
import com.javaear.koubeiblog.system.entity.model.RoleModel;
import com.javaear.koubeiblog.system.entity.model.UserModel;
import com.javaear.koubeiblog.system.plugin.cache.EhCacheContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author aooer
 */
@Component
public class AuthorizationPolicy implements SSOAuthorization {

    @Autowired
    private EhCacheContext cacheContext;

    /**
     * 开启配置 sso.permission.uri=true 支持、先验证 url 地址，后验证注解。
     */
    public boolean isPermitted(Token token, String permission) {
        if (token instanceof SSOToken && !StringUtils.isEmpty(((SSOToken) token).getData())) {
            //获取登陆用户的model
            UserModel userModel = JSON.parseObject(((SSOToken) token).getData(), UserModel.class);
            //获取登录用户的role
            RoleModel roleModel = cacheContext.getCacheDTO().getRoleModelList().stream()
                    .filter(m -> Objects.equals(m.getId(), userModel.getRole())).findFirst().get();
            if (roleModel != null) {
                if (!StringUtils.isEmpty(permission) && org.apache.commons.lang.StringUtils.isNumeric(permission)) {
                    //判断登录用户的role是否包含此permission
                    return roleModel.getPermissionList().contains(Integer.parseInt(permission));
                } else {
                    //查询uri对应的permission是否存在
                    PermissionModel permissionModel = cacheContext.getCacheDTO().getPermissionModelList().stream()
                            .filter(m -> !StringUtils.isEmpty(m.getUrl()) && permission.substring(0, permission.lastIndexOf(".")).endsWith(m.getUrl())).findFirst().orElse(null);
                    if (permissionModel != null)
                        return roleModel.getPermissionList().contains(permissionModel.getId());
                }
            }
        }
        return false;
    }
}
