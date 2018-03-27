package com.bm.wanma.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bm.wanma.R;
import com.bm.wanma.entity.EquipmentBean;
import com.bm.wanma.entity.PowerElectricpileListBean;
import com.bm.wanma.utils.Tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author cm
 * 充值选择金额，gridview适配器
 *
 */
public class MyRechargeMoneyGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private String typename;
	private ArrayList<String> mdata;
	private LayoutInflater inflater;
	 //标识选择的Item
	private int mselection = -1;
	
	 public void setSelection(int selection){
		 this.mselection = selection;
		 super.notifyDataSetChanged();
		 }
	
	
	public MyRechargeMoneyGridViewAdapter(Context context,ArrayList<String> data) {
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

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TextView tv_name = null;
		ImageView iv_icon = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.gridview_item_commit_device_error, null);
			tv_name = (TextView)convertView.findViewById(R.id.gridview_commit_error_tv);
			iv_icon = (ImageView)convertView.findViewById(R.id.gridview_commit_error_iv);
			convertView.setTag(new MyHold(tv_name, iv_icon));
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_name = hold.hold_name;
			iv_icon = hold.hold_icon;
			
		}
			typename = mdata.get(position);
			tv_name.setText(""+typename);
		
		if (position == mselection) {
				convertView.setBackground(mContext.getResources().getDrawable(
						R.drawable.popup_select_shape_checked));
				tv_name.setTextColor(mContext.getResources().getColor(R.color.common_orange));
				iv_icon.setVisibility(View.VISIBLE);
				
		} else {
			convertView.setBackground(mContext.getResources().getDrawable(
					R.drawable.popup_select_shape_uncheck));
			tv_name.setTextColor(mContext.getResources().getColor(R.color.common_gray));
			iv_icon.setVisibility(View.GONE);
			
			
		}
		
		return convertView;
	}

	private final class MyHold {
		TextView hold_name = null;
		ImageView hold_icon = null;
		public MyHold(
				TextView tvn,ImageView ivic){
			this.hold_name = tvn;
			this.hold_icon = ivic;
		}
	}
	
	
}
