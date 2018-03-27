package com.bm.wanma.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bm.wanma.R;
import com.bm.wanma.view.ContainsEmojiEditText;

/**
 * @author cm
 * 地址
 */
public class AddressActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton ib_back;
	private TextView tv_save;
	private ContainsEmojiEditText et_nick;
	private String nick;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		ib_back = (ImageButton) findViewById(R.id.security_settings_back);
		ib_back.setOnClickListener(this);
		tv_save = (TextView) findViewById(R.id.nick_save);
		tv_save.setOnClickListener(this);
		et_nick = (ContainsEmojiEditText) findViewById(R.id.nick_content);
		nick = getIntent().getStringExtra("address");
		if(nick != null){
			et_nick.setText(nick);
			et_nick.setSelection(nick.length());
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.security_settings_back:
			finish();
			break;
		case R.id.nick_save:
			nick = et_nick.getText().toString();
			if(nick.length()>0){
				Intent data = new Intent();
				data.putExtra("address", nick);
				setResult(RESULT_OK, data);
				finish();
			}else {
				showToast("请输入地址");
			}
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
