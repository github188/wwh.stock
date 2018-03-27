package com.skoo.stock.pub.action;

import com.skoo.common.SystemConfig;
import com.skoo.stock.common.Constant;
import com.skoo.stock.common.action.ManAction;
import com.skoo.stock.login.UserSession;
import com.skoo.stock.pub.domain.*;
import com.skoo.stock.pub.service.ChannelService;
import com.skoo.stock.pub.service.ContentService;
import com.skoo.stock.sys.domain.OperateLog;
import com.skoo.stock.sys.utils.json.JsonUtils;
import com.skoo.orm.service.support.paging.PageInfo;
import com.skoo.orm.service.support.query.Condition;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 
 * @description:Content action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/pub/content")
public class ContentAction extends ManAction<Content, ContentService> {

	@Autowired
	private ChannelService channelService;

	@RequestMapping(value = "queryPaged")
	public void queryPaged(HttpServletRequest request,
						   HttpServletResponse response) {
		try {
			Condition condition = bindCondition(request);
			condition.put("siteId", String.valueOf(UserSession.getSiteId()));
			PageInfo<ContentCust> page = entityService.queryPageCust(condition);
			printJson(response, JsonUtils.bean2Json(page, null, "yyyy-MM-dd HH:mm", ContentCust.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 栏目缓存部分
	 */
	private static List<ContentTreeNode> chn_tree = null;

	/**
	 * 栏目缓存部分
	 */
	private static List<ContentTreeNode> sub_chn_tree = null;

	@RequestMapping(value = "/tree")
	public String tree() {
		return getNameSpace() + "tree";
	}

	/**
	 * 栏目树构建
	 */
	@RequestMapping(value = "/treeinfo")
	public void treeinfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (UserSession.getSiteId() == -1) {
				if (chn_tree == null) {
					concatTree();
				} else {
					Integer cnt = channelService.getSqlSession().selectOne(
							"channel_cache_info");
					if (cnt == 1) {
						concatTree();
						channelService.getSqlSession().update(
								"channel_refresh_info");
					}
				}
				printJson(response, JsonUtils.bean2JsonArray(chn_tree));
			} else {
				if (sub_chn_tree == null) {
					subConcatTree();
				} else {
					Integer cnt = channelService.getSqlSession().selectOne(
							"channel_cache_info");
					if (cnt == 1) {
						subConcatTree();
						channelService.getSqlSession().update(
								"channel_refresh_info");
					}
				}
				printJson(response, JsonUtils.bean2JsonArray(sub_chn_tree));
			}

		} catch (Exception e) {
			logger.error("栏目数据获取失败", e);
		}
	}

	/**
	 * 内容编辑
	 */
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		if (id != null) {
			Content content = entityService.get(id);
			model.addAttribute("model", content);
			String contentId = String.valueOf(id);
			model.addAttribute("modelExt", entityService.getExt(contentId));
			model.addAttribute("modelTxt", entityService.getTxt(contentId));

			String channelId = String.valueOf(content.getChannelId());
			List<String> list = entityService.getChn(contentId);

			// 排除主栏目
			for(int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(channelId)) {
					list.remove(i);
					break;
				}
			}
			String chns = StringUtils.join(list.toArray(), ",");
			model.addAttribute("modelChn", chns);
		}
		return getNameSpace() + "edit";
	}

	/**
	 * 内容编辑
	 */
	@RequestMapping(value = "/add")
	public String add() {
		return getNameSpace() + "edit";
	}


	/**
	 * 下载类型元素新增
	 */
	@RequestMapping(value = "/loadAdd")
	public String loadAdd() {
		return getNameSpace() + "loadAdd";
	}

	/**
	 * 下载类型元素编辑
	 */
	@RequestMapping(value = "/loadEdit/{id}")
	public String loadEdit(@PathVariable("id") Integer id, Model model) {
		if (id != null)
			model.addAttribute("model", entityService.get(id));
		return getNameSpace() + "loadEdit";
	}

	/**
	 * 构建栏目树（商务厅）
	 */
	private void concatTree() {
		String swtRootChannel = SystemConfig.INSTANCE.getValue("swt_root_channel");
		if (StringUtils.isEmpty(swtRootChannel)) {
			swtRootChannel = "-1";
		}

		chn_tree = new ArrayList<ContentTreeNode>();
		ContentTreeNode root = new ContentTreeNode();
		root.setText("商务厅网站栏目");
		root.setId(Long.parseLong(swtRootChannel));
		root.setChildren(getTreeData(swtRootChannel));
		chn_tree.add(root);
	}

	/**
	 * 构建栏目树（地市局）
	 */
	private void subConcatTree() {
		String cityRootChannel = SystemConfig.INSTANCE.getValue("city_root_channel");
		if (StringUtils.isEmpty(cityRootChannel)) {
			cityRootChannel = "254152";
		}
		sub_chn_tree = new ArrayList<ContentTreeNode>();
		ContentTreeNode root = new ContentTreeNode();
		root.setText("地市子站栏目");
		root.setId(Long.parseLong(cityRootChannel));
		root.setChildren(getTreeData(cityRootChannel));
		sub_chn_tree.add(root);
	}

	/**
	 * 栏目列表Grid数据取得
	 * 
	 * @param pid
	 *            上层ID
	 * @return 组织列表所属结果
	 * 
	 * @throws Exception
	 */
	private List<ContentTreeNode> getTreeData(String pid) {
		List<ContentTreeNode> list = new ArrayList<ContentTreeNode>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("upId", pid);

		String swtRootChannel = SystemConfig.INSTANCE.getValue("swt_root_channel");
		if (pid.equals(swtRootChannel)) {
			String cityRootChannel = SystemConfig.INSTANCE.getValue("city_root_channel");
			map.put("excludeChannel", cityRootChannel);
		}
		List<Channel> channelList = channelService.getSqlSession()
				.selectList("Channel_tree_list", map);
		for (Channel channel : channelList) {
			ContentTreeNode node = new ContentTreeNode();
			node.setId(channel.getId());
			node.setText(channel.getChnName());
			node.setAttributes(channel.getChnOrg());
			List<ContentTreeNode> childrenTree = getTreeData(channel.getId()
					.toString());
			if (childrenTree.size() > 0 && childrenTree != null) {
				node.setChildren(childrenTree);
				node.setState("closed");
			} else {
				node.setChildren(new ArrayList<ContentTreeNode>());
			}
			list.add(node);
		}
		return list;
	}

	/**
	 * 内容发布
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "publish", method = RequestMethod.POST)
	public void publish(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String ids = request.getParameter("ids");
			entityService.updatePublishStatus(ids, Constant.PUBLISH_STATUS_PUB);
			printHtml(response, messageSuccuseWrap());
		} catch (Exception e) {
			logger.error("publish", e);
			printHtml(response, messageFailureWrap("发布失败！"));
		}
	}

	/**
	 * 内容下架
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "down", method = RequestMethod.POST)
	public void down(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			String ids = request.getParameter("ids");
			entityService.updatePublishStatus(ids, Constant.PUBLISH_STATUS_DOWN);
			printHtml(response, messageSuccuseWrap());
		} catch (Exception e) {
			logger.error("down", e);
			printHtml(response, messageFailureWrap("下架失败！"));
		}
	}

	/**
	 * 保存单条Dictionary记录.
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@Override
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Content o = bindEntity(request, entityClass);
			String title = OperateLog.OPERATING_UPDATE_TITLE;
			boolean isUpdate = true;
			if (o.getId() == null) {
				title = OperateLog.OPERATING_INSERT_TITLE;
				isUpdate = false;
				o.setSiteId(UserSession.getSiteId());
			}
			o.setCreator(UserSession.getUserId().toString());

			// 内容主表登录更新
			entityService.saveOrUpdate(o);
			Long id = o.getId();

			// 内容附加信息更新
			ContentExt contentExt = bindEntity(request, ContentExt.class);
			if (!StringUtils.isEmpty(request.getParameter("extId"))) {
				contentExt.setId(Long.parseLong(request.getParameter("extId")));
			} else {
				contentExt.setId(null);
				contentExt.setContentId(Integer.parseInt(String.valueOf(id)));
			}

			// 内容富文本信息更新
			ContentTxt contentTxt = bindEntity(request, ContentTxt.class);
			if (!StringUtils.isEmpty(request.getParameter("txtId"))) {
				contentTxt.setId(Long.parseLong(request.getParameter("txtId")));
			} else {
				contentTxt.setId(null);
				contentTxt.setContentId(Integer.parseInt(String.valueOf(id)));
			}

			// 内容频道
			String assChannelId = request.getParameter("assChannelId");
			String channelId = request.getParameter("channelId");
			String[] arrayStr = new String[] {};// 字符数组
			List<String> list = new ArrayList<>();// list

			if (!StringUtils.isEmpty(assChannelId)) {
				arrayStr = assChannelId.split(",");// 字符串转字符数组
				list = Arrays.asList(arrayStr);// 字符数组转list
			}

			List arrayList = new ArrayList(list);

			for(int i = 0; i < arrayList.size(); i++)
			{
				if (arrayList.get(i).equals(channelId)) {
					arrayList.remove(i);
					break;
				}
			}

			arrayList.add(0, channelId);

			Map<String, Object> ChnMap = new HashMap<>();
			ChnMap.put("list", arrayList);
			ChnMap.put("contentId", String.valueOf(id));

			Map<String, Object> map = new HashMap<>();
			map.put("Ext", contentExt);
			map.put("Txt", contentTxt);
			map.put("Channel", ChnMap);
			entityService.updateContentExt(map);

			// 写日志
			String content = "id=" + id;
			operateLogService.wirteLog(request, title, content);
			printText(response, messageSuccuseWrap());
		} catch (Exception e) {
			logger.error("save", e);
			printText(response, messageFailureWrap("保存失败！"));
		}
	}

}
