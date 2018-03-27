package com.bm.wanma.ui.activity;

import java.text.DecimalFormat;

import com.bm.wanma.R;
import com.bm.wanma.dialog.TipDrawGunDialog;
import com.bm.wanma.entity.BanlanceBean;
import com.bm.wanma.entity.ShareToThirdBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.TimeUtil;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
 
/**
 * @author cm
 * 充电完成界面
 */
public class ChargeFinishActivity extends BaseActivity implements OnClickListener{
	
	private TextView tv_payment,tv_payment_money,tv_server_money,tv_electric_money;
	private TextView tv_coupon_money,tv_duration,tv_power;
	private TextView tv_complete;
	private Intent getIn;
	private float power,servicemoney,electricMoney,paymentmoney,coupon,reduce;
	private String duration,pkUserId;
	private TipDrawGunDialog mDialog;
	private ImageButton	iv_charge_close;
	private Button bt_charge_finish_share;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_detail);
		mPageName = "ChargeFinishActivity";
		getIn = getIntent();
		pkUserId = PreferencesUtil.getStringPreferences(ChargeFinishActivity.this, "pkUserinfo");
		if(isNetConnection()){
			GetDataPost.getInstance(this).getBalance(handler, pkUserId);
//			GetDataPost.getInstance(this).getBalrance(handler, pkUserId);
		}
		//pileNum = getIn.getStringExtra("pilePK");
		initView();
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
	private void initView(){
		
		tv_payment = (TextView)findViewById(R.id.charge_finish_payment);
		tv_payment_money = (TextView)findViewById(R.id.charge_finish_payment_money);
		tv_electric_money = (TextView)findViewById(R.id.charge_detail_elctric_money);
		tv_server_money = (TextView) findViewById(R.id.charge_detail_service_money);
		tv_coupon_money = (TextView) findViewById(R.id.charge_detail_coupon_money);
		tv_duration = (TextView) findViewById(R.id.charge_detail_duration);
		tv_power = (TextView) findViewById(R.id.charge_detail_power);
//		tv_charge_detail_state = (TextView) findViewById(R.id.charge_detail_state);
		iv_charge_close = (ImageButton) findViewById(R.id.charge_close);
		iv_charge_close.setOnClickListener(this);
		bt_charge_finish_share = (Button) findViewById(R.id.charge_finish_share);
		bt_charge_finish_share.setOnClickListener(this);
		tv_complete = (TextView) findViewById(R.id.charge_finish_complete);
		tv_complete.setOnClickListener(this);
		initValue();
		mDialog = new TipDrawGunDialog(this);
		mDialog.setCancelable(false);
		mDialog.setOnPositiveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
	
	private void initValue(){
		DecimalFormat dotformat = new DecimalFormat(",##0.00");
		DecimalFormat dotformat1 = new DecimalFormat(",###.##");
		LogUtil.i("cm_socket", "duration_finish"+getIn.getLongExtra("duration", 0));
		duration = TimeUtil.getCutDown5(getIn.getLongExtra("duration", 0));
		power = getIn.getFloatExtra("power", 0);
		electricMoney = getIn.getFloatExtra("electricMoney", 0);
		servicemoney = getIn.getFloatExtra("serviceMoney", 0);
		coupon = getIn.getFloatExtra("coupon", 0);
		//first = getIn.getIntExtra("isFirst", 0);
		reduce = getIn.getFloatExtra("reduce", 0);
		paymentmoney = electricMoney+servicemoney-reduce;
		if(paymentmoney <=0){
			tv_payment.setText("充电完成，已免单");
		}
		tv_payment_money.setText(dotformat.format(paymentmoney) + " 元");
		tv_electric_money.setText(dotformat.format(electricMoney) + " 元");//电费
		
		tv_server_money.setText(dotformat.format(servicemoney)+ " 元");
		tv_coupon_money.setText(dotformat1.format(reduce)+ " 元");
		tv_duration.setText(duration);
		tv_power.setText(power + " kWh");	
//		tv_charge_detail_state.setText("已完成");
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.charge_finish_share:
			//分享
			
			finish();
			break;
		case R.id.charge_finish_complete:
			//完成
			finish();
			break;
			
		case R.id.charge_close:
			//完成
			finish();
			break;
//		case R.id.charge_finish_comment:
//			//去评价
//			Intent commentIn = new Intent();
//			commentIn.putExtra("epId",pileNum);
//			commentIn.setClass(ChargeFinishActivity.this, ChargeFinishCommentActivity.class);
//			startActivityForResult(commentIn, 0x80); 
//			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(0x80 == requestCode){
			finish();
		}
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		//获取余额
		if(bundle != null){
		BanlanceBean mBanlanceBean = (BanlanceBean) bundle.getSerializable(Protocol.DATA);
		if(mBanlanceBean != null){
			PreferencesUtil.setPreferences(this, "usinAccountbalance", mBanlanceBean.getUserAB());
		}
	 }
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		// TODO Auto-generated method stub
		
	}

}
