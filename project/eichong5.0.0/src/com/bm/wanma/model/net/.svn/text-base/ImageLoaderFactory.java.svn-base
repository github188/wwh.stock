package com.bm.wanma.model.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.bm.wanma.R;
import com.nostra13.universalimageloader.cache.disc.DiscCacheAware;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import java.io.File;

/**
 * 网络加载图片ImageLoad
 */
@SuppressWarnings("deprecation")
public class ImageLoaderFactory {
    private static final int CACHE_SIZE = 128 * 1024 * 1024;
    private static final int TIME_OUT = 60 * 1000;
    
    private static Context mContext;
    private static ImageLoader mImageLoader;
    private static ImageLoaderConfiguration mImageLoaderConfiguration;
    
    public static void init(Context context) {
        mContext = context;
        initImageLoader();
    }
    
    /**
     * 获取imageloader对象
     * @return
     */
    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }
    
    private static void initImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = ImageLoader.getInstance();
            initImageLoaderConfiguration();
            mImageLoader.init(mImageLoaderConfiguration);
        }
    }
    
    /**
     * 初始化imageloader的基本属性 例如超时时间 同时下载图片线程数量等
     */
    private static void initImageLoaderConfiguration() {
        if (mImageLoaderConfiguration == null) {
            DisplayImageOptions defaultOptions = generateDefaultDisplayImageOptions();

            initDiscCacheAware();

            mImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(mContext)
                    .threadPoolSize(3)
                    .memoryCache(new WeakMemoryCache())
                    .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                    .imageDownloader(new BaseImageDownloader(mContext,TIME_OUT, TIME_OUT)) // connectTimeout (5 s), readTimeout (20 s)
                    .defaultDisplayImageOptions(defaultOptions)
                    .build();
        }
    }
    
    /**
     * 初始化 图片缓存路径位置
     */
    private static void initDiscCacheAware() {
      /*  if (mDiscCacheAware == null) {
            TotalSizeLimitedDiscCache totalSizeLimitedDiscCache = new TotalSizeLimitedDiscCache(new File(Config.PATH_CACHE_IMAGES), CACHE_SIZE);
            mDiscCacheAware = totalSizeLimitedDiscCache;
        }*/
    }
  
    /**
     * 设置一个图片加载options
     * @return
     */
	public static DisplayImageOptions generateDefaultDisplayImageOptions() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .showImageOnFail(R.drawable.bg_map_head2)
                .showImageForEmptyUri(R.drawable.bg_map_head2)
                .showImageOnLoading(R.drawable.bg_map_head2)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        return defaultOptions;
    }

	/**
	 * 设置一个带有图片图片加载完成之前显示另一张图片的options 图片的资源id需要传入
	 * @param stubImageRes
	 * @return
	 */
    public static DisplayImageOptions generateDefaultDisplayImageOptionsWithStubImage(int stubImageRes) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .showStubImage(stubImageRes)
                .showImageForEmptyUri(stubImageRes)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return defaultOptions;
    }



}
