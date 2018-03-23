package cn.hzskt.bdtg.manage.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ryian.orm.service.BaseService;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hzskt.bdtg.manage.domain.Content;
import cn.hzskt.bdtg.manage.mapper.ContentMapper;

import com.github.pagehelper.PageInfo;


/**
 * 资讯相关服务
 * @author: autoCode
 * @history:
 */
@Service
public class  ContentService extends BaseService<Content, ContentMapper> {
	
	public Map<String, Object> selectContentPage(Map<String, Object> params){
		String typeCode = MapUtils.getString(params, "typeCode", "");
		if("-1".equals(typeCode)){
			params.remove("typeCode");
			String org = MapUtils.getString(params, "catOrg", "");
			params.put("catOrgList", Arrays.asList(org));
		}
		
		int page = MapUtils.getIntValue(params, "page", 1);
		int rows = MapUtils.getIntValue(params, "rows", 1);
		if(page < 1) page = 1;
		if(rows < 1) rows = 1;
		params.put("limit", (page-1)*rows);
		params.put("size", rows);
		
		
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("rows", selectPageList(params));
		maps.put("total", selectPageNum(params));
		return maps;
	}
	
	private List<Content> selectPageList(Map<String, Object> params){
		return this.sqlSession.selectList("Content_select_all", params);
	}
	
	private int selectPageNum(Map<String, Object> params){
		return this.sqlSession.selectOne("Content_select_all_nums", params);
	}

	public Content getContentById(String id){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("limit", 0);
		params.put("size", 1);
		List<Content> items = getSqlSession().selectList("Content_select_all", params);
		return (items!=null && items.size()>0) ? items.get(0) : new Content();
	}
	
	public List<Map<String, String>> selectContentCategoryRelation(String id){
		return this.getSqlSession().selectList("Content_select_relationMap", id);
	}
	
	/**
	 * 文章上架操作
	 * @param id
	 */
	public void onshow(String id, String status){
		boolean on = "1".equals(status);	//判断是否是上架操作
		Date now = new Date(); 
		this.changestatus(id, status, (on ? now : null), now);
	}
	
	private void changestatus(String id, String status, Date shelftime, Date updateTime){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("status", status);
		params.put("shelfDate", shelftime);
		params.put("updateDate", updateTime);
		System.out.println(params);
		this.getSqlSession().update("Content_change_status", params);
	}
	
	/**
	 * 内容信息删除操作
	 * 先删除关联，如果没有关联了则删除统计信息，最后删除内容信息
	 * @param id
	 * @param catOrg
	 */
	public void deleteContent(String id, String catOrg){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", catOrg);
		params.put("id", id);
		this.getSqlSession().delete("Content_remove_relation", params);	//删除信息和栏目关联
		int nums = this.getSqlSession().selectOne("Content_count_relation", params);	//查询是否还存在关联
		if(nums == 0){
			this.getSqlSession().delete("Content_remove_count", params);	//删除内容阅览统计信息
			this.getSqlSession().delete("Content_remove", params);	//删除内容信息
		}
	}
	
	
	/**
	 * 信息内容增加
	 * @param params
	 * @param catOrg 关联栏目列表
	 */
	public void addContent(Map<String, Object> params, String[] typeCodes){
		
		this.getSqlSession().insert("Content_insert", params);
		
		String id = MapUtils.getString(params, "id");
		String baseviews = MapUtils.getString(params, "baseViews", "0");
		String status = MapUtils.getString(params, "status", "0");
		
		this.saveContentCount(id, baseviews);
		this.saveContentCategory(id, typeCodes);
		this.onshow(id, status);
	}
	
	public void updateContent(Map<String, Object> params, String[] typeCodes){
		
		this.getSqlSession().update("Content_update", params);
		
		String id = MapUtils.getString(params, "id");
		//String baseviews = MapUtils.getString(params, "baseViews", "0");
		String status = MapUtils.getString(params, "status", "0");
		
		//this.saveContentCount(id, baseviews);
		
		this.saveContentCategory(id, typeCodes);
		this.onshow(id, status);
	}
	
	
	private void saveContentCount(String id, String baseview){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("base_views", baseview);
		this.getSqlSession().insert("Content_count_add", params);
	}
	
	private void saveContentCategory(String id, String[] typeCodes){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		this.getSqlSession().delete("Content_remove_relation", map);	//删除信息和栏目关联
		
		for(String code : typeCodes){
			if(StringUtils.isNotEmpty(code)){
				String org = this.getSqlSession().selectOne("Category_selectOrg", code);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("plaform", org);
				params.put("category", code);
				params.put("content", id);
				this.getSqlSession().insert("Content_category_add", params);
			}
		}

	}
	
	private SqlSession getSqlSession(){
		return this.sqlSession;
	}
	
}
