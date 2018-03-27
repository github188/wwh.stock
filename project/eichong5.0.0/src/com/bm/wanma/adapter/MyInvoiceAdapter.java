package com.bm.wanma.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bm.wanma.R;
import com.bm.wanma.entity.EnableInvoiceBean;
import com.bm.wanma.entity.EnableInvoiceSectionBean;
import com.bm.wanma.entity.InvoiceCheckHold;
import com.bm.wanma.utils.TimeUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * @author cm
 * 可开发票列表
 */
public class MyInvoiceAdapter extends BaseAdapter {
	private Context mContext;
	private List<EnableInvoiceSectionBean> mdata;
	private LayoutInflater inflater;
	private EnableInvoiceSectionBean sectionbean;
	private EnableInvoiceBean bean;
	//类型--内容
	public static final int TYPE_ITEM = 0;
	//类型--顶部悬浮的标题
	public static final int TYPE_SECTION = 1;
	private static final int TYPE_COUNT = 2;//item类型的总数
	private int currentType;//当前item类型
	private Map<Integer, Boolean> isCheckedMap;
	
	@SuppressLint("UseSparseArrays")
	public MyInvoiceAdapter(Context context,List<EnableInvoiceSectionBean> data) {
		this.mContext = context;
		this.mdata = data;
		inflater = LayoutInflater.from(mContext);
		isCheckedMap = new HashMap<Integer, Boolean>();
		for(int i=0;i<mdata.size();i++){
			isCheckedMap.put(i, false);
		}
	}
	
	public Map<Integer, Boolean> setCheckAll(boolean isAll){
		for(int i=0;i<mdata.size();i++){
			isCheckedMap.put(i, isAll);
		}
		notifyDataSetChanged();
		return isCheckedMap;
	}
	public Map<Integer, Boolean> setCheckAt(int p,boolean is){
		isCheckedMap.put(p, is);
		return isCheckedMap;
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
	public int getItemViewType(int position) {
		if(1 == mdata.get(position).getType()){
			return TYPE_SECTION;
			
		}else if(0 == mdata.get(position).getType()){
			return TYPE_ITEM;
		}else return 100;
	}
	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    sectionbean = mdata.get(position);
	    bean = sectionbean.getBillBean();
	    currentType = getItemViewType(position);
	    if(currentType == TYPE_SECTION){
	    	MySectionHold sectionHold = null;
	    	if(convertView == null){
	    		sectionHold = new MySectionHold();
	    		convertView = inflater.inflate(R.layout.listview_item_myinvoice_no, null);
	    		sectionHold.tv_time = (TextView) convertView.findViewById(R.id.listview_mybill_title);
	    		convertView.setTag(sectionHold);
	    	}else {
	    		sectionHold = (MySectionHold) convertView.getTag();
	    	}
	    	sectionHold.tv_time.setText(TimeUtil.parseDate(bean.getpTime(), "yyyy-MM", "yyyy年MM月"));
	    }else if(currentType == TYPE_ITEM){
	    	  InvoiceCheckHold itemHold = null;
	    	  if(convertView == null){
	    		    itemHold = new InvoiceCheckHold();
		    		convertView = inflater.inflate(R.layout.listview_item_myinvoice, null);
		    		itemHold.tv_title = (TextView) convertView.findViewById(R.id.listview_mybill_title);
		    		itemHold.tv_time = (TextView) convertView.findViewById(R.id.listview_mybill_time);
		    		itemHold.tv_money = (TextView) convertView.findViewById(R.id.listview_mybill_money);
		    		itemHold.cb = (CheckBox) convertView.findViewById(R.id.invoice_item_check);
		    		itemHold.line = convertView.findViewById(R.id.listview_line);
		    		convertView.setTag(itemHold);
		    	}else {
		    		itemHold = (InvoiceCheckHold) convertView.getTag();
		    	}
	    	  itemHold.tv_title.setText(bean.getpContent());
	    	  itemHold.tv_money.setText(String.format(mContext.getResources().getString(R.string.invoice_consume),
	    			  bean.getpMoney()));
	    	  itemHold.tv_time.setText(TimeUtil.parseDate(bean.getpTime(), "yyyy-MM-dd HH:mm:SS", "MM月dd日 HH:mm"));
	    	  
	    	  itemHold.cb.setChecked(isCheckedMap.get(position));
	    	  
	    	  if(position+2 < mdata.size() && TYPE_SECTION == getItemViewType(position+1)){
	    		  itemHold.line.setVisibility(View.GONE);
	    	  }else {
	    		  itemHold.line.setVisibility(View.VISIBLE);
	    	  }
	    
	    }
	    
		return convertView;
	}


	/*public class MyItemHold {
		TextView tv_title ;
		TextView tv_money;
		TextView tv_time;
		CheckBox cb;
		View line;
	}*/
	class MySectionHold {
		TextView tv_time;
	}
	
}
