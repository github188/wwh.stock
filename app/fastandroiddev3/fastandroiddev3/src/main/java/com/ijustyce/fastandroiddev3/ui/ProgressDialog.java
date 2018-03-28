package com.ijustyce.fastandroiddev3.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.ijustyce.fastandroiddev3.R;

/**
 * Created by zhuxiujie on 16/3/23.
 */
public class ProgressDialog extends Dialog{

    private TextView msgView;
    public ProgressDialog(Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.fastandroiddev3_dialog_progress);
        setCanceledOnTouchOutside(true);
    }

    public ProgressDialog(Context context, String msg){
        this(context);
        setMsg(msg);
    }

    public void setMsg(String msg){
        if (msgView == null) msgView = (TextView) findViewById(R.id.msg);
        if (msgView != null) msgView.setText(msg);
    }
}
