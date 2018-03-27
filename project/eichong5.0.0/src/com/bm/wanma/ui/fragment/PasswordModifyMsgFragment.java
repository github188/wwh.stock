package com.bm.wanma.ui.fragment;


import com.bm.wanma.R;
import com.bm.wanma.net.GetDataGet;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.RegularExpressionUtil;
import com.bm.wanma.utils.Tools;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author cm
 * 短信验证码修改
 */
public class PasswordModifyMsgFragment extends BaseFragment implements OnClickListener,OnFocusChangeListener{
	
	private EditText et_captcha,et_new_pwd,et_confirm;
	private ImageView iv_new,iv_confirm;
	private Button btn_chptcha;
	private TextView tv_commit;
	private boolean isNewEye,isConfirmEye;
	private String captcha,newPwd,confirmPwd,userPhone;
	private MyCount mc;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userPhone = PreferencesUtil.getStringPreferences(getActivity(), "usinPhone");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragment = inflater.inflate(
				R.layout.fragment_password_modify_msg, container, false);
		init(fragment);
		return fragment;
	}
	
	private void init(View v){
		et_captcha = (EditText) v.findViewById(R.id.modify_pwd_last);
		et_new_pwd = (EditText) v.findViewById(R.id.modify_pwd_new);
		et_new_pwd.setOnFocusChangeListener(this);
		et_new_pwd.addTextChangedListener(new MyTextWatch());
		et_confirm = (EditText) v.findViewById(R.id.modify_pwd_confirm);
		et_confirm.setOnFocusChangeListener(this);
		et_confirm.addTextChangedListener(new MyTextWatch());
		btn_chptcha =  (Button) v.findViewById(R.id.register_btn_captcha);
		btn_chptcha.setOnClickListener(this);
		btn_chptcha.addTextChangedListener(new MyTextWatch());
		iv_new =  (ImageView) v.findViewById(R.id.modify_pwd_new_eyes);
		iv_new.setOnClickListener(this);
		iv_confirm =  (ImageView) v.findViewById(R.id.modify_pwd_confirm_eyes);
		iv_confirm.setOnClickListener(this);
		tv_commit = (TextView) v.findViewById(R.id.commit_modify_pwd);
		tv_commit.setOnClickListener(this);
	}

	@Override
	public void onSuccess(String sign, Bundle bundle) {
		cancelPD();
		if (sign.equals(Protocol.RESET_PWD)) {
			showToast("修改成功");
			PreferencesUtil.setPreferences(getActivity(), "password", newPwd);
			getActivity().finish();
		} else if (Tools.judgeString(sign, Protocol.GET_AUTH_CODE)) {
			showToast("请查收验证码");
		}
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		cancelPD();
		showToast(bundle.getString(Protocol.MSG));

	}
	
	private void handleCommit(){
		captcha = et_captcha.getText().toString();
		newPwd = et_new_pwd.getText().toString();
		confirmPwd = et_confirm.getText().toString();
		if(TextUtils.isEmpty(captcha)){
			showToast("请输入验证码");
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
		if(!confirmPwd.equals(newPwd)){
			showToast("新密码和确认密码不一样");
			return;
		}
		if(isNetConnection()){
			if (isNetConnection()) {
				newPwd = Tools.encoderByMd5(newPwd);
				showPD("正在提交修改信息，请稍等...");
				GetDataPost.getInstance(getActivity()).resetPwd(userPhone,newPwd,captcha,"1000",handler);
			} else {
				showToast("亲，网络不稳，请检查网络连接!");
			}
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
		case R.id.register_btn_captcha:
			//获取短信验证码
			//userPhone = PreferencesUtil.getStringPreferences(getActivity(), "usinPhone");
			// 发送验证码
			getCode();
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
			case R.id.modify_pwd_new:
				iv_new.setVisibility(View.VISIBLE);
				iv_confirm.setVisibility(View.GONE);
				break;
			case R.id.modify_pwd_confirm:
				iv_new.setVisibility(View.GONE);
				iv_confirm.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
	}
	// 获取短信验证码
		public void getCode() {
			if (isNetConnection()) {
				showPD("正在获取信息");
				GetDataGet.getInstance(getActivity()).getCode(handler, userPhone);
				mc = new MyCount(60000, 1000);
				mc.start();
			} else {
				showToast("亲，网络不稳，请稍后再试...");
			}
		}
		
		
		
		
		// 定义一个倒计时的内部类
		class MyCount extends CountDownTimer {
			public MyCount(long millisInFuture, long countDownInterval) {
				super(millisInFuture, countDownInterval);
			}

			@Override
			public void onFinish() {
				btn_chptcha.setClickable(true);
				btn_chptcha.setText("重新获取验证码");
				btn_chptcha .setBackgroundColor(Color.parseColor("#ff8800"));
			}

			@Override
			public void onTick(long millisUntilFinished) {
				btn_chptcha.setClickable(false);
				btn_chptcha.setText(millisUntilFinished / 1000 + "秒重新获取");
				btn_chptcha .setBackgroundColor(Color.parseColor("#dcdcdc"));
			}
		}
		// 原密码textwatch
				private class MyTextWatch implements TextWatcher {
					@Override
					public void afterTextChanged(Editable s) {
						if(!TextUtils.isEmpty(et_captcha.getText().toString()) &&
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
