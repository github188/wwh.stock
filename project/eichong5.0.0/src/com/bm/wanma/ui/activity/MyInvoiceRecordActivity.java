package com.bm.wanma.ui.activity;

import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.adapter.MyInvoiceRecordAdapter;
import com.bm.wanma.entity.InvoiceRecordBean;
import com.bm.wanma.entity.InvoiceRecordDetailBean;
import com.bm.wanma.entity.InvoiceRecordSectionBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.view.PullToRefreshListView;
import com.bm.wanma.view.PullToRefreshListView.OnLoadListener;
import com.bm.wanma.view.PullToRefreshListView.OnRefreshListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ViewSwitcher;

/**
 * @author cm
 * 开票记录
 */
public class MyInvoiceRecordActivity extends BaseActivity implements OnRefreshListener,OnLoadListener,OnItemClickListener{
	
	private ImageButton ib_back;
	private PullToRefreshListView listview;
	private ViewSwitcher viewSwitcher;
	private final String pageNum = "20";
	private int currentPage;
	private boolean isRefresh;
	private String pkuserId,iId;
	private ArrayList<InvoiceRecordBean> invoiceRecordBeans;
	private ArrayList<InvoiceRecordSectionBean> data,rawdata;
	private InvoiceRecordSectionBean invoiceRecordSectionBean;
	private MyInvoiceRecordAdapter maAdapter;
	private InvoiceRecordDetailBean invoiceRecordDetailBean;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinvoice_record);
		initView();
		registerBoradcastReceiver();
	}
	
	private void initView(){
		
		ib_back = (ImageButton) findViewById(R.id.invoice_record_back);
		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		viewSwitcher = (ViewSwitcher) findViewById(R.id.invoice_record_viewswitcher);
		
		listview = (PullToRefreshListView) findViewById(R.id.invoice_record_listview);
		listview.setOnRefreshListener(this);
		listview.setOnLoadListener(this);
		listview.setOverScrollMode(View.OVER_SCROLL_NEVER);
		// 默认加载第一页数据
		pkuserId = PreferencesUtil.getStringPreferences(getActivity(),"pkUserinfo");
		currentPage = 1;
		isRefresh = true;
		data = new ArrayList<InvoiceRecordSectionBean>();
		rawdata = new ArrayList<InvoiceRecordSectionBean>();
		if(isNetConnection()){
			GetDataPost.getInstance(this).getMyInvoiceRecordList(handler, pkuserId, String.valueOf(currentPage), pageNum);
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
			cancelPD();
			if(sign.equals(Protocol.MY_INVOICE_RECORD)){
				invoiceRecordBeans = (ArrayList<InvoiceRecordBean>) bundle.getSerializable(Protocol.DATA);
				rawdata.clear();
				if(invoiceRecordBeans != null && invoiceRecordBeans.size()>0){
					rawdata = InvoiceRecordSectionBean.getData(invoiceRecordBeans);
				}
				if(isRefresh){
					data.clear();
					data.addAll(rawdata);
					listview.onRefreshComplete();
				}else {
					data = InvoiceRecordSectionBean.addList(data, rawdata);
					listview.onLoadComplete();
				}
				listview.setResultSize(invoiceRecordBeans.size());
				if(data.size()<10){
					listview.setResultTip();
				}
				if(maAdapter == null){
					if(data.size() > 0){
						maAdapter = new MyInvoiceRecordAdapter(this, data);
						listview.setAdapter(maAdapter);
						listview.setOnItemClickListener(this);
					}else {
						viewSwitcher.showNext();
					}
					
				}else {
					maAdapter.notifyDataSetChanged();
				}
			
				
			}else if(sign.equals(Protocol.MY_INVOICE_RECORD_DETAIL)){
				invoiceRecordDetailBean = (InvoiceRecordDetailBean) bundle.getSerializable(Protocol.DATA);
				if(invoiceRecordDetailBean != null){
					Intent item = null;
					//0受理中 1处理完成 2未支付邮费
					if("2".equals(invoiceRecordDetailBean.getStatus())){
						item = new Intent(this,MyInvoiceRecordDetailPayActivity.class);
					}else {
						item = new Intent(this,MyInvoiceRecordDetailActivity.class);
					}
					item.putExtra("InvoiceRecordDetailBean", invoiceRecordDetailBean);
					item.putExtra("iId",iId);
					startActivity(item);
				}
			}
		}
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		showToast(bundle.getString(Protocol.MSG));
		listview.onRefreshComplete();
		listview.onLoadComplete();
	}

	@Override
	public void onLoad() {
		isRefresh = false;
		listview.onRefreshComplete();
		//获取下一页数据
		currentPage ++;
		if(isNetConnection()){
			GetDataPost.getInstance(this).getMyInvoiceRecordList(handler, pkuserId, String.valueOf(currentPage), pageNum);
		}else {
			showToast("网络不稳，请稍后再试");
		}
	}

	@Override
	public void onRefresh() {
		isRefresh = true;
		listview.onLoadComplete();
		currentPage = 1;
		if(isNetConnection()){
			GetDataPost.getInstance(this).getMyInvoiceRecordList(handler, pkuserId, String.valueOf(currentPage), pageNum);
		}else {
			showToast("网络不稳，请稍后再试");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(position <= data.size()){
			invoiceRecordSectionBean = data.get(position-1);
			if(0 == invoiceRecordSectionBean.getType()){
			if(isNetConnection()){
				showPD("正在获取详情信息");
				iId = invoiceRecordSectionBean.getBillBean().getiId();
				GetDataPost.getInstance(this).getMyInvoiceRecordDetail(handler,invoiceRecordSectionBean.getBillBean().getiId());
			}else {
				showToast("网络不稳，稍后再试");
			}
		}
			
		}
	}
	
	public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction("action.invoice.close"); 
        //注册广播        
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    }  
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			  String action = intent.getAction();
			  if(action.equals("action.invoice.close")){
				  finish();
			  }
		 }
	};

	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}
}
