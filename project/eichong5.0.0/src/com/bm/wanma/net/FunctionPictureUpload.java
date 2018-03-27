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
 * 功能按钮图片加载
 * @author lyhr
 */
public class FunctionPictureUpload extends AsyncTask<String, Integer, Boolean> {
	private String ablSort;//位置  
	private String ablName;//按钮名字
	private String buttonPicUrl;//图片url
	private Context context;
	private String pkAblId;//id
	private String Update;//id
	/**
	 * 
	 * @param context  
	 * @param ablAction       按钮跳转地址
	 * @param buttonPicUrl    图片url
	 * @param ablSort         位置  
	 * @param pkAblId         id  "Update"
	 * @param ablName         按钮名字
	 */
	
	public FunctionPictureUpload(Context context, String buttonPicUrl,
			String ablSort ,String pkAblId,String ablName,String Update) {
		this.ablSort = ablSort;
		this.pkAblId = pkAblId;
		this.buttonPicUrl = buttonPicUrl;
		this.ablName = ablName;
		this.Update = Update;
		this.context = context;
	}

	@Override
	protected Boolean  doInBackground(String... params) {
		if(Tools.isEmptyString(pkAblId)||Tools.isEmptyString(buttonPicUrl)){
			return false;
		}
		URL myFileUrl = null;   
        try {   
            myFileUrl = new URL(buttonPicUrl);   
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
    		File f = new File(Tools.advertisementpath, pkAblId+"button");
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
		LogUtil.i("cm_netPost","content==-----------button-------------------------缓存"+ablName);
		if(!Tools.isEmptyString(pkAblId)){
			//保存id
			PreferencesUtil.setPreferences(context, pkAblId+"Id",pkAblId);
		}
		
		if(!Tools.isEmptyString(ablSort)){
			//位置
			PreferencesUtil.setPreferences(context, pkAblId+"ablSort",ablSort);
		}

		if(!Tools.isEmptyString(ablName)){
			PreferencesUtil.setPreferences(context, pkAblId+"ablName",ablName);
		}
		if(!Tools.isEmptyString(Update)){
			PreferencesUtil.setPreferences(context, pkAblId+"Update",Update);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);

	}
} 