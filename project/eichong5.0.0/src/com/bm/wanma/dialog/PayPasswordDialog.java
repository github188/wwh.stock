package com.bm.wanma.dialog;

import com.bm.wanma.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * @Function: 输入支付密码对话框  --确认退出或取消
 * @author cm
 */
public class PayPasswordDialog extends Dialog {
    private TextView positiveButton, negativeButton,tv_paymoney,tv_balance;
    private EditText et_password;
    private String paymoney,balance;
 
    public PayPasswordDialog(Context context,String str1,String str2) {
        super(context,R.style.ChargePayDialog);
        this.paymoney = str1;
        this.balance = str2;
        setCustomDialog();
    }
    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pay_password, null);
        positiveButton = (TextView) mView.findViewById(R.id.dialog_pay_password_confirm);
        negativeButton = (TextView) mView.findViewById(R.id.dialog_pay_password_cancle);
        et_password =  (EditText) mView.findViewById(R.id.dialog_pay_password_money_et);
        tv_paymoney = (TextView) mView.findViewById(R.id.dialog_pay_password_payment);
        tv_balance = (TextView) mView.findViewById(R.id.dialog_pay_password_current);
        tv_paymoney.setText(paymoney);
        tv_balance.setText(balance);
        super.setContentView(mView);
        mView.setBackgroundResource(R.drawable.common_shape_dialog);
    }
    public View getEditPwd(){
        return et_password;
    }
    public View getPositiveButton(){
        return positiveButton;
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
    
    @SuppressLint("NewApi")
	public void setOnPositiveBack(Drawable background){ 
        positiveButton.setBackground(background);
       // positiveButton.setPadding(20, 0, 20, 0);
       // positiveButton.set
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