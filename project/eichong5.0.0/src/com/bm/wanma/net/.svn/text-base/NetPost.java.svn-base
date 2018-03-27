package com.bm.wanma.net;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

import android.os.Handler;

import com.bm.wanma.entity.BaseBean;
import com.bm.wanma.model.net.FinalHttpFactory;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.ToastUtil;
import com.bm.wanma.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

public class NetPost extends NetBase {

	protected void getData(final Handler handler, final String url, final AjaxParams params,
                           final Type type) {
		LogUtil.i("cm_netPost", "url==" + url);
		LogUtil.i("cm_netPost", "params==" + params.toString());
		FinalHttpFactory.getFinalHttp().post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onSuccess(Object t) {
				Gson gson = new Gson();
				String content = t.toString();
				if (content != null) {
					content = content.trim();
				}
				BaseBean<?> bean = null;
				LogUtil.i("cm_netPost","content==" + content);
				if (content != null && !Tools.judgeString(content, "")) {
					try {
						bean = gson.fromJson(content, type);
					} catch (Exception e) {
						LogUtil.i("cm_netinfo", "Exception1");
						e.printStackTrace();
						Type defaulType = new TypeToken<BaseBean<?>>() {
						}.getType();
						try {
							bean = gson.fromJson(content, defaulType);
						}catch (Exception e1) {
							LogUtil.i("cm_netinfo", "Exception2");
						}
					}
				}
				setHandler(handler, bean, url);
			}
			//异常处理

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				//ToastUtil.TshowToast("netpost异常" );
				setErrorHandler(handler,url);
			}
			
		});

	}
	
	public void getData(final String url, final AjaxParams params, final Handler handler) {
		FinalHttpFactory.getFinalHttp().get(url, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object status) {
				LogUtil.i("info", "params===" + params.toString());
				String context = status.toString();
				setHandler(context, handler, url);
			}

		});
	}

}
