package com.bm.wanma.widget;


import com.bm.wanma.R;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;


public class LoadingDialog extends Dialog {
    private TextView mTextTv;

    public LoadingDialog(Context context) {
        super(context, R.style.Theme_Dialog);
        setContentView(R.layout.common_dialog_loading);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        mTextTv = (TextView) findViewById(R.id.text);
    }

    public void setText(String text) {
        mTextTv.setText(TextUtils.isEmpty(text) ? "" : text);
    }

    public String getText() {
        return mTextTv.getText().toString();
    }
}
