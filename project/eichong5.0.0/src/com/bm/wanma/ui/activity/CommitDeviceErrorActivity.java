package com.bm.wanma.ui.activity;

import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.adapter.MyDeviceErrorGridViewAdapter;
import com.bm.wanma.entity.EquipmentBean;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
import com.bm.wanma.view.MyDetailGridView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author cm
 * 设备纠错界面
 */
public class CommitDeviceErrorActivity extends BaseActivity implements OnClickListener{

	private ImageButton ib_back;
	private MyDetailGridView mGridView;
	private TextView tv_commit;
	private EditText et_content;
	private ArrayList<EquipmentBean> mbeanlist;
	private EquipmentBean mEquipmentBean;
	private MyDeviceErrorGridViewAdapter mAdapter;
	private String userId,errorType,electricId,electricType,content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commit_device_error);
		initView();
	}

	private void initView(){
		ib_back = (ImageButton) findViewById(R.id.commit_device_error_back);	
		ib_back.setOnClickListener(this);
		mGridView = (MyDetailGridView) findViewById(R.id.commit_device_error_gridview);
		tv_commit = (TextView) findViewById(R.id.commit_device_error_commit);
		tv_commit.setOnClickListener(this);
		et_content = (EditText) findViewById(R.id.commit_device_error_edit);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commit_device_error_back:
			finish();
			break;
		case R.id.commit_device_error_commit:
			//提交纠错
			if(!Tools.isEmptyString(errorType)){
				showPD("正在提交数据...");
				content = et_content.getText().toString().trim();
				userId = PreferencesUtil.getStringPreferences(CommitDeviceErrorActivity.this, "pkUserinfo");
				GetDataPost.getInstance(CommitDeviceErrorActivity.this).commitEquipment(handler, userId,
						errorType, electricId, electricType, content);
			}else {
				showToast("请选择纠错内容!");
			}
			
			 
			break;
		default:
			break;
		}
		
	}
	
	@Override
	protected void getData() {
		electricId = getIntent().getStringExtra("epId");
		electricType = getIntent().getStringExtra("deviceType");
		if(isNetConnection()){
			GetDataPost.getInstance(this).getEquipment(handler);
		}else {
			showToast("网络不稳，请稍后再试");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		if (sign.equals(Protocol.GET_EQUIPMENT_TYPE)) {
			mbeanlist = (ArrayList<EquipmentBean>) bundle.getSerializable(Protocol.DATA);
			mAdapter = new MyDeviceErrorGridViewAdapter(this, mbeanlist);
			mGridView.setAdapter(mAdapter);
			mGridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					mAdapter.setSelection(position);
					mEquipmentBean = mbeanlist.get(position);
					if(mEquipmentBean != null){
						errorType = mEquipmentBean.getPk_ConfigContent();
						if("其他".equals(mEquipmentBean.getCoCo_Content().trim())){
							et_content.setVisibility(View.VISIBLE);
						}else {
							et_content.setVisibility(View.GONE);
						}
					}
				}
			});
			
		}else if (sign.equals(Protocol.COMMIT_EQUIPMENT)) {
			showToast("提交成功");
			finish();
		}
		

	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		showToast(bundle.getString(Protocol.MSG));
		if(sign.equals(Protocol.GET_EQUIPMENT_TYPE)){
			finish();
		}

	}

	

}
