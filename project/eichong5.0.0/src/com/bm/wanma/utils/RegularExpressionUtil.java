package com.bm.wanma.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;



public class RegularExpressionUtil {
	
	private static String FirstFolder = "cm";// 一级目录
	public final static String ROOTPATH = Environment.getExternalStorageDirectory() + File.separator + FirstFolder + File.separator;
    //判断是否是电话号码
    public static boolean isMobilephone(String str) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
    /** 
     * 电话号码验证 
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isPhone(String str) {   
        Pattern p1 = null,p2 = null,p3 = null;  
        Matcher m = null;  
        boolean b = false;    
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
        p2 = Pattern.compile("^[0][1-9]{2,3}[0-9]{5,10}$");  // 验证带区号的-  
        p3 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的  
        if(str.length() >9){   
        	if(str.contains("-")){
        		m = p1.matcher(str);  
                b = m.matches();  
        	}else {
        		m = p2.matcher(str);  
                b = m.matches();  
        	}
        	  
        }else{  
            m = p3.matcher(str);  
            b = m.matches();   
        }    
        return b;  
    }  
    //验证密码长度 6-8为纯数字
    public static boolean isPasswordLength(String str){
       // Pattern pattern = Pattern.compile("^[A-Za-z0-9]{6,8}$");
    	Pattern pattern = Pattern.compile("^\\d{6,8}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
    //验证密码长度 6位纯数字
    public static boolean isPassword6Length(String str){
    	Pattern pattern = Pattern.compile("^\\d{6}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isFeelNumber(String str){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]{3,15}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
    //过滤特殊字符
    @SuppressWarnings("static-access")
	public static String stringFliter(String ttt){
    	String regEx = "^[A-Za-z\\d\\u4E00-\\u9FA5\\p{P}‘’“”]+$";
    	 Pattern p = Pattern.compile(regEx);  
    	 StringBuilder sb = new StringBuilder(ttt); 
    	  for (int i = ttt.length() - 1; i > 0; i--) {  
	            if (!p.matches(regEx, String.valueOf(ttt.charAt(i)))) {  
	                sb.deleteCharAt(i);  
	            }  
	        }
    	
    	return sb.toString();
    }

    public static boolean isPassword1(String str){
        Pattern pattern = Pattern.compile("^[0-9]{6}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumber(String str){
        Pattern pattern = Pattern.compile("^[0-9]{1,5}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLocalPhone(String str){
        Pattern pattern = Pattern.compile("^[0-9]{8,11}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    //验证是否是邮编
    public static boolean isPostcode(String str){
        Pattern pattern = Pattern.compile("^[1-9]\\d{6}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
    //验证是否是邮编
    public static boolean isBirthday(String str){
        Pattern pattern = Pattern.compile("^\\d{4}-(?:0\\d|1[0-2])-(?:[0-2]\\d|3[01])$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }


    // 验证邮箱地址是否正确
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    //手机验证
    public static final String phoneValidate = "^(170|13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}";

    //邮箱验证
    public static final String emailValidate = "^[0-9a-zA-Z]+@([0-9a-z][0-9a-z-]+.)+[a-z]{2,3}$";

    //密码验证
    public static final String passwordValidate = "^[0-9a-zA-Z]{6,20}";

    //日期验证
    public static final String dateValidate = "/((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))/ig";

    //姓名验证
    public static final String truenameValidate = "[\\u4E00-\\u9FA5]{2,5}(?:·[\\u4E00-\\u9FA5]{2,5})*";

    //qq验证
    public static final String qqValidate = "^[1-9]\\d{3,10}";

    //昵称验证
    public static final String usernameValidate = "^[\\u4e00-\\u9fa5]{4,16}|[a-zA-Z0-9]{2,8}$";


    //地址验证
    public static final String addressValidate = "[\\u4E00-\\u9FA5A_a-zA-Z0-9]{6,30}";

    //店名验证
    public static final String sellerValidate = "[\\u4E00-\\u9FA5A_a-zA-Z0-9]{1,30}";

    public static boolean search(String str){
        String[] strs = str.split(" ");
        if(str.startsWith(" ")){
            return false;
        }else if(str.endsWith(" ")){
            return false;
        }else if(strs.length>1){
            return false;
        }else{
            return true;
        }
    }
    
    
    /**
	 * 验证邮政编码
	 * 
	 * @param post
	 * @return
	 */
	public static boolean checkPost(String post) {
		if (post.matches("[1-9]\\d{5}(?!\\d)")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否为视频频
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean fileIsVideo(String suffix) {
		String[] fileIsAudios = new String[] { ".3gp", ".avi", ".mp4", ".mpeg", ".mpe", ".mpg4", ".rmvb", ".3gp" };
		for (String s : fileIsAudios) {
			if (suffix.toLowerCase().endsWith(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否为音频
	 * 
	 * @param suffix
	 * @return
	 */
	public static boolean fileIsAudio(String suffix) {
		String[] fileIsAudios = new String[] { ".amr", ".mp3", ".ogg", ".mp2", ".m3u", ".m4a", ".m4b", ".m4p", ".wav", ".wma", ".wmv" };
		for (String s : fileIsAudios) {
			if (suffix.toLowerCase().endsWith(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否为图片
	 */
	public static boolean fileIsImage(String suffix) {
		String[] fileIsAudios = new String[] { ".jpg", ".png", ".jpeg" };
		for (String s : fileIsAudios) {
			if (suffix.toLowerCase().endsWith(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param in
	 *            要拷贝文件的流
	 * @param dest
	 *            拷贝后文件保存的路径
	 * @return
	 */
	public static boolean copyfile(InputStream in, File dest) {
		try {
			OutputStream out = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			System.out.println("文件复制成功.");
			return true;
		} catch (FileNotFoundException ex) {
			System.out.println("文件复制失败:" + ex.getMessage() + " in the specified directory.");
		} catch (IOException e) {
			System.out.println("文件复制失败:" + e.getMessage());
		}
		return false;
	}

	/**
	 * 创建系统文件夹，用来存放图片等
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isCreateRootPath(Context context) {
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		if (!sdCardExist) {// 如果不存在SD卡，进行提示
			Toast.makeText(context, "请插入外部SD存储卡", Toast.LENGTH_SHORT).show();
			return false;
		} else {// 如果存在SD卡，判断文件夹目录是否存在
			File dirFirstFile = new File(ROOTPATH);// 新建一级主目录
			if (!dirFirstFile.exists()) {// 判断文件夹目录是否存在
				dirFirstFile.mkdir();// 如果不存在则创建
			}
			return true;
		}
	}

	public static InputStream openStream(Context context, Uri uri) {
		InputStream is = null;
		try {
			is = context.getContentResolver().openInputStream(uri);
			Log.i("xx", "文件流打开成功，路径=" + uri.getPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return is;
	}

	public static void copyfile(InputStream in, String dtFile) {
		try {
			// File f1 = new File(srFile);
			File f2 = new File(dtFile);
			OutputStream out = new FileOutputStream(f2);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			System.out.println("File copied.");
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage() + " in the specified directory.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 根据uris获取路径
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getImagePathFromUriPath(Context context, Uri uri) {
		ContentResolver resolver = context.getContentResolver();
		String fileName = null;
		Uri filePathUri = uri;
		if (uri != null) {
			if (uri.getScheme().toString().compareTo("content") == 0) {
				// content://开头的uri
				Cursor cursor = resolver.query(uri, null, null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					fileName = cursor.getString(column_index); // 取出文件路径

					// Android 4.1 更改了SD的目录，sdcard映射到/storage/sdcard0
					if (!fileName.startsWith("/storage") && !fileName.startsWith("/mnt")) {
						// 检查是否有”/mnt“前缀
						fileName = "/mnt" + fileName;
					}
					cursor.close();
				}
			} else if (uri.getScheme().compareTo("file") == 0) // file:///开头的uri
			{
				fileName = filePathUri.toString();// 替换file://
				fileName = filePathUri.toString().replace("file://", "");
				int index = fileName.indexOf("/sdcard");
				fileName = index == -1 ? fileName : fileName.substring(index);
				if (!fileName.startsWith("/mnt")) {
					// 加上"/mnt"头
					// fileName += "/mnt";
				}
			}
		}
		return fileName;
	}

}
