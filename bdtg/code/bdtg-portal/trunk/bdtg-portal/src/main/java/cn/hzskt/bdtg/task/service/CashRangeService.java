package cn.hzskt.bdtg.task.service;

import java.util.List;
import java.util.Map;

import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;

import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;
import cn.hzskt.bdtg.task.domain.CashRange;
import cn.hzskt.bdtg.task.mapper.CashRangeMapper;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  CashRangeService extends BaseService<CashRange,CashRangeMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<CashRange> query(Map<String, String> paramMap) {
        Example example = new Example(CashRange.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String name = paramMap.get("name");
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name+ "%");
        }
        example.setOrderByClause("orderNum asc");
        return mapper.selectByExample(example);
    }
}
