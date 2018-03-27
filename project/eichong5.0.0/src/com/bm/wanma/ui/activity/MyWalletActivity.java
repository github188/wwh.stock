package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.entity.CheckInvoiceBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author cm
 * 我的钱包
 *
 */
public class MyWalletActivity extends BaseActivity implements OnClickListener{

	private ImageButton ib_back;
	private TextView tv_balance,tv_charge;
	private String pkuserId;
	private String userBalance;
	private LinearLayout square_consume,square_invoice,square_record,square_card;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mywallet);
		initView();
		//registerBoradcastReceiver();
	}
	private void initView(){
		pkuserId = PreferencesUtil.getStringPreferences(getActivity(),"pkUserinfo");
	    userBalance = PreferencesUtil.getStringPreferences(getActivity(), "usinAccountbalance");
	    
		ib_back = (ImageButton) findViewById(R.id.mywallet_back);
		ib_back.setOnClickListener(this);
		tv_balance = (TextView) findViewById(R.id.mywallet_current_balance);
		tv_balance.setText("¥"+userBalance);
		tv_charge = (TextView) findViewById(R.id.mywallet_current_balance_charge);
		tv_charge.setOnClickListener(this);
		square_consume = (LinearLayout)findViewById(R.id.mywallet_zhangdan);
		square_consume.setOnClickListener(this);
		square_invoice = (LinearLayout)findViewById(R.id.mywallet_kaipiao);
		square_invoice.setOnClickListener(this);
		square_record = (LinearLayout)findViewById(R.id.mywallet_record);
		square_record.setOnClickListener(this);
		square_card = (LinearLayout)findViewById(R.id.mywallet_card);
		square_card.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mywallet_back:
			finish();
			break;
		case R.id.mywallet_current_balance_charge:
			//充值
			Intent in = new Intent();
			in.setClass(this, RechargeActivity.class);
			startActivityForResult(in, 0x11);
			break;
		case R.id.mywallet_zhangdan:
			//账单
			Intent myBill = new Intent(MyWalletActivity.this,MyBillActivity.class);
			startActivity(myBill);
			break;
		case R.id.mywallet_kaipiao:
			//开票
			if(isNetConnection()){
				showPD("正在加载");
				GetDataPost.getInstance(MyWalletActivity.this).getInvoiceCheck(handler, pkuserId);
			}else {
				showToast("网络不稳，请稍后再试");
			}
			break;	
		case R.id.mywallet_record:
			//开票记录
			Intent myInvoiceRecord = new Intent(MyWalletActivity.this,MyInvoiceRecordActivity.class);
			startActivity(myInvoiceRecord);
			break;	
		case R.id.mywallet_card:
			//我的电卡
			Intent icCardIn = new Intent();
			icCardIn.setClass(MyWalletActivity.this, MyIcCardActivity.class);
			startActivity(icCardIn);
			break;
		default:
			break;
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(0x11 == requestCode){
			//GetDataPost.getInstance(this).getMyWalletAll(handler, pkuserId, null, null, null);
			//getInitValue();
		}
		
	}

	@Override
	protected void getData() {
		

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		cancelPD();
	    if(sign.equals(Protocol.GET_INVOICE_CHECK)){
	    	if(bundle != null){
	    		CheckInvoiceBean bean =  (CheckInvoiceBean) bundle.getSerializable(Protocol.DATA);
	    		if(bean != null && 1 ==bean.getIc()){
	    			Intent myInvoice = new Intent(MyWalletActivity.this,InvoiceActivity.class);
	    			startActivity(myInvoice);	
	    		}else {
	    			Intent tipInvoice = new Intent(MyWalletActivity.this,InvoiceTipActivity.class);
	    			startActivity(tipInvoice);
	    		}
	    	}
	    }

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		showToast(bundle.getString(Protocol.MSG));
		finish();

	}

}
