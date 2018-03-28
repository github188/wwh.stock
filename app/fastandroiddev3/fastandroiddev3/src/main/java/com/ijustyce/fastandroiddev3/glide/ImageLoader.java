package com.ijustyce.fastandroiddev3.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ijustyce.fastandroiddev3.IApplication;
import com.ijustyce.fastandroiddev3.R;
import com.ijustyce.fastandroiddev3.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev3.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev3.baseLib.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yangchun on 16/6/14.
 */
public class ImageLoader {

    private static double wBili, hBili;
    private static final int MIN_WIDTH = 200;
    private static final int MIN_HEIGHT = 200;
    private static NetworkDisablingLoader networkDisablingLoader;

    static {
        int target_width = StringUtils.getInt(CommonTool.getMetaData(IApplication.getInstance(),
                "design_width"));
        int target_height = StringUtils.getInt(CommonTool.getMetaData(IApplication.getInstance(),
                "design_height"));
        int mWidth = CommonTool.getScreenWidth(IApplication.getInstance());
        int mHeight = CommonTool.getScreenHeight(IApplication.getInstance());

        wBili = ((double) mWidth) / ((double) target_width);
        hBili = ((double) mHeight / ((double) target_height));

        double max = 0.85 ;

        if (wBili > max) wBili = max;
        if (hBili > max) hBili = max;
    }

    /**
     * @param img
     * @param url
     * @param type
     */
    public static final int TYPE_TOP = 1;
    public static final int TYPE_BOTTOM = 2;
    public static final int TYPE_LEFT = 3;
    public static final int TYPE_RIGHT = 4;
    public static final int TYPE_ALL = 5;

    public static void loadWidthCorner(ImageView img, String url, int width, int height,
                                       int radius, int type) {
        RoundedCornersTransformation transformation = null;
        if (type < TYPE_TOP || type > TYPE_RIGHT) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.ALL);
        } else if (type == 1) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.TOP);
        } else if (type == 2) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.BOTTOM);
        } else if (type == 3) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.LEFT);
        } else if (type == 4) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.RIGHT);
        }
        load(img, url, width, height, transformation);
    }

    public static void loadWidthCorner(ImageView img, String url, int radius, int type) {
        loadWidthCorner(img, url, -1, -1, radius, type);
    }

    public static void loadCircle(ImageView img, String url) {
        loadCircle(img, url, -1, -1);
    }

    public static void loadCircle(ImageView img, String url, int width, int height) {
        Transformation transformation = new CropCircleTransformation(img.getContext());
        load(img, url, width, height, transformation);
    }

    public static ImageLoadType type;

    public abstract static class ImageLoadType {
        public abstract boolean isAliYunServer(String url);

        public abstract boolean isSlowNetWork();
    }

    private static class NetworkDisablingLoader implements StreamModelLoader<String> {
        @Override
        public DataFetcher<InputStream> getResourceFetcher(final String model, int width, int height) {
            return new DataFetcher<InputStream>() {
                @Override public InputStream loadData(Priority priority) throws Exception {
                    throw new IOException("Forces Glide network failure");
                }
                @Override
                public void cleanup() {
                }
                @Override
                public String getId() {
                    return model;
                }
                @Override
                public void cancel() {
                }
            };
        }
    }

    private static NetworkDisablingLoader getNetWorkDisablingLoader(){
        NetworkDisablingLoader loader = networkDisablingLoader;
        if (loader == null){
            synchronized (ImageLoader.class){
                loader = networkDisablingLoader;
                if (loader == null){
                    networkDisablingLoader = loader = new NetworkDisablingLoader();
                }
            }
        }
        return loader;
    }

    private static void doAliYunLoad(ImageView img, String defaultUrl, String wifiUrl, int width, int height, boolean slowNetWork
            , Transformation transformation){
        if (slowNetWork){
            BitmapRequestBuilder<String, Bitmap> offlineBuild = getBitmapBuilder(img, wifiUrl, width, height, transformation, getNetWorkDisablingLoader());
            BitmapRequestBuilder<String, Bitmap> netBuild = getBitmapBuilder(img, defaultUrl, width, height, transformation, null);
            if (netBuild == null || offlineBuild == null) return;
            netBuild.thumbnail(offlineBuild.diskCacheStrategy(DiskCacheStrategy.SOURCE)).into(img);
        }else{
            BitmapRequestBuilder wifi = getBitmapBuilder(img, wifiUrl, width, height, transformation, null);
            if (wifi != null) {
                wifi.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
            }
        }
    }

    private static boolean preLoad(ImageView img, String url, int width, int height, Transformation transformation) {
        if (type == null || !type.isAliYunServer(url)) return false;

        if( width == -1 && height == -1){
            String defaultUrl = url;
            String wifiUrl = url;
            doAliYunLoad(img, defaultUrl, wifiUrl, width, height, type.isSlowNetWork(), transformation);
        }
        else {
            String defaultUrl = "x-oss-process=image/resize,m_fill,";
            String wifiUrl = "x-oss-process=image/resize,m_fill,";
            if (width > 1 && height > 1) {
                String sizeUrl = "h_"+height + ",w_"+ width;
                defaultUrl += sizeUrl;
                wifiUrl += sizeUrl;
            }
            if (url.contains("?")) {
                String[] array = url.split("\\?");
                //defaultUrl = array[0] + defaultUrl;
                //wifiUrl = array[0] + wifiUrl;
                if (array.length > 1) {
                    wifiUrl = array[0]+"?"+wifiUrl+ ","+array[1];
                    defaultUrl = array[0]+"?"+wifiUrl+ ","+array[1];
                }
            }else{
                defaultUrl = url +"?" + defaultUrl;
                wifiUrl = url + "?"+ wifiUrl;
            }
            doAliYunLoad(img, defaultUrl, wifiUrl, width, height, type.isSlowNetWork(), transformation);
        }

        return true;


        /*String defaultUrl = "@1wh_80q_";
        String wifiUrl = "@1wh_";
        if (width > 1 && height > 1) {
            String sizeUrl = height + "h_" + width + "w";
            defaultUrl += sizeUrl;
            wifiUrl += sizeUrl;
        }
        if (url.contains("?")) {
            String[] array = url.split("\\?");
            defaultUrl = array[0] + defaultUrl;
            wifiUrl = array[0] + wifiUrl;
            if (array.length > 1) {
                defaultUrl = defaultUrl + "?" + array[1];
                wifiUrl = wifiUrl + "?" + array[1];
            }
        }else{
            defaultUrl = url + defaultUrl;
            wifiUrl = url + wifiUrl;
        }
        doAliYunLoad(img, defaultUrl, wifiUrl, width, height, type.isSlowNetWork(), transformation);
        return true;*/
    }

    public static void load(ImageView img, String url, int width, int height, Transformation transformation) {
        if (StringUtils.isEmpty(url) || img == null) {
            ILog.e("===ImageLoader===", "url or img is null ...");
            return;
        }
        if (width > MIN_WIDTH && height > MIN_HEIGHT){
            height *= hBili;
            width *= wBili;
        }

        String[] urls = url.split("\\?");

        //  this is gif
        if (urls[0].endsWith(".gif")) {
            DrawableRequestBuilder builder = getDrawableBuilder(img, url, width, height);
            if (builder != null) {
                builder.into(img);
            }
            return;
        }

        if (preLoad(img, url, width, height, transformation)) return;  //  preload 用于加载其他aliyun等图片资源,以下是正常逻辑!aliyun不支持gif
        BitmapRequestBuilder builder = getBitmapBuilder(img, url, width, height, transformation, null);
        if (builder != null) {
            builder.into(img);
        }
    }

    private static DrawableRequestBuilder getDrawableBuilder(ImageView img, String url, int width, int height){
        if (img == null || !canLoad(img.getContext())) return null;
        Object placeholder = img.getTag(R.string.glide_tag_placeholder);
        DrawableRequestBuilder<String> builder;
        try {
            builder = Glide.with(img.getContext()).load(url);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if (width > 0 && height > 0) builder.override(width, height);
        if (placeholder != null) {
            builder.placeholder((int) placeholder);
            Object error = img.getTag(R.string.glide_tag_failed);
            builder.error(error == null ? (int) placeholder : (int) error);
            Object nullImg = img.getTag(R.string.glide_tag_null);
            builder.fallback(nullImg == null ? (int) placeholder : (int) nullImg);
        }
        return builder;
    }

    private static BitmapRequestBuilder<String, Bitmap> getBitmapBuilder(ImageView img, String url, int width, int height,
                                                       Transformation transformation, StreamModelLoader loader){

        if (img == null || !canLoad(img.getContext())) return null;
        BitmapRequestBuilder<String, Bitmap> builder;
        try {
            RequestManager manager = Glide.with(img.getContext());
            if (loader != null) manager.using(loader);
            builder = manager.load(url).asBitmap();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        boolean hasSize = false;
        if (width > 1 && height > 1) {
            builder.override(width, height);
            hasSize = true;
        }
        if (transformation != null) {
            builder.format(DecodeFormat.PREFER_ARGB_8888).transform(transformation);
        }else if (hasSize && width <= MIN_WIDTH && height <= MIN_HEIGHT) {
            builder.format(DecodeFormat.PREFER_ARGB_8888);
        }
        Object placeholder = img.getTag(R.string.glide_tag_placeholder);
        if (placeholder != null) {
            builder.placeholder((int) placeholder);
            Object error = img.getTag(R.string.glide_tag_failed);
            builder.error(error == null ? (int) placeholder : (int) error);
            Object nullImg = img.getTag(R.string.glide_tag_null);
            builder.fallback(nullImg == null ? (int) placeholder : (int) nullImg);
        }
        return builder;
    }
    private static RequestListener<String, GlideDrawable> mRequestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            //显示错误信息
            Log.w("li", "onException: ", e);
            //打印请求URL
            Log.d("li", "onException: " + model);
            //打印请求是否还在进行
            Log.d("li", "onException: " + target.getRequest().isRunning());
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            //打印请求URL
            Log.d("li", "onException: " + model);
            //打印请求是否还在进行
            Log.d("li", "onException: " + target.getRequest().isRunning());
            return false;
        }
    };

    private static boolean canLoad(Context context) {
        if (context == null) return false;
        if (context instanceof Activity && ((Activity) context).isFinishing()) return false;
        return true;
    }

    public static void load(ImageView img, String url, int width, int height) {
        load(img, url, width, height, null);
    }

    public static void load(ImageView img, String url) {
        load(img, url, -1, -1, null);
    }
}
