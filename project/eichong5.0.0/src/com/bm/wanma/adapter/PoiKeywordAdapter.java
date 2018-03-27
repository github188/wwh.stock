package com.bm.wanma.adapter;

import java.util.ArrayList;
import com.bm.wanma.R;
import com.bm.wanma.dialog.CancleBespokeDialog;
import com.bm.wanma.entity.EmergencyCallBean;
import com.bm.wanma.entity.MyCollectBean;
import com.bm.wanma.entity.MyDynamicListBean;
import com.bm.wanma.entity.MyNewsSystemBean;
import com.bm.wanma.entity.PoiLatLngBean;
import com.bm.wanma.utils.Tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author cm
 * poi搜索界面，我的搜索地址listview适配器
 *
 */
public class PoiKeywordAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<PoiLatLngBean> mdata;
	private LayoutInflater inflater;
	private PoiLatLngBean itemBean;
	
	public PoiKeywordAdapter(Context context,ArrayList<PoiLatLngBean> data) {
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
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_item_search_point_collect, null);
			tv_name = (TextView)convertView.findViewById(R.id.listview_item_search_point_collect_name);
			convertView.setTag(new MyHold(tv_name));
			
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_name = hold.hold_tv_name;
		} 
		itemBean = mdata.get(position);
		tv_name.setText(""+itemBean.getTitle());
		
		return convertView;
	}

	private final class MyHold {
		TextView hold_tv_name = null;
		public MyHold(
				TextView tvname){
			this.hold_tv_name = tvname;
		}
	}
	
	
}
