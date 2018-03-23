//package struc;
//
//import java.io.IOException;
//
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
//import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
//import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
//
//public class ITEXT_test {
//	 /** 
//	  * @param args 
//	  * @throws IOException 
//	  */  
//	 public static void main(String[] args) throws IOException {  
//	  System.out.print(readPdf("C:\\Users\\Administrator\\Desktop\\beijing.pdf"));  
//	 }  
//	  
//	 public static String getPdfFileText(String fileName) throws IOException {  
//	  PdfReader reader = new PdfReader(fileName);  
//	  PdfReaderContentParser parser = new PdfReaderContentParser(reader);  
//	  StringBuffer buff = new StringBuffer();  
//	  TextExtractionStrategy strategy;  
//	  for (int i = 1; i <= reader.getNumberOfPages(); i++) {  
//	   strategy = parser.processContent(i,  
//	     new SimpleTextExtractionStrategy());  
//	   buff.append(strategy.getResultantText());  
//	  }  
//	  return buff.toString();  
//	 } 
//	 
//	 
//	 ///<summary>  
//	    ///读取单个或多个pdf  
//	    ///</summary>  
//	    ///<returns>文件内容字符串</returns>  
//	    @SuppressWarnings("null")  
//	    public static String readPdf(String fileName) throws IOException  
//	    {  
//	  
//	            PdfReader p = new PdfReader(fileName);  
//	            //从每一页读出的字符串  
//	            String str = null;  
//	            //"[......]"内部字符串  
//	            String subStr =null;  
//	            //函数返回的字符串  
//	            StringBuffer rtBuf=new  StringBuffer();  
//	              
//	            String rtStr=null;  
//	              
//	            //"[","]","(",")"在字符串中的位置  
//	            int bg = 0, ed = 0, subbg = 0, subed = 0;  
//	  
//	  
//	  
//	            //":"前面的字符串  
//	            String fc =null;  
//	  
//	            //":"前面的字符串  
//	            String bc =null;  
//	  
//	            
//	  
//	            //取得文档总页数  
//	            int pg = p.getNumberOfPages();  
//	  
//	  
//	            // ExcelIO ei = new ExcelIO();  
//	            for (int i = 1; i <= 1; i++)  
//	            {  
//	           
//	  
//	                bg = 0;  
//	                ed = 0;  
//	  
//	               //Arrays.fill(b, 0);  
//	                  
//	              //从每一页读出的8位字节数组  
//	                byte[] b = new byte[0];  
//	                //取得第i页的内容  
//	                b = p.getPageContent(i);  
//	  
//	                //下一行是把每一页的取得的字节数据写入一个txt的文件,仅供研究时用  
//	                //System.IO.File.WriteAllBytes(Application.StartupPath + "//P" + i.ToString() + ".txt", b);  
//	  
//	                StringBuilder sb = new StringBuilder();  
//	  
//	                //取得每一页的字节数组,将每一个字节转换为字符,并将数组转换为字符串  
//	                for (int j = 0; j < b.length; j++) sb.append((char)(b[j]));  
//	                str = sb.toString();  
//	              
//	                //return str;  
//	  
//	               if (str.indexOf("[") >= 0)  
//	                {  
//	  
//	                    //循环寻找"["和"]",直到找不到"["为止  
//	                    while (bg > -1)  
//	                    {  
//	                        //取得下一个"["和"]"的位置  
//	                        bg = str.indexOf("[", ed);  
//	                        ed = str.indexOf("]", bg + 1);  
//	  
//	                        //如果没有下一个"["就跳出循环  
//	                        if (bg == -1) break;  
//	  
//	                        //取得一个"[]"里的内容,将开始寻找"("和")"的位置初始为0  
//	                        subStr = str.substring(bg + 1, ed - bg - 1);  
//	                        subbg = 0;  
//	                        subed = 0;  
//	  
//	                        //循环寻找下一个"("和")",直到没有下一个"("就跳出循环  
//	                        while (subbg > -1)  
//	                        {  
//	                            //取得下一对"()"的位置  
//	                            subbg = subStr.indexOf("(", subed);  
//	                            subed = subStr.indexOf(")", subbg + 1);  
//	  
//	                            //如找不到下一对就跳出  
//	                            if (subbg == -1) break;  
//	                            //在返回字符串后面加上新找到的字符串  
//	                            rtStr = subStr.substring(subbg + 1, subed - subbg - 1);  
//	  
//	  
//	  
//	                        }  
//	                        rtStr+= rtStr + "|";  
//	                    }  
//	                    return rtStr;  
//	                }  
//	                else  
//	                {  
//	                    //每页的行数  
//	                    int lineNumber = 0;  
//	                    while (bg > -1)  
//	                    {  
//	                        //取得下一个"("和")"的位置  
//	                        bg = str.indexOf("(", ed);  
//	                        ed = str.indexOf(")", bg + 1);  
//	                        //如果没有下一个"["就跳出循环  
//	                        if (bg == -1) break;  
//	                        //每行加个'|'为以后分隔准备,为什么不用"/n/r",因为不需要换行功能  
//	                        //rtStr += str.substring(bg + 1, ed-1) + "|";  
//	                          
//	                        String rtStrTemp = str.substring(bg + 1, ed-1);  
//	                          
//	                        rtBuf.append(rtStrTemp);  
//	                        rtBuf.append("|");  
//	  
//	                    }  
//	                    rtStr=rtBuf.toString();  
//	                     
//	  
//	                }  
//	                  
//	  
//	            }  
//	            if (p != null)  
//	            {  
//	                p.close();  
//	            }  
//	      
//	            return rtStr;   
//	              
//	            
//	    }  
//}
