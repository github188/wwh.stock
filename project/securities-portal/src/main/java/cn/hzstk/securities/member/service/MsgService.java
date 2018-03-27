package cn.hzstk.securities.member.service;


import cn.hzstk.securities.common.constants.DictKeys;
import cn.hzstk.securities.member.domain.Msg;
import cn.hzstk.securities.member.mapper.MsgMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class MsgService extends BaseService<Msg,MsgMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    public List<Msg> query(Map<String, String> paramMap) {
        Example example = new Example(Msg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String pid = paramMap.get("pid");
        if (!StringUtils.isEmpty(pid)) {
            criteria.andLike("pid", "%" + pid+ "%");
        }
            return mapper.selectByExample(example);
    }

    public PageInfo<Msg> queryPaged1(Map<String, String> paramMap) {
        String rowsStr = paramMap.get("pageSize") == null?null:(String)paramMap.get("pageSize");
        String pageStr = paramMap.get("page") == null?null:(String)paramMap.get("page");
        int page = 1;
        int rows = 10;

        try {
            page = pageStr != null?Integer.valueOf(pageStr).intValue():page;
            rows = rowsStr != null?Integer.valueOf(rowsStr).intValue():rows;
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        PageHelper.startPage(page, rows);
        Example example = new Example(Msg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String viewStatus = paramMap.get("viewStatus");
        if (!StringUtils.isEmpty(viewStatus)) {
            if(!viewStatus.equals("0")){
                criteria.andEqualTo("viewStatus",viewStatus);
            }
        }
        String type = paramMap.get("type");
        if (!StringUtils.isEmpty(type)) {
            criteria.andEqualTo("type", type);
        }
        String jobType = paramMap.get("jobType");
        if (!StringUtils.isEmpty(jobType)) {
            criteria.andEqualTo("jobType", jobType);
        }
        String userid = paramMap.get("userid");
        if (!StringUtils.isEmpty(userid)) {
            criteria.andEqualTo("toUid", userid);
        }
        List<Msg> l = new ArrayList<Msg>();
        l = mapper.selectByExample(example);

        return new PageInfo<Msg>(l);
    }

    /**
     * 获取用户未读消息条数
     * @param uid
     * @return
     */
    public Integer getUnreadCnt(Long uid) {
        Msg msg = new Msg();
        msg.setValid(1);
        msg.setViewStatus(1);
        msg.setToUid(uid);
        int cnt = mapper.selectCount(msg);
        return cnt == 0 ? null : cnt;
    }

}
