package com.bm.wanma.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 引导页适配器
 * item是view的adapter
 * cm
 */
public class CommonViewPagerAdapter extends PagerAdapter {
    private List<View> mViewList;

    public CommonViewPagerAdapter(List<View> viewList){
        mViewList = viewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView(mViewList.get(position));
    }
}
