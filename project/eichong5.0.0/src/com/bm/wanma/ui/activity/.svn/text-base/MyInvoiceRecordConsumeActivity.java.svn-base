package com.bm.wanma.ui.activity;

import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.adapter.MyBillsAdapter;
import com.bm.wanma.entity.BillBean;
import com.bm.wanma.entity.BillSectionBean;
import com.bm.wanma.entity.MyBillList;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ViewSwitcher;

/**
 * @author cm
 * 开票记录详情包含消费记录
 */
public class MyInvoiceRecordConsumeActivity extends BaseActivity {
	
	private ImageButton ib_back;
	private ListView listview;
	private ViewSwitcher viewSwitcher;
	private MyBillList myBillList;
	private ArrayList<BillBean> billBeans;
	private ArrayList<BillSectionBean> rawdata;
	private MyBillsAdapter maAdapter;
	private String iId;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinvoice_record_consume);
		iId = getIntent().getStringExtra("iId");
		initView();
	}
	
	private void initView(){
		
		ib_back = (ImageButton) findViewById(R.id.invoice_mybill_back);
		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		viewSwitcher = (ViewSwitcher) findViewById(R.id.invoice_mybill_viewswitcher);
		
		listview = (ListView) findViewById(R.id.invoice_mybill_listview);
		rawdata = new ArrayList<BillSectionBean>();
		if(isNetConnection()){
			GetDataPost.getInstance(this).getMyInvoiceRecordPur(handler, iId);
		}
	}
	

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (bundle != null) {
			myBillList = (MyBillList) bundle.getSerializable(Protocol.DATA);
			if(myBillList != null){
				billBeans = myBillList.getConsumeRecord();
				if(billBeans != null && billBeans.size()>0){
					rawdata = BillSectionBean.getData(billBeans);
				}
				if(rawdata.size() > 0){
					maAdapter = new MyBillsAdapter(this, rawdata);
					listview.setAdapter(maAdapter);
				}else {
					viewSwitcher.showNext();
				}
			}
		}
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		showToast(bundle.getString(Protocol.MSG));
		finish();
	}


}
