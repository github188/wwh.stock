package com.bm.wanma.ui.activity;

import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.adapter.CarRepairAdapter;
import com.bm.wanma.entity.CarRepairBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.view.MyDetailListView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author cm
 * 车俩维修
 */
public class CarRepairActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton ib_back;
	private EditText et_keyword;
	private TextView tv_search;
	private ArrayList<CarRepairBean> carRepairBeans;
	private MyDetailListView mListView;
	private CarRepairAdapter mAdapter;
	private String keyword;
	private String latitude,longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_repair);
		ib_back = (ImageButton) findViewById(R.id.activity_car_repair_back);
		ib_back.setOnClickListener(this);
		et_keyword = (EditText) findViewById(R.id.activity_car_repair_et);
		tv_search = (TextView) findViewById(R.id.activity_car_repair_search);
		tv_search.setOnClickListener(this);
		mListView = (MyDetailListView) findViewById(R.id.activity_car_repair_listview);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_car_repair_back:
			finish();
			break;
		case R.id.activity_car_repair_search:
			//搜索
			keyword = et_keyword.getText().toString();
			if(!Tools.isEmptyString(keyword)){
				latitude = PreferencesUtil.getStringPreferences(getActivity().getApplicationContext(), "currentlat");
				longitude = PreferencesUtil.getStringPreferences(getActivity().getApplicationContext(), "currentlng");
				
				GetDataPost.getInstance(this).getCarRepair(handler, latitude, longitude, "1", "30", keyword, "2");
			}else {
				showToast("请输入关键词");
			}
			
			
			break;
		default:
			break;
		}
		
	}

	@Override
	protected void getData() {
		
		latitude = PreferencesUtil.getStringPreferences(getActivity().getApplicationContext(), "currentlat");
		longitude = PreferencesUtil.getStringPreferences(getActivity().getApplicationContext(), "currentlng");
		if (Tools.isEmptyString(latitude)||Tools.isEmptyString(longitude)) {
			latitude="30.308162";
			longitude="120.075782";
		}
		GetDataPost.getInstance(this).getCarRepair(handler, latitude, longitude, "1", "20", null,null);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if(bundle != null ){
			carRepairBeans = (ArrayList<CarRepairBean>) bundle.getSerializable(Protocol.DATA);
			if(carRepairBeans.size()>0){
				mAdapter = new CarRepairAdapter(this, carRepairBeans);
				mListView.setAdapter(mAdapter);
			}else {
				mAdapter = new CarRepairAdapter(this, carRepairBeans);
				mListView.setAdapter(mAdapter);
				showToast("未搜索到相关内容");
			}
			
		}

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		showToast(bundle.getString(Protocol.MSG));
		finish();

	}





}
