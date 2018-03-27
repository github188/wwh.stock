package com.bm.wanma.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bm.wanma.R;

public class ShowCustomKeybordUtil {
	
	private Context context; 
	private PopupWindow popupWindow;
	private GridView gridView;
	private ArrayList<Map<String, String>> valueList = new ArrayList<Map<String,String>>();
	private int currentIndex = -1;//用于记录当前输入密码格位置

	public ShowCustomKeybordUtil(Context ctx, EditText edit) {
		 // 一个自定义的布局，作为显示的内容
		context = ctx;
        View contentView = LayoutInflater.from(ctx).inflate(
                R.layout.popup_custom_keybord, null);
        gridView = (GridView)contentView.findViewById(R.id.custom_keybord);  
        setView();
        popupWindow = new PopupWindow(contentView,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        
	}
	
	private void setView() {  
        /* 初始化按钮上应该显示的数字 */  
        for (int i = 1; i < 13; i++) {  
            Map<String, String> map = new HashMap<String, String>();  
            if (i < 10) {  
                map.put("name", String.valueOf(i));  
            } else if (i == 10) {  
                map.put("name", "");  
            } else if (i == 12) {  
                map.put("name", "<<-");  
            } else if (i == 11) {  
                map.put("name", String.valueOf(0));  
            }  
            valueList.add(map);  
        }  
        gridView.setAdapter(adapter);  
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
                if (position < 11 && position != 9) {    //点击0~9按钮  
                    if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界  
                       // tvList[++currentIndex].setText(valueList.get(position).get("name"));  
                    }  
                } else {  
                    if (position == 11) {      //点击退格键  
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界  
                           // tvList[currentIndex--].setText("");  
                        }  
                    }  
                }  
            }  
        });  
        
        
        
    }  
        

        
	public void showKeybord(View v){
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		popupWindow.showAsDropDown(v);
	}
	
	
	 //GrideView的适配器  
    BaseAdapter adapter = new BaseAdapter() {  
        @Override  
        public int getCount() {  
            return valueList.size();  
        }  
  
        @Override  
        public Object getItem(int position) {  
            return valueList.get(position);  
        }  
  
        @Override  
        public long getItemId(int position) {  
            return position;  
        }  
  
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
            ViewHolder viewHolder;  
            if (convertView == null) {  
                convertView = View.inflate(context, R.layout.popup_custom_keybord_item_gride, null);  
                viewHolder = new ViewHolder();  
                viewHolder.btnKey = (TextView) convertView.findViewById(R.id.keybord_btn_keys);  
                convertView.setTag(viewHolder);  
            } else {  
                viewHolder = (ViewHolder) convertView.getTag();  
            }  
            viewHolder.btnKey.setText(valueList.get(position).get("name"));  
            if(position == 9){  
                viewHolder.btnKey.setBackgroundResource(R.drawable.custom_input_key_del);  
                viewHolder.btnKey.setEnabled(false);  
            }  
            if(position == 11){  
                viewHolder.btnKey.setBackgroundResource(R.drawable.custom_input_key_del);  
            }  
  
            return convertView;  
        }  
    };  
  
    /** 
     * 存放控件 
     */  
    public final class ViewHolder {  
        public TextView btnKey;  
    }  
	
	
}
