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
 * @Function: 提示插枪  -- 确定退出
 * @author cm
 */
public class TipInsertGunDialog extends Dialog {
    private TextView positiveButton;
    private TextView tv_title;
 
    public TipInsertGunDialog(Context context) {
        super(context,R.style.ChargePayDialog);
        setCustomDialog();
    }
    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_tip_insert_gun, null);
        positiveButton = (TextView) mView.findViewById(R.id.dialog_comment_success_confirm);
        tv_title = (TextView) mView.findViewById(R.id.dialog_tip_insert_title);
        
        super.setContentView(mView);
        mView.setBackgroundResource(R.drawable.common_shape_dialog);
    }
    
    public void setTips(String tip){
    	tv_title.setText(tip);
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

}