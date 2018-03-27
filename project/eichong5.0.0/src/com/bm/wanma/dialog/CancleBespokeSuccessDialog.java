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
 * @Function: 取消预约成功 对话框--确认
 * @author cm
 */
public class CancleBespokeSuccessDialog extends Dialog {
    private TextView positiveButton, tv_consume,tv_balance;
    private String consume,balance;
    public CancleBespokeSuccessDialog(Context context,String  bala,String cons) {
        super(context,R.style.ChargePayDialog);
        balance =  bala;
        consume = cons;
        setCustomDialog();
    }
    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_cancel_bespoke_succes, null);
        positiveButton = (TextView) mView.findViewById(R.id.dialog_cancel_sucess_confirm);
        tv_consume = (TextView) mView.findViewById(R.id.dialog_cancel_sucess_consume_tv);
        tv_balance = (TextView) mView.findViewById(R.id.dialog_cancel_sucess_balance_tv);
        tv_consume.setText(consume + "元");
        tv_balance.setText(balance + "元");
        
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