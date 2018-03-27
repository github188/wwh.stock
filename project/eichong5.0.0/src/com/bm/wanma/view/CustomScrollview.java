package com.bm.wanma.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class CustomScrollview extends ScrollView {
	private OnScrollToBottomListener onScrollToBottom;  
	public CustomScrollview(Context context) {
		super(context);
	}

	public CustomScrollview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomScrollview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
			boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);  
        if(scrollY != 0 && null != onScrollToBottom){  
            onScrollToBottom.onScrollBottomListener(clampedY);  
        }  
	}
	public void setOnScrollToBottomLintener(OnScrollToBottomListener listener){  
        onScrollToBottom = listener;  
    }  
  
    public interface OnScrollToBottomListener{  
        public void onScrollBottomListener(boolean isBottom);  
    }  
}
