package com.bm.wanma.ui.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bm.wanma.R;
import com.bm.wanma.entity.CarTypeBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.sortlistview.CharacterParser;
import com.bm.wanma.sortlistview.ClearEditText;
import com.bm.wanma.sortlistview.PinyinComparator;
import com.bm.wanma.sortlistview.SideBar;
import com.bm.wanma.sortlistview.SortAdapter;
import com.bm.wanma.sortlistview.SortModel;
import com.bm.wanma.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.bm.wanma.utils.LogUtil;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * cm
 * 选择车的品牌
 */
public class SelectCarBrandActivity extends BaseActivity implements OnClickListener{
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	private PinyinComparator pinyinComparator;
	
	private ArrayList<CarTypeBean> mCarListBean;
	private ArrayList<String> mCarBrandnames;
	private String [] msortData;
	private ImageButton select_brand_ibtn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_car_brand);
		mCarBrandnames = new ArrayList<String>();
		select_brand_ibtn_back = (ImageButton)findViewById(R.id.select_car_brand_back);
		select_brand_ibtn_back.setOnClickListener(this);
		//initViews();
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0 && resultCode == RESULT_OK){
			setResult(RESULT_OK, data);
			finish();//finish应该写到这个地方
		}
		
	}


	private void initViews() {
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
			}
		});
		
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 Intent in = new Intent();
				 String tempName = ((SortModel)adapter.getItem(position)).getName();
				 String carId = null;
				 for(CarTypeBean bean : mCarListBean){
						if(tempName.equals(bean.getParaName())){
							carId = bean.getPkParaconfig();
						}
				}
				in.putExtra("carbrandname", ((SortModel)adapter.getItem(position)).getName());
				in.putExtra("carbrand",carId);
				in.setClass(SelectCarBrandActivity.this, SelectCarNameActivity.class);
				startActivityForResult(in, 0);
				//Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
			}
		});
		
		
		for(CarTypeBean bean : mCarListBean){
			if(bean.getParaName() != null){
				mCarBrandnames.add(bean.getParaName());
			}
		}
		msortData = (String[]) mCarBrandnames.toArray(new String[0]);
		
		//SourceDateList = filledData(getResources().getStringArray(R.array.date));
		SourceDateList = filledData(msortData);
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);
		
		
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
		
		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			try {
				String pinyin = characterParser.getSelling(date[i]);
				Log.i("cm_socket", "pinyin==="+pinyin);
				String sortString = pinyin.substring(0, 1).toUpperCase();//crash
				if(sortString.matches("[A-Z]")){
					sortModel.setSortLetters(sortString.toUpperCase());
				}else{
					sortModel.setSortLetters("#");
				}
				mSortList.add(sortModel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mSortList;
		
	}
	
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	
	@Override
	protected void getData() {
		GetDataPost.getInstance(this).getCarType(handler);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (sign.equals(Protocol.FIND_PARACONFIG_LIST) && bundle != null) {
			mCarListBean = (ArrayList<CarTypeBean>) bundle.getSerializable(Protocol.DATA);
			initViews();
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.select_car_brand_back:
			finish();
			break;

		}
		
	}

}
