package cn.hzstk.securities.stk.service;

import cn.hzstk.securities.stk.domain.SuspendedInfo;
import cn.hzstk.securities.stk.mapper.SuspendedInfoMapper;
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
public class  SuspendedInfoService extends BaseService<SuspendedInfo,SuspendedInfoMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<SuspendedInfo> query(Map<String, String> paramMap) {
        Example example = new Example(SuspendedInfo.class);
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

        example.setOrderByClause("startDate desc");
        return mapper.selectByExample(example);
    }

    /**
     * 查询日期
     * @return
     */
    public String selectMaxStartDt() {
        return mapper.selectMaxStartDt();
    }

    /**
     * 根据条件更新数据
     * @param o
     * @return
     */
    public void updateSuspendedInfo(SuspendedInfo o) {
        mapper.updateSuspendedInfo(o);
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(SuspendedInfo o) {
        Example example = new Example(SuspendedInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<SuspendedInfo> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            for (SuspendedInfo aList : list) {
                if (o.getStartDate().equals(aList.getStartDate())) return null;
            }
            updateSuspendedInfo(o);
        }

        o.setCreateDate(new Date());
        return (long)this.mapper.insert(o);
    }
}
