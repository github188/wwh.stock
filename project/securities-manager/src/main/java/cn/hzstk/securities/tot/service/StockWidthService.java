package cn.hzstk.securities.tot.service;

import cn.hzstk.securities.tot.domain.StockWidth;
import cn.hzstk.securities.tot.mapper.StockWidthMapper;
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
public class  StockWidthService extends BaseService<StockWidth,StockWidthMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<StockWidth> query(Map<String, String> paramMap) {
        Example example = new Example(StockWidth.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String stockCode = paramMap.get("stockCode");
        if (!StringUtils.isEmpty(stockCode)) {
            criteria.andLike("stockCode", "%" + stockCode+ "%");
        }
        String stockName = paramMap.get("stockName");
        if (!StringUtils.isEmpty(stockName)) {
            criteria.andLike("stockName", "%" + stockName+ "%");
        }

        return mapper.selectByExample(example);
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(StockWidth o) {
        Example example = new Example(StockWidth.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("stockCode", o.getStockCode());
        //criteria.andEqualTo("startDt", o.getStartDt());

        List<StockWidth> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
            o.setUpWidth1(list.get(0).getUpWidth());
            o.setUpWidth2(list.get(0).getUpWidth1());
            o.setUpWidth3(list.get(0).getUpWidth2());
        }

        return super.saveOrUpdate(o);
    }
}
