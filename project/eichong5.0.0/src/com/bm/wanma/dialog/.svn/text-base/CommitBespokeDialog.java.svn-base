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
 * @Function: 提交预约 对话框--确认退出或取消
 * @author cm
 */
public class CommitBespokeDialog extends Dialog {
    private TextView positiveButton, negativeButton,tv_title;
    private String title;
 
    public CommitBespokeDialog(Context context,String str) {
        super(context,R.style.ChargePayDialog);
        title = str;
        setCustomDialog();
    }
    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_cancel_bespoke, null);
        positiveButton = (TextView) mView.findViewById(R.id.dialog_cancle_bespoke_confirm);
        negativeButton = (TextView) mView.findViewById(R.id.dialog_cancle_bespoke_cancel);
        tv_title = (TextView) mView.findViewById(R.id.dialog_cancel_bespoke_title);
        tv_title.setText(title);
        
        super.setContentView(mView);
        mView.setBackgroundResource(R.drawable.common_shape_dialog);
    }
    public void setButtonTitle(String pos,String neg){
    	 positiveButton.setText(pos);
    	 negativeButton.setText(neg);
     }
    public void setButtonGone(){
   	 positiveButton.setVisibility(View.GONE);
   	 negativeButton.setVisibility(View.GONE);
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