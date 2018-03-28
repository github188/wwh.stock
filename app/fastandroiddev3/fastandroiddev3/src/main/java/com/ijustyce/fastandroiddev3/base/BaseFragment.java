package com.ijustyce.fastandroiddev3.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ijustyce.fastandroiddev3.R;
import com.ijustyce.fastandroiddev3.http.HttpManager;
import com.ijustyce.fastandroiddev3.ui.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangchun on 2016/11/12.
 */

public abstract class BaseFragment<Bind extends ViewDataBinding> extends Fragment {

    public Bind contentView;
    public Context context;
    public Activity activity;
    public View mView;
    public Bundle arguments;
    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mView != null) return mView;
        if (cancelCreate()) {
            getActivity().finish();
            return null;
        }
        contentView = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mView = contentView.getRoot();
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        arguments = getArguments();
        return mView;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();
        initViewAndData();
        afterCreate();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissProcess();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProcess();
        dialog = null;
        contentView = null;
        context = null;
        activity = null;
    }

    public abstract int getLayoutId();
    public void afterCreate(){};
    void initViewAndData(){};

    public boolean cancelCreate() {
        return false;
    }

    private ProgressDialog dialog;
    public Dialog getDialog() {
        if (dialog == null && activity != null) {
            dialog = new ProgressDialog(activity, getResString(R.string.net_loading_hint));
        }
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

    public void backClick(View view) {
        if (activity != null) activity.finish();
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
        return this.getResources().getColor(color);
    }

    public boolean hasNull(){
        return contentView == null;
    }
}