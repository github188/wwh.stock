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
 * @Function: 消费记录筛选时间错误警告框
 * @author cm
 */
public class WalletWarningDialog extends Dialog {
    private TextView positiveButton;
    private TextView tv_title;
    private String title;
 
    public WalletWarningDialog(Context context,String str) {
        super(context,R.style.ChargePayDialog);
        this.title = str;
        setCustomDialog();
    }
    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_warning_wallet, null);
        positiveButton = (TextView) mView.findViewById(R.id.wallet_warning_negative);
        tv_title = (TextView) mView.findViewById(R.id.wallet_warning_title);
        tv_title.setText(title);
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
   
}