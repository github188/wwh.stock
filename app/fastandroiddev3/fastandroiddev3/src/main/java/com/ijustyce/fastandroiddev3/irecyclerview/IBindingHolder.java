package com.ijustyce.fastandroiddev3.irecyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yangchun on 16/7/19.
 */

public class IBindingHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;
    public long itemPosition;
    public IBindingHolder(View itemView) {
        super(itemView);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }


}
