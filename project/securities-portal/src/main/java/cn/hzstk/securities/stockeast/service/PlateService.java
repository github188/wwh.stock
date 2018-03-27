package cn.hzstk.securities.stockeast.service;

import cn.hzstk.securities.stockeast.domain.Plate;
import cn.hzstk.securities.stockeast.mapper.PlateMapper;
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
@Controller("net-plate-service")
@Component
public class  PlateService extends BaseService<Plate,PlateMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Plate> query(Map<String, String> paramMap) {
        Example example = new Example(Plate.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String dt = paramMap.get("dt");
        if (!StringUtils.isEmpty(dt)) {
            criteria.andLike("dt", "%" + dt+ "%");
        } else {
            example.setOrderByClause("dt desc");
        }
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
    public Long saveOrUpdate(Plate o) {
        Example example = new Example(Plate.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("dt", o.getDt());
        criteria.andEqualTo("plateType", o.getPlateType());
        criteria.andEqualTo("plateCode", o.getPlateCode());

        List<Plate> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            o.setId(list.get(0).getId());
        }

        return super.saveOrUpdate(o);
    }
}
