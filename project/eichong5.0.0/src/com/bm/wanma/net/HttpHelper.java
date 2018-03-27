package com.bm.wanma.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.bm.wanma.R;
import com.bm.wanma.net.ProgressMultiPartEntity.ProgressListener;
import com.bm.wanma.utils.ProjectApplication;
import com.bm.wanma.utils.StringUtils;
import android.content.Context;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TimeFormatException;


/**
 * Http请求类
 * @author planet
 *
 */
public class HttpHelper {
	public static final String TAG = "HttpHelper";
	public static final int TIME_OUT = 10000;
	private static Handler mHandler = ProjectApplication.getInstance().getHandler();
	private static boolean log = true;

	/**
	 * Get请求
	 * @param url
	 * @param params 参数
	 * @param httpHandler
	 */
	public static void asyncGet(final String url, final HashMap<String, String> params, final HttpHandler httpHandler){
		new Thread(new Runnable() {
            @Override
            public void run() {
                final HttpResult result = connect(url, params, "GET");
                //Files.getContentUri(volumeName);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpHandler.handleResponse(result);
                    }
                });
            }
        }).start();
	}
	
	/**
	 * 给http参数列表添加权限验证
	 * @param params
	 */
	public static void wrapAuth(Map<String, String> params){
		/*if(ZHApplication.getUser() != null && App.getUser().getUser_ID() != null && params != null){
			params.put("User_ID", App.getUser().getUser_ID());
			String userkey = App.getUser().getUser_LoginName()
					+ App.getUser().getPasswordMD5()
					+"yibaotong_md5";
			params.put("User_Key", MD5.getInstance().getMD5ofStr(userkey).toLowerCase());
		}*/
	}
	
	/**
	 * 同步Get请求
	 * @param url
	 * @param params 参数
	 * @param httpHandler
	 */
	public static HttpResult syncGet(final String url, final HashMap<String, String> params){
		return connect(url, params, "GET");
	}


	/**
	 * Post请求读取字符串
	 * @param url
	 * @param params 参数
	 * @param httpHandler
	 */
	public static void asyncPost(final String url, final HashMap<String, String> params, final HttpStringHandler httpHandler){
		new Thread(new Runnable() {
			@Override
			public void run() {
				final HttpStringResult result = postString(url, params);
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						httpHandler.handleResponse(result);
					}
				});
			}
		}).start();
	}

	/**
	 * POST请求
	 * @param url
	 * @param params
	 * @param httpHandler
	 * asyncFormPost(final String url, final HashMap<String, String> params, final HashMap<String, File> files, final HttpStringHandler httpHandler){
		asyncFormPost(url, params, files, httpHandler, null);
	 */
	public static void asyncPost(final String url, final HashMap<String, String> params, final HttpHandler httpHandler){
		new Thread(new Runnable() {
			@Override
			public void run() {
				final HttpResult result = connect(url, params, "POST");
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						httpHandler.handleResponse(result);
					}
				});
			}
		}).start();
	}

	/**
	 * Form表单请求
	 * @param url
	 * @param params
	 * @param files
	 * @param httpHandler
	 */
	public static void asyncFormPost(final String url, final HashMap<String, String> params, final HashMap<String, File> files, final HttpStringHandler httpHandler){
		asyncFormPost(url, params, files, httpHandler, null);
	}
	
	public static void asyncFormPost(final String url, final HashMap<String, String> params, final String fileFieldName, final List<File> files, final HttpStringHandler httpHandler){
		asyncFormPost(url, params, fileFieldName, files, httpHandler, null);
	}


	/**
	 * Form表单请求
	 * @param url
	 * @param params
	 * @param files
	 * @param httpHandler
	 * @param progressListener
	 */
	public static void asyncFormPost(final String url, final HashMap<String, String> params, final HashMap<String, File> files, final HttpStringHandler httpHandler,
			final ProgressListener progressListener){
		new Thread(new Runnable() {
			@Override
			public void run() {
				final HttpStringResult result = postFormString(url, params, files, progressListener);
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						httpHandler.handleResponse(result);
					}
				});
			}
		}).start();
	}
	
	public static void asyncFormPost(final String url, final HashMap<String, String> params, final String fileFieldName, final List<File> files, final HttpStringHandler httpHandler,
			final ProgressListener progressListener){
		new Thread(new Runnable() {
			@Override
			public void run() {
				final HttpStringResult result = postFormString(url, params, fileFieldName, files, progressListener);
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						httpHandler.handleResponse(result);
					}
				});
			}
		}).start();
	}


	public interface HttpHandler{
		void handleResponse(HttpResult result);
	}

	public interface HttpStringHandler{
		void handleResponse(HttpStringResult result);
	}

	/**
	 * 发送请求
	 * @param urlStr
	 * @param params
	 * @param json
	 * @param httpMethod
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static HttpResult connect(String sourceUrl, Map<String,String> params, String httpMethod){
		wrapAuth(params);
		HttpResult result = new HttpResult(0);
		try {
			String urlStr = new String(sourceUrl);
			if(httpMethod.equals("GET")){
				urlStr+="?";
				if(params != null){
					Iterator<Entry<String, String>> it = params.entrySet().iterator();
					while(it.hasNext()){
						Entry<String,String> entry = it.next();
						System.out.println(">>key:"+entry.getKey()+" value="+entry.getValue());
						urlStr+=entry.getKey()+"="+URLEncoder.encode(entry.getValue(), "utf-8")+"&";
					}
					params = null;
				}
			}
			Log.i(TAG, "mthod="+httpMethod+" url="+urlStr);
			result.url = urlStr;

			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection(); 
			connection.setRequestMethod(httpMethod);
			connection.setDoInput(true);
			connection.setConnectTimeout(TIME_OUT); 
			if(params != null){
				Iterator<Entry<String, String>> it = params.entrySet().iterator();
				while(it.hasNext()){
					Entry<String,String> entry = it.next();
					connection.setRequestProperty(entry.getKey(), entry.getValue());
					Log.i(TAG, "post param:"+entry.getValue()+"="+entry.getKey());
				}
			}
			connection.connect();
			int resCode = connection.getResponseCode();
			//读数据
			InputStream in = connection.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;    
			while((len=(in.read(buffer))) != -1){    
				baos.write(buffer,0,len);    
			}
			String retStr = new String(baos.toByteArray());
			in.close();
			if(log)
			Log.i(TAG, retStr+" 状态码:"+resCode);
			//getResult(result, retStr, resCode);
		} catch (MalformedURLException e) {
			result.msg = e.getClass().getSimpleName()+" "+e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			if(e instanceof SocketTimeoutException){
				result.msg = "服务器连接超时!";
			}else if(e instanceof ConnectException
					|| e instanceof SocketException){
				result.msg = "网络连接失败!";//App.getInstance().getString(R.string.connect_failed);
			}else if(e instanceof FileNotFoundException){
				result.msg = "服务器连接失败!";//App.getInstance().getString(R.string.connect_server_failed);
			}else{
				result.msg = e.getClass().getSimpleName()+" "+e.getMessage();
			}
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * Post请求字符串
	 * @param urlStr
	 * @param params
	 * @return
	 */
	public static HttpStringResult postString(String urlStr, Map<String,String> params){
		wrapAuth(params);
		HttpStringResult ret = new HttpStringResult();
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(urlStr);
			HttpEntity entity;

			ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
			if(params != null){
				Set<String> keys = params.keySet();
				for(Iterator<String> i = keys.iterator(); i.hasNext();) {
					String key = (String)i.next();
					pairs.add(new BasicNameValuePair(key, params.get(key)));
					Log.i("cm_netfilePost", "postForm params:"+key+"="+params.get(key));
				}
			}
			entity = new UrlEncodedFormEntity(pairs, "utf-8");


			httpPost.setEntity(entity);
			Log.i(TAG, "post总字节数:"+entity.getContentLength());
			HttpResponse response = client.execute(httpPost);
			//获取状态码
			int res = response.getStatusLine().getStatusCode();
			ret.resCode = res;
			HttpEntity responseEntity = response.getEntity();

			InputStream content = responseEntity.getContent();
			ret.setResult(StringUtils.readString(content));
			
			Log.i(TAG, ret.result+" 状态码:"+res);
		}catch (ConnectTimeoutException e){
			
			
		}catch(TimeFormatException e){
			e.printStackTrace();
			ret.setErrorMsg(e.getLocalizedMessage());
		} catch (IOException e) {
			e.printStackTrace();
			if(e instanceof SocketTimeoutException){
				ret.setErrorMsg(ProjectApplication.getInstance().getString(R.string.server_time_out));
			}else if(e instanceof ConnectException 
					|| e instanceof SocketException){
				ret.setErrorMsg(ProjectApplication.getInstance().getString(R.string.connect_failed));
			}else if(e instanceof FileNotFoundException){
				ret.setErrorMsg(ProjectApplication.getInstance().getString(R.string.connect_server_failed));
			}else{
				ret.setErrorMsg(e.getLocalizedMessage());
			}
		}
		return ret;
	}

	/**
	 * 表单提交
	 * @param urlStr
	 * @param params
	 * @param files
	 * @param progressListener
	 * @return
	 */
	public static HttpStringResult postForm(String urlStr, Map<String,String> params, HashMap<String, File> files,
			ProgressListener progressListener){
		HttpStringResult result = new HttpStringResult();
		try {
			result = postFormString(urlStr, params, files, progressListener);
		}catch(TimeFormatException e){
			result.msg = e.getClass().getSimpleName()+" "+e.getMessage();
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 表单提交
	 * @param urlStr
	 * @param params
	 * @param files
	 * @param progressListener
	 */
	public static HttpStringResult postFormString(String urlStr, Map<String,String> params, HashMap<String, File> files,
			ProgressListener progressListener){
		wrapAuth(params);
		HttpStringResult result = new HttpStringResult();
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(urlStr);
			HttpEntity entity;
			if(files == null && params != null){
				ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
				if(params != null){
					Set<String> keys = params.keySet();
					for(Iterator<String> i = keys.iterator(); i.hasNext();) {
						String key = (String)i.next();
						pairs.add(new BasicNameValuePair(key, params.get(key)));
						Log.i(TAG, "postForm params:"+key+"="+params.get(key));
					}
				}
				entity = new UrlEncodedFormEntity(pairs, "utf-8");
			}else {
				if(progressListener == null){
					entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				}else{
					entity = new ProgressMultiPartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, progressListener);
				}
				if(params != null){
					Set<String> keys = params.keySet();
					for(Iterator<String> i = keys.iterator(); i.hasNext();) {
						String key = (String)i.next();
						((MultipartEntity)entity).addPart(key, new StringBody(params.get(key), Charset.forName("utf-8")));
						Log.i(TAG, "postForm params:"+key+"="+params.get(key));
					}
				}
				if(files != null){
					Set<String> keys = files.keySet();
					for(Iterator<String> i = keys.iterator(); i.hasNext();) {
						String key = (String)i.next();
						if(files.get(key) != null){
						    Log.i(TAG, "postForm files:"+key+"="+files.get(key).getName());
						    ((MultipartEntity)entity).addPart(key, new FileBody(files.get(key)));
						}else{
						    Log.i(TAG, "postForm files:"+key+"=空文件");
						    //文件为空，上传空文件域
						    ((MultipartEntity)entity).addPart(key, new ByteArrayBody(new byte[0], "file.txt"));
						}
					}
				}
			}

			httpPost.setEntity(entity);
			Log.i(TAG, "post总字节数:"+entity.getContentLength());
			if(progressListener != null && files != null){
				progressListener.total(entity.getContentLength());
			}
			HttpResponse response = client.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			result.resCode = response.getStatusLine().getStatusCode();
			InputStream content = responseEntity.getContent();
			result.setResult(StringUtils.readString(content));
			if(log)
			Log.i(TAG, result.result);
		}catch(TimeFormatException e){
			e.printStackTrace();
			result.setErrorMsg(e.getLocalizedMessage());
		} catch (IOException e) {
			e.printStackTrace();
			if(e instanceof SocketTimeoutException){
				result.setErrorMsg(ProjectApplication.getInstance().getString(R.string.server_time_out));
			}else if(e instanceof ConnectException
					|| e instanceof SocketException){
				result.setErrorMsg(ProjectApplication.getInstance().getString(R.string.connect_failed));
			}else if(e instanceof FileNotFoundException){
				result.setErrorMsg(ProjectApplication.getInstance().getString(R.string.connect_server_failed));
			}else{
				result.setErrorMsg(e.getLocalizedMessage());
			}
		}
		return result;
	}
	
	public static HttpStringResult postFormString(String urlStr, Map<String,String> params, String fileFieldName,
			List<File> files,
			ProgressListener progressListener){
		wrapAuth(params);
		Log.i(TAG, "postFormString "+urlStr);
		HttpStringResult result = new HttpStringResult();
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(urlStr);
			HttpEntity entity;
			if(files == null && params != null){
				ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
				if(params != null){
					Set<String> keys = params.keySet();
					for(Iterator<String> i = keys.iterator(); i.hasNext();) {
						String key = (String)i.next();
						pairs.add(new BasicNameValuePair(key, params.get(key)));
						Log.i(TAG, "postForm params:"+key+"="+params.get(key));
					}
				}
				entity = new UrlEncodedFormEntity(pairs, "utf-8");
			}else {
				if(progressListener == null){
					entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				}else{
					entity = new ProgressMultiPartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, progressListener);
				}
				if(params != null){
					Set<String> keys = params.keySet();
					for(Iterator<String> i = keys.iterator(); i.hasNext();) {
						String key = (String)i.next();
						((MultipartEntity)entity).addPart(key, new StringBody(params.get(key), Charset.forName("utf-8")));
						Log.i(TAG, "postForm params:"+key+"="+params.get(key));
					}
				}
				if(files != null){
					/*Set<String> keys = files.keySet();
					for(Iterator<String> i = keys.iterator(); i.hasNext();) {
						String key = (String)i.next();
						if(files.get(key) != null){
						    Log.i(TAG, "postForm files:"+key+"="+files.get(key).getName());
						    ((MultipartEntity)entity).addPart(key, new FileBody(files.get(key)));
						}else{
						    Log.i(TAG, "postForm files:"+key+"=空文件");
						    //文件为空，上传空文件域
						    ((MultipartEntity)entity).addPart(key, new ByteArrayBody(new byte[0], "file.txt"));
						}
					}*/
					for(File f : files){
						((MultipartEntity)entity).addPart(fileFieldName, new FileBody(f));
					}
				}
			}

			httpPost.setEntity(entity);
			Log.i(TAG, "post总字节数:"+entity.getContentLength());
			if(progressListener != null && files != null){
				progressListener.total(entity.getContentLength());
			}
			HttpResponse response = client.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			result.resCode = response.getStatusLine().getStatusCode();
			InputStream content = responseEntity.getContent();
			result.setResult(StringUtils.readString(content));
			if(log)
			Log.i(TAG, result.result);
		}catch(TimeFormatException e){
			e.printStackTrace();
			result.setErrorMsg(e.getLocalizedMessage());
		} catch (IOException e) {
			e.printStackTrace();
			if(e instanceof SocketTimeoutException){
				result.setErrorMsg(ProjectApplication.getInstance().getString(R.string.server_time_out));
			}else if(e instanceof ConnectException
					|| e instanceof SocketException){
				result.setErrorMsg(ProjectApplication.getInstance().getString(R.string.connect_failed));
			}else if(e instanceof FileNotFoundException){
				result.setErrorMsg(ProjectApplication.getInstance().getString(R.string.connect_server_failed));
			}else{
				result.setErrorMsg(e.getLocalizedMessage());
			}
		}
		return result;
	}
}
