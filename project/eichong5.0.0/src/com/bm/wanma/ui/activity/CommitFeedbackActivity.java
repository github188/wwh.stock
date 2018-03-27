package com.bm.wanma.ui.activity;


import com.bm.wanma.R;
import com.bm.wanma.dialog.CommentSuccessDialog;
import com.bm.wanma.net.GetDataPost;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.view.ContainsEmojiEditText;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author cm
 * 意见反馈
 */
public class CommitFeedbackActivity extends BaseActivity implements OnClickListener{
	
	private ImageButton ib_back;
	private TextView tv_commit;
	private ContainsEmojiEditText et_content;
	private String content,pkuserId;
	private CommentSuccessDialog mdialog;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commit_feedback);
		content = PreferencesUtil.getStringPreferences(this, "feedback");
		ib_back = (ImageButton) findViewById(R.id.commit_feedback_back);
		ib_back.setOnClickListener(this);
		tv_commit = (TextView) findViewById(R.id.commit_feedback_commit);
		et_content = (ContainsEmojiEditText) findViewById(R.id.commit_feedback_content);
		et_content.addTextChangedListener(new MyRegistTextWatch());
		if(!TextUtils.isEmpty(content)){
			et_content.setText(content);
			et_content.setSelection(content.length());	
		}
		
	}
	
	

	@Override
	protected void getData() {
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commit_feedback_back:
			PreferencesUtil.setPreferences(this, "feedback",et_content.getText().toString());
			finish();
			break;
		case R.id.commit_feedback_commit:
			//提交反馈
			if(isNetConnection()){
				pkuserId = PreferencesUtil.getStringPreferences(CommitFeedbackActivity.this,"pkUserinfo");
				content = et_content.getText().toString().trim();
				GetDataPost.getInstance(CommitFeedbackActivity.this).commitMyFeedback(handler, pkuserId, content);
				
			}else {
				showToast("网络不稳，请稍后再试...");
			}
			break;
		default:
			break;
		}
		
	}
	
	// 两次返回键退出程序
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK
					&& event.getAction() == KeyEvent.ACTION_DOWN) {
				PreferencesUtil.setPreferences(this, "feedback",et_content.getText().toString());
				finish();
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}
	
	@Override
	public void onSuccess(String sign, Bundle bundle) {
		/*mdialog = new CommentSuccessDialog(this);
		mdialog.setCancelable(false);
		mdialog.setTextVisible();
		mdialog.setValueToText("提交成功!", "谢谢您的宝贵意见，稍后可至我的消息中查看反馈结果！");
		mdialog.setOnPositiveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mdialog.dismiss();
				finish();
			}
		});
		mdialog.show();*/
		showToast("提交成功");
		PreferencesUtil.setPreferences(this, "feedback", "");
		finish();
	}

	@Override
	public void onFaile(String sign, Bundle bundle) {
		showToast(bundle.getString(Protocol.MSG));
		finish();

	}
	private class MyRegistTextWatch implements TextWatcher{
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		@Override
		public void onTextChanged(CharSequence str, int start, int before,
				int count) {
		}
		@SuppressLint("NewApi")
		@Override
		public void afterTextChanged(Editable s) {
			String tempcon = et_content.getText().toString().trim();
			if (TextUtils.isEmpty(tempcon)) {
				//输入框没值，立即充值置灰
				tv_commit.setOnClickListener(null);
				tv_commit.setBackground(getResources().getDrawable(
						R.drawable.feedback_commit_bg_gray));
			}else {
				tv_commit.setOnClickListener(CommitFeedbackActivity.this);
				tv_commit.setBackground(getResources().getDrawable(
						R.drawable.popup_select_shape_confirm));
			}
		}
	}
	
	   /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
	public static boolean containsEmoji(String source) {
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
				return true;
			}
		}
		return false;
	}
	
	  /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}
	
	
	

}
