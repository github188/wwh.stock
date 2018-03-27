package com.bm.wanma.adapter;

import java.util.ArrayList;
import com.bm.wanma.R;
import com.bm.wanma.dialog.CancleBespokeDialog;
import com.bm.wanma.entity.EmergencyCallBean;
import com.bm.wanma.entity.MyDynamicListBean;
import com.bm.wanma.entity.MyNewsSystemBean;
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
 * 急救电话listview适配器
 *
 */
public class EmergencyCallAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<EmergencyCallBean> mdata;
	private LayoutInflater inflater;
	private EmergencyCallBean bean;
	private CancleBespokeDialog cancleBespokeDialog;
	
	public EmergencyCallAdapter(Context context,ArrayList<EmergencyCallBean> data) {
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
		TextView tv_call = null;
		View line = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_item_emergency_call, null);
			tv_name = (TextView)convertView.findViewById(R.id.listview_item_emergency_name);
			tv_call = (TextView)convertView.findViewById(R.id.listview_item_emergency_call);
			line = convertView.findViewById(R.id.listview_item_emergency_line);
			
			convertView.setTag(new MyHold(tv_name,tv_call,line));
			
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_name = hold.hold_tv_name;
			tv_call = hold.hold_tv_call;
			line = hold.hold_line;
		} 
		bean = mdata.get(position);
		if(bean != null){
			tv_name.setText(""+bean.getCom_name());
			tv_call.setText(""+bean.getCom_phone());
			tv_call.setTag(bean.getCom_phone());
			tv_call.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//联系客服
					//EmergencyCallBean tempBean = mdata.get(position);
					final String tel = (String) v.getTag();
					cancleBespokeDialog = new CancleBespokeDialog(mContext,tel);
					cancleBespokeDialog.setCancelable(false);
					cancleBespokeDialog.setButtonTitle("呼叫", "取消");
			        cancleBespokeDialog.setOnPositiveListener(new OnClickListener() {
				        @Override
				        public void onClick(View v) {
				        	//拨打电话
				        	Intent telintent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+tel));//直接拨打
				        	//Intent telintent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+TEL));
							telintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
							mContext.startActivity(telintent);
				        	cancleBespokeDialog.dismiss();
				        }
				    });
			        cancleBespokeDialog.setOnNegativeListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							cancleBespokeDialog.dismiss();
						}
					});
			        cancleBespokeDialog.show();
					
				}
			});
		}
		
		return convertView;
	}

	private final class MyHold {
		TextView hold_tv_name = null;
		TextView hold_tv_call = null;
	    View hold_line = null;
		public MyHold(
				TextView tvname,TextView tvcall,View vline){
			this.hold_tv_name = tvname;
			this.hold_tv_call = tvcall;
			this.hold_line = vline;
		}
	}
	
	
}
