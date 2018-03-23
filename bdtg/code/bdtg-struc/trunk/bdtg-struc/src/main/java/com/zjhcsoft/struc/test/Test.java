package com.zjhcsoft.struc.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;

import com.zjhcsoft.struc.fetch.wrapper.HttpCommonFetcherWrapper;

public class Test {
	public static void download(String urlString, String filename,
			String savePath) throws Exception {
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为5s
		con.setConnectTimeout(30 * 1000);
		// 输入流
		InputStream is = con.getInputStream();

		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}

	public static void main(String[] args) throws ParseException {
		String content = HttpCommonFetcherWrapper
				.httpExecute(
						"http://www.epinfo.org/selfmonitor/getSelfMonitorReportList/ad0f8a93-4074-11e3-a6a2-6c626d51ef74?reporttype=2&ts=1437370673606&page=1&pagesize=20",
						"utf-8", "POST");
		System.out.print(content);
	}
}
