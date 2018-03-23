package cn.hzskt.bdtg.manage.action;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.manage.domain.ComboTreeModel;
import cn.hzskt.bdtg.manage.service.CategoryService;
import cn.hzskt.bdtg.sys.domain.Dict;
import cn.hzskt.bdtg.sys.service.DictService;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;

@Controller
@RequestMapping("/manage/category-manage")
public class CategoryManageAction extends BaseMagicAction {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private DictService dictService;
	
	
	@RequestMapping("")
	public String execute(HttpServletRequest request) throws Exception {
		request.setAttribute("orgList", dictService.selectPlatform());
		return "manage/category/category-manage";
	}

	/**
	 * 栏目数Grid查询处理
	 * @throws Exception
	 */
	@JsonView
	@RequestMapping("/querTree")
	public void querTree(HttpServletResponse resp, String systemid, String id) throws Exception {
		List<Dict> items = dictService.selectPlatform();
		List list = categoryService.getCategoryTree("0", id, items);
		this.printHtml(resp, JSON.toJSONString(list));
	}


	/**
	 * 获取栏目Comboxtree
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCategoryData")
	public void getCategoryData(HttpServletResponse resp, String catOrg) throws Exception {
		List<Dict> items = dictService.selectPlatform();
		ComboTreeModel model = categoryService.getCategoryComboTree(catOrg, items);
		this.printHtml(resp, JSON.toJSONString(Arrays.asList(model)));
	}

	/**
	 * 保存单条Category记录.
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> params = this.bindMap(request);
		String id = MapUtils.getString(params, "id");
		String upId = MapUtils.getString(params, "upId", "-1");
		params.put("upId", upId);
		if(StringUtils.isNotBlank(id)){
			categoryService.editCategory(params);
		}
		else{
			categoryService.addCategory(params);
		}
		this.printHtml(response, JSON.toJSONString(messageSuccuseWrap()));
	}

	/**
	 * 删除Category记录.
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletResponse response, String id) throws Exception {
		categoryService.removeCategory(id);
		this.printHtml(response, JSON.toJSONString(messageSuccuseWrap()));
	}
	
}
