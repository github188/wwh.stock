package cn.hzstk.securities.financial.service;

import cn.hzstk.securities.financial.domain.Finance;
import cn.hzstk.securities.financial.mapper.FinanceMapper;
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
@SuppressWarnings("JavaDoc")
@Component
public class  FinanceService extends BaseService<Finance,FinanceMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Finance> query(Map<String, String> paramMap) {
        Example example = new Example(Finance.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String username = paramMap.get("username");
        String id = paramMap.get("intFinaId");
        String finaType = paramMap.get("finaType");
        String objType = paramMap.get("objType");
        if (!StringUtils.isEmpty(username)) {
            criteria.andEqualTo("username", username);
        }
        if (!StringUtils.isEmpty(finaType)) {
            criteria.andEqualTo("finaType", finaType);
        }
        if (!StringUtils.isEmpty(id)) {
            criteria.andEqualTo("id", id);
        }
        if (!StringUtils.isEmpty(objType)) {
            criteria.andEqualTo("objType", objType);
        }
        example.setOrderByClause("finaTime Desc");
        return mapper.selectByExample(example);
    }
}
