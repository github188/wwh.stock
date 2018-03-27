package cn.hzstk.securities.stockeast.service;

import cn.hzstk.securities.stockeast.domain.SelfSelect;
import cn.hzstk.securities.stockeast.mapper.SelfSelectMapper;
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
@SuppressWarnings({"ALL", "JavaDoc"})
@Component
public class SelfSelectService extends BaseService<SelfSelect,SelfSelectMapper> {

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
        if (!StringUtils.isEmpty(dt)) {
            criteria.andEqualTo("dt", dt);
        }
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
     * 查询编码
     * @return
     */
    public List<String> selectStockCode(String dt) {
        return mapper.selectStockCode(dt);
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
        criteria.andEqualTo("dt", o.getDt());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<SelfSelect> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) return 1l;

        return super.saveOrUpdate(o);
    }

    /**
    * 根据条件保存数据
    * @param g
    * @param sc
    * @param f
    * @return
    */
    public int saveOrUpdate(String g, String sc, String f) {
        Example example = new Example(SelfSelect.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("dt", g);
        String[] arr = sc.split("\\|");
        criteria.andEqualTo("stockCode", arr[0]);

        List<SelfSelect> list = mapper.selectByExample(example);
        if ("as".equals(f)) {
            if (list != null && list.size() > 0) return -1;
            SelfSelect o = new SelfSelect();
            o.setDt(g);
            o.setStockCode(arr[0]);
            return mapper.insert(o);
        } else {
            if (list != null && list.size() > 0) return mapper.delete(list.get(0));
        }
        return -1;
    }

    public void selectSummary(Map<String, String> params){
        this.sqlSession.selectOne("sp_select_summary", params);
    }
}
