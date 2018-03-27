package cn.hzstk.securities.east.service;

import cn.hzstk.securities.east.domain.SelectList;
import cn.hzstk.securities.east.mapper.SelectListMapper;
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
public class  SelectListService {
    @Autowired
    protected SelectListMapper mapper;

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<SelectList> query(Map<String, String> paramMap) {
        Example example = new Example(SelectList.class);
        Example.Criteria criteria = example.createCriteria();
        String listCont = paramMap.get("listCont");
        if (!StringUtils.isEmpty(listCont)) {
            criteria.andEqualTo("listCont", listCont);
        }

        return mapper.selectByExample(example);
    }
}
