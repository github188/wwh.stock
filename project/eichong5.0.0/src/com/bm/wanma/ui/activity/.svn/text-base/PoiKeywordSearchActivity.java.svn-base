package com.bm.wanma.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.bm.wanma.R;
import com.bm.wanma.adapter.PoiAdcodeAdapter;
import com.bm.wanma.adapter.PoiKeywordAdapter;
import com.bm.wanma.entity.PoiAdcodeBean;
import com.bm.wanma.entity.PoiLatLngBean;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.ToastUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PoiKeywordSearchActivity extends Activity implements TextWatcher,OnPoiSearchListener,OnClickListener,OnGeocodeSearchListener{
	private AutoCompleteTextView act_search;
	private ImageButton ib_back;
	private TextView tv_search;
	private String keyWord = "";// 要输入的poi搜索关键字
	private ProgressDialog progDialog = null;// 搜索时进度条
	private PoiResult poiResult; // poi返回的结果
	private int currentPage = 0;// 当前页面，从0开始计数
	private PoiSearch.Query query;// Poi查询条件类
	private PoiSearch poiSearch;// POI搜索
	private GeocodeSearch geocoderSearch ;
	private String lat,lng;
	private LatLonPoint latLonPoint;
	
	private ArrayList<PoiLatLngBean> initDataList;
	private ArrayList<PoiAdcodeBean> initDataTipList;
	private ListView mListView;
	private PoiKeywordAdapter mPoiKeywordAdapter;
	private PoiAdcodeAdapter mAdcodeAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poikeyword_search);
		act_search = (AutoCompleteTextView) findViewById(R.id.check_latlng_title_et);
		act_search.addTextChangedListener(this);// 添加文本输入框监听事件
		tv_search = (TextView) findViewById(R.id.check_latlng_title_search);
		tv_search.setOnClickListener(this);
		ib_back = (ImageButton) findViewById(R.id.check_latlng_title_back);
		ib_back.setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.check_latlng_title_search_listview);
		initDataList = new ArrayList<PoiLatLngBean>();
		initDataTipList = new ArrayList<PoiAdcodeBean>();
		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch.setOnGeocodeSearchListener(this);
		lat = PreferencesUtil.getStringPreferences(this, "currentlat");
		lng = PreferencesUtil.getStringPreferences(this, "currentlng");
		latLonPoint = new LatLonPoint(Double.valueOf(lat), Double.valueOf(lng));
		// latLonPoint参数表示一个Latlng，第二参数表示范围多少米，GeocodeSearch.AMAP表示是国测局坐标系还是GPS原生坐标系
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 1000,
				GeocodeSearch.AMAP);
		geocoderSearch.getFromLocationAsyn(query);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.common_orange);
        }
	}
	
	@TargetApi(19) 
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.check_latlng_title_search:
			doSearchQuery();
			break;
		case R.id.check_latlng_title_back:
			finish();
			break;

		default:
			break;
		}
		
	}

	/**
	 * 开始进行poi搜索
	 */
	protected void doSearchQuery() {
		showProgressDialog();// 显示进度框
		currentPage = 0;
		keyWord = act_search.getText().toString();
		query = new PoiSearch.Query(
				keyWord,
				"汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务" +
				"|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施",
				"");
		// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		// poi搜索类型共分为以下20种：汽车服务|汽车销售|
		//汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
		//住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
		//金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
		query.setPageSize(50);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页
		poiSearch = new PoiSearch(this, query);
		poiSearch.setOnPoiSearchListener(this);
		poiSearch.searchPOIAsyn();
	}

	@Override
	public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * POI信息查询回调方法
	 */ 
	@Override
	public void onPoiSearched(PoiResult result, int rCode) {
		dissmissProgressDialog();// 隐藏对话框
		if(0 == rCode){
			if (result != null && result.getQuery() != null) {// 搜索poi的结果
				poiResult = result;
				// 取得搜索到的poiitems有多少页
				List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
				initDataList.clear();
				for (PoiItem poiItem : poiItems) {
					PoiLatLngBean itemBean = new PoiLatLngBean();
					itemBean.setTitle(poiItem.getTitle());
					itemBean.setLat(String.valueOf(poiItem.getLatLonPoint().getLatitude()));
					itemBean.setLng(String.valueOf(poiItem.getLatLonPoint().getLongitude()));
					initDataList.add(itemBean);
				}
				mPoiKeywordAdapter = new PoiKeywordAdapter(PoiKeywordSearchActivity.this, initDataList);
				mListView.setAdapter(mPoiKeywordAdapter);
				mListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent data = new Intent();
						data.putExtra("poikeywrod", initDataList.get(position));
						setResult(RESULT_OK, data);
						finish();
					}
				});
				}
			}
		}  
		
		
		
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		/*String newText = s.toString().trim();
		if(newText.length()>0){
			Inputtips inputTips = new Inputtips(PoiKeywordSearchActivity.this,
					new InputtipsListener() {
						@Override
						public void onGetInputtips(List<Tip> tipList, int rCode) {
							if (rCode == 0) {// 正确返回
								initDataTipList.clear();
								for (int i = 0; i < tipList.size(); i++) {
									PoiAdcodeBean itemBean = new PoiAdcodeBean();
									itemBean.setTitle(tipList.get(i).getName());
									itemBean.setAdcode(tipList.get(i).getAdcode());
									initDataTipList.add(itemBean);
								} 
								
								mAdcodeAdapter = new PoiAdcodeAdapter(PoiKeywordSearchActivity.this, initDataTipList);
								mListView.setAdapter(mAdcodeAdapter);
								mListView.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(AdapterView<?> parent, View view,
											int position, long id) {
										Intent data = new Intent();
										data.putExtra("poikeywrod", initDataTipList.get(position));
										setResult(RESULT_FIRST_USER, data);
										finish();
									}
								});
								
							}
						}
					});
			try {
				inputTips.requestInputtips(newText, "");// 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号

			} catch (AMapException e) {
				e.printStackTrace();
			}
			
		}else {
			mListView.setAdapter(mPoiKeywordAdapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent data = new Intent();
					data.putExtra("poikeywrod", initDataList.get(position));
					setResult(RESULT_OK, data);
					finish();
				}
			});
		}
		
		*/
	}
	/**
	 * 显示进度框
	 */
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("正在搜索:\n" + keyWord);
		progDialog.show();
	}

	/**
	 * 隐藏进度框
	 */
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int arg1) {
		List<PoiItem> poiItems  = result.getRegeocodeAddress().getPois();
		for (PoiItem poiItem : poiItems) {
			PoiLatLngBean itemBean = new PoiLatLngBean();
			itemBean.setLat(String.valueOf(poiItem.getLatLonPoint().getLatitude()));
			itemBean.setLng(String.valueOf(poiItem.getLatLonPoint().getLongitude()));
			itemBean.setTitle(poiItem.getTitle());
			initDataList.add(itemBean);
		}
		//初始化列表值
		mPoiKeywordAdapter = new PoiKeywordAdapter(PoiKeywordSearchActivity.this, initDataList);
		mListView.setAdapter(mPoiKeywordAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent data = new Intent();
				data.putExtra("poikeywrod", initDataList.get(position));
				setResult(RESULT_OK, data);
				finish();
			}
		});
		
	}

}
