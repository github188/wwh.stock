package cn.hzstk.securities.stockeast.service;

import cn.hzstk.securities.stockeast.domain.CoreInfo;
import cn.hzstk.securities.stockeast.mapper.CoreInfoMapper;
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
public class CoreInfoService extends BaseService<CoreInfo,CoreInfoMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<CoreInfo> query(Map<String, String> paramMap) {
        Example example = new Example(CoreInfo.class);
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

        return mapper.selectByExample(example);
    }

    /**
     * 查询数据
     * @return
     */
    public String selectMaxDt(String code) {
        return mapper.selectMaxDt(code);
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(CoreInfo o) {
        Example example = new Example(CoreInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("dt", o.getDt());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<CoreInfo> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }
}
