package com.bm.wanma.ui.activity;

import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.adapter.EmergencyCallAdapter;
import com.bm.wanma.entity.EmergencyCallBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.view.MyDetailListView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * @author cm
 * 急救电话
 */
public class BackEmergencyCallActivity extends BaseActivity implements OnClickListener{

	private ImageButton ib_back;
	private MyDetailListView mListView;
	private EmergencyCallAdapter maAdapter;
	private ArrayList<EmergencyCallBean> mEmergencyCallBeans;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_call);
		ib_back = (ImageButton) findViewById(R.id.emergency_call_back);
		ib_back.setOnClickListener(this);
		mListView = (MyDetailListView) findViewById(R.id.emergency_call_listview);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.emergency_call_back:
			finish();
			break;

		default:
			break;
		}
		
	}

	@Override
	protected void getData() {
		GetDataPost.getInstance(this).getEmergencyCall(handler);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if(bundle != null ){
			mEmergencyCallBeans = (ArrayList<EmergencyCallBean>) bundle.getSerializable(Protocol.DATA);
			maAdapter = new EmergencyCallAdapter(this, mEmergencyCallBeans);
			mListView.setAdapter(maAdapter);
		}

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		showToast(bundle.getString(Protocol.MSG));
		finish();

	}



}
