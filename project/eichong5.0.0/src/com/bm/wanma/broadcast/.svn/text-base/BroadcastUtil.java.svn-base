package com.bm.wanma.broadcast;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * 广播工具，发送广播。
 * cm
 */
public class BroadcastUtil {

	 // 更新充电点完成
    public static final String BROADCAST_UPDATAPOINT = "com.bm.wanma.updata_point";
    // 搜索完成，跳转到地图
    public static final String BROADCAST_SEARCH_POINT = "com.bm.wanma.search_point";
    // 预约结束
    public static final String BROADCAST_Bespoke_Finish = "com.bm.wanma.bespoke.finish";
    //预约成功
    public static final String BROADCAST_Bespoke_OK = "com.bm.wanma.bespoke.ok";
    // 取消预约成功
    public static final String BROADCAST_Bespoke_CANCLE = "com.bm.wanma.bespoke.cancle";
   //预约成功-地图出现按钮
    public static final String BROADCAST_Bespoke_OK_VISIBLE = "com.bm.wanma.bespoke.ok.visible";
    // 搜索--删除搜索历史
    public static final String BROADCAST_SEARCH_POINT_DELETE_HISTORY = "com.bm.wanma.delete.history";
    //用户资料修改成功
    public static final String BROADCAST_Modify_UserInfo = "com.bm.wanma.getuserinfo";
    //用户资料修改成功,更新筛选结果
    public static final String BROADCAST_MODIFY_USERINFO_UPDATE = "com.bm.wanma.getuserinfo_update";
    //用户登录
    public static final String BROADCAST_LOGIN = "com.bm.wanma.login_success";
    //用户登录  
    public static final String BROADCAST_REGISTER_FINISH = "com.bm.wanma.register_finish";
    //用户强制退出
    public static final String BROADCAST_Force_Offline = "com.bm.wanma.force_offline";
    //充电结束-地图按钮消失
    public static final String BROADCAST_Charge_CANCLE = "com.bm.wanma.charge.cancle";
    //有充电订单
    public static final String BROADCAST_Charge_Ing = "com.bm.wanma.charge.ing";
    //tcp网络断掉
    public static final String BROADCAST_TCP_NETWORK = "com.bm.wanma.tcp.network";
  //等待充电变正在充电
    public static final String BROADCAST_Charge_CHANGE = "com.bm.wanma.charge.change";
    

    public static final void sendBroadcast(Context context, String action) {
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
        lbm.sendBroadcast(new Intent(action));
    }

    public static final void sendBroadcast(Context context, Intent intent) {
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
        lbm.sendBroadcast(intent);
    }

}
