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
 * @Function: 清空历史记录提示  -- 确定清除 
 * @author cm
 */
public class TipClearHistoryDialog extends Dialog {
    private TextView positiveButton;
    private TextView negativeButton;
    private TextView tv_content;
    private String content;
 
    public TipClearHistoryDialog(Context context,String str) {
        super(context,R.style.ChargePayDialog);
        content = str;
        setCustomDialog();
    }
    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_tip_clear_history, null);
        positiveButton = (TextView) mView.findViewById(R.id.dialog_cancle_bespoke_confirm);
        negativeButton = (TextView) mView.findViewById(R.id.dialog_cancle_bespoke_cancel);
        tv_content = (TextView) mView.findViewById(R.id.dialog_comment_content);
        tv_content.setText(content);
        super.setContentView(mView);
        mView.setBackgroundResource(R.drawable.common_shape_dialog);
    }
    
    
    public void setInVisible(){
    	positiveButton.setVisibility(View.GONE);
    	
    	
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