package cn.hzstk.securities.stockeast.service;

import cn.hzstk.securities.stockeast.domain.StockGroup;
import cn.hzstk.securities.stockeast.mapper.SelfSelectMapper;
import cn.hzstk.securities.stockeast.mapper.StockGroupMapper;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
* @description:
* @author: autoCode
* @history:
*/
@SuppressWarnings("JavaDoc")
@Component
public class StockGroupService extends BaseService<StockGroup,StockGroupMapper> {
    @Autowired
    private SelfSelectMapper smapper;

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<StockGroup> query(Map<String, String> paramMap) {
        Example example = new Example(StockGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String groupCode = paramMap.get("groupCode");
        if (!StringUtils.isEmpty(groupCode)) {
            criteria.andLike("groupCode", "%" + groupCode+ "%");
        }
        String groupName = paramMap.get("groupName");
        if (!StringUtils.isEmpty(groupName)) {
            criteria.andLike("groupName", "%" + groupName+ "%");
        }
        String groupFlag = paramMap.get("groupFlag");
        if (!StringUtils.isEmpty(groupFlag)) {
            criteria.andEqualTo("groupFlag", groupFlag);
        }

        return mapper.selectByExample(example);
    }

    public String selectDefaultCode() {
        String groupCode = "70769634";
        Example example = new Example(StockGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("groupFlag","0");

        List<StockGroup> list = mapper.selectByExample(example);
        if (list == null || list.size() == 0) return groupCode;
        return list.get(0).getGroupCode();
    }

    public String selectYjfp() {
        Example example = new Example(StockGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("groupName", "分红送配");
        List<StockGroup> list = mapper.selectByExample(example);
        if(list != null && list.size() > 0) {
            return list.get(0).getGroupCode();
        }

        String groupCode = mapper.selectMaxCode();
        if (StringUtils.isEmpty(groupCode)) groupCode = selectDefaultCode();
        StockGroup o = new StockGroup();
        o.setGroupCode(String.valueOf(Long.valueOf(groupCode) + 1));
        o.setGroupName("分红送配");
        o.setGroupFlag("1");
        mapper.insert(o);

        return o.getGroupCode();
    }

    /**
    * 根据条件保存数据
    * @param g
    * @param gn
    * @return
    */
    public String saveOrUpdate(String g, String gn) {
		StockGroup o = new StockGroup();
		Example example = new Example(StockGroup.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("valid","1");
        if (!StringUtils.isEmpty(g)) {
			criteria.andEqualTo("groupCode", g);
		} else {
			criteria.andEqualTo("groupCode", gn);
		}

		List<StockGroup> list = mapper.selectByExample(example);
        if (!StringUtils.isEmpty(g)) {
			if(list == null || list.size() == 0) {
				return "-1";
			} else {
				o.setId(list.get(0).getId());
				if (!StringUtils.isEmpty(gn)) {
					o.setGroupName(gn);
					mapper.updateByPrimaryKeySelective(o);
				} else {
                    mapper.delete(o);
                    smapper.deleteByGroup(g);
				}
				return list.get(0).getGroupCode();
			}
        }

		if(list != null && list.size() > 0) {
			return "-1";
		}
		String groupCode = mapper.selectMaxCode();
		if (StringUtils.isEmpty(groupCode)) groupCode = selectDefaultCode();
        o.setGroupCode(String.valueOf(Long.valueOf(groupCode) + 1));
		o.setGroupName(gn);
        o.setGroupFlag("1");

        mapper.insert(o);
        return o.getGroupCode();
    }
}
