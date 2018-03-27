package com.bm.wanma.sortlistview;

import java.util.List;

import com.bm.wanma.R;
import com.bm.wanma.dialog.CancleBespokeDialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class EmergencyTelSortAdapter extends BaseAdapter implements SectionIndexer{
	private List<EmergencyTelSortModel> list = null;
	private Context mContext;
	private EmergencyTelSortModel itemBean;
	private CancleBespokeDialog cancleBespokeDialog;
	
	public EmergencyTelSortAdapter(Context mContext, List<EmergencyTelSortModel> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * @param list
	 */
	public void updateListView(List<EmergencyTelSortModel> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final EmergencyTelSortModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.sort_emergency_listview_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.listview_item_emergency_name);
			viewHolder.tvTel = (TextView) view.findViewById(R.id.listview_item_emergency_call);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		int section = getSectionForPosition(position);
		
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		itemBean = this.list.get(position);
		viewHolder.tvTitle.setText(itemBean.getName());
		viewHolder.tvTel.setText(itemBean.getTel());
		viewHolder.tvTel.setTag(itemBean.getTel());
		viewHolder.tvTel.setOnClickListener(new OnClickListener() {
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
		
		return view;

	}
	


	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		TextView tvTel;
	}

	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}