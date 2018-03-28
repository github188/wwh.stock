package com.ijustyce.weekly1601.event;

import android.app.Activity;
import android.view.View;

import com.ijustyce.fastandroiddev3.baseLib.utils.FileUtils;
import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev3.http.FileAPI;
import com.ijustyce.fastandroiddev3.http.ProgressListener;
import com.ijustyce.weekly1601.R;
import com.ijustyce.weekly1601.WebViewActivity;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangchun on 2016/11/12.
 */

public class DownloadAndUploadEvent {

    private Activity activity;
    public DownloadAndUploadEvent(Activity activity) {
        this.activity = activity;
        if (activity instanceof WebViewActivity) {
            ((WebViewActivity)activity).contentView.webView.loadUrl("com.lzhplus.lzh://com.lzhplus.lzh/?linkId=10708764&linkType=3");
        }
    }

    public void downloadByWebView(View view) {
        //  这里webview需要继承ProgressWebView，它已经实现自动下载，加载下载url即可！
        if (activity instanceof WebViewActivity) {
            ((WebViewActivity)activity).contentView.webView.loadUrl("com.lzhplus.lzh://com.lzhplus.lzh/?linkId=10708764&linkType=3");
        }
    }

    public void download(View view) {
        //  如果不想监听下载进度，你可以不创建这个listener，并在创建FileAPI的时候传入null即可
        ProgressListener listener = new ProgressListener() {
            @Override
            public void onProgress(int percent, boolean isFinish) {

            }
        };
        FileAPI api = FileAPI.initByUrlAndListener(
                "http://ijustyce.com/app/app-android_test-debug.apk", listener);
        //  如果不是apk文件，你可以不设置autoInstall，autoInstall会根据文件类型打开下载的文件
        //  下载完成后，点击通知也可以打开下载的文件
        api.showNotify(true).autoInstall(true).setNotifyView("文件下载中", "文件下载中 ", R.mipmap.ic_launcher);
        //  如果你不需要下载完成的回调，可以把Action1 传为null
        api.startDownload(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void upload(View view) {
        //  如果不需要监听上传进度，你可以不创建这个对象，调用FileAPI的时候传入null即可
        ProgressListener listener = new ProgressListener() {
            @Override
            public void onProgress(int percent, boolean isFinish) {

            }
        };
        String path = FileUtils.getAvailablePath("test");
        File file = new File(path + "/4.png");
        if (!file.exists()) {
            ILog.d("===upload===", "file not exists");
            return;
        }

        String url = "http://test.beta.jilvinfo.com/upload.php";
        FileAPI fileAPI = FileAPI.initByUrlAndListener(url, listener);
        //  通知栏显示上传进度，这里的true是指如果没有权限显示通知，是否打开配置本应用通知权限的界面，而不是指是否需要展示通知
        //  如果不需要展示通知，不写这行代码就好，默认就是不显示的
        fileAPI.showNotify(true).setNotifyView("文件上传中", "文件上传中 ", R.mipmap.ic_launcher);
        //  如果不需要上传文成后的回调，你可以不创建Action1，传入null即可, 不过，一般情况，我们需要这个回调
        fileAPI.startUpload("uploadedfile", file, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = response.body().string();  //  回去服务端返回的数据，可能是json，可能不是
                    ILog.i("===result===", responseString);
                    //  TODO 根据你自己的业务，进行判断上传是否OK
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
