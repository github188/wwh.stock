package com.bm.wanma.adapter;

import java.util.ArrayList;
import com.bm.wanma.R;
import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.dialog.CancleBespokeDialog;
import com.bm.wanma.entity.EmergencyCallBean;
import com.bm.wanma.entity.MyCollectBean;
import com.bm.wanma.entity.MyDynamicListBean;
import com.bm.wanma.entity.MyNewsSystemBean;
import com.bm.wanma.ui.activity.SearchPointActivity;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author cm
 * 搜索界面，搜索历史listview适配器
 *
 */
public class SearchMyHistoryAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> mdata;
	private LayoutInflater inflater;
	
	public SearchMyHistoryAdapter(Context context,ArrayList<String> data) {
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
		ImageButton ib_del = null;
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_item_search_point_history, null);
			tv_name = (TextView)convertView.findViewById(R.id.listview_item_search_point_history_name);
			ib_del = (ImageButton)convertView.findViewById(R.id.listview_item_search_point_history_del);
			convertView.setTag(new MyHold(tv_name,ib_del));
			
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_name = hold.hold_tv_name;
			ib_del = hold.hold_ib_del;
		} 
		if(!Tools.isEmptyString(mdata.get(position))){
			tv_name.setText(mdata.get(position));
			ib_del.setTag(position);
			ib_del.setOnClickListener(new OnClickListener() {
				//点击删除
				@Override
				public void onClick(View v) {
					int p = (Integer) v.getTag();
					mdata.remove(p);
					//String savehistory = PreferencesUtil.getStringPreferences(mContext, "searchHistory");
					StringBuilder sb = new StringBuilder();
					for(int i = 0;i<mdata.size();i++){
						  sb.insert(0, mdata.get(i) + ",");
					}
					 PreferencesUtil.setPreferences(mContext, "searchHistory", sb.toString());
					notifyDataSetChanged();
					mContext.sendBroadcast(new Intent(BroadcastUtil.BROADCAST_SEARCH_POINT_DELETE_HISTORY));
				}
			});
		}
	
		return convertView;
	}

	private final class MyHold {
		TextView hold_tv_name = null;
		ImageButton hold_ib_del = null;
		public MyHold(
				TextView tvname,ImageButton ibdel){
			this.hold_tv_name = tvname;
			this.hold_ib_del = ibdel;
		}
	}
	
	
}
