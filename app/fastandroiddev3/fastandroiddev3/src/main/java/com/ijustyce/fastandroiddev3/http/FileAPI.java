package com.ijustyce.fastandroiddev3.http;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.ijustyce.fastandroiddev3.IApplication;
import com.ijustyce.fastandroiddev3.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev3.baseLib.utils.FileUtils;
import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev3.baseLib.utils.RegularUtils;
import com.ijustyce.fastandroiddev3.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev3.baseLib.utils.ThreadUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by yangchun on 2016/11/10.
 */

public class FileAPI {
    private Retrofit retrofit;

    private String url;
    private ProgressListener listener;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager notificationManager;
    private int notifyId;
    private String notifyContent;
    private File downloadedFile;
    private boolean autoInstall;

    public static FileAPI initByUrlAndListener(String url, ProgressListener listener){
        FileAPI api = new FileAPI();
        api.url = url;
        api.listener = listener;
        return api;
    }

    private ProgressListener defaultListener = new ProgressListener() {
        @Override
        public void onProgress(int percent, boolean isFinish) {
            if (mBuilder != null) {
                if (isFinish) {
                    afterTransferFinish();
                    return;
                }
                mBuilder.setProgress(100, percent, false);
                mBuilder.setContentText(notifyContent + percent + "%");
                notificationManager.notify(notifyId, mBuilder.build());
            }
            if (listener != null) {
                listener.onProgress(percent, isFinish);
            }
        }
    };

    public FileAPI autoInstall(boolean value) {
        autoInstall = value;
        return this;
    }

    private void afterTransferFinish(){

        if (downloadedFile != null && downloadedFile.exists()) {
            if (autoInstall && downloadedFile.getName().endsWith(".apk")) {
                FileUtils.setupApk(downloadedFile, IApplication.getInstance());
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(downloadedFile), FileUtils.getFileType(downloadedFile));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pi = PendingIntent.getActivity(IApplication.getInstance(), notifyId, intent, 0);
            mBuilder.setContentIntent(pi);
        }

        mBuilder.setContentTitle("文件传输已完成");
        mBuilder.setContentText("文件传输已完成");
        mBuilder.setProgress(0, 100, false);
        notificationManager.notify(notifyId, mBuilder.build());
    }

    public FileAPI showNotify(boolean openSettingIfNoRight){
        if (Build.VERSION.SDK_INT >= 19 && openSettingIfNoRight &&
                !CommonTool.isNotificationEnabled(IApplication.getInstance())) {
            CommonTool.setNotify(IApplication.getInstance());
        }
        if (notifyId <= 0) notifyId = (int)System.currentTimeMillis();
        if (notificationManager == null) {
            notificationManager = (NotificationManager) IApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        }if (mBuilder == null) {
            mBuilder = new NotificationCompat.Builder(IApplication.getInstance());
        }
        return this;
    }

    public FileAPI setNotifyView(String title, String content, int iconRes) {
        if (mBuilder == null) showNotify(false);
        mBuilder.setTicker(title).setContentTitle(title).setContentText(content).setSmallIcon(iconRes);
        mBuilder.setAutoCancel(true);
        notificationManager.notify(notifyId, mBuilder.build());
        notifyContent = content;
        return this;
    }

    private FileAPI() {

        DownloadInterceptor interceptor = new DownloadInterceptor(defaultListener);
        OkHttpClient client = HttpManager.getBuilder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://ijustyce.win/")
                .client(client)
                .build();
    }

    public boolean startUpload(String parameter, File[] uploadFile, Callback<ResponseBody> callback){
        if (!RegularUtils.isUrl(url)) {
            ILog.e("===FileAPI===", "url is error while upload file ");
            return false;
        }
        int size = uploadFile == null ? 0 : uploadFile.length;
        if (size < 1) {
            ILog.e("===FileAPI===", "file is null or not exists");
            return false;
        }
        MultipartBody.Part[] upload = new MultipartBody.Part[size];
        int index = 0;
        for (File file : uploadFile) {
            if (file == null) continue;
            UploadRequestBody fileRequestBody = new UploadRequestBody(file, defaultListener);
            MultipartBody.Part body = MultipartBody.Part.createFormData(parameter,
                    file.getName(), fileRequestBody);
            upload[index] = body;
            index++;
        }
        Call<ResponseBody> call = retrofit.create(HttpService.class).upload(url, upload);
        HttpManager.send(callback, call);
        return true;
    }

    public boolean startUpload(String parameter, File uploadFile, Callback<ResponseBody> callback){
        File[] files = {uploadFile};
        return startUpload(parameter, files, callback);
    }

    private void save(Response<ResponseBody> response, File file){
        try {
            InputStream is = response == null || response.body() == null ? null : response.body().byteStream();
            if (is == null) {
                return;
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }
            fos.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getDownloadedFile(){
        return downloadedFile;
    }

    public boolean startDownload(final Callback<ResponseBody> callBack) {
        if (!RegularUtils.isUrl(url)) {
            ILog.e("===FileAPI===", "url is error while download file ");
            return false;
        }
        String fullFileName = url.substring(url.lastIndexOf("/") +1 ).split("\\?")[0];
        String fileName = FileUtils.getFileName(fullFileName);
        if (StringUtils.isEmpty(fullFileName) || StringUtils.isEmpty(fileName)) return false;
        String extraName = FileUtils.getFileExtraName(fullFileName);
        final File savedFile = FileUtils.getUnExistsFile(fileName + extraName, "download");
        if (!RegularUtils.isUrl(url)) {
            ILog.e("===FileAPI===", "url is error while download file ");
            return false;
        }
        if (savedFile == null || savedFile.exists()) {
            ILog.e("===FileAPI===", "file is null or already exists, please call FileUtils.renameIfExists if need");
            return false;
        }
        downloadedFile  = savedFile;
        Call<ResponseBody> call = retrofit.create(HttpService.class).download(url);
        HttpManager.send(new Callback<ResponseBody>() {
            @Override
            public void onResponse(final Call<ResponseBody> call, final Response<ResponseBody> response) {
                ThreadUtils.execute(new Runnable() {
                    @Override
                    public void run() {
                        save(response, savedFile);
                        if (callBack != null) callBack.onResponse(call, response);
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (callBack != null) callBack.onFailure(call, t);
                t.printStackTrace();
            }
        }, call);
        return true;
    }
}
