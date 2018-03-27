package cn.hzstk.securities.net.service;

import cn.hzstk.securities.net.domain.ProfitShares;
import cn.hzstk.securities.net.mapper.ProfitSharesMapper;
import cn.hzstk.securities.util.DateUtil;
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
public class  ProfitSharesService extends BaseService<ProfitShares,ProfitSharesMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<ProfitShares> query(Map<String, String> paramMap) {
        Example example = new Example(ProfitShares.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String dt = paramMap.get("dt");
        if (StringUtils.isNotEmpty(dt)) {
            criteria.andEqualTo("dt", dt);
        } else {
            criteria.andEqualTo("dt", DateUtil.getSemiYear());
        }
        String stockCode = paramMap.get("stockCode");
        if (StringUtils.isNotEmpty(stockCode)) {
            criteria.andLike("stockCode", "%" + stockCode+ "%");
        }

        example.setOrderByClause("turn_scale+0 desc,registerDate desc");
        return mapper.selectByExample(example);
    }

    /**
     * 查询日期
     * @return
     */
    public String selectMaxDt() {
        return mapper.selectMaxDt();
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(ProfitShares o) {
        Example example = new Example(ProfitShares.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("dt", o.getDt());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<ProfitShares> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }
}
