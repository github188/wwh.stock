package com.bm.wanma.ui.activity;

import java.util.ArrayList;

import pulltorefresh.ListViewCustom;
import pulltorefresh.PullToRefreshBase;
import pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import pulltorefresh.PullToRefreshScrollView;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bm.wanma.R;
import com.bm.wanma.adapter.MyBillsOneAdapter;
import com.bm.wanma.entity.BillOneBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.TimeUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * @author 
 * 账单
 */
public class MyBillActivity extends BaseActivity implements OnRefreshListener2<ScrollView>{
	
	private ImageButton ib_back;
	private LinearLayout ll_mybill_month;
	private ListViewCustom listview;
	private TextView tv_nodata;
	private PullToRefreshScrollView pScrollView;
	private final String pageNum = "20";
	private int currentPage;
	private boolean isRefresh;
	private String pkuserId;
	private ArrayList<BillOneBean> data,rawdata;
	private MyBillsOneAdapter maAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mybill);
		mPageName = "MyBillActivity";
		initView();
	}
	
	private void initView(){
		ll_mybill_month = (LinearLayout) findViewById(R.id.mybill_month);
		ll_mybill_month.setVisibility(View.INVISIBLE);
		ll_mybill_month.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 本月账单
				Intent intent = new Intent();
				intent.putExtra("datatime", TimeUtil.getMothTime(System.currentTimeMillis()));
				intent.setClass(MyBillActivity.this, MyBillDetailsActivity.class);
				startActivity(intent);
			}
		});
		ib_back = (ImageButton) findViewById(R.id.invoice_mybill_back);
		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		tv_nodata = (TextView) findViewById(R.id.invoice_mybill__nodata);
		tv_nodata.setText("暂无数据");
		listview = (ListViewCustom) findViewById(R.id.invoice_mybill_listview);
		pScrollView = (PullToRefreshScrollView) findViewById(R.id.invoice_mybill_refresh);
		pScrollView.setOnRefreshListener(this);
		listview.setOverScrollMode(View.OVER_SCROLL_NEVER);
		// 默认加载第一页数据
		pkuserId = PreferencesUtil.getStringPreferences(getActivity(),"pkUserinfo");
		currentPage = 1;
		isRefresh = true;
		data = new ArrayList<BillOneBean>();
		rawdata = new ArrayList<BillOneBean>();
		if(isNetConnection()){
			GetDataPost.getInstance(this).getMyBillList(handler, pkuserId, String.valueOf(currentPage), pageNum);
		}
	}
	
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	MobclickAgent.onResume(mContext);
	MobclickAgent.onPageStart(mPageName);
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPause(mContext);
	MobclickAgent.onPageEnd(mPageName);
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
				rawdata = (ArrayList<BillOneBean>) bundle.getSerializable(Protocol.DATA);
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
						maAdapter = new MyBillsOneAdapter(getActivity(), data);
						listview.setAdapter(maAdapter);
						listview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
								maAdapter.getView(position, view, listview);
							}
						});
					}
//					tv_nodata.setVisibility(View.GONE);
					listview.setVisibility(View.VISIBLE);
					ll_mybill_month.setVisibility(View.VISIBLE);
					maAdapter.notifyDataSetChanged();
				}else {
//						tv_nodata.setVisibility(View.VISIBLE);
						listview.setVisibility(View.GONE);
						ll_mybill_month.setVisibility(View.INVISIBLE);
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
					GetDataPost.getInstance(this).getMyBillList(handler, pkuserId, String.valueOf(currentPage), pageNum);
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
					GetDataPost.getInstance(this).getMyBillList(handler, pkuserId, String.valueOf(currentPage), pageNum);
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
