package cn.hzskt.bdtg.job.mapper;

import cn.hzskt.bdtg.job.domain.Folder;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @description:
* @author: autoCode
* @history:
*/
public interface FolderMapper extends Mapper<Folder>{
    @Select("select id,code from job_folder where valid='1' and tid = #{param1} and name=#{param2} and pcode=#{param3}")
    public List<Folder> getId(Long tid,String name,String pcode);
    @Select("select id from job_folder where valid='1' and tid = #{param1}  and pcode=#{param2}")
    public List<Folder> getsonId(Long tid,String pcode);
}
