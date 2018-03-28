package com.skoo.autocode.util;


/**
 * <p>
 * 内容摘要: 字符串工具类
 * </p>
 * <p>
 * 完成日期: 2013年9月7日 下午5:05:37
 * </p>
 * <p>
 * 修改记录:
 * </p>
 * <p/>
 * <pre>
 *    修改日期:
 *    修 改 人:
 *    修改内容:
 * </pre>
 *
 * @author gomiten@163.com
 */
public class StringUtil {

    /**
     * @param name
     * @return
     * @date：2013年9月7日
     * @Description：驼峰规则
     */
    public static String toCamel(String name) {
        return toCamel(name, true);
    }

    /**
     * @param name
     * @param forClass
     * @return
     * @date：2013年9月7日
     * @Description：驼峰规则
     */
    public static String toCamel(String name, boolean forClass) {
        if (isBlank(name)) {
            return null;
        }
        name = name.toLowerCase();
        String[] temp = name.split("_");
        if (temp.length < 2) {
            if (forClass) {
                String firstChar = name.substring(0, 1);
                return firstChar.toUpperCase() + name.substring(1);
            }
            return name;
        }
        StringBuffer sb = new StringBuffer();
        String firstSub = temp[0];

        if (firstSub.length() > 1) {
            String firstChar = firstSub.substring(0, 1);
            if (forClass) {
                firstChar = firstChar.toUpperCase();
            }
            sb.append(firstChar);
            sb.append(firstSub.substring(1));

        } else {
            if (forClass) {
                sb.append(firstSub.toUpperCase());
            } else {
                sb.append(firstSub);
            }
        }
        for (int i = 1; i < temp.length; i++) {
            String item = temp[i];
            String first = item.substring(0, 1);
            first = first.toUpperCase();
            sb.append(first);
            sb.append(item.substring(1));
        }

        return sb.toString();
    }

    /**
     * @param packge
     * @return
     * @date：2013年9月7日
     * @Description：命名惯例转换
     */
    public static String packge2path(String packge) {
        return packge = packge.replaceAll("\\.", "/");
    }

    public static boolean isBlank(String v) {
        if (null == v || "".equals(v)) {
            return true;
        } else {
            return false;
        }

    }

}
