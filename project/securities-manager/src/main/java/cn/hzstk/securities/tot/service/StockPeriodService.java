package cn.hzstk.securities.tot.service;

import cn.hzstk.securities.common.constants.ParamKeys;
import cn.hzstk.securities.sys.service.ParamService;
import cn.hzstk.securities.tot.domain.StockPeriod;
import cn.hzstk.securities.tot.mapper.StockPeriodMapper;
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
@SuppressWarnings("JavaDoc")
@Component
public class  StockPeriodService extends BaseService<StockPeriod,StockPeriodMapper> {
    @Autowired
    ParamService paramService;

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<StockPeriod> query(Map<String, String> paramMap) {
        Example example = new Example(StockPeriod.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String stockCode = paramMap.get("stockCode");
        if (StringUtils.isEmpty(stockCode) || !StringUtils.isNumeric(stockCode)) {
            stockCode = paramService.getbyName(ParamKeys.RX_DEFAULT_CODE);
        }
        criteria.andLike("stockCode", "%" + stockCode+ "%");
        example.setOrderByClause("start_dt asc");

        return mapper.selectByExample(example);
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(StockPeriod o) {
        Example example = new Example(StockPeriod.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("startDt", o.getStartDt());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<StockPeriod> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }

    public void selectStatPeriod(Map<String, String> params){
        this.sqlSession.selectOne("sp_per_stat_period", params);
    }
}
