//package com.bm.wanma.popup;
//
//import java.io.ByteArrayOutputStream;
//import java.net.URLEncoder;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Bitmap.CompressFormat;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.PopupWindow;
//
//import com.bm.wanma.R;
//import com.bm.wanma.entity.ShareToThirdBean;
//import com.bm.wanma.net.Protocol;
//import com.bm.wanma.utils.LogUtil;
//import com.bm.wanma.utils.ShareConstant;
//import com.bm.wanma.utils.ToastUtil;
//import com.bm.wanma.utils.Tools;
//import com.bm.wanma.weixin.Util;
//import com.sina.weibo.sdk.api.share.BaseResponse;
//import com.sina.weibo.sdk.api.share.IWeiboHandler;
//import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
//import com.sina.weibo.sdk.api.share.WeiboShareSDK;
//import com.sina.weibo.sdk.constant.WBConstants;
//import com.tencent.connect.share.QQShare;
//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
//import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
//import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//import com.tencent.tauth.IUiListener;
//import com.tencent.tauth.Tencent;
//import com.tencent.tauth.UiError;
//
//public class CustomShareBoard extends PopupWindow implements OnClickListener,IWeiboHandler.Response,IWXAPIEventHandler{
//	 private Activity mActivity;
//	 private IWXAPI api;
//	 public  Tencent mTencent;
//	 private String url,weiboUrl ;
//	 private IWeiboShareAPI  mWeiboShareAPI = null;
//	 private String name,addresss;
//	 
//	public CustomShareBoard(Activity context ,ShareToThirdBean bean) {
//		super(context);
//		 this.mActivity = context;
//	        initView(mActivity);
//	        name = bean.getName();
//	        addresss = bean.getAddr();
//	        url = Protocol.SERVER_ADDRESS_HTML +"/html/app/appShare.html?"+"lng="
//	        +bean.getLng()+"&lat="+bean.getLat()+"&type="+bean.getType()
//	        +"&name="+name+"&addr="+ addresss +"&service="+bean.getService();
//	        
//	        weiboUrl = Protocol.SERVER_ADDRESS_HTML +"/html/app/appShare.html?"
//	        +"lng="+bean.getLng()+"&lat="+bean.getLat()+"&type="+bean.getType()
//	    	        +"&name="+URLEncoder.encode(bean.getName())+"&addr="+URLEncoder.encode(bean.getAddr())+"&service="+bean.getService();
//	}
//	 private void initView(Context context) {
//	        View rootView = LayoutInflater.from(context).inflate(R.layout.custom_share_board, null);
//	        rootView.findViewById(R.id.wechat).setOnClickListener(this);
//	        rootView.findViewById(R.id.wechat_circle).setOnClickListener(this);
//	        rootView.findViewById(R.id.qq).setOnClickListener(this);
//	        rootView.findViewById(R.id.qzone).setOnClickListener(this);
//	        rootView.findViewById(R.id.sina).setOnClickListener(this);
//	        setContentView(rootView);
//	        setWidth(LayoutParams.MATCH_PARENT);
//	        setHeight(LayoutParams.WRAP_CONTENT);
//	        setFocusable(true);
//	        setBackgroundDrawable(new BitmapDrawable());
//	        setTouchable(true);
//	        //微信注册初始化  
//	        api = WXAPIFactory.createWXAPI(mActivity, ShareConstant.WX_APP_ID, true);  
//	        api.registerApp(ShareConstant.WX_APP_ID); 
//	        //qq 
//	        mTencent = Tencent.createInstance(ShareConstant.QQ_APP_ID, mActivity);
//	        //sina
//	        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mActivity, ShareConstant.SINA_APP_KEY);
//	        mWeiboShareAPI.registerApp();
//	        /*if (savedInstanceState != null) {
//	            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
//	        }*/
//	      
//	    }
//	 /* qq分享 */
//	 public void share2QQ()
//	 {
//	 Bundle params = new Bundle();
//	 params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//	 //这条分享消息被好友点击后的跳转URL。
//	 params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
//	 //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
//	 params.putString(QQShare.SHARE_TO_QQ_TITLE, name);
//	 //分享的图片URL
////	 params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, 
////	 "http://a2.qpic.cn/psb?/V10IttrZ3IqSr1/E4PSOfhPSXGaHNs3Ir1Qa.b8MV3KroLpTO*HSMrPSVY!/b/dH4BAAAAAAAA&bo=PAA8AAAAAAADByI!&rf=viewer_4");
//	 params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,Tools.advertisementpath+"fenxiang");
//	 //分享的消息摘要，最长50个字
//	 params.putString(QQShare.SHARE_TO_QQ_SUMMARY, addresss);
//	 //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//	 params.putString(QQShare.SHARE_TO_QQ_APP_NAME, name);
//	 //标识该消息的来源应用，值为应用名称+AppId。
//	// params.putString(QQShare.share_to_qq_Constants.PARAM_APP_SOURCE, "星期几" + AppId);
//	// params.putString(QQShare., value);
//	 mTencent.shareToQQ(mActivity, params, new BaseUiListener());
//	 
//	 }
//	 
//	 private class BaseUiListener implements IUiListener {
//	
//		 @Override
//		 public void onError(UiError e) {
//			 LogUtil.i("cm_netPost","onError");
//		 ToastUtil.showToast("onError:", "code:" + e.errorCode + ", msg:"
//		 + e.errorMessage + ", detail:" + e.errorDetail);
//		 }
//		 @Override
//		 public void onCancel() {
//			 LogUtil.i("cm_netPost","onCancel");
//			ToastUtil.showToast("onCancel", "");
//		 }
//		@Override
//		public void onComplete(Object response) {
//			doComplete(response);
//		}
//		 protected void doComplete(Object values) {
//			 LogUtil.i("cm_netPost","doComplete"+values);
//		 } 
//		 
//	}
//	 
//	 /* 微信分享 */
//	 private void shareWX(int flag){
//		
//			 if (!api.isWXAppInstalled()) {  
//			        ToastUtil.TshowToast("您还未安装客户端");  
//			        return;  
//			    }  
//			 WXWebpageObject webpage = new WXWebpageObject();  
//			    webpage.webpageUrl = url;  
//			    WXMediaMessage msg = new WXMediaMessage(webpage);  
//			    msg.title = name;  
//			   /* msg.description = getResources().getString(  
//			            R.string.app_share_weixin_txt);  */
//			    msg.description = addresss;
//			    //最大64KB，支持JPG格式
//			    Bitmap thumb = BitmapFactory.decodeResource(mActivity.getResources(),  
//			            R.drawable.bg_map_head2);  
//			    msg.setThumbImage(thumb);  
//			    SendMessageToWX.Req req = new SendMessageToWX.Req();  
//			    req.transaction = String.valueOf(System.currentTimeMillis());  
//			    req.message = msg;  
//			   // flag 1是朋友圈，0是好友
//			    req.scene = flag;  
//			    api.sendReq(req); 
//			    
//	 }
//	 
//	 
//
//	@Override
//	public void onClick(View v) {
//		//dismiss();
//		switch (v.getId()) {
//		case R.id.wechat:
//			shareWX(0);
//			break;
//
//		case R.id.wechat_circle:
//			shareWX(1);
//			break;
//		case R.id.qq:
//			share2QQ();
//			break;
//		case R.id.qzone:
//			share2QQ();
//			break;
//		case R.id.sina:
//		/*	mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mActivity,
//					ShareConstant.SINA_APP_KEY);
//			mWeiboShareAPI.registerApp();*/
//		Intent sinaIn = new Intent();
//		sinaIn.putExtra("url", weiboUrl);
//		sinaIn.putExtra("name", name);
//		sinaIn.putExtra("addr", addresss);
//		sinaIn.setClass(mActivity, com.bm.wanma.ui.activity.Shareweibo.class);
//		mActivity.startActivity(sinaIn);
//			break;
//		}
//	}
//	
//	
//	@Override
//	public void onResponse(BaseResponse baseResp) {
//		switch (baseResp.errCode) {
//        case WBConstants.ErrorCode.ERR_OK:
//           ToastUtil.TshowToast("cm_ok");
//            break;
//        case WBConstants.ErrorCode.ERR_CANCEL:
//        	 ToastUtil.TshowToast("cm_cancel");
//            break;
//        case WBConstants.ErrorCode.ERR_FAIL:
//        	 ToastUtil.TshowToast("cm_fall");
//            break;
//        }
//		
//	}
//	@Override
//	public void onReq(BaseReq arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void onResp(BaseResp arg0) {
//		
//		ToastUtil.TshowToast("cm_fall");
//	}
//
//}
