package com.skoo.stock.login;

import com.skoo.common.SystemConfig;
import com.skoo.stock.login.domain.AssDomain;
import com.skoo.stock.login.domain.UserInfo;
import com.skoo.orm.service.BaseService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SSOService extends BaseService<UserInfo> {

    public List<UserInfo> queryData(String sqlText, Map<String, String> map) {
        map.put("database", SystemConfig.INSTANCE.getValue("hcimdatabasename"));
        return super.query(sqlText, map);
    }

    public List<AssDomain> getAssDomain(Map<String, String> map) {
        return super.getSqlSession().selectList("UserInfo_assocId", map);
    }
}
