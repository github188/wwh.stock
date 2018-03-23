/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zjhcsoft.struc.util;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A collection of String processing utility methods.
 */
public class StringUtil {

	/**
	 * Returns a copy of <code>s</code> padded with trailing spaces so that it's
	 * length is <code>length</code>. Strings already <code>length</code>
	 * characters long or longer are not altered.
	 */
	public static String rightPad(String s, int length) {
		StringBuffer sb = new StringBuffer(s);
		for (int i = length - s.length(); i > 0; i--)
			sb.append(" ");
		return sb.toString();
	}

	/**
	 * Returns a copy of <code>s</code> padded with leading spaces so that it's
	 * length is <code>length</code>. Strings already <code>length</code>
	 * characters long or longer are not altered.
	 */
	public static String leftPad(String s, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = length - s.length(); i > 0; i--)
			sb.append(" ");
		sb.append(s);
		return sb.toString();
	}

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
			'b', 'c', 'd', 'e', 'f' };

	/**
	 * Convenience call for {@link #toHexString(byte[], String, int)}, where
	 * <code>sep = null; lineLen = Integer.MAX_VALUE</code>.
	 * 
	 * @param buf
	 */
	public static String toHexString(byte[] buf) {
		return toHexString(buf, null, Integer.MAX_VALUE);
	}

	/**
	 * Get a text representation of a byte[] as hexadecimal String, where each
	 * pair of hexadecimal digits corresponds to consecutive bytes in the array.
	 * 
	 * @param buf
	 *            input data
	 * @param sep
	 *            separate every pair of hexadecimal digits with this separator,
	 *            or null if no separation is needed.
	 * @param lineLen
	 *            break the output String into lines containing output for
	 *            lineLen bytes.
	 */
	public static String toHexString(byte[] buf, String sep, int lineLen) {
		if (buf == null)
			return null;
		if (lineLen <= 0)
			lineLen = Integer.MAX_VALUE;
		StringBuffer res = new StringBuffer(buf.length * 2);
		for (int i = 0; i < buf.length; i++) {
			int b = buf[i];
			res.append(HEX_DIGITS[(b >> 4) & 0xf]);
			res.append(HEX_DIGITS[b & 0xf]);
			if (i > 0 && (i % lineLen) == 0)
				res.append('\n');
			else if (sep != null && i < lineLen - 1)
				res.append(sep);
		}
		return res.toString();
	}

	/**
	 * Convert a String containing consecutive (no inside whitespace)
	 * hexadecimal digits into a corresponding byte array. If the number of
	 * digits is not even, a '0' will be appended in the front of the String
	 * prior to conversion. Leading and trailing whitespace is ignored.
	 * 
	 * @param text
	 *            input text
	 * @return converted byte array, or null if unable to convert
	 */
	public static byte[] fromHexString(String text) {
		text = text.trim();
		if (text.length() % 2 != 0)
			text = "0" + text;
		int resLen = text.length() / 2;
		int loNibble, hiNibble;
		byte[] res = new byte[resLen];
		for (int i = 0; i < resLen; i++) {
			int j = i << 1;
			hiNibble = charToNibble(text.charAt(j));
			loNibble = charToNibble(text.charAt(j + 1));
			if (loNibble == -1 || hiNibble == -1)
				return null;
			res[i] = (byte) (hiNibble << 4 | loNibble);
		}
		return res;
	}

	private static final int charToNibble(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		} else if (c >= 'a' && c <= 'f') {
			return 0xa + (c - 'a');
		} else if (c >= 'A' && c <= 'F') {
			return 0xA + (c - 'A');
		} else {
			return -1;
		}
	}

	/**
	 * Checks if a string is empty (ie is null or empty).
	 */
	public static boolean isEmpty(String str) {
		return (str == null) || (str.equals(""));
	}

	/**
	 * 比较两个（只有一处不同的）字符串，解析出模版
	 * 
	 * @param s1
	 *            字符串1
	 * @param s2
	 *            字符串2
	 * @return 模版
	 * 
	 * @author wangtao
	 */
	public static String prepareURLString(String s1, String s2) {
		int len1 = s1.length();
		int len2 = s2.length();

		char[] b1 = new char[len1];
		char[] b2 = new char[len2];

		s1.getChars(0, len1, b1, 0);
		s2.getChars(0, len2, b2, 0);

		StringBuilder stringBuilder = new StringBuilder("");

		// 比较两个字符串并把不同的字符替换为@
		int dis = Math.abs(len1 - len2);
		int j = 0;
		char[] high, low;

		if (len1 > len2) {
			high = b1;
			low = b2;
		} else {
			high = b2;
			low = b1;
		}

		for (int i = 0; i < high.length; i++) {

			if (high[i] == low[j]) {
				stringBuilder.append(high[i]);
			} else if (stringBuilder.charAt(stringBuilder.length() - 1) != '@') {
				stringBuilder.append("@");
			}

			if (stringBuilder.charAt(stringBuilder.length() - 1) == '@' && dis > 0) {
				dis--;
			} else {
				j++;
			}
		}

		return stringBuilder.toString();
	}

	/**
	 * 通过模版匹配字符串，解析出与模板不相同的字符
	 * 
	 * @param temp
	 *            模版
	 * @param s
	 *            字符串
	 * @return 不同的字符串
	 * 
	 * @author wangtao
	 */
	public static String extractDifferenceString(String temp, String s) {
		String[] temps = temp.split("@");
		for (int i = 0; i < temps.length; i++) {
			s = s.replaceAll(quoteReplacement(temps[i]), "");
		}
		return s.trim();
	}

	/**
	 * 对string进行反义，使"\\"，"$","?","+","*"进行反义
	 * 
	 * @param s
	 *            待反义字符串
	 * @return 反义后的字符串
	 * 
	 * @author wangtao
	 */
	public static String quoteReplacement(String s) {
		if ((s.indexOf('\\') == -1) && (s.indexOf('$') == -1) && (s.indexOf('?') == -1)
				&& (s.indexOf('+') == -1) && (s.indexOf('*') == -1)) {
			return s;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\\' || c == '$' || c == '?' || c == '+' || c == '*') {
				sb.append('\\');
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 生成N个零的字符串
	 * 
	 * @author wangtao 2013-12-16
	 */
	public static String zero(int len) {
		StringBuilder zero = new StringBuilder();
		for (int j = 0; j < len; j++) {
			zero.append("0");
		}
		return zero.toString();
	}

	public static String javabeanToString(Object javabean) {
		return ReflectionToStringBuilder.toString(javabean, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * 顺序字符包含
	 * 
	 * @author wangtao 2014-4-29
	 */
	public static boolean isContainChar(CharSequence pat, CharSequence sub) {
		int charPosition = 0;
		for (int i = 0; i < sub.length(); i++) {
			char subChar = sub.charAt(i);
			boolean isContain = false;
			int j = 0;
			while (j < pat.length()) {
				if (j >= charPosition && pat.charAt(j) == subChar) {
					isContain = true;
					charPosition = j + 1;// 匹配后指针往前移动1位
					break;
				}
				j++;
			}
			if (!isContain) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 空格数量
	 * 
	 * @author wangtao 2014-4-29
	 */
	public static int countSpaceCharacter(CharSequence s) {
		int spaceCharacter = 0;
		for (int i = 0; i < s.length(); i++) {
			char tmp = s.charAt(i);
			if (tmp == ' ') {
				spaceCharacter++;
			}
		}
		return spaceCharacter;
	}

	/**
	 * string数组非空
	 * @author wangtao 2014-5-6
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
				if (result == false) {
					return result;
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		// if (args.length != 1)
		// System.out.println("Usage: StringUtil <encoding name>");
		// else
		// System.out.println(args[0] + " is resolved to " +
		// EncodingDetector.resolveEncodingAlias(args[0]));
		prepareURLString("http://bbs.hangzhou.com.cn/forum-11-1.html",
				"http://bbs.hangzhou.com.cn/forum-11-2.html");

		System.out.println(isContainChar("科尔沁左翼中旗", "科尔沁左中旗"));
		// System.out.println("#" + countSpaceCharacter("土默 特左旗") + "#");
	}
}
