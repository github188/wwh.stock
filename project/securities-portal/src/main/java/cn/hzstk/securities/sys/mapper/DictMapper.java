package cn.hzstk.securities.sys.mapper;

import cn.hzstk.securities.sys.domain.Dict;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by allenwc on 15/8/12.
 */
public interface DictMapper extends Mapper<Dict>{

    @Select("select distinct(key_name) from sys_dict where valid='1'")
    public List<String> getKeyNames();

}
