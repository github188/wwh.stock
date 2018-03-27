package cn.hzstk.securities.stk.service;

import cn.hzstk.securities.stk.domain.Tiger;
import cn.hzstk.securities.stk.mapper.TigerMapper;
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
public class  TigerService extends BaseService<Tiger,TigerMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Tiger> query(Map<String, String> paramMap) {
        Example example = new Example(Tiger.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String dt = paramMap.get("dt");
        if (!StringUtils.isEmpty(dt)) {
            criteria.andEqualTo("dt", dt);
        } else {
            criteria.andEqualTo("dt", DateUtils.format(new Date()));
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
    public Long saveOrUpdate(Tiger o) {
        Example example = new Example(Tiger.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("dt", o.getDt());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<Tiger> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }

    public int queryCntByDt(String dt) {
        Example example = new Example(Tiger.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        if (StringUtils.isEmpty(dt)) dt = DateUtils.format(new Date());
        criteria.andEqualTo("dt", dt);

        return mapper.selectCountByExample(example);
    }
}
