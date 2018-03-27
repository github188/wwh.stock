package com.bm.wanma.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class MyDetailGridView extends GridView {

	public MyDetailGridView(Context context) {
		super(context);
	}

	public MyDetailGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyDetailGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			return false;// 禁止Gridview进行滑动
		}
		return super.dispatchTouchEvent(ev);

	}

	 @Override
	    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

	        int expandSpec = MeasureSpec.makeMeasureSpec(
	                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
	        super.onMeasure(widthMeasureSpec, expandSpec);
	    }
	
}
