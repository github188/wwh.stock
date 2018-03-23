package cn.hzskt.bdtg.manage.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ryian.orm.service.BaseService;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.hzskt.bdtg.manage.domain.Category;
import cn.hzskt.bdtg.manage.domain.ComboTreeModel;
import cn.hzskt.bdtg.manage.domain.GridTreeModel;
import cn.hzskt.bdtg.manage.mapper.CategoryMapper;
import cn.hzskt.bdtg.sys.domain.Dict;

@Service
public class CategoryService extends BaseService<Category, CategoryMapper> {

	/**
	 * 查询栏目树集合
	 * @param sysId 平台记录Id
	 * @param id 栏目节点Id
	 * @param plaformList 平台数字字典集合
	 * @return
	 */
	public List<GridTreeModel> getCategoryTree(String sysId, String id, List<Dict> plaformList){
		boolean parent = (sysId != null && "0".equals(sysId) && (id==null || "".equals(id)));
		return parent ? selectTopNode(plaformList) : selectTreeNode(sysId, id);
	}
	
	private List<GridTreeModel> selectTopNode(List<Dict> plaformList){
		List<GridTreeModel> allList = new ArrayList<GridTreeModel>();
		for(Dict plaform : plaformList) {
			GridTreeModel rooModel = new GridTreeModel();
		    rooModel.setId(-2147483647 + Integer.parseInt(plaform.getValue()));//ID
		    rooModel.setUpId(-1);//上级ID
		    rooModel.setCatName(plaform.getContent());	//
		    rooModel.setCatOrg(Integer.parseInt(plaform.getValue()));
		    rooModel.setState("closed");
	    	allList.add(rooModel);
		}
		return allList;
	}
	
	private List<GridTreeModel> selectTreeNode(String sysId, String id){
		List<Category>  categoryList = selectCategoryList(sysId, id);
		return categoryToTreeNode(categoryList, sysId);
	}
	
	private List<Category> selectCategoryList(String orgId, String id){
		Map<String, Object> params = new HashMap<String, Object>();
		String kid = id;
		List<String> searchList = new ArrayList<String>();
		if(Integer.parseInt(kid) < 0) {
			Integer platformid=Integer.parseInt(kid) + 2147483647;
			searchList.add(platformid.toString());
			kid="-1";
			params.put("catOrgList", searchList);
		}
		params.put("upId", kid);
		return this.getSqlSession().selectList("Category_select_treeNode", params);
	}
	
	private List<GridTreeModel> categoryToTreeNode(List<Category> categoryList, String sysId){
		List<GridTreeModel> allList = new ArrayList<GridTreeModel>();
		for (Category category : categoryList) {
			 GridTreeModel ctm = new GridTreeModel();
			 ctm.setId(category.getId());
	         ctm.setUpId(category.getUpId());
	         ctm.setCatCode(category.getCatCode());
	         ctm.setCatName(category.getCatName());
	         ctm.setCatOrg(category.getCatOrg());
	         ctm.setCatOrder(category.getCatOrder());
	         ctm.setIsShow(category.getIsShow());
	         ctm.setUpCatName(category.getUpCatName());
	         
	         List<Category>  children = selectCategoryList(sysId, String.valueOf(category.getId()));
	         if(children!=null && children.size() > 0){
	        	 ctm.setState("closed");
	         }
	         
	         allList.add(ctm);
		}
		return allList;
	}
	
	private List<Category> getCategoryChildren(String pid, String orgId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("catOrgList", Arrays.asList(orgId));
		params.put("upId", pid);
		return this.getSqlSession().selectList("Category_select_treeNode", params);
	}
	
	public ComboTreeModel getCategoryComboTree(String orgId, List<Dict> plaformList){
		ComboTreeModel root = getComboRoot(orgId, plaformList);
		root.setChildren(getComboxTreeData(String.valueOf(root.getId()), orgId));
		return root;
	}
	
	private ComboTreeModel getComboRoot(String orgId, List<Dict> plaformList){
		String name = "";
		for(Dict bean : plaformList) {
			if(orgId.equals(bean.getValue())) {
				name = bean.getContent();
				break;
			}
		}
		ComboTreeModel all = new ComboTreeModel();
		all.setId(-1);
 		all.setText(name);
		return all;
	}
	
	public List<ComboTreeModel> getComboxTreeData(String parentId, String orgId){
		List<ComboTreeModel> items = new ArrayList<ComboTreeModel>();
		List<Category> categories = getCategoryChildren(parentId, orgId);
		for(Category category : categories){
			ComboTreeModel model = new ComboTreeModel();
			model.setId(category.getId());
			model.setText(category.getCatName());
			model.setChildren(getComboxTreeData(String.valueOf(category.getId()), orgId));
			items.add(model);
		}
		return items;
	}
	
	/**
	 * 添加栏目分类信息
	 * @param params
	 */
	public void addCategory(Map<String, String> params){
		this.getSqlSession().insert("Category_add", params);
	}
	
	/**
	 * 添加栏目分类信息
	 * @param params
	 */
	public void editCategory(Map<String, String> params){
		this.getSqlSession().insert("Category_edit", params);
	}
	
	/**
	 * 删除栏目信息， 同时删除子栏目及相应的内容信息
	 * @param id
	 * @return
	 */
	public boolean removeCategory(String id){
		List<String> items = selectCategoryChildren(id, null);
		this.removeCategoryChildren(items);
		this.dropCategory(id);
		return true;
	}
	
	/**
	 * 通过递归的方式获取某个节点下的所有的节点
	 * @param id
	 * @param orgId
	 * @return
	 */
	private List<String> selectCategoryChildren(String id, String orgId){
		List<String> items = new ArrayList<String>();
		List<Category> categories = selectCategoryList(orgId, id);
		for(Category category : categories){
			String cid = String.valueOf(category.getId());
			items.add(cid);
			items.addAll(selectCategoryChildren(cid, orgId));
		}
		return items;
	}
	
	private void removeCategoryChildren(List<String> items){
		for(String item : items){
			dropCategory(item);
		}
	}
	
	private void dropCategory(String id){
		this.getSqlSession().delete("Category_remove", id);
	}
	
	private SqlSession getSqlSession(){
		return this.sqlSession;
	}
	
}
