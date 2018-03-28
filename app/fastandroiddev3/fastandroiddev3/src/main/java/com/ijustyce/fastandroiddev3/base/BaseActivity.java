package com.ijustyce.fastandroiddev3.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.ijustyce.fastandroiddev3.R;
import com.ijustyce.fastandroiddev3.baseLib.callback.CallBackManager;
import com.ijustyce.fastandroiddev3.databinding.ListActivityView;
import com.ijustyce.fastandroiddev3.http.HttpManager;
import com.ijustyce.fastandroiddev3.manager.AppManager;
import com.ijustyce.fastandroiddev3.ui.CommonTitleBar;
import com.ijustyce.fastandroiddev3.ui.ProgressDialog;
import com.ijustyce.fastandroiddev3.viewmodel.CommonTitleBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangchun on 2016/11/12.
 */

public abstract class BaseActivity<Bind extends ViewDataBinding> extends AppCompatActivity {

    public Bind contentView;
    public Context context;
    public Activity activity;
    private String key;
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (cancelCreate()) {
            finish();
            return;
        }
        context = getBaseContext();
        activity = this;
        contentView = DataBindingUtil.setContentView(activity, getLayoutId());
        initViewAndData();
        afterCreate();
        CallBackManager.onCreate(activity);
        key = getClass().getName() + System.currentTimeMillis();
        AppManager.add(key, activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CallBackManager.onResume(activity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CallBackManager.onPause(activity);
        dismissProcess();
    }

    @Override
    protected void onStop() {
        super.onStop();
        CallBackManager.onStop(activity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProcess();
        dialog = null;
        contentView = null;
        CallBackManager.onDestroy(activity);
        AppManager.delete(key);
        context = null;
        activity = null;
    }

    public abstract int getLayoutId();
    public abstract void afterCreate();
    void initViewAndData(){};

    public boolean cancelCreate() {
        return false;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_out, R.anim.push_right_out);
    }

    private ProgressDialog dialog;
    public Dialog getDialog() {
        if (dialog == null) dialog = new ProgressDialog(activity, getResString(R.string.net_loading_hint));
        return dialog;
    }

    public void showProcess(){
        Dialog dialog = getDialog();
        if (dialog != null) dialog.show();
    }

    public <T> void getNetData(final Callback<T> callback, Call<T> call){
        showProcess();
        boolean result = HttpManager.send(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (activity == null || contentView == null) {
                    return;
                }
                dismissProcess();
                if (callback != null) callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (activity == null || contentView == null) {
                    return;
                }
                dismissProcess();
                if (callback != null) callback.onFailure(call, t);
            }
        }, call);
        if (!result) dismissProcess();
    }

    public void dismissProcess(){
        if (dialog != null && dialog.isShowing()) dialog.cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backPress();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void backClick(View view) {
        backPress();
    }

    public void backPress() {
        finish();
    }

    public final String getResString (@StringRes int stringId) {
        try{
            return getResources().getString(stringId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public final int getResColor(int color) {
        return getResources().getColor(color);
    }

    public boolean hasNull(){
        return contentView == null;
    }

    public final void setTitleLabel (@StringRes int stringId) {
        setTitleLabel(getResString(stringId));
    }

    public final void setTitleLabel (String title) {
        CommonTitleBarView titleBarView = getTitleBarView();
        if (titleBarView != null && titleBarView.titleText != null) {
            titleBarView.titleText.set(title);
        }
    }

    public CommonTitleBarView getTitleBarView() {
        CommonTitleBar titleBar = getTitleBar();
        return titleBar == null ? null : titleBar.getViewModel();
    }

    public CommonTitleBar getTitleBar() {
        if (contentView instanceof ListActivityView) {
            return ((ListActivityView) contentView).titleBar;
        }
        return null;
    }
}