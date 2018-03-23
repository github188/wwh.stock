package cn.hzskt.bdtg.common.message.consumer;

import cn.hzskt.bdtg.job.domain.Status;
import cn.hzskt.bdtg.job.domain.Task;
import cn.hzskt.bdtg.job.service.StatusService;
import cn.hzskt.bdtg.job.service.TaskService;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by allenwc on 16/5/23.
 */
@Component
public class JobStatusMessageListener implements MessageListener {
    @Autowired
    private StatusService statusService;
    @Autowired
    private TaskService taskService;

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        try {
            String text = new String(message.getBody());
            JSONObject jsonObject = JSONObject.parseObject(text);
            if(jsonObject!=null){
                String type=jsonObject.getString("type");
                String progress="0";
                String progressStr="";
                if("A".equals(type)){
                    progress="5";
                }else if("B".equals(type)){
                    progress="10";
                }else if("C".equals(type)){
                    progress="15";
                }else if("D".equals(type)){
                    progress="70";
                }else if("E".equals(type)){
                    progress="80";
                }else if("F".equals(type)){
                    progress="90";
                }else {
                    progress="100";
                }
                //保存状态
                Status status = new Status();
                status.setValid(1);
                status.setTid(Long.parseLong(jsonObject.getString("tid")));
                status.setStatusTime(new Date(jsonObject.getString("statusTime")));
                status.setUid(Long.parseLong(jsonObject.getString("creator")));
                status.setUserName(jsonObject.getString("createName"));
                status.setContent(jsonObject.getString("createName") +"："+ jsonObject.getString("content"));
                statusService.saveOrUpdate(status);
                //计算进度
                Task task=new Task();
                task = taskService.get(jsonObject.getLongValue("tid"));

                if(task.getProgressVal()==null){
                    task.setProgressVal(Long.parseLong(progress));
                }else if(task.getProgressVal()<Long.parseLong(progress)){
                    task.setProgressVal(Long.parseLong(progress));
                }
                taskService.saveOrUpdate(task);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return Action.CommitMessage;
    }
}
