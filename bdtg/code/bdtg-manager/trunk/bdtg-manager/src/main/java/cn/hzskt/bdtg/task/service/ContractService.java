package cn.hzskt.bdtg.task.service;

import cn.hzskt.bdtg.task.domain.Contract;
import cn.hzskt.bdtg.task.mapper.ContractMapper;
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
public class  ContractService extends BaseService<Contract,ContractMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Contract> query(Map<String, String> paramMap) {
        Example example = new Example(Contract.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String tkId = paramMap.get("tkId");
        if (!StringUtils.isEmpty(tkId)) {
            criteria.andLike("tkId", "%" + tkId+ "%");
        }
        
        String Status = paramMap.get("Status");
        if (!StringUtils.isEmpty(Status)) {
            criteria.andEqualTo("pyStatus", Status);
        }
        String tktitle = paramMap.get("tktitle");
        if (!StringUtils.isEmpty(tktitle)) {
            criteria.andLike("taskTitle", "%" + tktitle+ "%");
        }
        
        example.setOrderByClause("cash_time desc");
        return mapper.selectByExample(example);
    }
}
