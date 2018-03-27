package cn.hzstk.securities.util;

import cn.hzstk.securities.common.Constant;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class ExcelUtil {
    static private Workbook wb;
    static private Sheet sheet;
    static private Row row;

    /**
     *
     * @method ：readExcelTitle<br>
     * @describe ：读取 Excel 文件<br>
     * @author ：wanglongjie<br>
     * @createDate ：2015年8月31日下午2:41:25 <br>
     * @param fileName
     *            ：Excel 文件路径
     * @return String[]
     */
    public static String[] readExcelTitle(String fileName) {
        InputStream is;
        try {
            is = new FileInputStream(fileName);
            /*String postfix = fileName.substring(fileName.lastIndexOf("."),
                    fileName.length());
            if (postfix.equals(".xls")) {
                // 针对 2003 Excel 文件
                wb = new HSSFWorkbook(new POIFSFileSystem(is));
                sheet = wb.getSheetAt(0);
            } else {*/
                // 针对2007 Excel 文件
                wb = new XSSFWorkbook(is);
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);// 获取第一行（约定第一行是标题行）
        int colNum = row.getPhysicalNumberOfCells();// 获取行的列数
        String[] titles = new String[colNum];
        for (int i = 0; i < titles.length; i++) {
            titles[i] = getCellFormatValue(row.getCell(i));
        }
        return titles;
    }

    /**
     *
     * @method ：readExcelContent<br>
     * @describe ：读取 Excel 内容<br>
     * @author ：wanglongjie<br>
     * @createDate ：2015年8月31日下午3:12:06 <br>
     * @param fName
     *            ：Excel 文件路径
     * @return List<Map<String,String>>
     */
    public static List<Map<String, String>> readExcelContent(String fName) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> content;
        String fileName = FileUtil.getDefaultPath(fName);
        try {
            InputStream is;
            is = new FileInputStream(fileName);
            /*String postfix = fileName.substring(fileName.lastIndexOf("."),
                    fileName.length());
            if (postfix.equals(".xls")) {
                // 针对 2003 Excel 文件
                wb = new HSSFWorkbook(new POIFSFileSystem(is));
                sheet = wb.getSheetAt(0);
            } else {*/
                // 针对2007 Excel 文件
                wb = new XSSFWorkbook(is);
            /*ExcelExtractor extractor = new ExcelExtractor(wb);
            extractor.setFormulasNotResults(true);
            extractor.setIncludeSheetNames(false);
            body = extractor.getText();*/
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();// 得到总行数
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        String titles[] = readExcelTitle(fileName);
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            int j = 0;
            row = sheet.getRow(i);
            content = new LinkedHashMap<>();
            do {
                content.put(titles[j], getCellFormatValue(row.getCell(j))
                        .trim());
                j++;
            } while (j < colNum);
            list.add(content);
        }
        return list;
    }

    /**
     * 根据Cell类型设置数据
     *
     * @param cell
     * @return
     */
    private static String getCellFormatValue(Cell cell) {
        String cellvalue;
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_NUMERIC:
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    } else {
                        // 如果是纯数字取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case Cell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    // 默认的Cell值
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    public static List<String[]> readExcelSheet(String fileName, int sheetNo) {
        List<String[]> list = new LinkedList<>();
        InputStream is;
        String fName = FileUtil.getDefaultPath(fileName);
        try {
            is = new FileInputStream(fName);
            wb = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (("投资日历" + Constant.FILE_SUFFIX_XLS).equals(fileName)) {
            sheet = wb.getSheetAt(wb.getNumberOfSheets() + sheetNo - 2);
        } else {
            sheet = wb.getSheetAt(sheetNo);
        }
        int rowNum = sheet.getLastRowNum();// 得到总行数
        for (int i = 0; i < rowNum + 1; i++) {
            row = sheet.getRow(i);
            int colNum = row.getPhysicalNumberOfCells();// 获取行的列数
            String[] content = new String[colNum];
            for (int j = 0; j < colNum; j++) {
                content[j] = getCellFormatValue(row.getCell(j));
            }
            list.add(content);
        }
        return list;
    }

    public static void main(String[] args) {
        String file = "投资日历" + Constant.FILE_SUFFIX_XLS;
        List<Map<String, String>> list = readExcelContent(file);
        Map<String, String> map;
        for (int i = 0; i < list.size(); i++) {
            map = list.get(i);
            Entry<String, String> entry;
            for (Iterator<Entry<String, String>> it = map.entrySet().iterator(); it
                    .hasNext();) {
                entry = it.next();
                System.out.println(entry.getKey() + "-->" + entry.getValue());
            }
            System.out.println("............");
        }
    }

}
