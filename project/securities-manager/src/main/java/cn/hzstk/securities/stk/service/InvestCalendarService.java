package cn.hzstk.securities.stk.service;

import cn.hzstk.securities.stk.domain.InvestCalendar;
import cn.hzstk.securities.stk.mapper.InvestCalendarMapper;
import cn.hzstk.securities.util.DateUtil;
import net.ryian.commons.DateUtils;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
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
public class  InvestCalendarService extends BaseService<InvestCalendar,InvestCalendarMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<InvestCalendar> query(Map<String, String> paramMap) {
        Example example = new Example(InvestCalendar.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String dt = paramMap.get("dt");
            if (StringUtils.isEmpty(dt)) {
            dt = DateUtil.formatNextDay(DateUtils.format(new Date()));
        }
        criteria.andLike("dt", "%" + dt+ "%");
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
    public Long saveOrUpdate(InvestCalendar o) {
        Example example = new Example(InvestCalendar.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("dt", o.getDt());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<InvestCalendar> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }

    public int queryCntByDt(String dt) {
        Example example = new Example(InvestCalendar.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        if (StringUtils.isEmpty(dt)) dt = DateUtil.formatNextDay(dt);
        criteria.andEqualTo("dt", dt);

        return mapper.selectCountByExample(example);
    }
}
