package net.ryian.example.sys.mapper;

import net.ryian.example.sys.domain.Role;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by allenwc on 15/9/10.
 */
public interface RoleMapper extends Mapper<Role> {

    @Select("select * from sys_role where valid='1' and id in (select role_id from sys_r_user_role where valid='1' and user_id=#{value})")
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "code", column = "code"),
            @Result(property = "note", column = "note")
    })
    public List<Role> getRolesByUser(Long userId);

    @Delete("DELETE FROM sys_r_user_role where user_id=#{userId}")
    public void deleteByUser(Long userId);

    @Insert("INSERT INTO sys_r_user_role(user_id,role_id,CREATE_DATE,CREATOR) VALUES (#{userId},#{roleId},NOW(),#{creator})")
    public void insertRoleUser(Map map);


}
