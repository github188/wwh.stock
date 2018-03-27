package com.bm.wanma.ui.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bm.wanma.R;
import com.bm.wanma.entity.EmergencyCallBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.sortlistview.CharacterParser;
import com.bm.wanma.sortlistview.ClearEditText;
import com.bm.wanma.sortlistview.EmergencyPinyinComparator;
import com.bm.wanma.sortlistview.EmergencyTelSortAdapter;
import com.bm.wanma.sortlistview.EmergencyTelSortModel;
import com.bm.wanma.sortlistview.SideBar;
import com.bm.wanma.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.bm.wanma.utils.LogUtil;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * cm
 * 急救电话
 */
public class EmergencyCallSortActivity extends BaseActivity implements OnClickListener{
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private EmergencyTelSortAdapter adapter;
	private ClearEditText mClearEditText;
	private CharacterParser characterParser;
	private List<EmergencyTelSortModel> SourceDateList;
	private EmergencyPinyinComparator pinyinComparator;
	
	private ArrayList<EmergencyCallBean> EmergencyCallListBean;
	private ImageButton ib_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_sort_tel);
		ib_back = (ImageButton)findViewById(R.id.select_car_brand_back);
		ib_back.setOnClickListener(this);
		//initViews();
	}

	private void initViews() {
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new EmergencyPinyinComparator();
		
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
				//Toast.makeText(getApplication(), ((EmergencyTelSortModel)adapter.getItem(position)).getName(), 1).show();
			}
		});
		
		SourceDateList = filledData(EmergencyCallListBean);
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new EmergencyTelSortAdapter(this, SourceDateList);
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
	
	@SuppressLint("DefaultLocale")
	private List<EmergencyTelSortModel> filledData(ArrayList<EmergencyCallBean>  list ){
		
		List<EmergencyTelSortModel> mSortList = new ArrayList<EmergencyTelSortModel>();
		for(EmergencyCallBean bean : list){
			EmergencyTelSortModel sortModel = new EmergencyTelSortModel();
			String name = bean.getCom_name();
			sortModel.setTel(bean.getCom_phone());
			sortModel.setName(name);
			String pinyin = characterParser.getSelling(name);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}

	
	private void filterData(String filterStr){
		List<EmergencyTelSortModel> filterDateList = new ArrayList<EmergencyTelSortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(EmergencyTelSortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				String tel = sortModel.getTel();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString()) ||
						tel.indexOf(filterStr.toString()) != -1 ){
					filterDateList.add(sortModel);
				}
			}
		}
		
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	
	@Override
	protected void getData() {
		GetDataPost.getInstance(this).getEmergencyCall(handler);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (bundle != null) {
			EmergencyCallListBean = (ArrayList<EmergencyCallBean>) bundle.getSerializable(Protocol.DATA);
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
