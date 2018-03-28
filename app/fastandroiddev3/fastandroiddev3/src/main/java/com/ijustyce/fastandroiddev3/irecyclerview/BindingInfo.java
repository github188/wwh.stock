package com.ijustyce.fastandroiddev3.irecyclerview;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;

/**
 * Created by yangchun on 16/7/19.
 */

public class BindingInfo {

    public SparseArray<Object> info;
    public int size = 1;
    public int layoutId;
    public ItemCreate callBack;
    public ViewHolderCallBack viewHolderCallBack;

    @Deprecated
    public BindingInfo(@LayoutRes int layoutId, int key){
        this.layoutId = layoutId;
        info = new SparseArray<>();
        info.put(key, null);
    }

    public interface ItemCreate{
        public <T> void onCreated(T item, int position, ViewDataBinding binding);
    }

    public interface ViewHolderCallBack {
        public <T> int getViewType (T item);
        public int getLayoutId (int type);
    }

    public BindingInfo setAfterCreateCallBack(ItemCreate afterCreateCallBack) {
        this.callBack = afterCreateCallBack;
        return this;
    }

    public BindingInfo setViewHolderCallBack(ViewHolderCallBack callBack) {
        this.viewHolderCallBack = callBack;
        return this;
    }

    public static BindingInfo createByLayoutIdAndBindName(@LayoutRes int layoutId, int key){
        return new BindingInfo(layoutId, key);
    }

    public BindingInfo add(int key, Object value){
        info.put(key, value);
        size = info.size();
        return this;
    }
}
