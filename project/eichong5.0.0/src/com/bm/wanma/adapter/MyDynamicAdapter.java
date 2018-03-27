package com.bm.wanma.adapter;

import java.io.File;
import java.util.ArrayList;
import com.bm.wanma.R;
import com.bm.wanma.entity.MyDynamicListBean;
import com.bm.wanma.entity.MyNewsSystemBean;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author cm
 * 动态，listview适配器
 *
 */
public class MyDynamicAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<MyDynamicListBean> mdata;
	private LayoutInflater inflater;
	private MyDynamicListBean bean;
	@SuppressWarnings("deprecation")
	DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(false)// 设置下载的图片是否缓存在内存中
			.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
			.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
			.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
			.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
			.build();// 构建完成
	
	public MyDynamicAdapter(Context context,ArrayList<MyDynamicListBean> data) {
		this.mContext = context;
		this.mdata = data;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mdata.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TextView tv_name = null;
		TextView tv_time = null;
		ImageView im_advertisement = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_item_advertisement_system, null);
			tv_name = (TextView)convertView.findViewById(R.id.listview_item_mynews_system_title);
			tv_time = (TextView)convertView.findViewById(R.id.listview_item_mynews_system_time);
			im_advertisement = (ImageView) convertView.findViewById(R.id.im_advertisement);
			convertView.setTag(new MyHold(tv_name,im_advertisement,tv_time));
			
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_name = hold.hold_tv_name;
			im_advertisement = hold.hold_im_advertisement;
			tv_time = hold.hold_tv_time;
		}
			bean = mdata.get(position);
		if(bean != null){
			tv_name.setText(""+bean.getAdDesc());
			
			ImageLoader.getInstance().displayImage(bean.getDynamicPic(), im_advertisement,options);
			String tempTime = bean.getAdDate();
			tempTime = Tools.parseDate(tempTime, "yyyy-MM-dd", "M.dd");
			tv_time.setText(tempTime);
		}
		
		return convertView;
	}

	private final class MyHold {
		TextView hold_tv_name = null;
		ImageView hold_im_advertisement = null;
		TextView hold_tv_time = null;
		public MyHold(TextView tvname,ImageView im_advertisement,TextView tvtitme){
			this.hold_tv_name = tvname;
			this.hold_im_advertisement = im_advertisement;
			this.hold_tv_time = tvtitme;
		}
	}

	
}
