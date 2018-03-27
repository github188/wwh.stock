package com.bm.wanma.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.bm.wanma.R;
import com.bm.wanma.entity.VersionInfoBean;

public class UpdateAppManager {

	/* 下载中 */  
    private static final int DOWNLOAD = 1;  
    /* 下载结束 */  
    private static final int DOWNLOAD_FINISH = 2;  
    /* 下载保存路径 */  
    private String mSavePath;  
    /* 记录进度条数量 */  
    private int progress;  
    /* 是否取消更新 */  
    private boolean cancelUpdate = false;  
    private Context mContext;  
    /* 更新进度条 */  
    private ProgressBar mProgress;  
    private Dialog mDownloadDialog;  
    private VersionInfoBean mInfoBean;
    private int versionCode;//当前版本号
    private boolean flagForce;
  
    @SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch (msg.what)  
            {  
            // 正在下载   
            case DOWNLOAD:  
                // 设置进度条位置   
               mProgress.setProgress(progress);  
                break;  
            case DOWNLOAD_FINISH:  
                // 安装文件   
            	mDownloadDialog.cancel();
                installApk();  
                break;  
            default:  
                break;  
            }  
        };  
    };  
  
    public UpdateAppManager(Context context,VersionInfoBean bean,int verCode)  
    {  
        this.mContext = context;  
        this.mInfoBean = bean;
        this.versionCode = verCode;
    }  
  
    /** 
     * 检测软件更新 
     */  
    public void checkUpdate(){ 
    	if(mInfoBean.getVersNumber() != null && versionCode < Integer.parseInt(mInfoBean.getVersNumber())){
    		//0正常更新1强制更新
    		if(mInfoBean.getVers_auto_update() != null && mInfoBean.getVers_auto_update().equals("1")){
    			 showForceNoticeDialog(); 
    		}else {
    			 // 显示提示对话框   
                showNoticeDialog(); 
    		}
    		
    	}
    	
   }  
    /** 
     * 显示强制软件更新对话框 
     */  
    private void showForceNoticeDialog()  
    {  
        // 构造对话框   
        AlertDialog.Builder builder = new Builder(mContext);  
        builder.setTitle("软件更新");  
        builder.setMessage("检测到新版本，如果不更新，将无法使用，是否立即更新？");  
        // 更新   
        builder.setCancelable(false);
        builder.setPositiveButton("更新", new OnClickListener()  
        {  
            @Override  
            public void onClick(DialogInterface dialog, int which)  
            {  
                dialog.dismiss();  
                // 显示强制下载对话框   
                showDownloadDialog(); 
                flagForce = true;
            }  
        });  
        // 稍后更新   
        builder.setNegativeButton("退出", new OnClickListener()  
        {  
            @Override  
            public void onClick(DialogInterface dialog, int which)  
            {  
                dialog.dismiss();
                System.exit(0);  
            }  
        });  
        Dialog noticeDialog = builder.create();  
        noticeDialog.show();  
    }  
  
  
    /** 
     * 显示软件更新对话框 
     */  
    private void showNoticeDialog()  
    {  
        // 构造对话框   
        AlertDialog.Builder builder = new Builder(mContext);  
        builder.setTitle("软件更新");  
        builder.setMessage("检测到新版本，是否立即更新？");  
        // 更新   
        builder.setCancelable(false);
        builder.setPositiveButton("更新", new OnClickListener()  
        {  
            @Override  
            public void onClick(DialogInterface dialog, int which)  
            {  
                dialog.dismiss();  
                // 显示下载对话框   
                showDownloadDialog(); 
                flagForce = false;
            }  
        });  
        // 稍后更新   
        builder.setNegativeButton("稍后更新", new OnClickListener()  
        {  
            @Override  
            public void onClick(DialogInterface dialog, int which)  
            {  
                dialog.dismiss();
            }  
        });  
        Dialog noticeDialog = builder.create();  
        noticeDialog.show();  
    }  
  
    /** 
     * 显示软件下载对话框 
     */  
    private void showDownloadDialog()  
    {  
        // 构造软件下载对话框   
        AlertDialog.Builder builder = new Builder(mContext);  
        builder.setTitle("正在更新");  
        // 给下载对话框增加进度条   
        final LayoutInflater inflater = LayoutInflater.from(mContext);  
        View v = inflater.inflate(R.layout.softupdate_progress, null);  
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);  
        builder.setView(v); 
       // ToastUtil.TshowToast("下载对话框");
        builder.setCancelable(false);
        // 取消更新   
        builder.setNegativeButton("取消", new OnClickListener()  
        {  
            @Override  
            public void onClick(DialogInterface dialog, int which)  
            {  
            	//设置取消状态   
                cancelUpdate = true; 
                dialog.dismiss();  
                if(flagForce){
                  System.exit(0);  
                }
            }  
        });  
        mDownloadDialog = builder.create();  
        mDownloadDialog.show();  
        // 下载文件   
        downloadApk();  
    }  
  
    /** 
     * 下载apk文件 
     */  
    private void downloadApk()  {  
        // 启动新线程下载软件   
        new downloadApkThread().start();  
    }  
  
    /** 
     * 下载文件线程 
     */  
    private class downloadApkThread extends Thread  
    {  
        @Override  
        public void run()  
        {  
            try  
            {  
                // 判断SD卡是否存在，并且是否具有读写权限   
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))  
                {  
                    // 获得存储卡的路径   
                    String sdpath = Environment.getExternalStorageDirectory() + "/";  
                    mSavePath = sdpath + "download"; 
                    URL url;
                    if(mInfoBean.getVersUrl()!= null && !mInfoBean.getVersUrl().isEmpty()){
                    	url = new URL(mInfoBean.getVersUrl()); 
                    }else {
                    	url = new URL("http://www.eichong.com/app/android/eichong.apk");
                    }
                    // 创建连接   
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
                    conn.connect();  
                    // 获取文件大小   
                    int length = conn.getContentLength();  
                    // 创建输入流   
                    InputStream is = conn.getInputStream();  
  
                    File file = new File(mSavePath);  
                    // 判断文件目录是否存在   
                    if (!file.exists())  
                    {  
                        file.mkdir();  
                    }  
                    File apkFile = new File(mSavePath, "eichong");  
                    FileOutputStream fos = new FileOutputStream(apkFile);  
                    int count = 0;  
                    // 缓存   
                    byte buf[] = new byte[1024];  
                    // 写入到文件中   
                    do  
                    {  
                        int numread = is.read(buf);  
                        count += numread;  
                        // 计算进度条位置   
                        progress = (int) (((float) count / length) * 100);  
                        // 更新进度   
                        mHandler.sendEmptyMessage(DOWNLOAD);  
                        if (numread <= 0)  
                        {  
                            // 下载完成   
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);  
                            break;  
                        }  
                        // 写入文件   
                        fos.write(buf, 0, numread);  
                    } while (!cancelUpdate);// 点击取消就停止下载.   
                    fos.close();  
                    is.close();  
                }  
            } catch (MalformedURLException e)  
            {  
                e.printStackTrace();  
            } catch (IOException e)  
            {  
                e.printStackTrace();  
            }  
            // 取消下载对话框显示   
            mDownloadDialog.dismiss();  
        }  
    };  
  
    /** 
     * 安装APK文件 
     */  
    private void installApk()  
    {  
        File apkfile = new File(mSavePath, "eichong");  
        if (!apkfile.exists())  
        {  
            return;  
        }  
        // 通过Intent安装APK文件   
        Intent i = new Intent(Intent.ACTION_VIEW);  
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//如何不设置，点击打开是不会打开新版本应用
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
        ProjectApplication.setGuideType(false);// 重新进入引导页
        mContext.startActivity(i);  
        //安装后自动系统
        android.os.Process.killProcess(android.os.Process.myPid()); 
    } 

}
