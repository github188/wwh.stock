package cn.hzskt.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.zjhcsoft.smartcity.magic.common.SystemConfig;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class stockList {  
	public static String path = "D:/wwh/stock";
	public static String[] title = {"指数名称","当前点数","当前价格","涨跌率","成交量（手）","成交额（万元）"};
	public static String[] stockTitle = {"股票名字","今 开","昨 收","当前价格","最高","最低","买一","卖一","成交量","成交额"
    		,"买一(股)","买一(价)","买二(股)","买二(价)","买三(股)","买三(价)","买四(股)","买四(价)","买五(股)","买五(价)"
    		,"卖一(股)","卖一(价)","卖二(股)","卖二(价)","卖三(股)","卖三(价)","卖四(股)","卖四(价)","卖五(股)","卖五(价)","日期","时间"};
	  
    /** 
     * @param args 
     * @throws IOException 
     */  
    public static void main(String[] args) throws IOException {  
    	Document doc;
    	try {
/*            String url = "http://cj.gw.com.cn/news/stock/601288.shtml";  
            File file = new File("C:/Users/Administrator/Desktop/had_tag/myjsoup/dazhihui/dazhihui.txt");  
            File newFile = new File("C:/Users/Administrator/Desktop/had_tag/myjsoup/dazhihui/newdazhihui.txt");  
            boolean mark = downloadPage(url, file);  
            System.out.println(mark);  
            boolean mark2 = replaceAllFileString(file, newFile, " ", "");  
            System.out.println(mark2);  
            ArrayList<String> list = resolvePageText(newFile);  
            for(int i = 0; i < list.size(); i++){  
                System.out.println(list.get(i));  
            }  
*/
    		//getStock("002639");
	    	//获取文档
    		/*Connection conn=null;
    		Statement st=null;
    		if (conn==null || conn.isClosed()){
    		String dburl = "jdbc:mysql://dbserver_ip:3306/dbname";
    		Class.forName( "org.gjt.mm.mysql.Driver" );
    		conn = DriverManager.getConnection(dburl, "root", "password");
    		st = conn.createStatement(); 
    		}
    		ResultSet rs = st.executeQuery(" sysdate()");
    		rs.next();
    		System.out.println(String.valueOf(rs.getObject(1)));*/

    		getSinajs();
/*	    	doc=Jsoup.connect("http://quote.eastmoney.com/center/list.html#26").get();
	    	Elements links = doc.select("[class=gridview]");
	        for (Element link : links) {
	        	System.out.println(link.attr("abs:href") + "\t" + trim(link.text(), 35));
	        }*/
	        /*
            Long start = System.currentTimeMillis();
            System.out.println(new Date());
        	String addr = "http://www.jd.com";
        	int ii = 0;
        	String fileName = addr.split("\\.")[1]+String.valueOf(ii)+".txt";
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
            String level="";

        	List<String[]> listAddr = FileUtil.readFile(fileName);
        	String[] outArray =null;
    	    for(int i=0;i<listAddr.size();i++){
    	    	outArray = (String[])listAddr.get(i);
    	    	if (outArray.length < 2) continue;
    	    	if (outArray.length == 3) level = "div."+outArray[2]+" ";
    	    	level += "a[href]";
    	    	doc=Jsoup.connect(outArray[0]).get();
    	    	Elements links = doc.select(level);
    	        for (Element link : links) {
    	        	if (!map.containsKey(link.attr("abs:href"))) map.put(link.attr("abs:href"),outArray[1]+"|"+trim(link.text(), 35));
    	        	//System.out.println(link.attr("abs:href") + "\t" + trim(link.text(), 35));
    	        }

    	        if (map.containsKey(outArray[0])) map.remove(outArray[0]);
    	    }

    	    fileName = addr.split("\\.")[1]+String.valueOf(ii+1)+".txt";
            FileUtil.writeFile(map, fileName);

            Long end = System.currentTimeMillis();
            System.out.println("time:"+(end-start)/1000);
*/    		
	    	 
/*	    	*//*****获取单一元素******//*
	    	//与JS类似的根据ID选择的选择器<div id="content"></div>
	    	Element content = doc.getElementById("_JD_ALLSORT");
	    	 
	    	*//*****一下方法的返回值都是Elements集合******//*
	    	 
	    	//获取所有的a标签<a href="#"></a>
	    	content.getElementsByTag("a");
	    	//类选择器<div></div>
	    	doc.getElementsByClass("divClass");
	    	//获取Document的所有元素
	    	doc.getAllElements();
	    	//根据属性获取元素<a href="#"></a>
	    	doc.getElementsByAttribute("href");
	    	//根据属性前缀获取元素 <li data-name="Peter Liu" data-city="ShangHai" data-lang="CSharp" data-food="apple">
	    	doc.getElementsByAttributeStarting("data-");
	    	//根据key-value选择如<a href="http://xdemo.org"></a>
	    	doc.getElementsByAttributeValue("href","http://xdemo.org");
	    	//和上面的正好相反
	    	doc.getElementsByAttributeValueNot("href","http://xdemo.org");
	    	//根据key-value,其中value可能是key对应属性的一个子字符串，选择如<a href="http://xdemo.org"></a>
	    	doc.getElementsByAttributeValueContaining("href", "xdemo");
	    	//根据key-value,其中key对应值的结尾是value，选择如<a href="http://xdemo.org"></a>
	    	doc.getElementsByAttributeValueEnding("href", "org");
	    	//和上面的正好相反
	    	doc.getElementsByAttributeValueStarting("href","http://xdemo");
	    	//正则匹配，value需要满足正则表达式，<a href="http://xdemo.org"></a>,如href的值含有汉字
	    	doc.getElementsByAttributeValueMatching("href",Pattern.compile("[\u4e00-\u9fa5]"));
	    	//同上
	    	doc.getElementsByAttributeValueMatching("href", "[\u4e00-\u9fa5]");
	    	//根据元素所在的z-index获取元素
	    	doc.getElementsByIndexEquals(0);
	    	//获取z-index大于x的元素
	    	doc.getElementsByIndexGreaterThan(0);
	    	//和上面的正好相反
	    	doc.getElementsByIndexLessThan(10);
	    	 
	    	//遍历标签
	    	for (Element link : content.getElementsByTag("a")) {
	    	 String linkHref = link.attr("href");
	    	 String linkText = link.text();
	    	}
	    	 
	    	*//**************一些其他常用的方法**************//*
	    	//获取网页标题
	    	doc.title();
	    	//获取页面的所有文本
	    	doc.text();
	    	 
	    	//为元素添加一个css class
	    	content.addClass("newClass");
	    	//根据属性获取值
	    	content.attr("id");
	    	//获取所有子元素
	    	content.children();
	    	//获取元素内的所有文本
	    	content.text();
	    	//获取同级元素
	    	content.siblingElements();
	 */   	 
	    	 
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	 
	}

    private static void getStock(String name) {
        ArrayList<String> list=null;  
    	Document doc;
    	try {
	    	doc = Jsoup.connect("http://data.eastmoney.com/zjlx/" + name + ".html").get();
	    	Elements elements = doc.getElementsByClass("tit");
	        for (Element link : elements) {
	        	if (link.select("a") != null) System.out.println(link.attr("abs:href") + "\t" + trim(link.text(), 35));
	        }
	    	elements = doc.getElementsByClass("tab1");
            list = new ArrayList<String>();  
            //select("table#table_style_e");  
            for(Element element:elements){  
                if(element.text()!=null&& !"".equals(element.text())){  
                    Elements es = element.select("tr");  
                    for(Element tdelement:es){  
                        Elements tdes = tdelement.select("td");  
                        for(int i = 0; i < 3 && i < tdes.size(); i++){  
                            list.add(tdes.get(i).text());  
                            //System.out.println(tdes.get(i).text());  
                        }  
                    }  
                }  
            }  
            for(int i = 0; i < list.size(); i++){  
                System.out.println(list.get(i));  
            }  
	    	//System.out.println(content.getElementsByTag("th"));
/*	    	Elements links = doc.select("[class=content]");
	        for (Element link : links) {
	        	System.out.println(link.getElementsByTag("th"));
	        	System.out.println(link.attr("abs:href") + "\t" + trim(link.text(), 35));
	        }
*/    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    public static boolean downloadPage(String url, File file){  
        try {  
            Document doc = Jsoup.connect(url).data("jquery","java").userAgent("Mozilla").cookie("auth", "tiken").timeout(5000).get();  
            String pageHtml = doc.toString();  
            OutputStream out = new FileOutputStream(file);  
            out.write(pageHtml.toString().getBytes());  
            out.close();      
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    }  
    //参数说明：oldFile为所需要替换的文件，即为原文件；   newFile为替换后新的文件 ；oldString为所需要替换的字符串；newString为替换字符串  
    public static boolean replaceAllFileString(File oldFile, File newFile, String oldString, String newString){  
        try {  
        BufferedReader reader = new BufferedReader(new FileReader(oldFile));  
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));  
        String teamString = null;  
        while((teamString = reader.readLine()) != null){  
            String str = teamString.replaceAll(oldString, newString);  
            writer.write(str);  
        }  
        reader.close();  
        writer.close();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    }  
    public static ArrayList<String> resolvePageText(File file){  
        ArrayList<String> list=null;  
        try {  
            Document doc = Jsoup.parse(file, "GBK");  
            Elements elements = doc.getElementsByClass("table_style_e");  
            list = new ArrayList<String>();  
            //select("table#table_style_e");  
            for(Element element:elements){  
                if(element.text()!=null&& !"".equals(element.text())){  
                    Elements es = element.select("tr");  
                    for(Element tdelement:es){  
                        Elements tdes = tdelement.select("td");  
                        for(int i = 0; i < tdes.size(); i++){  
                    		list.add(tdes.get(i).text());  
                            //System.out.println(tdes.get(i).text());  
                        }  
                    }  
                }  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }     
        return list;  
    }  

    private static void getSinajs() throws IOException {
    	String inputName = "stock";
    	List<String> listName = FileUtil.readStockFile(inputName);
    	//http://quote.eastmoney.com/stocklist.html
    	//http://data.eastmoney.com/notice/
    	//http://image.sinajs.cn/newchart/monthly/n/sh000001.gif
    	//http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/601006.phtml
    	String name;	
        LinkedHashMap<String, String[]> map = new LinkedHashMap<String, String[]>();
	    map.put("代号", title);
	    String[] stockdatasplit;

        //map.put("股票代号", stockTitle);
/*	    for(int i=0;i<listName.size();i++){
	    	name = (String)listName.get(i);
	    	if (StringUtil.isBlank(name)) map.put("股票代号", title);
	    	if (StringUtil.isBlank(name) || name.startsWith("*")) continue;
	    	if (name.startsWith("3") || name.startsWith("00")) {
	    		name = "s_sz" + name;
		    	stockdatasplit = StockUtil.getStockValue(name);
	    	} else if (name.startsWith("s")) {
		    	stockdatasplit = StockUtil.getStockstar(name);
	    	} else {
	    		name = "s_sh" + name;
		    	stockdatasplit = StockUtil.getStockValue(name);
	    	}
		    map.put(name.replace("s_", ""), stockdatasplit);
	    }
*/
	    if ("stock".equals(inputName)) {
		    map = StockUtil.getStockList(inputName, SystemConfig.INSTANCE.getValue(inputName+"Address"));
		    FileUtil.createExcel(map, inputName);
	    } else {
		    for(int i=0;i<listName.size();i++){
		    	name = (String)listName.get(i);
		    	if (StringUtil.isBlank(name) || name.startsWith("*") || name.startsWith("s")) continue;
				String sAddr = "http://stock.quote.stockstar.com/stockinfo_capital/flow.aspx?code=" + name + "&pageid=1|17";
			    map = StockUtil.getStockList(name,sAddr);
			    FileUtil.createExcelHist(map, inputName);
		    }
	    }

/*        map = new LinkedHashMap<String, String[]>();
	    map.put("股票代号", new String[]{});
	    for(int i=0;i<listName.size();i++){
	    	name = (String)listName.get(i);
	    	if (StringUtil.isBlank(name) || name.startsWith("*") || name.startsWith("s")) continue;
	    	map = StockUtil.getStockHist(name);
		    FileUtil.createExcelHist(map, inputName);
	    }
*/
	    Runtime.getRuntime().exec("cmd  /c start D:/wwh/stock/"+inputName+".xls");
/*
        Runtime CRuntime = Runtime.getRuntime();
        Process CProce = null;
        //打开生成文件     
        CProce=CRuntime.exec("cmd  /c start D:/wwh/stock/"+inputName+".xls");
        //关闭进程
        try {
			CProce.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        CProce.destroy();
*/
	}

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}