package cn.hzskt.bdtg.job.service;

import cn.hzskt.bdtg.job.domain.Folder;
import cn.hzskt.bdtg.job.mapper.FolderMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  FolderService extends BaseService<Folder,FolderMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Folder> query(Map<String, String> paramMap) {
        Example example = new Example(Folder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String tid = paramMap.get("tid");
        if (!StringUtils.isEmpty(tid)) {
            criteria.andEqualTo("tid",tid);
        }
        String name = paramMap.get("name");
        if (!StringUtils.isEmpty(name)) {
            criteria.andEqualTo("name",name);
        }
        String pcode = paramMap.get("pcode");
        if (!StringUtils.isEmpty(pcode)) {
            criteria.andEqualTo("pcode",pcode);
        }
        String type = paramMap.get("type");
        if (!StringUtils.isEmpty(type)) {
            criteria.andEqualTo("type",type);
        }
        return mapper.selectByExample(example);
    }
}
