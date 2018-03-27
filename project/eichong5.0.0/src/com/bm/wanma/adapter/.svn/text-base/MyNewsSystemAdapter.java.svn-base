package com.bm.wanma.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.bm.wanma.R;
import com.bm.wanma.entity.MyNewsSystemBean;
import com.bm.wanma.utils.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author cm
 * 系统消息，listview适配器
 *
 */
public class MyNewsSystemAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<MyNewsSystemBean> mdata;
	private LayoutInflater inflater;
	private MyNewsSystemBean bean;
	
	public MyNewsSystemAdapter(Context context,ArrayList<MyNewsSystemBean> data) {
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
		TextView tv_content = null;
		TextView tv_time = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_item_mynews_system, null);
			tv_name = (TextView)convertView.findViewById(R.id.listview_item_mynews_system_title);
			tv_content = (TextView)convertView.findViewById(R.id.listview_item_mynews_system_content);
			tv_time = (TextView)convertView.findViewById(R.id.listview_item_mynews_system_time);
			
			convertView.setTag(new MyHold(tv_name,tv_content,tv_time));
			
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_name = hold.hold_tv_name;
			tv_content = hold.hold_tv_content;
			tv_time = hold.hold_tv_time;
		}
			bean = mdata.get(position);
		if(bean != null){
			tv_name.setText(""+bean.getTitle());
			tv_content.setText(""+bean.getContent());
			String tempTime = bean.getEdittime();
			long da = Long.parseLong(tempTime);
			DateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
			Date date = new Date(da);
			//tempTime = Tools.parseDate(tempTime, "yyyy-MM-dd HH:mm:ss", "MM/dd HH:mm");
			tv_time.setText(""+sdf.format(date));
			
		}
		
		return convertView;
	}

	private final class MyHold {
		TextView hold_tv_name = null;
		TextView hold_tv_content = null;
		TextView hold_tv_time = null;
		public MyHold(
				TextView tvname,TextView tvcontent,TextView tvtitme){
			this.hold_tv_name = tvname;
			this.hold_tv_content = tvcontent;
			this.hold_tv_time = tvtitme;
		}
	}
	
	
}
