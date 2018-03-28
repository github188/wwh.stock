package com.ijustyce.fastandroiddev3.ui;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.ijustyce.fastandroiddev3.R;
import com.ijustyce.fastandroiddev3.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev3.databinding.ConfirmDialogView;

/**
 * Created by yc on 17-3-19.
 */

public class ConfirmDialog extends Dialog {

    private ConfirmDialogView dialogView;
    private ConfirmDialogClickListener clickListener;
    public boolean cancelAfterClickConfirm = true;
    public boolean cancelAfterClickCancel = true;
    public boolean autoCancel = true;

    public static class ConfirmDialogClickListener {
        public void onCancel(){}
        public void onConfirm(){}
    }

    public ConfirmDialog(Context context, ConfirmDialogClickListener clickListener) {
        super(context, R.style.Dialog);
        this.clickListener = clickListener;
        dialogView = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.fastandroiddev3_view_confirm_dialog, null, false);
        setContentView(dialogView.getRoot());
        addClickListener();
    }

    private void addClickListener() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogView == null || v == null || clickListener == null) return;
                if (v == dialogView.tvCancel) {
                    clickListener.onCancel();
                    afterClick(false);
                }else if (v == dialogView.tvConfirm) {
                    clickListener.onConfirm();
                    afterClick(true);
                }
                if (autoCancel) cancel();
            }
        };
        dialogView.tvCancel.setOnClickListener(onClickListener);
        dialogView.tvConfirm.setOnClickListener(onClickListener);
    }

    private void afterClick(boolean isClickConfirm) {
        if (cancelAfterClickCancel && !isClickConfirm) {
            cancel();
        }else if (cancelAfterClickConfirm && isClickConfirm) {
            cancel();
        }
    }

    public ConfirmDialog setCancelText(String value){
        if (dialogView == null || dialogView.tvCancel == null) return null;
        if (StringUtils.isEmpty(value)) dialogView.tvCancel.setText("");
        else dialogView.tvCancel.setText(value);
        return this;
    }

    public ConfirmDialog setConfirmBtText(String value){
        if (dialogView == null || dialogView.tvConfirm == null) return null;
        if (StringUtils.isEmpty(value)) dialogView.tvConfirm.setText("");
        else dialogView.tvConfirm.setText(value);
        return this;
    }

    public ConfirmDialog setMsg(String value){
        if (dialogView == null || dialogView.tvMessage == null) return null;
        if (StringUtils.isEmpty(value)) dialogView.tvMessage.setText("");
        else dialogView.tvMessage.setText(value);
        return this;
    }
}