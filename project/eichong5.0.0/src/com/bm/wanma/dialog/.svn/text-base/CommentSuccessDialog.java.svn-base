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
 * @Function: 评价成功  -- 确定退出
 * @author cm
 */
public class CommentSuccessDialog extends Dialog {
    private TextView positiveButton;
    private TextView tv_content,tv_title;
 
    public CommentSuccessDialog(Context context) {
        super(context,R.style.ChargePayDialog);
        setCustomDialog();
    }
    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment_success, null);
        positiveButton = (TextView) mView.findViewById(R.id.dialog_comment_success_confirm);
        tv_content = (TextView) mView.findViewById(R.id.dialog_comment_content);
        tv_title = (TextView) mView.findViewById(R.id.dialog_comment_title);
        super.setContentView(mView);
        mView.setBackgroundResource(R.drawable.common_shape_dialog);
    }
     
    public void setValueToText(String title,String content){
    	tv_content.setText(content);
    	tv_title.setText(title);
    }
    public void setTextVisible(){
    	tv_content.setVisibility(View.VISIBLE);
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