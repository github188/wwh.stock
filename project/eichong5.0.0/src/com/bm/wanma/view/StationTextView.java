package com.bm.wanma.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class StationTextView extends TextView {

	public StationTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		 // 创建画笔  
        Paint p = new Paint();  
        p.setColor(Color.RED);// 设置红色
        canvas.drawCircle(60, 20, 10, p);// 小圆 
		
		
		super.onDraw(canvas);
	}
	
	

}
