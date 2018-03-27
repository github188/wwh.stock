package cn.hzstk.securities.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import tk.mybatis.mapper.common.Mapper;
import cn.hzstk.securities.manage.domain.Content;

/**
 * Created by allenwc on 15/8/12.
 */
public interface ContentMapper extends Mapper<Content> {

    @Select("select distinct(key_name) from sys_dict where valid='1'")
    public List<String> getKeyNames();

}
