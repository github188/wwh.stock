package com.ijustyce.fastandroiddev3.viewmodel;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ijustyce.fastandroiddev3.base.BaseViewPager;
import com.ijustyce.fastandroiddev3.base.FragmentAdapter;
import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangchun on 2017/3/28.
 */

public class TabLayoutView {

    public TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<CharSequence> mTitleList;
    private List<Fragment> mFragmentList;
    private FragmentAdapter mFragmentAdapter;
    private TabLayoutCallBack callBack;

    public interface TabLayoutCallBack {
        TabLayout getTabLayout();

        ViewPager getViewPager();

        void onPageSelect(int position);

        int getResColor(int color);
    }

    public String getTitle(int position) {
        int size = mTitleList == null || mTitleList.isEmpty() ? 0 : mTitleList.size();
        if (size <= position || position < 0) return "";
        return mTitleList.get(position).toString();
    }

    public Fragment getFragment(int position) {
        int size = mFragmentList == null || mFragmentList.isEmpty() ? 0 : mFragmentList.size();
        if (size <= position || position < 0) return null;
        return mFragmentList.get(position);
    }

    public int getCurrentItem (){
        return mViewPager == null ? -1 : mViewPager.getCurrentItem();
    }

    public TabLayoutView(FragmentManager fragmentManager, TabLayoutCallBack layoutCallBack) {
        this.callBack = layoutCallBack;
        initData();
        setAdapter(fragmentManager);
    }

    public final void addTitleAndFragment(ArrayList<CharSequence> title, ArrayList<Fragment> fragment) {
        if (title != null && fragment != null && title.size() == fragment.size()) {
            mTitleList.addAll(title);
            mFragmentList.addAll(fragment);
            dataChanged();
        }
    }

    private void dataChanged() {
        if (mFragmentList != null && mTitleList != null && mFragmentAdapter != null) {
            mFragmentAdapter.notifyDataSetChanged();
        }
    }

    public void onResume() {
        int index = mViewPager.getCurrentItem();
        if (mFragmentList == null || mFragmentList.isEmpty()) return;
        if (index < 0 || index >= mFragmentList.size()) return;
        Fragment fragment = mFragmentList.get(index);
        if (fragment != null) {
            fragment.onResume();
        }
    }

    private void initData() {
        mTabLayout = callBack.getTabLayout();
        mViewPager = callBack.getViewPager();
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
    }

    public final void setTabHeight(int height) {
        if (mTabLayout != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            mTabLayout.setLayoutParams(params);
        }
    }

    /**
     * 用adapter 关联起来
     */
    private void setAdapter(FragmentManager fragmentManager) {
        mFragmentAdapter = new FragmentAdapter(fragmentManager, mFragmentList, mTitleList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size() > 3 ? 3 : mFragmentList.size());
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                callBack.onPageSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setScrollMode() {

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    public void setTabBackground(int color) {

        mTabLayout.setBackgroundColor(callBack.getResColor(color));
    }

    public void setTabIndicatorHeight(int height) {

        mTabLayout.setSelectedTabIndicatorHeight(height);
    }

    /**
     * 设置tab的可见性
     *
     * @param value 只能是 View.GONE、View.VISIBLE、View.INVISIBLE
     */
    public void setTabVisibility(int value) {

        if (value == View.GONE || value == View.VISIBLE || value == View.INVISIBLE) {
            mTabLayout.setVisibility(value);
        } else {
            ILog.e("===BaseTopTabFragment===", "setTabVisibility, value only can be " +
                    "View.GONE, View.VISIBLE or View.INVISIBLE");
        }
    }

    public void setCurrentFragment(int id) {

        if (id > -1 && id < mFragmentList.size() && mViewPager != null) {
            mViewPager.setCurrentItem(id, true);
        } else {
            ILog.e("===BaseTopTabFragment===", "setCurrentFragment, id overflow or mViewPager is null");
        }
    }

    public void setCustomView(View view, int position) {
        if (mTitleList == null) return;
        TabLayout.Tab tab = position < 0 || position > mTitleList.size() ? null : mTabLayout.getTabAt(position);
        if (tab == null) return;
        tab.setCustomView(view);
    }

    /**
     * 是否禁止左右滑动
     *
     * @param canScroll TRUE的时候可以左右滑动，false的时候不能左右滑动，默认是true
     */
    public void setCanScroll(boolean canScroll) {
        if (mViewPager instanceof BaseViewPager) {
            ((BaseViewPager) mViewPager).setCanScroll(canScroll);
        }
    }

    public void setTabIndicatorColor(int color) {

        mTabLayout.setSelectedTabIndicatorColor(callBack.getResColor(color));
    }

    public void setTabTextColor(int normalColor, int selectedColor) {

        mTabLayout.setTabTextColors(callBack.getResColor(normalColor), callBack.getResColor(selectedColor));
    }

    public void onDestroy() {
        callBack = null;
        mFragmentAdapter = null;
        mTitleList = null;
        mFragmentList = null;
        mTabLayout = null;
        mViewPager = null;
    }
}
