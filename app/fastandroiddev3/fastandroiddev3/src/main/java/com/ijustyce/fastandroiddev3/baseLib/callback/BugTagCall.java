package com.ijustyce.fastandroiddev3.baseLib.callback;

import android.app.Activity;
import android.view.MotionEvent;

/**
 * Created by yc on 2016/5/7 0007.  bugtag 的 life call
 */
public class BugTagCall implements ActivityLifeCall {

    @Override
    public void onCreate(Activity activity) {

    }

    @Override
    public void onResume(Activity activity) {
      //  Bugtags.onResume(activity);
    }

    @Override
    public void onPause(Activity activity) {
     //   Bugtags.onPause(activity);
    }

    @Override
    public void onStop(Activity activity) {

    }

    @Override
    public void onDestroy(Activity activity) {

    }

    @Override
    public void dispatchTouchEvent(MotionEvent event, Activity activity) {
      //  Bugtags.onDispatchTouchEvent(activity, event);
    }
}
