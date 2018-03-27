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
 * @Function:  充值成功 对话框--确认退出
 * @author cm
 */
public class RechargeSuccesDialog extends Dialog {
    private TextView positiveButton,tv_title;
    private String price;
 
    public RechargeSuccesDialog(Context context,String str) {
        super(context,R.style.ChargePayDialog);
        price = str;
        setCustomDialog();
    }
    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_recharge_succes, null);
        positiveButton = (TextView) mView.findViewById(R.id.dialog_charge_sucess_confirm);
        tv_title = (TextView) mView.findViewById(R.id.dialog_cancel_bespoke_price);
        tv_title.setText(price);
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