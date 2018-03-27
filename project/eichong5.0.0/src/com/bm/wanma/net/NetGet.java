package com.bm.wanma.net;

import java.lang.reflect.Type;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.os.Handler;
import com.bm.wanma.entity.BaseBean;
import com.bm.wanma.model.net.FinalHttpFactory;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.Tools;
import com.google.gson.Gson;

/**
 * 网络访问
 * 
 */
public class NetGet extends NetBase {

	/**
	 * 异步get获取
	 */
	protected void getData(final Handler handler, final String url, final AjaxParams params, final Type type) {
		LogUtil.i("cm_netGet", "url=="+url);
		LogUtil.i("cm_netGet", "params="+params);
		FinalHttpFactory.getFinalHttp().get(url, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				Gson gson = new Gson();
				String context = t.toString();
				if (context != null) {
					context = context.trim();
				}
				LogUtil.i("cm_netGet", "context=====" + context);
				BaseBean<?> bean = null;
				if (context != null && !Tools.judgeString(context, "")) {
					try {
						bean = gson.fromJson(context, type);
					} catch (Exception e) {
						LogUtil.i("cm_netGet", "Exception");
						e.printStackTrace();
					}
				}
				setHandler(handler, bean, url);
			}
		});

	}
}
