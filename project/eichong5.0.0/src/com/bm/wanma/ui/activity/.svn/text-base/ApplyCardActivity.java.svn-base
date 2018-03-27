package com.bm.wanma.ui.activity;


import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;

import com.bm.wanma.R;
import com.bm.wanma.dialog.MyAlertDialog;
import com.bm.wanma.entity.AreaBean;
import com.bm.wanma.entity.CityBean;
import com.bm.wanma.entity.ProvinceBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.RegularExpressionUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.view.ContainsEmojiEditText;
import com.bm.wanma.view.wheelcity.OnWheelChangedListener;
import com.bm.wanma.view.wheelcity.OnWheelScrollListener;
import com.bm.wanma.view.wheelcity.WheelView;
import com.bm.wanma.view.wheelcity.adapters.AbstractWheelTextAdapter;
import com.bm.wanma.view.wheelcity.adapters.AreaArrayWheelAdapter;
import com.bm.wanma.view.wheelcity.adapters.CityArrayWheelAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author cm
 * 申请电卡
 */
public class ApplyCardActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton ib_back;
	private TextView tv_commit,tv_code;
	private ContainsEmojiEditText et_name,et_phone,et_addr;
	private String name,phone,address,code,pkuserId;
	private FinalDb finalDb;
	private List<ProvinceBean> provinceList;
	private List<CityBean> cityList;
	private List<AreaBean> areaList;
	private ProvinceBean provinceBean,currentP;
	private CityBean cityBean,currentC;
	private WheelView view_province;
	private WheelView view_city;
	private WheelView view_area;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_card_apply);
		initView();
		initValue();
		
	}
	
	private void initView(){
		ib_back = (ImageButton) findViewById(R.id.activity_my_card_apply_back);
		ib_back.setOnClickListener(this);
		tv_commit = (TextView) findViewById(R.id.activity_my_card_apply_commit);
		tv_commit.setOnClickListener(this);
		et_name = (ContainsEmojiEditText) findViewById(R.id.activity_my_card_apply_et_name);
		et_phone = (ContainsEmojiEditText) findViewById(R.id.activity_my_card_apply_et_phone);
		et_addr = (ContainsEmojiEditText) findViewById(R.id.activity_my_card_apply_et_addr);
		tv_code = (TextView) findViewById(R.id.activity_my_card_apply_et_code);
		tv_code.setOnClickListener(this);
	}

	private void initValue(){
		String province = PreferencesUtil.getStringPreferences(this, "province");
		String city = PreferencesUtil.getStringPreferences(this, "city");
		String distric = PreferencesUtil.getStringPreferences(this, "distric");
		String dese = PreferencesUtil.getStringPreferences(this, "desc");
		code = province + city + distric;
		if(!Tools.isEmptyString(dese) && !Tools.isEmptyString(distric)){
			address = dese.substring(dese.indexOf(distric)+distric.length());
			tv_code.setText(code);
			et_addr.setText(address);
		}
	}
	
	@Override
	protected void getData() {
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_my_card_apply_back:
			finish();
			break;
		case R.id.activity_my_card_apply_commit:
			//提交申请
			handleCommitEvent();
			break;
		case R.id.activity_my_card_apply_et_code:
			//选择地区
			handleSelectAddress();
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		cancelPD();
		showToast("提交成功");
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		showToast(bundle.getString(Protocol.MSG));
		finish();

	}
	//处理提交申请事件
	private void handleCommitEvent(){
		pkuserId = PreferencesUtil.getStringPreferences(ApplyCardActivity.this,"pkUserinfo");
		name = et_name.getText().toString();
		phone = et_phone.getText().toString();
		code = tv_code.getText().toString();
		address = et_addr.getText().toString();
		if(Tools.isEmptyString(name)){
			showToast("联系人不能为空");
			return;
		}else if(!RegularExpressionUtil.isMobilephone(phone)){
			showToast("请检查手机号码");
			return;
		}else if(Tools.isEmptyString(code)){
			showToast("所在地区不能为空");
			return;
		}else if(Tools.isEmptyString(address)){
			showToast("详细地址不能为空");
			return;
		}
		if(isNetConnection()){	
		code = code + address;
		showPD("信息提交中，请稍等");
		GetDataPost.getInstance(ApplyCardActivity.this).applyMyCard(handler, pkuserId, name, phone, code);
		}else {
		showToast("网络不稳，请稍后再试...");
		}
	}
	
	//选择省市区
	private void handleSelectAddress(){
		View view = dialogm();
		final MyAlertDialog dialog1 = new MyAlertDialog(
				ApplyCardActivity.this).builder().setTitle("请选择省市区")
				.setView(view)
				.setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				});
		dialog1.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cityList.size()>0 && areaList.size()>0){
					code = provinceList.get(view_province.getCurrentItem()).getPROVINCE_NAME()
							+ cityList.get(view_city.getCurrentItem()).getCITY_NAME()
							+ areaList.get(view_area.getCurrentItem()).getAREA_NAME();
					
				}else if(cityList.size()>0 &&  areaList.size()==0){
					code = provinceList.get(view_province.getCurrentItem()).getPROVINCE_NAME()
							+ cityList.get(view_city.getCurrentItem()).getCITY_NAME();
					
				}else {
					code = provinceList.get(view_province.getCurrentItem()).getPROVINCE_NAME();
					
				}
				tv_code.setText(""+code);
				
			}
		});
		dialog1.show();
		
	}
	private View dialogm() {
		View contentView = LayoutInflater.from(ApplyCardActivity.this).inflate(
				R.layout.wheelcity_cities_layout, null);
		//finalDb = FinalDb.create(ApplyCardActivity.this,Protocol.DATABASE_NAME);
		finalDb = FinalDb.create(getActivity(),Protocol.DATABASE_NAME, true,Protocol.dbNumer,null);
		provinceList = finalDb.findAll(ProvinceBean.class);
		//初始化值
		cityList = new ArrayList<CityBean>();
		areaList = new ArrayList<AreaBean>();
		//省选择
		 view_province = (WheelView) contentView
				.findViewById(R.id.wheelcity_country);
		view_province.setVisibleItems(3);
		view_province.setViewAdapter(new CountryAdapter(this));
		//城市选择
		 view_city = (WheelView) contentView
				.findViewById(R.id.wheelcity_city);
		// 地区选择
	    view_area = (WheelView) contentView
				.findViewById(R.id.wheelcity_ccity);
		provinceBean = provinceList.get(0);
		cityList = finalDb.findAllByWhere(CityBean.class,"PROVINCE_ID ="+provinceBean.getPROVINCE_ID());
		updateCities(view_city, cityList, 0);
		if(cityList.size()>0){
			cityBean = cityList.get(0);
			areaList = finalDb.findAllByWhere(AreaBean.class, "CITY_ID = "+cityBean.getCITY_ID());
			updatearea(view_area, areaList);
		}else {
			areaList.clear();
			updatearea(view_area,areaList);
		}
		
		view_province.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				
			}
		});
		
		view_province.addScrollingListener(new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
				
			}
			
			@Override
			public void onScrollingFinished(WheelView wheel) {
				provinceBean = provinceList.get(view_province.getCurrentItem());
				if(provinceBean.equals(currentP)){
					return ;
				}
				currentP = provinceBean;
				cityList = finalDb.findAllByWhere(CityBean.class,"PROVINCE_ID ="+provinceBean.getPROVINCE_ID());
				updateCities(view_city, cityList, 0);
				if(cityList.size()>0){
					cityBean = cityList.get(0);
					areaList = finalDb.findAllByWhere(AreaBean.class, "CITY_ID = "+cityBean.getCITY_ID());
					updatearea(view_area, areaList);
				}else {
					areaList.clear();
					updatearea(view_area,areaList);
				}
				
			}
		});
		view_city.addScrollingListener(new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {
			}
			@Override
			public void onScrollingFinished(WheelView wheel) {
				cityBean = cityList.get(view_city.getCurrentItem());
				if(cityBean.equals(currentC)){
					return ;
				}
				currentC = cityBean;
				
				areaList = finalDb.findAllByWhere(AreaBean.class, "CITY_ID = "+cityBean.getCITY_ID());
				updatearea(view_area, areaList);
			}
		});
		view_city.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				
			}
		});

		view_area.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				
			}
		});
		//初始化值
		view_province.setCurrentItem(0);// 设置北京

		return contentView;
	}
	
	/**
	 * Adapter for countries
	 */
	private class CountryAdapter extends AbstractWheelTextAdapter {
		// Countries names
		//private String countries[] = addressData.provinces;//AddressData.PROVINCES;

		/**
		 * Constructor
		 */
		protected CountryAdapter(Context context) {
			super(context, R.layout.wheelcity_country_layout, NO_RESOURCE);
			setItemTextResource(R.id.wheelcity_country_name);
			
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return provinceList.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			String temp = provinceList.get(index).getPROVINCE_NAME();
			if(temp.length()>3){
				temp = temp.substring(0, 3)+"..";
			}
			return temp;
		}
	}
	
	/**
	 * Updates the city wheel
	 */
	private void updateCities(WheelView city, List<CityBean> cities, int index) {
		if(cities.size()>0){
			CityArrayWheelAdapter adapter = new CityArrayWheelAdapter(this,
					cities);
			adapter.setTextSize(18);
			city.setVisibility(View.VISIBLE);
			city.setViewAdapter(adapter);
			city.setCurrentItem(0);
		}else {
			city.setVisibility(View.GONE);
		}
		
	}
	/**
	 * Updates the area wheel
	 */
	private void updatearea(WheelView city, List<AreaBean> areas) {
		if(areas.size()>0){
			AreaArrayWheelAdapter adapter = new AreaArrayWheelAdapter(this,
					areas);
			adapter.setTextSize(18);
			city.setVisibility(View.VISIBLE);
			city.setViewAdapter(adapter);
			city.setCurrentItem(0);
		}else {
			city.setVisibility(View.GONE);
		}
		
	}
	
	
}
