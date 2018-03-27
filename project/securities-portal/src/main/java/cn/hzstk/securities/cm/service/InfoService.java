package cn.hzstk.securities.cm.service;

import cn.hzstk.securities.cm.domain.Info;
import cn.hzstk.securities.cm.domain.InfoCategory;
import cn.hzstk.securities.cm.mapper.InfoMapper;
import cn.hzstk.securities.task.utils.PageUtils;
import com.github.pagehelper.PageInfo;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  InfoService extends BaseService<Info,InfoMapper> {

    public List<Info> selectInfoList(Map<String, String> params){
        return this.sqlSession.selectList("CmInfo_select_all", params);
    }
    public int selectcount(Map<String, String> params){
        return this.sqlSession.selectOne("CmInfo_select_all_nums", params);
    }
    public List<InfoCategory> selectCategoryList(){
        return this.sqlSession.selectList("CmInfoCategory_selectall", "");
    }
    public void updateInfo(Map<String, String> params){
        this.getSqlSession().update("CmInfo_update", params);
    }
    private SqlSession getSqlSession(){
        return this.sqlSession;
    }
    /**
     * 我承接的任务，主要查询稿件中标的任务
     * @param maps
     * @return
     */
    public PageInfo selectInfoPage(Map<String, String> maps,int pageSize,int pageNums) {
        int page = (pageNums < 1) ? 1 : pageNums;
        int nums = (pageSize < 1) ? 10 : pageSize;
        maps.put("limit", String.valueOf((page-1) * nums));
        maps.put("size", String.valueOf(nums));
        List items = selectInfoList(maps);
        int total =selectcount(maps);
        return PageUtils.buildPageInfo(items, total, page, pageSize, 10);
    }
    public List<Info> getUploadTop(){
        return this.sqlSession.selectList("CmInfo_select_topshow");
    }
}
