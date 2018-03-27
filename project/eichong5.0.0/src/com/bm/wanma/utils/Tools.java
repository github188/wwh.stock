package com.bm.wanma.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.bm.wanma.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * @author cm
 * 工具类
 */
public class Tools {
	/**
	 * 图片路径
	 */
    public static String advertisementpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/eichong/advertisement/";

	// MD5加密 -- 32位
	public static String encoderByMd5(String srcStr) {
		if(null == srcStr){
			return "";
		}
		String str = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			md.update(srcStr.getBytes());
			// 获得密文
			byte b[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();//32位
			//str = buf.toString().substring(8, 24); //md5 16位
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//大写return str.toUpperCase();
		return str;
	}
	
	//token加密 替换字符
	public static String replace(byte a) {
		
		String str = String.valueOf((char)a);
		if (a >= 48 && a <= 57) {
			if ("1".equals(str)) {
				str = str.replaceAll("1", "7");
			} else if ("2".equals(str)) {
				str = str.replaceAll("2", "5");
			} else if ("3".equals(str)) {
				str = str.replaceAll("3", "8");
			} else if ("5".equals(str)) {
				str = str.replaceAll("5", "2");
			} else if ("6".equals(str)) {
				str = str.replaceAll("6", "9");
			} else if ("7".equals(str)) {
				str = str.replaceAll("7", "1");
			} else if ("8".equals(str)) {
				str = str.replace("8", "3");
			} else if ("9".equals(str)) {
				str = str.replace("9", "6");
			}
		}else if (a >= 65 && a <= 90) {
			//大写转小写 +32
			char b = (char) (a +32);
			b = (char) (b + 3);
			if(b > 122){
				b = (char) (b-26);
			}
			str = String.valueOf(b);
		}else if (a >= 97 && a <= 122) {
			char b = (char)(a-32);
			b = (char) (b + 3);
			if (b > 90) {
				b = (char) (b - 26);
			}
			str = String.valueOf(b);
		}
		return str;
	}

	/**
	 * 判断字符串不为空
	 */
	public static boolean isEmptyString(String str){
		if(str == null){
			return true;
		}else if(str.replaceAll(" ", "").length()==0){
			return true;
		}else {
			return false;
		}
	
		
	}
	/**
	 * 判断字符串不为null
	 */
	public static boolean isNullString(String str){
		
		if(str == null){
			return true;
		}else return false;
		
	}
	
	/**
	 * 获取当前时间
	 * cm
	 */
	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}
	
	/**
	 * 判断距离是否大于1000m，大于的话，转为km显示
	 * cm
	 */
	
	public static String getMeterOrKM(String str){
		if(TextUtils.isEmpty(str)){
			return "0m";
		}
		double m = Double.parseDouble(str);
		if(m>=1000){
			double km = Math.rint(m / 100) / 10;// 这个结果是千米
			return km+"km";
			
		}else {
			return m+"m";
		}
		
	}
	/**
	 * 取小数点后一位，四舍五入
	 * cm
	 */
	public static String getSub1Value(String str){
		BigDecimal b = new BigDecimal(str); 
		str = b.setScale(1,BigDecimal.ROUND_HALF_UP).toString();
		return str;
	}
	/**
	 * 取小数点后二位，四舍五入
	 * cm
	 */
	public static String getSub2Value(String str){
		BigDecimal b = new BigDecimal(str); 
		str = b.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
		return str;
	}
	
	/**
	 * 获取0-9 a-z A-Z随机数 cm
	 */
	public static String getRandomChar(int length) {
		char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
		buffer.append(chr[random.nextInt(62)]);
		}
		return buffer.toString();
		}
	
	/**
	 * 将字符串转成ASCII cm
	 * @return
	 */
	public static String stringToAscii(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append((int) chars[i]).append(",");
			} else {
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}

	/**
	 * 将ASCII转成字符串 cm
	 */
	public static String asciiToString(String value) {
		StringBuffer sbu = new StringBuffer();
		String[] chars = value.split(",");
		for (int i = 0; i < chars.length; i++) {
			sbu.append((char) Integer.parseInt(chars[i]));
		}
		return sbu.toString();
	}
	 /**
     * 判断数据字符是否是数字（能转成数字的）
     * @param str 数字字符
     * @return 是数字字符返回true,不是返回false
     */
    public static boolean isNumber(String strNumber){
        boolean bolResult = false;
        BigDecimal temp;
        try {
            temp = new BigDecimal(strNumber);
            bolResult = true;
        } catch (NumberFormatException e) {
            e.getStackTrace();
        }
        return bolResult;
    }
    /** 
     * 格式化金额         
     * @param s 
     * @param len 小数点后 位数
     * @param unit 单位
     * @return 
     */  
    public static String formatMoney(String s, int len ,String unit)   
    {  
        if (s == null || s.length() < 1) {  
            return 0.00 + unit;  
        }  
        NumberFormat formater = null;  
        double num;
		try {
			num = Double.parseDouble(s);
		} catch (NumberFormatException e) {
			num = 0;
			e.printStackTrace();
		}  
        if (len == 0) {  
            formater = new DecimalFormat("###,###");  
  
        } else {  
            StringBuilder buff = new StringBuilder();  
            buff.append("###,###.");  
            for (int i = 0; i < len; i++) {  
                buff.append("#");  
            }  
            formater = new DecimalFormat(buff.toString());  
        }  
        String result = formater.format(num);  
        if(result.indexOf(".") == -1)  
        {  
            result = unit + result + ".00";  
        }  
        else  
        {  
            result = unit + result;  
        }  
        return result;  
    }
    
	/**
     * 数据相减
     * @param augend减数
     * @param augend2被减数
     * @return augend-augend2
     */
    public static String reduceStr(String augend, String augend2) {
        String strResult = "";
        if(isNumber(augend)&&isNumber(augend2)){
            BigDecimal bdAugend = new BigDecimal(augend);
            BigDecimal bdAugend2 = new BigDecimal(augend2);
            bdAugend = bdAugend.subtract(bdAugend2);
            strResult = bdAugend.toString();
        }
        return strResult;
    }	
    /**
     * 数据相加
     * @param augend
     * @param augend2
     * @return augend+augend2
     */
    public static String addStr(String augend, String augend2) {
        String strResult = "0";
        if(isNumber(augend)&&isNumber(augend2)){
            BigDecimal bdAugend = new BigDecimal(augend);
            BigDecimal bdAugend2 = new BigDecimal(augend2);
            bdAugend = bdAugend.add(bdAugend2);
            strResult = bdAugend.toString();
        }
        return strResult;
    }	
    
    /**
     * 数据相加
     * @param augend
     * @param augend2
     * @param augend3
     * @return augend+augend2-augend3
     */
    public static String addReduceStr(String augend, String augend2, String augend3) {
        String strResult = "0";
        if(isNumber(augend)&&isNumber(augend2)){
            BigDecimal bdAugend = new BigDecimal(augend);
            BigDecimal bdAugend2 = new BigDecimal(augend2);
            BigDecimal bdAugend3 = new BigDecimal(augend3);
            bdAugend = bdAugend.add(bdAugend2).subtract(bdAugend3);
            strResult = bdAugend.toString();
        }
        return strResult;
    }	
	
	public static void showToast(Activity activity, String context) {
		if (context.length() > 0)
			Toast.makeText(activity, context, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 将dp类型的尺寸转换成px类型的尺寸
	 * 
	 * @param size
	 * @param context
	 * @return
	 */
	public static int dip2px(Context context, int dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 判断两个String 是否相等
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean judgeString(String arg0, String arg1) {
		return arg0 != null && arg1 != null && arg0.equals(arg1);
	}

	/**
	 * 
	 * @param arg0
	 * @param dateFormat
	 * @return
	 */
	public static boolean judgeString(String arg0, String... dateFormat) {
		for (int i = 0; i < dateFormat.length; i++) {
			if (judgeString(arg0, dateFormat[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 把指定的String 时间，按照他的格式转换为时间戳
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static long parseTime(String date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date2 = null;
		try {
			date2 = sdf.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date2.getTime();
	}

	/**
	 * 把时间戳更改为日期
	 * @param date
	 * */
	public static String getdate(String date) {
		long da = Long.parseLong(date);
		;
		if (date.length() == 10) {
			da *= 1000;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		Date da1 = new Date(da);

		return sdf.format(da1);
	}

	/**
	 * 获得时间
	 * @param date
	 * */
	public static String getTime(String date) {
		long da = Long.parseLong(date);
		if (date.length() == 10) {
			da *= 1000;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date da1 = new Date(da);
		return sdf.format(da1);
	}

	/**
	 * 根据给定的格式化参数，将给定的时间戳转换为需要的字符串
	 * 
	 * @param dateString
	 * @param dateFormat
	 * @return java.util.Date
	 */
	public static String parse(String timestamp, String dateFormat) {
		if ("".equals(timestamp.trim()) || timestamp == null) {
			return null;
		}
		long da = Long.parseLong(timestamp);
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(da);
		// try {
		// date = sdf.parse(dateString);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		return sdf.format(date);
	}

	/**
	 * cm
	 * @Title: parseDate
	 * @Description: 把时间转换成指定类型
	 * @return String 返回类型
	 * @param date
	 *            时间字符串 如2014-01-02
	 * @param dateFormat
	 *            传入时间的格式，yyyy-MM-dd
	 * @param returnForma
	 *            指定的返回时间的格式
	 */
	public static String parseDate(String date, String dateFormat, String returnFormat) {
		if (judgeString("", date)) {
			return " ";
		}
		if(Tools.isEmptyString(dateFormat)){
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date2 = null;
		try {
			date2 = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		DateFormat df = new SimpleDateFormat(returnFormat);
		return df.format(date2);
	}

	/**
	 * 把时间戳转换为yyyy-MM-dd HH:mm:ss的格式
	 * 
	 * @param date
	 * @return
	 */
	public static String parse(String date) {
		return parse(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 判断是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * 判断日期是否按照格式
	 * 
	 * @param data
	 * @return
	 */
	public static boolean judgeData(String data) {
		if (isNull(data))
			return false;
		if (!isFormatDate(data)) {
			return false;
		}
		String[] date = data.split("-");
		String[] day31 = { "01", "03", "05", "07", "08", "10", "12" };
		String[] day30 = { "04", "06", "09", "11" };
		String month2 = "02";
		/* 如果是31天的月份，判定日期不能大于31 */
		if (judgeString(date[1], day31)) {

			if (Integer.parseInt(date[2]) > 31) {
				return false;
			}
		}
		/* 如果是30天的月份，判定日期不能大于30 */
		if (judgeString(date[1], day30)) {
			if (Integer.parseInt(date[2]) > 30) {
				return false;
			}
		}
		/* 如果是闰年 */
		if (isLeapYear(date[0])) {
			/* 如果是2月，判定是否大于29 */
			if (judgeString(date[1], month2)) {
				if (Integer.parseInt(date[2]) > 29) {
					return false;
				}
			}
		} else {
			/* 如果是2月，判定是否大于29 */
			if (judgeString(date[1], month2)) {
				if (Integer.parseInt(date[2]) > 28) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 判断时间是否符合格式要求
	 * @param date
	 * @return
	 */
	public static boolean isFormatDate(String date) {
		String pattner = "[0-9]{4}[-][0-9]{2}[-][0-9]{2}";
		return date.matches(pattner);
	}

	/**
	 * 是否是闰年
	 * @param y
	 * @return
	 */
	public static boolean isLeapYear(String y) {
		int year = Integer.parseInt(y);
		if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: before
	 * @Description: 传入的时间距离现在多久
	 * @param date
	 * @return String 返回类型
	 */
	public static String before(String date) {
		String text = "";
		long time = 0;
		try {
			time = Long.parseLong(date);
		} catch (Exception e) {
			return "";
		}
		time = date.length() == 10 ? time * 1000 : time;
		long now = System.currentTimeMillis();
		int timeChar = Math.abs((int) (now / 1000 - time / 1000) / 60);// 分钟
		text = timeChar + "分钟";
		// if (timeChar < 60) {
		// text = timeChar + "秒";
		// } else if (timeChar >= 60 && timeChar < 3600) {
		// timeChar = timeChar / (60);
		// text = timeChar + "分钟";
		// } else if (timeChar >= 3600 && timeChar < 86400) {
		// timeChar = timeChar / (60 * 60);
		// text = timeChar + "小时";
		// } else if (timeChar >= 86400 && timeChar < 2592000) {
		// timeChar = timeChar / (60 * 60 * 24);
		// text = timeChar + "天";
		// } else if (timeChar >= 2592000 && timeChar < 946080000) {
		// timeChar = timeChar / (60 * 60 * 24 * 30);
		// text = timeChar + "月";
		// } else {
		// timeChar = timeChar / (60 * 60 * 24 * 30 * 365);
		// text = timeChar + "年";
		// }
		return text;
	}
	/** 
     * 截取一段byte数组
     */
	  public static byte[] subBytes(byte[] src, int begin, int count) {
	        byte[] bs = new byte[count];
	        for (int i=begin; i<begin+count; i++) bs[i-begin] = src[i];
	        return bs;
	    }
	 
	
	
	/** 
     * Convert hex string to hex string.
     */   
	public static String hexStr2Str(String hexStr)    
	{      
	    String str = "0123456789ABCDEF";      
	    char[] hexs = hexStr.toCharArray();      
	    byte[] bytes = new byte[hexStr.length() / 2];      
	    int n;      
	  
	    for (int i = 0; i < bytes.length; i++)    
	    {      
	        n = str.indexOf(hexs[2 * i]) * 16;      
	        n += str.indexOf(hexs[2 * i + 1]);      
	        bytes[i] = (byte) (n & 0xff);      
	    }      
	    return new String(bytes);      
	}   
	
	public static String str2HexStr(String str)    
	{      
	    char[] chars = "0123456789ABCDEF".toCharArray();      
	    StringBuilder sb = new StringBuilder("");    
	    byte[] bs = str.getBytes();      
	    int bit;      
	        
	    for (int i = 0; i < bs.length; i++)    
	    {      
	        bit = (bs[i] & 0x0f0) >> 4;      
	        sb.append(chars[bit]);      
	        bit = bs[i] & 0x0f;      
	        sb.append(chars[bit]);    
	        sb.append(' ');    
	    }      
	    return sb.toString().trim();      
	}    
	    
	
    /** 
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。 
     * @param src byte[] data 
     * @return hex string 
     */     
    public static String bytesToHexString(byte[] src){  
        StringBuilder stringBuilder = new StringBuilder("");  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        for (int i = 0; i < src.length; i++) {  
            int v = src[i] & 0xFF;  
            String hv = Integer.toHexString(v);  
            if (hv.length() < 2) {  
                stringBuilder.append(0);  
            }  
            stringBuilder.append(hv);  
        }  
        return stringBuilder.toString();  
    }  
    /** 
     * Convert hex string to byte[] 
     * @param hexString the hex string 
     * @return byte[] 
     */  
    @SuppressLint("DefaultLocale")
	public static byte[] hexStringToBytes(String hexString) {  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }  
        hexString = hexString.toUpperCase();  
        int length = hexString.length() / 2;  
        char[] hexChars = hexString.toCharArray();  
        byte[] d = new byte[length];  
        for (int i = 0; i < length; i++) {  
            int pos = i * 2;  
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
        }  
        return d;  
    }  
    /** 
     * Convert char to byte 
     * @param c char 
     * @return byte 
     */  
     private static byte charToByte(char c) {  
        return (byte) "0123456789ABCDEF".indexOf(c);  
    }  
     //大端序转小端序
     static int BigEndToLittleEnd(int value) throws IOException 
     {
         int tmp;
         tmp = (value << 24) | ((value << 8) & 0xFF0000) | ((value >> 8) & 0xFF00) | ((value >> 24) & 0xFF);
         return tmp;
     }
     /** 
      * draw text to bitmap
      * @param text 
      * @return bitmap 
      */  
     public static Bitmap getMarkerIcon(Context context,String text){
    	 Resources resources = context.getResources();
    	 float scale = resources.getDisplayMetrics().density; 
    	 int textSize = 0;
    	 if(TextUtils.isEmpty(text)){
    		 text = "1";
    	 }else {
    		 if(text.length() == 1){
     			textSize = 15;
     		}else if(text.length() == 2){
    			textSize = 13;
    		}else if(text.length()==3){
    			textSize = 12;
    		}else if(text.length()> 3){
    			textSize = 9;
    		}
    	 }
    	 Bitmap bitmap =   
                 BitmapFactory.decodeResource(resources, R.drawable.marker_num);  
    	 Config bitmapConfig = bitmap.getConfig();  
    	 if(bitmapConfig == null) {  
             bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;  
         }
    	 bitmap = bitmap.copy(bitmapConfig, true);  
    	 Canvas canvas = new Canvas(bitmap);  
    	 Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);  
    	 paint.setColor(resources.getColor(R.color.common_black));  
         // text size in pixels  
         paint.setTextSize(textSize * scale); 
         Rect bounds = new Rect();
         paint.getTextBounds(text, 0, text.length(), bounds);  
         int x = (bitmap.getWidth() - bounds.width())/2;  
         int y = (bitmap.getHeight() + bounds.height())/2;   
         canvas.drawText(text, x , y , paint); 
    	 return bitmap;
    	 
     }
     
     
     public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,int size) {
    	// Bitmap target = Bitmap.createBitmap(size,size,bitmap.getConfig());
    	 Bitmap target = Bitmap.createScaledBitmap(bitmap, size, size, false);
    	 Bitmap output = Bitmap.createBitmap(target.getWidth(), target.getWidth(), Config.ARGB_8888);
    	 Canvas canvas = new Canvas(output);
    	 final int color = 0xff424242;
    	 final Paint paint = new Paint();
    	 final Rect rect = new Rect(0, 0,target.getWidth(), target.getHeight());
    	 final RectF rectF = new RectF(rect);
    	 //final float roundPx = target.getWidth() / 2;
    	 final float roundPx = size / 2;
    	 paint.setAntiAlias(true);
    	 canvas.drawARGB(0, 0, 0, 0);
    	 paint.setColor(color);
 
    	 canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
    	 paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    	 canvas.drawBitmap(bitmap, null, rectF, paint);
    	 
    	 return output;
    	 } 
     
     
     
     
     
     public static Bitmap getRoundedCornerBitmapback(Bitmap bitmap,int size) {
     	 Bitmap target = Bitmap.createScaledBitmap(bitmap, size, size, false);
     	 Bitmap output = Bitmap.createBitmap(target.getWidth(), target.getWidth(), Config.ARGB_8888);
     	 Canvas canvas = new Canvas(output);
     	 final int color = 0xff424242;
     	 final Paint paint = new Paint();
     	 final Rect rect = new Rect(0, 0,target.getWidth(), target.getHeight());
     	 final RectF rectF = new RectF(rect);
     	 final float roundPx = size / 2;
     	 paint.setAntiAlias(true);
     	 canvas.drawARGB(0, 0, 0, 0);
     	 paint.setColor(color);
     	 canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
     	 paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
     	 canvas.drawBitmap(bitmap, null, rectF, paint);
     	 
     	 return output;
     	 } 
     /**
      * 判断本地是否存在该文件
      * @param picName 文件名字
      * @return
      */
 	public static boolean isPicture(String picName) {
 		File f = new File(advertisementpath, picName);
 		File examine = new File(Tools.advertisementpath);
		if (!examine.exists()) {
			examine.mkdirs();
		}
		if (f.exists()) {
			return true;
		}
 		return false;
 	}
 	public static void saveBitmap(Bitmap bm) {
 		  File f = new File(advertisementpath, "fenxiang");
 		  if (f.exists()) {
 		   f.delete();
 		  }
 		  try {
 		   FileOutputStream out = new FileOutputStream(f);
 		   bm.compress(Bitmap.CompressFormat.PNG, 90, out);
 		   out.flush();
 		   out.close();
 		  } catch (FileNotFoundException e) {
 		   // TODO Auto-generated catch block
 		   e.printStackTrace();
 		  } catch (IOException e) {
 		   // TODO Auto-generated catch block
 		   e.printStackTrace();
 		  }

 		 }
}
