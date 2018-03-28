package com.ijustyce.fastandroiddev3.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.ijustyce.fastandroiddev3.R;
import com.ijustyce.fastandroiddev3.databinding.ListActivityView;
import com.ijustyce.fastandroiddev3.http.HttpManager;
import com.ijustyce.fastandroiddev3.irecyclerview.BindingInfo;
import com.ijustyce.fastandroiddev3.irecyclerview.IDataInterface;
import com.ijustyce.fastandroiddev3.irecyclerview.IRecyclerView;
import com.ijustyce.fastandroiddev3.irecyclerview.IResponseData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by yangchun on 2016/11/12.
 */

public abstract class BaseListActivity<Bind extends ViewDataBinding, Bean,
        Model extends IResponseData<Bean>> extends BaseActivity<Bind> {

    public boolean refreshOnResume = false, showAutoRefreshAnim = true, showRefreshAnim = true, autoRefresh = true;
    public IRecyclerView recyclerView;
    public IDataInterface<Bean, Model> iDataInterface;

    @Override
    final void initViewAndData() {
        recyclerView = getRecyclerView();
        if (recyclerView == null) return;

        BindingInfo bindingInfo = getBindingInfo();
        if (bindingInfo == null) return;
        iDataInterface = new IDataInterface<Bean, Model>(context, bindingInfo) {
            @Override
            public boolean loadData(int pageNo, Callback<Model> observer) {
                Call<Model> httpCall = getListCall(pageNo);
                return httpCall == null || HttpManager.send(observer, httpCall);
            }

            @Override
            public void dataLoadFailed(Throwable throwable) {
                if (activity == null || contentView == null) return;
                BaseListActivity.this.dataLoadFailed(throwable);
            }

            @Override
            public boolean onDataGet(Model data) {
                if (activity == null || contentView == null) return false;
                return BaseListActivity.this.onDataGet(data);
            }

            @Override
            public void onDataShow(ArrayList<Bean> data) {
                if (activity == null || contentView == null) return;
                BaseListActivity.this.onDataShow(data);
            }

            @Override
            public void afterBindToView(Model model) {
                BaseListActivity.this.afterBindToView(model);
            }
        };
        recyclerView.setDataInterface(iDataInterface);
    }

    public void afterBindToView(Model model) {

    }

    public void dataLoadFailed(Throwable throwable){

    }

    public boolean onDataGet(Model data){
        return true;
    }

    public volatile View emptyView;
    public View getEmptyView(){
        if (emptyView != null) return emptyView;
        if (contentView instanceof ListActivityView && ((ListActivityView) contentView).recyclerView != null) {
            ViewStub viewStub = ((ListActivityView) contentView).recyclerView.noData.getViewStub();
            synchronized (this) {
                if (emptyView == null) {
                    emptyView = viewStub.inflate();
                }
            }
        }
        return emptyView;
    }

    private View.OnClickListener onClickListener;
    private void showOrHideEmptyView(boolean show){
        if (activity == null || contentView == null) return;
        View view = getEmptyView();
        if (view != null) {
            view.setVisibility(show ? View.VISIBLE : View.GONE);
            if (onClickListener == null) {
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refresh(showRefreshAnim);
                    }
                };
                view.setOnClickListener(onClickListener);
            }
        }
    }

    public boolean showEmptyView() {
        return !recyclerView.hasFooter() && !recyclerView.hasHeader();
    }

    @CallSuper
    public void onDataShow(ArrayList<Bean> data){
        showOrHideEmptyView(data != null && data.isEmpty() && showEmptyView());
    }

    public boolean hasNull(){
        return contentView == null || recyclerView == null;
    }

    public final void refresh() {
        refresh(true);
    }

    public final void refresh(boolean showAnim) {
        if (!hasNull() && iDataInterface != null) {
            recyclerView.onRefresh(showAnim);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (contentView == null || !autoRefresh || iDataInterface == null) return;
        if (refreshOnResume || iDataInterface.hasNoData()) {
            refresh(showAutoRefreshAnim);
        }
    }

    public void setNoDataMsg(@StringRes int id) {
        setNoDataMsg(getResString(id));
    }

    public void setNoDataMsg(String text) {
        View emptyView = getEmptyView();
        View textView = emptyView.findViewById(R.id.noDataMsg);
        if (textView instanceof TextView) {
            ((TextView) textView).setText(text);
        }
    }

    public void setNoDataImg(@DrawableRes int res) {
        View emptyView = getEmptyView();
        View imageView = emptyView.findViewById(R.id.noDataImg);
        if (imageView instanceof ImageView) {
            ((ImageView) imageView).setImageResource(res);
        }
    }

    public IRecyclerView getRecyclerView() {
        if (contentView instanceof ListActivityView) {
            return ((ListActivityView) contentView).recyclerView.list;
        }
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fastandroiddev3_activity_list_common;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recyclerView != null) {
            recyclerView.onDestroy();
            recyclerView = null;
        }
    }

    //  以下方法是必须要子类完成的

    public abstract BindingInfo getBindingInfo();

    public abstract Call<Model> getListCall(int pageNo);
}