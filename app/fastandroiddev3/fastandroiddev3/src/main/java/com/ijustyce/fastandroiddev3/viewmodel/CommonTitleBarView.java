package com.ijustyce.fastandroiddev3.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.ijustyce.fastandroiddev3.R;
import com.ijustyce.fastandroiddev3.base.BaseViewModel;

/**
 * Created by yangchun on 2016/11/12.
 */

public class CommonTitleBarView extends BaseViewModel {

    public ObservableField<String> leftText = new ObservableField<>();
    public ObservableInt leftTextColor = new ObservableInt();

    public ObservableField<String> titleText = new ObservableField<>();
    public ObservableInt titleTextColor = new ObservableInt();

    public ObservableField<String> rightText = new ObservableField<>();
    public ObservableInt rightTextColor = new ObservableInt();

    public ObservableInt leftIcon = new ObservableInt();
    public ObservableInt rightIcon = new ObservableInt();

    public ObservableInt showLine = new ObservableInt();
    public ObservableInt titleBg = new ObservableInt();

    public CommonTitleBarView(){
        leftTextColor.set(R.color.color_000000);
        rightTextColor.set(R.color.color_000000);
        titleTextColor.set(R.color.color_000000);
        leftIcon.set(R.mipmap.icon_back);
        rightIcon.set(-100);
        showLine.set(View.VISIBLE);
        titleBg.set(R.color.color_FFFFFF);
    }
}