package com.bm.wanma.socket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.bm.wanma.broadcast.BroadcastUtil;
import com.bm.wanma.net.Protocol;
import com.bm.wanma.ui.activity.ITcpCallBack;
import com.bm.wanma.utils.LogUtil;
import com.bm.wanma.utils.PreferencesUtil;
import com.bm.wanma.utils.Tools;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class TCPSocketManager {

	private Context mcContext;
	private static TCPSocketManager mTcpSocketManager = null;

	private final int STATE_OPEN = 1;// socket打开
	private final int STATE_CLOSE = 1 << 1;// socket关闭
	private final int STATE_CONNECT_START = 1 << 2;// 开始连接server
	private final int STATE_CONNECT_SUCCESS = 1 << 3;// 连接成功
	private final int STATE_CONNECT_FAILED = 1 << 4;// 连接失败
	private final int STATE_CONNECT_WAIT = 1 << 5;// 等待连接
	private int state = STATE_CONNECT_START;
	private Socket socket = null;
	private OutputStream outStream = null;
	private InputStream inStream = null;

	/** 心跳检测时间 */
	private static final long HEART_BEAT_RATE = 10 * 1000;
	private long sendHeartBeatTime = 0L;
	private Thread connThread = null;
	private Thread sendHeartThread = null;
	private Thread readPacketThread = null;
	private Thread responsePacketThread = null;
	private byte[] response;
	private int connCount = 0;

	private String pileNum;
	private byte headNum;
	private int pileType,gunState;
	private ITcpCallBack mTcpCallBack;
	private boolean isFirstNotify;

	public TCPSocketManager(Context c) {
		this.mcContext = c;

	}

	public synchronized static TCPSocketManager getInstance(Context c) {

		if (mTcpSocketManager == null) {
					mTcpSocketManager = new TCPSocketManager(c);
		}

		return mTcpSocketManager;

	}

	public void setTcpCallback(ITcpCallBack callBack) {
		this.mTcpCallBack = callBack;
	}

	// 处理包数据
	private void handlePacketData(ByteArrayInputStream in) throws IOException {
		int reason = StreamUtil.readByte(in);
		short cmdtype = StreamUtil.readShort(in);
		LogUtil.i("cm_socket", "传送原因" + reason + "指令编码" + cmdtype);
		switch (cmdtype) {
		case SocketConstant.CMD_TYPE_CONNECT:
			// 连接充电桩，响应
			int successflag = StreamUtil.readByte(in);
			short errorcode = StreamUtil.readShort(in);
			if (0 == successflag) {
				LogUtil.i("cm_socket", "连接充电桩失败原因" + errorcode);
			} else if (1 == successflag) {
				int headState = StreamUtil.readByte(in);
				int type = 1;
				try {
					type = StreamUtil.readByte(in);
				} catch (Exception e) {
					e.printStackTrace();
				}
				LogUtil.i("cm_socket", "连接充电桩成功,充电枪状态" + headState + "交直流"
						+ type);
				sendHeartThread = new Thread(new SendHeartRunnable());
				sendHeartThread.start();
			}

			break;
		case SocketConstant.CMD_TYPE_HEART:
			// 心跳
			LogUtil.i("cm_socket", "接收心跳响应");
			break;

		case SocketConstant.CMD_TYPE_CANCEL_BESPOKE:
			// 取消预约
			LogUtil.i("cm_socket", "取消预约");
			break;
		case SocketConstant.CMD_TYPE_CONNECT_AWAIT:
			// 开始等待
			int awaitState = StreamUtil.readByte(in);
			int time = StreamUtil.readUB3(in);
			LogUtil.i("cm_socket", "桩状态 :" + awaitState);
			LogUtil.i("cm_socket", "充电输出电压" + time);
			break;
		case SocketConstant.CMD_TYPE_REAL_DATA:

			int state = StreamUtil.readByte(in);
			short chargeTime = StreamUtil.readShort(in);
			short dianya = StreamUtil.readShort(in);
			short dianliu = StreamUtil.readShort(in);
			int diandu = StreamUtil.readInt(in);
			short feilv = StreamUtil.readShort(in);
			int yuchong = StreamUtil.readInt(in);
			int yichongjine = StreamUtil.readInt(in);
			int soc = StreamUtil.readByte(in);
			int fushu = StreamUtil.readInt(in);
			int gaojing = StreamUtil.readInt(in);
			// 实时数据包
			LogUtil.i("cm_socket", "工作状态" + state);
			LogUtil.i("cm_socket", "累计充电时间" + chargeTime);
			LogUtil.i("cm_socket", "充电输出电压" + dianya);
			LogUtil.i("cm_socket", "充电输出电流" + (float) dianliu / 100);
			LogUtil.i("cm_socket", "充电电度" + (float) diandu / 100);
			LogUtil.i("cm_socket", " 当前费率" + feilv);
			LogUtil.i("cm_socket", "预充金额" + (float) yuchong / 100);
			LogUtil.i("cm_socket", "已充金额" + (float) yichongjine / 100);
			LogUtil.i("cm_socket", "soc" + soc);
			LogUtil.i("cm_socket", "附属设备状态" + fushu);
			LogUtil.i("cm_socket", "告警状态" + gaojing);

			break;

		case SocketConstant.CMD_TYPE_CONSUME_RECORD:
			// 消费记录
			LogUtil.i("cm_socket", "消费记录返回");
			String order = new String(StreamUtil.readWithLength(in, 21));
			handleResponseConsume(order, (byte) 0x01);
			LogUtil.i("cm_socket", "订单号" + order);
			long temps = (long) StreamUtil.readInt(in);
			long tempe = (long) StreamUtil.readInt(in);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			String startdate = sdf.format(new Date(temps * 1000));
			String enddate = sdf.format(new Date(tempe * 1000));
			String totalpower = String
					.valueOf((float) StreamUtil.readInt(in) / 1000);
			String totalmoney = String
					.valueOf((float) StreamUtil.readInt(in) / 100);
			String servicemoney = String
					.valueOf((float) StreamUtil.readInt(in) / 100);
			String pilePK = String.valueOf(StreamUtil.readInt(in));
			int isFirst = StreamUtil.readByte(in);
			float coupon = (float) StreamUtil.readInt(in) / 100;
			float reduce = (float) StreamUtil.readInt(in) / 100;
			LogUtil.i("cm_socket", "开始时间" + startdate);
			LogUtil.i("cm_socket", "结束时间" + enddate);
			LogUtil.i("cm_socket", "总电量" + totalpower);
			LogUtil.i("cm_socket", "总充电金额" + totalmoney);
			LogUtil.i("cm_socket", "总服务费" + servicemoney);
			LogUtil.i("cm_socket", "电桩id" + pilePK);
			LogUtil.i("cm_socket", "是否首次" + isFirst);
			LogUtil.i("cm_socket", "优惠券面额" + coupon);
			LogUtil.i("cm_socket", "抵扣金额" + reduce);
			close();
			break;
		default:
			break;
		}

	}

	public void paraData(byte[] msg) throws Exception {
		// 将sock读出的数据放进缓存
		int writeLen = MyByteBuf.writeBytes(msg);
		// 分解数据包
		decode();
		// sock读出的数据没有全部放入缓存，继续处理
		if (writeLen < msg.length) {
			byte[] bb = new byte[msg.length - writeLen];
			System.arraycopy(msg, writeLen, bb, 0, msg.length - writeLen);
			paraData(bb);
		}
	}

	/**
	 * 分包处理
	 */
	public void decode() {
		// 读处理
		int readableBytes = MyByteBuf.readableBytes();
		if (readableBytes < 7)// 如果长度小于长度,不读
		{
			return;
		}
		int pos = MyByteBuf.bytesBefore((byte) 0x45);// 找到的位置
		int pos1 = MyByteBuf.bytesBefore((byte) 0x43);// 找到的位置
		int discardLen = 0;
		if (pos < 0 || pos1 < 0 || (pos1 - pos) != 1)// 没找到，全部读掉
		{
			discardLen = readableBytes;
			LogUtil.i("cm_socket", "decode not find flag header 0x4543,pos:"
					+ pos + "readableBytes:" + readableBytes + ",discardLen"
					+ discardLen + "\n");
		}
		if (pos > 0) {
			discardLen = pos;
			LogUtil.i("cm_socket", "decode find flag header 0x68 at pos:" + pos
					+ ",discardLen" + discardLen + "\n");
		}
		if (discardLen > 0) {
			byte[] dicardBytes = new byte[discardLen];
			MyByteBuf.readBytes(dicardBytes);//
			if (discardLen == readableBytes) {
				// 没有数据可对，还回
				return;
			}
		}
		readableBytes = MyByteBuf.readableBytes();// 读取缓存剩余的字节
		if (readableBytes < 7) {
			return;
		}
		MyByteBuf.readByte();// 读取协议头1
		MyByteBuf.readByte();// 读取协议头2
		int lengL = MyByteBuf.readByte();// 读长度1
		int lengH = MyByteBuf.readByte();// 读长度2

		int msg_len = lengL + lengH * 0x100;

		int remain_len = MyByteBuf.readableBytes();

		if (remain_len < msg_len) {
			LogUtil.i("cm_socket", "remain_len:" + remain_len + "\n");
			MyByteBuf.resetReaderIndex();
			return;
		}

		byte datab[] = null;
		datab = new byte[msg_len];
		MyByteBuf.readBytes(datab);
		sendHeartBeatTime = System.currentTimeMillis();
		LogUtil.i("cm_socket", "返回完整的包" + Tools.bytesToHexString(datab));
		try {
			handlePacketData(new ByteArrayInputStream(datab));
			mTcpCallBack.handleTcpPacket(new ByteArrayInputStream(datab));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 递归分包处理
		decode();
	}

	public void open(String pile, byte head) {
		if (state == STATE_CONNECT_SUCCESS) {
			try {
				this.pileNum = pile;
				this.headNum = head;
				outStream = socket.getOutputStream();
				inStream = socket.getInputStream();
				byte[] data = getConnetPackage();
				byte[] sendPacket = SocketPacket.getSendPackage(data,
						(byte) 0,
						(short) SocketConstant.CMD_TYPE_CONNECT);
				LogUtil.i(
						"cm_socket",
						"连接充电桩发送报文"
								+ Tools.bytesToHexString(sendPacket));
				responsePacket(sendPacket);
				/*synchronized (outStream) {
					outStream.write(sendPacket);
					outStream.flush();
				}*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		conn(pile, head);
	}
	

	public int getGunState() {
		return gunState;
	}

	public void setGunState(int gunState) {
		this.gunState = gunState;
	}

	public int getPileType() {
		return pileType;
	}

	public void setPileType(int pileType) {
		this.pileType = pileType;
	}

	public String getPileNum() {
		return pileNum;
	}

	public byte getHeadNum() {
		return headNum;
	}

	public synchronized void conn(String pile, byte head) {
		this.pileNum = pile;
		this.headNum = head;
		state = STATE_OPEN;
		// connThread =new Thread(new ConnRunnable());
		connThread = new ConnRunnable();
		connThread.start();
	}
	
	public void responsePacket(byte[] b){
		response = b;
		try {
			if (null != responsePacketThread && responsePacketThread.isAlive()) {
				responsePacketThread.interrupt();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			responsePacketThread = null;
		}
		responsePacketThread = new Thread(new ResponsePackerRunnable());
		responsePacketThread.start();
	}

	public synchronized void reopen() {
		if (state == STATE_CONNECT_SUCCESS) {
			return;
		}
		close();
		state = STATE_OPEN;
		connThread = new ConnRunnable();
		connThread.start();
	}

	/**
	 * 判断是否存在tcp连接
	 */
	public boolean hasTcpConnection() {
		if (STATE_CONNECT_SUCCESS == state) {
			return true;
		} else
			return false;
	}

/*	*//**
	 * 应答重新插枪提醒事件
	 *//*
	public void sendTipInsertGun(int count) {
		try {
			byte[] sendTipPacket = SocketPacket.getSendPackage(
					SocketPacket.getTipInsertGunPackage((byte) count),
					(byte) 0,
					(short) SocketConstant.CMD_TYPE_TIP_INSERT_GUN_EVENT);
			LogUtil.i("cm_socket",
					"应答重新插枪提醒" + Tools.bytesToHexString(sendTipPacket));
			outStream.write(sendTipPacket);
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}*/

	/**
	 * 应答枪与车连接状态提醒事件
	 */
	public void sendTipGunState(byte[] bytes) {
		try {
			byte[] sendTipPacket = SocketPacket.getSendPackage(
					SocketPacket.getTipGunStatePackage(bytes), (byte) 1,
					(short) SocketConstant.CMD_TYPE_GUN);
			LogUtil.i("cm_socket",
					"应答车与枪连接状态" + Tools.bytesToHexString(sendTipPacket));
			responsePacket(sendTipPacket);
			//outStream.write(sendTipPacket);
			//outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 应答收到消费记录
	 */
	public void handleResponseConsume(String order, byte b) {
		try {
			byte[] sendTipPacket = SocketPacket.getSendPackage(
					SocketPacket.getResponseConsumePackage(order, b), (byte) 0,
					(short) SocketConstant.CMD_TYPE_CONSUME_RECORD);
			LogUtil.i("cm_socket",
					"应答收到消费记录" + Tools.bytesToHexString(sendTipPacket));
			responsePacket(sendTipPacket);
			//outStream.write(sendTipPacket);
			//outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 发送开始充电
	 */
	public void sendStartChargeCMD(String money) {
		try {
			byte[] sendStartChargePacket = SocketPacket.getSendPackage(
					SocketPacket.getStartChargePackage(money, (byte) 1),
					(byte) 0, (short) SocketConstant.CMD_TYPE_START_CHARGE);
			LogUtil.i("cm_socket",
					"发送开始充电" + Tools.bytesToHexString(sendStartChargePacket));
			responsePacket(sendStartChargePacket);
				//outStream.write(sendStartChargePacket);
				//outStream.flush();

		} catch (Exception e) {
			state = STATE_CLOSE;
			// 开始充电异常处理
			try {
				PacketHeader Header = new PacketHeader();
				Header.setLength(6);
				ByteArrayOutputStream bmsg = new ByteArrayOutputStream(
						SocketConstant.PHONE_SENDBUFFER);
				bmsg.write(Header.toByteArray());
				short cmdtype = 10;
				short error = 1111;
				bmsg.write((byte) 0);
				byte cmdtypeL = (byte) (cmdtype & 0x00ff);
				bmsg.write(cmdtypeL);
				byte cmdtypeH = (byte) ((cmdtype >> 8) & 0x00ff);
				bmsg.write(cmdtypeH);
				bmsg.write((byte) 0);
				byte errorL = (byte) (error & 0x00ff);
				bmsg.write(errorL);
				byte errorH = (byte) ((error >> 8) & 0x00ff);
				bmsg.write(errorH);
				byte[] errorD = bmsg.toByteArray();
				handlePacketData(new ByteArrayInputStream(errorD));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			reopen();
			e.printStackTrace();
		}

	}

	/**
	 * 发送停止充电
	 */
	public void sendStopChargeCMD() {
		try {
			byte[] sendStopChargePacket = SocketPacket.getSendPackage(null,
					(byte) 0, (short) SocketConstant.CMD_TYPE_STOP_CHARGE);
			LogUtil.i("cm_socket",
					"发送结束充电" + Tools.bytesToHexString(sendStopChargePacket));
			responsePacket(sendStopChargePacket);
			/*synchronized (outStream) {
				outStream.write(sendStopChargePacket);
				outStream.flush();
			}*/
		} catch (Exception e) {
			// 停止充电异常处理，发送广播
			handleStopChargeError();
			reopen();
			e.printStackTrace();
		}
	}

	private void handleStopChargeError() {
		try {
			PacketHeader Header = new PacketHeader();
			Header.setLength(6);
			ByteArrayOutputStream bmsg = new ByteArrayOutputStream(
					SocketConstant.PHONE_SENDBUFFER);
			bmsg.write(Header.toByteArray());
			short cmdtype = 11;
			short error = 1111;
			bmsg.write((byte) 0);
			byte cmdtypeL = (byte) (cmdtype & 0x00ff);
			bmsg.write(cmdtypeL);
			byte cmdtypeH = (byte) ((cmdtype >> 8) & 0x00ff);
			bmsg.write(cmdtypeH);
			bmsg.write((byte) 0);
			byte errorL = (byte) (error & 0x00ff);
			bmsg.write(errorL);
			byte errorH = (byte) ((error >> 8) & 0x00ff);
			bmsg.write(errorH);
			byte[] errorD = bmsg.toByteArray();
			handlePacketData(new ByteArrayInputStream(errorD));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 连接socket
	private class ConnRunnable extends Thread {
		public void run() {
			while (state != STATE_CLOSE) {
				try {
					state = STATE_CONNECT_START;
					LogUtil.i("cm_socket", "STATE_CONNECT_START");
					socket = new Socket();
					socket.setTcpNoDelay(true);
					// socket.setSoTimeout(15000);
					socket.connect(new InetSocketAddress(Protocol.HOST,
							Protocol.PORT));
					state = STATE_CONNECT_SUCCESS;
					isFirstNotify = false;
				} catch (IOException e) {
					e.printStackTrace();
					state = STATE_CONNECT_FAILED;
					LogUtil.i("cm_socket", "STATE_CONNECT_FAILED");
					mTcpCallBack.handleSocketException();
					break;
					/*if (!isNetConnection() && !isFirstNotify) {
						notifyNoNetwork();
					}
					if (connCount < 3) {
						connCount++;
					} else {
						// 处理连接socket异常
						try {
							PacketHeader Header = new PacketHeader();
							Header.setLength(6);
							ByteArrayOutputStream bmsg = new ByteArrayOutputStream(
									SocketConstant.PHONE_SENDBUFFER);
							bmsg.write(Header.toByteArray());
							short cmdtype = SocketConstant.CMD_TYPE_CONNECT;
							short error = 110;
							bmsg.write((byte) 0);
							byte cmdtypeL = (byte) (cmdtype & 0x00ff);
							bmsg.write(cmdtypeL);
							byte cmdtypeH = (byte) ((cmdtype >> 8) & 0x00ff);
							bmsg.write(cmdtypeH);
							bmsg.write((byte) 0);
							byte errorL = (byte) (error & 0x00ff);
							bmsg.write(errorL);
							byte errorH = (byte) ((error >> 8) & 0x00ff);
							bmsg.write(errorH);

							bmsg.write((byte) 0);// 充电枪状态
							bmsg.write((byte) 0);

							byte[] errorD = bmsg.toByteArray();
							LogUtil.i("cm_socket", "mTcpCallBack"+"three times");
							mTcpCallBack
									.handleTcpPacket(new ByteArrayInputStream(
											errorD));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					}

					try {
						Thread.sleep(5 * 1000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
						LogUtil.i("cm_socket", "异常名称" + ex.getClass() + "引起原因"
								+ ex.getCause());
						break;
					}*/
				}

				if (state == STATE_CONNECT_SUCCESS) {
					try {
						connCount = 0;
						outStream = socket.getOutputStream();
						inStream = socket.getInputStream();
						byte[] data = getConnetPackage();
						byte[] sendPacket = SocketPacket.getSendPackage(data,
								(byte) 0,
								(short) SocketConstant.CMD_TYPE_CONNECT);
						LogUtil.i(
								"cm_socket",
								"连接充电桩发送报文"
										+ Tools.bytesToHexString(sendPacket));
						synchronized (outStream) {
							outStream.write(sendPacket);
							outStream.flush();
						}
						// 开启线程获取数据报文
						readPacketThread = new Thread(new ReadPacketRunnable());
						readPacketThread.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				} else {
					state = STATE_CONNECT_WAIT;
					LogUtil.i("cm_socket", "STATE_CONNECT_WAIT");
					// 如果有网络没有连接上，则定时取连接，没有网络则直接退出
					try {
						Thread.sleep(10 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}

				}
			}
		}
	}

	// 接收报文
	private class ReadPacketRunnable implements Runnable {
		@Override
		public void run() {

			byte[] b = new byte[1024];
			MyByteBuf.clear();
			try {
				int n = inStream.read(b);
				while (n != -1) {
					byte[] readByte = new byte[n];
					System.arraycopy(b, 0, readByte, 0, n);
					paraData(readByte);
					n = inStream.read(b);
				}
			} catch (Exception e) {
				// 接收报文异常

				e.printStackTrace();
			}
		}
	}
	
	//响应报文
	private class ResponsePackerRunnable implements Runnable{
		@Override
		public void run() {
				if(outStream != null){
					try {
					outStream.write(response);
					outStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		}
		
	}

	// 发心跳
	private class SendHeartRunnable implements Runnable {

		public void run() {
			try {
				byte[] buffer = SocketPacket.getSendPackage(null, (byte) 0,
						(short) SocketConstant.CMD_TYPE_HEART);
				while (state == STATE_CONNECT_SUCCESS && null != outStream) {
					if ((System.currentTimeMillis() - sendHeartBeatTime) > HEART_BEAT_RATE) {
						// byte[] buffer = SocketPacket.getSendPackage(null,
						// (byte)0,(short)2);
						LogUtil.i("cm_socket",
								"发送心跳包" + Tools.bytesToHexString(buffer));
						sendHeartBeatTime = System.currentTimeMillis();
						synchronized (outStream) {
							outStream.write(buffer);
							outStream.flush();
						}

					}
				}
			} catch (SocketException e1) {
				state = STATE_CLOSE;
				e1.printStackTrace();// 发送的时候出现异常，说明socket被关闭了(服务器关闭)java.net.SocketException:
										// sendto failed: EPIPE (Broken pipe)
				reopen();
			} catch (Exception e) {
				state = STATE_CLOSE;
				reopen();
				e.printStackTrace();
			}
		}
	}

	// 获取连接充电桩数据包
	private byte[] getConnetPackage() throws IOException {
		String pkuser = PreferencesUtil.getStringPreferences(mcContext,
				"pkUserinfo");
		String pwd = PreferencesUtil
				.getStringPreferences(mcContext, "password");
		long userId = Long.valueOf(pkuser);
		// 获取设备id
		TelephonyManager tm = (TelephonyManager) mcContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = tm.getDeviceId();
		deviceId = Tools.encoderByMd5(deviceId);
		String yzm = deviceId + pwd + pkuser;
		yzm = Tools.encoderByMd5(yzm);

		ByteArrayOutputStream bmsg = new ByteArrayOutputStream(
				SocketConstant.PHONE_SENDBUFFER);
		LogUtil.i("cm_socket", "yzm" + yzm + "密码 " + pwd);
		bmsg.write(pileNum.getBytes());
		bmsg.write(headNum);

		bmsg.write(SocketParseByteUtil.longToByte(userId));
		bmsg.write(yzm.getBytes());
		bmsg.write(0x03);

		return bmsg.toByteArray();
	}

	public synchronized void close() {
		if (state != STATE_CLOSE) {
			try {
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				socket = null;
			}

			try {
				if (null != outStream) {
					synchronized (outStream) {
						outStream.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				outStream = null;
			}

			try {
				if (null != inStream) {
					inStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				inStream = null;
			}

			try {
				if (null != connThread && connThread.isAlive()) {
					connThread.interrupt();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				connThread = null;
			}

			try {
				if (null != sendHeartThread && sendHeartThread.isAlive()) {
					sendHeartThread.interrupt();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sendHeartThread = null;
			}
			try {
				if (null != readPacketThread && readPacketThread.isAlive()) {
					readPacketThread.interrupt();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				readPacketThread = null;
			}
			state = STATE_CLOSE;
		}
	}

	/* 判断是否有网络 */
	public boolean isNetConnection() {
		ConnectivityManager cwjManager = (ConnectivityManager) mcContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}

	private void notifyNoNetwork() {
		isFirstNotify = true;
		Intent intent = new Intent(BroadcastUtil.BROADCAST_TCP_NETWORK);
		mcContext.sendBroadcast(intent);
	}

}
