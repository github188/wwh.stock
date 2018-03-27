package cn.hzstk.securities.manage.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.manage.domain.ComboTreeModel;
import cn.hzstk.securities.manage.domain.Content;
import cn.hzstk.securities.manage.domain.GridTreeModel;
import cn.hzstk.securities.manage.service.CategoryService;
import cn.hzstk.securities.manage.service.ContentService;
import cn.hzstk.securities.sys.domain.Dict;
import cn.hzstk.securities.sys.service.DictService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 资讯发布
 * 2016-03-10
 * @author: zhangjf
 * @history:
 */
@Controller
@RequestMapping("/manage/content-publish")
public class ContentPublishAction extends BaseMagicAction {
	
	@Autowired
	private ContentService entityService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private DictService dictService;

	@RequestMapping("")
	public String execute(HttpServletRequest request) throws Exception {
		request.setAttribute("orgList", dictService.selectPlatform());
		return "manage/content/content-publish";
	}
	
	/**
	 * 分页查询Contact列表.
	 */
	@RequestMapping("queryPaged")
	public void queryPaged(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map params = this.bindMap(request);
		Map page = entityService.selectContentPage(params);
		printJson(response, JSON.toJSONString(page));
	}

	/**
	 * 分页查询Contact列表.
	 */
	@RequestMapping("add")
	public String add(HttpServletRequest request) throws Exception {
		request.setAttribute("orgList", dictService.selectPlatform());
		return "manage/content/content-add";
	}
	
	/**
	 * 保存单条Contact记录. 保存内容表对应的栏目关联表，内容统计表
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse reponse, String[] typeCode) throws Exception {
		Map params = this.bindMap(request);
		entityService.addContent(params, typeCode);
		this.printHtml(reponse, this.messageSuccuseWrap());
	}
	
	/**
	 * 根据id获取单条Contact记录.
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, String id) throws Exception {
		request.setAttribute("orgList", dictService.selectPlatform());
		request.setAttribute("model", entityService.getContentById(id));
		request.setAttribute("relations", entityService.selectContentCategoryRelation(id));
		return "manage/content/content-add";
	}
	
	/**
	 * 保存单条Contact记录. 保存内容表对应的栏目关联表，内容统计表
	 */
	@RequestMapping("update")
	public void update(HttpServletRequest request, HttpServletResponse reponse, String[] typeCode) throws Exception {
		Map params = this.bindMap(request);
		entityService.updateContent(params, typeCode);
		this.printHtml(reponse, this.messageSuccuseWrap());
	}
	
	/**
	 * 修改状态
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/changeStatus")
	public void changeStatus(HttpServletResponse reponse, String id, String status) throws Exception {
		entityService.onshow(id, status);
		this.printHtml(reponse, this.messageSuccuseWrap());
	}

	/**
	 * 删除Contact记录.
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletResponse reponse, String id, String catOrg) throws Exception {
		entityService.deleteContent(id, catOrg);
		this.printHtml(reponse, this.messageSuccuseWrap());
	}

	/**
	 * 栏目数Grid查询处理
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/querTree")
	public void querTree(HttpServletResponse resp, String systemid, String id) throws Exception {
		List<Dict> items = dictService.selectPlatform();
		List<GridTreeModel> list = categoryService.getCategoryTree("0", id, items);
		this.printHtml(resp, JSON.toJSONString(list));
	}

	/**
	 * 根据平台编码获取平台栏目树
	 * @param response
	 * @param catOrg
	 * @throws Exception
	 */
	@RequestMapping("/plaformTree")
	public void plaformTree(HttpServletResponse response, String catOrg) throws Exception {
		List<ComboTreeModel> items = categoryService.getComboxTreeData("-1", catOrg);
		this.printHtml(response, JSON.toJSONString(items));
	}
	
}
