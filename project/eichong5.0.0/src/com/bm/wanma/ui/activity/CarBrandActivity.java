package com.bm.wanma.ui.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bm.wanma.R;
import com.bm.wanma.adapter.CarBrandListViewAdapter;
import com.bm.wanma.entity.CarTypeBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.LogUtil;

/**
 * @author cm
 * 选择品牌
 */
public class CarBrandActivity extends BaseActivity implements OnClickListener,OnItemClickListener{
	
	private ImageButton ib_back;
	private ListView listView;
	private ArrayList<CarTypeBean> mCarListBean;
	private CarBrandListViewAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_brand);
		ib_back = (ImageButton) findViewById(R.id.security_settings_back);
		ib_back.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.area_listview);
		if(isNetConnection()){
			GetDataPost.getInstance(this).getCarType(handler);
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.security_settings_back:
			finish();
			break;
		default:
			break;
		}
		
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == 0x01){
			setResult(RESULT_OK, data);
			finish();
		}
		
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (sign.equals(Protocol.FIND_PARACONFIG_LIST) && bundle != null) {
			mCarListBean = (ArrayList<CarTypeBean>) bundle.getSerializable(Protocol.DATA);
			if(mCarListBean !=null && mCarListBean.size()>0){
				mAdapter = new CarBrandListViewAdapter(this, mCarListBean);
				listView.setAdapter(mAdapter);
				listView.setOnItemClickListener(this);
			}
		}

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		if(LogUtil.isDebug){
			showToast("错误码"+bundle.getString(Protocol.CODE)+"\n"+bundle.getString(Protocol.MSG));
		}else {
			showToast(bundle.getString(Protocol.MSG));
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent in = new Intent();
		in.putExtra("carbrand",mCarListBean.get(position).getPkParaconfig());
		in.putExtra("carbrandName",mCarListBean.get(position).getParaName());
		in.setClass(this, SelectCarNameActivity.class);
		startActivityForResult(in, 0x01);
		
	}



}
