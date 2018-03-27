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
import android.widget.Toast;

import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;
/**
 * 功能按钮图片加载
 * @author lyhr
 */
public class ImgBannerPictureUpload extends AsyncTask<String, Integer, Boolean> {

	private String buttonPicUrl;//图片url
	private Context context;
	private String pkAblId;//id

	/**
	 * 
	 * @param context  
	 * @param ablAction       按钮跳转地址
	 * @param buttonPicUrl    图片url
	 * @param ablSort         位置  
	 * @param pkAblId         id
	 * @param ablName         按钮名字
	 */
	
	public ImgBannerPictureUpload(Context context,String buttonPicUrl,String pkAblId) {

		this.pkAblId = pkAblId;
		this.buttonPicUrl = buttonPicUrl;
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
    		File f = new File(Tools.advertisementpath, pkAblId+"banner");
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
//		if(!bitmap){
//			return;
//		}
//		LogUtil.i("cm_netPost","content==------------------------------------缓存"+bitmap);

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