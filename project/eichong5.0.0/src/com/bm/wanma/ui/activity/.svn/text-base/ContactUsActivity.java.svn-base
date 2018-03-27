package com.bm.wanma.ui.activity;

import com.bm.wanma.R;
import com.bm.wanma.dialog.CancleBespokeDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author cm 联系我们
 */
public class ContactUsActivity extends BaseActivity implements OnClickListener {

	private TextView tv_contact, tv_apply, tv_record ,tv_electric_card;
	private ImageButton btn_back;
	private static final String TEL = "400-085-0006";
	private CancleBespokeDialog cancleBespokeDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_us);
		initView();
	}

	private void initView() {
		btn_back = (ImageButton) findViewById(R.id.settings_back);
		btn_back.setOnClickListener(this);
		tv_contact = (TextView) findViewById(R.id.contact_aichong);
		tv_contact.setOnClickListener(this);
		tv_apply = (TextView) findViewById(R.id.contact_apply_invoice);
		tv_apply.setOnClickListener(this);
		tv_record = (TextView) findViewById(R.id.contact_invoice_record);
		tv_record.setOnClickListener(this);
		
		tv_electric_card = (TextView) findViewById(R.id.electric_card);
		tv_electric_card.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.settings_back:
			finish();
			break;
		case R.id.contact_aichong:
			contactUs();
			break;
		case R.id.contact_apply_invoice:
			//开票
			startActivity(new Intent(ContactUsActivity.this,InvoiceActivity.class));
			/*if(isNetConnection()){
				showPD("正在加载");
				String pkuserId = PreferencesUtil.getStringPreferences(getActivity(),"pkUserinfo");
				GetDataPost.getInstance(ContactUsActivity.this).getInvoiceCheck(handler, pkuserId);
			}else {
				showToast("网络不稳，请稍后再试");
			}*/
			break;
		case R.id.contact_invoice_record:
			//开票记录
			Intent myInvoiceRecord = new Intent(ContactUsActivity.this,MyInvoiceRecordActivity.class);
			startActivity(myInvoiceRecord);
			break;
			
		case R.id.electric_card:
			//我的电卡
			Intent icCardIn = new Intent();
			icCardIn.setClass(ContactUsActivity.this, MyIcCardActivity.class);
			startActivity(icCardIn);
			break;
		default:
			break;
		}

	}
	
	private void contactUs(){
		if(cancleBespokeDialog == null){
			cancleBespokeDialog = new CancleBespokeDialog(this,TEL);
			cancleBespokeDialog.setCancelable(false);
			cancleBespokeDialog.setButtonTitle("呼叫", "取消");
	        cancleBespokeDialog.setOnPositiveListener(new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	//拨打电话
		        	Intent telintent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+TEL));//直接拨打
		        	//Intent telintent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+TEL));
					telintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					startActivity(telintent);
		        	cancleBespokeDialog.dismiss();
		        }
		    });
	        cancleBespokeDialog.setOnNegativeListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					cancleBespokeDialog.dismiss();
				}
			});
		}
		if(!cancleBespokeDialog.isShowing()){
			cancleBespokeDialog.show();
		}
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		/*cancelPD();
	    if(sign.equals(Protocol.GET_INVOICE_CHECK)){
	    	if(bundle != null){
	    		CheckInvoiceBean bean =  (CheckInvoiceBean) bundle.getSerializable(Protocol.DATA);
	    		if(bean != null && 1 ==bean.getIc()){
	    			Intent myInvoice = new Intent(ContactUsActivity.this,InvoiceActivity.class);
	    			startActivity(myInvoice);	
	    		}else {
	    			Intent tipInvoice = new Intent(ContactUsActivity.this,InvoiceTipActivity.class);
	    			startActivity(tipInvoice);
	    		}
	    	}
	    }*/

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		// TODO Auto-generated method stub

	}

}
