package cn.hzstk.securities.net.service;

import cn.hzstk.securities.net.domain.PerformanceForecast;
import cn.hzstk.securities.net.mapper.PerformanceForecastMapper;
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
public class  PerformanceForecastService extends BaseService<PerformanceForecast,PerformanceForecastMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<PerformanceForecast> query(Map<String, String> paramMap) {
        Example example = new Example(PerformanceForecast.class);
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

        example.setOrderByClause("reportDt desc");
        return mapper.selectByExample(example);
    }

    /**
     * 查询日期
     * @return
     */
    public String selectMaxReportDt() {
        return mapper.selectMaxReportDt();
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(PerformanceForecast o) {
        Example example = new Example(PerformanceForecast.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("dt", o.getDt());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<PerformanceForecast> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }
}
