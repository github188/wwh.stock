package cn.hzskt.bdtg.cm.action;

import cn.hzskt.bdtg.cm.service.InfoCategoryService;
import cn.hzskt.bdtg.common.Api.FileUploadAction;
import cn.hzskt.bdtg.manage.domain.ComboTreeModel;
import cn.hzskt.bdtg.manage.domain.GridTreeModel;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.cm.domain.Info;
import cn.hzskt.bdtg.cm.service.InfoService;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
*
* @description:Info action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/cm/info")
@SuppressWarnings("serial")
public class InfoAction extends MagicAction<Info,InfoService> {
    @Autowired
    private InfoService entityService;

    @Autowired
    private InfoCategoryService categoryService;

    @RequestMapping("cminfo")
    public String execute(HttpServletRequest request) throws Exception {
        return "cm/info/cminfo";
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
     * 栏目数Grid查询处理
     *
     * @throws Exception
     */
    @RequestMapping("querTree")
    public void querTree(HttpServletResponse resp, String systemid, String id) throws Exception {
        List<GridTreeModel> list = categoryService.getCategoryTree(id);
        this.printHtml(resp, JSON.toJSONString(list));
    }

    /**
     * 保存文件
     */
    @RequestMapping(value = "savefile", method = RequestMethod.POST)
    public void savefile(HttpServletRequest request, HttpServletResponse response,String path,String name,String category) {
        try {
            List<Info> infoList = new ArrayList<>();
            Map<String,Object> map = new HashMap<>();
            map.put("fileName",name);
            map.put("category", category);
            map.put("url", path);
            infoList = entityService.selectNameList(map);
            if(infoList.size()>0){
                map.put("id",infoList.get(0).getId());
                FileUploadAction.delete(infoList.get(0).getUrl());
                entityService.updateInfo(map);
            }else{
                entityService.insertInfo(map);
            }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("上传失败！"));
        }
    }
    /**
     * 删除文件
     */
    @RequestMapping(value = "delfile", method = RequestMethod.POST)
    public void delfile(HttpServletRequest request, HttpServletResponse response,String id,String url) {
        try {
            entityService.deleteContent(id);
            FileUploadAction.delete(url);
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("操作失败！"));
        }
    }
}
