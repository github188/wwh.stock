package cn.hzskt.bdtg.job.service;

import cn.hzskt.bdtg.job.domain.Member;
import cn.hzskt.bdtg.job.mapper.MemberMapper;
import cn.hzskt.bdtg.task.domain.Task;
import cn.hzskt.bdtg.task.mapper.TaskMapper;
import com.github.pagehelper.PageInfo;
import net.ryian.commons.StringUtils;
import net.ryian.orm.service.BaseService;
import net.ryian.orm.service.support.datasource.DataSourceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
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
public class  MemberService extends BaseService<Member,MemberMapper> {

    @Autowired
    TaskMapper taskMapper;
    /**
    * 根据条件查询job_member表
    * @param paramMap
    * @return
    */
    public List<Object> getTidList(Map<String, String> paramMap) {
        DataSourceContextHolder.setCustomerType("weather_ds");
        Example example = new Example(Member.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid","1");
        String userId = paramMap.get("userId");
        if (StringUtils.isEmpty(userId)) {
            userId="";
        }
        criteria.andEqualTo("userId", userId);
        List<Member> list= new ArrayList<>();
        list=  mapper.selectByExample(example);
        DataSourceContextHolder.clearCustomerType();
        List<Object> tasklist = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            tasklist.add(list.get(i).getTid().toString());
        }
        return tasklist;
    }
    /**
     * 根据条件查询task_content
     * @param paramMap
     * @return
     */
    public List<Task> querypgTask(Map<String, String> paramMap) {
        List<Task> taskList = new ArrayList<>();
        List<Object> tidlist = getTidList(paramMap);
        if(tidlist.size()>0){
            Example example = new Example(Task.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("valid","1");
            String Id = paramMap.get("id");
            if (!StringUtils.isEmpty(Id)) {
                criteria.andEqualTo("id", Id);
            }
            String title = paramMap.get("taskTitle");
            if (!StringUtils.isEmpty(title)) {
                criteria.andLike("taskTitle", "%" + title + "%");
            }
            String status = paramMap.get("taskStatus");
            if (!StringUtils.isEmpty(status)) {
                criteria.andEqualTo("taskStatus", status);
            }
            criteria.andIn("id",tidlist);
            taskList =  taskMapper.selectByExample(example);
        }
        return  taskList;

    }
    /**
     * 我承接的任务，主要查询稿件中标的任务
     * @param maps
     * @return
     */
    public PageInfo selectByUserid(Map<String, String> maps) {

        List items = querypgTask(maps);
        int num = items.size();
        PageInfo page = new PageInfo();
        page.setTotal(num);
        page.setList(items);
        return page;
    }
}
