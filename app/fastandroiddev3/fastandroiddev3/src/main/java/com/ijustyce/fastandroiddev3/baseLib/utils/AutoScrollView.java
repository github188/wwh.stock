package com.ijustyce.fastandroiddev3.baseLib.utils;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 * Created by yangchun on 2017/2/15.
 */

public class AutoScrollView {

    private Handler handler = new Handler();
    private View firstView, second, scrollView, updateView;
    private int position = 1, max, delay = 2_000, animTime = 300;
    private UpDateViewCall upDateViewCall;

    public AutoScrollView(View view1, View view2) {
        this.firstView = view1;
        this.second = view2;
        scrollView = firstView;
        updateView = second;

        initAnim();
        handler.postDelayed(runnable, delay);
    }

    public void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        updateView = null;
    }

    public AutoScrollView setMaxNum(int max) {
        this.max = max;
        return this;
    }

    public AutoScrollView setUpDateViewCall(UpDateViewCall upDateViewCall) {
        this.upDateViewCall = upDateViewCall;
        return this;
    }

    public void setAnimTime(int time) {
        this.animTime = time;
    }

    private int time = 0;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time++;
            if (scrollView == null) {
                if (handler != null) handler.removeCallbacksAndMessages(null);
                return;
            }
            scrollView.startAnimation(set);
        }
    };

    private AnimationSet set;
    private void initAnim(){
        int begin, end;
        if (time >= 6) time = 0;
        if (time < 2) begin = 0;
        else if (time == 2) begin = 2;
        else if (time == 3) begin = -1;
        else begin = 1;

        if (time < 2) end = -1;
        else if (time == 2) end = 1;
        else if (time == 3) end = -2;
        else end = 0;

        set = new AnimationSet(true);
        final TranslateAnimation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, begin, Animation.RELATIVE_TO_SELF, end);
        translate.setDuration(animTime);
        set.addAnimation(translate);
        set.setFillAfter(true);

        translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                scrollView = switchView(scrollView);
                if (time == 1 || time == 4) {
                    updateView = switchView(updateView);
                    position++;
                    if (position == max) position = 0;
                    if (upDateViewCall != null) {
                        upDateViewCall.onUpdate(updateView, position);
                    }
                    startAnim(false);
                }
                else {
                    startAnim(time == 3 || time == 6);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void startAnim(boolean delay) {
        initAnim();
        handler.postDelayed(runnable, delay ? this.delay : 0);
    }

    public interface UpDateViewCall {
        void onUpdate(View view, int position);
    }

    private View switchView (View target) {
        if (target == firstView) {
            target = second;
        }else {
            target = firstView;
        }
        return target;
    }
}
