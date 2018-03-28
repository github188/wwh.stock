package com.ijustyce.fastandroiddev3.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.ijustyce.fastandroiddev3.R;
import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev3.databinding.TitleBarView;
import com.ijustyce.fastandroiddev3.event.TitleBarEvent;
import com.ijustyce.fastandroiddev3.viewmodel.CommonTitleBarView;

/**
 * Created by yangchun on 2016/11/12.
 */

public class CommonTitleBar extends RelativeLayout {

    private int leftIconId;
    private String leftText;
    private int leftTxtColor;
    private String titleTxt;
    private String rightText;
    private int rightTxtColor;
    private int rightIconId;

    private int titleColor;
    private int bgColor;

    private boolean showLine;
    private CommonTitleBarView viewModel;
    public TitleBarView titleBarView;
    public CommonTitleBar(Context context) {
        super(context);
    }

    public CommonTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, R.style.commonTitleStyle);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleBar, defStyle, 0);
        leftText = arr.getString(R.styleable.CommonTitleBar_leftTxt);
        leftIconId = arr.getResourceId(R.styleable.CommonTitleBar_leftIcon, -100);
        rightIconId = arr.getResourceId(R.styleable.CommonTitleBar_rightIcon, -100);
        bgColor = arr.getResourceId(R.styleable.CommonTitleBar_titleBg, -100);
        titleTxt = arr.getString(R.styleable.CommonTitleBar_titleTxt);
        rightText = arr.getString(R.styleable.CommonTitleBar_rightTxt);
        showLine = arr.getBoolean(R.styleable.CommonTitleBar_showLine, true);

        titleColor = arr.getColor(R.styleable.CommonTitleBar_titleColor, getResources().getColor(R.color.black));
        rightTxtColor = arr.getColor(R.styleable.CommonTitleBar_rightTxtColor, getResources().getColor(R.color.black));
        leftTxtColor = arr.getColor(R.styleable.CommonTitleBar_leftTextColor, getResources().getColor(R.color.black));
        arr.recycle();

        titleBarView = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.fastandroiddev3_common_header, this, true);
    }

    public void setClickEvent(TitleBarEvent event) {
        if (event != null && titleBarView != null) {
            titleBarView.setClickEvent(event);
        }else{
            ILog.e("===CommonTitleBar===", "event or titlebarView is null");
        }
    }

    public CommonTitleBarView getViewModel() {
        return viewModel;
    }

    private void initView(){
        viewModel = new CommonTitleBarView();
        viewModel.showLine.set(showLine ? VISIBLE : GONE);
        if (leftIconId != -100) {
            viewModel.leftIcon.set(leftIconId);
        }
        viewModel.rightIcon.set(rightIconId);
        if (bgColor != -100) {      //  防止删除默认值
            viewModel.titleBg.set(bgColor);
        }
        viewModel.leftText.set(leftText);
        viewModel.leftTextColor.set(leftTxtColor);
        viewModel.titleText.set(titleTxt);
        viewModel.titleTextColor.set(titleColor);
        viewModel.rightTextColor.set(rightTxtColor);
        viewModel.rightText.set(rightText);

        titleBarView.setViewModel(viewModel);
        super.onFinishInflate();
    }

    protected void onFinishInflate() {
        if (isInEditMode() || titleBarView == null) {
            return;
        }
        initView();
    }
}