package com.bm.wanma.dialog;


import com.bm.wanma.R;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
/**
 * @Function: eichong4.0版本通用提示对话框
 * @author cm
 */
public class CustomCommonDialog extends Dialog implements android.view.View.OnClickListener{
	private static int default_width = 280; //默认宽度  
    private static int default_height = 160;//默认高度 
     
     private TextView tv_content;
     private TextView tv_button;
     private CustomDialogCallback mCallBack;
 
    public CustomCommonDialog(Context context,int layout, int style) {
    	this(context,default_width,default_height,layout,style);
    }
    public CustomCommonDialog(Context context,int layout) {
    	this(context,default_width,default_height,layout,R.style.customCommonDialog);
    }
    public CustomCommonDialog(Context context) {
    	this(context,default_width,default_height,R.layout.dialog_custom_common,R.style.customCommonDialog);
    }
    public CustomCommonDialog(Context context, int width, int height, int layout, int style) {  
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
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
       /* float density = getDensity(context);  
        params.width = (int) (width*density);  
        params.height = (int) (height*density);*/  
        params.width = (int)(width * 0.8);  
        params.height = (int) (height*0.25);
        params.gravity = Gravity.CENTER;  
        
       // params.alpha = 0.5f;
        //params.dimAmount = 0.5f;
        
        window.setAttributes(params);  
        findViewById(R.id.custom_positiveButton).setOnClickListener(this);
    }  
    private float getDensity(Context context) {  
        Resources resources = context.getResources();  
        DisplayMetrics dm = resources.getDisplayMetrics();  
       return dm.density;  
    }  
public TextView getContentView(){
	tv_content = (TextView) findViewById(R.id.custom_content);
	return tv_content;
}

	public void setCustomDialogListener(CustomDialogCallback callback){
		this.mCallBack = callback;
	}
	
	public interface CustomDialogCallback{
		void OnDialogClick();
	}

	@Override
	public void onClick(View v) {
		if(mCallBack != null){
			mCallBack.OnDialogClick();
		}
	}
  
}