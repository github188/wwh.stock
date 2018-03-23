package cn.hzskt.bdtg.cm.service;

import cn.hzskt.bdtg.cm.domain.InfoCategory;
import cn.hzskt.bdtg.cm.mapper.InfoCategoryMapper;
import cn.hzskt.bdtg.manage.domain.ComboTreeModel;
import cn.hzskt.bdtg.manage.domain.GridTreeModel;
import cn.hzskt.bdtg.sys.domain.Dict;
import net.ryian.orm.service.BaseService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;


/**
* @description:
* @author: autoCode
* @history:
*/
@Service
public class  InfoCategoryService extends BaseService<InfoCategory,InfoCategoryMapper> {

    /**
     * 查询栏目树集合
     * @param id 栏目节点Id
     * @return
     */
    public List<GridTreeModel> getCategoryTree(String id){
        boolean parent = (id==null || "".equals(id));
        return parent ? selectTopNode() : selectTreeNode(id);
    }

    private List<GridTreeModel> selectTopNode(){
        List<GridTreeModel> allList = new ArrayList<GridTreeModel>();
        GridTreeModel rooModel = new GridTreeModel();
        rooModel.setId(-2147483647);//ID
        rooModel.setUpId(-1);//上级ID
        rooModel.setCatName("资料管理");	//
//            rooModel.setCatOrg(Integer.parseInt(plaform.getValue()));
        rooModel.setState("closed");
        allList.add(rooModel);
        return allList;
    }

    private List<GridTreeModel> selectTreeNode( String id){
        List<InfoCategory>  infoCategoryList = selectCategoryList(id);
        return categoryToTreeNode(infoCategoryList);
    }

    private List<InfoCategory> selectCategoryList(String id){
        Map<String, Object> params = new HashMap<String, Object>();
        String kid = id;
        List<String> searchList = new ArrayList<String>();
        if(Integer.parseInt(kid) < 0) {
            kid="-1";
        }
        params.put("upId", kid);
        return this.getSqlSession().selectList("CmInfoCategory_select_treeNode", params);
    }

    private List<GridTreeModel> categoryToTreeNode(List<InfoCategory> categoryList){
        List<GridTreeModel> allList = new ArrayList<GridTreeModel>();
        for (InfoCategory category : categoryList) {
            GridTreeModel ctm = new GridTreeModel();
            ctm.setId(category.getId());
            ctm.setUpId(category.getUpId());
            ctm.setCatCode(category.getCatCode());
            ctm.setCatName(category.getCatName());
            //ctm.setCatOrg(category.getCatOrg());
            ctm.setCatOrder(category.getCatOrder());
            ctm.setIsShow(category.getIsShow());
            //ctm.setUpCatName(category.getUpCatName());

            List<InfoCategory>  children = selectCategoryList(String.valueOf(category.getId()));
            if(children!=null && children.size() > 0){
                ctm.setState("closed");
            }

            allList.add(ctm);
        }
        return allList;
    }

    private List<InfoCategory> getCategoryChildren(String pid, String orgId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("catOrgList", Arrays.asList(orgId));
        params.put("upId", pid);
        return this.getSqlSession().selectList("CmInfoCategory_select_treeNode", params);
    }

    public ComboTreeModel getCategoryComboTree(String orgId){
        ComboTreeModel root = getComboRoot();
        root.setChildren(getComboxTreeData(String.valueOf(root.getId()), orgId));
        return root;
    }

    private ComboTreeModel getComboRoot(){
        ComboTreeModel all = new ComboTreeModel();
        all.setId(-1);
        all.setText("资料管理");
        return all;
    }

    public List<ComboTreeModel> getComboxTreeData(String parentId, String orgId){
        List<ComboTreeModel> items = new ArrayList<ComboTreeModel>();
        List<InfoCategory> categories = getCategoryChildren(parentId, orgId);
        for(InfoCategory category : categories){
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
        this.getSqlSession().insert("CmInfoCategory_add", params);
    }

    /**
     * 添加栏目分类信息
     * @param params
     */
    public void editCategory(Map<String, String> params){
        this.getSqlSession().insert("CmInfoCategory_edit", params);
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
        List<InfoCategory> categories = selectCategoryList(id);
        for(InfoCategory category : categories){
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
        this.getSqlSession().delete("CmInfoCategory_remove", id);
    }

    private SqlSession getSqlSession(){
        return this.sqlSession;
    }


}
