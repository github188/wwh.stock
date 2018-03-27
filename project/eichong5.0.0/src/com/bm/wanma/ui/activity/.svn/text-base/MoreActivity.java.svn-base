package com.bm.wanma.ui.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.bm.wanma.R;
import com.bm.wanma.adapter.FunctionButtonAdapter;
import com.bm.wanma.entity.FunctionButtonBean;
import com.bm.wanma.ui.scan.CaptureActivity;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
public class MoreActivity extends BaseActivity implements OnClickListener{
	private static MoreMapCallback mapcallback;
	private ImageView iv_more_close;
	private GridView iv_more_funtion;
	private ArrayList<FunctionButtonBean> buttonBeans;
	private String pkuserId;
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		buttonBeans = (ArrayList<FunctionButtonBean>) getIntent().getSerializableExtra("funtionbutton");
		pkuserId = PreferencesUtil.getStringPreferences(this,"pkUserinfo");
		initView();
    }

    @Override
    protected void getData() {

    }

	protected void initView() {
		iv_more_close = (ImageView) findViewById(R.id.more_close);
		iv_more_close.setOnClickListener(this);
		iv_more_funtion = (GridView) findViewById(R.id.more_function);
		FunctionButtonAdapter adapter = new FunctionButtonAdapter(this, buttonBeans);
		iv_more_funtion.setAdapter(adapter);
		iv_more_funtion.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					
					redirect(position);
				} catch (Exception e) {
				}
			}
		});
	}
	private void redirect(int position) {
		Intent intent = new Intent();
		if (buttonBeans.get(position).getAblAction().equals("1")) {
			if(!Tools.isEmptyString(pkuserId)){
				intent.setClass(this, CaptureActivity.class);
			}else {
				intent.setClass(this, LoginActivity.class);
				intent.putExtra("source_inteface", "captureactivity");
			}
			startActivity(intent);
		}else if (buttonBeans.get(position).getAblAction().equals("2")) {
			mapcallback.homemap();
			finish();
		}else if (buttonBeans.get(position).getAblAction().equals("3")) {
			intent.putExtra("defaultpage", "pagedun");
			if(Tools.isEmptyString(pkuserId)){
				intent.putExtra("source_inteface", "myperson_record");
				intent.setClass(this, LoginActivity.class); 
			}else {
				intent.setClass(this, MyChargeActivity.class);
			}
			startActivity(intent);
		}else if (buttonBeans.get(position).getAblAction().equals("4")) {
			if(Tools.isEmptyString(pkuserId)){
				intent.putExtra("source_inteface", "myperson_recharge");
				intent.setClass(this, LoginActivity.class);
			}else {
				intent.setClass(this, RechargeActivity.class);
			}
			startActivity(intent);
		}else {
			intent.putExtra("h5url", buttonBeans.get(position).getAblUrl());
			intent.putExtra("h5title", buttonBeans.get(position).getAblName());
			intent.setClass(this, CommonH5Activity.class);
			startActivity(intent);
		}
		
	}
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more_close:
			finish();
			break;

		}
	}


	public static void setcallback(MoreMapCallback callback){
		mapcallback = callback;
	}
	public interface MoreMapCallback{
		void homemap();
	}
}
