package cn.hzstk.securities.shop.service;

import cn.hzstk.securities.shop.domain.Shop;
import cn.hzstk.securities.shop.mapper.ShopMapper;
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
public class  ShopService extends BaseService<Shop,ShopMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Shop> query(Map<String, String> paramMap) {
        Example example = new Example(Shop.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String shopId = paramMap.get("shopId");
        if (!StringUtils.isEmpty(shopId)) {
            criteria.andLike("shopId", "%" + shopId+ "%");
        }
return mapper.selectByExample(example);
    }
    public List<Shop> getByUid(long id) {
        Example example = new Example(Shop.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid",id);
        return mapper.selectByExample(example);
    }
}
