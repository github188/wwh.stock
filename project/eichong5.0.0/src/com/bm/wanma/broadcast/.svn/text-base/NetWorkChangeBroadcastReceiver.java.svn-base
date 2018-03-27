package com.bm.wanma.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkChangeBroadcastReceiver extends BroadcastReceiver {

	
	private static HomeNetWorkChangeCallback mapcallback;
	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager= 
		        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if(info !=null && mapcallback != null && info.isAvailable()){
			
			mapcallback.homenetworkchange();
		}

}
	
	public static void setNetWorkChangecallback(HomeNetWorkChangeCallback callback){
		mapcallback = callback;
	}
	public interface HomeNetWorkChangeCallback{
		void homenetworkchange();
	}

}
