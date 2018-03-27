package cn.hzstk.securities.east.service;

import cn.hzstk.securities.east.domain.SelectDetail;
import cn.hzstk.securities.east.mapper.SelectDetailMapper;
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
public class  SelectDetailService {
    @Autowired
    protected SelectDetailMapper mapper;

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<SelectDetail> query(Map<String, String> paramMap) {
        Example example = new Example(SelectDetail.class);
        Example.Criteria criteria = example.createCriteria();
        String ul = paramMap.get("ul");
        if (!StringUtils.isEmpty(ul)) {
            criteria.andLike("ul", ul + "%");
        }

        return mapper.selectByExample(example);
    }
}
