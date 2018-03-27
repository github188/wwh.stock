package com.bm.wanma.ui.activity;

import java.util.ArrayList;

import pulltorefresh.ListViewCustom;
import pulltorefresh.PullToRefreshBase;
import pulltorefresh.PullToRefreshScrollView;
import pulltorefresh.PullToRefreshBase.OnRefreshListener2;

import com.bm.wanma.R;

import com.bm.wanma.adapter.MyBillsOneAdapter;
import com.bm.wanma.adapter.RechargeRecordAdapter;

import com.bm.wanma.entity.BillOneBean;
import com.bm.wanma.entity.RechargeRecordBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.view.PullToRefreshListView;
import com.bm.wanma.view.PullToRefreshListView.OnLoadListener;
import com.bm.wanma.view.PullToRefreshListView.OnRefreshListener;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author lyh
 * 充值记录
 */
public class RechargeRecordActivity extends BaseActivity implements OnRefreshListener2<ScrollView>{
	
	private ImageView iv_recharge_record_back;
	private ListViewCustom listview;
	private TextView tv_nodata;
	private PullToRefreshScrollView pScrollView;
	private final String pageNum = "20";
	private int currentPage;
	private boolean isRefresh;
	private String pkuserId;
	private ArrayList<RechargeRecordBean> rawdata,data;
	private RechargeRecordAdapter maAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge_record);
		initView();
	}
	
	private void initView(){

		iv_recharge_record_back = (ImageView) findViewById(R.id.activity_recharge_record_back);
		iv_recharge_record_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tv_nodata = (TextView) findViewById(R.id.recharge_record__nodata);
		tv_nodata.setText("暂无数据");
		listview = (ListViewCustom) findViewById(R.id.recharge_record_listview);
		pScrollView = (PullToRefreshScrollView) findViewById(R.id.recharge_record_refresh);
		pScrollView.setOnRefreshListener(this);
		listview.setOverScrollMode(View.OVER_SCROLL_NEVER);
		// 默认加载第一页数据
		pkuserId = PreferencesUtil.getStringPreferences(getActivity(),"pkUserinfo");
		currentPage = 1;
		isRefresh = true;
		rawdata = new ArrayList<RechargeRecordBean>();
		data = new ArrayList<RechargeRecordBean>();
		if(isNetConnection()){
			GetDataPost.getInstance(this).getMyRechargeList(handler, pkuserId, String.valueOf(currentPage), pageNum);
		}
	}
	

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (bundle != null) {
			
			pScrollView.onRefreshComplete();
			rawdata = (ArrayList<RechargeRecordBean>) bundle.getSerializable(Protocol.DATA);
			if(rawdata.size()<20){
				pScrollView.pullsettins = false;
			}else {
				pScrollView.pullsettins = true;
			}
			if(isRefresh){
					data.clear();
					data.addAll(rawdata);
			}else {
				
				if(rawdata.size()>0){
					data.addAll(rawdata);
				}else {
					showToast("暂无更多数据");
				}
			}
			
			if(data.size()>0){
				if(maAdapter == null){
					maAdapter = new RechargeRecordAdapter(getActivity(), data);
					listview.setAdapter(maAdapter);
//					listview.setOnItemClickListener(new OnItemClickListener() {
//
//						@Override
//						public void onItemClick(AdapterView<?> parent, View view,
//								int position, long id) {
//							maAdapter.getView(position-1, view, listview);
//						}
//					});
				}
//				tv_nodata.setVisibility(View.GONE);
				listview.setVisibility(View.VISIBLE);
				maAdapter.notifyDataSetChanged();
			}else {
//					tv_nodata.setVisibility(View.VISIBLE);
					listview.setVisibility(View.GONE);
			}
	}
		
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		showToast(bundle.getString(Protocol.MSG));
		finish();
	}



	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// 下拉刷新
				String label = (String) DateFormat.format("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()); 
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新："+label);
				isRefresh = true;
				currentPage = 1;
				if(isNetConnection()){
					GetDataPost.getInstance(this).getMyRechargeList(handler, pkuserId, String.valueOf(currentPage), pageNum);
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
					GetDataPost.getInstance(this).getMyRechargeList(handler, pkuserId, String.valueOf(currentPage), pageNum);
				}else {
					showToast("网络不稳，请稍后再试");
				}
	}

	@Override
	public void onPullUpToRefreshNoData(
			PullToRefreshBase<ScrollView> refreshView) {
		showToast("暂无更多数据");
		
	}

}
