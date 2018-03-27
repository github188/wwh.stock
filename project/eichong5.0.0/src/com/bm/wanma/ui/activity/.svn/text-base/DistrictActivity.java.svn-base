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
import com.bm.wanma.adapter.AreaListViewAdapter;
import com.bm.wanma.adapter.CityListViewAdapter;
import com.bm.wanma.entity.AreaBean;
import com.bm.wanma.entity.CityBean;
import com.bm.wanma.net.Protocol;

/**
 * @author cm
 * 地区
 */
public class DistrictActivity extends BaseActivity implements OnClickListener,OnItemClickListener{
	
	private ImageButton ib_back;
	private ListView listview;
	private AreaListViewAdapter mAdapter;
	private List<AreaBean> areaBeans;
	private FinalDb finalDb;
	private String cityid,provinceName,cityName;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_district);
		
		init();
		initValue();
	}
	
	private void initValue(){
		finalDb = FinalDb.create(getActivity(),Protocol.DATABASE_NAME,true,Protocol.dbNumer,null);
		cityid = getIntent().getStringExtra("city");
		provinceName = getIntent().getStringExtra("provinceName");
		cityName = getIntent().getStringExtra("cityName");
		areaBeans = finalDb.findAllByWhere(AreaBean.class, "CITY_ID = "+cityid);
		mAdapter = new AreaListViewAdapter(this, areaBeans);
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
		Intent data = new Intent();
		data.putExtra("provinceName", provinceName);
		data.putExtra("cityName", cityName);
		data.putExtra("areaname", areaBeans.get(arg2).getAREA_NAME());
		data.putExtra("pCode",areaBeans.get(arg2).getPROVINCE_ID());
		data.putExtra("cCode", areaBeans.get(arg2).getCITY_ID());
		data.putExtra("aCode", areaBeans.get(arg2).getAREA_ID());
		setResult(RESULT_OK, data);
		finish();
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
