package cn.hzskt.bdtg.common.message.consumer;

import net.ryian.core.SystemConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.hzskt.bdtg.common.util.UuidUtil;
import cn.hzskt.bdtg.job.domain.Folder;
import cn.hzskt.bdtg.job.domain.Member;
import cn.hzskt.bdtg.job.domain.Task;
import cn.hzskt.bdtg.job.domain.TaskBid;
import cn.hzskt.bdtg.job.service.FolderService;
import cn.hzskt.bdtg.job.service.MemberService;
import cn.hzskt.bdtg.job.service.TaskBidService;
import cn.hzskt.bdtg.job.service.TaskService;
import cn.hzskt.bdtg.sys.domain.Dict;
import cn.hzskt.bdtg.sys.service.DictService;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

/**
 * Created by allenwc on 16/5/6.
 */
@Component
public class JobInitMessageListener implements MessageListener {

	private static Logger logger = Logger.getLogger(JobInitMessageListener.class);
	
	@Autowired
	private TaskBidService taskBidService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private DictService dictService;

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
    	try {
    		String text = new String(message.getBody());
    		logger.error("submessage" + text);
    		JSONObject task = JSONObject.parseObject(text);
    		Long tid = task.getLong("id");
    		int status = task.getIntValue("status");
    		//状态为工作中
    		if(status == 4) {
    			// TODO: 16/5/6  初始化项目,包括(1)将业主及PM加入job_member中,(2)初始化job_folder
    	    	Task tk = taskService.get(tid);
    	    	if(tk!=null && !("1".equals(tk.getSubflag()))){
    	    		this.saveMember(tid, tk);	//设置业主及
    	    		this.createForder(tid);
    	    		
    	    		tk = new Task();
    	    		tk.setId(tid);
    	    		tk.setSubflag("1");
    	    		taskService.saveOrUpdate(tk);
    	    	}
    		}
		} 
    	catch (Exception e) {
    		logger.error("消息订阅异常" + message, e);
		}
        return Action.CommitMessage;
    }
    
    public void saveMember(Long tid, Task task){
    	if(task!=null){
    		//任务发布者自动设为业主
    		Member member = new Member();
    		member.setUserId(task.getUid());
    		member.setUserName(task.getUsername());
    		member.setType(2);
    		member.setTid(tid);
    		memberService.saveOrUpdate(member);
    	}
    	
    	TaskBid bid = taskBidService.getBid(String.valueOf(tid));
    	if(bid!=null){
    		//中标者自动设为PM
    		Member member = new Member();
    		member.setUserId(bid.getUid());
    		member.setUserName(bid.getUsername());
    		member.setType(4);
    		member.setTid(tid);	
    		memberService.saveOrUpdate(member);
    	}
    }
    
    private void createForder(Long tid){
    	String conf = SystemConfig.INSTANCE.getValue("folder_types");
    	if(StringUtils.isNotBlank(conf)){
    		String[] codes = conf.split(",");
    		for(String code : codes){
    			Dict dict = dictService.getDictByKeyNameAndValue("folder_type", code);
    			Folder folder = new Folder();
    			folder.setTid(tid);
    			folder.setPcode("-1");
    			folder.setCode(UuidUtil.getUUID());
    			folder.setType(Long.valueOf(code));
    			folder.setName(dict.getContent());
    			folderService.saveOrUpdate(folder);
    		}
    	}
    }
    
}
