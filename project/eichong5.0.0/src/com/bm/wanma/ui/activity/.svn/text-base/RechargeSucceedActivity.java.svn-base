package com.bm.wanma.ui.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.wanma.R;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.dialog.RechargeSuccesDialog;
import com.bm.wanma.dialog.TakePhotoDialog;
import com.bm.wanma.entity.CityBean;
import com.bm.wanma.entity.IntegralRechargeBean;
import com.bm.wanma.entity.ProvinceBean;
import com.bm.wanma.entity.UserInfoBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.NetFile;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.HeadImageUtils;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.view.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author cm
 * 我的个人资料界面
 */
public class RechargeSucceedActivity extends BaseActivity implements OnClickListener{
	
	private String money;
	private String type;
	private LinearLayout ll_lottery_module; 
	private TextView t_recharge_money,t_jifen_title,t_jifen_size,t_degree;
	private String pkuserId;
	private String  provincecode, citycode;
	IntegralRechargeBean integralRechargeBean = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reharge_succeed);
		Intent intent = getIntent();
		pkuserId = PreferencesUtil.getStringPreferences(this,"pkUserinfo");
		money = intent.getStringExtra("rechargemoney");
		type = intent.getStringExtra("rechargetype");
		initView();
		
	}

	private void initView(){
		t_recharge_money = (TextView) findViewById(R.id.recharge_money);
		t_jifen_title = (TextView) findViewById(R.id.jifen_title);
		t_jifen_size = (TextView) findViewById(R.id.jifen_size);
		t_degree = (TextView) findViewById(R.id.t_degree);
		ll_lottery_module = (LinearLayout) findViewById(R.id.ll_lottery_module);
		t_recharge_money.setText(""+money);
		provincecode = PreferencesUtil.getStringPreferences(this,"provincecode");
		findViewById(R.id.settings_back).setOnClickListener(this);
		citycode = PreferencesUtil.getStringPreferences(this, "citycode");
		if (Tools.isEmptyString(provincecode)||Tools.isEmptyString(citycode)) {
			provincecode = "330000";
			citycode = "330100";
		}
		if (!Tools.isEmptyString(citycode)&&!Tools.isEmptyString(provincecode)) {		
		GetDataPost.getInstance(this).getRechargeIntegral(handler, pkuserId,provincecode, citycode,money);
		}
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.settings_back:
			Intent data = new Intent();
			data.putExtra("nick", 0x01);
			setResult(RESULT_OK, data);
			finish();
			break;
		default:
			break;
		}
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent data = new Intent();
			data.putExtra("nick", 0x01);
			setResult(RESULT_OK, data);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void getData() {

	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (bundle!=null) {
			 integralRechargeBean = (IntegralRechargeBean) bundle.getSerializable(Protocol.DATA);
			if (integralRechargeBean!=null) {
				if (Tools.isEmptyString(integralRechargeBean.getIntegralValue())||integralRechargeBean.getIntegralValue().equals("0")) {
					t_jifen_title.setVisibility(View.GONE);
					t_jifen_size.setVisibility(View.GONE);
				}else {
					t_jifen_title.setVisibility(View.VISIBLE);
					t_jifen_size.setVisibility(View.VISIBLE);
					t_jifen_size.setText(""+integralRechargeBean.getIntegralValue());
				}
				
				if (Tools.isEmptyString(integralRechargeBean.getChoiceCount())||integralRechargeBean.getChoiceCount().equals("0")) {
					ll_lottery_module.setVisibility(View.GONE);
				}else {
					ll_lottery_module.setVisibility(View.VISIBLE);
					t_degree.setText(""+integralRechargeBean.getChoiceCount());
				}
			}
		}
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		t_jifen_title.setVisibility(View.GONE);
		t_jifen_size.setVisibility(View.GONE);
		ll_lottery_module.setVisibility(View.GONE);
	}
}
