package com.bm.wanma.ui.activity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.bm.wanma.R;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.dialog.ChargeFinishTipDialog;
import com.bm.wanma.dialog.FinishChargeWarnDialog;
import com.bm.wanma.socket.SocketConstant;
import com.bm.wanma.socket.StreamUtil;
import com.bm.wanma.socket.TCPSocketManager;
import com.bm.wanma.ui.shareactivity.ShareDetailActivity;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.view.DynamicWave;
import com.bm.wanma.view.SlideUnlockView;
import com.bm.wanma.view.SlideUnlockView.OnUnLockListener;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author cm
 * 充电实时界面
 */
public class RealTimeChargeActivity extends BaseActivity implements OnClickListener,ITcpCallBack,OnUnLockListener{

	private ImageButton ib_back,ib_question;
	private DynamicWave wave;
	private ImageView iv_car;
	private int car_height,car_width;
	private TextView tv_duration,tv_electric,tv_money,tv_soc,tv_unlock_tip;
	private SlideUnlockView slideUnlockView;
	private ChargeFinishTipDialog mFinishChargeDialog;
	private Intent getIn;
	private TCPSocketManager mTcpSocketManager;
	private int type;
	private FinishChargeWarnDialog mDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_realtime_charge);
		mPageName = "RealTimeChargeActivity";
		mTcpSocketManager = TCPSocketManager.getInstance(this);
		type = mTcpSocketManager.getPileType();
		initView();
		registerBoradcastReceiver();
		mTcpSocketManager.setTcpCallback(this);
	} 
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(mContext);
		MobclickAgent.onPageStart(mPageName);
		mTcpSocketManager.reopen();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(mContext);
		MobclickAgent.onPageEnd(mPageName);
	}
	private void initView() {
		ib_back = (ImageButton) findViewById(R.id.realtime_charge_back);
		ib_back.setOnClickListener(this);
		ib_question = (ImageButton) findViewById(R.id.realtime_charge_price_help_icon);
		ib_question.setOnClickListener(this);
		
		wave = (DynamicWave) findViewById(R.id.realtime_charge_dynamicwave);
		iv_car = (ImageView) findViewById(R.id.realtime_charge_car);
		iv_car.setOnClickListener(this);
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
	    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
	    iv_car.measure(w, h);
	    car_height = iv_car.getMeasuredHeight();
	    car_width = iv_car.getMeasuredWidth();
	    LayoutParams lp = wave.getLayoutParams();
        lp.width = car_width;
        lp.height = car_height;
        wave.setLayoutParams(lp);
        wave.setCurrentY(car_height/30);
	    
		tv_duration = (TextView) findViewById(R.id.realtime_charge_time);
		tv_soc = (TextView) findViewById(R.id.realtime_charge_soc);
		tv_electric = (TextView) findViewById(R.id.realtime_charge_electric);
		tv_money = (TextView) findViewById(R.id.realtime_charge_money);
		
		slideUnlockView = (SlideUnlockView) findViewById(R.id.realtime_charge_unlock);
		slideUnlockView.setOnUnLockListener(this);
		tv_unlock_tip = (TextView) findViewById(R.id.realtime_charge_unlock_tip);
		
		getIn = getIntent();
		if(getIn != null){
			initValue();
		}
		
	}
	private void initValue(){
		//0:空闲3 预约中6 充电中9 故障停用
		short tempT = getIn.getShortExtra("chargeTime", (short) 0);
		tv_duration.setText(TimeUtil.getCutDownByMin(tempT));
		tv_electric.setText((float)getIn.getIntExtra("diandu", 0)/100+"");
		tv_money.setText((float)getIn.getIntExtra("yichong",0)/100+"");
		LogUtil.i("cm_netPost", "typetypetypetypetype==" + type);
		if(5 == type){
			tv_soc.setText(getIn.getIntExtra("soc", 0)+"%");
		}else {
			tv_soc.setText("--%");
		}
		  wave.startAnimation();
	}
	
	private void showFinishChargeDialog(){
		if(mDialog == null){
			mDialog = new FinishChargeWarnDialog(RealTimeChargeActivity.this);
			mDialog.setCancelable(false);
		}
		mDialog.setOnPositiveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//发送结束充电命令
				if(mTcpSocketManager.hasTcpConnection()){
					showPD("正在发送停止充电请求");
					mTcpSocketManager.sendStopChargeCMD();
					mDialog.dismiss();
				}else {
					mTcpSocketManager.reopen();
					//showPD("正在发送停止充电请求");
					//mTcpSocketManager.sendStopChargeCMD();
					showToast("通讯异常，请重试");
					mDialog.dismiss();
					slideUnlockView.reset();
				}
				
			}
		});
		mDialog.setOnNegativeListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
					slideUnlockView.reset();
				}
			});
		mDialog.show();
		
	}
	
	
	//处理返回的tcp报文
	@SuppressLint("SimpleDateFormat")
	@Override
	public void handleTcpPacket(final ByteArrayInputStream result) {
		runOnUiThread(new Runnable() {
			@SuppressLint("NewApi")
			@Override
			public void run() {
				try {
					StreamUtil.readByte(result);//int reason = 
					short cmdtype = StreamUtil.readShort(result);
					switch (cmdtype) {
					case SocketConstant.CMD_TYPE_REAL_DATA:
						//实时信息响应
						int state = StreamUtil.readByte(result);
						short chargeTime = StreamUtil.readShort(result);
						StreamUtil.readShort(result);//short dianya = 
						StreamUtil.readShort(result);//short dianliu = 
						int diandu = StreamUtil.readInt(result);
						short tempfeilv = StreamUtil.readShort(result);
						int feilv = tempfeilv & 0xffff ;
						int yuchong = StreamUtil.readInt(result);
						int yichong = StreamUtil.readInt(result);
						int soc = StreamUtil.readByte(result);
						if(6 == state){ 
							//充电中
							wave.startAnimation();
						}else {
							//stopAnimation();
							wave.stopAnimation();
						}
						
						tv_duration.setText(TimeUtil.getCutDownByMin(chargeTime));
						tv_electric.setText((float)diandu/100+"");
						tv_money.setText((float)yichong/100+"");
						if(5 == type){
							tv_soc.setText(soc+"%");
						}else {
							tv_soc.setText("--%");
						}
						break;
					case SocketConstant.CMD_TYPE_STOP_CHARGE:
						cancelPD();
						int stopflag = StreamUtil.readByte(result);
						if(0 == stopflag){
							short error = StreamUtil.readShort(result);
							Log.i("cm_socket", "停止充电失败原因"+ error);
							showErrorCode(error);
							slideUnlockView.reset();
						}else if(1 == stopflag){
							Log.i("cm_socket", "停止充电响应成功");
						}
						break;
					case SocketConstant.CMD_TYPE_YX:
						//遥信数据--桩是否断网
						int isNetWork = StreamUtil.readByte(result);
						switch (isNetWork) {
						case 1:
							//联网
							wave.startAnimation();
							break;

						case 0 :
							//断网
							cancelPD();
							wave.stopAnimation();
							showToast("电桩网络通信中断，请稍后再试...");
							break;
						}
						break;
					case SocketConstant.CMD_TYPE_CONNECT:
						int successflag = StreamUtil.readByte(result);
						short errorcode = StreamUtil.readShort(result);
						switch (successflag) {
						case 1:
							int headState = StreamUtil.readByte(result);
							type = StreamUtil.readByte(result);
							 if(3 == headState || 0 == headState){
								 showFinishChargeTipDialog();
							     wave.stopAnimation();
							     if(mDialog != null && mDialog.isShowing()){
							    	 mDialog.dismiss();
							     }
							     mTcpSocketManager.close();
								 
								}else if(6 == headState){
									 wave.startAnimation();
								}  
							break;
						case 0:
							if(errorcode == 110){
								slideUnlockView.reset();
							}
							showErrorCode(errorcode);
							break;
						}
						
						break;
					case SocketConstant.CMD_TYPE_CONSUME_RECORD:
						cancelPD();
						Intent chargefinishIn = new Intent();
						chargefinishIn.setAction(BroadcastUtil.BROADCAST_Charge_CANCLE);
						sendBroadcast(chargefinishIn);
						PreferencesUtil.setPreferences(RealTimeChargeActivity.this,"chargepilenum","");
			        	PreferencesUtil.setPreferences(RealTimeChargeActivity.this,"chargeheadnum","");
						//发送广播，通知充电结束，地图地标消失，
						String order = new String(StreamUtil.readWithLength(result, 21));
						long temps = (long) StreamUtil.readInt(result);
						long tempe = (long) StreamUtil.readInt(result);
						long duration = tempe - temps;
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分",
								Locale.getDefault());
						String startdate = sdf.format(new Date(temps * 1000));
						String enddate = sdf.format(new Date(tempe * 1000));
						float power = (float) StreamUtil
								.readInt(result) / 1000;
						float electricMoney = (float) StreamUtil
								.readInt(result) / 100;
						float serviceMoney = (float) StreamUtil
								.readInt(result) / 100;
						String pilePK = String.valueOf(StreamUtil.readInt(result));
						int isFirst = StreamUtil.readByte(result);
						float coupon = (float)StreamUtil.readInt(result)/100;
						float reduce = (float)StreamUtil.readInt(result)/100;
						LogUtil.i("cm_netPost","coupon----------------------------------== " + coupon+"---------reduce= "+reduce);
						Intent recardIn = new Intent(RealTimeChargeActivity.this,
								ShareDetailActivity.class);
						recardIn.putExtra("order", order);
						recardIn.putExtra("sart", startdate);
						recardIn.putExtra("end", enddate);
						recardIn.putExtra("duration", duration);//时长
						recardIn.putExtra("power", power);//总电量
						recardIn.putExtra("electricMoney", electricMoney);//充电金额
						recardIn.putExtra("serviceMoney", serviceMoney);//服务费
						recardIn.putExtra("pilePK", pilePK);//电桩主键
						recardIn.putExtra("isFirst", isFirst);//是否首次体验 
						recardIn.putExtra("coupon", coupon);//优惠券面额
						recardIn.putExtra("reduce", reduce);//优惠券抵扣 金额
						wave.stopAnimation();
						startActivity(recardIn);
						finish();
						break;
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.realtime_charge_back:
			mTcpSocketManager.close();
			Intent backIn = new Intent();
			if (getIn.getStringExtra("interfacefrom").equals("pile")) {
				backIn.setClass(RealTimeChargeActivity.this, HomeActivity.class);
				backIn.putExtra("tag", "1");
				startActivity(backIn);
			}else if (getIn.getStringExtra("interfacefrom").equals("shaomiao")) {
				backIn.setClass(RealTimeChargeActivity.this, HomeActivity.class);
				backIn.putExtra("tag", "1");
				startActivity(backIn);
			}else if (getIn.getStringExtra("interfacefrom").equals("person")) {
				backIn.setClass(RealTimeChargeActivity.this, HomeActivity.class);
				backIn.putExtra("tag", "3");
				startActivity(backIn);
			}else if (getIn.getStringExtra("interfacefrom").equals("mapmob")) {
				backIn.setClass(RealTimeChargeActivity.this, HomeActivity.class);
				backIn.putExtra("tag", "2");
				startActivity(backIn);
			}else if (getIn.getStringExtra("interfacefrom").equals("home")) {
				backIn.setClass(RealTimeChargeActivity.this, HomeActivity.class);
				backIn.putExtra("tag", "1");
				startActivity(backIn);
			}
			finish();
			break;
		default:
			break;
		}
		
	}
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK
			&& event.getAction() == KeyEvent.ACTION_DOWN) {
		mTcpSocketManager.close(); 
		Intent backIn = new Intent();
		if (getIn.getStringExtra("interfacefrom").equals("pile")) {
			backIn.setClass(RealTimeChargeActivity.this, HomeActivity.class);
			backIn.putExtra("tag", "1");
			startActivity(backIn);
		}else if (getIn.getStringExtra("interfacefrom").equals("shaomiao")) {
			backIn.setClass(RealTimeChargeActivity.this, HomeActivity.class);
			backIn.putExtra("tag", "1");
			startActivity(backIn);
		}else if (getIn.getStringExtra("interfacefrom").equals("person")) {
			backIn.setClass(RealTimeChargeActivity.this, HomeActivity.class);
			backIn.putExtra("tag", "3");
			startActivity(backIn);
		}else if (getIn.getStringExtra("interfacefrom").equals("mapmob")) {
			backIn.setClass(RealTimeChargeActivity.this, HomeActivity.class);
			backIn.putExtra("tag", "2");
			startActivity(backIn);
		}else if (getIn.getStringExtra("interfacefrom").equals("home")) {
			backIn.setClass(RealTimeChargeActivity.this, HomeActivity.class);
			backIn.putExtra("tag", "1");
			startActivity(backIn);
		}
		finish();
		
		return true;
	}
	return super.onKeyDown(keyCode, event);
}
	@Override
	protected void onStart() {
		super.onStart();
	}
	

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mFinishChargeDialog != null && mFinishChargeDialog.isShowing()){
			mFinishChargeDialog.dismiss();
		}
//		mTcpSocketManager.close();
		unregisterReceiver(mBroadcastReceiver);
		
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onSuccess(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}
	public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Force_Offline); 
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_TCP_NETWORK); 
        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Charge_CANCLE); 
        //注册广播        
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    }  
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			  String action = intent.getAction();
			  if(action.equals(BroadcastUtil.BROADCAST_Force_Offline)){ 
				  mTcpSocketManager.close();
				  //stopAnimation();
			 }else if(action.equals(BroadcastUtil.BROADCAST_TCP_NETWORK)){ 
				 showToast(getResources().getString(R.string.network_tips));
			 }else if(action.equals(BroadcastUtil.BROADCAST_Charge_CANCLE)){ 
				 //充电结束，极光推送
				 wave.stopAnimation();
				 runOnUiThread(new Runnable() {
					@Override
					public void run() {
						showFinishChargeTipDialog();
					}
				});
				
			 }
		}
	};
	
	 //网络变化广播接收器
	 public  class NetWorkReceiver extends BroadcastReceiver {  
	        @Override  
	        public void onReceive(Context context, Intent intent) {  
	        	
	        	if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
	        		if(isNetConnection() && isRunningForeground(RealTimeChargeActivity.this)){
	        			Log.i("cm_socket", "网络变化，skcket重连");
	        			mTcpSocketManager.reopen();
	        		}
	        	}
	        }  
	    }

	@Override
	public void setUnLocked() {
		showFinishChargeDialog();
	}
	@Override
	public void onHidden(boolean hidden) {
		if(hidden){
			tv_unlock_tip.setVisibility(View.GONE);
		}else {
			tv_unlock_tip.setVisibility(View.VISIBLE);
		}
	}  
	
	private void showFinishChargeTipDialog(){
		if(mFinishChargeDialog == null){
			mFinishChargeDialog = new ChargeFinishTipDialog(RealTimeChargeActivity.this);
			mFinishChargeDialog.setCancelable(false);
			mFinishChargeDialog.setOnPositiveListener(new OnClickListener() {
					@Override 
					public void onClick(View v) {
						mFinishChargeDialog.dismiss();
						finish();
					}
				});
		}
		 mFinishChargeDialog.show();
	}
	@Override
	public void handleSocketException() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showErrorCode(110);
			}
		});
		
	}

}
