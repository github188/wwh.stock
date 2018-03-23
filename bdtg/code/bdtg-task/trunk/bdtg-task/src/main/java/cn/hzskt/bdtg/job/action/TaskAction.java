package cn.hzskt.bdtg.job.action;

import javax.servlet.http.HttpServletRequest;

import cn.hzskt.bdtg.sys.domain.Dict;
import cn.hzskt.bdtg.sys.service.DictService;
import com.alibaba.druid.filter.AutoLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.common.util.WebUtil;
import cn.hzskt.bdtg.job.domain.Task;
import cn.hzskt.bdtg.job.service.TaskService;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
*
* @description:Status action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/job/task")
public class TaskAction extends MagicAction<Task, TaskService> {
	@Autowired
	DictService dictService;
	
    protected void beforeIndex(HttpServletRequest request,Model model) {
    	long tid = WebUtil.getTid(request);
    	Task tk = this.entityService.get(tid);
		List<Dict> productList = dictService.selectDictByKeyName("task_product_type");
		List<Dict> professionList = dictService.selectDictByKeyName("task_zhuanye_type");
		//产品
		String productstr="";
		if(tk.getProduct()!=null){
			for(int i =0;i<tk.getProduct().split(",").length;i++){
				for(int j=0;j<productList.size();j++){
					if(productList.get(j).getValue().equals(tk.getProduct().split(",")[i])){
						if("".equals(productstr)){
							productstr+=productList.get(j).getContent();
						}else {
							productstr+=","+productList.get(j).getContent();
						}
						break;
					}
				}
			}
		}
		tk.setProduct(productstr);
		//专业
//		String professionStr="";
//		for(int i =0;i<tk.getProfession().split(",").length;i++){
//			for(int j=0;j<professionList.size();j++){
//				if(professionList.get(j).getValue().equals(tk.getProfession().split(",")[i])){
//					if("".equals(professionStr)){
//						professionStr+=professionList.get(j).getContent();
//					}else {
//						professionStr+=","+professionList.get(j).getContent();
//					}
//					break;
//				}
//			}
//		}
//		tk.setProfession(professionStr);
    	request.setAttribute("model", tk);
    }
}
