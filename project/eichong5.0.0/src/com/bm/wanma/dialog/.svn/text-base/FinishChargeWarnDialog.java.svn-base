package com.bm.wanma.dialog;


import com.bm.wanma.R;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
/**
 * @Function: eichong4.0版本通用提示对话框
 * @author cm
 */
public class FinishChargeWarnDialog extends Dialog {
	 private static int default_width = 280; //默认宽度  
     private static int default_height = 160;//默认高度 
 
 
    public FinishChargeWarnDialog(Context context,int layout, int style) {
    	this(context,default_width,default_height,layout,style);
    }
    public FinishChargeWarnDialog(Context context,int layout) {
    	this(context,default_width,default_height,layout,R.style.customCommonDialog);
    }
    public FinishChargeWarnDialog(Context context) {
    	this(context,default_width,default_height,R.layout.dialog_finish_charge_warn,R.style.customCommonDialog);
    }
    public FinishChargeWarnDialog(Context context, int width, int height, int layout, int style) {  
        super(context, style);
        
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wmManager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);  
        wmManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        //set content  
        setContentView(layout);  
        //set window params  
        Window window = getWindow();  
        //window.setBackgroundDrawable(new BitmapDrawable());
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();  
        //set width,height by density and gravity  
       /* float density = getDensity(context);  
        params.width = (int) (width*density);  
        params.height = (int) (height*density);  */
        params.width = (int)(width * 0.8);  
        params.height = (int) (height*0.25);
        params.gravity = Gravity.CENTER;  
        window.setAttributes(params);  
    }  
    private float getDensity(Context context) {  
        Resources resources = context.getResources();  
        DisplayMetrics dm = resources.getDisplayMetrics();  
       return dm.density;  
    }  

	public void setOnPositiveListener(View.OnClickListener listener) {
		 findViewById(R.id.dialog_finish_commit).setOnClickListener(listener);
	}
	public void setOnNegativeListener(View.OnClickListener listener) {
		 findViewById(R.id.dialog_finish_cancel).setOnClickListener(listener);
	}
}