package com.bm.wanma.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bm.wanma.R;
import com.bm.wanma.entity.InvoiceRecordDetailBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;

public class InvoiceErrorActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton ib_close,ib_back;
	private TextView tv_title,tv_pay;
	private String orderId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice_error);
		ib_close = (ImageButton) findViewById(R.id.invoice_close);
		ib_close.setOnClickListener(this);
		ib_back = (ImageButton) findViewById(R.id.invoice_back);
		ib_back.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.common_invoice_title);
		tv_title.setText("支付失败");
		tv_pay = (TextView) findViewById(R.id.invoice_error_pay);
		tv_pay.setOnClickListener(this);
		orderId = getIntent().getStringExtra("orderId");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.invoice_close:
			sendBroadcast(new Intent("action.invoice.close"));
			finish();
			break;
		case R.id.invoice_back:
			//开票列表
			Intent list = new Intent(this,MyInvoiceRecordActivity.class);
			startActivity(list);
			finish();
			break;
		case R.id.invoice_error_pay:
			if(isNetConnection()){
				showPD("正在加载");
				GetDataPost.getInstance(this).getMyInvoiceRecordDetail(handler, orderId);
			}else {
				showToast("网络不稳，请稍后再试");
			}
			
			break;
		default:
			break;
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent list = new Intent(this,MyInvoiceRecordActivity.class);
			startActivity(list);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	@Override
	protected void getData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		cancelPD();
		if(bundle != null && sign.equals(Protocol.MY_INVOICE_RECORD_DETAIL)){
			InvoiceRecordDetailBean bean = (InvoiceRecordDetailBean) bundle.getSerializable(Protocol.DATA);
			Intent item = new Intent(this,MyInvoiceRecordDetailPayActivity.class);
			item.putExtra("InvoiceRecordDetailBean", bean);
			item.putExtra("iId",orderId);
			startActivity(item);
			finish();
		}
	}



	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		showToast(bundle.getString(Protocol.MSG));
	}
	

	
	
	
	
	

}
