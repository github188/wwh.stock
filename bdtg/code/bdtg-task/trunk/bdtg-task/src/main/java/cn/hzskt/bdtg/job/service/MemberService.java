package cn.hzskt.bdtg.job.service;

import cn.hzskt.bdtg.common.util.WebUtil;
import cn.hzskt.bdtg.job.domain.AuMember;
import cn.hzskt.bdtg.job.domain.Member;
import cn.hzskt.bdtg.job.mapper.MemberMapper;
import cn.hzskt.bdtg.member.domain.AuthSpace;
import cn.hzskt.bdtg.member.mapper.AuthSpaceMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import net.ryian.orm.service.support.datasource.DataSourceContextHolder;
import net.ryian.orm.service.support.datasource.annotation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tk.mybatis.mapper.entity.Example;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @description:
* @author: autoCode
* @history:
*/
@Component
public class  MemberService extends BaseService<Member,MemberMapper> {

    /**
    * 根据条件查询分页
    * @param paramMap
    * @return
    */
    @Autowired
    AuthSpaceMapper authSpaceMapper;
    public List<Member> query(Map<String, String> paramMap) {
        Example example = new Example(Member.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String userId = paramMap.get("userId");
        if (!StringUtils.isEmpty(userId)) {
            criteria.andLike("userId", "%" + userId+ "%");
        }
        String type = paramMap.get("type");
        if (!StringUtils.isEmpty(type)) {
            criteria.andEqualTo("type",type);
        }
        return mapper.selectByExample(example);
    }

    public Member getMemberByTaskAndUser(Long tid,Long uid) {
        Member member = new Member();
        member.setValid(1);
        member.setUserId(uid);
        member.setTid(tid);
        List<Member> members = mapper.select(member);
        if(members != null && !members.isEmpty())
            return members.get(0);
        else
            return null;
    }

    public List<AuthSpace> getAuthspace(Map<String, String> paramMap,List<Object> uidlist) {
        DataSourceContextHolder.setCustomerType("manager_ds");
        List<AuthSpace> list = new ArrayList<>();
        if(uidlist.size()>0){
            Example example = new Example(AuthSpace.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("valid", "1");
            String userName = paramMap.get("userName");
            if (!StringUtils.isEmpty(userName)) {
                criteria.andLike("userName", "%" + userName + "%");
            }
            criteria.andIn("userId",uidlist);
            list =  authSpaceMapper.selectByExample(example);
            DataSourceContextHolder.clearCustomerType();
        }
        return list;
    }

    public List<Member> getMember(Map<String, String> paramMap) {
        Example example = new Example(Member.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String type=paramMap.get("type");
        if(!StringUtils.isEmpty(type)){
            if(paramMap.get("type").equals("1")){
                criteria.andNotEqualTo("type","2");
                criteria.andNotEqualTo("type","3");
            }else{
                criteria.andEqualTo("type", paramMap.get("type"));
            }
        }
        criteria.andEqualTo("tid", paramMap.get("tid"));
        return mapper.selectByExample(example);
    }

    public PageInfo<AuMember> getAumemberList(Map<String, String> paramMap) {
        String rowsStr = paramMap.get("rows") == null?null:(String)paramMap.get("rows");
        String pageStr = paramMap.get("page") == null?null:(String)paramMap.get("page");
        int page = 1;
        int rows = 10;

        try {
            page = pageStr != null?Integer.valueOf(pageStr).intValue():page;
            rows = rowsStr != null?Integer.valueOf(rowsStr).intValue():rows;
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        List<Member> memberlist =  getMember(paramMap);
        List<Object> uidlist = new ArrayList<Object>();
        for(int mint =0;mint<memberlist.size();mint++){
            uidlist.add(memberlist.get(mint).getUserId());
        }
        List<AuthSpace> authlist = getAuthspace(paramMap, uidlist);
        List<AuMember> list = new ArrayList<AuMember>();
        for (int i=0;i<memberlist.size();i++){
            AuMember auMember = new AuMember();
            for(int j=0;j<authlist.size();j++){
                if(memberlist.get(i).getUserId()==authlist.get(j).getUserId()){
                    //ID
                    auMember.setId(memberlist.get(i).getId());
                    //用户ID
                    auMember.setUserId(memberlist.get(i).getUserId());
                    //用户名
                    auMember.setUserName(authlist.get(j).getUserName());
                    //姓名或公司名称
                    auMember.setName(authlist.get(j).getName());
                    //所属公司
                    auMember.setCompany(authlist.get(0).getCompany());
                    //认证状态
                    auMember.setAuthStatus(authlist.get(j).getAuthStatus());
                    //用户类型
                    auMember.setUserType(authlist.get(j).getUserType());
                    //手机
                    auMember.setMobile(authlist.get(j).getMobile());
                    //邮箱
                    auMember.setEmail(authlist.get(j).getEmail());
                    //QQ
                    auMember.setQq(authlist.get(j).getQq());
                    //类型
                    auMember.setMbType(memberlist.get(i).getType());
                    //专业
                    auMember.setMajor(memberlist.get(i).getMajor());
                    list.add(auMember);
                    break;
                }
            }

        }
        PageInfo<AuMember> aupage = new PageInfo<AuMember>();
        aupage.setList(list);
        return aupage;
    }

    
    public List<Member> getMemberBytid(Long tid) {
        Member member = new Member();
        member.setValid(1);
        member.setTid(tid);
        return mapper.select(member);
    }
    
    public Member getMemberByUser(Long uid,Long tid) {
        Member member = new Member();
        member.setValid(1);
        member.setUserId(uid);
        member.setTid(tid);
        member.setValid(1);
        List<Member> members = mapper.select(member);
        if(members != null && !members.isEmpty()) {
            return members.get(0);
        } else
            return null;
    }
    
    public int countbymajor(String major,Long tid) {
    	 Member member = new Member();
         member.setMajor(Long.parseLong(major));
         member.setTid(tid);
         member.setValid(1);
        return mapper.selectCount(member);
    }
    
    public List<String> getJobMemberIds(Long taskId){
    	Example example = new Example(Member.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        criteria.andEqualTo("tid", taskId);
        List array = Arrays.asList("2", "3");
        criteria.andNotIn("type", array);
        List<String> items = new ArrayList<String>();
        for(Member mb : mapper.selectByExample(example)){
        	items.add(String.valueOf(mb.getUserId()));
        }
    	return items;
    }
    
}
