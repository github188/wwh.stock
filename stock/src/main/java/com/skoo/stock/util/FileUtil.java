package com.skoo.stock.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.skoo.common.SystemConfig;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FileUtil {
	//public static String path = "C:/Users/Administrator/Desktop/had_tag/ebusiness/";
	public static String path = System.getProperty("stock")+"/WEB-INF/logs/";
    //request.getSession().getServletContext().getRealPath("/")+this.getServletContect().getRealPath("/");

    public static List<String[]> readFile(String fName){ 
    	List<String[]> list = new LinkedList<String[]>();
    	//String fileName = path + fName;
	    try{
		    String encoding = "gbk";
		    File file = new File(fName);
		    String lineinfo="";
		    if(file.isFile()&&file.exists()){
			    InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
			    BufferedReader bufferReader = new BufferedReader(read);
			    while((lineinfo=bufferReader.readLine())!=null){
				    StringTokenizer stk = new StringTokenizer(lineinfo, "\t") ;//被读取的文件的字段以tab分隔
				    String[] strArrty = new String[stk.countTokens()];
				    int i=0;
				    while(stk.hasMoreTokens()){
				    	strArrty[i++]=stk.nextToken().trim();
				    }
				
				    list.add(strArrty);
			    }
			    read.close();
		    }
	    }catch(Exception e){
		    System.out.println("读取文件内容出错");
		    e.printStackTrace();
	    }

	    return list;
    }     

    public static List<String> readStockFile(){
    	List<String> list = new ArrayList<String>();
    	String fileName = path + System.getProperty("historyFilename");
	    try{
		    String encoding = "utf-8";
		    File file = new File(fileName);
		    String lineinfo;
		    if(file.isFile()&&file.exists()){
			    InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
			    BufferedReader bufferReader = new BufferedReader(read);
			    while((lineinfo=bufferReader.readLine())!=null){
				    list.add(lineinfo);
			    }
			    read.close();
		    }

            if (list.size() > 0) writeFile();
	    }catch(Exception e){
		    System.out.println("读取文件内容出错");
		    e.printStackTrace();
	    }

	    return list;
    }
    
    public static List<String> readStockFile(String fName){
    	List<String> list = new ArrayList<String>();
    	String fileName = path + fName;
        if (fileName.indexOf(".")<=0) fileName += ".txt";
	    try{
		    String encoding = "utf-8";
		    File file = new File(fileName);
		    String lineinfo="";
		    if(file.isFile()&&file.exists()){
			    InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
			    BufferedReader bufferReader = new BufferedReader(read);
			    while((lineinfo=bufferReader.readLine())!=null){
				    list.add(lineinfo);
			    }
			    read.close();
		    }
	    }catch(Exception e){
		    System.out.println("读取文件内容出错");
		    e.printStackTrace();
	    }

	    return list;
    }

    public static void writeFile() throws IOException {
		FileWriter fw = null;
		BufferedWriter bw = null;
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String fileName = path + SystemConfig.INSTANCE.getValue("historyFilename");
		try {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw, 100);
            Iterator iter = map.entrySet().iterator();  
            while (iter.hasNext()) {  
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                String val = (String)entry.getValue();  

                bw.write(key+"\t"+val+"\n");
			}
		} catch (IOException e) {
			System.out.println("写入文件出错");
		} finally {
			if (bw != null) {
				bw.flush();
				bw.close();
			}
			if (fw != null)
				fw.close();
		}
    }

    public static void writeFile(LinkedHashMap<String, String> map,String fName) throws IOException {
		FileWriter fw = null;
		BufferedWriter bw = null;
		String fileName = path + fName;
		try {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw, 100);
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                String val = (String)entry.getValue();

                bw.write(key+"\t"+val+"\n");
			}
		} catch (IOException e) {
			System.out.println("写入文件出错");
		} finally {
			if (bw != null) {
				bw.flush();
				bw.close();
			}
			if (fw != null)
				fw.close();
		}
    }

    /**
     * 输出Excel文件，输出格式为多行多列 
     * @param map 
     */  
    public static void createExcel(Map<String, String[]> map,String fName) {  
        try {  
            // 新建一输出文件流  
        	String fileName = path+fName+".xls";
            File file = new File(fileName);
            HSSFWorkbook workbook;
            HSSFSheet sheet;
            String shName = "指数";
            if (file.exists()) {  
                FileInputStream fis = new FileInputStream(file);
                workbook = new HSSFWorkbook(fis);
                sheet = workbook.getSheet(shName);
                if (sheet==null) sheet = workbook.createSheet(shName);
            } else {
	            // 创建新的Excel 工作簿  
	            workbook = new HSSFWorkbook();  
	            // 在Excel工作簿中建一工作表，其名为缺省值  
	            sheet = workbook.createSheet(shName);  
            }
            HSSFRow row = null;  
            // 在索引0的位置创建单元格（左上端）  
            HSSFCell cell1 = null;  
            HSSFCell cell2 = null;  
  
            Iterator iter = map.entrySet().iterator();
            int i = 0;  
  
            while (iter.hasNext()) {  
                Map.Entry entry = (Map.Entry) iter.next();  
                Object key = entry.getKey();  
                String[] val = (String[])entry.getValue();  
                row = sheet.createRow((short) i++);  
                cell1 = row.createCell((short) 0);  
                // 定义单元格为字符串类型  
                //cell1.setCellType(HSSFCell.CELL_TYPE_STRING);  
  
                // 在单元格中输入一些内容  
                if (isNumericEx(key.toString())) {
                	cell1.setCellValue(Integer.valueOf(key.toString()));
                } else {
                	cell1.setCellValue(key.toString());
                }
                
                for (int j=0;j<val.length;j++) {
                    cell2 = row.createCell((short) j+1);  
                    //cell2.setCellType(HSSFCell.CELL_TYPE_STRING);  
                    if (isNumericEx(val[j])) {
                    	cell2.setCellValue(Double.parseDouble(val[j]));
                    } else {
                    	cell2.setCellValue(val[j]);
                    }
                }
            }  
  
            // 把相应的Excel 工作簿存盘  
            FileOutputStream fOut = new FileOutputStream(fileName);
            FileChannel fc = fOut.getChannel();
            FileLock lock = fc.tryLock();
            if (lock == null)
            {
            	Runtime.getRuntime().exec("cmd  /c  end  path+fName");
                return;
            }

            workbook.write(fOut);  
            fOut.flush();  
            // 操作结束，关闭文件  
            fOut.close();  
            System.out.println("文件生成...");  
  
        } catch (Exception e) {  
            System.out.println("出现异常: " + e);  
        }  
    }  

    /** 
     * 输出Excel文件，输出格式为多行多列 
     * @param map 
     */  
    public static void createExcelHist(Map<String, String[]> map,String fName) {  
        try {  
            // 新建一输出文件流  
        	String fileName = path+fName+".xls";
            File file = new File(fileName);
            HSSFWorkbook workbook;
            HSSFSheet sheet;
            Iterator iter = map.entrySet().iterator();
            String shName = "指数";
            String[] val = null;
            if (iter.hasNext()) {
	            Map.Entry entry = (Map.Entry) iter.next();  
	            Object key = entry.getKey();
	            val = (String[])entry.getValue();
	            if ("stock".equals(fName)) {
		            shName = val[0].substring(0, val[0].indexOf(")")+1);
	            } else {
	            	shName = key.toString();
	            }
            }
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new HSSFWorkbook(fis);
                sheet = workbook.getSheet(shName);
                if (sheet==null) sheet = workbook.createSheet(shName);
            } else {
	            // 创建新的Excel 工作簿  
	            workbook = new HSSFWorkbook();  
	            // 在Excel工作簿中建一工作表，其名为缺省值  
	            sheet = workbook.createSheet(shName);  
            }
            HSSFRow row = null;  
            // 在索引0的位置创建单元格（左上端）  
            HSSFCell cell1 = null;  
            HSSFCell cell2 = null;  
  
            int i = 0;  
            if (!"stock".equals(fName)) {
                row = sheet.createRow((short) i++);  
                cell1 = row.createCell((short) 0);  
                // 在单元格中输入一些内容  
                cell1.setCellValue("序号");  
                
                for (int j=0;j<val.length;j++) {
                    cell2 = row.createCell((short) j+1);
                    //cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell2.setCellValue(val[j]);
                    if (j > 255) break;
                }
           }
            while (iter.hasNext()) {  
                Map.Entry entry = (Map.Entry) iter.next();  
                Object key = entry.getKey();  
                val = (String[])entry.getValue();  
                row = sheet.createRow((short) i++);
                cell1 = row.createCell((short) 0);  
                // 定义单元格为字符串类型  
                //cell1.setCellType(HSSFCell.CELL_TYPE_STRING);  
  
                // 在单元格中输入一些内容  
                cell1.setCellValue(key.toString());

                for (int j=0;j<val.length;j++) {
                    cell2 = row.createCell((short) j+1);
                    //cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell2.setCellValue(val[j]);
                    if (j > 255) break;
                }
            }  
  
            // 把相应的Excel 工作簿存盘  
            FileOutputStream fOut = new FileOutputStream(fileName);
            workbook.write(fOut);  
            fOut.flush();  
            // 操作结束，关闭文件  
            fOut.close();  
            System.out.println("文件生成...");  
  
        } catch (Exception e) {  
            System.out.println("出现异常: " + e);  
        }  
    }  
    /** 
     * 使用Pattern和Matcher类的方法 
     * @param s 
     * @return 
     */  
    public static boolean isNumber(String s){  
      
        String regex = "^[1-9][0-9]*\\.[0-9]+$|^[1-9][0-9]*$|^0+\\.[0-9]+$";  
        Pattern pattern = Pattern.compile(regex);  
        char c = s.charAt(0);  
        if(c=='+'||c=='-'){  
            s = s.substring(1);  
        }  
        Matcher matcher = pattern.matcher(s);  
        boolean bool = matcher.matches();
        return bool;
    }  
    public static boolean isNumericEx(String str) {
        try {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }
/*
    *//** 
     * 更新Excel文件，输出格式为多行多列 
     * @param map 
     *//*  
    public static void updateExcel(Map<String, String[]> map,String fName) {  
    	Workbook book = null;
        try {  
        	File file = new File(path+fName);
            // Excel获得文件  
            Workbook wb = Workbook.create(file);  
            // 打开一个文件的副本，并且指定数据写回到原文件  
            book = WorkbookFactory.createWorkbook(file, wb);  
            Sheet sheet = book.getSheetAt(0);  
            WritableSheet wsheet = book.getSheet(0);  
            HSSFRow row = null;  
            // 在索引0的位置创建单元格（左上端）  
            HSSFCell cell1 = null;  
            HSSFCell cell2 = null;  
  
            Iterator iter = map.entrySet().iterator();  
            int i = 0;  
  
            while (iter.hasNext()) {  
                Map.Entry entry = (Map.Entry) iter.next();  
                Object key = entry.getKey();  
                String[] val = (String[])entry.getValue();  
                row = sheet.createRow((short) i++);  
                cell1 = row.createCell((short) 0);  
                // 定义单元格为字符串类型  
                cell1.setCellType(HSSFCell.CELL_TYPE_STRING);  
  
                // 在单元格中输入一些内容  
                cell1.setCellValue(key.toString());  
                
                for (int j=0;j<val.length;j++) {
                    cell2 = row.createCell((short) j+1);  
                    cell2.setCellType(HSSFCell.CELL_TYPE_STRING);  
                    cell2.setCellValue(val[j]);  
                }
  
                if (i > 255) {  
                    break;  
                }  
            }  
  
            // 把相应的Excel 工作簿存盘  
            FileOutputStream fOut = new FileOutputStream(fileName);  
            workbook.write(fOut);  
            fOut.flush();  
            // 操作结束，关闭文件  
            fOut.close();  
            System.out.println("文件生成...");  
  
        } catch (Exception e) {  
            System.out.println("出现异常: " + e);  
        }  
    }  
*/}
