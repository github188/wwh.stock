package cn.hzstk.securities.east.service;

import cn.hzstk.securities.east.domain.SelectFirst;
import cn.hzstk.securities.east.mapper.SelectFirstMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class  SelectFirstService {
    @Autowired
    protected SelectFirstMapper mapper;

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<SelectFirst> query(Map<String, String> paramMap) {
        Example example = new Example(SelectFirst.class);
        Example.Criteria criteria = example.createCriteria();
        String name = paramMap.get("name");
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name+ "%");
        }
        String focus = paramMap.get("focus");
        if (!StringUtils.isEmpty(focus)) {
            criteria.andLike("focus", "%" + focus+ "%");
        }

        return mapper.selectByExample(example);
    }
}
