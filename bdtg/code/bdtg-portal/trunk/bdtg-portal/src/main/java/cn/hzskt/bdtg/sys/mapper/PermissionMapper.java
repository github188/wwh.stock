package cn.hzskt.bdtg.sys.mapper;

import cn.hzskt.bdtg.sys.domain.Permission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by allenwc on 15/9/10.
 */
public interface PermissionMapper extends Mapper<Permission> {

    @Select("SELECT * FROM sys_permission where id in (select permission_id from sys_r_role_permission where valid=1 and role_id in (select role_id from sys_r_user_role where valid=1 and user_id=#{value}))")
    public List<Permission> getPermissionsByUser(Long userId);

    @Select("select * from sys_permission where id in (select permission_id from sys_r_role_permission where valid=1 and role_id=#{value})")
    public List<Permission> getPermissionsByRole(Long roleId);

    @Delete("DELETE FROM sys_r_role_permission WHERE role_id = #{value}")
    public void delPermissionsByRole(Long roleId);

    @Insert("insert into sys_r_role_permission(role_id,permission_id,CREATE_DATE,CREATOR)values(#{roleId},#{permissionId},now(),#{creator})")
    public void insertPermissionRole(Map map);

}
