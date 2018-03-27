package com.bm.wanma.socket;

import android.annotation.SuppressLint;
import android.util.Log;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SocketParseByteUtil {
	
	
	/** 
     * @ 解析收取的包
     */ 
	public void paraData(byte []msg) throws Exception {
		  //将sock读出的数据放进缓存
		  int writeLen =  MyByteBuf.writeBytes(msg);
		  //分解数据包
		  decode();
		  //sock读出的数据没有全部放入缓存，继续处理
		  if(writeLen< msg.length)
		  {
			  byte [] bb = new byte[msg.length-writeLen];
			  System.arraycopy(msg, writeLen, bb, 0, msg.length-writeLen);
			  paraData(bb);
		  }
	   } 
	
	/**
	 * 分包处理
	 */
	 public void decode() {
		 //读处理
		  int readableBytes= MyByteBuf.readableBytes();
		  if(readableBytes<7)//如果长度小于长度,不读
		  {
				return;
		  }
			
			int pos= MyByteBuf.bytesBefore((byte)0x45);//找到的位置
			int pos1= MyByteBuf.bytesBefore((byte)0x43);//找到的位置
			int discardLen=0;
			if(pos < 0 || pos1<0 || (pos1-pos)!=1)//没找到，全部读掉
			{
				discardLen = readableBytes;
				Log.i("cm_socket", "decode not find flag header 0x4543,pos:"+pos+"readableBytes:"+readableBytes+",discardLen"+discardLen+"\n");
			}
			if(pos>0)
			{
				discardLen = pos;
				Log.i("cm_socket", "decode find flag header 0x68 at pos:"+pos +",discardLen"+discardLen+"\n");
			}
			if(discardLen>0)
			{
				byte[] dicardBytes= new byte[discardLen];
				MyByteBuf.readBytes(dicardBytes);//
				if(discardLen == readableBytes)
				{
					//没有数据可对，还回
					return;
				}
			}
			readableBytes= MyByteBuf.readableBytes();//读取缓存剩余的字节
			if(readableBytes<7)
			{
				return;
			}
			byte protocolhead1 = MyByteBuf.readByte();//读取协议头1
			byte protocolhead2 = MyByteBuf.readByte();//读取协议头2
			int lengL = MyByteBuf.readByte();//读长度1
			int lengH = MyByteBuf.readByte();//读长度2
		    
			int msg_len = lengL+lengH*0x100;
			
			int remain_len = MyByteBuf.readableBytes();

			if(remain_len<msg_len )
			{
				Log.i("cm_socket","remain_len:"+remain_len+"\n");
				MyByteBuf.resetReaderIndex();
				return ; 
			}
			    
			byte datab[]= null;
			datab= new byte[msg_len];
			MyByteBuf.readBytes(datab);
			
	 }
	
	
	
	
	
	
	
	
	public static String bcd2Str_div_ff(byte[] bytes) {
		
		short valid_len=0; 
		for(int i=0;i< bytes.length;i++)
		{
			if( (bytes[i]&0x0FF)==255)
			{
				break;
			}

			valid_len += 1;
		}
		
		if(valid_len>0)
		{
	        StringBuffer temp = new StringBuffer(valid_len * 2);  
	        for (int i = 0; i <valid_len; i++) {  
	            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));  
	            temp.append((byte) (bytes[i] & 0x0f));  
	        }  
	        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp  
	                .toString().substring(1) : temp.toString();  
		}
		else
		{
			return "";
		}
    } 
	
	public static String bcd2Str(byte[] bytes) {
		
        StringBuffer temp = new StringBuffer(bytes.length * 2);  
        for (int i = 0; i <bytes.length; i++) {  
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));  
            temp.append((byte) (bytes[i] & 0x0f));  
        }  
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp  
                .toString().substring(1) : temp.toString();  
		
    }
 	public static String littleEninaBcd2Str(byte[] bytes) {
		
        StringBuffer temp = new StringBuffer(bytes.length * 2);  
        for (int i = (bytes.length-1); i >=0; i--) {  
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));  
            temp.append((byte) (bytes[i] & 0x0f));  
        }  
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp  
                .toString().substring(1) : temp.toString();  
		
    }
	
	public static String bcd2StrDividFF(byte[] bytes) {
		int count= bytes.length;
		int validLen=0;
		for(int i=0;i<count;i++)
		{
			int nb=bytes[i]&0xFF;
			if(nb == 255)
			//if((int)bytes[i]== 255)
			{
				break;
			}
			validLen +=1;
		}
		
        StringBuffer temp = new StringBuffer(validLen * 2);  
        for (int i = 0; i <validLen; i++) {  
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));  
            temp.append((byte) (bytes[i] & 0x0f));  
        }  
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp  
                .toString().substring(1) : temp.toString();  
		
    }
  
     
    public static byte[] str2Bcd(String asc) {  
        int len = asc.length();  
        int mod = len % 2;  
        if (mod != 0) {  
            asc = "0" + asc;  
            len = asc.length();  
        }  
        byte abt[] = new byte[len];  
        if (len >= 2) {  
            len = len / 2;  
        }  
        byte bbt[] = new byte[len];  
        abt = asc.getBytes();  
        int j, k;  
        for (int p = 0; p < asc.length() / 2; p++) {  
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {  
                j = abt[2 * p] - '0';  
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {  
                j = abt[2 * p] - 'a' + 0x0a;  
            } else {  
                j = abt[2 * p] - 'A' + 0x0a;  
            }  
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {  
                k = abt[2 * p + 1] - '0';  
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {  
                k = abt[2 * p + 1] - 'a' + 0x0a;  
            } else {  
                k = abt[2 * p + 1] - 'A' + 0x0a;  
            }  
            int a = (j << 4) + k;  
            byte b = (byte) a;  
            bbt[p] = b;  
        }  
        return bbt;  
    }  
    
    public static long getframenum(byte b1,byte b2)
    {
        short s1=(short)b1;
        short s2=(short)b2;
        s2= (short) (s2 <<8);
          
        short receiveNumber = (short) (s2 + s1);
        receiveNumber =(short) (receiveNumber>>1);
        return receiveNumber;
    }
    
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i=begin; i<begin+count; i++) bs[i-begin] = src[i];
        return bs;
    }
    
    public static int getUnsignedByte (byte data){      //??data?????????????0~255 (0xFF ??BYTE)??
        return data&0x0FF ;
      }

     public int getUnsignedByte (short data){      //??data?????????????0~65535 (0xFFFF ?? WORD)??
           return data&0x0FFFF ;
      }       

    public static long getUnsignedIntt (int data){     //??int????????0~4294967295 (0xFFFFFFFF??DWORD)??
        return data&0x0FFFFFFFF ;
      }
    
    
    
    
    
  //long类型转成byte数组 
    @SuppressLint("UseValueOf")
	public static byte[] longToByte(long number) { 
          long temp = number; 
          byte[] b = new byte[8]; 
          for (int i = 0; i < b.length; i++) { 
              b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位 
              temp = temp >> 8; // 向右移8位 
          } 
          return b; 
      } 
      
      //byte数组转成long 
      public static long byteToLong(byte[] b) { 
          long s = 0; 
          long s0 = b[0] & 0xff;// 最低位 
          long s1 = b[1] & 0xff; 
          long s2 = b[2] & 0xff; 
          long s3 = b[3] & 0xff; 
          long s4 = b[4] & 0xff;// 最低位 
          long s5 = b[5] & 0xff; 
          long s6 = b[6] & 0xff; 
          long s7 = b[7] & 0xff; 
   
          // s0不变 
          s1 <<= 8; 
          s2 <<= 16; 
          s3 <<= 24; 
          s4 <<= 8 * 4; 
          s5 <<= 8 * 5; 
          s6 <<= 8 * 6; 
          s7 <<= 8 * 7; 
          s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7; 
          return s; 
      } 
 

      public static byte[] int2Bytes(int i) {  
      	byte[] b = new byte[4];
      	b[0] = (byte) (i & 0xff);
      	b[1] = (byte) (i >> 8);
      	b[2] = (byte) (i >> 16);
      	b[3] = (byte) (i >> 24);
      	
      	return b;  
       }
  
    
    public static byte[] int2ByteArr(int i) {  
    	byte[] b = new byte[4];
    	b[0] = (byte) (i & 0xff);// 最低位
    	b[1] = (byte) ((i >> 8) & 0xff);// 次低位
    	b[2] = (byte) ((i >> 16) & 0xff);// 次高位
    	b[3] = (byte) (i >>> 24);// 最高位,无符号右移。
    	
    	return b;  
     }
    
    public static int bytes2int(byte[] b) {  
    	int num=0;
    	if(b.length>0)
    	{
    		for(int i=0;i<b.length;i++)
    		{
    			if(i>0)
    			{
    				num += (b[i]&0xff) <<(i<<(i*8));
    			}
    			else
    			{
    				num = b[i]&0xff;
    			}
    		}
    	}
    	
    	
    	return num;  
     }
    
   
 
    public static byte[] short2Bytes(short num) {  
    	byte[] byteNum = new byte[2];  
    	
    	byteNum[0] = (byte) (num & 0xff);
    	byteNum[1] = (byte) (num >> 8);
    	
    	return byteNum;  
    }

     /**
     * 通过byte数组取到short
     * @param b
     * @param index  第几位开始取
     * @return
     */ 
     public static short getShort(byte[] b, int index) { 
           return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff)); 
     } 
     /**
      * 通过byte数组取到int
      * @param b
      * @param index  第几位开始取
      * @return
      */ 
      public static int getInt(byte[] b, int index) { 
    	  
            return (int) ((b[index + 3] << 24)|(b[index + 2] << 16)|(b[index + 1] << 8) | b[index + 0] & 0xff); 
      } 
     
     /** 
      * 将一个单字节的Byte转换成十六进制的数 
      * @param b 
      * byte 
      * @return convert result 
      */  
     public static String byteToHex(byte b) {  
         int i = b & 0xFF;  
         return Integer.toHexString(i);  
     }  
     /** 
      * 将一个单字节的Byte转换成十进制的数 
      * @param b 
      * byte 
      * @return convert result 
      */  
     public static int byteToD(byte b) {  
         int i = b & 0xFF;  
         return i;  
     }  
     
    public  static void dumpHex(byte[] msgContext)
	{
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    	Date now = new Date();  
    	System.out.print(dateFormat.format( now ));
		int len= msgContext.length;
		for(int i=0;i<len;i++)
		{
			if(i>0 && (i%8)==0)
			{
				System.out.print("\n");
			}
			String hex="0x" + Integer.toHexString((msgContext[i] & 0x000000FF) | 0xFFFFFF00).substring(6).toUpperCase() +" " ;
			System.out.print(hex);
		}
		System.out.print("\n");
	
	}
    public  static String ConvertHex(byte[] msgContext,int flag)
	{
    	final StringBuilder sb = new StringBuilder();
       
    	Date now = new Date(); 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//????????????????? 

    	String sNow = dateFormat.format( now ); 
		if(flag ==1)
    	{
    		sb.append("ep-> server,"); 
    	}
    	else if(flag==0)
    	{
    		sb.append(" server->ep,"); 
    	}
    	else if(flag ==2)
    	{
    		sb.append("phone->server,"); 
    	}
    	else if(flag ==3)
    	{
    		sb.append("server->phone,"); 
    	}
		
    	sb.append(sNow+ " len:"+msgContext.length+ "\r\n"); 


		int len= msgContext.length;
		for(int i=0;i<len;i++)
		{
			
			String hex=Integer.toHexString((msgContext[i] & 0x000000FF) | 0xFFFFFF00).substring(6).toUpperCase();
			sb.append(hex);
		}
		sb.append("\r\n");
		
		return sb.toString();
	
	}
    
	
	public static byte getP56Time2a()[] {

		byte P56Time2a[] = new byte[7];
		
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR); 
		int month = cal.get(Calendar.MONTH)+1;
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);  
		
		int hourOfDay = cal.get(Calendar.HOUR_OF_DAY); 
		int Minute = cal.get(Calendar.MINUTE); 
		
		int Second = cal.get(Calendar.SECOND);

		P56Time2a[0] = (byte) ((Second * 1000) & 0xff);
		P56Time2a[1] = (byte) ((Second * 1000) >> 8);
		P56Time2a[2] = (byte) Minute;
		P56Time2a[3] = (byte) hourOfDay;
		P56Time2a[4] = (byte) dayOfMonth;
		P56Time2a[5] = (byte) (month);
		P56Time2a[6] =(byte) (year % 100);

		return P56Time2a;
	}
	
	public static byte getP56Time2a(java.util.Date date)[] {

		byte P56Time2a[] = new byte[7];
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);//把当前时间赋给日历
		
		int year = cal.get(Calendar.YEAR); 
		int month = cal.get(Calendar.MONTH)+1;
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);  
		
		int hourOfDay = cal.get(Calendar.HOUR_OF_DAY); 
		int Minute = cal.get(Calendar.MINUTE); 
		
		int Second = cal.get(Calendar.SECOND);

		P56Time2a[0] = (byte) ((Second * 1000) & 0xff);
		P56Time2a[1] = (byte) ((Second * 1000) >> 8);
		P56Time2a[2] = (byte) Minute;
		P56Time2a[3] = (byte) hourOfDay;
		P56Time2a[4] = (byte) dayOfMonth;
		P56Time2a[5] = (byte) (month);
		P56Time2a[6] =(byte) (year % 100);

		return P56Time2a;
	}
	
	public static long getP56Time2aTime(byte P56Time2a[]) {
		int year=2000 + (int)P56Time2a[6];
		int month= (int)P56Time2a[5];
		int day=(int)P56Time2a[4];
		int hour=(int)P56Time2a[3];
		int minute=(int)P56Time2a[2];
		
		 int MillSec = P56Time2a[0] & 0xff;
		 MillSec |= (P56Time2a[1] & 0xff) << 8;

		long second= MillSec/1000 ;
        
		try {
			  String in=String.format("%d-%d-%d %d:%d:%d", year, month, day, hour,minute,second);
			  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
		       
			  Date date= format.parse(in);
			 Calendar cal = Calendar.getInstance();        
	         cal.setTime(date);        
	         return cal.getTimeInMillis()/1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return 0;
		}        
        
	}
	
	public static Date stringToDate(String str) 
	{  
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		Date date = null;  
		try {  
		            // Fri Feb 24 00:00:00 CST 2012  
		      date = format.parse(str);   
		} catch (ParseException e) 
		{  
      
       // 2012-02-24  
		    	 
		}  
		//date = java.util.Date.valueOf(str); 
	
	
		return date;
	}
	public short Hex2BCD(short hex)
	{
		short bcd = 0;
		for(int i=0; i<8; i++)
		{
			bcd += (hex % 10)<<(i*4);
			hex /= 10;
		}

		return bcd;
	}

	short BCD2Hex(short bcd)
	{
		short hex = 0;
		short ten = 1;
		for(int i=0; i<8; i++)
		{
			hex += (bcd & 0x0F)*(ten);
			ten *= 10;
			bcd >>= 4;
		}

		return hex;
	}
		private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	
	public static String MD5Encode(byte[] origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}
}
