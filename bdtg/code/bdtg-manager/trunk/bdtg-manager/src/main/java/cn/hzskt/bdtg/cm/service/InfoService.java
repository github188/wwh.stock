package cn.hzskt.bdtg.cm.service;

import cn.hzskt.bdtg.cm.domain.Info;
import cn.hzskt.bdtg.cm.mapper.InfoMapper;
import cn.hzskt.bdtg.manage.domain.ComboTreeModel;
import cn.hzskt.bdtg.manage.domain.GridTreeModel;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


/**
* @description:
* @author: autoCode
* @history:
*/
@Service
public class  InfoService extends BaseService<Info,InfoMapper> {

    public Map<String, Object> selectContentPage(Map<String, Object> params){

        int page = MapUtils.getIntValue(params, "page", 1);
        int rows = MapUtils.getIntValue(params, "rows", 1);
        if(page < 1) page = 1;
        if(rows < 1) rows = 1;
        params.put("limit", (page - 1) * rows);
        params.put("size", rows);


        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("rows", selectPageList(params));
        maps.put("total", selectPageNum(params));
        return maps;
    }

    private List<Info> selectPageList(Map<String, Object> params){
        return this.sqlSession.selectList("CmInfo_select_all", params);
    }

    private int selectPageNum(Map<String, Object> params){
        return this.sqlSession.selectOne("CmInfo_select_all_nums", params);
    }
    public List<Info> selectNameList(Map<String, Object> params){
        return this.sqlSession.selectList("CmInfo_SelectNameList", params);
    }
    /**
     * 增加
     * @param params
     */
    public void insertInfo(Map<String, Object> params){
        this.getSqlSession().insert("CmInfo_insert", params);
    }

    public void updateInfo(Map<String, Object> params){
        this.getSqlSession().update("CmInfo_update", params);
    }
    public void deleteContent(String id){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        this.getSqlSession().delete("CmInfo_remove", params);	//删除信息和栏目关联
    }
    private SqlSession getSqlSession(){
        return this.sqlSession;
    }
}
