package com.bm.wanma.socket;

public class SocketConstant {

	public static final byte HEAD_FLAG1 = 0x45;//E
	public static final byte HEAD_FLAG2 = 0x43;//C
	public static final int PHONE_SENDBUFFER = 256;//
	public static final short CMD_TYPE_CONNECT = 21;//连接充电桩  应答-是  --之前是1，现在替换21
	public static final short CMD_TYPE_HEART = 2;//心跳  是
	public static final short CMD_TYPE_OPEN_LED = 3;//闪LED  否
	public static final short CMD_TYPE_CLOSE_LED = 4;//关LED  否
	public static final short CMD_TYPE_CALL_PILL = 5;//呼叫充电桩  否
	public static final short CMD_TYPE_STOP_CALL_PILE = 6;//停止呼叫充电桩  否
	public static final short CMD_TYPE_UNLOCK = 7;//降地锁 否
	public static final short CMD_TYPE_CANCEL_BESPOKE = 8;//取消预约 是
	public static final short CMD_TYPE_KAI_GAI = 9;//开盖  否
	public static final short CMD_TYPE_START_CHARGE = 10;//充电 是
	public static final short CMD_TYPE_STOP_CHARGE = 11;//停止充电 是
	public static final short CMD_TYPE_CONSUME_RECORD = 12;//消费记录  推送
	public static final short CMD_TYPE_DC_SELF_CHECK = 13;//直流自检  服务器推送
	public static final short CMD_TYPE_CHARGE_EVENT = 14;//充电事件  服务器推送
	public static final short CMD_TYPE_REAL_DATA = 101;//实时数据   服务器推送
	public static final short CMD_TYPE_YX = 102;//遥信数据--桩断网  服务器推送
	public static final short CMD_TYPE_GUN = 103;//枪状态
	public static final short CMD_TYPE_CONNECT_AWAIT = 104;//等待
	
	
	public static final short CMD_TYPE_AC_YC = 103;//交流遥测  服务器推送
	public static final short CMD_TYPE_AC_BC_YC = 104;//交流变长遥测  服务器推送
	public static final short CMD_TYPE_DC_REAL_DATA = 201;//直流实时数据(整包)   服务器推送
	public static final short CMD_TYPE_DC_YX = 202;//直流遥信  服务器推送
	public static final short CMD_TYPE_DC_YC = 203;//直流流遥测  服务器推送
	public static final short CMD_TYPE_DC_BC_YC = 204;//直流流变长遥测  服务器推送
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
