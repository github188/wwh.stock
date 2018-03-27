package cn.hzstk.securities.stk.service;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.net.service.StockGroupService;
import cn.hzstk.securities.stk.domain.SelfSelect;
import cn.hzstk.securities.stk.mapper.SelfSelectMapper;
import cn.hzstk.securities.util.DigitFormat;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@SuppressWarnings({"ALL", "JavaDoc"})
@Component
public class SelfSelectService extends BaseService<SelfSelect,SelfSelectMapper> {
    private static final Logger logger = LoggerFactory.getLogger(Constant.LOG_REAL);

    @Autowired
    private StockGroupService stockGroupService;

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    @SuppressWarnings("JavaDoc")
    public List<SelfSelect> query(Map<String, String> paramMap) {
        Example example = new Example(SelfSelect.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String dt = paramMap.get("dt");
        if (StringUtils.isEmpty(dt)) dt = stockGroupService.selectDefaultCode();
        criteria.andEqualTo("dt", dt);
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
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(SelfSelect o) {
        Example example = new Example(SelfSelect.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("stockCode", o.getStockCode());
        criteria.andEqualTo("dt", o.getDt());

        List<SelfSelect> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
            if (StringUtils.isNotEmpty(o.getTurnOver())) {
                o.setChangeRate(DigitFormat.calcWidth(o.getPreClose(), o.getPrice(), list.get(0).getPrice()));
                logger.info(o.getStockCode()+"|"+o.getPrice()+"|"+o.getAveragePrice()+"|"+o.getChangeWidth()+"|"+o.getChangeAmount()+"|"+o.getAmount()+"|"+o.getChangeRate());
            }
        }

        return super.saveOrUpdate(o);
    }
}
