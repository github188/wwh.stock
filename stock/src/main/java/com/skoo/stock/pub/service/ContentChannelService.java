package com.skoo.stock.pub.service;

import com.skoo.orm.service.BaseService;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.orm.service.support.query.Criteria;
import com.skoo.orm.service.support.query.Restrictions;
import com.skoo.stock.pub.domain.ContentChannel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class ContentChannelService extends BaseService<ContentChannel> {

    /**
     * 根据条件查询分页
     *
     * @param condition
     * @return
     */
    public PageInfo<ContentChannel> queryPaged(Condition condition) {
        Assert.notNull(condition);
        Criteria c = condition.createCriteria(ContentChannel.class);
        String channelId = condition.get("channelId");
        if (!StringUtils.isEmpty(channelId))
            c.add(Restrictions.like("channelId", "%" + channelId + "%"));
        return super.queryPaged(condition);
    }

    /**
     * 批量删除数据
     *
     * @param map 参数
     * @return
     */
    public int deleteChannelList(Map<String, Object> map) {
        Assert.notNull(map);
        return super.getSqlSession().insert("ContentChannel_delete_batch", map);
    }

    /**
     * 查询未登陆数据
     *
     * @param map 参数
     * @return
     */
    public List<String> selectNoInsertList(Map map) {
        Assert.notNull(map);
        return super.getSqlSession().selectList("ContentChannel_noinsert_list", map);
    }

    /**
     * 批量新增数据
     *
     * @param map 参数
     * @return
     */
    public int insertChannelList(Map<String, Object> map) {
        Assert.notNull(map);
        return super.getSqlSession().insert("ContentChannel_insert_batch", map);
    }
}
