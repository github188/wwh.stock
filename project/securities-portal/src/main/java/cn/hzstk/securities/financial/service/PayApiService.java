package cn.hzstk.securities.financial.service;

import cn.hzstk.securities.financial.domain.PayApi;
import cn.hzstk.securities.financial.mapper.PayApiMapper;
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
public class  PayApiService extends BaseService<PayApi,PayApiMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<PayApi> query(Map<String, String> paramMap) {
        Example example = new Example(PayApi.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String payment = paramMap.get("payment");
        if (!StringUtils.isEmpty(payment)) {
            criteria.andLike("payment", "%" + payment+ "%");
        }
        return mapper.selectByExample(example);
    }
    
    /**
     * 查询所有的线下支付
     * @param paramMap
     * @return
     */
    public List<PayApi> getoffline(){
    	PayApi api = new PayApi();
    	api.setType("offline");
    	api.setIsopen("1");
    	return mapper.select(api);
    }
}
