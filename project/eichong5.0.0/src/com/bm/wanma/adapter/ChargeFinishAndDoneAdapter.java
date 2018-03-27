package com.bm.wanma.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import com.bm.wanma.R;
import com.bm.wanma.entity.ChargeFinishAndDoneBean;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.utils.Tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author lyh
 * 已完成
 */
@SuppressLint("NewApi")
public class ChargeFinishAndDoneAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<ChargeFinishAndDoneBean> mdata;
	private LayoutInflater inflater;
	private ChargeFinishAndDoneBean bean;

	public ChargeFinishAndDoneAdapter(Context context,ArrayList<ChargeFinishAndDoneBean> data) {
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

	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv_charge_datatime = null;//日期
			TextView tv_charge_site= null;//地点
			TextView tv_charge_money = null;//金额
			TextView tv_charge_state = null;//状态
			TextView tv_money_title = null;
	    	  if(convertView == null){
		    		convertView = inflater.inflate(R.layout.listview_charge_done, null);
		    		tv_charge_datatime = (TextView) convertView.findViewById(R.id.datatime);
		    		tv_charge_site = (TextView) convertView.findViewById(R.id.site);
		    		tv_charge_money = (TextView) convertView.findViewById(R.id.money);
		    		tv_charge_state = (TextView) convertView.findViewById(R.id.state);
		    		tv_money_title = (TextView) convertView.findViewById(R.id.moneyleft);
		    		convertView.setTag(new MyHold(tv_charge_datatime, tv_charge_site, tv_charge_money, tv_charge_state,tv_money_title));
		    	}else {
		    		MyHold hold = (MyHold) convertView.getTag();
		    		tv_charge_datatime = hold.hold_tv_charge_datatime;
		    		tv_charge_site = hold.hold_tv_charge_site;
					tv_charge_money = hold.hold_tv_charge_money;
					tv_charge_state = hold.hold_tv_charge_state;
					tv_money_title = hold.hold_tv_money_title;
		    	}
	    	  bean = mdata.get(position);
	    	  if(bean != null){
	    		  
	    		  if(bean.getChOr_ChargingStatus().equals("1")){
	    			  tv_charge_datatime.setText(TimeUtil.getChargeDataTime(bean.getBegin_charge_time(), ""));//时间		
	    		  }else {
	    			  tv_charge_datatime.setText(TimeUtil.getChargeDataTime(bean.getBegin_charge_time(), bean.getEnd_charge_time()));//时间					
				}
	    		  
	    		  
	    		  tv_charge_site.setText(bean.getElPi_ElectricPileAddress());//地点
	    		  if(!Tools.isEmptyString(bean.getChRe_FrozenAmt())
	    				  &&bean.getChOr_ChargingStatus().equals("1")
  			  			&&Float.valueOf(bean.getChRe_FrozenAmt())>0
	    				  ){	    			  
	    			  tv_money_title.setText("预冻结金额");
	    			  tv_charge_money.setText(bean.getChRe_FrozenAmt()+" 元");//金额
	    			  
	    		  }else if(Tools.isEmptyString(bean.getChRe_FrozenAmt())&&bean.getChOr_ChargingStatus().equals("1")){	
	    			  tv_money_title.setText("预冻结金额");
	    			  tv_charge_money.setText("--");//金额
	    		 	  
	    		  }else if(!Tools.isEmptyString(bean.getChRe_FrozenAmt())
	    				  &&bean.getChOr_ChargingStatus().equals("17")
  			  			&&Float.valueOf(bean.getChRe_FrozenAmt())>0
	    				  ){	    			  
	    			  tv_money_title.setText("预冻结金额");
	    			  tv_charge_money.setText(bean.getChRe_FrozenAmt()+" 元");//金额
	    			  
	    		  }else if(Tools.isEmptyString(bean.getChRe_FrozenAmt())&&bean.getChOr_ChargingStatus().equals("17")){	
	    			  tv_money_title.setText("预冻结金额");
	    			  tv_charge_money.setText("--");//金额
	    		 	  
	    		  }else if(bean.getChOr_ChargingStatus().equals("1")){	
	    			  tv_money_title.setText("预冻结金额");
	    			  tv_charge_money.setText("--");//金额
	    		 	  
	    		  }else if(!Tools.isEmptyString(bean.getChRe_FrozenAmt())&&bean.getChOr_ChargingStatus().equals("5")){	
	    			  tv_money_title.setText("预冻结金额");
	    			  tv_charge_money.setText(bean.getChRe_FrozenAmt()+" 元");//金额
	    			  
	    		  }else{
	    			  	if (!Tools.isEmptyString(bean.getChOr_ServiceMoney())
	    			  			&&!Tools.isEmptyString(bean.getChOr_ChargeMoney())
	    			  			&&!Tools.isEmptyString(bean.getCouMoney())
	    			  			&&Float.valueOf(bean.getCouMoney())>0
	    			  			) {
		    				  BigDecimal servicemoney = new BigDecimal(bean.getChOr_ServiceMoney()).setScale(2,BigDecimal.ROUND_HALF_UP);
		    				  BigDecimal chargemoney = new BigDecimal(bean.getChOr_ChargeMoney()).setScale(2,BigDecimal.ROUND_HALF_UP);
		    				  BigDecimal coumone = new BigDecimal(bean.getCouMoney()).setScale(2,BigDecimal.ROUND_HALF_UP);
 
		    				  tv_charge_money.setText(((servicemoney.add(chargemoney)).subtract(coumone)).toString()+" 元");
		    			}else if (!Tools.isEmptyString(bean.getChOr_ServiceMoney())&&!Tools.isEmptyString(bean.getChOr_ChargeMoney())) {
	    				  BigDecimal bigDecimal = new BigDecimal(bean.getChOr_ServiceMoney()).setScale(2,BigDecimal.ROUND_HALF_UP);
	    				  BigDecimal bigDecimal2 = new BigDecimal(bean.getChOr_ChargeMoney()).setScale(2,BigDecimal.ROUND_HALF_UP);
//	    				  tv_charge_money.setText((Float.parseFloat(bean.getChOr_ServiceMoney()) + Float.parseFloat(bean.getChOr_ChargeMoney()))+"元");//金额
	    				  
	    				  tv_charge_money.setText(bigDecimal.add(bigDecimal2).toString()+" 元");
	    			  	}else  {							
	    			  		tv_charge_money.setText(bean.getChOr_ChargeMoney()+" 元");//金额
						}
				}
	    		  
	    		  //订单状态 1：待支付 2：支付成功 3 充电完成，未支付 （1状态下需要支付）4：异常订单 5：临时结算
	    		  if(bean.getChOr_ChargingStatus().equals("1")){
	    			  tv_charge_state.setText("充电中");//状态
	    			  tv_charge_state.setTextColor(Color.parseColor("#ff7d00"));
	    		  }else if(bean.getChOr_ChargingStatus().equals("17")){
	    			  tv_charge_state.setText("等待中");//状态
	    			  tv_charge_state.setTextColor(Color.parseColor("#ff7d00"));
	    		  }else if(bean.getChOr_ChargingStatus().equals("2")){
	    			  tv_charge_state.setTextColor(Color.parseColor("#666666"));
	    			  tv_charge_state.setText("已完成");//状态
	    		  }else if(bean.getChOr_ChargingStatus().equals("3")){
	    			  tv_charge_state.setTextColor(Color.parseColor("#333333"));
	    			  tv_charge_state.setText("未付款");//状态
	    		  }else if(bean.getChOr_ChargingStatus().equals("4")){
	    			  tv_charge_state.setText("已退款");//状态
	    			  tv_charge_state.setTextColor(Color.parseColor("#666666"));
	    		  }else if(bean.getChOr_ChargingStatus().equals("5")){
	    			  tv_charge_state.setText("临时结算");//状态
	    			  tv_charge_state.setTextColor(Color.parseColor("#333333"));
	    		  }
	    		  
	    	  }
		return convertView;
	}


	private  class MyHold {
		TextView hold_tv_charge_datatime = null;//日期
		TextView hold_tv_charge_site= null;//地点
		TextView hold_tv_charge_money = null;//金额
		TextView hold_tv_charge_state = null;//状态
		TextView hold_tv_money_title = null;
		public MyHold(TextView tv_charge_datatime,
				TextView tv_charge_site,TextView tv_charge_money,TextView tv_charge_state,TextView tv_money_title){
			this.hold_tv_charge_datatime = tv_charge_datatime;
			this.hold_tv_charge_site = tv_charge_site;
			this.hold_tv_charge_money = tv_charge_money;
			this.hold_tv_charge_state = tv_charge_state;
			this.hold_tv_money_title = tv_money_title;
		}
	}
	
}
