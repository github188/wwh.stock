//package com.bm.wanma.ui.activity;
//
//import java.io.InputStream;
//
//import com.bm.wanma.R;
//import com.bm.wanma.utils.ShareConstant;
//import com.bm.wanma.weibo.AccessTokenKeeper;
//import com.sina.weibo.sdk.api.ImageObject;
//import com.sina.weibo.sdk.api.WebpageObject;
//import com.sina.weibo.sdk.api.WeiboMultiMessage;
//import com.sina.weibo.sdk.api.share.BaseResponse;
//import com.sina.weibo.sdk.api.share.IWeiboHandler;
//import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
//import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
//import com.sina.weibo.sdk.api.share.WeiboShareSDK;
//import com.sina.weibo.sdk.auth.AuthInfo;
//import com.sina.weibo.sdk.auth.Oauth2AccessToken;
//import com.sina.weibo.sdk.auth.WeiboAuthListener;
//import com.sina.weibo.sdk.constant.WBConstants;
//import com.sina.weibo.sdk.exception.WeiboException;
//import com.sina.weibo.sdk.utils.Utility;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.widget.Toast;
//
//public class Shareweibo extends Activity implements IWeiboHandler.Response {
//
//	/** 微博微博分享接口实例 */
//	private IWeiboShareAPI mWeiboShareAPI = null;
//	private String url;
//	private String name,address;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		
//		// 创建微博分享接口实例
//		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this,
//				ShareConstant.SINA_APP_KEY);
//		mWeiboShareAPI.registerApp();
//		if (savedInstanceState != null) {
//			mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
//		}
//		url = getIntent().getStringExtra("url");
//		name = getIntent().getStringExtra("name");
//		address = getIntent().getStringExtra("addr");
//		sendMultiMessage(url);
//	}
//
//	@Override
//	protected void onNewIntent(Intent intent) {
//		super.onNewIntent(intent);
//		// 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
//		// 来接收微博客户端返回的数据；执行成功，返回 true，并调用
//		// {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
//		mWeiboShareAPI.handleWeiboResponse(intent, this);
//	}
//
//	private void sendMultiMessage(String url) {
//		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//		
//		//weiboMessage.mediaObject = getTextObj();
//		
//		 ImageObject imageobj = new ImageObject();  
//		 InputStream is = getResources().openRawResource(R.drawable.bg_map_head2);    
//	     Bitmap mBitmap = BitmapFactory.decodeStream(is);
//		 if (mBitmap != null) {  
//		        imageobj.setImageObject(mBitmap);  
//		    } 
//		
//		//weiboMessage.imageObject= imageobj;
//		weiboMessage.mediaObject = getWebpageObj();
//		// 2. 初始化从第三方到微博的消息请求
//		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//		// 用transaction唯一标识一个请求
//		request.transaction = String.valueOf(System.currentTimeMillis());
//		request.multiMessage = weiboMessage;
//		// mWeiboShareAPI.sendRequest(Shareweibo.this, request);
//		 
//		 
//		// 3. 发送请求消息到微博，唤起微博分享界面
//		AuthInfo authInfo = new AuthInfo(this, ShareConstant.SINA_APP_KEY,
//				ShareConstant.SINA_REDIRECT_URL, ShareConstant.SINA_SCOPE);
//		Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(this);
//		String token = "";
//		if (accessToken != null) {
//			token = accessToken.getToken();
//		}
//		mWeiboShareAPI.sendRequest(this, request, authInfo, token,
//				new WeiboAuthListener() {
//					@Override
//					public void onWeiboException(WeiboException arg0) {
//						Toast.makeText(Shareweibo.this, "WeiboException", 1)
//								.show();
//					}
//					@Override
//					public void onComplete(Bundle bundle) {
//						// TODO Auto-generated method stub
//						Oauth2AccessToken newToken = Oauth2AccessToken
//								.parseAccessToken(bundle);
//						if(newToken.isSessionValid()){
//							AccessTokenKeeper.writeAccessToken(getApplicationContext(),
//									newToken);
//							
//						/*	Toast.makeText(Shareweibo.this,
//									"onAuthorizeComplete token = ", 1).show();*/
//						}else {
//					        // 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
//				            String code = bundle.getString("code");
//				           /* Toast.makeText(Shareweibo.this,
//									"code = " + code, 1).show();*/
//				        }
//					}
//
//					@Override
//					public void onCancel() {
//					}
//				});
//
//	}
//
//	@Override
//	public void onResponse(BaseResponse arg0) {
//		switch (arg0.errCode) {
//		case WBConstants.ErrorCode.ERR_OK:
//			Toast.makeText(this, "分享成功！", Toast.LENGTH_LONG).show();
//			finish();
//			break;
//		case WBConstants.ErrorCode.ERR_CANCEL:
//			Toast.makeText(this, "分享取消!", Toast.LENGTH_LONG).show();
//			finish();
//			break;
//		case WBConstants.ErrorCode.ERR_FAIL:
//			Toast.makeText(this, "分享失败!",
//					Toast.LENGTH_LONG).show();
//			finish();
//			break;
//		}
//
//	}
//	
//	 /**
//     * 创建多媒体（网页）消息对象。
//     * 
//     * @return 多媒体（网页）消息对象。
//     */
//    private WebpageObject getWebpageObj() {
//        WebpageObject mediaObject = new WebpageObject();
//        mediaObject.identify = Utility.generateGUID();
//        mediaObject.title = name;
//        mediaObject.description = address;
//        
//        // 设置 Bitmap 类型的图片到视频对象里
//        InputStream is = getResources().openRawResource(R.drawable.bg_map_head2);    
//        Bitmap mBitmap = BitmapFactory.decodeStream(is);    
//         mediaObject.setThumbImage(mBitmap);
//        mediaObject.actionUrl = url;
//        mediaObject.defaultText = "Webpage 默认文案";
//        return mediaObject;
//    }
//	
//	 @Override
//	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	 super.onActivityResult(requestCode, resultCode, data);
//	 		finish();
//	 }
//
//}
