package cn.hzstk.securities.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;
import cn.hzstk.securities.manage.domain.Category;

/**
 * Created by allenwc on 15/8/12.
 */
public interface CategoryMapper extends Mapper<Category>{

    @Select("select distinct(key_name) from sys_dict where valid='1'")
    public List<String> getKeyNames();

}
