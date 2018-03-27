package com.bm.wanma.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.wanma.R;
import com.bm.wanma.entity.EverydayGetBean;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.TimeUtil;
import com.bm.wanma.utils.Tools;
/**
 * 首页功能按钮的容器
 * @author lyh
 *
 */
public class EverydayGetAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<EverydayGetBean> everydayGetBeans;
	private EverydayGetBean everydayGetBean;
	public EverydayGetAdapter(Context context,ArrayList<EverydayGetBean> everydayGetBeans) {
		this.mContext = context;
		this.everydayGetBeans = everydayGetBeans;
	}
    @Override  
    public int getCount() {  
        return everydayGetBeans.size();  
    }  

    @Override  
    public Object getItem(int position) {  
        return everydayGetBeans.get(position);  
    }  

    @Override  
    public long getItemId(int position) {  
        return position;  
    }  

    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        ViewHolder viewHolder;  
        if (convertView == null) {  
            convertView = View.inflate(mContext, R.layout.everyday_item_gride, null);  
            viewHolder = new ViewHolder();  
            viewHolder.btnKey = (LinearLayout) convertView.findViewById(R.id.keybord_btn_keys);  
            viewHolder.btndata_text = (TextView) convertView.findViewById(R.id.keybord_btn_data_text);  
            viewHolder.btnKey_t_integrate = (TextView) convertView.findViewById(R.id.t_integrate);  
            viewHolder.btnKey_img = (RelativeLayout) convertView.findViewById(R.id.delete_to);  
            viewHolder.bt = (ImageView) convertView.findViewById(R.id.bt);  
            convertView.setTag(viewHolder);  
        } else {  
            viewHolder = (ViewHolder) convertView.getTag();  
        }  
        everydayGetBean = everydayGetBeans.get(position);
       if (everydayGetBean!=null) {
    	   viewHolder.btnKey_t_integrate.setText(everydayGetBean.getPoint());
    	   if (position==0) {
    		   viewHolder.btnKey_t_integrate.setTextColor(Color.parseColor("#ffffff"));
    		   viewHolder.btnKey_img.setBackgroundResource(R.drawable.common_button_shape_activate_recharge);
    		   viewHolder.btndata_text.setTextColor(Color.parseColor("#333333"));
		}
    	  String s = PreferencesUtil.getStringPreferences(mContext,
   				"usinBirthdate");

    	   if (!Tools.isEmptyString(s)&&(TimeUtil.parseDate(everydayGetBean.getDate(),
					  "yyyy-MM-dd", "MM-dd")).equals(TimeUtil.parseDate(s,
	    	    			  "yyyy-MM-dd", "MM-dd"))) {
    		   viewHolder.bt.setVisibility(View.VISIBLE);
    	   	}else{
    	   		viewHolder.bt.setVisibility(View.INVISIBLE);
    	   	}
    	   if (position!=0) {			
    		   viewHolder.btndata_text.setText(TimeUtil.parseDate(everydayGetBean.getDate(),
    				   "yyyy-MM-dd", "MM-dd"));
		}
    	   
       }
      
        return convertView;  
    }
    /** 
     * 存放控件 
     */  
    public final class ViewHolder {  
        public LinearLayout btnKey;  
        public TextView btndata_text,btnKey_t_integrate;
        public RelativeLayout btnKey_img; 
        public ImageView  bt;
    }
    
   
}
