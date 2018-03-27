package com.bm.wanma.dialog;

import com.bm.wanma.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * @Function: 更新充电点 对话框--取消退出
 * @author cm
 */
public class RefreshPointDialog extends Dialog {
    private TextView positiveButton;
    private ImageView refresh;
    private Context mContext;
 
    public RefreshPointDialog(Context context) {
        super(context,R.style.ChargePayDialog);
        mContext = context;
        setCustomDialog();
    }
    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_refresh_point, null);
        positiveButton = (TextView) mView.findViewById(R.id.dialog_refresh_cancel);
        refresh = (ImageView) mView.findViewById(R.id.dialog_refresh_icon);
        Animation operatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.dialog_refresh);  
        LinearInterpolator lin = new LinearInterpolator();  
        operatingAnim.setInterpolator(lin);  
        refresh.setAnimation(operatingAnim);
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