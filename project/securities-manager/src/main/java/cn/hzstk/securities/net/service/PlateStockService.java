package cn.hzstk.securities.net.service;

import cn.hzstk.securities.net.domain.PlateStock;
import cn.hzstk.securities.net.mapper.PlateStockMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
* @description:
* @author: autoCode
* @history:
*/
@Controller("net-plate-stock-service")
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
        String plateType = paramMap.get("plateType");
        if (!StringUtils.isEmpty(plateType)) {
            criteria.andLike("plateType", "%" + plateType+ "%");
        }
        String plateCode = paramMap.get("plateCode");
        if (!StringUtils.isEmpty(plateCode)) {
            criteria.andLike("plateCode", "%" + plateCode+ "%");
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
        criteria.andEqualTo("plateType", o.getPlateType());
        criteria.andEqualTo("plateCode", o.getPlateCode());
        criteria.andEqualTo("stockCode", o.getStockCode());

        List<PlateStock> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
            o.setCockNum(list.get(0).getCockNum()+o.getCockNum());
        }

        return super.saveOrUpdate(o);
    }
}
