package struc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cyberneko.html.parsers.DOMParser;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTRow;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ExcelParseTest {
	private POIFSFileSystem fs;
	private static XSSFWorkbook wb;
	private static XSSFSheet sheet;
	private static XSSFRow row;

	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param InputStream
	 * @return String 表头内容的数组
	 */
	public String[] readExcelTitle(InputStream is) {
		try {
			// fs = new POIFSFileSystem(is);
			wb = new XSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("colNum:" + colNum);
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			// title[i] = getStringCellValue(row.getCell((short) i));
			title[i] = getCellFormatValue(row.getCell((short) i));
		}
		return title;
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public Map<Integer, String> readExcelContent(InputStream is) {
		Map<Integer, String> content = new HashMap<Integer, String>();
		String str = "";
		try {
			// fs = new POIFSFileSystem(is);
			wb = new XSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			while (j < colNum) {
				// 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
				// 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
				// str += getStringCellValue(row.getCell((short) j)).trim() +
				// "-";
				str += getCellFormatValue(row.getCell((short) j)).trim()
						+ "    ";
				j++;
			}
			content.put(i, str);
			str = "";
		}
		return content;
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param xssfCell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private static String getStringCellValue1(XSSFCell xssfCell) {
		String strCell = "";
		switch (xssfCell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = xssfCell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(xssfCell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(xssfCell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		if (xssfCell == null) {
			return "";
		}
		return strCell;
	}

	private static String getStringCellValue(XSSFCell xssfCell) {
		return xssfCell.toString().replaceAll(" ", "").replaceAll("\n", "");
	}

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getDateCellValue(XSSFCell cell) {
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
						+ "-" + date.getDate();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				result = "";
			}
		} catch (Exception e) {
			System.out.println("日期格式不正确!");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(XSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}

	@SuppressWarnings("unused")
	public static void main1(String[] args) {

		try {
			InputStream is = new FileInputStream(
					"C:\\Users\\Administrator\\Desktop\\2014123017055097556.xlsx");
			wb = new XSSFWorkbook(is);
			sheet = wb.getSheetAt(0);
			// 得到总行数
			int rowNum = sheet.getLastRowNum();
			String ent_name = null;
			String region_name = null;
			String type_name = null;
			String site_name = null;
			String monitor_time = null;
			String mul = null;
			List<Integer> position_list = new ArrayList<Integer>();
			for (int i = 0; i <= rowNum; i++) {
				row = sheet.getRow(i);
				if (row.getPhysicalNumberOfCells() <= 1) {
					continue;
				}
				// if ("".equals(getStringCellValue(row.getCell(1)))
				// && "".equals(getStringCellValue(row.getCell(2)))
				// && "".equals(getStringCellValue(row.getCell(3)))) {
				// continue;
				// }
				XSSFCell th_cell = row.getCell(0);
				// 去掉第一行表头 并且获取数据所在的列位置
				if ("企业名称".equals(getStringCellValue(th_cell))) {
					position_list.clear();
					XSSFRow first_row = sheet.getRow(i + 1);
					int check_point = 0;
					for (int k = 0; k < first_row.getLastCellNum(); k++) {
						if ("停产".equals(getStringCellValue(first_row.getCell(k)))) {
							check_point = k;
							continue;
						}
						if (!"".equals(getStringCellValue(first_row.getCell(k)))) {
							position_list.add(k);
						}
					}
					// 处理首行为停产的情况
					if (check_point != 0) {

						// 避免循环进标准字段
						int last_index = position_list
								.get(position_list.size() - 1);

						int row_index = i + 2;
						while (position_list.size() < 10) {
							XSSFRow row = sheet.getRow(row_index);
							for (int temp = check_point; temp < last_index; temp++) {

								if ("停产".equals(getStringCellValue(row
										.getCell(temp)))) {
									continue;
								}
								if (!"".equals(getStringCellValue(row
										.getCell(temp)))) {
									position_list.add(temp);
								}
							}
							row_index++;
						}
					}
					continue;
				}
				// 判断最长的一行 超标可能为空特殊判断
				// if(position_list.size()<11){
				// int m = position_list.get(position_list.size()-1);
				// int n = position_list.get(position_list.size()-2);
				// if(m-n==2){
				// position_list.add(m-1);
				// }else{
				// //TO-DO
				// }
				// }

				if (!"".equals(getStringCellValue(row.getCell(position_list
						.get(0))))) {
					ent_name = getStringCellValue(row.getCell(position_list
							.get(0)));
				}
				if (!"".equals(getStringCellValue(row.getCell(position_list
						.get(1))))) {
					region_name = getStringCellValue(row.getCell(position_list
							.get(1)));
				}
				if (!"".equals(getStringCellValue(row.getCell(position_list
						.get(2))))) {
					type_name = getStringCellValue(row.getCell(position_list
							.get(2)));
				}
				if (!"".equals(getStringCellValue(row.getCell(position_list
						.get(3))))) {
					site_name = getStringCellValue(row.getCell(position_list
							.get(3)));
				}
				if (!"".equals(getStringCellValue(row.getCell(position_list
						.get(4))))) {
					monitor_time = getStringCellValue(row.getCell(position_list
							.get(4)));
				}
				if (!"".equals(getStringCellValue(row.getCell(position_list
						.size() - 1)))) {
					mul = getStringCellValue(row
							.getCell(position_list.size() - 1));
				}

				String item = getStringCellValue(row.getCell(position_list
						.get(5)));
				String pol = getStringCellValue(row.getCell(position_list
						.get(6)));
				String standard = getStringCellValue(row.getCell(position_list
						.get(7)));
				String is_standard = getStringCellValue(row
						.getCell(position_list.get(8)));
				System.out.println(ent_name + " " + region_name + " "
						+ type_name + " " + site_name + " " + monitor_time
						+ " " + item + " " + pol + " " + standard + " "
						+ is_standard);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<String> initial(Element el) {
		List<String> list = new ArrayList<String>();
		Integer rowspan = 1;
		if (el.attributeValue("rowspan") != null) {
			rowspan = Integer.valueOf(el.attributeValue("rowspan"));
		}
		for (int i = 0; i < rowspan; i++) {
			list.add(el.getStringValue().trim());
		}
		return list;
	}

	private static void parselist(List<List<String>> rowlist,
			List<Element> list, int row_index, int max_row) {

		Integer minrow = selectminrow(rowlist);
		List<Integer> insertlist = selectmincol(rowlist, minrow);
		if (insertlist == null) {
			for (Element el : list) {
				Integer colspan = 1;
				if (el.attributeValue("colspan") != null) {
					colspan = Integer.valueOf(el.attributeValue("colspan"));
				}
				for (int j = 0; j < colspan; j++) {
					rowlist.add(initial(el));
				}
			}
		} else {
			for (int i = 0; i < insertlist.size(); i++) {
				int rm = saveElement(rowlist, insertlist.get(i), list.get(i),
						row_index, max_row);
				for (int m = 1; m < rm; m++) {
					insertlist.remove(i + 1);
				}
			}
		}
	}

	private static int saveElement(List<List<String>> rowlist, int position,
			Element el, int row_index, int max_row) {
		Integer rowspan = 1;
		Integer colspan = 1;
		if (el.attributeValue("rowspan") != null) {
			rowspan = Integer.valueOf(el.attributeValue("rowspan"));
		}
		if (el.attributeValue("colspan") != null) {
			colspan = Integer.valueOf(el.attributeValue("colspan"));
		}
		for (int j = 0; j < colspan; j++) {
			for (int i = 0; i < rowspan; i++) {
				String value = el.getStringValue().replaceAll(" ", "").trim();
				value =judge_bottom_top(value, row_index, max_row, rowspan);
				rowlist.get(position).add(value);
			}
			position++;
		}
		return colspan;
	}

	// 选出行数为最小的列
	private static List<Integer> selectmincol(List<List<String>> rowlist,
			Integer i) {
		List<Integer> insertlist = new ArrayList<Integer>();
		if (rowlist.size() == 0) {
			return null;
		}
		for (int j = 0; j < rowlist.size(); j++) {
			if (rowlist.get(j).size() == i) {
				insertlist.add(j);
			}
		}
		return insertlist;
	}

	// 计算出最小的行数
	private static Integer selectminrow(List<List<String>> rowlist) {
		int i = 10000;
		if (rowlist.size() == 0) {
			return 0;
		}
		for (int j = 0; j < rowlist.size(); j++) {
			int temp = rowlist.get(j).size();
			if (temp < i) {
				i = temp;
			}
		}
		return i;
	}

	@SuppressWarnings("unused")
	private static void printlist(List<List<String>> list) {
		for (int j = 0; j < list.get(0).size(); j++) {
			for (int i = 0; i < list.size(); i++) {
				System.out.print(list.get(i).get(j) + " ");
			}
			System.out.print("\n");
		}
	}

	private static String judge_bottom_top(String value, int row_index,
			int max_row, int rowspan) {
		if ("".equals(value) || value == null) {
			if (max_row == (row_index + rowspan - 1) || max_row == row_index) {
				return "bot";
			}
			if (row_index == 2) {
				return "top";
			}
			return value;
		}
		return value;
	}
	
	private static void top_deal(List<String> list){
		for(int i = 0 ;i < list.size()-1;i++){
			if("top".equals(list.get(i+1))){
				if(!"bot".equals(list.get(i))){
					list.set(i+1,list.get(i));
				}else{
					list.set(i+1,"");
				}
			}
		}
	}
	private static void bot_deal(List<String> list){
		if("bot".equals(list.get(list.size()-1))){
			list.set(list.size()-1, "");
		}
		for(int i = list.size()-1 ;i > 0;i--){
			if("bot".equals(list.get(i-1))){
				if(!"top".equals(list.get(i))){
					list.set(i-1,list.get(i));
				}else{
					list.set(i-1,"");
				}
			}
		}
	}
	public static void main(String[] args) {
		try {
			InputStream is;
			is = new FileInputStream(
					"C:\\Users\\Administrator\\Desktop\\组合 1.html");
			DOMParser parser = new DOMParser();

			// 使用nekohtml对源码进行清理、补全等处理
			parser.setFeature("http://xml.org/sax/features/namespaces", false);
			parser.setProperty(
					"http://cyberneko.org/html/properties/default-encoding",
					"utf-8");
			parser.parse(new InputSource(is));
			Document document = new DOMReader().read(parser.getDocument());

			List<List<String>> rowlist = new ArrayList<List<String>>();
			List<Element> tablelist = document.selectNodes("/HTML/BODY/TABLE");

			for (int i = 1; i <= tablelist.size(); i++) {
				List<Element> trlist = document.selectNodes("/HTML/BODY/TABLE["
						+ i + "]/TBODY/TR");
				for (int j = 2; j <= trlist.size(); j++) {
					List<Element> tdlist = document
							.selectNodes("/HTML/BODY/TABLE[" + i
									+ "]/TBODY/TR[" + j + "]/TD");
					try {
						parselist(rowlist, tdlist, j, trlist.size());
					} catch (Exception e) {
						System.out.print(i + " " + j);
						System.exit(0);
					}
				}
			}
			for(int k=0;k<rowlist.size();k++){
				top_deal(rowlist.get(k));
				bot_deal(rowlist.get(k));
			}
			printlist(rowlist);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
		}

	}
}
