package com.bm.wanma.adapter;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.bm.wanma.R;
import com.bm.wanma.entity.ListModeBean;
import com.bm.wanma.ui.activity.LoginActivity;
import com.bm.wanma.ui.navigation.NaviCustomActivity;
import com.bm.wanma.ui.navigation.TTSController;
import com.bm.wanma.ui.navigation.Utils;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class backMyListModeListViewAdapter extends BaseAdapter implements
		AMapNaviListener, AMapNaviViewListener {

	// 实体类
	private List<ListModeBean> mdata;
	private Context mcontext;
	private LayoutInflater inflater;

	// 起点终点列表
	private ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
	private ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
	private ProgressDialog mRouteCalculatorProgressDialog;// 路径规划过程显示状态

	private String geoLat;
	private Double startGeoLat;
	private String geoLng;
	private Double startGeoLng;
	private String edoLat;
	private Double endEdoLat;
	private String edoLng;
	private Double endEdoLng;
	private String pkUserinfo;
	
	public backMyListModeListViewAdapter(Context context,
			List<ListModeBean> data) {
		this.mdata = data;
		this.mcontext = context;
		inflater = LayoutInflater.from(context);
		pkUserinfo = PreferencesUtil.getStringPreferences(mcontext,"pkUserinfo");
		// init();
	}

	private void init(ListModeBean navbean) {
		TTSController.getInstance(mcontext).startSpeaking();
		//获取当前经纬度
		geoLat = PreferencesUtil.getStringPreferences(mcontext, "currentlat");
		geoLng = PreferencesUtil.getStringPreferences(mcontext, "currentlng");
		startGeoLat = Double.parseDouble(geoLat);
		startGeoLng = Double.parseDouble(geoLng);
		// 获取目的地经纬度
		edoLat = navbean.getElectricLatitude();
		edoLng = navbean.getElectricLongitude();
		Log.i("fragment", navbean.getElectricName()+edoLat+edoLng);
		if (!Tools.isEmptyString(edoLat) && !Tools.isEmptyString(edoLng)) {
			endEdoLat = Double.parseDouble(edoLat.trim());
			endEdoLng = Double.parseDouble(edoLng.trim());
			NaviLatLng mNaviStart = new NaviLatLng(startGeoLat, startGeoLng);
			NaviLatLng mNaviEnd = new NaviLatLng(endEdoLat, endEdoLng);
			mStartPoints.clear();
			mEndPoints.clear();
			mStartPoints.add(mNaviStart);
			mEndPoints.add(mNaviEnd);
		}

		mRouteCalculatorProgressDialog = new ProgressDialog(mcontext);
		mRouteCalculatorProgressDialog.setCancelable(true);
		AMapNavi aMapNavi = AMapNavi.getInstance(mcontext);
		if (this instanceof AMapNaviListener && aMapNavi != null) {
			aMapNavi.setAMapNaviListener(this);
		}

	}

	@Override
	public int getCount() {
		return mdata.size();
	}

	@Override
	public Object getItem(int position) {
		return mdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View conventview, ViewGroup arg2) {
		TextView tv_name = null;
		TextView tv_distance = null;
		TextView tv_address = null;
		TextView tv_fastNum = null;
		TextView tv_fast_idle = null;
		TextView tv_slowNum = null;
		TextView tv_slow_idle = null;
		TextView tv_price = null;
		TextView tv_score = null;
		TextView tv_bespoke = null;
		LinearLayout ll_navgation= null;
		LinearLayout ll_bespoke = null;
	    ImageView iv_bespoke = null;
	    RelativeLayout rl_listview_addr = null;

		if (conventview == null) {
			conventview = inflater.inflate(R.layout.listview_item_fragment_listmode,
					null);
			tv_name = (TextView) conventview
					.findViewById(R.id.listview_name);
			tv_distance = (TextView) conventview
					.findViewById(R.id.listview_distance);
			tv_address = (TextView) conventview
					.findViewById(R.id.listview_addr);
			tv_fastNum = (TextView) conventview
					.findViewById(R.id.listview_fast_num);
			tv_fast_idle = (TextView) conventview
					.findViewById(R.id.listview_fast_idle);
			tv_slowNum = (TextView) conventview
					.findViewById(R.id.listview_slow_num);
			tv_slow_idle = (TextView) conventview
					.findViewById(R.id.listview_slow_idle);
			tv_price = (TextView) conventview
					.findViewById(R.id.listview_tv_price);
			tv_score = (TextView) conventview
					.findViewById(R.id.listview_tv_score);
			tv_bespoke = (TextView) conventview
					.findViewById(R.id.listview_tv_bespoke);
			iv_bespoke = (ImageView)conventview.findViewById(R.id.listview_iv_bespoke);
			ll_navgation = (LinearLayout)conventview.findViewById(R.id.listview_navgation);
			ll_bespoke = (LinearLayout)conventview.findViewById(R.id.listview_bespoke);
			rl_listview_addr = (RelativeLayout)conventview.findViewById(R.id.listview_rl_addr);
			
			// 保存conventview对象到ObjectClass类中
			conventview.setTag(new ObjectClass(tv_name,
					tv_distance, tv_address,
					tv_fastNum, tv_fast_idle,
					tv_slowNum, tv_slow_idle,tv_price,tv_score,tv_bespoke,
					ll_navgation,ll_bespoke,iv_bespoke,rl_listview_addr
					));

		} else {
			// 得到保存的对象
			ObjectClass objectclass = (ObjectClass) conventview.getTag();
			tv_name = objectclass.obj_tv_name;
			tv_distance = objectclass.obj_tv_distance;
			tv_address = objectclass.obj_tv_address;
			tv_fastNum = objectclass.obj_tv_fastNum;
			tv_fast_idle = objectclass.obj_tv_fast_idle;
			tv_slowNum = objectclass.obj_tv_slowNum;
			tv_slow_idle = objectclass.obj_tv_slow_idle;
			tv_price = objectclass.obj_tv_price;
			tv_score = objectclass.obj_tv_score;
			tv_bespoke = objectclass.obj_tv_bespoke;
			ll_navgation = objectclass.obj_ll_navgation;
			ll_bespoke = objectclass.obj_ll_bespoke;
			iv_bespoke = objectclass.obj_iv_bespoke;
			rl_listview_addr = objectclass.obj_rl_addr;
		}
		
		ListModeBean listBean = (ListModeBean) mdata
				.get(position);
		if(listBean != null){
			if(!Tools.isEmptyString(listBean.getElectricName())){
				tv_name.setText(listBean.getElectricName());
			}
			if(!Tools.isEmptyString(listBean.getElectricAddress())){
				tv_address.setText(listBean.getElectricAddress());
			}
			String distance = listBean.getElectricDistance();
			if(!Tools.isEmptyString(distance)){
				distance = Tools.getMeterOrKM(distance);
				tv_distance.setText(distance);
			}
			if(!Tools.isEmptyString(listBean.getZlHeadNum())){
				tv_fastNum.setText(listBean.getZlHeadNum());
			}
			if(!Tools.isEmptyString(listBean.getZlFreeHeadNum())){
				tv_fast_idle.setText(listBean.getZlFreeHeadNum());
			}
			if(!Tools.isEmptyString(listBean.getJlHeadNum())){
				tv_slowNum.setText(listBean.getJlHeadNum());
			}
			if(!Tools.isEmptyString(listBean.getJlHeadNum())){
				tv_slow_idle.setText(listBean.getJlHeadNum());
			}
			if(!Tools.isEmptyString(listBean.getServiceCharge())){
				tv_price.setText(listBean.getServiceCharge()+"元/度");
			}
			if(!Tools.isEmptyString(listBean.getCommentStart())){
				tv_score.setText(listBean.getCommentStart()+"分");
			}
			
			//1支持预约 0不支持
			ll_bespoke.setTag(position);
			if("1".equals(listBean.getIsAppoint())){
					tv_bespoke.setTextColor(mcontext.getResources().getColor(
							R.color.common_orange));
					iv_bespoke.setImageResource(R.drawable.pop_anchor_bespoke);
					//点击预约进入详情界面
					ll_bespoke.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							int pos = Integer.parseInt(v.getTag().toString());
							ListModeBean listBean = mdata.get(pos);	
							if(Tools.isEmptyString(pkUserinfo)){
								Log.i("fragment", "请登录");
							}else 
							Log.i("fragment", listBean.getElectricName());
							
						}
					});
			}
	
			// 添加导航图标点击事件
			ll_navgation.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ListModeBean listBean = (ListModeBean) mdata.get(position);
					Log.i("fragment", "position"+position);
					// 先初始化导航数据
					init(listBean);
					// 启动FPS导航
					if (AMapNavi.getInstance(mcontext) != null) {
						AMapNavi.getInstance(mcontext).calculateDriveRoute(
								mStartPoints, mEndPoints, null,
								AMapNavi.DrivingDefault);
						mRouteCalculatorProgressDialog.show();
					}
				}
			});
			//点击进入详情界面
			rl_listview_addr.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(!Tools.isEmptyString(pkUserinfo)){
						ListModeBean listBean = (ListModeBean) mdata.get(position);
						System.out.println("ListModeBean"+listBean.toString());
					}else {
						Intent loginIn = new Intent();
						loginIn.putExtra("source_inteface", "login");
						loginIn.setClass(mcontext, LoginActivity.class);
						mcontext.startActivity(loginIn);
						
					}
				}
			});
			
		}
		
		return conventview;

	}

	private final class ObjectClass {
		TextView obj_tv_name = null;
		TextView obj_tv_distance = null;
		TextView obj_tv_address = null;
		TextView obj_tv_fastNum = null;
		TextView obj_tv_fast_idle = null;
		TextView obj_tv_slowNum = null;
		TextView obj_tv_slow_idle = null;
		TextView obj_tv_price = null;
		TextView obj_tv_score = null;
		TextView obj_tv_bespoke = null;
		LinearLayout obj_ll_navgation= null;
		LinearLayout obj_ll_bespoke = null;
	    ImageView obj_iv_bespoke = null;
	    RelativeLayout obj_rl_addr = null;

		public ObjectClass(TextView name, TextView distance, TextView address,
				TextView fastNum, TextView fast_idle, TextView slowNum,
				TextView slow_idle, TextView price, TextView score,
				TextView bespoke, LinearLayout ll_navgation,
				LinearLayout ll_bespoke, ImageView iv_bespoke,RelativeLayout rl_addr) {
			this.obj_tv_name = name;
			this.obj_tv_distance = distance;
			this.obj_tv_address = address;
			this.obj_tv_fastNum = fastNum;
			this.obj_tv_fast_idle = fast_idle;
			this.obj_tv_slowNum = slowNum;
			this.obj_tv_slow_idle = slow_idle;
			this.obj_tv_price = price;
			this.obj_tv_score = score;
			this.obj_tv_bespoke = bespoke;
			this.obj_ll_navgation = ll_navgation;
			this.obj_ll_bespoke = ll_bespoke ;
			this.obj_iv_bespoke = iv_bespoke;
			this.obj_rl_addr = rl_addr;
		}
	}

	@Override
	public void onNaviCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviMapMode(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviSetting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviTurnClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNextRoadClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScanViewButtonClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onArriveDestination() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onArrivedWayPoint(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCalculateRouteFailure(int arg0) {
		mRouteCalculatorProgressDialog.dismiss();

	}

	@Override
	public void onCalculateRouteSuccess() {
		System.out.println("ListModeBean*********");
		mRouteCalculatorProgressDialog.dismiss();
		Intent intent = new Intent(mcontext, NaviCustomActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		Bundle bundle = new Bundle();
		//bundle.putInt(Utils.ACTIVITYINDEX, Utils.LISTMODEFRAGMENT);
		bundle.putBoolean(Utils.ISEMULATOR, false);
		intent.putExtras(bundle);
		mcontext.startActivity(intent);
		// mcontext.finish();

	}

	@Override
	public void onEndEmulatorNavi() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetNavigationText(int arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGpsOpenStatus(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInitNaviFailure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInitNaviSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChange(AMapNaviLocation arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviInfoUpdated(AMapNaviInfo arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReCalculateRouteForTrafficJam() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReCalculateRouteForYaw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartNavi(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTrafficStatusUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLockMap(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNaviInfoUpdate(NaviInfo arg0) {
		// TODO Auto-generated method stub
		
	}


}
