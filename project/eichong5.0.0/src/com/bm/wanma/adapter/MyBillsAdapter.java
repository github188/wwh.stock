package com.bm.wanma.adapter;

import java.util.List;

import com.bm.wanma.R;
import com.bm.wanma.entity.BillBean;
import com.bm.wanma.entity.BillSectionBean;
import com.bm.wanma.utils.GetResourceUtil;
import com.bm.wanma.utils.TimeUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author cm
 * 动态，listview适配器
 *
 */
public class MyBillsAdapter extends BaseAdapter {
	private Context mContext;
	private List<BillSectionBean> mdata;
	private LayoutInflater inflater;
	private BillSectionBean bean;
	private BillBean billBean;
	
	public MyBillsAdapter(Context context,List<BillSectionBean> data) {
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
		
		TextView tv_title = null;
		TextView tv_money = null;
		TextView tv_time = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_item_mybills, null);
			tv_title = (TextView)convertView.findViewById(R.id.listview_mybill_title);
			tv_money = (TextView)convertView.findViewById(R.id.listview_mybill_money);
			tv_time = (TextView)convertView.findViewById(R.id.listview_mybill_time);
			convertView.setTag(new MyHold(tv_title,tv_money,tv_time));
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_title = hold.hold_tv_title;
			tv_money = hold.hold_tv_money;
			tv_time = hold.hold_tv_time;
		}
		bean = mdata.get(position);
		if(bean.getType() == 1){//标题
			tv_time.setText(TimeUtil.parseDate(bean.getBillBean().getRecordTime(),
					"yyyy-MM", "yyyy年MM月"));
			tv_time.setTextColor(mContext.getResources().getColor(R.color.common_black));
			tv_title.setVisibility(View.GONE);
			tv_money.setVisibility(View.GONE);
			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.common_light_white));
		}else if(bean.getType() == 0){
			tv_title.setVisibility(View.VISIBLE);
			tv_money.setVisibility(View.VISIBLE);
			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.common_white));
			billBean = bean.getBillBean();
			tv_time.setText(TimeUtil.parseDate(billBean.getRecordTime(),
					"yyyy-MM-dd HH:mm", "MM月dd日 HH:mm"));
			tv_time.setTextColor(mContext.getResources().getColor(R.color.common_light_black));
			//(1-充电消费 2-预约消费 3-购物消费 4-充值 6-优惠券抵扣)
			if("1".equals(billBean.getRecordTitle())){
				tv_title.setText("充电消费");
				tv_money.setTextColor(mContext.getResources().getColor(R.color.common_green));
				tv_money.setText("- ¥"+billBean.getRecordMoney());
			}else if("2".equals(billBean.getRecordTitle())){
				tv_title.setText("预约消费");
				tv_money.setTextColor(mContext.getResources().getColor(R.color.common_green));
				tv_money.setText("- ¥"+billBean.getRecordMoney());
			}else if("3".equals(billBean.getRecordTitle())){
				tv_title.setText("购物消费");
				tv_money.setTextColor(mContext.getResources().getColor(R.color.common_green));
				tv_money.setText("- ¥"+billBean.getRecordMoney());
			}else if("4".equals(billBean.getRecordTitle())){
				tv_title.setText("充值");
				tv_money.setTextColor(mContext.getResources().getColor(R.color.common_orange));
				tv_money.setText("+ ¥"+billBean.getRecordMoney());
			}else if("6".equals(billBean.getRecordTitle())){
				tv_title.setText("优惠券抵扣");
				tv_money.setTextColor(mContext.getResources().getColor(R.color.common_orange));
				tv_money.setText("- ¥"+billBean.getRecordMoney());
			}
		}
		
		return convertView;
	}

	private final class MyHold {
		TextView hold_tv_title = null;
		TextView hold_tv_money = null;
		TextView hold_tv_time = null;
		public MyHold(
				TextView tvtitle,TextView tvmoney,TextView tvtitme){
			this.hold_tv_title = tvtitle;
			this.hold_tv_money = tvmoney;
			this.hold_tv_time = tvtitme;
		}
	}
	
	
}
