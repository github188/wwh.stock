package com.ijustyce.fastandroiddev3.glide;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.ijustyce.fastandroiddev3.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev3.baseLib.utils.ThreadUtils;

import java.io.File;

/**
 * Created by yangchun on 2017/1/8.
 */

public class MyGlideModule implements GlideModule {

    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int memory = CommonTool.getAvailMemory(context);
        double value = 1.2;
        if (memory < 650) value = 1.0;
        if (memory < 350) value = 0.8;
        if (memory < 150) value = 0.5;

        int customMemoryCacheSize = (int) (value * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (value * defaultBitmapPoolSize);

        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        File cacheDir = context.getExternalCacheDir();//指定的是数据的缓存地址
        int diskCacheSize = 1024 * 1024 * 300;//最多可以缓存多少字节的数据，这里是300M
        if (cacheDir == null) {
            builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "glide", diskCacheSize));
        }
        else {
            builder.setDiskCache(new DiskLruCacheFactory(cacheDir.getPath(), "glide", diskCacheSize));
        }

        long space = getAvailableSpace();
        if (space < 80) {
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    Glide.get(context).clearDiskCache();
                }
            });
        }
    }

    private long getAvailableSpace(){
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long availCount = sf.getAvailableBlocks();
        long available = availCount * blockSize;
        available /= 1024 * 1024;
        return available;
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
