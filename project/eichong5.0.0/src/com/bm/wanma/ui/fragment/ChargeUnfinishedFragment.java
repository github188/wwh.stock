package com.bm.wanma.ui.fragment;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import pulltorefresh.ListViewCustom;
import pulltorefresh.PullToRefreshBase;
import pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import pulltorefresh.PullToRefreshScrollView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;
import com.bm.wanma.R;
import com.bm.wanma.adapter.ChargeFinishAndDoneAdapter;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.entity.ChargeFinishAndDoneBean;
import com.bm.wanma.entity.ScanInfoBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.socket.SocketConstant;
import com.bm.wanma.socket.StreamUtil;
import com.bm.wanma.socket.TCPSocketManager;
import com.bm.wanma.ui.activity.ChargeDetailActivity;
import com.bm.wanma.ui.activity.ITcpCallBack;
import com.bm.wanma.ui.activity.PileDetailActivity;
import com.bm.wanma.ui.activity.RealTimeChargeActivity;
import com.bm.wanma.ui.fragment.BaseFragment;
import com.bm.wanma.utils.PreferencesUtil;

public class ChargeUnfinishedFragment extends BaseFragment implements OnItemClickListener, ITcpCallBack, OnRefreshListener2<ScrollView>{
	
	private ListViewCustom listview;
//	private TextView tv_nodata;
	private PullToRefreshScrollView pScrollView;
	private final String pageNum = "20";
	private int currentPage;
	private boolean isRefresh;
	private String pkuserId;
	private ArrayList<ChargeFinishAndDoneBean> rawdata,beans;
	private ChargeFinishAndDoneBean finishbean;
	private ChargeFinishAndDoneAdapter maAdapter;
	private TCPSocketManager mTcpSocketManager;
	private ScanInfoBean mScanInfoBean;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 默认加载第一页数据
		registerBoradcastReceiver();
		pkuserId = PreferencesUtil.getStringPreferences(getActivity(),"pkUserinfo");
		currentPage = 1;
		isRefresh = true;
	}
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragment = inflater.inflate(
				R.layout.fragment_charge_done, container, false);
//		tv_nodata = (TextView) fragment.findViewById(R.id.charge_done_nodata);
//		tv_nodata.setText("暂无");
		listview = (ListViewCustom) fragment.findViewById(R.id.charge_done_finish_listview);
		pScrollView = (PullToRefreshScrollView) fragment.findViewById(R.id.charge_done_finish_refresh);
		pScrollView.setOnRefreshListener(this);
		rawdata = new ArrayList<ChargeFinishAndDoneBean>();
		beans = new ArrayList<ChargeFinishAndDoneBean>();
		if(isNetConnection()){
			GetDataPost.getInstance(getActivity()).getChargeInfoList(handler, pkuserId, "0", String.valueOf(currentPage), pageNum);
		}
		return fragment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if(bundle != null ){

			if (sign.equals(Protocol.INDENT_PARTICULARS)) {
				pScrollView.onRefreshComplete();
				rawdata = (ArrayList<ChargeFinishAndDoneBean>) bundle.getSerializable(Protocol.DATA);
				if(rawdata.size()<20){
					pScrollView.pullsettins = false;
				}else {
					pScrollView.pullsettins = true;
				}
				if(isRefresh){
						beans.clear();
						beans.addAll(rawdata);

				}else {
					
					if(rawdata.size()>0){
						beans.addAll(rawdata);
					}else {
						showToast("暂无更多数据");
					}
				}
				
				if(beans.size()>0){
					if(maAdapter == null){
						maAdapter = new ChargeFinishAndDoneAdapter(getActivity(), beans);
						listview.setAdapter(maAdapter);
						
					}
					maAdapter.notifyDataSetChanged();
					listview.setOnItemClickListener(this);
				}
			} else if (sign.equals(Protocol.GET_SCAN_INFO)) {
				mScanInfoBean = (ScanInfoBean) bundle.getSerializable(Protocol.DATA);
				mScanInfoBean.setPrechargeMoney(""+finishbean.getChOr_ChargeMoney());
				showPD("正在获取充电信息...");
				mTcpSocketManager = TCPSocketManager.getInstance(getActivity());
				mTcpSocketManager.setTcpCallback(this);
				if(!mTcpSocketManager.hasTcpConnection()){
					mTcpSocketManager.conn(finishbean.getElPi_ElectricPileCode(), 
							Byte.parseByte(finishbean.getHeadCode()));
				} else {
					showToast("通讯连接异常，请稍后重试...");
					mTcpSocketManager.reopen();
				}
			}
		}
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		pScrollView.onRefreshComplete();
		showToast(bundle.getString(Protocol.MSG));
	}



	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// 下拉刷新
		String label = (String) DateFormat.format("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()); 
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新："+label);
		isRefresh = true;
		currentPage = 1;
		if(isNetConnection()){
			GetDataPost.getInstance(getActivity()).getChargeInfoList(handler, pkuserId, "0", String.valueOf(currentPage), pageNum);
		}else {
			showToast("网络不稳，请稍后再试");
		}

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// 上拉加载
		isRefresh = false;
		//获取下一页数据
		currentPage ++;
		if(isNetConnection()){
			GetDataPost.getInstance(getActivity()).getChargeInfoList(handler, pkuserId, "0", String.valueOf(currentPage), pageNum);
		}else {
			showToast("网络不稳，请稍后再试");
		}
		
	}
	@Override
	public void onPullUpToRefreshNoData(PullToRefreshBase<ScrollView> refreshView) {
		showToast("暂无更多数据");
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
						realIn.putExtra("interfacefrom", "chongdian");
						//mTcpSocketManager.close();
						startActivity(realIn);
						
						break;
					case SocketConstant.CMD_TYPE_CONNECT:
						int successflag = StreamUtil.readByte(result);
						short errorcode = StreamUtil.readShort(result);
						int headType = StreamUtil.readByte(result);
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
							
//							if (17 == headType) {							
//								Intent charlIn = new Intent(getActivity(),
//										PileDetailActivity.class);
//								charlIn.putExtra("interfacefrom", "chongdian");
//								charlIn.putExtra("awaitType", 1);//1等待 0详情
//								charlIn.putExtra("scanInfo", mScanInfoBean);
//								startActivity(charlIn);
//							}
							//连接成功
							if(headType == 0){
								showToast("充电枪空闲，此订单已结束");
								PreferencesUtil.setPreferences(getActivity(),"chargepilenum","");
					        	PreferencesUtil.setPreferences(getActivity(),"chargeheadnum","");
					        	mTcpSocketManager.close();
							}
							break;
						case 0:
							showErrorCode(errorcode);
							break;
						default:
							break;
						}
						
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mTcpSocketManager != null){
			mTcpSocketManager.close();
		}
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}
	 public void registerBoradcastReceiver(){  
	        IntentFilter myIntentFilter = new IntentFilter();  
	        myIntentFilter.addAction(BroadcastUtil.BROADCAST_Charge_CANCLE); 
	        //注册广播        
	        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);  
	    }  
	 
	//取消充电返回列表时重新获取数据
			private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
				@Override
				public void onReceive(Context context, Intent intent) {
					String action = intent.getAction();
					 if(action.equals(BroadcastUtil.BROADCAST_Charge_CANCLE)){  
						 currentPage=1;
						 GetDataPost.getInstance(getActivity()).getChargeInfoList(handler, pkuserId, "0", String.valueOf(currentPage), pageNum);
			         }  
				}  
		          
		    };
		    
			public void showErrorCode(int error){
				switch (error) {
				case 110:
					showToast(getResources().getString(R.string.tip_error_110));
					break;
				case 1002:
					showToast(getResources().getString(R.string.tip_error_1002));
					break;
				case 6000:
					showToast(getResources().getString(R.string.tip_error_6000));
					break;
				case 6001:
					showToast(getResources().getString(R.string.tip_error_6001));
					break;
				case 6100:
					showToast(getResources().getString(R.string.tip_error_6100));
					break;
				case 6101:
					showToast(getResources().getString(R.string.tip_error_6101));
					break;
				case 6102:
					showToast(getResources().getString(R.string.tip_error_6102));
					break;	
				case 6104:
					showToast(getResources().getString(R.string.tip_error_6104));
					break;
				case 6105:
					showToast(getResources().getString(R.string.tip_error_6105));
					break;
				case 6200:
					showToast(getResources().getString(R.string.tip_error_6200));
					break;
				case 6201:
					showToast(getResources().getString(R.string.tip_error_6201));
					break;
				case 6202:
					showToast(getResources().getString(R.string.tip_error_6202));
					break;
				case 6300:
					showToast(getResources().getString(R.string.tip_error_6300));
					break;
				case 6301:
					showToast(getResources().getString(R.string.tip_error_6301));
					break;
				case 6401:
					showToast(getResources().getString(R.string.tip_error_6401));
					break;
				case 6402:
					showToast(getResources().getString(R.string.tip_error_6402));
					break;
				case 6403:
					showToast(getResources().getString(R.string.tip_error_6403));
					break;
				case 6404:
					showToast(getResources().getString(R.string.tip_error_6404));
					break;
				case 6405:
					showToast(getResources().getString(R.string.tip_error_6405));
					break;
				case 6406:
					showToast(getResources().getString(R.string.tip_error_6406));
					break;
				case 6600:
					showToast(getResources().getString(R.string.tip_error_6600));
					break;	
				case 6601:
					showToast(getResources().getString(R.string.tip_error_6601));
					break;	
				case 6633:
					showToast(getResources().getString(R.string.tip_error_6633));
					break;	
				case 6700:
					showToast(getResources().getString(R.string.tip_error_6700));
					break;
				case 6701:
					showToast(getResources().getString(R.string.tip_error_6701));
					break;
				case 6702:
					showToast(getResources().getString(R.string.tip_error_6702));
					break;
				case 6703:
					showToast(getResources().getString(R.string.tip_error_6703));
					break;
				case 6704:
					showToast(getResources().getString(R.string.tip_error_6704));
					break;
				case 6705:
					showToast(getResources().getString(R.string.tip_error_6705));
					break; 
				case 6706:
					showToast(getResources().getString(R.string.tip_error_6706));
					break; 
				case 6800:
					showToast(getResources().getString(R.string.tip_error_6800));
					break;
				case 6801:
					showToast(getResources().getString(R.string.tip_error_6801));
					break;
				case 6802:
					showToast(getResources().getString(R.string.tip_error_6802));
					break;
				case 6803:
					showToast(getResources().getString(R.string.tip_error_6803));
					break;
				case 6804:
					showToast(getResources().getString(R.string.tip_error_6804));
					break;
				default:
					showToast("未知原因");
					break;
				}
				
			}

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
						if("17".equals(beans.get(position).getChOr_ChargingStatus())){
							finishbean = beans.get(position);
//							GetDataPost.getInstance(getActivity()).getScanInfo(handler,beans.get(0).getElPi_ElectricPileCode(),beans.get(0).getHeadCode());
						}else 
							if("1".equals(beans.get(position).getChOr_ChargingStatus())){
							showPD("正在获取充电信息...");
							mTcpSocketManager = TCPSocketManager.getInstance(getActivity());
							mTcpSocketManager.setTcpCallback(this);
							if(!mTcpSocketManager.hasTcpConnection()){
								mTcpSocketManager.conn(beans.get(position).getElPi_ElectricPileCode(), 
										Byte.parseByte(beans.get(position).getHeadCode()));
							} else {
								showToast("通讯连接异常，请稍后重试...");
								mTcpSocketManager.reopen();
							}
						}else {
							Intent in = new Intent();
							in.setClass(getActivity(),ChargeDetailActivity.class);
							in.putExtra("ordernumber", beans.get(position).getPk_ChargingOrder());
							startActivity(in);
						}
						

					
			}

			@Override
			public void handleSocketException() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						cancelPD();
						showErrorCode(110);
					}
				});
				
			} 
}
