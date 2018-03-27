package com.bm.wanma.ui.activity;

import java.util.ArrayList;
import java.util.Collections;

import pulltorefresh.ListViewCustom;
import pulltorefresh.PullToRefreshBase;
import pulltorefresh.PullToRefreshScrollView;
import pulltorefresh.PullToRefreshBase.OnRefreshListener2;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.bm.wanma.R;
import com.bm.wanma.adapter.IntegralDetailAdapter;
import com.bm.wanma.adapter.EverydayGetAdapter;
import com.bm.wanma.adapter.FunctionButtonAdapter;
import com.bm.wanma.adapter.IntegralDetailAdapter;
import com.bm.wanma.adapter.MyListModeListViewAdapter;
import com.bm.wanma.entity.IntegralDetailBean;
import com.bm.wanma.entity.EverydayGetBean;
import com.bm.wanma.entity.IntegralDetailBean;
import com.bm.wanma.entity.MapModeBean;
import com.bm.wanma.entity.PersonIntegralBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.ComparatorListMode;
import com.bm.wanma.utils.PreferencesUtil;

public class IntegralDetailActivity extends BaseActivity implements OnClickListener , OnRefreshListener2<ScrollView>{
	private ListViewCustom  listview;
	private TextView t_receive;
	private PullToRefreshScrollView pScrollView;
	private final String pageNum = "20";
	private int currentPage=1;
	private boolean isRefresh;
	private String pkuserId;
	private ArrayList<IntegralDetailBean> rawdata,beans;
	private IntegralDetailAdapter maAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_integral_detail);
//		integralsize = getIntent().getStringExtra("integralsize");
		isRefresh = true;
		pkuserId = PreferencesUtil.getStringPreferences(getActivity(), "pkUserinfo");
		initView();
		GetDataPost.getInstance(getActivity()).getIntegralPersonage(handler, pkuserId);
	}
	
 
	private void initView() {
		findViewById(R.id.integral_back).setOnClickListener(this);
		findViewById(R.id.integral_guizhe).setOnClickListener(this);
		listview = (ListViewCustom) findViewById(R.id.listview);
		t_receive = (TextView) findViewById(R.id.receive);
		
		pScrollView = (PullToRefreshScrollView) findViewById(R.id.integral_detail_refresh);
		pScrollView.setOnRefreshListener(this);
		rawdata = new ArrayList<IntegralDetailBean>();
		beans = new ArrayList<IntegralDetailBean>();
		if(isNetConnection()){
			GetDataPost.getInstance(getActivity()).getIntegralDetails(handler, pkuserId, String.valueOf(currentPage), pageNum);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.integral_back:
			finish();
			break;
		case R.id.integral_guizhe:
			Intent Instruction = new Intent();
			Instruction.setClass(getActivity(), CommonH5Activity.class);
			Instruction.putExtra("h5url", Protocol.INTEGRALRULES);
			Instruction.putExtra("h5title", "积分说明");
			getActivity().startActivity(Instruction);
			break;
		default:
			break;
		}
	}


	@Override
	protected void getData() {

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		pScrollView.onRefreshComplete();
		if(bundle != null ){
			if (sign.equals(Protocol.INTEGRAL_PERSONAGE)) {
				//个人积分
				if (bundle != null) {
				PersonIntegralBean personIntegralBean = (PersonIntegralBean) bundle.getSerializable(Protocol.DATA);
				if(personIntegralBean!= null){
					t_receive.setText(""+personIntegralBean.getPoint());
				}}
			}
			else if(sign.equals(Protocol.INTEGRAL_DETAILS_LIST)){
				
				rawdata = (ArrayList<IntegralDetailBean>) bundle.getSerializable(Protocol.DATA);
				if(rawdata.size()<20){
					pScrollView.pullsettins = false;
				}else {
					pScrollView.pullsettins = true;
				}
				if(isRefresh){
					if (beans.size()>0) {
						beans.clear();
					}
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
						maAdapter = new IntegralDetailAdapter(getActivity(), beans);
						listview.setAdapter(maAdapter);
						
					}
					
					maAdapter.notifyDataSetChanged();
				}
			}
//			pScrollView.getRefreshableView().fullScroll(pScrollView.FOCUS_UP);
		}
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
//		pScrollView.onRefreshComplete();
		showToast(bundle.getString(Protocol.MSG));
	}




//
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// 下拉刷新
		String label = (String) DateFormat.format("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()); 
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新："+label);
		isRefresh = true;
		currentPage = 1;
		if(isNetConnection()){
			GetDataPost.getInstance(getActivity()).getIntegralDetails(handler, pkuserId, String.valueOf(currentPage), pageNum);
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
			GetDataPost.getInstance(getActivity()).getIntegralDetails(handler, pkuserId, String.valueOf(currentPage), pageNum);
		}else {
			showToast("网络不稳，请稍后再试");
		}
		
	}
	@Override
	public void onPullUpToRefreshNoData(PullToRefreshBase<ScrollView> refreshView) {
		showToast("暂无更多数据");
	}
}
