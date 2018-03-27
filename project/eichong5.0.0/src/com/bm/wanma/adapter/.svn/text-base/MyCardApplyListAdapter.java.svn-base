package com.bm.wanma.adapter;

import java.util.ArrayList;
import com.bm.wanma.R;
import com.bm.wanma.entity.MyCardApplyInfo;
import com.bm.wanma.utils.RegularExpressionUtil;
import com.bm.wanma.utils.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author cm
 * 充点卡申请列表，listview适配器
 *
 */
public class MyCardApplyListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<MyCardApplyInfo> mdata;
	private LayoutInflater inflater;
	private MyCardApplyInfo bean;
	
	public MyCardApplyListAdapter(Context context,ArrayList<MyCardApplyInfo> data) {
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
		TextView tv_phone = null;
		TextView tv_address = null;
		TextView tv_apply_time = null;
		TextView tv_status = null;
		TextView tv_ic = null;
		TextView tv_ic_tag = null;
		TextView tv_guashi = null;
		TextView tv_guashi_tag = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_item_myapply_info, null);
			tv_name = (TextView)convertView.findViewById(R.id.listview_card_name);
			tv_phone = (TextView)convertView.findViewById(R.id.listview_card_phone);
			tv_address = (TextView)convertView.findViewById(R.id.listview_card_addr);
			tv_apply_time = (TextView)convertView.findViewById(R.id.listview_card_time);
			tv_status = (TextView)convertView.findViewById(R.id.listview_card_status);
			tv_ic = (TextView)convertView.findViewById(R.id.listview_card_ic);
			tv_ic_tag = (TextView)convertView.findViewById(R.id.listview_card_ic_temp);
			tv_guashi = (TextView)convertView.findViewById(R.id.listview_card_guashi);
			tv_guashi_tag = (TextView)convertView.findViewById(R.id.listview_card_guashi_temp);
			convertView.setTag(new MyHold(tv_name,tv_phone,tv_address,tv_apply_time,tv_status,tv_ic,tv_ic_tag,tv_guashi,tv_guashi_tag));
			
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_name = hold.tv_name;
			tv_phone = hold.tv_phone;
			tv_address = hold.tv_address;
			tv_apply_time = hold.tv_apply_time;
			tv_status = hold.tv_status;
			tv_ic = hold.tv_ic;
			tv_ic_tag = hold.tv_ic_tag;
			tv_guashi = hold.tv_guashi;
			tv_guashi_tag = hold.tv_guashi_tag;
		}
		bean = mdata.get(position);
		tv_name.setText(""+bean.getName());
		//手机号码加****
		String tempphone = bean.getPhone();
		if(RegularExpressionUtil.isMobilephone(tempphone)){
			String nick = tempphone.substring(0,3)+"****"+tempphone.substring(7,11);
			tv_phone.setText(nick);
		}else {
			tv_phone.setText(" ");
		}
		tv_address.setText(""+bean.getAddr());
		String applyTime = bean.getAppTime();
		applyTime = Tools.parseDate(applyTime, "", "yyyy-MM-dd HH:mm");
		tv_apply_time.setText(applyTime);
		//appStatus0：申请中，1：申请成功 , 2:申请失败 
		String tempStatus = bean.getAppStatus();
		if("0".equals(tempStatus)){
			tv_status.setText("申请中");
			tv_status.setTextColor(mContext.getResources().getColor(R.color.common_orange));
			tv_ic.setVisibility(View.GONE);
			tv_ic_tag.setVisibility(View.GONE);
			tv_guashi.setVisibility(View.GONE);
			tv_guashi_tag.setVisibility(View.GONE);
		}else if("1".equals(tempStatus)){
			//cardStatus卡状态 0：正常，1：挂失
			String tempCardStatus = bean.getCardStatus();
			if("0".equals(tempCardStatus)){
				tv_status.setText("已发卡");
				tv_status.setTextColor(mContext.getResources().getColor(R.color.common_green));
				tv_ic.setText(""+bean.getOutNum());
				tv_guashi.setVisibility(View.GONE);
				tv_guashi_tag.setVisibility(View.GONE);
			}else if("1".equals(tempCardStatus)){
				tv_status.setText("挂失");
				tv_status.setTextColor(mContext.getResources().getColor(R.color.common_red));
				tv_ic.setText(""+bean.getOutNum());
				String tempgTime = bean.getLossTime();
				tempgTime = Tools.parseDate(tempgTime, "", "yyyy-MM-dd HH:mm");
				tv_guashi.setText(tempgTime);
				tv_guashi.setVisibility(View.VISIBLE);
				tv_guashi_tag.setVisibility(View.VISIBLE);
			}
		}else if("2".equals(tempStatus)){
			tv_status.setText("申请失败");
			tv_status.setTextColor(mContext.getResources().getColor(R.color.common_red));
			tv_ic.setVisibility(View.GONE);
			tv_ic_tag.setVisibility(View.GONE);
			tv_guashi.setVisibility(View.GONE);
			tv_guashi_tag.setVisibility(View.GONE);
		}
		return convertView;
	}

	private final class MyHold {
		TextView tv_name = null;
		TextView tv_phone = null;
		TextView tv_address = null;
		TextView tv_apply_time = null;
		TextView tv_status = null;
		TextView tv_ic = null;
		TextView tv_ic_tag = null;
		TextView tv_guashi = null;
		TextView tv_guashi_tag = null;
		public MyHold(
				TextView name,TextView phone,TextView address,TextView apply_time,
				TextView status,TextView ic,TextView ic_tag,TextView guashi,TextView guashi_tag){
			this.tv_name = name;
			this.tv_phone = phone;
			this.tv_address = address;
			this.tv_apply_time = apply_time;
			this.tv_status = status;
			this.tv_ic = ic;
			this.tv_ic_tag = ic_tag;
			this.tv_guashi = guashi;
			this.tv_guashi_tag = guashi_tag;
		}
	}
	
	
}
