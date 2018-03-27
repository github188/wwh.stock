package com.bm.wanma.ui.fragment;


import com.bm.wanma.R;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.RegularExpressionUtil;
import com.bm.wanma.utils.Tools;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author cm
 * 密码修改
 */
public class PasswordModifyFragment extends BaseFragment implements OnClickListener,OnFocusChangeListener{
	
	private EditText et_last_pwd,et_new_pwd,et_confirm;
	private ImageView iv_last,iv_new,iv_confirm;
	private TextView tv_commit;
	private boolean isLastEye,isNewEye,isConfirmEye;
	private String lastPwd,newPwd,confirmPwd;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragment = inflater.inflate(
				R.layout.fragment_password_modify, container, false);
		init(fragment);
		return fragment;
	}
	
	private void init(View v){
		et_last_pwd = (EditText) v.findViewById(R.id.modify_pwd_last);
		et_last_pwd.setOnFocusChangeListener(this);
		et_last_pwd.addTextChangedListener(new MyTextWatch());
		et_new_pwd = (EditText) v.findViewById(R.id.modify_pwd_new);
		et_new_pwd.setOnFocusChangeListener(this);
		et_new_pwd.addTextChangedListener(new MyTextWatch());
		et_confirm = (EditText) v.findViewById(R.id.modify_pwd_confirm);
		et_confirm.setOnFocusChangeListener(this);
		et_confirm.addTextChangedListener(new MyTextWatch());
		iv_last =  (ImageView) v.findViewById(R.id.modify_pwd_last_eyes);
		iv_last.setOnClickListener(this);
		iv_new =  (ImageView) v.findViewById(R.id.modify_pwd_new_eyes);
		iv_new.setOnClickListener(this);
		iv_confirm =  (ImageView) v.findViewById(R.id.modify_pwd_confirm_eyes);
		iv_confirm.setOnClickListener(this);
		tv_commit = (TextView) v.findViewById(R.id.commit_modify_pwd);
		tv_commit.setOnClickListener(this);
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		showToast("修改成功");
		PreferencesUtil.setPreferences(getActivity(), "password", Tools.encoderByMd5(newPwd));
		getActivity().finish();
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		showToast(bundle.getString(Protocol.MSG));

	}
	
	private void handleCommit(){
		lastPwd = et_last_pwd.getText().toString();
		newPwd = et_new_pwd.getText().toString();
		confirmPwd = et_confirm.getText().toString();
		if(TextUtils.isEmpty(lastPwd)){
			showToast("原密码不能为空");
			return;
		}
		if(TextUtils.isEmpty(newPwd)){
			showToast("新密码不能为空");
			return;
		}
		if(TextUtils.isEmpty(confirmPwd)){
			showToast("确认密码不能为空");
			return;
		}
		if (!RegularExpressionUtil.isPasswordLength(newPwd)) {
			showToast("请输入6-8位的密码");
			return;
		}
		if(lastPwd.equals(newPwd)){
			showToast("新密码不能和老密码一样");
			return;
		}
		if(!confirmPwd.equals(newPwd)){
			showToast("新密码和确认密码不一样");
			return;
		}
		if(isNetConnection()){
			String uId = PreferencesUtil.getStringPreferences(getActivity(), "pkUserinfo");
			showPD("正在提交修改信息，请稍等...");
			GetDataPost.getInstance(getActivity()).modifyPwd(uId,Tools.encoderByMd5(lastPwd),
					Tools.encoderByMd5(newPwd), handler);
		}else {
			showToast("亲，网络不稳，请检查网络连接!");
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commit_modify_pwd:
			handleCommit();
			
			break;
		case R.id.modify_pwd_last_eyes:
			if (isLastEye) {
				isLastEye = false;
				iv_last.setImageResource(R.drawable.btn_eyes);
				et_last_pwd
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
			} else {
				isLastEye = true;
				iv_last.setImageResource(R.drawable.btn_eyes2);
				et_last_pwd
						.setTransformationMethod(HideReturnsTransformationMethod
								.getInstance());
			}
			lastPwd = et_last_pwd.getText().toString();
			et_last_pwd.setText(lastPwd);
			et_last_pwd.setSelection(lastPwd.length());
			break;
		case R.id.modify_pwd_new_eyes:
			if (isNewEye) {
				isNewEye = false;
				iv_new.setImageResource(R.drawable.btn_eyes);
				et_new_pwd
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
			} else {
				isNewEye = true;
				iv_new.setImageResource(R.drawable.btn_eyes2);
				et_new_pwd
						.setTransformationMethod(HideReturnsTransformationMethod
								.getInstance());
			}
			newPwd = et_new_pwd.getText().toString();
			et_new_pwd.setText(newPwd);
			et_new_pwd.setSelection(newPwd.length());
			break;
		case R.id.modify_pwd_confirm_eyes:
			if (isConfirmEye) {
				isConfirmEye = false;
				iv_confirm.setImageResource(R.drawable.btn_eyes);
				et_confirm
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
			} else {
				isConfirmEye = true;
				iv_confirm.setImageResource(R.drawable.btn_eyes2);
				et_confirm
						.setTransformationMethod(HideReturnsTransformationMethod
								.getInstance());
			}
			confirmPwd = et_confirm.getText().toString();
			et_confirm.setText(confirmPwd);
			et_confirm.setSelection(confirmPwd.length());
			break;
		default:
			break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus){
			switch (v.getId()) {
			case R.id.modify_pwd_last:
				iv_last.setVisibility(View.VISIBLE);
				iv_new.setVisibility(View.GONE);
				iv_confirm.setVisibility(View.GONE);
				break;
			case R.id.modify_pwd_new:
				iv_last.setVisibility(View.GONE);
				iv_new.setVisibility(View.VISIBLE);
				iv_confirm.setVisibility(View.GONE);
				break;
			case R.id.modify_pwd_confirm:
				iv_last.setVisibility(View.GONE);
				iv_new.setVisibility(View.GONE);
				iv_confirm.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
	}

	
	// 原密码textwatch
		private class MyTextWatch implements TextWatcher {

			@Override
			public void afterTextChanged(Editable s) {
				if(!TextUtils.isEmpty(et_last_pwd.getText().toString()) &&
						!TextUtils.isEmpty(et_new_pwd.getText().toString()) &&
						!TextUtils.isEmpty(et_confirm.getText().toString())){
					tv_commit.setEnabled(true);
					tv_commit.setBackgroundResource(R.drawable.common_button_shape_activate);
				} else {
					tv_commit.setEnabled(false);
					tv_commit.setBackgroundResource(R.drawable.feedback_commit_bg_gray);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

		}
	
	
	
}
