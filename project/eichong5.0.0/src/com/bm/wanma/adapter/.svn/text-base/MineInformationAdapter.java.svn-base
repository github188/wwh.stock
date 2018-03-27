package com.bm.wanma.adapter;

import java.util.List;

import com.bm.wanma.R;
import com.bm.wanma.entity.OrderStatusBean;
import com.bm.wanma.ui.activity.ChargeDetailActivity;
import com.bm.wanma.utils.TimeUtil;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MineInformationAdapter extends BaseAdapter{
	private Context mContext;
	private List<OrderStatusBean> mdata;
	private LayoutInflater inflater;
	private OrderStatusBean bean;
	public MineInformationAdapter(Context mContext,List<OrderStatusBean> data) {
		this.mContext = mContext;
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
		TextView tv_dynamic = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.mine_information_item, null);
			tv_data = (TextView) convertView.findViewById(R.id.tv_data);
			tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			tv_dynamic = (TextView) convertView.findViewById(R.id.tv_dynamic);
			convertView.setTag(new MyHold(tv_data, tv_title, tv_content, tv_dynamic));
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_data = hold.hold_tv_data;
			tv_title = hold.hold_tv_title;
			tv_content = hold.hold_tv_content;
			tv_dynamic = hold.hold_tv_dynamic;
		}
		bean = mdata.get(position);
		if(bean !=null && mdata.size()>0){
			tv_data.setText(TimeUtil.getDataForNews(bean.getTime()*1000,"MM月dd日"));
			tv_title.setText(bean.getTitle());
			tv_content.setText(bean.getContent());
			tv_dynamic.setText("查看详情");
		}
		tv_dynamic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();
				intent.putExtra("ordernumber", bean.getOrderid());
				intent.setClass(mContext, ChargeDetailActivity.class);
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}
	private final class MyHold {
		TextView hold_tv_data = null;
		TextView hold_tv_title = null;
		TextView hold_tv_content = null;
		TextView hold_tv_dynamic = null;
	public MyHold(TextView tv_data,TextView tv_title,TextView tv_content,TextView tv_dynamic){
		   this.hold_tv_data = tv_data;
		   this.hold_tv_title = tv_title;
		   this.hold_tv_content = tv_content;
		   this.hold_tv_dynamic = tv_dynamic;
	   }
	}
}
