package com.javaear.koubeiblog.system.controller.manager;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.javaear.koubeiblog.system.controller.AbstractManagerController;
import com.javaear.koubeiblog.system.entity.NBaseModel;
import com.javaear.koubeiblog.system.entity.dto.PermissionDTO;
import com.javaear.koubeiblog.system.entity.model.PermissionModel;
import com.javaear.koubeiblog.system.entity.model.RoleModel;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限管理控制器
 *
 * @author aooer
 */
@Component
public class AuthorityManagerController extends AbstractManagerController<RoleModel> {

    /**
     * 列出所有的角色和具体权限
     *
     * @param modelMap modelMap
     */
    @RequestMapping("authority-management")
    public void list(ModelMap modelMap) {
        //查询全部角色
        List<RoleModel> roleModelList = generalMapper.selectList(new EntityWrapper<>(new RoleModel()));
        //查询全部权限
        List<PermissionModel> permissionModelList = generalMapper.selectList(new EntityWrapper<>(new PermissionModel()));
        //构造前端展示模型.map(roleModel->roleModel.getPermissionList().contains(permissionModel.getId())?1:0)
        List<PermissionDTO> permissionDTOMap = permissionModelList.stream()
                .map(permissionModel -> new PermissionDTO(permissionModel,
                        roleModelList.stream()
                                .collect(Collectors.toMap(NBaseModel::getId,
                                        roleModel -> roleModel.getPermissionList().contains(permissionModel.getId()) ? 1 : 0)))).collect(Collectors.toList());
        modelMap.put("roles", roleModelList);
        modelMap.put("permissions", permissionDTOMap);
        modelMap.put("title", "文章管理");
    }

    @RequestMapping(value = "authority-edit")
    public void edit(ModelMap modelMap) {
        String permissionId = request.getParameter("permissionId");
        String roleId = request.getParameter("roleId");
        String status = request.getParameter("status");
        Assert.notNull(permissionId);
        Assert.notNull(roleId);
        Assert.notNull(status);
        // TODO: 2016/10/20 校验参数
        // 根据角色id查询角色
        RoleModel roleModel = generalMapper.selectById(roleId, RoleModel.class);
        // status 1增加权限,0删除权限
        if (status.equals("1")) {
            // 如果当前角色没有此权限，则添加
            if (!roleModel.getPermissionList().contains(Integer.parseInt(permissionId))) {
                List<Integer> newPermissionList = roleModel.getPermissionList();
                newPermissionList.add(Integer.parseInt(permissionId));
                roleModel.setPermissionList(newPermissionList);
                generalMapper.updateById(roleModel);
            }
        } else if (status.equals("0")) {
            // 如果当前角色包含此权限，则删除
            if (roleModel.getPermissionList().contains(Integer.parseInt(permissionId))) {
                List<Integer> newPermissionList = roleModel.getPermissionList();
                roleModel.setPermissionList(newPermissionList.stream().filter(i -> i != Integer.parseInt(permissionId)).collect(Collectors.toList()));
                generalMapper.updateById(roleModel);
            }
        }
        modelMap.put("result", "success");
    }

}
