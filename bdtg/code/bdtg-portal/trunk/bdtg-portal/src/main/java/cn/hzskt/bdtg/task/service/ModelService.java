package cn.hzskt.bdtg.task.service;

import cn.hzskt.bdtg.task.domain.Model;
import cn.hzskt.bdtg.task.mapper.ModelMapper;
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
public class  ModelService extends BaseService<Model,ModelMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Model> query(Map<String, String> paramMap) {
        Example example = new Example(Model.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String modelId = paramMap.get("modelId");
        if (!StringUtils.isEmpty(modelId)) {
            criteria.andLike("modelId", "%" + modelId+ "%");
        }
return mapper.selectByExample(example);
    }
}
