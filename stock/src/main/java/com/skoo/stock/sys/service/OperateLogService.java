package com.skoo.stock.sys.service;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.login.UserSession;
import com.skoo.stock.sys.domain.OperateLog;
import com.skoo.stock.util.RequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class OperateLogService extends BaseService<OperateLog> {

    /**
     * 根据条件查询分页
     *
     * @param condition
     * @return
     */
    public PageInfo<OperateLog> queryPaged(Condition condition) {
        Assert.notNull(condition);
        Criteria c = condition.createCriteria(OperateLog.class);
        String userId = condition.get("userId");
        if (!StringUtils.isEmpty(userId))
            c.add(Restrictions.like("userId", "%" + userId + "%"));
        return super.queryPaged(condition);
    }

    public void wirteLog(HttpServletRequest request, String title,
                         String content) {
        try {
            String ip = RequestUtil.getIpAddr(request);
            UrlPathHelper helper = new UrlPathHelper();
            String uri = helper.getOriginatingRequestUri(request);
            OperateLog log = new OperateLog();
            long userId = 0;
            if (UserSession.getUserInfo() != null) {
                userId = UserSession.getUserId();
            }
            if (OperateLog.LOGIN_FAILURE_TITLE.equals(title)) {
                log.setCategory(OperateLog.LOGIN_FAILURE);
            } else if (OperateLog.LOGIN_SUCCESS_TITLE.equals(title)) {
                log.setCategory(OperateLog.LOGIN_SUCCESS);
            } else {
                log.setCategory(OperateLog.OPERATING);
            }
            log.setIp(ip);
            log.setSiteId(1l);
            log.setUserId(userId);
            log.setUrl(uri);
            log.setTitle(title);
            log.setContent(content);
            log.setCreator(String.valueOf(userId));
            saveOrUpdate(log);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
