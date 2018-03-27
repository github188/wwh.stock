package com.bm.wanma.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.bm.wanma.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
/**
 * ImageView创建工厂
 */
public class ViewFactory {
	
	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param text
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.img_banner) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.img_banner) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.img_banner) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象
		
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		ImageLoader.getInstance().displayImage(url, imageView, options);
		return imageView;
	}
	

}
