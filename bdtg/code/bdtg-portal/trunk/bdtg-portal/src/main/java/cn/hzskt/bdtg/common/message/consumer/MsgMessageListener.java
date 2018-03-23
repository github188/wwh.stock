package cn.hzskt.bdtg.common.message.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Created by allenwc on 16/5/6.
 */
@Component
public class MsgMessageListener implements MessageListener {
    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        // TODO: 16/5/6 将接收到的消息添加到消息里面
        return Action.CommitMessage;
    }
}
