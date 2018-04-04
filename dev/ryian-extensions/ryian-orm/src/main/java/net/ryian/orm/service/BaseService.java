package net.ryian.orm.service;

import com.github.pagehelper.PageHelper;
import net.ryian.commons.GenericsUtils;
import net.ryian.orm.domain.BaseEntity;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by allenwc on 15/9/8.
 */
public abstract class BaseService<K extends BaseEntity, T extends Mapper<K>> {

    @Autowired
    protected SqlSession sqlSession;

    @Autowired
    protected T mapper;

    protected Class<K> entityClass;

    public BaseService() {
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());
    }

    public PageInfo<K> queryPaged(Map<String, String> paramMap) {
        String rowsStr = paramMap.get("rows") == null ? null
                : (String) paramMap.get("rows");
        String pageStr = paramMap.get("page") == null ? null
                : (String) paramMap.get("page");

        // 第几页
        int page = 1;
        // 每页显示数量
        int rows = 10;
        try {
            page = pageStr != null ? Integer.valueOf(pageStr) : page;
            rows = rowsStr != null ? Integer.valueOf(rowsStr) : rows;
        } catch (Exception e) {
            e.printStackTrace();
        }
        PageHelper.startPage(page, rows);
        List<K> list = query(paramMap);
        return new PageInfo(list);
    }

    public List<K> query(Map<String, String> paramMap) {
        return Collections.emptyList();
    };

    public K get(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 根据ID逻辑删除对象.
     */
    public int logicRemove(Long id) {
        K o = null;
        try {
            o = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        o.setId(id);
        o.setValid(0);
        o.setUpdateDate(new Date());
        return mapper.updateByPrimaryKeySelective(o);
    }

    /**
     * 插入或更新对象
     *
     * @param o
     * @return
     */
    public Long saveOrUpdate(K o) {
        if (o.getId() == null || o.getId() == 0) {
            o.setCreateDate(new Date());
            mapper.insert(o);
        } else {
            o.setUpdateDate(new Date());
            mapper.updateByPrimaryKeySelective(o);
        }
        return o.getId();
    }

    public List<K> getAll() {
        K o = null;
        try {
            o = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return mapper.select(o);
    }


}
