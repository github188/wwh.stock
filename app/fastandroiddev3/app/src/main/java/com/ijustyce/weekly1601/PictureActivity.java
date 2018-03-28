package com.ijustyce.weekly1601;

import android.support.v4.view.ViewPager;

import com.ijustyce.fastandroiddev3.base.BasePageActivity;
import com.ijustyce.fastandroiddev3.irecyclerview.BindingInfo;
import com.ijustyce.weekly1601.bean.PictureBean;
import com.ijustyce.weekly1601.databinding.PictureView;

/**
 * Created by yangchun on 2017/2/17.
 */

public class PictureActivity extends BasePageActivity <PictureView, PictureBean>{

    @Override
    public BindingInfo getBindingInfo() {
        return BindingInfo.createByLayoutIdAndBindName(R.layout.view_picture, BR.item);
    }

    @Override
    public ViewPager getViewPager() {
        return contentView.viewPager;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_picture;
    }

    @Override
    public void afterCreate() {
        super.afterCreate();

        PictureBean first = new PictureBean();
        first.url = "http://pic11.nipic.com/20101214/213291_155243023914_2.jpg";

        PictureBean first1 = new PictureBean();
        first1.url = "http://pic17.nipic.com/20111122/6759425_152002413138_2.jpg";

        PictureBean first2 = new PictureBean();
        first2.url = "http://img0.imgtn.bdimg.com/it/u=2320677199,2423076609&fm=23&gp=0.jpg";

        PictureBean first3 = new PictureBean();
        first3.url = "http://img0.imgtn.bdimg.com/it/u=111798862,3781959513&fm=23&gp=0.jpg";

        PictureBean first4 = new PictureBean();
        first4.url = first.url;

        PictureBean first5 = new PictureBean();
        first5.url = first1.url;

        PictureBean first6 = new PictureBean();
        first6.url = first2.url;

        PictureBean first7 = new PictureBean();
        first7.url = first3.url;

        PictureBean first8 = new PictureBean();
        first8.url = first.url;

        PictureBean first9 = new PictureBean();
        first9.url = first1.url;

        PictureBean first10 = new PictureBean();
        first10.url = first2.url;

        data.add(first);
        data.add(first1);
        data.add(first2);
        data.add(first3);
        data.add(first4);
        data.add(first5);
        data.add(first6);
        data.add(first7);
        data.add(first8);
        data.add(first9);
        data.add(first10);
        dataChanged();
    }
}
