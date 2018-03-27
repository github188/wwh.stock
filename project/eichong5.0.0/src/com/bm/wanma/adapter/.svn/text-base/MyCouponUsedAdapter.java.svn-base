package com.bm.wanma.adapter;

import java.util.ArrayList;
import com.bm.wanma.R;
import com.bm.wanma.entity.CouponBean;
import com.bm.wanma.utils.GetResourceUtil;
import com.bm.wanma.utils.Tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author cm
 * 已用优惠券
 */
@SuppressLint("NewApi")
public class MyCouponUsedAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<CouponBean> mdata;
	private LayoutInflater inflater;
	private CouponBean bean;
	private Typeface typeFace;

	public MyCouponUsedAdapter(Context context,ArrayList<CouponBean> data) {
		this.mContext = context;
		this.mdata = data;
		inflater = LayoutInflater.from(mContext);
		typeFace = Typeface.createFromAsset(mContext.getAssets(),"fonts/impact.ttf");
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
			  MyItemHold itemHold = null;
	    	  if(convertView == null){
	    		    itemHold = new MyItemHold();
		    		convertView = inflater.inflate(R.layout.listview_coupon_used, null);
		    		itemHold.tv_value = (TextView) convertView.findViewById(R.id.coupon_value);
		    		itemHold.tv_condition = (TextView) convertView.findViewById(R.id.coupon_condition);
		    		itemHold.tv_time = (TextView) convertView.findViewById(R.id.coupon_timeout);
		    		itemHold.tv_type = (TextView) convertView.findViewById(R.id.coupon_type);
		    		itemHold.rl_value_bg = (RelativeLayout) convertView.findViewById(R.id.coupon_value_rl);
		    		itemHold.iv_status = (ImageView) convertView.findViewById(R.id.coupon_status);
		    		itemHold.tv_coupon_source = (TextView) convertView.findViewById(R.id.coupon_source);
		    		convertView.setTag(itemHold);
		    	}else {
		    		itemHold = (MyItemHold) convertView.getTag();
		    	}
	    	  bean = mdata.get(position);
	    	  itemHold.tv_value.setText(bean.getcValue()+"");
	    	  itemHold.tv_value.setTypeface(typeFace);
	    	  itemHold.tv_time.setText
			  ("有效期至:"+Tools.parseDate(bean.getTimeout(), "yyyy-MM-dd", "yyyy年MM月dd日"));
	    	  if("1".equals(bean.getLimitation())){//1-仅限交流电桩，2-仅限直流电装，3-不限充电桩
	    		  itemHold.rl_value_bg.setBackgroundResource(R.drawable.img_coupon_ac_bg);
	    		  itemHold.tv_type.setText("仅限交流");
	    		  itemHold.tv_type.setVisibility(View.VISIBLE);
	    		  itemHold.tv_type.setBackgroundResource(R.drawable.img_coupon_ac_type);
	    		  //券对应的活动1-注册送现金券活动，2-首次体验享现金券，3-邀请注册返现金券活动，4-邀请首次消费返现金券活动
	    		  if("2".equals(bean.getRule())){
	    			  itemHold.tv_condition.setText("新手体验券");
	    		  }else {
	    			  if(0 == Integer.valueOf(bean.getcCondition()) ){
	    				  itemHold.tv_condition.setText("无门槛现金券");
	    			  }else{
	    				  String condition = mContext.getResources().getString(R.string.coupon_condition, bean.getcCondition());
	    				  itemHold.tv_condition.setText(getRedString(condition));
	    			  }
	    		  }
	    	  }else if("2".equals(bean.getLimitation())){
	    		  itemHold.rl_value_bg.setBackgroundResource(R.drawable.img_coupon_dc_bg);
	    		  itemHold.tv_type.setText("仅限直流");
	    		  itemHold.tv_type.setVisibility(View.VISIBLE);
	    		  itemHold.tv_type.setBackgroundResource(R.drawable.img_coupon_dc_type);
	    		  //券对应的活动1-注册送现金券活动，2-首次体验享现金券，3-邀请注册返现金券活动，4-邀请首次消费返现金券活动
	    		  if("2".equals(bean.getRule())){
	    			  itemHold.tv_condition.setText("新手体验券");
	    		  }else {
	    			  if(Integer.valueOf(bean.getcCondition()) == 0){
	    				  itemHold.tv_condition.setText("无门槛现金券");
	    			  }else{
	    				  String condition = mContext.getResources().getString(R.string.coupon_condition, bean.getcCondition());
	    				  itemHold.tv_condition.setText(getRedString(condition));
	    			  }
	    		  }
	    	  }else if("3".equals(bean.getLimitation())){
	    		  itemHold.rl_value_bg.setBackgroundResource(R.drawable.img_coupon_common_bg);
	    		  itemHold.tv_type.setVisibility(View.GONE);
	    		  //券对应的活动1-注册送现金券活动，2-首次体验享现金券，3-邀请注册返现金券活动，4-邀请首次消费返现金券活动
	    		  if("2".equals(bean.getRule())){
	    			  itemHold.tv_condition.setText("新手体验券");
	    		  }else {
	    			  if(Integer.valueOf(bean.getcCondition()) == 0){
	    				  itemHold.tv_condition.setText("无门槛现金券");
	    			  }else{
	    				  String condition = mContext.getResources().getString(R.string.coupon_condition, bean.getcCondition());
	    				  itemHold.tv_condition.setText(getRedString(condition));
	    			  }
	    		  }
	    	  }
	    	  itemHold.tv_coupon_source.setText(""+bean.getcLabel());
		return convertView;
	}


	public class MyItemHold {
		TextView tv_value;
		TextView tv_condition;
		TextView tv_time;
		TextView tv_type;
		ImageView iv_status;
		TextView tv_coupon_source;
		RelativeLayout rl_value_bg;
		
	}
	private SpannableStringBuilder getRedString(String str){
	      int end = str.indexOf("使用");  
	       SpannableStringBuilder style = new SpannableStringBuilder(str);     
	       style.setSpan(new ForegroundColorSpan(Color.RED),1,end,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);      
		return style;
	}
	
}
