package com.ijustyce.fastandroiddev3.base;

import android.databinding.ViewDataBinding;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.ijustyce.fastandroiddev3.viewmodel.TabLayoutView;

/**
 * Created by yc on 15-12-25.   顶部是tab的fragment
 */
public abstract class BaseTopTabActivity<Bind extends ViewDataBinding> extends BaseActivity<Bind>
        implements TabLayoutView.TabLayoutCallBack {

    public TabLayoutView tabLayoutView;
    @Override
    public final void initViewAndData(){
        tabLayoutView = new TabLayoutView(getSupportFragmentManager(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tabLayoutView != null) {
            tabLayoutView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tabLayoutView != null) {
            tabLayoutView.onDestroy();
        }
    }

    public abstract TabLayout getTabLayout();
    public abstract ViewPager getViewPager();

    public void onPageSelect(int position) {
    }
}
