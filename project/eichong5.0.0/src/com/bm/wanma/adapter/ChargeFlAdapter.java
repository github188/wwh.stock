package com.bm.wanma.adapter;

import java.util.ArrayList;
import java.util.Map;

import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.wanma.R;
import com.bm.wanma.entity.FunctionButtonBean;
import com.bm.wanma.entity.RateBean;
import com.bm.wanma.net.FunctionPictureUpload;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.ProjectApplication;
import com.bm.wanma.utils.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 首页功能按钮的容器
 * @author lyh
 *
 */
public class ChargeFlAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<RateBean> details;
	public ChargeFlAdapter(Context context,ArrayList<RateBean> details) {
		this.mContext = context;
		this.details = details;
	}
    @Override  
    public int getCount() {  
        return details.size();  
    }  

    @Override  
    public Object getItem(int position) {  
        return details.get(position);  
    }  

    @Override  
    public long getItemId(int position) {  
        return position;  
    }  

    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        ViewHolder viewHolder;  
        if (convertView == null) {  
            convertView = View.inflate(mContext, R.layout.charge_fl_item_gride, null);  
            viewHolder = new ViewHolder();  
            viewHolder.charge_fl_text = (TextView) convertView.findViewById(R.id.charge_fl_text);  
            convertView.setTag(viewHolder);  
        } else {  
            viewHolder = (ViewHolder) convertView.getTag();  
        }  
        
        viewHolder.charge_fl_text.setText(details.get(position).getFl()+"元/千瓦时x"+details.get(position).getQt()+"千瓦");//电费价格1
        return convertView;  
    }
    /** 
     * 存放控件 
     */  
    public final class ViewHolder {  
        public TextView charge_fl_text;
    }
    
  
}
