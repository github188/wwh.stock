package com.ijustyce.fastandroiddev3.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ijustyce.fastandroiddev3.irecyclerview.BindingInfo;

import java.util.ArrayList;

/**
 * Created by yangchun on 2017/2/17.
 */

public class BasePagerAdapter<T> extends PagerAdapter {

    private ViewDataBinding view;
    private ArrayList<T> data;
    private LayoutInflater layoutInflater;
    private BindingInfo bindingInfo;

    public BasePagerAdapter(Context context, ArrayList<T> data, BindingInfo bindingInfo) {
        this.data = data;
        mChildCount = data == null ? 0 : data.size();
        this.bindingInfo = bindingInfo;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void destroyItem(ViewGroup paramView, int paramInt, Object paramObject) {
        if (paramObject instanceof View && paramView != null) {
            paramView.removeView((View) paramObject);
        }
    }

    public Object instantiateItem(ViewGroup paramView, final int paramInt) {
        ViewDataBinding localView = buildView(paramView);
        T bean = getObject(paramInt);
        for (int i = 0; i < bindingInfo.size; i++) {
            Object value = bindingInfo.info.valueAt(i);
            localView.setVariable(bindingInfo.info.keyAt(i), value == null ? bean : value);
        }
        localView.executePendingBindings();
        paramView.addView(localView.getRoot());
        return localView.getRoot();
    }

    private ViewDataBinding buildView(ViewGroup viewGroup) {
        return DataBindingUtil.inflate(layoutInflater, bindingInfo.layoutId, viewGroup, false);
    }

    public final T getObject(int position) {
        if (data == null || position < 0 || position >= data.size()) {
            return null;
        }
        return data.get(position);
    }

    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = data == null ? 0 : data.size();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object)   {
        if ( mChildCount > 0) {
            mChildCount--;  //  这里不能直接 = 0 否则第二个依旧不会变
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
