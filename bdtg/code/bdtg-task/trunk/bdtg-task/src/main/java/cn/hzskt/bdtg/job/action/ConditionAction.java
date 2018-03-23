package cn.hzskt.bdtg.job.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.common.util.WebUtil;
import cn.hzskt.bdtg.job.domain.Condition;
import cn.hzskt.bdtg.job.domain.ConditionMember;
import cn.hzskt.bdtg.job.domain.Duty;
import cn.hzskt.bdtg.job.domain.Item;
import cn.hzskt.bdtg.job.domain.Member;
import cn.hzskt.bdtg.job.service.ConditionMemberService;
import cn.hzskt.bdtg.job.service.ConditionService;
import cn.hzskt.bdtg.job.service.DutyService;
import cn.hzskt.bdtg.job.service.ItemService;
import cn.hzskt.bdtg.job.service.MemberService;
import cn.hzskt.bdtg.sys.domain.Dict;
import cn.hzskt.bdtg.sys.domain.User;
import cn.hzskt.bdtg.sys.service.DictService;
import cn.hzskt.bdtg.sys.utils.json.DictFilter;

/**
 * 
 * @description:Condition action
 * @author: autoCode
 * @history:
 */
@Controller
@RequestMapping("/job/condition")
@SuppressWarnings("serial")
public class ConditionAction extends MagicAction<Condition, ConditionService> {

	@Autowired
	ItemService itemService;
	@Autowired
	DictService dictService;
	@Autowired
	MemberService memberService;
	@Autowired
	ConditionMemberService conditionMemberService;
	@Autowired
	DutyService dutyService;
	
	@RequestMapping(value = "query")
	public void query(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();

			paramMap.put("tid", WebUtil.getTid(request).toString());

			List<Condition> list = entityService.list(paramMap);
			JSONArray array = (JSONArray) JSONObject.parse(JSONObject
					.toJSONString(list, new DictFilter()));
			JSONObject o = new JSONObject();
			o.put("rows", array);

			JSONArray merges = new JSONArray();
			// 合并信息
			if (list.size() > 0) {
				String temp = list.get(0).getMajor();
				int rowspan = 0;
				int index = 0;
				for (int i = 0; i < list.size(); i++) {
					if (temp.equals(list.get(i).getMajor())) {
						rowspan++;

						if (i == list.size() - 1) {
							JSONObject obj = new JSONObject();
							obj.put("index", index);
							obj.put("rowspan", rowspan);
							merges.add(obj);
						}
					} else {
						JSONObject obj = new JSONObject();
						obj.put("index", index);
						obj.put("rowspan", rowspan);
						merges.add(obj);

						temp = list.get(i).getMajor();
						rowspan = 1;
						index = i;
					}
				}
			}
			o.put("merge", merges);
			o.put("total", array.size());
			printJson(response, o.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "querypm")
	public void querypm(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();

			paramMap.put("tid", WebUtil.getTid(request).toString());
			List<Condition> list = entityService.list(paramMap);
			JSONArray array = (JSONArray) JSONObject.parse(JSONObject
					.toJSONString(list, new DictFilter()));
			
			
			JSONArray array_after = new JSONArray();

			for (int k = 0; k < array.size(); k++) {
				JSONObject ob = array.getJSONObject(k);
				Long conditionid = ob.getLong("id");

				// 判断是否确认
				int result = conditionMemberService.querybycondition(conditionid);
				
				
				//查询有多少成员可以看到该条件
				//TODO
				int total = memberService.countbymajor(ob.getString("major"), ob.getLong("tid"));
				if(result==total&&total!=0){
				ob.put("constatus", 1);
				}else if(result>0&&result<total){
				ob.put("constatus", 0);	
				}else{
				ob.put("constatus", -1);	
				}
				array_after.add(ob);
			}
			
			JSONObject o = new JSONObject();
			o.put("rows", array_after);

			JSONArray merges = new JSONArray();
			// 合并信息
			if (list.size() > 0) {
				String temp = list.get(0).getMajor();
				int rowspan = 0;
				int index = 0;
				for (int i = 0; i < list.size(); i++) {
					if (temp.equals(list.get(i).getMajor())) {
						rowspan++;

						if (i == list.size() - 1) {
							JSONObject obj = new JSONObject();
							obj.put("index", index);
							obj.put("rowspan", rowspan);
							merges.add(obj);
						}
					} else {
						JSONObject obj = new JSONObject();
						obj.put("index", index);
						obj.put("rowspan", rowspan);
						merges.add(obj);

						temp = list.get(i).getMajor();
						rowspan = 1;
						index = i;
					}
				}
			}
			o.put("merge", merges);
			printJson(response, o.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "querymember")
	public void querymember(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			Long tid = WebUtil.getTid(request);
			// 获取当前用户的专业信息
			User user = WebUtil.getUser(request);
			Long userid = user.getId();
			Member member = memberService.getMemberByUser(userid, tid);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("tid",tid.toString());
			map.put("memberId", member.getId().toString());
			List<Duty> dutylist = dutyService.query(map);
			List<Condition> list =new ArrayList<Condition>();
			
			Set<String> set =new HashSet<String>();
			for(Duty duty:dutylist){
				set.add(duty.getMajor());
			}
			for (String val : set) {
			String major=String.valueOf(val);
			paramMap.put("tid", WebUtil.getTid(request).toString());
			paramMap.put("major", major);

			list.addAll(entityService.list(paramMap));
			}
			
			JSONArray array = (JSONArray) JSONObject.parse(JSONObject
					.toJSONString(list, new DictFilter()));
			JSONArray array_after = new JSONArray();

			for (int k = 0; k < array.size(); k++) {
				JSONObject ob = array.getJSONObject(k);
				Long conditionid = ob.getLong("id");

				// 判断是否确认
				int result = conditionMemberService.querybycondition(
						conditionid, userid);
				ob.put("status", result);
				ob.put("status_plus", result);
				array_after.add(ob);
			}

			JSONObject o = new JSONObject();
			o.put("rows", array_after);

			JSONArray merges = new JSONArray();
			// 合并信息
			if (list.size() > 0) {
				String temp = list.get(0).getMajor();
				int rowspan = 0;
				int index = 0;
				for (int i = 0; i < list.size(); i++) {
					if (temp.equals(list.get(i).getMajor())) {
						rowspan++;

						if (i == list.size() - 1) {
							JSONObject obj = new JSONObject();
							obj.put("index", index);
							obj.put("rowspan", rowspan);
							merges.add(obj);
						}
					} else {
						JSONObject obj = new JSONObject();
						obj.put("index", index);
						obj.put("rowspan", rowspan);
						merges.add(obj);

						temp = list.get(i).getMajor();
						rowspan = 1;
						index = i;
					}
				}
			}
			o.put("merge", merges);
			printJson(response, o.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void beforeIndex(HttpServletRequest request, Model model) {
		// TODO Auto-generated method stub
		String taskid = WebUtil.TASK_ID_KEY;
	}

	@Override
	protected void beforeAdd(HttpServletRequest request, Model model) {
		List<Item> list = itemService.query(WebUtil.getTid(request));

		// 获取所有的专业信息
		Set<String> set = new HashSet<String>();
		for (Item item : list) {
			union(item.getMajor().split(","), set);
		}
		Map<String, Dict> dicts = dictService.getDictsByKey("major_type");
		List<Dict> selectlist = new ArrayList<Dict>();
		for (String val : set) {
			selectlist.add(dicts.get(val));
		}
		model.addAttribute("selects", selectlist);

	}

	@RequestMapping(value = "pmindex")
	public String pmindex(HttpServletRequest request, Model model) {
		beforeIndex(request, model);
		return getNameSpace() + "index_pm";
	}

	@RequestMapping(value = "cyindex")
	public String cyindex(HttpServletRequest request, Model model) {
		beforeIndex(request, model);
		return getNameSpace() + "index_cy";
	}
	
	  @RequestMapping(value = "confirm",method = RequestMethod.POST)
	    public void confirm(HttpServletRequest request,
	                       HttpServletResponse response) {
	        try {
	        	User user = WebUtil.getUser(request);
	            String id = request.getParameter("id");
	            Condition condition = entityService.get(Long.valueOf(id));
	            
	            ConditionMember cm  =new ConditionMember();
	            cm.setConditionId(condition.getId());
	            cm.setJmbId(user.getId());
	            cm.setTid(condition.getTid());
	            
	            conditionMemberService.saveOrUpdate(cm);
	            printJson(response, messageSuccuseWrap());
	        } catch (Exception e) {
	            logger.error("confirm", e);
	            printJson(response, messageFailureWrap("失败！"));
	        }
	    }
	  
	  /**
	     * 保存单条Dictionary记录.
	     */
	  @Override
	    @RequestMapping(value = "save", method = RequestMethod.POST)
	    public void save(HttpServletRequest request, HttpServletResponse response) {
	        try {
	            Condition o = bindEntity(request, entityClass);
	            o.setTid(WebUtil.getTid(request));
	            entityService.saveOrUpdate(o);
	            printJson(response, messageSuccuseWrap());
	        } catch (Exception e) {
	            logger.error("save", e);
	            printJson(response, messageFailureWrap("保存失败！"));
	        }
	    }
	public void union(String[] arr1, Set<String> set) {
		for (String str : arr1) {
			set.add(str);
		}
	}
	

}
