package com.bm.wanma.ui.scan;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bm.wanma.R;
import com.bm.wanma.dialog.CustomCommonDialog;
import com.bm.wanma.dialog.CustomCommonDialog.CustomDialogCallback;
import com.bm.wanma.dialog.CustomTipInsertGunDialog;
import com.bm.wanma.entity.ScanInfoBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.socket.SocketConstant;
import com.bm.wanma.socket.StreamUtil;
import com.bm.wanma.socket.TCPSocketManager;
import com.bm.wanma.ui.activity.BaseActivity;
import com.bm.wanma.ui.activity.ITcpCallBack;
import com.bm.wanma.ui.activity.OpenDoorActivity;
import com.bm.wanma.ui.activity.PileDetailActivity;
import com.bm.wanma.ui.activity.RealTimeChargeActivity;
import com.bm.wanma.ui.activity.WashCarActivity;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.ToastUtil;
import com.bm.wanma.view.ScanInputEditText;
import com.bm.wanma.view.ScanInputEditText.OnKeybordInputFinish;
import com.umeng.analytics.MobclickAgent;
import com.zbar.lib.camera.CameraManager;
import com.zbar.lib.decode.CaptureActivityHandler;
import com.zbar.lib.decode.InactivityTimer;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 描述: 扫描界面
 */
public class CaptureActivity extends BaseActivity implements Callback,OnClickListener,OnKeybordInputFinish,ITcpCallBack,CustomDialogCallback {

	private CaptureActivityHandler captureHandler;
	private boolean hasSurface;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.50f;
	private boolean vibrate,isCloseTCP;
	private int x = 0;
	private int y = 0;
	private int cropWidth = 0;
	private int cropHeight = 0;
	private RelativeLayout mContainer = null;
	private RelativeLayout mCropLayout = null;
	private boolean isNeedCapture = false;
	private ImageButton scan_ibtn_back;
	private String mcode,mq;
	private ImageView mQrLineView;
	private TranslateAnimation mAnimation;
	private boolean isOpenLight;
	private ScanInfoBean mScanInfoBean;
	private LinearLayout ll_scan_light;
	private LinearLayout ll_scan_input_ll;
	private ImageView iv_scan_light;
	private ScanInputEditText editText;
	private GridView gridView;
	private boolean isShowInput;
	private ArrayList<Map<String, String>> valueList = new ArrayList<Map<String,String>>();
	private int currentIndex = -1;//用于记录当前输入密码格位置
	private StringBuilder sb;
	TextView tv_capture_clike;
	private int Length;
	private ImageView im_keyboard_close;
	
	private TCPSocketManager mTcpSocketManager;
	private int gunState,headState;
	private CustomCommonDialog customDialog;
	private CustomTipInsertGunDialog tipInsertGunDialog;
	
	
	
	public boolean isNeedCapture() {
		return isNeedCapture;
	}

	public void setNeedCapture(boolean isNeedCapture) {
		this.isNeedCapture = isNeedCapture;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCropWidth() {
		return cropWidth;
	}

	public void setCropWidth(int cropWidth) {
		this.cropWidth = cropWidth;
	}

	public int getCropHeight() {
		return cropHeight;
	}

	public void setCropHeight(int cropHeight) {
		this.cropHeight = cropHeight;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_qr_scan);
		mPageName = "CaptureActivity";
		ll_scan_input_ll = (LinearLayout) findViewById(R.id.scan_input_ll);
		//ll_scan_input_ll.setOnClickListener(this);
		ll_scan_light = (LinearLayout) findViewById(R.id.scan_light_ll);
		ll_scan_light.setOnClickListener(this);
		iv_scan_light = (ImageView) findViewById(R.id.scan_light_iv);
		tv_capture_clike = (TextView) findViewById(R.id.capture_clike);
		tv_capture_clike.setOnClickListener(this);
		scan_ibtn_back = (ImageButton)findViewById(R.id.activity_scan_back);
		scan_ibtn_back.setOnClickListener(this);
		editText = (ScanInputEditText) findViewById(R.id.scan_input_edittext);
		editText.setOnkeybordListener(this);
		editText.setInputType(InputType.TYPE_NULL);//隐藏软键盘
		editText.setOnClickListener(this);
		im_keyboard_close = (ImageView) findViewById(R.id.keyboard_close);
		im_keyboard_close.setOnClickListener(this);
		//是否开启手电筒
		isOpenLight = false;
		// 初始化 CameraManager
		CameraManager.init(getApplication());
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);

		mContainer = (RelativeLayout) findViewById(R.id.capture_containter);
		mContainer.setOnClickListener(this);
		mCropLayout = (RelativeLayout) findViewById(R.id.capture_crop_layout);

		mQrLineView = (ImageView) findViewById(R.id.capture_scan_line);
		mAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.9f);
		mAnimation.setDuration(1500);
		mAnimation.setRepeatCount(-1);
		mAnimation.setRepeatMode(Animation.REVERSE);
		mAnimation.setInterpolator(new LinearInterpolator());
		//mQrLineView.setAnimation(mAnimation);
		registerBoradcastReceiver();
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.scan_light_ll:
			//手电筒开关
			if(isOpenLight){
				CameraManager.get().offLight();//关闭手电筒
				isOpenLight = false;
				ll_scan_light.setBackground(getResources().getDrawable(R.drawable.scan_lamp__light_black_circle));
//				iv_scan_light.setImageResource(R.drawable.btn_lamp_on);
			}else {
				CameraManager.get().openLight(); //开启手电筒
				isOpenLight = true ;
				ll_scan_light.setBackground(getResources().getDrawable(R.drawable.scan_light_white_bg));
//				iv_scan_light.setImageResource(R.drawable.btn_lamp_off);
			}
			 
			break;
		case R.id.activity_scan_back:
			CaptureActivity.this.finish();
			break;
		case R.id.scan_input_ll:
		case R.id.scan_input_edittext:
			if(!isShowInput){
				isShowInput = true;
				showKeybord();
			}
			if(!gridView.isShown()){
				ll_scan_input_ll.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.capture_containter:
			if(gridView != null && gridView.isShown()){
				empty();
				ll_scan_input_ll.setVisibility(View.GONE);
			}
			break;
		case R.id.capture_clike:
			if(!isShowInput){
				isShowInput = true;
				showKeybord();
			}
			if(!gridView.isShown()){
				ll_scan_input_ll.setVisibility(View.VISIBLE);
			}
			break;
			
		case R.id.keyboard_close:
			if(gridView!= null && gridView.isShown()){
				empty();
				
				ll_scan_input_ll.setVisibility(View.GONE);
			}
			break;
		default:
			break;
		}	
	}
	/**
	 * 清空密码框数据
	 */
	private void empty() {
		for (int i = 0; i < Length; i++) {			
			if (currentIndex - 1 >= -1) {//判断是否删除完毕————要小心数组越界  
				currentIndex --;
				sb.deleteCharAt(sb.length()-1);
				editText.setText(sb.toString());
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if(gridView!= null && gridView.isShown()){
				empty();
				ll_scan_input_ll.setVisibility(View.GONE);
			}else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showKeybord(){
	    sb = new StringBuilder();
		gridView = (GridView)findViewById(R.id.custom_keybord);
		/* 初始化按钮上应该显示的数字 */  
        for (int i = 1; i < 13; i++) {  
            Map<String, String> map = new HashMap<String, String>();  
            if (i < 10) {  
                map.put("name", String.valueOf(i));  
            } else if (i == 10) {  
                map.put("name", "  ");  
            } else if (i == 12) {  
                map.put("name", "  ");  
            } else if (i == 11) {  
                map.put("name", String.valueOf(0));  
            }  
            valueList.add(map);  
        }  
        gridView.setAdapter(adapter);  
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
            	
                if (position < 11 && position != 9) {    //点击0~9按钮  
                    if (currentIndex >= -1 && currentIndex < 5) {      
                    	//判断输入位置————要小心数组越界  
                    	 currentIndex ++;
                    	 sb.append(valueList.get(position).get("name"));
                         editText.setText(sb.toString());
                    }  
                } else if(position == 11){  
                      //点击退格键  
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界  
                        	currentIndex --;
                        	sb.deleteCharAt(sb.length()-1);
                            editText.setText(sb.toString());
                        }  
                      
                }
            }  
        });  
		
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(mContext);
		MobclickAgent.onPageStart(mPageName);
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true; 
		mQrLineView.setAnimation(mAnimation);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(mContext);
		MobclickAgent.onPageEnd(mPageName);
		if (captureHandler != null) {
			captureHandler.quitSynchronously();
			captureHandler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		if(mTcpSocketManager != null){
			unregisterReceiver(mBroadcastReceiver);
	  		if(isCloseTCP){
	  			mTcpSocketManager.close();
	  		}
		}
		if(customDialog != null && customDialog.isShowing()){
			customDialog.dismiss();
		}
		if(tipInsertGunDialog != null && tipInsertGunDialog.isShowing()){
			tipInsertGunDialog.dismiss();
		}
		super.onDestroy();
	}

	public void handleDecode(String result) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		//扫描成功，解析二维码
		if (result != null && !result.isEmpty()) {
			if(result.contains("type=") && result.contains("&code") && result.contains("&q")){
				//mtype = result.substring(result.indexOf("type=")+5, result.indexOf("&code"));
				mcode = result.substring(result.indexOf("code=")+5, result.indexOf("&q"));
				mq = result.substring(result.indexOf("&q=")+3);
				if(isNetConnection()){
					mQrLineView.clearAnimation();
					showPD(getString(R.string.request_data));
					GetDataPost.getInstance(this).getScanInfo(handler, mcode, mq);
				}else {
					showToast("亲，网络不稳，请检查网络连接");
				}
				
			}else if(result.contains("type=xc")){
				//洗车
				Intent carIn = new Intent(CaptureActivity.this,WashCarActivity.class);
				startActivity(carIn);
				finish();
				
			}else if(result.contains("type=km")){
				//开门
				Intent carIn = new Intent(CaptureActivity.this,OpenDoorActivity.class);
				startActivity(carIn);
				finish();
				
			}else {
				ToastUtil.TshowToast("无法识别的二维码！");
				finish();
			}
			
		} else {
			Toast.makeText(CaptureActivity.this, "扫描失败", Toast.LENGTH_SHORT)
			.show();
			finish();
			
		}
		// 连续扫描，不发送此消息扫描一次结束后就不能再次扫描
		 //captureHandler.sendEmptyMessage(R.id.restart_preview);
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
	
		try {
			CameraManager.get().openDriver(surfaceHolder);
			//CameraManager.get().requestAutoFocus(captureHandler, 1);
			Point point = CameraManager.get().getCameraResolution();
			int width = point.y;
			int height = point.x;
			Log.i("cm_camera", "width"+width);
			int x = mCropLayout.getLeft() * width / mContainer.getWidth();
			int y = mCropLayout.getTop() * height / mContainer.getHeight();

			int cropWidth = mCropLayout.getWidth() * width / mContainer.getWidth();
			int cropHeight = mCropLayout.getHeight() * height / mContainer.getHeight();

			setX(x);
			setY(y);
			setCropWidth(cropWidth);
			setCropHeight(cropHeight);
			// 设置是否需要截图
			//setNeedCapture(false);
			setNeedCapture(true);

		} catch (IOException ioe) {
		
			return;
		} catch (RuntimeException e) {
			//应用权限被禁在这异常 提示
			Toast.makeText(getApplicationContext(), "请开启摄像头权限", 1).show();
			
			return;
		}
		if (captureHandler == null) {
			captureHandler = new CaptureActivityHandler(CaptureActivity.this);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public Handler getHandler() {
		return captureHandler;
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	@Override
	protected void getData() {
		
	}
	
	
	@Override
	public void onInputFinish(String result) {
		Length = result.length();
		loadData(result);
	}
	
	private void loadData(String str){
		if(isNetConnection()){
			showPD(getString(R.string.request_data));
			GetDataPost.getInstance(this).getzNumSelPileInfo(handler, str);
		}else {
			showToast("亲，网络不稳，请检查网络连接");
		}
	}
 
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		//扫描结果处理
		if(bundle != null){
			mScanInfoBean = (ScanInfoBean) bundle.getSerializable(Protocol.DATA);
			empty();
			ll_scan_input_ll.setVisibility(View.GONE);
			if(mScanInfoBean == null){
				cancelPD();
				showCustomDialog("暂时无法连接，请重试");
			}else {
				handlerConnectionPile(mScanInfoBean);
			}
			
		}else {
			cancelPD();
			showCustomDialog("暂时无法连接，请重试");
		}
		
	}
	
	private void showCustomDialog(String content){
		if(customDialog == null){
			customDialog = new CustomCommonDialog(this);
			customDialog.setCancelable(false);
			customDialog.setCustomDialogListener(this);
		}
		customDialog.getContentView().setText(content);
		if(!customDialog.isShowing()){
			customDialog.show();
		}
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		if(sign.equals(Protocol.GET_ZNum_PILE_INFO)){
			showToast(bundle.getString(Protocol.MSG));
			empty();
		}else {
			showCustomDialog(bundle.getString(Protocol.MSG));
		}
	}

	 //GrideView的适配器  
    BaseAdapter adapter = new BaseAdapter() {  
        @Override  
        public int getCount() {  
            return valueList.size();  
        }  
  
        @Override  
        public Object getItem(int position) {  
            return valueList.get(position);  
        }  
  
        @Override  
        public long getItemId(int position) {  
            return position;  
        }  
  
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
            ViewHolder viewHolder;  
            if (convertView == null) {  
                convertView = View.inflate(CaptureActivity.this, R.layout.popup_custom_keybord_item_gride, null);  
                viewHolder = new ViewHolder();  
                viewHolder.btnKey = (RelativeLayout) convertView.findViewById(R.id.keybord_btn_keys);  
                viewHolder.btnKey_text = (TextView) convertView.findViewById(R.id.keybord_btn_keys_text);  
                viewHolder.delete_to = (ImageView) convertView.findViewById(R.id.delete_to);  
                convertView.setTag(viewHolder);  
            } else {  
                viewHolder = (ViewHolder) convertView.getTag();  
            }  
            viewHolder.btnKey_text.setText(valueList.get(position).get("name"));  
            if(position == 9){  
            	convertView.setBackgroundColor(Color.parseColor("#d1d5db"));
                viewHolder.btnKey.setEnabled(false);  
            }  
            if(position == 11){  
            	convertView.setBackgroundResource(R.drawable.custom_input_key_del);  
            	viewHolder.delete_to.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.delete_shibie));
            }  
  
            return convertView;  
        }  
    };  
  
    /** 
     * 存放控件 
     */  
    public final class ViewHolder {  
        public RelativeLayout btnKey;  
        public TextView btnKey_text;
        public ImageView delete_to; 
    }
    /** 
     * 处理连接充电桩
     */
    private void handlerConnectionPile(ScanInfoBean mInfoBean){
    	//实例化TCPSocketManager，连接充电桩
    			mTcpSocketManager = TCPSocketManager.getInstance(this);
    			//elpiElectricpilecode = mInfoBean.getElpiElectricpilecode();
    			//ePHeElectricpileHeadId =  mInfoBean.getePHeElectricpileHeadId();
    			try {
    				isCloseTCP = true;
    				mTcpSocketManager.setTcpCallback(this);
    				mTcpSocketManager.open(mInfoBean.getElpiElectricpilecode(),Byte.parseByte(mInfoBean.getePHeElectricpileHeadId()));
    			} catch (NumberFormatException e) {
    				e.printStackTrace();
    				LogUtil.i("cm_socket", "枪头编号异常，不是数字格式");
    				//showToast("枪头编号异常，不是数字格式");
    				showCustomDialog("暂时无法连接，请重试");
    			}
    	
    }
  //回调，处理tcp返回的报文
  	@Override
  	public void handleTcpPacket(final ByteArrayInputStream result){
  		
  		runOnUiThread(new Runnable() {
  			@Override
  			public void run() {
  				 try {
  					    cancelPD();
  						int reason = StreamUtil.readByte(result);
  						short cmdtype = StreamUtil.readShort(result);
  						LogUtil.i("cm_netPost", "000000000000000==" + cmdtype);
  						switch (cmdtype) {
  						case SocketConstant.CMD_TYPE_CONNECT:
  							int successflag = StreamUtil.readByte(result);
  								short errorcode = StreamUtil.readShort(result);
  	  							headState = StreamUtil.readByte(result);
  	  						LogUtil.i("cm_netPost", "******************==" + headState);
  	  							int type = 1;
  	  							 try {
  	  								type = StreamUtil.readByte(result);
  	  							} catch (Exception e) {
  	  								e.printStackTrace();
  	  							}
  	  							mTcpSocketManager.setPileType(type);
  	  							handleConnectEvent(successflag,errorcode,headState,type);
  							break;
  					
  						case SocketConstant.CMD_TYPE_GUN:
  							//枪与车的连接状态  1:枪与车未连接 2:枪与车连接 
  							gunState = StreamUtil.readByte(result);
  							LogUtil.i("cm_socket", "枪与车的连接状态"+gunState);
  							mTcpSocketManager.setGunState(gunState);
  							mTcpSocketManager.sendTipGunState(StreamUtil.readWithLength(result, 3));
  							if(headState == 5){//等待放电
  								if(gunState == 1){
  									if(tipInsertGunDialog == null){
  										tipInsertGunDialog = new CustomTipInsertGunDialog(CaptureActivity.this);
  	  									tipInsertGunDialog.setCancelable(false);
  									}
  									if(!tipInsertGunDialog.isShowing()){
  										tipInsertGunDialog.show();
  									}
  								}else {
  									showCustomDialog("准备放电，请稍等");
  								}
  							}
  							break;
  						case SocketConstant.CMD_TYPE_REAL_DATA:
  						// 收到实时数据，进入实时数据界面
  							int state = StreamUtil.readByte(result);
  							short chargeTime = StreamUtil.readShort(result);
  							StreamUtil.readShort(result);//short dianya = 
  							StreamUtil.readShort(result);//short dianliu = 
  							int diandu = StreamUtil.readInt(result);
  							short feilv = StreamUtil.readShort(result);
  							int yuchong = StreamUtil.readInt(result);
  							int yichong = StreamUtil.readInt(result);
  							int soc = StreamUtil.readByte(result);
  							StreamUtil.readInt(result);//int fushu = 
  							StreamUtil.readInt(result);//int gaojing = 
  							Intent realIn = new Intent(CaptureActivity.this,
  									RealTimeChargeActivity.class);
  							realIn.putExtra("state", state);
  							realIn.putExtra("chargeTime", chargeTime);
  							realIn.putExtra("diandu", diandu);
  							realIn.putExtra("feilv", feilv);
  							realIn.putExtra("yuchong", yuchong);
  							realIn.putExtra("yichong", yichong);
  							realIn.putExtra("soc", soc);
  							realIn.putExtra("interfacefrom", "shaomiao");
  							//sendBroadcast(new Intent("scan_start_charge"));
  							isCloseTCP = false;
  							startActivity(realIn);
  							finish();
  							break;
  						}
  					} catch (IOException e) {
  						e.printStackTrace();
  					}
  			}
  		});
  		
  	}
  	
  	private void handleConnectEvent(int successflag,short errorcode,int headState,int type){
  		switch (successflag) {
  		case 1:
  		    if(3 == headState || 0 == headState){
  		    	Intent in = new Intent();
				in.putExtra("scanInfo", mScanInfoBean);
				in.putExtra("type", type);
				in.putExtra("awaitType", 0);//1等待 0详情
				in.putExtra("interfacefrom", "shaomiao");
				in.setClass(CaptureActivity.this, PileDetailActivity.class);
				startActivity(in);
  		    	
  			}else if(6 == headState){
  				//充电中，等待实时信息，跳转展示界面
  			}else if(7 == headState){
  				Intent in = new Intent();
				in.putExtra("scanInfo", mScanInfoBean);
				in.putExtra("type", type);
				in.putExtra("awaitType", 1);//1等待 0详情
				in.putExtra("interfacefrom", "shaomiao");
				in.setClass(CaptureActivity.this, PileDetailActivity.class);
				startActivity(in);
  			}
  			break;
  		case 0:
  			showErrorDialog(errorcode);
  			break;
  		}
  	}
 
  	
  	public void registerBoradcastReceiver(){  
          IntentFilter myIntentFilter = new IntentFilter();  
          myIntentFilter.addAction("scan_start_charge"); 
          //注册广播        
          registerReceiver(mBroadcastReceiver, myIntentFilter);  
      }  
  	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

  		@Override
  		public void onReceive(Context context, Intent intent) {
  			  String action = intent.getAction();
  			  if(action.equals("scan_start_charge")){ 
  				 isCloseTCP = false;
  				 finish();
  			 }
  		}
  	};



	@Override
	public void OnDialogClick() {
		if(customDialog != null && customDialog.isShowing()){
			customDialog.dismiss();
		}
		//重启扫描动画
		mQrLineView.startAnimation(mAnimation);
		captureHandler.sendEmptyMessage(R.id.restart_preview);
	}

private void showErrorDialog(short code){
	
 switch (code) {
	case 110:
		showCustomDialog(getResources().getString(R.string.tip_error_110));
		break;
	case 1002:
		showCustomDialog(getResources().getString(R.string.tip_error_1002));
		break;
	case 6000:
		showCustomDialog(getResources().getString(R.string.tip_error_6000));
		break;
	case 6001:
		showCustomDialog(getResources().getString(R.string.tip_error_6001));
		break;
	case 6100:
		showCustomDialog(getResources().getString(R.string.tip_error_6100));
		break;
	case 6101:
		showCustomDialog(getResources().getString(R.string.tip_error_6101));
		break;
	case 6102:
		showCustomDialog(getResources().getString(R.string.tip_error_6102));
		break;	
	case 6104:
		showCustomDialog(getResources().getString(R.string.tip_error_6104));
		break;
	case 6105:
		showCustomDialog(getResources().getString(R.string.tip_error_6105));
		break;
	case 6200:
		showCustomDialog(getResources().getString(R.string.tip_error_6200));
		break;
	case 6201:
		showCustomDialog(getResources().getString(R.string.tip_error_6201));
		break;
	case 6202:
		showCustomDialog(getResources().getString(R.string.tip_error_6202));
		break;
	case 6203:
		showCustomDialog(getResources().getString(R.string.tip_error_6203));
	case 6300:
		showCustomDialog(getResources().getString(R.string.tip_error_6300));
		break;
	case 6301:
		showCustomDialog(getResources().getString(R.string.tip_error_6301));
		break;
	case 6401:
		showCustomDialog(getResources().getString(R.string.tip_error_6401));
		break;
	case 6402:
		showCustomDialog(getResources().getString(R.string.tip_error_6402));
		break;
	case 6403:
		showCustomDialog(getResources().getString(R.string.tip_error_6403));
		break;
	case 6404:
		showCustomDialog(getResources().getString(R.string.tip_error_6404));
		break;
	case 6405:
		showCustomDialog(getResources().getString(R.string.tip_error_6405));
		break;
	case 6406:
		showCustomDialog(getResources().getString(R.string.tip_error_6406));
		break;
	case 6600:
		showCustomDialog(getResources().getString(R.string.tip_error_6600));
		break;	
	case 6601:
		showCustomDialog(getResources().getString(R.string.tip_error_6601));
		break;	
	case 6633:
		showCustomDialog(getResources().getString(R.string.tip_error_6633));
		break;	
	case 6700:
		showCustomDialog(getResources().getString(R.string.tip_error_6700));
		break;
	case 6701:
		showCustomDialog(getResources().getString(R.string.tip_error_6701));
		break;
	case 6702:
		showCustomDialog(getResources().getString(R.string.tip_error_6702));
		break;
	case 6703:
		showCustomDialog(getResources().getString(R.string.tip_error_6703));
		break;
	case 6704:
		showCustomDialog(getResources().getString(R.string.tip_error_6704));
		break;
	case 6705:
		showCustomDialog(getResources().getString(R.string.tip_error_6705));
		break; 
	case 6706:
		showCustomDialog(getResources().getString(R.string.tip_error_6706));
		break; 
	case 6800:
		showCustomDialog(getResources().getString(R.string.tip_error_6800));
		break;
	case 6801:
		showCustomDialog(getResources().getString(R.string.tip_error_6801));
		break;
	case 6802:
		showCustomDialog(getResources().getString(R.string.tip_error_6802));
		break;
	case 6803:
		showCustomDialog(getResources().getString(R.string.tip_error_6803));
		break;
	case 6804:
		showCustomDialog(getResources().getString(R.string.tip_error_6804));
		break;
	default:
		showCustomDialog("未知原因");
		break;
	}
		
	}

@Override
public void handleSocketException() {
	 runOnUiThread(new Runnable() {
		@Override
		public void run() {
			showCustomDialog(getResources().getString(R.string.tip_error_110));
		}
	});
}
	
}