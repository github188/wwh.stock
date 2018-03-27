package cn.hzstk.securities.stk.service;

import cn.hzstk.securities.stk.domain.StocksInfo;
import cn.hzstk.securities.stk.mapper.StocksInfoMapper;
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
public class  StocksInfoService extends BaseService<StocksInfo,StocksInfoMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<StocksInfo> query(Map<String, String> paramMap) {
        Example example = new Example(StocksInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String name = paramMap.get("name");
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name+ "%");
        }
        String code = paramMap.get("code");
        if (!StringUtils.isEmpty(code)) {
            criteria.andLike("code", "%" + code+ "%");
        }
        String stype = paramMap.get("stype");
        if (!StringUtils.isEmpty(stype)) {
            criteria.andEqualTo("stype", stype);
        }

        return mapper.selectByExample(example);
    }

    /**
     * 是否下市
     * @return
     */
    public boolean isDown(String code) {
        Example example = new Example(StocksInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("status",2);
        criteria.andEqualTo("code", code);

        List<StocksInfo> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            return true;
        }

        return false;
    }

    /**
    * 根据条件保存数据
    * @param o
    * @return
    */
    public Long saveOrUpdate(StocksInfo o) {
        Example example = new Example(StocksInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("code", o.getCode());
        criteria.andEqualTo("stype", o.getStype());

        List<StocksInfo> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            //o.setId(list.get(0).getId());
            return 0l;
        }

        return super.saveOrUpdate(o);
    }
}
