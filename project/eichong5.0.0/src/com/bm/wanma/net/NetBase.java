package com.bm.wanma.net;
import java.io.Serializable;

import net.tsz.afinal.http.AjaxCallBack;

import com.bm.wanma.entity.BaseBean;
import com.bm.wanma.model.net.FinalHttpFactory;
import com.bm.wanma.ui.activity.BaseActivity;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.ProjectApplication;
import com.bm.wanma.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 封装handler
 */
public class NetBase {

	protected void setHandler(Handler handler, BaseBean<?> bean, String sign) {
		String net_msg = "";
		Message msg = new Message();
		msg.what = 0;
		msg.obj = sign;
		Bundle bundle = new Bundle();
		if (bean != null) {
			LogUtil.i("cm_netPost_status", "status==" +bean.getStatus());
			net_msg = bean.getMsg();
			if (bean.getStatus().equals("100")) {
				msg.what = 1;
				if (bean.getData() != null ) {
					bundle.putSerializable(Protocol.DATA, (Serializable) bean.getData());
				}
			}else if (bean.getStatus().equals("5000")) {
				//token失效
				FinalHttpFactory.getFinalHttp().post(Protocol.GET_API_TOKEN, null, new AjaxCallBack<Object>(){
					@Override
					public void onSuccess(Object t) {
						Gson gson = new Gson();
						String content = t.toString();
						if (content != null) {
							content = content.trim();
						}
						BaseBean<?> bean = null;
						if (content != null && !Tools.judgeString(content, "")) {
							try {
								bean = gson.fromJson(content, new TypeToken<BaseBean<?>>() {
								}.getType());
							} catch (Exception e) {
							}
							if (bean != null) {
								if (bean.getStatus().equals("100")) {
									if (bean.getData() != null ) {
										PreferencesUtil.setPreferences(ProjectApplication.getInstance(),"apiToken",(String)bean.getData());
									}
								}
							}
						}
					}
				});
			}
			handler.sendEmptyMessage(BaseActivity.HIDE_PD);
			bundle.putString(Protocol.MSG, net_msg);
			bundle.putString(Protocol.CODE, bean.getStatus());
			msg.setData(bundle);
			handler.sendMessage(msg);
		}else {
			//net_msg = "服务器无响应";
			net_msg = "";
			handler.sendEmptyMessage(BaseActivity.HIDE_PD);
			bundle.putString(Protocol.MSG, net_msg);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
		
	}
	//请求异常处理
	protected void setErrorHandler(Handler handler,String sign) {
		Message msg = Message.obtain();
		msg.what = 0;
		msg.obj = sign;
		Bundle bundle = new Bundle();
			bundle.putString(Protocol.MSG, "连接异常，请稍后再试!");
		handler.sendEmptyMessage(BaseActivity.HIDE_PD);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}
	
	
	
	protected void setHandler(String context, Handler handler, String sign) {
		Message msg = Message.obtain();
		msg.what = 0;
		msg.obj = sign;
		Bundle bundle = new Bundle();
		if (context != null && context.length() > 0) {
			msg.what = 1;
			bundle.putString(Protocol.DATA, context);
		}
		handler.sendEmptyMessage(BaseActivity.HIDE_PD);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

}
