package com.zjhcsoft.struc.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
public static void createfile(String content,String name){
	   FileOutputStream out = null;   

       FileOutputStream outSTr = null;   

       BufferedOutputStream Buff=null;   
 
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH");
       try {   


           outSTr = new FileOutputStream(new File("/data/work/weather_xml/"+name+".txt"));   

            Buff=new BufferedOutputStream(outSTr);   

           long begin0 = System.currentTimeMillis();   

           Buff.write(content.getBytes());

           Buff.flush();   

           Buff.close();   

         

       } catch (Exception e) {   

           e.printStackTrace();   

       }   

       finally {   

           try {   

               Buff.close();   

               outSTr.close();     

           } catch (Exception e) {   

               e.printStackTrace();   

           }   

       }   



}   
}
