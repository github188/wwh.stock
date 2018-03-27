package com.bm.wanma.ui.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.bm.wanma.R;
import com.bm.wanma.adapter.MyListModeListViewAdapter;
import com.bm.wanma.adapter.SearchMyCollectAdapter;
import com.bm.wanma.adapter.SearchMyHistoryAdapter;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.dialog.TipClearHistoryDialog;
import com.bm.wanma.entity.ElectricPileBean;
import com.bm.wanma.entity.ElectricPileDetailsBean;
import com.bm.wanma.entity.ListModeBean;
import com.bm.wanma.entity.MapModeBean;
import com.bm.wanma.entity.MyCollectBean;
import com.bm.wanma.entity.PowerStationBean;
import com.bm.wanma.entity.SelectValueBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.ComparatorListMode;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.ProjectApplication;
import com.bm.wanma.utils.ToastUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.view.MyDetailListView;
import com.umeng.analytics.MobclickAgent;

/**
 * @author cm
 * 搜索充电点
 */
public class SearchPointActivity extends BaseActivity implements OnClickListener,OnScrollListener{
	private String currentLat,currentLng;
	
	private String pkUserinfo;
	private String electricId;
	private ElectricPileDetailsBean pileBean;
	
	
	private TextView ib_back;
	private EditText et_keyword;
	private TextView tv_no_history,tv_history,tv_clear_history;
	private TextView tv_collect_more,tv_no_aotu,tv_aotu;
	private LinearLayout ll_history_collect;
	private MyDetailListView history_listview,collect_listview;
	private ListView search_listview;
	private ArrayList<MyCollectBean> listBean;
	private ArrayList<String> historyList;
	private SearchMyCollectAdapter searchMyCollectAdapter;
	private SearchMyHistoryAdapter searchMyHistoryAdapter;

	private List<MapModeBean> allMapTableBeans;
	
	private MyListModeListViewAdapter maAdapter;
	private List<MapModeBean> allKeywordMapBean;
	private MapModeBean mapModeBean;
	private TipClearHistoryDialog mTipClearHistoryDialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_point);
		initView();
		registerBoradcastReceiver();		
	}
	
	private void initView(){

		ib_back = (TextView) findViewById(R.id.activity_search_point_back);
		ib_back.setOnClickListener(this);
		et_keyword = (EditText) findViewById(R.id.activity_search_point_et);
		et_keyword.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {  
                    startSearchKeyword(et_keyword);
                    saveHistory();
                }  
				return true;
			}
		});
		
		ll_history_collect = (LinearLayout) findViewById(R.id.search_point_ll);
		tv_no_history = (TextView) findViewById(R.id.search_point_tv_history_no);
		tv_history = (TextView) findViewById(R.id.search_point_tv_history);
		history_listview = (MyDetailListView) findViewById(R.id.search_point_history_listview);
		search_listview = (ListView) findViewById(R.id.search_point_search_listview);
		
		search_listview.setOnScrollListener(this);
		
		collect_listview = (MyDetailListView) findViewById(R.id.search_point_collect_listview);
		tv_clear_history = (TextView) findViewById(R.id.search_point_tv_history_clear);
		tv_clear_history.setOnClickListener(this);

		tv_no_aotu = (TextView) findViewById(R.id.search_point_search_listview_no_result);
		tv_aotu = (TextView) findViewById(R.id.search_point_search_listview_result);
		
		historyList = new ArrayList<String>();
		historyList = getHistory();
		if(historyList.size()>0){

			tv_history.setVisibility(View.VISIBLE);
			tv_clear_history.setVisibility(View.VISIBLE);
			searchMyHistoryAdapter = new SearchMyHistoryAdapter(this,historyList);
			history_listview.setAdapter(searchMyHistoryAdapter);
			history_listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//去搜索
					et_keyword.setText(historyList.get(position));
					//goToSearch();
					historyList.remove(position);
					StringBuilder sb = new StringBuilder();
					for(int i = historyList.size()-1;i>=0;i--){
						  sb.insert(0, historyList.get(i) + ",");
					}
					 PreferencesUtil.setPreferences(SearchPointActivity.this, "searchHistory", sb.toString());
					
					 
					startSearchKeyword(et_keyword);
					saveHistory();
					//隐藏，搜索
					ll_history_collect.setVisibility(View.GONE);
				}
			});
		}else {
			tv_no_history.setVisibility(View.VISIBLE);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_search_point_back:
			finish();
			break;

		case R.id.search_point_tv_history_clear:
			//清空历史,弹框提示
			mTipClearHistoryDialog = new TipClearHistoryDialog(SearchPointActivity.this,"确定清空搜索历史?");
			mTipClearHistoryDialog.setCancelable(false);
			mTipClearHistoryDialog.setOnPositiveListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					clearHistory();
					mTipClearHistoryDialog.dismiss();
				}
			}) ;
			mTipClearHistoryDialog.setOnNegativeListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mTipClearHistoryDialog.dismiss();
				}
			});
			mTipClearHistoryDialog.show();
			break;
		default:
			break;
		}
		
	}
	private void startSearchKeyword(View v){
		  //隐藏软键盘  
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);  
       // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        
        
		String key = et_keyword.getText().toString();
		if(Tools.isEmptyString(key)){
			showToast("请输入关键词");
			return;
		}
		et_keyword.setText(key);
		currentLat = PreferencesUtil.getStringPreferences(getActivity(), "currentlat");
		currentLng = PreferencesUtil.getStringPreferences(getActivity(), "currentlng");
		if (isNetConnection()) {
			GetDataPost.getInstance(getActivity()).getSearchList(handler,
					  currentLng, currentLat,key,"1000");
			
			
		} else {
			showToast("网络不稳，请稍后再试");
		}
		
	}
	



	@Override
	protected void getData() {
		if(isNetConnection()){
			String userId = PreferencesUtil.getStringPreferences(this, "pkUserinfo");
			String lat = PreferencesUtil.getStringPreferences(this, "currentlat");
			String lng = PreferencesUtil.getStringPreferences(this, "currentlng");
			GetDataPost.getInstance(this).getMyCollectList(handler, userId, lat,lng);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if(bundle != null){
		  if(sign.equals(Protocol.GET_SEARCH_LIST)) {//搜索结果
			if(allKeywordMapBean == null){
				allKeywordMapBean = new ArrayList<MapModeBean>();
			}
			allMapTableBeans = (ArrayList<MapModeBean>) bundle.getSerializable(Protocol.DATA);
			allKeywordMapBean.clear();
			allKeywordMapBean.addAll(allMapTableBeans);

			if(allKeywordMapBean.size() == 0){
				tv_no_aotu.setVisibility(View.VISIBLE);
				tv_aotu.setVisibility(View.VISIBLE);
//				et_keyword.setText("");
				maAdapter = new MyListModeListViewAdapter(this, allKeywordMapBean,"1");
				search_listview.setAdapter(maAdapter);
			}else {
				ComparatorListMode comparator = new ComparatorListMode("distance");
				Collections.sort(allKeywordMapBean, comparator);
				maAdapter = new MyListModeListViewAdapter(this, allKeywordMapBean,"1");
				search_listview.setAdapter(maAdapter);
				search_listview.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						maAdapter.getView(position, view, search_listview);
						if (id!=R.id.navigation) {								
							mapModeBean = allKeywordMapBean.get(position);
							Intent detail = new Intent(SearchPointActivity.this,StationStiltDetailActivity.class);
							detail.putExtra("mapModeBean", mapModeBean);
							startActivity(detail);
						}
					}
				});
				tv_no_aotu.setVisibility(View.GONE);
				tv_aotu.setVisibility(View.VISIBLE);
			}

		 
			//隐藏，搜索
			ll_history_collect.setVisibility(View.GONE);
			
		}

		else if (sign.equals(Protocol.POWER_Pile_DETAIL)) {
			//电站详情
			cancelPD();
			pileBean = (ElectricPileDetailsBean) bundle.getSerializable(Protocol.DATA);
			if(pileBean != null){
				Intent detailIn = new Intent();
				detailIn.putExtra("pileBean", pileBean);
				detailIn.setClass(this, StationStiltDetailActivity.class);
//				detailIn.putExtra("electricId", electricId);
				startActivity(detailIn);
			}
		}
		}
		
	}

	@Override
	public void onFaile(String sign, Bundle bundle) { } 
	@Override
	protected void onDestroy() {
		super.onDestroy();
//		unregisterReceiver(mBroadcastReceiver);
	}
	
	//保存搜索历史，最新10条
	private void saveHistory(){
		String text = et_keyword.getText().toString();
		String savehistory = PreferencesUtil.getStringPreferences(SearchPointActivity.this, "searchHistory");
		if(!savehistory.contains(text + ",")){
			  StringBuilder sb = new StringBuilder(savehistory);
			  sb.insert(0, text + ",");
			  //如果超过5条，更新最早的
			  String[] listH = sb.toString().split(",");
			  if(listH.length>10){
				  String[] newHistories = new String[10];  
				  System.arraycopy(listH, 0, newHistories, 0, 10); 
				  sb = new StringBuilder();
				  sb.append(newHistories[0]+",").append(newHistories[1]+",")
				  .append(newHistories[2]+",").append(newHistories[3]+",")
				  .append(newHistories[4]+",").append(newHistories[5]+",")
				  .append(newHistories[6]+",").append(newHistories[7]+",")
				  .append(newHistories[8]+",").append(newHistories[9]);
			  }
			  PreferencesUtil.setPreferences(SearchPointActivity.this, "searchHistory",sb.toString());
		}
	}
	
	//获取搜索历史
	private ArrayList<String> getHistory(){
		String savehistory = PreferencesUtil.getStringPreferences(SearchPointActivity.this, "searchHistory");
		String[] listH = savehistory.split(",");
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0;i<listH.length;i++){
			if(!Tools.isEmptyString(listH[i])){
				list.add(listH[i]);
			}
		}
		return list;
	}
	
	//清空搜索历史
		private void clearHistory(){
			PreferencesUtil.setPreferences(SearchPointActivity.this, "searchHistory", "");
			sendBroadcast(new Intent(BroadcastUtil.BROADCAST_SEARCH_POINT_DELETE_HISTORY));
		}
		
		private  void registerBoradcastReceiver(){  
	        IntentFilter myIntentFilter = new IntentFilter();  
	        myIntentFilter.addAction(BroadcastUtil.BROADCAST_SEARCH_POINT_DELETE_HISTORY); 
	        myIntentFilter.addAction(BroadcastUtil.BROADCAST_SEARCH_POINT); 
	        //注册广播        
	        registerReceiver(mBroadcastReceiver, myIntentFilter);  
	    }  
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			 if(action.equals(BroadcastUtil.BROADCAST_SEARCH_POINT_DELETE_HISTORY)){ 
				 if(getHistory().size()==0){
					 tv_clear_history.setVisibility(View.GONE);
					 tv_history.setVisibility(View.GONE);
					 tv_no_history.setVisibility(View.VISIBLE);
					 history_listview.setVisibility(View.GONE);
				 }
			 }	
			 }
	};

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {}

	/** 
     * 滑动时被调用 
     */ 
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {}	


}
