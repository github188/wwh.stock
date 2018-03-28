package com.ijustyce.fastandroiddev3.base;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

public abstract class IBaseService extends IntentService {

    public Context mContext;
	@Override
	protected void onHandleIntent(Intent intent) {

	}

	public IBaseService(String name) {
		super(name);
	}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }
	
	@Override
	public void onCreate() {
		super.onCreate();

        mContext = this;
	}
}
