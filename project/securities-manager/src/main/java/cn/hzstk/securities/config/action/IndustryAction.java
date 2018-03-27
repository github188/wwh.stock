package cn.hzstk.securities.config.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ryian.orm.domain.BaseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.AfterFilter;

import cn.hzstk.securities.common.action.MagicAction;
import cn.hzstk.securities.config.domain.Industry;
import cn.hzstk.securities.config.service.IndustryService;

/**
*
* @description:Industry action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/config/industry")
@SuppressWarnings("serial")
public class IndustryAction extends MagicAction<Industry,IndustryService> {
	
	@RequestMapping(value = "queryTreeGrid",method = RequestMethod.GET)
    public void queryTreeGrid(@RequestParam(value = "id",defaultValue = "0")String id,HttpServletResponse response) {
        List<Industry> industryList = entityService.getIndustrysByPid(Integer.valueOf(id));
        String jsonStr;
        if("0".equals(id)) {
        	Industry i = new Industry();
        	 i.setId(0L);
             i.setIndusPid(-1);;
             i.setIndusName("置顶");
             JSONObject o = JSON.parseObject(JSON.toJSONString(i));
             JSONArray array = new JSONArray();
             o.put("children",JSON.parseArray(JSON.toJSONString(industryList, new IndustryTreeGridFilter())));
             array.add(o);
             jsonStr = array.toJSONString();
        } else {
            jsonStr = JSON.toJSONString(industryList, new IndustryTreeGridFilter());
        }
        printJson(response, jsonStr);
    }
	
	@RequestMapping(value = "add/{id}")
	    public String add(HttpServletRequest request,@PathVariable("id") Long id, Model model) {
		 	if (id != null) {
		 		if(id==0L){
		 			 Industry top = new Industry();
		 			 top.setId(0L);
		 			 top.setIndusPid(-1);;
		 			 top.setIndusName("置顶");	
		 			 
		 			 model.addAttribute("model",top);
		 		}else{
		 			BaseEntity entity = entityService.get(id);
		 			model.addAttribute("model",entity);
		 		}
		 	}
	      //  beforeAdd(request,model);
	        return getNameSpace() + "add";
	    }
	
	/**目录下拉框作废 
	 protected void beforeAdd(HttpServletRequest request,Model model){
		 //获取select目录
		 List<Industry> father = new ArrayList<Industry>();
		 Industry top = new Industry();
		 top.setId(0L);
		 top.setIndusPid(-1);;
		 top.setIndusName(SystemConfig.INSTANCE.getValue("top"));
		 
		 father.add(top); //加入根节点
		 father.addAll(entityService.getsecondary());
		 model.addAttribute("father", father);
	 }

*/
	 /**
     * 保存单条Dictionary记录.
     */
	@Override
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        try {
            Industry o = bindEntity(request, entityClass);
            
            //处理
            if(o.getIsRecommend()==null){
            	o.setIsRecommend(0);
            }
            if(o.getTogoods()==null){
            	o.setTogoods(0);
            }
            if(o.getTotask()==null){
            	o.setTotask(0);
            }
            entityService.saveOrUpdate(o);
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("保存失败！"));
        }
    }
	

	 protected void beforeEdit(HttpServletRequest request,Model model, BaseEntity entity) {
		 //获取select目录
		Map<String,Object> map = model.asMap();
		Industry son = (Industry) map.get("model");
		if(son.getIndusPid()==0){
			model.addAttribute("father", "置顶");	
		}else{
		Industry father =entityService.get((long)son.getIndusPid());
		model.addAttribute("father", father.getIndusName());
		}
	    }
	 
	 @Override
	 @RequestMapping(value = "delete",method = RequestMethod.POST)
	    public void delete(HttpServletRequest request,
	                       HttpServletResponse response) {
	        try {
	            String ids = request.getParameter("ids");
	            for (String id : ids.split(",")) {
	                entityService.logicRemove(Long.parseLong(id));
	                
	                //删除子行业
	                List<Industry> list = entityService.getIndustrysByPid(Integer.valueOf(id));
	                for(Industry son:list){
	                	 entityService.logicRemove(son.getId());
	                }
	            }
	            printJson(response, messageSuccuseWrap());
	        } catch (Exception e) {
	            logger.error("delete", e);
	            printJson(response, messageFailureWrap("删除失败！"));
	        }
	    }	
	 public class IndustryTreeGridFilter extends AfterFilter {


	        @Override
	        public void writeAfter(Object o) {
	            writeKeyValue("state","closed");
	        }
	    }
}
