package com.bm.wanma.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bm.wanma.R;
import com.bm.wanma.entity.SystemNoticeBean;
import com.bm.wanma.utils.TimeUtil;

public class SystemNoticeAdapter extends BaseAdapter{
	private List<SystemNoticeBean> mdata;
	private LayoutInflater inflater;
	private SystemNoticeBean bean;
	public SystemNoticeAdapter(Context mContext,List<SystemNoticeBean> data) {
		this.mdata = data;
		inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); ;
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
		TextView tv_data = null;
		TextView tv_title = null;
		TextView tv_content = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.mine_system_notice_item, null);
			tv_data = (TextView) convertView.findViewById(R.id.tv_data);
			tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			convertView.setTag(new MyHold(tv_data, tv_title, tv_content));
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_data = hold.hold_tv_data;
			tv_title = hold.hold_tv_title;
			tv_content = hold.hold_tv_content;
		}
		bean = mdata.get(position);
		if(bean !=null && mdata.size()>0){
			tv_data.setText(TimeUtil.getDataForNews(bean.getTime()*1000,"MM月dd日"));
			tv_title.setText(bean.getTitle());
			tv_content.setText(bean.getContent());
		}
		
		return convertView;
	}
	private final class MyHold {
		TextView hold_tv_data = null;
		TextView hold_tv_title = null;
		TextView hold_tv_content = null;
	public MyHold(TextView tv_data,TextView tv_title,TextView tv_content){
		   this.hold_tv_data = tv_data;
		   this.hold_tv_title = tv_title;
		   this.hold_tv_content = tv_content;
	   }
	}
}
