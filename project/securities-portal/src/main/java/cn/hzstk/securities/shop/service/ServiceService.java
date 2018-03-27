package cn.hzstk.securities.shop.service;

import cn.hzstk.securities.shop.domain.Service;
import cn.hzstk.securities.shop.mapper.ServiceMapper;
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

    public List<Service> getBySI(Long shopId) {
        Example example = new Example(Service.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        if (null!=shopId) {
            criteria.andEqualTo("shopId", shopId);
        }
        return mapper.selectByExample(example);
    }
}
