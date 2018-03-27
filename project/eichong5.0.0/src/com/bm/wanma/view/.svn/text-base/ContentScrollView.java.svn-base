package com.bm.wanma.view;

import android.content.Context;
import android.util.AttributeSet;

import android.widget.ScrollView;

public class ContentScrollView extends ScrollView {

    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    private OnScrollChangedListener listener;

    public ContentScrollView(Context context) {
        super(context);
    }

    public ContentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContentScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollChangeListener(OnScrollChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        listener.onScrollChanged(l, t, oldl, oldt);
    }

}
