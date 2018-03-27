package cn.hzstk.securities.indexvm.action;

import cn.hzstk.securities.cm.domain.Info;
import cn.hzstk.securities.cm.domain.InfoCategory;
import cn.hzstk.securities.cm.service.InfoService;
import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.common.utils.PortalUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import net.ryian.core.SystemConfig;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/indexvm")
public class IndexvmAction extends BaseMagicAction {

	@Autowired
	InfoService infoService;

	@RequestMapping("news")
	public String news(HttpServletRequest request, String status) {
		return super.getNameSpace()+"newlist";
	}
	@RequestMapping("vip")
	public String vip(HttpServletRequest request, String status) {

		return super.getNameSpace()+"viplist";
	}
	@RequestMapping("expert")
	public String expert(HttpServletRequest request, String status) {

		return super.getNameSpace()+"expertlist";
	}
	@RequestMapping("database")
	public String database(HttpServletRequest request,HttpServletResponse response) {
		try {
			Map<String,String> params = bindMap(request);
			int pageNums = MapUtils.getInteger(params, "page", 1);
			int pageSize = MapUtils.getInteger(params, "size", 10);
			params.put("page", String.valueOf(pageNums));
			params.put("size",String.valueOf(pageSize));
			PageInfo page =infoService.selectInfoPage(params,pageSize,pageNums);
			List<InfoCategory> categorys=new ArrayList<>();
			categorys = infoService.selectCategoryList();
			List<Info> infos = new ArrayList<>();
			infos=infoService.getUploadTop();
			request.setAttribute("tops",infos);
			request.setAttribute("pagenation", page);
			request.setAttribute("categorys", categorys);
			request.setAttribute("maps", params);
			PortalUtil.setUrls(request);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			return super.getNameSpace()+"databaselist";
		}
	}
	@RequestMapping("download")
	public void download(HttpServletRequest request,HttpServletResponse response) {
		try {
			Map<String,String> params = bindMap(request);
			infoService.updateInfo(params);
			Map<String,String> data = new HashMap<>();
			data.put("status","success");
			response.getWriter().write(JSON.toJSONString(data));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
