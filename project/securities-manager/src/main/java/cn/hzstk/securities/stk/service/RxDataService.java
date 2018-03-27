package cn.hzstk.securities.stk.service;

import cn.hzstk.securities.stk.domain.RxData;
import cn.hzstk.securities.stk.mapper.RxDataMapper;
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
public class  RxDataService extends BaseService<RxData,RxDataMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<RxData> query(Map<String, String> paramMap) {
        Example example = new Example(RxData.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String stockCode = paramMap.get("stockCode");
        if (!StringUtils.isEmpty(stockCode) && StringUtils.isNumeric(stockCode)) {
            criteria.andLike("stockCode", "%" + stockCode+ "%");
        }
        String stockName = paramMap.get("stockName");
        if (!StringUtils.isEmpty(stockName)) {
            criteria.andLike("stockName", "%" + stockName+ "%");
        }
        example.setOrderByClause("dt desc");

        return mapper.selectByExample(example);
    }

    /**
     * 查询编码
     * @return
     */
    public List<String> getStockInfo(String code) {
        return mapper.selectStockInfo(code);
    }

    /**
     * 查询编码
     * @return
     */
    public List<String> getAllStockCode() {
        return mapper.getAllStockCode();
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(RxData o) {
        Example example = new Example(RxData.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("dt", o.getDt());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<RxData> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }
}
