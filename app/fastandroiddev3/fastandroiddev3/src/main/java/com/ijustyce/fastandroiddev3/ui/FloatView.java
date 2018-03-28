package com.ijustyce.fastandroiddev3.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.ijustyce.fastandroiddev3.baseLib.callback.ActivityLifeCall;
import com.ijustyce.fastandroiddev3.baseLib.callback.CallBackManager;

/**
 * Created by yangchun on 16/8/24.
 */

public class FloatView {

    public class FloatViewLife implements ActivityLifeCall {
        @Override
        public void onResume(Activity activity) {
            show();
        }

        @Override
        public void onPause(Activity activity) {
            hide();
        }

        @Override
        public void onCreate(Activity activity) {

        }

        @Override
        public void onStop(Activity activity) {

        }

        @Override
        public void onDestroy(Activity activity) {

        }

        @Override
        public void dispatchTouchEvent(MotionEvent event, Activity activity) {

        }
    }

    public FloatViewLife viewLife = new FloatViewLife();
    private View view;
    private WindowManager wm;
    private boolean shouldShow;
    private WindowManager.LayoutParams wmParams;

    public FloatView(Activity context, @LayoutRes int layoutId) {
        view = LayoutInflater.from(context).inflate(layoutId, null);
        wmParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return onTouchEvent(motionEvent);
            }
        });
    }

    //  是否应该显示悬浮窗!
    public void setShouldShow(boolean shouldShow) {
        this.shouldShow = shouldShow;
        if (shouldShow) show();
        else hide();
    }

    public void setLocation(int x, int y) {
        wmParams.x = x;
        wmParams.y = y;
    }

    private boolean isViewAdded = false;

    public View getView() {
        return view;
    }

    public void show() {
        if (shouldShow && !isViewAdded && wm != null && view != null) {
            wm.addView(view, wmParams);
            isViewAdded = true;
            CallBackManager.addActivityLifeCall(viewLife);
        }
    }

    public void hide() {
        if (isViewAdded && view != null && wm != null) {
            try {
                wm.removeView(view);
                isViewAdded = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void destroy() {
        hide();
        view = null;
        wm = null;
        onClickListener = null;
        shouldShow = false;
        viewLife = null;
    }

    private void updateView() {
        if (view != null && wm != null && wmParams != null) {
            wm.updateViewLayout(view, wmParams);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private float lastX, lastY, nowX, nowY, tranX, tranY;
    private View.OnClickListener onClickListener;
    private boolean hasMove = false;

    private boolean onTouchEvent(MotionEvent event) {
        boolean ret = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获取按下时的X，Y坐标
                lastX = event.getRawX();
                lastY = event.getRawY();
                hasMove = false;
                ret = true;
                break;
            case MotionEvent.ACTION_MOVE:
                // 获取移动时的X，Y坐标
                nowX = event.getRawX();
                nowY = event.getRawY();
                if (lastY == nowY && lastX == nowX){
                    hasMove = false;    //      设置没有移动过
                    break;
                }
                // 计算XY坐标偏移量
                tranX = nowX - lastX;
                tranY = nowY - lastY;
                // 移动悬浮窗
                wmParams.x += tranX;
                wmParams.y += tranY;
                //更新悬浮窗位置
                updateView();
                //记录当前坐标作为下一次计算的上一次移动的位置坐标
                lastX = nowX;
                lastY = nowY;
                hasMove = true;     //      设置已经移动过了
                break;
            case MotionEvent.ACTION_UP:
                if (!hasMove && onClickListener != null) {
                    onClickListener.onClick(view);
                }
                break;
        }
        return ret;
    }
}
