package com.bm.wanma.utils;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

/**
 * @author cm
 * 扫描声音管理类
 */
public class SoundManager {
	private SoundPool mSoundPool;
	private AudioManager mAudioManager;
	private HashMap<String, Integer> soundPoolMap;
	private Context mContext;
	
	private static final int MAX_STREAS = 3;
	
	public SoundManager(Context context){
		initSoundManager(context);
	}
	
	public void initSoundManager(Context context){
		mContext = context;
		mAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		soundPoolMap = new HashMap<String, Integer>();
		mSoundPool = new SoundPool(MAX_STREAS, AudioManager.STREAM_MUSIC, 0);
	}
	public void addSound(String soundName,int resId){
		this.soundPoolMap.put(soundName, mSoundPool.load(mContext, resId, 1));
	}
	
	public void playSound(String soundName){
		if(mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL){
			this.mSoundPool.play(soundPoolMap.get(soundName).intValue(), 1, 1, 0, 0, 1);
		}
	}
	
	public void stopSound(String soundName){
		this.mSoundPool.stop(soundPoolMap.get(soundName).intValue());
	}
	
	public void ptl(String log){
		Log.i("test",log);
	}
}
