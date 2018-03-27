package cn.hzstk.securities.task.service;

import java.util.ArrayList;
import java.util.List;

import net.ryian.orm.service.BaseService;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import cn.hzstk.securities.task.domain.Mark;
import cn.hzstk.securities.task.mapper.MarkMapper;

@Service
public class MarkService extends BaseService<Mark, MarkMapper> {
	
	public boolean hasMark(String taskId, String bid, String usrId){
		if(StringUtils.isBlank(usrId)){
			return true;
		}
		Mark mark = new Mark();
		mark.setObjId(Integer.parseInt(taskId));
		mark.setOriginId(Integer.parseInt(bid));
		mark.setByUid(Integer.parseInt(usrId));
		int num = mapper.selectCount(mark);
        return num > 0;
	}
	
	public List<Mark> selectTaskMask(String id){
		Example example = new Example(Mark.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("objId", id);
		example.setOrderByClause("id asc");
		
		List items = mapper.selectByExample(example);
		if(items == null) items = new ArrayList();
		return items;
	}
	
	public PageInfo<Mark> selectMyMark(String usrId, int page, int rows){
		Example example = new Example(Mark.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("uid", usrId);
		example.or().andEqualTo("byUid", usrId);
		
		List items = mapper.selectByExampleAndRowBounds(example, new RowBounds(page, rows));
		int num = mapper.selectCountByExample(example);
		
		PageInfo<Mark> data = new PageInfo<Mark>();
		data.setList(items);
		data.setTotal(num);
		return data;
	}
	
	
}
