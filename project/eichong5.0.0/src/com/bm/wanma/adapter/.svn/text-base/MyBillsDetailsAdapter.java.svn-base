package com.bm.wanma.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.bm.wanma.R;
import com.bm.wanma.entity.BillBean;
import com.bm.wanma.entity.BillDetailsBean;
import com.bm.wanma.entity.BillOneBean;
import com.bm.wanma.entity.BillSectionBean;
import com.bm.wanma.utils.GetResourceUtil;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.utils.ToastUtil;
import com.bm.wanma.utils.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author lyh
 * 账单明细适配器
 *
 */
public class MyBillsDetailsAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<BillDetailsBean> mdata;
	private BillDetailsBean billBean;
	
	public MyBillsDetailsAdapter(Context context,ArrayList<BillDetailsBean> data) {
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
		TextView tv_bills_details_data = null;//日期
		TextView tv_bills_details_datatmie = null;//时间
		TextView tv_recharge_money = null;//充值金额
		TextView tv_bills_recharge_type = null;//充值类型
		
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_item_mybills_details, null);
			
			tv_bills_details_data = (TextView)convertView.findViewById(R.id.bills_details_data);
			tv_bills_recharge_type = (TextView)convertView.findViewById(R.id.recharge_type);
			tv_bills_details_datatmie = (TextView)convertView.findViewById(R.id.bills_details_datatmie);//明细按钮
			tv_recharge_money = (TextView)convertView.findViewById(R.id.recharge_money);
			convertView.setTag(new MyHold(tv_bills_details_data,tv_bills_details_datatmie,tv_recharge_money,tv_bills_recharge_type));
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_bills_details_data = hold.hold_tv_bills_details_data;
			tv_bills_details_datatmie = hold.hold_tv_bills_details_datatmie;
			tv_recharge_money = hold.hold_tv_recharge_money;
			tv_bills_recharge_type = hold.hold_tv_bills_recharge_type;
		}
		billBean = mdata.get(position);
		if(billBean !=null){
			tv_bills_details_data.setText(TimeUtil.getDataTime(billBean.getPhTm(), "data"));
//			if(TimeUtil.getDataTime(billBean.getPhTm(), "data").equals("今天")||TimeUtil.getDataTime(billBean.getPhTm(), "data").equals("昨天")){
//				tv_bills_details_datatmie.setText(" ");
//			}else {				
				tv_bills_details_datatmie.setText(TimeUtil.getDataTime(billBean.getPhTm(), "time"));
//			}
				if (!Tools.isEmptyString(billBean.getMn())) {					
//					BigDecimal bigDecimal = new BigDecimal(billBean.getMn()).setScale(2,BigDecimal.ROUND_HALF_UP);
					if(billBean.getType().equals("1")){	
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("充电消费");
					}else if(billBean.getType().equals("2")){
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("停车费");
					}else if(billBean.getType().equals("3")){				
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("信用还款");
					}else if(billBean.getType().equals("4")){				
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("充值");
					}else if(billBean.getType().equals("5")){				
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("发票快递费");
					}else if(billBean.getType().equals("6")){				
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("溢缴款");
					}else if(billBean.getType().equals("7")){				
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("资金划入");
					}else if(billBean.getType().equals("11")){				
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("充电消费退款 ");
					}else if(billBean.getType().equals("12")){				
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("停车费退款");
					}else if(billBean.getType().equals("13")){				
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("信用还款退款");
					}else if(billBean.getType().equals("14")){				
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("充值退款");
					}else if(billBean.getType().equals("15")){				
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("发票快递费退款 ");
					}else if(billBean.getType().equals("16")){				
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("溢缴款领回");
					}else if(billBean.getType().equals("17")){				
						tv_recharge_money.setText(billBean.getMn()+" 元");
						tv_bills_recharge_type.setText("资金划出 ");
					}
				}
		}
		return convertView;
	}

	private final class MyHold {
		TextView hold_tv_bills_details_data = null;//日期
		TextView hold_tv_bills_details_datatmie = null;//时间
		TextView hold_tv_recharge_money = null;//充值金额
		TextView hold_tv_bills_recharge_type = null;//类型
		public MyHold(TextView tv_bills_details_data,
				TextView tv_bills_details_datatmie,TextView tv_recharge_money,TextView tv_bills_recharge_type){
			this.hold_tv_bills_details_data = tv_bills_details_data;
			this.hold_tv_bills_details_datatmie = tv_bills_details_datatmie;
			this.hold_tv_recharge_money = tv_recharge_money;
			this.hold_tv_bills_recharge_type = tv_bills_recharge_type;
		}
	}
	
	
}
