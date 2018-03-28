package com.ijustyce.fastandroiddev3.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.ijustyce.fastandroiddev3.R;
import com.ijustyce.fastandroiddev3.baseLib.utils.FileUtils;
import com.ijustyce.fastandroiddev3.http.FileAPI;

import java.io.File;

/**
 * Created by yangchun on 16/4/13.  带进度条的WebView，
 * 如果你调用了  setWebChromeClient  请务必在 onProgressChanged 里调用这个类的onProgressChanged
 */
public class ProgressWebView extends WebView{

    private ProgressBar progressbar;
    private Context mContext;

    public ProgressWebView(Context context, AttributeSet attrs) {

        super(context, attrs);
        mContext = context;
        progressbar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 10, 0, 0));
        addView(progressbar);
        getSettings().setSupportZoom(true);
        getSettings().setAppCacheEnabled(true);
        getSettings().setJavaScriptEnabled(true);
        setWebChromeClient(new WebChromeClient());
        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                FileAPI fileAPI = FileAPI.initByUrlAndListener(url, null);
                fileAPI.showNotify(true).autoInstall(true).setNotifyView("文件下载中", "文件下载中 ", R.mipmap.ic_launcher);
                fileAPI.startDownload(null);
            }
        });
    }

    @Override
    public void destroy() {
        super.destroy();
        setDownloadListener(null);
        mContext = null;
    }

    public void setProgressDrawable(Drawable drawable) {

        progressbar.setProgressDrawable(drawable);
    }

    public boolean isLoadFinish(){
        return progressbar != null && progressbar.getVisibility() == GONE;
    }

    //  重写这个方法可以设置
    public void setProgressbarHeight(int height) {

        if (height > 5) {
            progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, height, 0, 0));
        }
    }

    public void setOnPageFinish(OnPageFinish onPageFinish){
        this.onPageFinish = onPageFinish;
    }

    private OnPageFinish onPageFinish;
    public interface OnPageFinish{
        void onPageFinish();
    }

    //  如果你调用了 setWebChromeClient ，请务必调用这个方法！！！
    public void onProgressChanged(WebView view, int newProgress) {

        if (newProgress == 100) {
            if (onPageFinish != null){
                onPageFinish.onPageFinish();
            }
            progressbar.setVisibility(GONE);
        } else {
            if (progressbar.getVisibility() == GONE)
                progressbar.setVisibility(VISIBLE);
            progressbar.setProgress(newProgress);
        }
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            ProgressWebView.this.onProgressChanged(view, newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
