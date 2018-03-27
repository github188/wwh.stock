package com.bm.wanma.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.content.Context;
import android.os.AsyncTask;

import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
/**
 * 广告图片加载
 * @author lyhr
 */
public class PictureUpload extends AsyncTask<String, Integer, Boolean> {
	ControlAppear appear;
	private String AdvertPicUrl;
	private String Picname;
	private Context context;
	private String begintime;
	private String endtime;
	private String Adtid;
	private String adtgo;
	private String AdUrlName;
	private String AdUrl;
	/**
	 * @param context 
	 * @param AdvertPicUrl 图片地址
	 * @param Picname 图片名字
	 * @param begintime 开始时间
	 * @param endtime 结束时间
	 * @param Adtid 广告id
	 * @param adtgo 是否跳转  0否1是
	 * @param AdUrlName 广告地址文件名
	 * @param AdUrl 广告地址
	 */
	public PictureUpload(ControlAppear appear,Context context, String AdvertPicUrl,
			String Picname ,String begintime,String endtime,String Adtid,String adtgo,String AdUrlName,String AdUrl) {
		this.begintime = begintime;
		this.endtime = endtime;
		this.AdvertPicUrl = AdvertPicUrl;
		this.Picname = Picname;
		this.Adtid = Adtid;
		this.adtgo = adtgo;
		this.AdUrlName = AdUrlName;
		this.AdUrl = AdUrl;
		this.context = context;
		this.appear = appear;
	}

	@Override
	protected Boolean  doInBackground(String... params) {
		if(Tools.isEmptyString(Picname)){
			return false;
		}
		URL myFileUrl = null;   
        try {   
            myFileUrl = new URL(AdvertPicUrl);   
        } catch (MalformedURLException e) {
            e.printStackTrace();   
        } 
        InputStream is = null;
        try {   
            HttpURLConnection conn = (HttpURLConnection) myFileUrl   
            .openConnection();  
         
            conn.setDoInput(true);   
      
            conn.connect();  
            if(conn.getResponseCode()!=200){
	            return false;
	         	}
            is = conn.getInputStream();   
      
            File examine = new File(Tools.advertisementpath);
    		if (!examine.exists()) {
    			examine.mkdirs();
    		}
    		File f = new File(Tools.advertisementpath, Picname);
    		if (f.exists()) {
    			f.delete();
    		}
    		
    		FileOutputStream out = new FileOutputStream(f);
            int length = 0;
            byte[] buff = new byte[1024 * 1024];
    		while ((length = is.read(buff)) > 0) {
            out.write(buff, 0, length);
            }
            out.flush();
            out.close();
            return true;

    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        return false;  
	}

	@Override
	protected void onPostExecute(Boolean bitmap) {
		super.onPostExecute(bitmap);
		if(!bitmap){
			return;
		}
		if(!Tools.isEmptyString(AdUrl)){
			PreferencesUtil.setPreferences(context, AdUrlName,AdUrl);
		}
		if(!Tools.isEmptyString(adtgo)){
			PreferencesUtil.setPreferences(context, Picname+"adtgo",adtgo);
		}
		if(!Tools.isEmptyString(Adtid)){
			PreferencesUtil.setPreferences(context, Picname+"AdId",Adtid);
		}
		if(!Tools.isEmptyString(AdvertPicUrl)){
			PreferencesUtil.setPreferences(context, Picname+"Url",AdvertPicUrl);
		}
		if(!Tools.isEmptyString(begintime)){
			PreferencesUtil.setPreferences(context, Picname+"BeginTime",begintime);
		}
		if(!Tools.isEmptyString(endtime)){
			PreferencesUtil.setPreferences(context, Picname+"EndTime",endtime);
		}
		PreferencesUtil.setPreferences(context, Picname+"Click", true);

		if(Picname.equals("Tab1")){ appear.Appear(); }
		
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);

	}
	
	public interface ControlAppear{
		 void Appear();
	}
	
} 