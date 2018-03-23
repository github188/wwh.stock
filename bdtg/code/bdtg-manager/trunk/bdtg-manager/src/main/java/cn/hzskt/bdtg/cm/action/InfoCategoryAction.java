package cn.hzskt.bdtg.cm.action;

import cn.hzskt.bdtg.manage.domain.ComboTreeModel;
import cn.hzskt.bdtg.sys.domain.Dict;
import cn.hzskt.bdtg.sys.service.DictService;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.cm.domain.InfoCategory;
import cn.hzskt.bdtg.cm.service.InfoCategoryService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
*
* @description:InfoCategory action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/cm/info-category")
@SuppressWarnings("serial")
public class InfoCategoryAction extends MagicAction<InfoCategory,InfoCategoryService> {

    @Autowired
    private DictService dictService;
    @Autowired
    private InfoCategoryService infoCategoryService;

    @RequestMapping("/category")
    public String execute(HttpServletRequest request) throws Exception {
        return "cm/info-category/cminfocategory";
    }

    /**
     * 栏目数Grid查询处理
     * @throws Exception
     */
    @JsonView
    @RequestMapping("/querTree")
    public void querTree(HttpServletResponse resp,String id) throws Exception {
//        List<Dict> items = dictService.selectPlatform();
        List list = infoCategoryService.getCategoryTree(id);
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
        //List<Dict> items = dictService.selectPlatform();
        ComboTreeModel model = infoCategoryService.getCategoryComboTree(catOrg);
        this.printHtml(resp, JSON.toJSONString(Arrays.asList(model)));
    }

    /**
     * 保存单条Category记录.
     */
    @RequestMapping("/save")
    public void save(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String> params = this.bindMap(request);
            String id = MapUtils.getString(params, "id");
            String upId = MapUtils.getString(params, "upId", "-1");
            params.put("upId", upId);
            if(StringUtils.isNotBlank(id)){
                infoCategoryService.editCategory(params);
            }
            else{
                infoCategoryService.addCategory(params);
            }
            this.printHtml(response, JSON.toJSONString(messageSuccuseWrap()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 删除Category记录.
     */
    @RequestMapping("/cadelete")
    public void delete(HttpServletResponse response, String id) throws Exception {
        infoCategoryService.removeCategory(id);
        this.printHtml(response, JSON.toJSONString(messageSuccuseWrap()));
    }
}
