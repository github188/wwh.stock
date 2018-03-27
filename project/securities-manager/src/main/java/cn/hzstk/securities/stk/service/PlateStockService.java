package cn.hzstk.securities.stk.service;

import cn.hzstk.securities.stk.domain.PlateStock;
import cn.hzstk.securities.stk.mapper.PlateStockMapper;
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
public class  PlateStockService extends BaseService<PlateStock,PlateStockMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<PlateStock> query(Map<String, String> paramMap) {
        Example example = new Example(PlateStock.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String plateCode = paramMap.get("plateCode");
        if (!StringUtils.isEmpty(plateCode)) {
            criteria.andLike("plateCode", "%" + plateCode+ "%");
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
    public Long saveOrUpdate(PlateStock o) {
        Example example = new Example(PlateStock.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("plateCode", o.getPlateCode());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<PlateStock> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }
}
