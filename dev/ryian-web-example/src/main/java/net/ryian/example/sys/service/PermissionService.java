package net.ryian.example.sys.service;

import net.ryian.commons.StringUtils;
import net.ryian.example.sys.domain.Permission;
import net.ryian.example.sys.mapper.PermissionMapper;
import net.ryian.orm.service.BaseService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: autoCode
 * @history:
 */
@Component
public class  PermissionService extends BaseService<Permission,PermissionMapper> {

	@Override
	public Long saveOrUpdate(Permission permission) {
		Long id = super.saveOrUpdate(permission);
		String pTreePath;
		if(permission.getPid() == 0) {
			pTreePath = "0";
		} else {
			pTreePath = this.get(permission.getPid()).getTreePath();
		}
		permission.setTreePath(pTreePath + "." + Long.valueOf(id));
		return super.saveOrUpdate(permission);
	}

	public List<Permission> getPermissionsByUser(Long userId) {
		List<Permission> allPermissions = getAll();
		if(userId == 0) {
			return allPermissions;
		} else {
			List<Permission> permissions = new ArrayList<Permission>();
			List<Permission> thePermissions = mapper.getPermissionsByUser(userId);
			for(Permission permission : thePermissions) {
				for(Permission p : allPermissions) {
					if(permission.getPid().equals(p.getId())) {
						if(!permissions.contains(p)) {
							permissions.add(p);
						}
						break;
					}
				}
				permissions.add(permission);
			}
			return permissions;
		}
	}

	public List<Permission> getPermissionsByPid(Long pid) {
		Permission permission = new Permission();
		permission.setPid(pid);
		return mapper.select(permission);
	}

	public List<Permission> getPermissionsByRole(Long roleId) {
		return mapper.getPermissionsByRole(roleId);
	}

	public void saveRolePermissions(Long roleId,String permissions,Long currentUserId) {
		mapper.delPermissionsByRole(roleId);
		if(!StringUtils.isEmpty(permissions)) {
			Map map = new HashMap();
			map.put("roleId",roleId);
			map.put("creator",currentUserId);
			for(String permission:permissions.split(",")) {
				map.put("permissionId",Long.valueOf(permission));
				mapper.insertPermissionRole(map);
			}
		}
	}

}
