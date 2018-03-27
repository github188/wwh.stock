package com.bm.wanma.dialog;

import com.bm.wanma.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * @Function: eichong4.0版本选择头像对话框
 * @author cm
 */
public class TakePhotoDialog extends Dialog {

	public TakePhotoDialog(Context context) {
		this(context, R.layout.take_photo_dialog, R.style.customCommonDialog);
	}

	public TakePhotoDialog(Context context, int layout, int style) {
		super(context, style);
		setContentView(layout);
		Window window = getWindow();
		window.setBackgroundDrawableResource(android.R.color.transparent);
		WindowManager.LayoutParams params = window.getAttributes();
		params.gravity = Gravity.BOTTOM;
		window.setAttributes(params);
	}

	public TextView getTakeTextView() {
		return (TextView) findViewById(R.id.take);
	}

	public TextView getSelectTextView() {
		return (TextView) findViewById(R.id.select);
	}

	public TextView getCancelTextView() {
		return (TextView) findViewById(R.id.cancel);
	}

}