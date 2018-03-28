package com.ijustyce.weekly1601;

import com.ijustyce.fastandroiddev3.base.BaseActivity;
import com.ijustyce.weekly1601.databinding.IWebView;
import com.ijustyce.weekly1601.event.DownloadAndUploadEvent;

/**
 * Created by yangchun on 2016/11/12.
 */

public class WebViewActivity extends BaseActivity<IWebView> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void afterCreate() {
        DownloadAndUploadEvent event = new DownloadAndUploadEvent(this);
        contentView.setClickEvent(event);
    }
}
