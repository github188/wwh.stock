package cn.hzstk.securities.util;

import cn.hzstk.securities.common.Constant;
import cn.hzstk.securities.common.constants.SystemProperties;
import net.ryian.commons.DateUtils;
import net.ryian.core.SystemConfig;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    protected static Logger logger = Logger.getLogger(Constant.LOG_ERROR);
	public static String path = System.getProperty("log_path")+"/logs/";
    //request.getSession().getServletContext().getRealPath("/")+this.getServletContect().getRealPath("/");

    public static List<String[]> readFile(String fName){ 
    	List<String[]> list = new LinkedList<>();
    	//String fileName = path + fName;
	    try{
            File file = getFile(fName);
            if (!isExistFile(file)) return list;
		    String lineInfo;
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), Constant.GBK);
            BufferedReader bufferReader = new BufferedReader(read);
            while((lineInfo =bufferReader.readLine())!=null){
                StringTokenizer stk = new StringTokenizer(lineInfo, "\t") ;//被读取的文件的字段以tab分隔
                String[] strArrty = new String[stk.countTokens()];
                int i=0;
                while(stk.hasMoreTokens()){
                    strArrty[i++]=stk.nextToken().trim();
                }

                if (strArrty.length > 0) list.add(strArrty);
            }
            read.close();
	    }catch(Exception e){
		    logger.info("读取文件内容出错");
		    e.printStackTrace();
	    }

	    return list;
    }

    public static boolean deleteFile(String fName) {
        File file = getFile(fName);

        return !isExistFile(file) || file.delete();
    }

    private static File getFile(String fName){
        String filename = getDefaultPath(fName);

        return new File(filename);
    }

    private static void closeFile(String fName){
        File file = getFile(fName);

        //return file.deleteOnExit();
    }

    private static boolean isExistFile(File file){
        return file != null;
    }
    public static String getDefaultPath(String fName){
        String tempPath = SystemConfig.INSTANCE.getValue(SystemProperties.ZS_INSTALL_PATH)+SystemConfig.INSTANCE.getValue(SystemProperties.FILE_DOWNLOAD_PATH);
        if (fName.indexOf(".") > 0) {
            tempPath += fName;
        } else {
            String tmp = DateUtils.format(new Date(), Constant.DATE_FORMAT8_STR);
            tempPath += fName + tmp + Constant.FILE_SUFFIX_TXT;
        }

        return tempPath;
    }

    public static boolean isExistFile(String fName){
        File file = getFile(fName);
        return file.exists();

    }

    public static boolean isRunning(String processName) {
        BufferedReader reader = null;
        try {
            Process pro = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq "+ processName+ "\"");
            reader = new BufferedReader(new InputStreamReader(pro.getInputStream(), Constant.GBK));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(processName)) {
                    return true;
                }
            }
            return false;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<String> readStockFile(){
    	List<String> list = new ArrayList<>();
    	String fileName = path + "logFile.log";
	    try{
		    File file = new File(fileName);
		    String lineInfo;
		    if(file.isFile()&&file.exists()){
			    InputStreamReader read = new InputStreamReader(new FileInputStream(file), Constant.UTF8);
			    BufferedReader bufferReader = new BufferedReader(read);
			    while((lineInfo =bufferReader.readLine())!=null){
				    list.add(lineInfo);
			    }
			    read.close();
		    }

            if (list.size() > 0) writeFile();
	    }catch(Exception e){
		    logger.info("读取文件内容出错");
		    e.printStackTrace();
	    }

	    return list;
    }

    public static void writeFile() throws IOException {
		FileWriter fw = null;
		BufferedWriter bw = null;
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        String fileName = path + "logFile.log";
		try {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw, 100);
            for (Object o : map.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                Object key = entry.getKey();
                String val = (String) entry.getValue();

                bw.write(key + "\t" + val + "\n");
            }
		} catch (IOException e) {
			logger.info("写入文件出错");
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
    public static final int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0])
                .intValue();
    }
    public static void main(String[] args) {
        System.out.println("PID:" + getProcessID());

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
