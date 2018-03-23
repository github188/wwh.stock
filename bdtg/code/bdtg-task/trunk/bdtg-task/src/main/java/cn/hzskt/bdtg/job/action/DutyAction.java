package cn.hzskt.bdtg.job.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hzskt.bdtg.common.message.ONSConfig;
import com.aliyun.openservices.ons.api.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.common.util.WebUtil;
import cn.hzskt.bdtg.job.domain.Duty;
import cn.hzskt.bdtg.job.domain.Folder;
import cn.hzskt.bdtg.job.domain.Item;
import cn.hzskt.bdtg.job.domain.Member;
import cn.hzskt.bdtg.job.service.DutyService;
import cn.hzskt.bdtg.job.service.FolderService;
import cn.hzskt.bdtg.job.service.ItemService;
import cn.hzskt.bdtg.job.service.MemberService;
import cn.hzskt.bdtg.sys.domain.Dict;
import cn.hzskt.bdtg.sys.service.DictService;
import cn.hzskt.bdtg.sys.service.UserService;

/**
*
* @description:FolderPermission action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/job/duty")
@SuppressWarnings("serial")
public class DutyAction extends MagicAction<Duty,DutyService> {
		@Autowired
		MemberService memberService;
		@Autowired
		UserService userService;
		@Autowired
		DictService dictService;
		@Autowired
		ItemService itemService;
		@Autowired
		FolderService folderService;
		@Autowired
		Producer producer;
		
		@Override
		@RequestMapping(value = "queryPaged")
	    public void queryPaged(HttpServletRequest request,
	                           HttpServletResponse response) throws Exception {
	        try {
	            @SuppressWarnings("unchecked")
	            //member
	            Long tid =WebUtil.getTid(request);
	            List<Member> members = memberService.getMemberBytid(tid);
	            
	            JSONObject o =new JSONObject();
	            JSONArray array = (JSONArray) JSONObject.parse(JSONObject.toJSONString(members));
	            Map<String, Dict> dictmap = dictService.getDictsByKey("major_type");
	            JSONArray array_after = new JSONArray();
	            for(int i=0;i<array.size();i++){
	            	JSONObject obj = array.getJSONObject(i);
	            	Map<String, String> paramMap =new HashMap<String,String>();
	            	paramMap.put("memberId", obj.getString("id"));
	            	paramMap.put("tid", String.valueOf(tid));
	            	List<Duty> dutys = entityService.query(paramMap);
	            	
	            	JSONArray dutyarray = new JSONArray();
	            	for(Duty duty:dutys){
	            		JSONObject dutyobj =  (JSONObject) JSONObject.parse(JSONObject.toJSONString(duty));
	            		//专业
	            		String majorid = dutyobj.getString("major");
	            		String name = dictmap.get(majorid).getContent();
	            		dutyobj.put("majorStr", name);
	            		//主项
	            		Item item = itemService.get(Long.valueOf(dutyobj.getString("item")));
	            		dutyobj.put("itemStr", item.getName());
	            		//职责
	            		Map<String, String> paramMap2 =new HashMap<String,String>();
	            		paramMap2.put("type", dutyobj.getString("duty"));
	            		List<Folder> typelist = folderService.query(paramMap2);
	            		if(typelist.size()>0){
	            		dutyobj.put("dutyStr", typelist.get(0).getName());
	            		}
	            		dutyarray.add(dutyobj);
	            	}
	            	obj.put("duty", dutyarray);
					array_after.add(obj);
	            }
	            o.put("rows", array_after);
	            printJson(response,o.toJSONString());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		
		@RequestMapping(value = "edit/{id}")
	    public String edit(HttpServletRequest request,@PathVariable("id") Long id, Model model)
	            throws Exception {
	        if (id != null) {
	        	
	        	 Map<String, Dict> dictmap = dictService.getDictsByKey("major_type");
	        	Member entity = memberService.get(id);
	        	 model.addAttribute("model", entity);
	            Long tid = WebUtil.getTid(request);
	            
	        	//获取原有的职责
	        	Map<String, String> paramMap3 =new HashMap<String,String>();
	        	paramMap3.put("memberId", String.valueOf(id));
	        	paramMap3.put("tid", String.valueOf(tid));
            	List<Duty> dutylist = entityService.query(paramMap3);
            	Map<Long, Duty> dutymap =new HashMap<Long,Duty>();
            	for(Duty duty:dutylist){
            		dutymap.put(duty.getItem(), duty);
            	}
            	
	            //获取任务下所有主项
	            List<Item> items = itemService.query(tid);
	            JSONArray data = new JSONArray();
	            for(Item item:items){
	            String[] majors = item.getMajor().split(",");
	            
	            //获取专业下拉框内容
	            List<Dict> selects = new ArrayList<Dict>();
	            for(int i=0;i<majors.length;i++){
	            	String major_id = majors[i];
	            	Dict dict = dictmap.get(major_id);
	            	selects.add(dict);
	            }
	            
	            //获取职责下拉框
	            Map<String, String> paramMap =new HashMap<String,String>();
            	paramMap.put("name", String.valueOf(item.getName()));
            	paramMap.put("tid", String.valueOf(tid));
            	List<Folder> plist= folderService.query(paramMap);
            	if(plist.size()<=0){
            		break;
            	}
            	//获取子文件夹
            	Folder pf = plist.get(0);
            	Map<String, String> paramMap2 =new HashMap<String,String>();
            	paramMap2.put("pcode", pf.getCode());
            	List<Folder> slist= folderService.query(paramMap2);
            	
            	//拼接为一个JSONOBJECT
            	JSONObject obj =  (JSONObject) JSONObject.parse(JSONObject.toJSONString(item));
            	JSONArray dutys = (JSONArray) JSONObject.parse(JSONObject.toJSONString(slist));
            	JSONArray majorselect = (JSONArray) JSONObject.parse(JSONObject.toJSONString(selects));
            	obj.put("dutys", dutys);
            	obj.put("majors", majorselect);
            	
            	 if(dutymap.get(item.getId())!=null){
            		 Duty duty = dutymap.get(item.getId());
            		 obj.put("checked", "true");
            		 obj.put("major", duty.getMajor());
            		 obj.put("duty", duty.getDuty());
 	            }else{
 	            	 obj.put("checked", "false");
            		 obj.put("major", "");
            		 obj.put("duty", "");
 	            }
            	 
            	data.add(obj);
	            }
	            model.addAttribute("data", data);
	           
	        }
	        return getNameSpace() + "edit";
	    }
		
		@RequestMapping(value = "load_item")
	    public void load_area(HttpServletRequest request,
	                          HttpServletResponse response,String major) throws Exception {
			
			Long task = WebUtil.getTid(request);
			List<Item> items = itemService.queryitems(Long.valueOf(task), major);
			JSONArray array = (JSONArray) JSONObject.parse(JSONObject.toJSONString(items));
			 
			 //获取主项的文件夹信息
			JSONArray array_after = new JSONArray(); 
			for(int i=0;i<array.size();i++){
				JSONObject obj = array.getJSONObject(i);
				String name = obj.getString("name");
				
				Map params = new HashMap();
				params.put("tid", String.valueOf(task));
				params.put("name", name);
				List<Folder> pfolder = folderService.query(params);
				
				for(Folder pf:pfolder){
					JSONObject fobj =(JSONObject) JSONObject.parse(JSONObject.toJSONString(pf));
					//查询子文件夹
					Map params2 = new HashMap();
					params2.put("pcode", pf.getCode());
					
					List<Folder> sfolder = folderService.query(params2);
					fobj.put("children", (JSONArray) JSONObject.parse(JSONObject.toJSONString(sfolder)));
					obj.put("folders", fobj);
					array_after.add(obj);
				}
			}
	        response.setContentType("text/plain");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
	        PrintWriter writer;
	        try {
	            writer = response.getWriter();
	            writer.print(array_after.toJSONString());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		
		
		
		/**
	     * 保存单条Dictionary记录.
	     */
		@Override
	    @RequestMapping(value = "save", method = RequestMethod.POST)
	    public void save(HttpServletRequest request, HttpServletResponse response) {
	        try {
	        	String id = request.getParameter("id");
	        	Long tid = WebUtil.getTid(request);
	        	
	        	Map<String, String> paramMap =new HashMap<String,String>();
            	paramMap.put("memberId", id);
            	paramMap.put("tid", String.valueOf(tid));
            	List<Duty> needdelete = entityService.query(paramMap);
	        	for(Duty d:needdelete){
	        		entityService.logicRemove(d.getId());
	        	}
            	
	        	Map<String, String[]> map = request.getParameterMap();
	        	String[] items = map.get("item");
	        	String[] majors = map.get("major");
	        	String[] dutys = map.get("duty");
	        	String[] dutyids =map.get("dutyid"); 
	        	for(int i=0;i<items.length;i++){
	        		Duty duty = new Duty();
	        		if(dutyids!=null&&dutyids[i]!=null){
	        			duty.setId(Long.valueOf(dutyids[i]));
	        		}
	        		duty.setMemberId(Integer.valueOf(id));
	        		duty.setItem(Long.valueOf(items[i]));
	        		duty.setMajor(majors[i]);
	        		duty.setDuty(Integer.valueOf(dutys[i]));
	        		duty.setTid(tid);
	        		entityService.saveOrUpdate(duty);
	        	}
				Member member = new Member();
				member = memberService.get(Long.parseLong(id));
				ONSConfig.sendJobStatusMessage(producer, request, "设置" +"【"+ member.getUserName()+"】"+"职责", "C");
	            printJson(response, messageSuccuseWrap());
	        } catch (Exception e) {
	            logger.error("save", e);
	            printJson(response, messageFailureWrap("保存失败！"));
	        }
	    }
}
