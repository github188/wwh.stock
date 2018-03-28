package com.ijustyce.fastandroiddev3.baseLib.utils;

import android.os.AsyncTask;

/**
 * Created by yangchun on 2017/1/3.
 */

public class TaskUtils<Result> extends AsyncTask<Integer, Integer, Result> {

    @Override
    protected Result doInBackground(Integer... params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        //  do what you need, in the mainThread
    }
    @Override
    protected void onPostExecute(Result result) {
        //  do what you need, in the mainThread
    }
    @Override
    protected void onProgressUpdate(Integer... progress) {
        //  do what you need, in the mainThread
    }
    @Override
    protected void onCancelled(Result result) {
        //  do what you need, in the mainThread
    }
    @Override
    protected void onCancelled() {
        //  do what you need, in the mainThread
    }
}
