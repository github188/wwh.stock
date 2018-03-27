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
public class FunctionButtonAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<FunctionButtonBean> buttonBeans;
	public FunctionButtonAdapter(Context context,ArrayList<FunctionButtonBean> buttonBeans) {
		this.mContext = context;
		this.buttonBeans = buttonBeans;
	}
    @Override  
    public int getCount() {  
        return buttonBeans.size();  
    }  

    @Override  
    public Object getItem(int position) {  
        return buttonBeans.get(position);  
    }  

    @Override  
    public long getItemId(int position) {  
        return position;  
    }  

    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        ViewHolder viewHolder;  
        if (convertView == null) {  
            convertView = View.inflate(mContext, R.layout.function_button_item_gride, null);  
            viewHolder = new ViewHolder();  
            viewHolder.btnKey = (LinearLayout) convertView.findViewById(R.id.keybord_btn_keys);  
            viewHolder.btnKey_text = (TextView) convertView.findViewById(R.id.keybord_btn_keys_text);  
            viewHolder.btnKey_img = (ImageView) convertView.findViewById(R.id.delete_to);  
            convertView.setTag(viewHolder);  
        } else {  
            viewHolder = (ViewHolder) convertView.getTag();  
        }  
        try {
            if(Tools.isPicture(buttonBeans.get(position).getPkAblId()+"button")
            		&&!Tools.isEmptyString(PreferencesUtil.getStringPreferences(mContext, buttonBeans.get(position).getPkAblId()+"Id"))
            		&&PreferencesUtil.getStringPreferences(mContext, buttonBeans.get(position).getPkAblId()+"Id")
            		.equals(buttonBeans.get(position).getPkAblId())
            		&&!Tools.isEmptyString(PreferencesUtil.getStringPreferences(mContext, buttonBeans.get(position).getPkAblId()+"Update"))
            		&&PreferencesUtil.getStringPreferences(mContext, buttonBeans.get(position).getPkAblId()+"Update")
            		.equals(buttonBeans.get(position).getAblUpdatedate())
            		){
            	ImageLoader.getInstance().displayImage("file:///"+Tools.advertisementpath + buttonBeans.get(position).getPkAblId()+"button", viewHolder.btnKey_img , options);
//            		viewHolder.btnKey_img.setImageBitmap(BitmapFactory.decodeFile(Tools.advertisementpath + buttonBeans.get(position).getAblAction()+"button"));
            		viewHolder.btnKey_text.setText(PreferencesUtil.getStringPreferences(mContext, buttonBeans.get(position).getPkAblId()+"ablName"));

    		} else{
    			if (!Tools.isEmptyString(buttonBeans.get(position).getPkAblId())&&!buttonBeans.get(position).getPkAblId().equals("0")) {
    				ImageLoader.getInstance().displayImage(buttonBeans.get(position).getButtonPicUrl(), viewHolder.btnKey_img , options);
    				viewHolder.btnKey_text.setText(buttonBeans.get(position).getAblName().substring(0, 
    						buttonBeans.get(position).getAblName().length()>4?4:buttonBeans.get(position).getAblName().length()));
    			}else {
    				if (!ProjectApplication.getButtonType()||buttonBeans.get(position).getPkAblId().equals("0")) {

    				if (buttonBeans.get(position).getAblAction().equals("1")) {
    					viewHolder.btnKey_img.setImageBitmap(BitmapFactory.decodeStream(
    							mContext.getResources().openRawResource(R.drawable.img_scan_home)));
    				}else if (buttonBeans.get(position).getAblAction().equals("3")) {
    					viewHolder.btnKey_img.setImageBitmap(BitmapFactory.decodeStream(
    							mContext.getResources().openRawResource(R.drawable.img_record_home)));
    				}else if (buttonBeans.get(position).getAblAction().equals("2")) {
    					viewHolder.btnKey_img.setImageBitmap(BitmapFactory.decodeStream(
    							mContext.getResources().openRawResource(R.drawable.img_map_home)));
    				}else if (buttonBeans.get(position).getAblAction().equals("4")) {
    					viewHolder.btnKey_img.setImageBitmap(BitmapFactory.decodeStream(
    							mContext.getResources().openRawResource(R.drawable.img_top_up_home)));
    				}
    				viewHolder.btnKey_text.setText(buttonBeans.get(position).getAblName());
    				}else if (!buttonBeans.get(position).getAblAction().equals("5")) {	
//    					"file:///"+Tools.advertisementpath+imageCarouselBean.getPkBlId()+"banner"
    					ImageLoader.getInstance().displayImage("file:///"+Tools.advertisementpath + buttonBeans.get(position).getPkAblId()+"button", viewHolder.btnKey_img , options);
//    						viewHolder.btnKey_img.setImageBitmap(BitmapFactory.decodeFile(Tools.advertisementpath + buttonBeans.get(position).getAblAction()+"button"));
    						viewHolder.btnKey_text.setText(PreferencesUtil.getStringPreferences(mContext, buttonBeans.get(position).getPkAblId()+"ablName"));					
    				}
    			}
    		}
            
            if (buttonBeans.get(position).getAblAction().equals("5")) {
    			viewHolder.btnKey_img.setImageBitmap(BitmapFactory.decodeStream(mContext.getResources().openRawResource(R.drawable.img_all_home)));
            	viewHolder.btnKey_text.setText("全部");
    		}
		} catch (Exception e) {
			
		}
      
        return convertView;  
    }
    /** 
     * 存放控件 
     */  
    public final class ViewHolder {  
        public LinearLayout btnKey;  
        public TextView btnKey_text;
        public ImageView btnKey_img; 
    }
    
    DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.img_moren) // 设置图片下载期间显示的图片
			.showImageForEmptyUri(R.drawable.img_moren) // 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.img_moren) // 设置图片加载或解码过程中发生错误显示的图片
			.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
			.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
			// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
			.build(); // 创建配置过得DisplayImageOption对象
}
