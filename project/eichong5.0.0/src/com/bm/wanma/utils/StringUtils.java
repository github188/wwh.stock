package com.bm.wanma.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;

import com.google.gson.Gson;
/**
 * 字符串处理工具类
 */
public class StringUtils {

    /**
     * 从流中读取字符串
     * @param input
     * @return
     * @throws IOException
     */
    public static String readString(InputStream input) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;    
        while((len=(input.read(buffer))) != -1){  
            baos.write(buffer,0,len);    
        }
        String retStr = new String(baos.toByteArray());
        input.close();
        return retStr;
    }

    public static boolean validatePhone(String phone) {
        if (phone==null || phone.length()==0)
            return false;
        String pattern = "^1[3,4,5,6,8]+\\d{9}$";
        return phone.matches(pattern);
    }
    
  //将字符串写入到文本文件中
    public static void WriteStringToFile(String strcontent,String strFilePath)
    {
      //每次写入时，都换行写
      String strContent=strcontent+"\n";
      try {
           File file = new File(strFilePath);
           if (!file.exists()) {
            file.createNewFile();
           }
           RandomAccessFile raf = new RandomAccessFile(file, "rw");
           raf.seek(file.length());
           raf.write(strContent.getBytes());
           raf.close();
      } catch (Exception e) {
          }
    }
    
    
    
    
    
    
    /**
     * 转换成map
     * @return
     */
    public static HashMap<String, String> toMap(Object obj){
        Gson g = new Gson();
        return g.fromJson(g.toJson(obj), HashMap.class);
    }
}
