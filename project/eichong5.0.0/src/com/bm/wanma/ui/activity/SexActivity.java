package com.bm.wanma.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bm.wanma.R;

/**
 * @author cm
 * 性别
 */
public class SexActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton ib_back;
	private TextView tv_man,tv_female;
	private Drawable drawable;
	private String sex;
	private Intent data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sex);
		ib_back = (ImageButton) findViewById(R.id.security_settings_back);
		ib_back.setOnClickListener(this);
		tv_man = (TextView) findViewById(R.id.sex_man);
		tv_man.setOnClickListener(this);
		tv_female = (TextView) findViewById(R.id.sex_female);
		tv_female.setOnClickListener(this);
		sex = getIntent().getStringExtra("sex");
		drawable = getResources().getDrawable(R.drawable.btn_sex_selected);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());  
		//0 男 1 女属性
		if("0".equals(sex)){
			tv_man.setCompoundDrawables(null, null, drawable, null);  
			tv_female.setCompoundDrawables(null, null, null, null);  
		}else if("1".equals(sex)){
			tv_female.setCompoundDrawables(null, null, drawable, null);  
			tv_man.setCompoundDrawables(null, null, null, null);  
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.security_settings_back:
			finish();
			break;
		case R.id.sex_man:
			tv_man.setCompoundDrawables(null, null, drawable, null);  
			tv_female.setCompoundDrawables(null, null, null, null);  
			sex = "0";
			data = new Intent();
			data.putExtra("sex", sex);
			setResult(RESULT_OK, data);
			finish();
			break;
		case R.id.sex_female:
			tv_man.setCompoundDrawables(null, null, null, null);  
			tv_female.setCompoundDrawables(null, null, drawable, null);  
			sex = "1";
			data = new Intent();
			data.putExtra("sex", sex);
			setResult(RESULT_OK, data);
			finish();
			break;
		default:
			break;
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
