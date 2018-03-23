package struc;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Xpdf {
	public static void main(String args[]) throws IOException{
		String PATH_TO_XPDF="C:\\xpdf\\bin64\\pdftotext.exe";

        String filename="C:\\Users\\Administrator\\Desktop\\beijing.pdf";

         

          int Begin = 1;

          int End = 300;

         

          String strResponse = "";

         

        String[] cmd = new String[]

             { PATH_TO_XPDF,

                     "-cfg", "xpdfrc", "-q", filename, "-"};

        Process p = Runtime.getRuntime().exec(cmd);

        BufferedInputStream bis = new BufferedInputStream(p.getInputStream());

        InputStreamReader reader = new InputStreamReader(bis, "UTF-8");



         

          if(Begin > 0)

          {

               // 跳过Begin个字符

               reader.skip(Begin);

          }

           

         

          // 本次要读取的字符数

          int nLengthRead = End - Begin;

          if(nLengthRead > 0)

          {

               // 准备好缓冲区

              char [] buf = new char[nLengthRead];

               // 输出到缓冲区

              int nLengthWriteToBuffer = reader.read(buf);

              reader.close();

              

               // nLengthWriteToBuffer就是当前读取到缓冲区的字符数

               if(nLengthWriteToBuffer > 0)

               {

                     // 只有nLengthWriteToBuffer大于0，才说明文档有内容

                     strResponse = new String(buf);

               }

               else

               {

                     // 否则可能是到了文档结尾

                     strResponse = "PDF_EOF_OF_DOC";

               }

          }

         

        System.out.println("\t\r\n" + strResponse);
	}
}
