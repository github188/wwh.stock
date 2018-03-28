package com.ijustyce.fastandroiddev3.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.v4.view.ViewPager;

import com.ijustyce.fastandroiddev3.irecyclerview.BindingInfo;

import java.util.ArrayList;

/**
 * Created by yangchun on 2017/2/17.
 */

public abstract class BasePageActivity<Bind extends ViewDataBinding, Bean > extends BaseActivity<Bind> {

    public ArrayList<Bean> data;
    public BasePagerAdapter<Bean> adapter;

    public abstract ViewPager getViewPager();
    public abstract BindingInfo getBindingInfo();

    @CallSuper
    @Override
    public void afterCreate() {
        data = new ArrayList<>();
        ViewPager pager = getViewPager();
        pager.setOffscreenPageLimit(3);
        adapter = new BasePagerAdapter<>(context, data, getBindingInfo());
        pager.setAdapter(adapter);
    }

    public final void dataChanged() {
        if (data != null && adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
