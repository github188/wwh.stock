package cn.hzstk.securities.stk.service;

import cn.hzstk.securities.stk.domain.HqDetails;
import cn.hzstk.securities.stk.mapper.HqDetailsMapper;
import net.ryian.commons.DateUtils;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
@Component
public class  HqDetailsService extends BaseService<HqDetails,HqDetailsMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    @Cacheable(value = "myCache", key = "'query_HqDetails'")
    public List<HqDetails> query(Map<String, String> paramMap) {
        Example example = new Example(HqDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String dt = paramMap.get("dt");
        if (!StringUtils.isEmpty(dt)) {
            criteria.andEqualTo("dt", dt);
        } else {
            criteria.andEqualTo("dt", DateUtils.format(new Date()));
        }
        String stockCode = paramMap.get("stockCode");
        if (!StringUtils.isEmpty(stockCode) && StringUtils.isNumeric(stockCode)) {
            criteria.andLike("stockCode", "%" + stockCode+ "%");
        }
        String sort = paramMap.get("sort");
        if (!StringUtils.isEmpty(sort)) {
            example.setOrderByClause(sort + "+0 " + paramMap.get("order"));
        }

        return mapper.selectByExample(example);
    }

    /**
     * 查询编码
     * @return
     */
    public String getStockCode(String name) {
        return mapper.getStockCode(name).get(0);
    }

    /**
     * 查询日期
     * @return
     */
    public String selectMinDt() {
        return mapper.selectMinDt();
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(HqDetails o) {
        Example example = new Example(HqDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("dt", o.getDt());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<HqDetails> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }

    public int queryCntByDt(String dt) {
        Example example = new Example(HqDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        if (StringUtils.isEmpty(dt)) dt = DateUtils.format(new Date());
        criteria.andEqualTo("dt", dt);

        return mapper.selectCountByExample(example);
    }
}
