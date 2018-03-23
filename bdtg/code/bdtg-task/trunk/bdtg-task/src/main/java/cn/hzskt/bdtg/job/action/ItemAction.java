package cn.hzskt.bdtg.job.action;

import cn.hzskt.bdtg.common.message.ONSConfig;
import cn.hzskt.bdtg.common.util.UuidUtil;
import cn.hzskt.bdtg.common.util.WebUtil;
import cn.hzskt.bdtg.job.domain.Folder;
import cn.hzskt.bdtg.job.mapper.FolderMapper;
import cn.hzskt.bdtg.job.mapper.ItemMapper;
import cn.hzskt.bdtg.job.service.FolderService;
import cn.hzskt.bdtg.sys.domain.Dict;
import cn.hzskt.bdtg.sys.mapper.DictMapper;
import cn.hzskt.bdtg.sys.utils.json.DictFilter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Producer;
import com.github.pagehelper.PageInfo;
import net.ryian.commons.StringUtils;
import net.ryian.core.SystemConfig;
import net.ryian.orm.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.job.domain.Item;
import cn.hzskt.bdtg.job.service.ItemService;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*
* @description:Item action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/job/item")
@SuppressWarnings("serial")
public class ItemAction extends MagicAction<Item,ItemService> {
    @Autowired
    DictMapper dictMapper;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    FolderService folderService;
    @Autowired
    FolderMapper folderMapper;
    @Autowired
    Producer producer;
    @RequestMapping(value = "queryPaged")
    public void queryPaged(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        try {
            Long tid = WebUtil.getTid(request);
            if(tid==null){
                tid=Long.parseLong("1");
            }
            Map<String,String> map = new HashMap<>();
            map = getParameterMap(request);
            map.put("tid",tid.toString());
            PageInfo<?> page = entityService.queryPaged(map);
            JSONObject o = (JSONObject) JSONObject.parse(JSONObject.toJSONString(page,new DictFilter()));
            o.put("rows", o.get("list"));
            o.remove("list");
            o.put("totalPageCount", o.get("lastPage"));
            o.put("countPerPage", o.get("pageSize"));
            o.put("currentPage",o.get("pageNum"));
            printJson(response,o.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void beforeAdd(HttpServletRequest request,Model model) {
        model.addAttribute("dictlist",dictMapper.getdictlist());
    }
    protected void beforeIndex(HttpServletRequest request,Model model) {
        model.addAttribute("dictlist",dictMapper.getdictlist());
    }
    protected void beforeEdit(HttpServletRequest request,Model model, BaseEntity entity) {
        model.addAttribute("dictlist",dictMapper.getdictlist());
    }
    /**
     * 保存单条Dictionary记录.
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long tid = WebUtil.getTid(request);
            if(tid==null){
                tid=Long.parseLong("1");
            }
            Item item = bindEntity(request, entityClass);
            String str="添加主项【";
            if(item.getId()==null){
                item.setTask(tid);
                String code = UuidUtil.getUUID();
                //主项文件夹创建或更新
                saveFolder(code,"-1",item.getName(),"0",tid);

                String[] foritem = SystemConfig.INSTANCE.getValue("item_folder_types").split(",");
                String key_name="folder_type";
                for(int i=0;i<foritem.length;i++){
                    List<Dict> list = new ArrayList<Dict>();
                    String name="";
                    list = dictMapper.getContent(key_name, foritem[i]);
                    if(list.size()>0){
                        name=list.get(0).getContent();
                    }
                    //主项下子文件夹创建或更新
                    saveFolder("", code, name, foritem[i], tid);
                }
            }else{
                str="修改主项【";
                //获取旧名称
                Item upitem = new Item();
                upitem = entityService.get(item.getId());
                List<Folder> upfolderList = new ArrayList<Folder>();
                upfolderList = folderMapper.getId(tid, upitem.getName(),"-1");
                if(upfolderList.size()>0){
                    Folder folder = new Folder();
                    folder= folderService.get(upfolderList.get(0).getId());
                    folder.setName(item.getName());
                    folderService.saveOrUpdate(folder);
                }
            }
            entityService.saveOrUpdate(item);
            ONSConfig.sendJobStatusMessage(producer, request, str + item.getName() + "】", "B");
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("保存失败！"));
        }
    }

    /**
     * 文件夹的创建或更新
     * @param code uuid唯一标示编码
     * @param pcode 父Id
     * @param name 文件名
     * @param type 类型
     * @param tid 所属任务
     * @return
     */
    private boolean saveFolder(String code,String pcode,String name,String type,Long tid){
        try {
            Folder folder = new Folder();
            List<Folder> folderList = new ArrayList<Folder>();
            folder.setCode(code);
            folder.setName(name);
            folder.setType(Long.parseLong(type));
            folder.setPcode(pcode);
            folder.setTid(tid);
            folderService.saveOrUpdate(folder);
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 保存单条Dictionary记录.
     */
    @RequestMapping(value = "namevalidate", method = RequestMethod.POST)
    public void namevalidate(HttpServletRequest request, HttpServletResponse response,String id,String name) {

        try {
            Long tid = WebUtil.getTid(request);
            if(tid==null){
                tid=Long.parseLong("1");
            }
            Map<String, String> param = new HashMap<String, String>();
            if(id==null){
                id="";
            }
           int count = itemMapper.getcount(tid,name,id);
            if(count==0){
                param.put("vars", "Y");
            }else{
                param.put("vars", "N");
            }
            this.printText(response, JSON.toJSONString(param));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public void delete(HttpServletRequest request,
                       HttpServletResponse response) {
        try {
            Long tid = WebUtil.getTid(request);
            if(tid==null){
                tid=Long.parseLong("1");
            }
            String ids = request.getParameter("ids");
            for (String id : ids.split(",")) {
                Item item = new Item();
                item = entityService.get(Long.parseLong(id));
                //删除主项
                entityService.logicRemove(Long.parseLong(id));
                ONSConfig.sendJobStatusMessage(producer, request, "删除主项【" + item.getName()+"】", "B");
                List<Folder> delfolders = new ArrayList<Folder>();
                delfolders = folderMapper.getId(tid, item.getName(), "-1");
                //删除主项文件夹
                folderService.logicRemove(delfolders.get(0).getId());
                List<Folder> delfolderList = new ArrayList<Folder>();
                delfolderList = folderMapper.getsonId(tid,delfolders.get(0).getCode());
                if(delfolderList.size()>0){
                    for(int i=0;i<delfolderList.size();i++){
                        //删除主项文件夹的子文件夹
                        folderService.logicRemove(delfolderList.get(i).getId());
                    }
                }
            }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("delete", e);
            printJson(response, messageFailureWrap("删除失败！"));
        }
    }
}
