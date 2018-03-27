package com.bm.wanma.dialog;


import com.bm.wanma.R;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
/**
 * @Function: 充电结束提示去订单列表查看详情
 * @author cm
 */
public class ChargeFinishTipDialog extends Dialog {
     

    public ChargeFinishTipDialog(Context context) {
    	this(context,R.layout.dialog_charge_finish_tip,R.style.customCommonDialog);
    }
    public ChargeFinishTipDialog(Context context,int layout, int style) {  
        super(context, style);
        //set content  
        setContentView(layout);  
        //set window params  
        Window window = getWindow();  
        //window.setBackgroundDrawable(new BitmapDrawable());
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();  
        //set width,height by density and gravity  
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wmManager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);  
        wmManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        params.width = (int)(width * 0.8);  
        params.height = (int) (height*0.25);
        params.gravity = Gravity.CENTER;  
        window.setAttributes(params);  
       
    }  

    public void setOnPositiveListener(View.OnClickListener l){
    	 findViewById(R.id.custom_positiveButton).setOnClickListener(l);
    }
  
}