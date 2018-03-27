package com.bm.wanma.adapter;

import java.util.List;

import com.bm.wanma.R;
import com.bm.wanma.entity.InvoiceRecordBean;
import com.bm.wanma.entity.InvoiceRecordSectionBean;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.utils.Tools;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author cm
 * 动态，listview适配器
 *
 */
public class MyInvoiceRecordAdapter extends BaseAdapter {
	private Context mContext;
	private List<InvoiceRecordSectionBean> mdata;
	private LayoutInflater inflater;
	private InvoiceRecordSectionBean bean;
	private InvoiceRecordBean billBean;
	
	public MyInvoiceRecordAdapter(Context context,List<InvoiceRecordSectionBean> data) {
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
		MyHold viewHold = null;
		if(convertView == null){
			viewHold = new MyHold();
			convertView = inflater.inflate(R.layout.listview_item_myinvoice_record, null);
			viewHold.tv_money = (TextView)convertView.findViewById(R.id.listview_money);
			viewHold.tv_time = (TextView)convertView.findViewById(R.id.listview_time);
			viewHold.tv_status = (TextView)convertView.findViewById(R.id.listview_status);
			viewHold.iv_arrow = (ImageView) convertView.findViewById(R.id.listview_arrow);
			convertView.setTag(viewHold);
		}else {
			viewHold = (MyHold) convertView.getTag();
		}
		
		bean = mdata.get(position);
		if(bean.getType() == 1){//标题
			viewHold.tv_time.setText(TimeUtil.parseDate(bean.getBillBean().getTime(),
					"yyyy-MM","yyyy年MM月"));
			viewHold.tv_time.setTextColor(mContext.getResources().getColor(R.color.common_black));
			viewHold.tv_money.setVisibility(View.GONE);
			viewHold.tv_status.setVisibility(View.GONE);
			viewHold.iv_arrow.setVisibility(View.GONE);
			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.common_light_white));
		}else if(bean.getType() == 0){
			viewHold.tv_money.setVisibility(View.VISIBLE);
			viewHold.tv_status.setVisibility(View.VISIBLE);
			viewHold.iv_arrow.setVisibility(View.VISIBLE);
			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.common_white));
			billBean = bean.getBillBean();
			viewHold.tv_time.setText(TimeUtil.parseDate(billBean.getTime(),
					"yyyy-MM-dd HH:mm", "MM月dd日 HH:mm"));
			viewHold.tv_time.setTextColor(mContext.getResources().getColor(R.color.common_light_black));
			viewHold.tv_money.setText(Tools.formatMoney(billBean.getAmount(), 2, "¥"));
			//0受理中 1处理完成 2未支付邮费
			if("0".equals(billBean.getStatus())){
				viewHold.tv_status.setText("待开票");
				viewHold.tv_status.setTextColor(mContext.getResources().getColor(R.color.common_black));
			}else if("1".equals(billBean.getStatus())){
				viewHold.tv_status.setText("已开票");
				viewHold.tv_status.setTextColor(mContext.getResources().getColor(R.color.common_black));
			}else if("2".equals(billBean.getStatus())){
				viewHold.tv_status.setText("未支付");
				viewHold.tv_status.setTextColor(mContext.getResources().getColor(R.color.common_orange));
			}else if("3".equals(billBean.getStatus())){
				viewHold.tv_status.setText("已拒绝");
				viewHold.tv_status.setTextColor(mContext.getResources().getColor(R.color.common_black));
			}
		}
		
		return convertView;
	}

	private final class MyHold {
		TextView tv_money;
		TextView tv_time;
		TextView tv_status;
		ImageView iv_arrow;
	}
	
	
}
