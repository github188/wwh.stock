/*package com.bm.wanma.ui.fragment;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bm.wanma.R;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.socket.SocketConstant;
import com.bm.wanma.socket.StreamUtil;
import com.bm.wanma.socket.TCPSocketManager;
import com.bm.wanma.ui.activity.ChargeListActivity;
import com.bm.wanma.ui.activity.ITcpCallBack;
import com.bm.wanma.ui.activity.RealTimeChargeActivity;
import com.bm.wanma.ui.activity.SearchPointActivity;
import com.bm.wanma.ui.activity.StationStiltDetailActivity;
import com.bm.wanma.ui.activity.StationStiltDetailActivity.MapCallback;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;

*//**
 * 首页地图列表界面
 * @author cm
 *
 *//*
public class MapAndListFragment extends BaseFragment implements OnClickListener,MapCallback,ITcpCallBack{
	private BaseFragment currentFragment;
	private MapModeFragment mapModeF;
	private ImageView iv_charging_animation;
	private ImageButton switchBtn;
	private TextView tv_search;
	private boolean isMapMode;
	private String pilenum,headnum;
	private TCPSocketManager mTcpSocketManager;
	private AlphaAnimation animation = new AlphaAnimation(1, 0);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initFragment();
		isMapMode = true;
		StationStiltDetailActivity.callback(this); 
		registerBoradcastReceiver();
	}
	

	@TargetApi(19)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View pileFragment = inflater.inflate(
				R.layout.fragment_map_and_list, container, false);
		iv_charging_animation= (ImageView)pileFragment.findViewById(R.id.nav_charging);
		iv_charging_animation.setOnClickListener(this);
		switchBtn = (ImageButton)pileFragment.findViewById(R.id.swith_button);
		switchBtn.setOnClickListener(this);
		tv_search = (TextView) pileFragment.findViewById(R.id.nav_search);
		tv_search.setOnClickListener(this);
		return pileFragment;
	}

	@Override
	public void onResume() {
		super.onResume();
		pilenum = PreferencesUtil.getStringPreferences(getActivity(),"chargepilenum");
		headnum = PreferencesUtil.getStringPreferences(getActivity(),"chargeheadnum");
		if(!Tools.isEmptyString(pilenum) && !Tools.isEmptyString(headnum)){
			animation.setDuration(3000);
			animation.setRepeatCount(-1);
			iv_charging_animation.startAnimation(animation);
	    }else {
	    	iv_charging_animation.setVisibility(View.GONE);
	    }
		
	}
	
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mBroadcastReceiver);
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nav_charging:
			clickChargingBtn();//充电中点击事件
			break;
		case R.id.swith_button:
//			clickSwitchBtn();
			//进入充电点界面
			Intent chargelistIn = new Intent();
			chargelistIn.setClass(getActivity(),ChargeListActivity.class);
			startActivity(chargelistIn);
			break;
		case R.id.nav_search:
			//进入搜索界面
			Intent searchIn = new Intent();
		    searchIn.setClass(getActivity(),SearchPointActivity.class);
			startActivity(searchIn);
			break;
		default:
			break;
		}
	}
	
	
	
	@SuppressLint("NewApi")
	private void initFragment(){
		
		if(mapModeF == null){
			mapModeF = new MapModeFragment();
			
		}
		if(!mapModeF.isAdded()){
			getChildFragmentManager().beginTransaction().
			add(R.id.map_list_content_layout, mapModeF).commit();
			// 记录当前Fragment
			currentFragment = mapModeF;
		}
		
	}
	
	
	
	*//**
	 * 添加或者显示碎片
	 * @param transaction
	 * @param fragment
	 *//*
	private void addOrShowFragment(FragmentTransaction transaction,
			BaseFragment fragment) {
		if (currentFragment == fragment)
			return;

		if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
			transaction.hide(currentFragment)
					.add(R.id.map_list_content_layout, fragment).commit();
		} else {
			transaction.hide(currentFragment).show(fragment).commit();
		}

		currentFragment = fragment;
	}
  private void clickChargingBtn(){
	showPD("正在获取充电信息...");
	mTcpSocketManager = TCPSocketManager.getInstance(getActivity());
	mTcpSocketManager.setTcpCallback(this);
	if(!mTcpSocketManager.hasTcpConnection()){
		mTcpSocketManager.conn(pilenum, 
				Byte.parseByte(headnum));
	}
  }
	
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		// TODO Auto-generated method stub

	}


	@SuppressLint("NewApi")
	@Override
	public void map(String callback) {
		isMapMode = true;
		addOrShowFragment(getChildFragmentManager().beginTransaction(),mapModeF);
		//switchBtn.setBackground(getActivity().getResources().getDrawable(R.drawable.nav_btn_list));
		switchBtn.setImageResource(R.drawable.nav_btn_list);
	}

	//处理tcp报文
		@Override
		public void handleTcpPacket(final ByteArrayInputStream result) {
			//收到实时数据，进入实时数据界面
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					cancelPD();
				    try {
						StreamUtil.readByte(result);//int reason = 
						short cmdtype = StreamUtil.readShort(result);
						switch (cmdtype) {
						case SocketConstant.CMD_TYPE_REAL_DATA:
							handleRealDataEvent(result);
							break;
						case SocketConstant.CMD_TYPE_CONNECT:
							handleConnectEvent(result);
							break;
						default:
							break;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		
		//处理socket-实时数据事件
		private void handleRealDataEvent(ByteArrayInputStream result) throws IOException{
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
			Intent realIn = new Intent(getActivity(),
					RealTimeChargeActivity.class);
			realIn.putExtra("state", state);
			realIn.putExtra("chargeTime", chargeTime);
			realIn.putExtra("diandu", diandu);
			realIn.putExtra("feilv", feilv);
			realIn.putExtra("yuchong", yuchong);
			realIn.putExtra("yichong", yichong);
			realIn.putExtra("soc", soc);
			//mTcpSocketManager.close();
			startActivity(realIn);
		}
		//处理socket-连接充电桩事件
		private void handleConnectEvent(ByteArrayInputStream result) throws IOException {
			int successflag = StreamUtil.readByte(result);
			short errorcode = StreamUtil.readShort(result);
			int headState = StreamUtil.readByte(result);
			int type = 1;
			 try {
				type = StreamUtil.readByte(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mTcpSocketManager.setPileType(type);
			switch (successflag) {
			case 1:
				//连接成功
				if(3 == headState || 0 == headState){
					 showToast("充电已结束");
					 PreferencesUtil.setPreferences(getActivity(),"chargepilenum","");
		        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum","");
		        	 iv_charging_animation.setVisibility(View.GONE);
		        	 mTcpSocketManager.close();
				}
				break;
			case 0:
				showErrorCode(errorcode);
				mTcpSocketManager.close();
				break;
			default:
				break;
			}
	   }
		
		public void registerBoradcastReceiver(){  
	        IntentFilter myIntentFilter = new IntentFilter();  
	        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Charge_CANCLE);
	        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Charge_Ing);
	        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Force_Offline);
	        //注册广播        
	        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);  
	    }  
		//更新地图点
		private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if(action.equals(BroadcastUtil.BROADCAST_Charge_CANCLE)){
		        	 //充电结束
		        	 PreferencesUtil.setPreferences(getActivity(),"chargepilenum","");
		        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum","");
		        	 iv_charging_animation.setVisibility(View.GONE);
		         } else if(action.equals(BroadcastUtil.BROADCAST_Charge_Ing)){
		        	 //充电进行中
		        	 pilenum = intent.getStringExtra("chargepilenum");
		        	 headnum = intent.getStringExtra("chargeheadnum");
		        	 PreferencesUtil.setPreferences(getActivity(),"chargepilenum",pilenum);
		        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum",headnum);
		        	 animation.setDuration(3000);
		 			 animation.setRepeatCount(-1);
		 			 iv_charging_animation.startAnimation(animation);
		        	  
		         } else if(action.equals(BroadcastUtil.BROADCAST_Force_Offline)){
		        	 //强制下线
		        	 iv_charging_animation.setVisibility(View.GONE);
		        	 PreferencesUtil.setPreferences(getActivity(),"chargepilenum","");
		        	 PreferencesUtil.setPreferences(getActivity(),"chargeheadnum","");
		         }
			}  
	    };
	
}
*/