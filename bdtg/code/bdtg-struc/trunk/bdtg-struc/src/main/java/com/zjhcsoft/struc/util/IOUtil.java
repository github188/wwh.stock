package com.zjhcsoft.struc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author wangtao 2013-12-12
 *
 */
public class IOUtil {
	/**
	 * 读取每行，转为List
	 * 
	 * @throws IOException
	 */
	public static List<String> readEncode(Reader reader) throws IOException {

		BufferedReader in = new BufferedReader(reader);
		List<String> nodes = new ArrayList<String>();
		String line;

		while ((line = in.readLine()) != null) {
			if (line.length() == 0) {
				continue;
			}
			if (line.startsWith("#")) {
				continue;
			} else {
				nodes.add(line.trim());
			}
		}
		in.close();
		return nodes;
	}
}
