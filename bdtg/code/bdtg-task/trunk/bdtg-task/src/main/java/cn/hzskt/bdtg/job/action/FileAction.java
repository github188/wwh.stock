package cn.hzskt.bdtg.job.action;

import cn.hzskt.bdtg.api.action.FileUploadAction;
import cn.hzskt.bdtg.common.message.ONSConfig;
import cn.hzskt.bdtg.common.util.WebUtil;
import cn.hzskt.bdtg.job.domain.Duty;
import cn.hzskt.bdtg.job.domain.Folder;
import cn.hzskt.bdtg.job.domain.Member;
import cn.hzskt.bdtg.job.service.*;
import com.aliyun.openservices.ons.api.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzskt.bdtg.common.action.MagicAction;
import cn.hzskt.bdtg.job.domain.File;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
*
* @description:File action
* @author: autoCode
* @history:
*/
@Controller
@RequestMapping("/job/file")
@SuppressWarnings("serial")
public class FileAction extends MagicAction<File,FileService> {

    @Autowired
    FolderService folderService;
    @Autowired
    DutyService dutyService;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    Producer producer;
    protected void beforeIndex(HttpServletRequest request,Model model) {
        //获取项目ID
        Long tid = WebUtil.getTid(request);
        //根据用户ID获取member表数据
        Member member = memberService.getMemberByTaskAndUser(tid, WebUtil.getUser(request).getId());
        //获取member表的ID
        Long mmbid= member.getId();
        //根据用户ID获取member_type
        Integer member_type=member.getType();
        //获取文件夹
        Map<String,String> map = getParameterMap(request);
        map.put("tid", tid.toString());
        List<Folder> follist=folderService.query(map);

        //通过获取的文件夹及当前登录用户的职责进行文件夹权限分配
        List<Folder> mfollist = new ArrayList<>();
        for(int i=0;i<follist.size();i++){
            //当文件夹类型为1-6之间的时候，默认登录的所有用户都有权限拥有
            if(follist.get(i).getType()>=1&&follist.get(i).getType()<=6){
                mfollist.add(follist.get(i));
            }else if(follist.get(i).getType()==0&&(member_type==1||member_type==4||member_type==5)){
                boolean haveson = false;
                List<Folder> follist2= folderService.query(map);
                //获取用户职责
                List<Duty> dulist = new ArrayList<>();
                dulist=dutyService.getDutyByTid(Integer.parseInt(mmbid.toString()),tid);
                Long itemid=itemService.getItemByName(tid,follist.get(i).getName()).getId();
                String code=follist.get(i).getCode();
                for(int j=0;j<dulist.size();j++){
                    if(dulist.get(j).getItem()==itemid){
                        Long type= Long.parseLong(dulist.get(j).getDuty().toString());
                        for(int s=0;s<follist2.size();s++){
                            if(follist2.get(s).getPcode().equals(code)&&follist2.get(s).getType()==type){
                                mfollist.add(follist2.get(s));
                                haveson=true;
                            }
                        }
                    }
                }
                if(haveson){
                    mfollist.add(follist.get(i));
                }
            }
        }
        model.addAttribute("follist",mfollist);
    }
    @RequestMapping("weblist")
    protected String weblist(HttpServletRequest request,Model model) {
        Long tid=WebUtil.getTid(request);
        Map<String,String> map = getParameterMap(request);
        map.put("tid", tid.toString());
        List<Folder> follist=folderService.query(map);
        String id="";
        for(int i=0;i<follist.size();i++){
            if(follist.get(i).getType()==1){
                id=follist.get(i).getId().toString();
                break;
            }
        }
        model.addAttribute("FIL_ALL","FIL_YZZLK");
        model.addAttribute("type","1");
        model.addAttribute("folderid",id);
        return getNameSpace() + "files";
    }
    @RequestMapping(value = "filelist",method = RequestMethod.GET)
    public String filelist(HttpServletRequest request,Model model) {
        String id= request.getParameter("id");
        String type=request.getParameter("type");
        String strurl="";
        //权限设定用
        String FIL_ALL="";
        //获取项目ID
        Long tid = WebUtil.getTid(request);
        //根据用户ID获取member表数据
        Member member = memberService.getMemberByTaskAndUser(tid, WebUtil.getUser(request).getId());
        //根据用户ID获取member_type
        Integer member_type=member.getType();
        model.addAttribute("member_type",member_type);
        switch (type){
            case "1" : strurl="files";FIL_ALL="FIL_YZZLK"; break;
            case "2" : strurl="files";FIL_ALL="FIL_GLK"; break;
            case "3" : strurl="files";FIL_ALL="FIL_TJWJK"; break;
            case "4" : strurl="files";FIL_ALL="FIL_SBCSZLK"; break;
            case "5" : strurl="files";FIL_ALL="FIL_CPK"; break;
            case "6" : strurl="files";FIL_ALL="FIL_YZSCK"; break;
            case "7" : strurl="sheji"; break;
            case "8" : strurl="jiaohe"; break;
            case "9" : strurl="shenhe"; break;
            case "10" : strurl="shending"; break;
            case "11" : strurl="huiqian"; break;
            default : strurl=""; break;
        }
        model.addAttribute("type",type);
        model.addAttribute("folderid",id);
        model.addAttribute("FIL_ALL",FIL_ALL);
        return getNameSpace() + strurl;
    }

    /**
     * 保存文件
     */
    @RequestMapping(value = "savefile", method = RequestMethod.POST)
    public void savefile(HttpServletRequest request, HttpServletResponse response,String path,String name,String size,String folderid) {
        try {
            boolean issave =true;
            File file= new File();
            List<File> pfile = entityService.getFileByNameAndId(name);
            //所在文件夹的类型
            Long type = folderService.get(Long.parseLong(folderid)).getType();
            String pcode = folderService.get(Long.parseLong(folderid)).getPcode();
            if(pfile.size()>0){
                //判断上传的文件是否已经在设计以外的流程中，如果有，则不允许上传
                for(int i=0;i<pfile.size();i++){
                    //同一个文件夹中不能存在同名的文件
                    if(folderid.equals(pfile.get(i).getFolder())){
                        issave=false;
                        break;
                    }else{
                        Folder folder = folderService.get(pfile.get(i).getFolder());
                        Long typei=folder.getType();
                        String pcodei=folder.getPcode();
                        //上传文件夹是成品库的时候，所上传的文件在主项下的文件夹中都不能存在
                        if(type==5&&(typei>=7&&typei<=11)){
                            issave=false;
                            break;
                        }else if(type>=7&&type<=11){
                            //当已经有同名文件在成品库的时候，不能上传
                            if(typei==5){
                                issave=false;
                                break;
                            }else{
                                if(pcode.equals(pcodei)){
                                    issave=false;
                                    break;
                                }
                            }
                        }
                    }
                }
                //如果
                if(issave){
                    file.setId(pfile.get(0).getId());
                }
            }else{
                file.setUpdateDate(new Date());
            }
            if(issave){
                file.setPath(path);
                file.setName(name);
                file.setFoldSize(Long.parseLong(size));
                file.setFolder(Long.parseLong(folderid));
                file.setUploadName(WebUtil.getUser(request).getUserName());
                entityService.saveOrUpdate(file);
                ONSConfig.sendJobStatusMessage(producer, request, "上传文件【" + name+"】至【"+getTypeStr(String.valueOf(type))+"】", "D");
                printJson(response, messageSuccuseWrap());
            }else{
                //当存在审核中的文件，将刚上传的文件从服务器删除
                FileUploadAction.delete(path);
                printJson(response, messageFailureWrap("该文件已在流程中！"));
            }
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("上传失败！"));
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param ids 要操作的文件ID
     */
    @RequestMapping(value = "upfile", method = RequestMethod.POST)
    public void upfile(HttpServletRequest request, HttpServletResponse response,String ids,String type) {
        try {
            Long tid = WebUtil.getTid(request);
            for (String id : ids.split(",")) {
                File file = new File();
                file = entityService.get(Long.parseLong(id));
                file.setFolder(refolder(tid,file.getFolder(),Long.parseLong(type)));
                entityService.saveOrUpdate(file);
                ONSConfig.sendJobStatusMessage(producer, request, "提交文件【" + file.getName() + "】至【" + getTypeStr(String.valueOf(type))+ "】", "D");
            }
            printJson(response, messageSuccuseWrap());
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("操作失败！"));
        }
    }

    /**
     * 获取上传文件夹ID
     * @param tid 任务ID
     * @param folderid 当前文件夹ID
     * @param type 上传文件夹类型
     * @return
     * @throws Exception
     */
    private Long refolder(Long tid,Long folderid,Long type) throws Exception{
        List<Folder> folders = new ArrayList<>();
        //提交到成品库的时候
        if(type==5){
            folders = entityService.getFolderByTidandType(tid, type);
        }else{
            //普通提交的时候
            String pcode = folderService.get(folderid).getPcode();
            folders = entityService.getFolderByCodeandType(pcode,type);
        }
        return  folders.get(0).getId();
    }
    /**
     * 删除文件
     */
    @RequestMapping(value = "delfile", method = RequestMethod.POST)
    public void delfile(HttpServletRequest request, HttpServletResponse response,String ids) {
        Boolean reflag=false;
        try {
            for (String id : ids.split(",")) {
                File file = new File();
                file = entityService.get(Long.parseLong(id));
                reflag = FileUploadAction.delete(file.getPath());
                if(reflag){
                    entityService.logicRemove(file.getId());
                    ONSConfig.sendJobStatusMessage(producer, request, "删除文件【" + file.getName()+"】", "D");
                }
            }
            if(reflag){
                printJson(response, messageSuccuseWrap());
            }else {
                printJson(response, messageFailureWrap("删除失败！"));
            }
        } catch (Exception e) {
            logger.error("save", e);
            printJson(response, messageFailureWrap("操作失败！"));
        }
    }
    private  String getTypeStr(String type){
        String typeStr="";
        switch (type){
            case "1" :typeStr= "业主资料库"; break;
            case "2" : typeStr= "管理库"; break;
            case "3" : typeStr= "条件文件库"; break;
            case "4" : typeStr= "设备厂商资料库"; break;
            case "5" : typeStr= "成品库"; break;
            case "6" : typeStr= "业主审查库"; break;
            case "7" : typeStr= "设计"; break;
            case "8" : typeStr= "校核"; break;
            case "9" : typeStr= "审核"; break;
            case "10" : typeStr= "审定"; break;
            case "11" : typeStr= "会签"; break;
        }
        return typeStr;
    }
}
