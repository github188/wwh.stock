package com.bm.wanma.ui.activity;

import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bm.wanma.R;
import com.bm.wanma.adapter.CityListViewAdapter;
import com.bm.wanma.entity.CityBean;
import com.bm.wanma.entity.ProvinceBean;
import com.bm.wanma.net.Protocol;

/**
 * @author cm
 * 地区
 */
public class CityActivity extends BaseActivity implements OnClickListener,OnItemClickListener{
	
	private ImageButton ib_back;
	private ListView listview;
	private CityListViewAdapter mAdapter;
	private List<CityBean> citys;
	private FinalDb finalDb;
	private String provinceId,provinceName;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city);
		init();
		initValue();
	}
	
	private void initValue(){
		finalDb = FinalDb.create(getActivity(),Protocol.DATABASE_NAME,true,Protocol.dbNumer,null);
		provinceId = getIntent().getStringExtra("province");
		provinceName = getIntent().getStringExtra("provinceName");
		citys = finalDb.findAllByWhere(CityBean.class,"PROVINCE_ID="+provinceId);
		mAdapter = new CityListViewAdapter(this, citys);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(this);
		
	}
	private void init(){
		ib_back = (ImageButton) findViewById(R.id.security_settings_back);
		ib_back.setOnClickListener(this);
		listview = (ListView) findViewById(R.id.area_listview);
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		Intent data = new Intent();
//		data.putExtra("province",provinceId);
//		data.putExtra("city", citys.get(arg2).getCITY_ID());
//		data.putExtra("cityName", citys.get(arg2).getCITY_NAME());
//		data.putExtra("provinceName", provinceName);
//		setResult(RESULT_OK, data);
//		finish();
		
		Intent data = new Intent();
//		ProvinceBean bean = provinces.get(arg2);
		data.putExtra("provinceName", provinceName);
		data.putExtra("cityName", citys.get(arg2).getCITY_NAME());
		data.putExtra("city", citys.get(arg2).getCITY_ID());
		data.setClass(this, DistrictActivity.class);
		startActivityForResult(data, 0x03);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == 0x03){
			setResult(RESULT_OK, data);
			finish();
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
