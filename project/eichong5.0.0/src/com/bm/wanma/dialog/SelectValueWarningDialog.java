package com.bm.wanma.dialog;

import com.bm.wanma.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * @Function:  筛选条件，警告 对话框--返回 去填写
 * @author cm
 */
public class SelectValueWarningDialog extends Dialog {
    private TextView positiveButton,negativeButton;
 
    public SelectValueWarningDialog(Context context) {
        super(context,R.style.ChargePayDialog);
        setCustomDialog();
    }
    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_select_value_warnig, null);
        positiveButton = (TextView) mView.findViewById(R.id.dialog_select_value_go);
        negativeButton = (TextView) mView.findViewById(R.id.dialog_select_value_back);
        super.setContentView(mView);
        mView.setBackgroundResource(R.drawable.common_shape_dialog);
    }
     
     
     @Override
    public void setContentView(int layoutResID) {
    }
 
    @Override
    public void setContentView(View view, LayoutParams params) {
    }
 
    @Override
    public void setContentView(View view) {
    }
 
    /**
     * 确定键监听器
     * @param listener
     */ 
    public void setOnPositiveListener(View.OnClickListener listener){ 
        positiveButton.setOnClickListener(listener); 
    } 
    
    /**
     * 取消键监听器
     * @param listener
     */ 
    public void setOnNegativeListener(View.OnClickListener listener){ 
        negativeButton.setOnClickListener(listener); 
    }
}