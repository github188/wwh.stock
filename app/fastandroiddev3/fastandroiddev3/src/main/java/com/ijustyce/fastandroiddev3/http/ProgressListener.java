package com.ijustyce.fastandroiddev3.http;

/**
 * Created by yangchun on 2016/11/10.
 */

public interface ProgressListener {
    void onProgress(int percent, boolean isFinish);
}