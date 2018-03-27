package com.bm.wanma.adapter;

import java.util.ArrayList;
import com.bm.wanma.R;
import com.bm.wanma.entity.MyNewsFeedbackBean;
import com.bm.wanma.entity.MyNewsSystemBean;
import com.bm.wanma.utils.GetResourceUtil;
import com.bm.wanma.utils.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author cm
 * 我的反馈，listview适配器
 *
 */
public class MyNewsFeedbackAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<MyNewsFeedbackBean> mdata;
	private LayoutInflater inflater;
	private MyNewsFeedbackBean bean;
	
	public MyNewsFeedbackAdapter(Context context,ArrayList<MyNewsFeedbackBean> data) {
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
		
		TextView tv_status = null;
		TextView tv_content = null;
		TextView tv_time = null;
		TextView tv_backcontent = null;
		TextView tv_backtime = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.listview_item_mynews_feedback, null);
			tv_status = (TextView)convertView.findViewById(R.id.listview_item_mynews_feedback_status);
			tv_content = (TextView)convertView.findViewById(R.id.listview_item_mynews_feedback_content);
			tv_time = (TextView)convertView.findViewById(R.id.listview_item_mynews_feedback_time);
			tv_backcontent = (TextView)convertView.findViewById(R.id.listview_item_mynews_feedback_backcontent);
			tv_backtime = (TextView)convertView.findViewById(R.id.listview_item_mynews_feedback_backtime);
			
			convertView.setTag(new MyHold(tv_status,tv_content,tv_time,tv_backcontent,tv_backtime));
			
		}else {
			MyHold hold = (MyHold) convertView.getTag();
			tv_status = hold.hold_tv_name;
			tv_content = hold.hold_tv_content;
			tv_time = hold.hold_tv_time;
			tv_backcontent = hold.hold_tv_backcontent;
			tv_backtime = hold.hold_tv_backtime;
		}
		bean = mdata.get(position);
		if(bean != null){
			//反馈状态 0已入库1处理中 2处理完成
			if("0".equals(bean.getFeBa_Status())){
				tv_status.setText("未处理");
				tv_status.setTextColor(mContext.getResources().getColor(R.color.common_red));
				tv_backtime.setVisibility(View.GONE);
				tv_backtime.setText("");
				tv_backcontent.setVisibility(View.GONE);
				tv_backcontent.setText("");
			}else if("1".equals(bean.getFeBa_Status())){
				tv_status.setText("处理中");
				tv_status.setTextColor(mContext.getResources().getColor(R.color.common_red));
				/*tv_backtime.setVisibility(View.GONE);
				tv_backtime.setText("");
				tv_backcontent.setVisibility(View.GONE);
				tv_backcontent.setText("");*/
				String tempBTime = bean.getFeBa_Updatedate();
				tempBTime = Tools.parseDate(tempBTime, "yyyy-MM-dd HH:mm:ss", "MM/dd HH:mm");
				tv_backtime.setVisibility(View.VISIBLE);
				tv_backtime.setText(tempBTime);
				tv_backcontent.setVisibility(View.VISIBLE);
				tv_backcontent.setText("回复:  "+bean.getFeBa_Reason());
			}else if("2".equals(bean.getFeBa_Status())){
				tv_status.setText("已处理");
				tv_status.setTextColor(mContext.getResources().getColor(R.color.common_green));
				String tempBTime = bean.getFeBa_Updatedate();
				tempBTime = Tools.parseDate(tempBTime, "yyyy-MM-dd HH:mm:ss", "MM/dd HH:mm");
				tv_backtime.setVisibility(View.VISIBLE);
				tv_backtime.setText(tempBTime);
				tv_backcontent.setVisibility(View.VISIBLE);
				tv_backcontent.setText("回复:  "+bean.getFeBa_Reason());
			}
			tv_content.setText(""+bean.getFeBa_Content());
			String tempTime = bean.getFeBa_Createdate();
			tempTime = Tools.parseDate(tempTime, "yyyy-MM-dd HH:mm:ss", "MM/dd HH:mm");
			tv_time.setText(tempTime);
			
			
		}
		
		return convertView;
	}

	private final class MyHold {
		TextView hold_tv_name = null;
		TextView hold_tv_content = null;
		TextView hold_tv_time = null;
		TextView hold_tv_backcontent = null;
		TextView hold_tv_backtime = null;
		public MyHold(
				TextView tvname,TextView tvcontent,TextView tvtitme,TextView tvbackcontent,TextView tvbacktime){
			this.hold_tv_name = tvname;
			this.hold_tv_content = tvcontent;
			this.hold_tv_time = tvtitme;
			this.hold_tv_backcontent = tvbackcontent;
			this.hold_tv_backtime = tvbacktime;
		}
	}
	
	
}
