package com.bm.wanma.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bm.wanma.R;
import com.bm.wanma.entity.InformationBean;
import com.bm.wanma.entity.MalfunctionBean;
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
 * 故障，listview适配器
 *
 */
public class MalfunctionAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<MalfunctionBean> mdata;
	private LayoutInflater inflater;
	private MalfunctionBean bean;
	
	public MalfunctionAdapter(Context context,ArrayList<MalfunctionBean> data) {
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
		
		TextView tv_malfunction_charge = null;
		TextView tv_malfunction_address = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.list_malfunction_item, null);
			tv_malfunction_charge = (TextView) convertView.findViewById(R.id.malfunction_charge);
			tv_malfunction_address = (TextView) convertView.findViewById(R.id.malfunction_address);
			
			convertView.setTag(new MyHold(tv_malfunction_charge,tv_malfunction_address));
			
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_malfunction_charge = hold.hold_tv_malfunction_charge;
			tv_malfunction_address = hold.hold_tv_malfunction_address;
		}
		bean = mdata.get(position);

		if(bean != null){
			tv_malfunction_charge.setText(""+bean.getMprName());
			tv_malfunction_address.setText(""+bean.getAddress());
		}
		
		return convertView;
	}

	private final class MyHold {
		TextView hold_tv_malfunction_charge = null;
		TextView hold_tv_malfunction_address = null;
		public MyHold(
				TextView malfunction_charge,TextView hold_tv_malfunction_address){
			this.hold_tv_malfunction_charge = malfunction_charge;
			this.hold_tv_malfunction_address = hold_tv_malfunction_address;
		}
	}
	
}
