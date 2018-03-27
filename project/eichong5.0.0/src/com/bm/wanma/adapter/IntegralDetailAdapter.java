package com.bm.wanma.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import com.bm.wanma.R;
import com.bm.wanma.entity.IntegralDetailBean;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.utils.Tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author lyh
 * 已完成
 */
@SuppressLint("NewApi")
public class IntegralDetailAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<IntegralDetailBean> mdata;
	private LayoutInflater inflater;
	private IntegralDetailBean bean;

	public IntegralDetailAdapter(Context context,ArrayList<IntegralDetailBean> data) {
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

	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv_integral_detail_time_title = null;//日期
			TextView tv_integral_detail_type= null;//类型
			TextView tv_integral_detail_size = null;//大小

	    	  if(convertView == null){
		    		convertView = inflater.inflate(R.layout.list_integral_detail_item, null);
		    		tv_integral_detail_time_title = (TextView) convertView.findViewById(R.id.integral_detail_time_title);
		    		tv_integral_detail_type = (TextView) convertView.findViewById(R.id.integral_detail_type);
		    		tv_integral_detail_size = (TextView) convertView.findViewById(R.id.integral_detail_size);
		    		
		    		convertView.setTag(new MyHold(tv_integral_detail_time_title, tv_integral_detail_type, tv_integral_detail_size));
		    	}else {
		    		MyHold hold = (MyHold) convertView.getTag();
		    		tv_integral_detail_time_title = hold.hold_tv_integral_detail_time_title;
		    		tv_integral_detail_type = hold.hold_tv_integral_detail_type;
		    		tv_integral_detail_size = hold.hold_tv_integral_detail_size;
		    	}
	    	  bean = mdata.get(position);
	    	  if(bean != null){
	    		  tv_integral_detail_time_title.setText(TimeUtil.parseDate(bean.getIntegral_date(),
	    				   "yyyy-MM-dd", "yyyy-MM-dd"));
	    		  tv_integral_detail_type.setText(bean.getActivity_name());
	    		  if (!Tools.isEmptyString(bean.getDirection())&&bean.getDirection().equals("0")) {
	    			  tv_integral_detail_size.setText("+"+bean.getIntegral_value());
	    		  }else {
	    			  tv_integral_detail_size.setText("-"+bean.getIntegral_value());
				}
	    		  
	    	  }
		return convertView;
	}


	private  class MyHold {
		TextView hold_tv_integral_detail_time_title = null;//日期
		TextView hold_tv_integral_detail_type = null;
		TextView hold_tv_integral_detail_size = null;
		public MyHold(TextView tv_integral_detail_time_title,
				TextView tv_integral_detail_type,TextView tv_integral_detail_size){
			this.hold_tv_integral_detail_time_title = tv_integral_detail_time_title;
			this.hold_tv_integral_detail_type = tv_integral_detail_type;
			this.hold_tv_integral_detail_size = tv_integral_detail_size;
		}
	}
	
}
