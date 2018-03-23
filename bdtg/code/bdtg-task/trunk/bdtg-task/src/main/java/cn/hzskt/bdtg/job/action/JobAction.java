package cn.hzskt.bdtg.job.action;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.common.constants.DictKeys;
import cn.hzskt.bdtg.job.domain.Member;
import cn.hzskt.bdtg.job.domain.Task;
import cn.hzskt.bdtg.job.service.MemberService;
import cn.hzskt.bdtg.job.service.TaskService;
import cn.hzskt.bdtg.sys.domain.Permission;
import cn.hzskt.bdtg.sys.service.DictService;
import cn.hzskt.bdtg.sys.service.PermissionService;
import com.alibaba.fastjson.JSONObject;
import net.ryian.commons.CollectionUtils;
import net.ryian.core.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by allenwc on 16/4/28.
 */
@Controller
public class JobAction extends BaseMagicAction {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private DictService dictService;
    @Autowired
    private MemberService memberService;

    @RequestMapping("/{tid}")
    public String index(HttpServletRequest request,@PathVariable Long tid) {
        if(tid == null) {
            return "error";
        } else {
            Long userId = getCurrentUser(request).id;
            Task task = taskService.get(tid);
            if(task == null) {
                return "error";
            }
            request.setAttribute("task",task);
            Member member = memberService.getMemberByTaskAndUser(tid,userId);
            if(member == null && userId != 1) {
                return "error";
            }
            if(userId == 1) {
                member = new Member();
                member.setUserId(1l);
                member.setType(2);
            }
            if(member != null) {
                request.setAttribute("member_type",dictService.getDictByKeyNameAndValue(DictKeys.MEMBER_TYPE,String.valueOf(member.getType())).getContent());
            }
            request.getSession().setAttribute("tid",tid);
            request.setAttribute("applicationName", SystemConfig.INSTANCE.getValue("application_name"));
            List<Permission> permissions = permissionService.getPermissionsByUser(member);
            List<Permission> menus = getMenus(permissions);
            if(CollectionUtils.isEmpty(menus)) {
                return "error";
            }
            request.setAttribute("menus",menus);
            request.setAttribute("username", getCurrentUser(request).getName());
            return "index";
        }
    }



    private List<Permission> getMenus(List<Permission> permissions) {
        List<Permission> menus = new ArrayList<>();
        for(Permission menu : permissions) {
            if(menu.getPid()==0l) {
                List<Permission> subMenus = new ArrayList<>();
                for(Permission subMenu : permissions) {
                    if(subMenu.getPid().equals(menu.getId())) {
                        List<Permission> subMenus1 = new ArrayList<>();
                        for(Permission subMenu1 : permissions) {
                            if(subMenu1.getPid().equals(subMenu.getId())) {
                                subMenus1.add(subMenu1);
                            }
                        }
                        Collections.sort(subMenus1, new PermissionComparator());
                        subMenu.setChildren(subMenus1);
                        subMenus.add(subMenu);
                    }
                }
                Collections.sort(subMenus, new PermissionComparator());
                menu.setChildren(subMenus);
                menus.add(menu);
            }
        }
        Collections.sort(menus, new PermissionComparator());
        return menus;
    }

    class PermissionComparator implements Comparator<Permission> {

        @Override
        public int compare(Permission o1, Permission o2) {
            int sort1 = o1.getSort() == null ? 9999 : o1.getSort();
            int sort2 = o2.getSort() == null ? 9999 : o2.getSort();
            return sort1-sort2;
        }
    }

}
