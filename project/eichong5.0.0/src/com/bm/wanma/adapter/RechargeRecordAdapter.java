package com.bm.wanma.adapter;

import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.entity.RechargeRecordBean;
import com.bm.wanma.utils.TimeUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 
 * @author lyh
 * 充值记录适配器
 */
public class RechargeRecordAdapter extends BaseAdapter{
	private ArrayList<RechargeRecordBean> mdata;
	private LayoutInflater inflater;
	private RechargeRecordBean bean;
	public RechargeRecordAdapter(Context mContext,ArrayList<RechargeRecordBean> data) {
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
		TextView tv_datatime = null;
		TextView tv_time = null;
		TextView tv_money = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.recharge_record_item, null);
			tv_datatime = (TextView) convertView.findViewById(R.id.datatime);
			tv_time = (TextView) convertView.findViewById(R.id.time);
			tv_money = (TextView) convertView.findViewById(R.id.money);
			convertView.setTag(new MyHold(tv_datatime, tv_time, tv_money));
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_datatime = hold.hold_tv_datatime;
			tv_time = hold.hold_tv_time;
			tv_money = hold.hold_tv_money;
		}
		bean = mdata.get(position);
		if(bean !=null && mdata.size()>0){
			tv_datatime.setText(TimeUtil.getDataTime(bean.getPhTm(), "data"));
//			if (TimeUtil.getDataTime(bean.getPhTm(), "data").equals("今天")||TimeUtil.getDataTime(bean.getPhTm(), "data").equals("昨天")) {				
//				tv_time.setText("");
//			}else {
				tv_time.setText(TimeUtil.getDataTime(bean.getPhTm(), "time"));
//			}
			tv_money.setText(bean.getMn()+" 元");
		}
		return convertView;
	}
	private final class MyHold {
		TextView hold_tv_datatime = null;
		TextView hold_tv_time = null;
		TextView hold_tv_money = null;
	public MyHold(TextView tv_datatime,TextView tv_time,TextView tv_money){
		   this.hold_tv_datatime = tv_datatime;
		   this.hold_tv_time = tv_time;
		   this.hold_tv_money = tv_money;
	   }
	}
}
