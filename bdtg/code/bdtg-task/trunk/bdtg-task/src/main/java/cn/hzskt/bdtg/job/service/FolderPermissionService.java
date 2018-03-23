package cn.hzskt.bdtg.job.service;

import cn.hzskt.bdtg.job.domain.FolderPermission;
import cn.hzskt.bdtg.job.mapper.FolderPermissionMapper;
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
public class  FolderPermissionService extends BaseService<FolderPermission,FolderPermissionMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<FolderPermission> query(Map<String, String> paramMap) {
        Example example = new Example(FolderPermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String uid = paramMap.get("uid");
        String tid = paramMap.get("tid");
        String except = paramMap.get("except");
        if (!StringUtils.isEmpty(uid)) {
            criteria.andEqualTo("uid", uid);
        }
        if (!StringUtils.isEmpty(tid)) {
            criteria.andEqualTo("tid", tid);
        }
        if (!StringUtils.isEmpty(except)) {
            criteria.andNotEqualTo("id", except);
        }
        return mapper.selectByExample(example);
    }
}
