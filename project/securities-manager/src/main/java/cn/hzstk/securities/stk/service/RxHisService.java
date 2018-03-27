package cn.hzstk.securities.stk.service;

import cn.hzstk.securities.stk.domain.RxHis;
import cn.hzstk.securities.stk.mapper.RxHisMapper;
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
public class  RxHisService extends BaseService<RxHis,RxHisMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<RxHis> query(Map<String, String> paramMap) {
        Example example = new Example(RxHis.class);
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
    public Long saveOrUpdate(RxHis o) {
        Example example = new Example(RxHis.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("dt", o.getDt());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<RxHis> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            //o.setId(list.get(0).getId());
            return 1l;
        }

        return super.saveOrUpdate(o);
    }
}
