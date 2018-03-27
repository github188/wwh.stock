package com.bm.wanma.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bm.wanma.R;
import com.bm.wanma.entity.InformationBean;
import com.bm.wanma.entity.MyNewsSystemBean;
import com.bm.wanma.utils.ProjectApplication;
import com.bm.wanma.utils.Tools;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author lyh
 * 资讯，listview适配器
 *
 */
public class InformationAdapter extends BaseAdapter {
	private Context mContext;
	private List<InformationBean> mdata;
	private LayoutInflater inflater;
	private InformationBean bean;
	
	public InformationAdapter(Context context,List<InformationBean> data) {
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
		ImageView iv_list_item_img = null;
		TextView tv_time = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_item_information, null);
			tv_name = (TextView)convertView.findViewById(R.id.listview_item_title);
			iv_list_item_img = (ImageView)convertView.findViewById(R.id.list_item_img);
			tv_time = (TextView)convertView.findViewById(R.id.listview_item_time);
			
			convertView.setTag(new MyHold(tv_name,iv_list_item_img,tv_time));
			
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_name = hold.hold_tv_name;
			iv_list_item_img = hold.hold_iv_list_item_img;
			tv_time = hold.hold_tv_time;
		}
		try {
			bean = mdata.get(position);
			if (!isNetwork()&&!ProjectApplication.getButtonType()) {
				tv_name.setBackgroundColor(Color.parseColor("#EBECED"));
				tv_time.setBackgroundColor(Color.parseColor("#EBECED"));
			}else {
				tv_name.setBackgroundColor(Color.parseColor("#ffffff"));
				tv_time.setBackgroundColor(Color.parseColor("#ffffff"));
			}
			if(bean != null){
				tv_name.setText(""+bean.getNeiName());
				ImageLoader.getInstance().displayImage(bean.getNewsPicUrl(), iv_list_item_img, options);
				if (Tools.isEmptyString(bean.getNeiCreatedate())) {
					tv_time.setText(""+Tools.parseDate(bean.getNeiUpdatedate() ,"yyyy/MM/dd HH:mm", "yyyy-MM-dd"));
				}else {				
					tv_time.setText(""+Tools.parseDate(bean.getNeiCreatedate() ,"yyyy/MM/dd HH:mm", "yyyy-MM-dd"));
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return convertView;
	}

	private final class MyHold {
		TextView hold_tv_name = null;
		ImageView hold_iv_list_item_img = null;
		TextView hold_tv_time = null;
		public MyHold(
				TextView tvname,ImageView iv_list_item_img,TextView tvtitme){
			this.hold_tv_name = tvname;
			this.hold_iv_list_item_img = iv_list_item_img;
			this.hold_tv_time = tvtitme;
		}
	}
	

	
	private boolean isNetwork() {
		boolean b = false;
		ConnectivityManager connectivityManager= 
		        (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if(info !=null){
			b = true;
		}else {
			b = false;
		}
		return b;
	}

	DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.img_1) // 设置图片下载期间显示的图片
			.showImageForEmptyUri(R.drawable.img_1) // 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.img_1) // 设置图片加载或解码过程中发生错误显示的图片
			.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
			.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
			// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
			.build(); // 创建配置过得DisplayImageOption对象
}
