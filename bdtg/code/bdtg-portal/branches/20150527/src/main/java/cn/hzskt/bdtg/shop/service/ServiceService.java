package cn.hzskt.bdtg.shop.service;

import cn.hzskt.bdtg.shop.domain.Service;
import cn.hzskt.bdtg.shop.mapper.ServiceMapper;
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
public class  ServiceService extends BaseService<Service,ServiceMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Service> query(Map<String, String> paramMap) {
        Example example = new Example(Service.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String serviceId = paramMap.get("serviceId");
        if (!StringUtils.isEmpty(serviceId)) {
            criteria.andLike("serviceId", "%" + serviceId+ "%");
        }
return mapper.selectByExample(example);
    }
}
