package com.ijustyce.fastandroiddev3.baseLib.utils;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

/**
 * Created by yangchun on 16/8/5.
 */

public class ScrollUtils {

    private WeakReference<ScrollListener> listener;
    private GestureDetector mGestureDetector;

    public interface ScrollListener{
        void scrollUp(float value);
        void scrollDown(float value);
    }

    private void onScrollDown(float value){
        if (listener != null && listener.get() != null)
            listener.get().scrollDown(value);
    }

    private void onScrollUp(float value){
        if (listener != null && listener.get() != null)
            listener.get().scrollUp(value);
    }

    public ScrollUtils(Context context, ScrollListener scrollListener){

        GestureDetector.OnGestureListener  gestureListener = new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                onScrollUp(-1);
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (distanceY > distanceX || -distanceY > -distanceX) {
                    if (distanceY > -5 && distanceY < 5) return false;  //  -5 到 5不处理，防止抖动！
                    if (distanceY < 0) onScrollDown(distanceY);
                    else onScrollUp(distanceY);
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            /**
             *
             * @param e1            首次手势点的移动事件
             * @param e2            当前手势点的移动事件
             * @param velocityX     每秒x轴方向的移动速度
             * @param velocityY     每秒y轴方向的移动速度
             * @return
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                if (listener == null || listener.get() == null) return false;
//                if (e1 == null || e2 == null) return false;
//                float distance = e1.getY() - e2.getY();
//                if (distance > FLING_MIN_DISTANCE
//                        && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
//                    listener.get().scrollUp(distance);
//                } else if (e2.getY()-e1.getY() > FLING_MIN_DISTANCE
//                        && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
//                    listener.get().scrollDown(-distance);
//                }
                return false;
            }
        };

        listener = new WeakReference<>(scrollListener);
        mGestureDetector = new GestureDetector(context, gestureListener);
    }

    public void onScroll(MotionEvent event){
        if (mGestureDetector != null) mGestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            onScrollUp(-1);
        }
    }
}
