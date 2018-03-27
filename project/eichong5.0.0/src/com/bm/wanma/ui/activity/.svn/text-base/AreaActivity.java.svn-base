package com.bm.wanma.ui.activity;

import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.wanma.R;
import com.bm.wanma.adapter.ProvinceListViewAdapter;
import com.bm.wanma.entity.AreaBean;
import com.bm.wanma.entity.CityBean;
import com.bm.wanma.entity.ProvinceBean;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;

/**
 * @author cm
 * 地区
 */
public class AreaActivity extends BaseActivity implements OnClickListener,OnItemClickListener{
	
	private ImageButton ib_back;
	private TextView tv_current_area;
	private ListView listview;
	private ProvinceListViewAdapter mAdapter;
	private List<ProvinceBean> provinces;
	private String province,city,provinceId,areaname,areacode;
	private FinalDb finalDb;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_province);
		finalDb = FinalDb.create(getActivity(),Protocol.DATABASE_NAME,true,Protocol.dbNumer,null);
		init();
		initValue();
	}
	
	private void initValue(){
		provinces = finalDb.findAll(ProvinceBean.class);
		mAdapter = new ProvinceListViewAdapter(this, provinces);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(this);
		
	}
	private void init(){
		ib_back = (ImageButton) findViewById(R.id.security_settings_back);
		ib_back.setOnClickListener(this);
		tv_current_area = (TextView) findViewById(R.id.area_current);
		listview = (ListView) findViewById(R.id.area_listview);

		province = PreferencesUtil.getStringPreferences(this, "province");
		city = PreferencesUtil.getStringPreferences(this, "city");
		areacode = PreferencesUtil.getStringPreferences(this, "areacode");
		
		if(!TextUtils.isEmpty(areacode)){
			 List<AreaBean> tempLC = finalDb.findAllByWhere(AreaBean.class, "AREA_ID = "+areacode);
			 if(tempLC.size()>0){
				 areaname = tempLC.get(0).getAREA_NAME();
			 }
		}
		
		if(!TextUtils.isEmpty(areaname)){
			tv_current_area.setText(province+"  "+city+" "+areaname);
			tv_current_area.setOnClickListener(this);
		}else {
			tv_current_area.setText("无法获取到你的位置信息");
			Drawable drawable = getResources().getDrawable(R.drawable.img_area_fail);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			tv_current_area.setCompoundDrawables(drawable, null, null, null); 
			tv_current_area.setCompoundDrawablePadding(10);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.security_settings_back:
			finish();
			break;
		case R.id.area_current:
			 String cityCode = PreferencesUtil.getStringPreferences(this, "cityCode");
			 List<CityBean> tempLC = finalDb.findAllByWhere(CityBean.class, "CITY_ID="+cityCode);
			 if(tempLC.size()>0){
				 provinceId = tempLC.get(0).getPROVINCE_ID();
			 }
			    Intent data = new Intent();
//			    data.putExtra("provinceName", provinceName);
//				data.putExtra("cityName", cityName);
//				data.putExtra("areaname", areaBeans.get(arg2).getAREA_NAME());
				data.putExtra("pCode",provinceId);
				data.putExtra("cCode", cityCode);
				data.putExtra("aCode", areacode);
			    
			    
				data.putExtra("provinceName",province);
				data.putExtra("cityName",city);
				data.putExtra("areaname", areaname);
				
				setResult(RESULT_OK, data);
				finish();
			break;
		default:
			break;
		}
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent data = new Intent();
		ProvinceBean bean = provinces.get(arg2);
		data.putExtra("province", bean.getPROVINCE_ID());
		data.putExtra("provinceName", bean.getPROVINCE_NAME());
		data.setClass(AreaActivity.this, CityActivity.class);
		startActivityForResult(data, 0x03);
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
