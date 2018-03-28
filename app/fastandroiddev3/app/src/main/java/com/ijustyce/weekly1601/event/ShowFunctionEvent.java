package com.ijustyce.weekly1601.event;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.ijustyce.fastandroiddev3.manager.AppManager;
import com.ijustyce.weekly1601.AutoLayoutActivity;
import com.ijustyce.weekly1601.ListActivity;
import com.ijustyce.weekly1601.PictureActivity;
import com.ijustyce.weekly1601.ScrollActivity;
import com.ijustyce.weekly1601.WebViewActivity;

/**
 * Created by yangchun on 2016/11/12.
 */

public class ShowFunctionEvent {

    private Activity activity;
    public ShowFunctionEvent(Activity activity) {
        this.activity = activity;
    }

    public void showWebView(View view) {
        AppManager.newActivity(activity, WebViewActivity.class);
    }

    public void showListActivity (View view) {
        AppManager.newActivity(activity, ListActivity.class);
    }

    private int add(int a, int b) {
        return a + b;
    }

    public void QRCode(View view) {

//        Intent intent = new Intent(activity, Scan.class);
//        activity.startActivityForResult(intent, MainActivity.SCAN);
    }

    public void showScrollView(View view) {
        Intent intent = new Intent(activity, ScrollActivity.class);
        activity.startActivity(intent);
    }

    public void showPicture() {
        Intent intent = new Intent(activity, PictureActivity.class);
        activity.startActivity(intent);
    }

    public void autoLayout() {
        Intent intent = new Intent(activity, AutoLayoutActivity.class);
        activity.startActivity(intent);
    }
}
