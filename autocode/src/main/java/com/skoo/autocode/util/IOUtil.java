package com.skoo.autocode.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * <p>内容摘要:io工具类</p>
 * <p>完成日期: 2013年9月7日 下午5:06:30</p>
 * <p>修改记录:</p>
 * <pre>
 *    修改日期:
 *    修 改 人:
 *    修改内容:
 * </pre>
 *
 * @author gomiten@163.com
 */
public class IOUtil {
    public static void writeCodeFile(String path, String code)
            throws IOException {
        path = path.replaceAll("\\\\", "/");

        String dir = path.substring(0, path.lastIndexOf("/"));
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        FileOutputStream fileOut = new FileOutputStream(path);
        OutputStreamWriter fileOutWriter = new OutputStreamWriter(fileOut, "utf-8");
        fileOutWriter.write(code);
        fileOutWriter.flush();
        fileOutWriter.close();

        System.out.print("done!");
        System.out.println(path);
    }
}
