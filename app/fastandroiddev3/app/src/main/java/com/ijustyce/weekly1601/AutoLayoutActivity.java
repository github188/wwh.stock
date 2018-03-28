package com.ijustyce.weekly1601;

import com.ijustyce.fastandroiddev3.base.BaseActivity;
import com.ijustyce.weekly1601.databinding.AutoLayoutView;
import com.ijustyce.weekly1601.event.AutoLayoutEvent;

/**
 * Created by yangchun on 2017/3/10.
 */

public class AutoLayoutActivity extends BaseActivity<AutoLayoutView> {

    @Override
    public void afterCreate() {
        contentView.setEvent(new AutoLayoutEvent());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_auto_layout;
    }
}
