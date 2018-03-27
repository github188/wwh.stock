package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.utils.PreferencesUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class InvoiceTipActivity extends BaseActivity implements OnClickListener{
	
	private TextView tv_commit;
	private ImageButton ib_close;
	private String uId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice_process);
		tv_commit = (TextView) findViewById(R.id.invoice_process_commit);
		tv_commit.setOnClickListener(this);
		ib_close = (ImageButton) findViewById(R.id.invoice_process_close);
		ib_close.setOnClickListener(this);
		uId = PreferencesUtil.getStringPreferences(this,"pkUserinfo");
		GetDataPost.getInstance(this).addInvoiceCheck(handler, uId);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.invoice_process_commit:
		case R.id.invoice_process_close:
			Intent myInvoice = new Intent(InvoiceTipActivity.this,InvoiceActivity.class);
			startActivity(myInvoice);
			finish();
			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		// TODO Auto-generated method stub

	}



}
