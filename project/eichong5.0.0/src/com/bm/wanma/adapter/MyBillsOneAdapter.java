package com.bm.wanma.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.bm.wanma.R;
import com.bm.wanma.entity.BillOneBean;
import com.bm.wanma.ui.activity.MyBillDetailsActivity;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.utils.Tools;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author cm
 * 动态，listview适配器
 *
 */
public class MyBillsOneAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<BillOneBean> mdata;
	private BillOneBean billBean;
	
	public MyBillsOneAdapter(Context context,ArrayList<BillOneBean> data) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		TextView tv_datatmie = null;//时间
		TextView tv_examine_detail = null;//明细按钮
		TextView tv_electric_charge_money = null;//电费金额
		TextView tv_electricize_degree = null;//充多少电
		TextView tv_recharge_money = null;//充值多少钱
		TextView tv_favorable_money = null;//优惠多少
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_item_mybillsone, null);
			tv_datatmie = (TextView)convertView.findViewById(R.id.datatmie);
			tv_examine_detail = (TextView)convertView.findViewById(R.id.examine_detail);//明细按钮
			tv_electric_charge_money = (TextView)convertView.findViewById(R.id.electric_charge);
			tv_electricize_degree = (TextView)convertView.findViewById(R.id.electricize_degree);
			tv_recharge_money = (TextView)convertView.findViewById(R.id.recharge_money);
			tv_favorable_money = (TextView)convertView.findViewById(R.id.favorable_money);
			convertView.setTag(new MyHold(tv_datatmie,tv_examine_detail,tv_electric_charge_money,tv_electricize_degree,tv_recharge_money,tv_favorable_money));
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_datatmie = hold.hold_tv_datatmie;
			tv_examine_detail = hold.hold_tv_examine_detail;
			tv_electric_charge_money = hold.hold_tv_electric_charge_money;
			tv_electricize_degree = hold.hold_tv_electricize_degree;
			tv_recharge_money = hold.hold_tv_recharge_money;
			tv_favorable_money = hold.hold_tv_favorable_money;
		}
		billBean = mdata.get(position);
		
		if(billBean != null){
			tv_datatmie.setText(TimeUtil.parseDate(billBean.getMt(),
					"yyyy-MM", "M月"));//时间
			tv_electric_charge_money.setText(billBean.getMn()+" 元");//电费金额
			tv_electricize_degree.setText(billBean.getQt()+" kwh(度)");//充多少电
			if (!Tools.isEmptyString(billBean.getCz())) {				
				BigDecimal bigDecimal = new BigDecimal(billBean.getCz()).setScale(2,BigDecimal.ROUND_HALF_UP);
				tv_recharge_money.setText(bigDecimal.toString()+" 元");//充值多少钱
			}
			tv_favorable_money.setText(billBean.getCp() +" 元");//优惠多少
			tv_examine_detail.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(!Tools.isEmptyString(mdata.get(position).getMt())){
						Intent intent = new Intent();
						intent.putExtra("datatime", mdata.get(position).getMt());
						intent.setClass(mContext, MyBillDetailsActivity.class);
						mContext.startActivity(intent);						
					}
				}
			});
		}
		
		return convertView;
	}

	private final class MyHold {
		TextView hold_tv_datatmie = null;//明细按钮
		TextView hold_tv_examine_detail = null;//明细按钮
		TextView hold_tv_electric_charge_money = null;//电费金额
		TextView hold_tv_electricize_degree = null;//充多少电
		TextView hold_tv_recharge_money = null;//充值多少钱
		TextView hold_tv_favorable_money = null;//优惠多少
		public MyHold(TextView tv_datatmie,
				TextView tv_examine_detail,TextView tv_electric_charge_money,TextView tv_electricize_degree
				,TextView tv_recharge_money,TextView tv_favorable_money){
			this.hold_tv_datatmie = tv_datatmie;
			this.hold_tv_examine_detail = tv_examine_detail;
			this.hold_tv_electric_charge_money = tv_electric_charge_money;
			this.hold_tv_electricize_degree = tv_electricize_degree;
			this.hold_tv_recharge_money = tv_recharge_money;
			this.hold_tv_favorable_money = tv_favorable_money;
		}
	}
	
	
}
