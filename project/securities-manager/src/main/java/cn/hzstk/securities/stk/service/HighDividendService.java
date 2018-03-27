package cn.hzstk.securities.stk.service;

import cn.hzstk.securities.stk.domain.HighDividend;
import cn.hzstk.securities.stk.mapper.HighDividendMapper;
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
public class  HighDividendService extends BaseService<HighDividend,HighDividendMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<HighDividend> query(Map<String, String> paramMap) {
        Example example = new Example(HighDividend.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String stockCode = paramMap.get("stockCode");
        if (!StringUtils.isEmpty(stockCode)) {
            criteria.andLike("stockCode", "%" + stockCode+ "%");
        }
        String stockName = paramMap.get("stockName");
        if (!StringUtils.isEmpty(stockName)) {
            criteria.andLike("stockName", "%" + stockName+ "%");
        } else {
            criteria.andEqualTo("dividendDate", "");
            Example.Criteria criteria1 = example.or();
            criteria1.andEqualTo("valid","1");
            criteria1.andGreaterThanOrEqualTo("dividendDate", DateUtils.format(new Date()));
        }

        return mapper.selectByExample(example);
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(HighDividend o) {
        Example example = new Example(HighDividend.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("stockCode", o.getStockCode());
        criteria.andEqualTo("planDate", o.getPlanDate());

        List<HighDividend> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }
}
