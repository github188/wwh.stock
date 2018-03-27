package cn.hzstk.securities.net.service;

import cn.hzstk.securities.net.domain.FundDetails;
import cn.hzstk.securities.net.mapper.FundDetailsMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
* @description:
* @author: autoCode
* @history:
*/
@Controller("net-fund-details-service")
@Component
public class  FundDetailsService extends BaseService<FundDetails,FundDetailsMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<FundDetails> query(Map<String, String> paramMap) {
        Example example = new Example(FundDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String dt = paramMap.get("dt");
        if (!StringUtils.isEmpty(dt)) {
            criteria.andLike("dt", "%" + dt+ "%");
        }
        String stockCode = paramMap.get("stockCode");
        if (!StringUtils.isEmpty(stockCode)) {
            criteria.andLike("stockCode", "%" + stockCode+ "%");
        }

        return mapper.selectByExample(example);
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(FundDetails o) {
        Example example = new Example(FundDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("dt", o.getDt());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<FundDetails> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }
}
