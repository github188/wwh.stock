package cn.hzstk.securities.util;

import net.ryian.commons.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title: 格式化：开源,开放
 * Description: opeansource
 * Copyright: Copyright (c) 2004
 * Company: ?海棠
 * @author HaiTang Ming
 * @version 1.0
 */
public class DigitFormat {
    public static final String NUMBER_FORMAT = "#0.00";

    public DigitFormat() {
    }
    /**
     * 将给定的数字按给定的形式输出
     * @param d double
     * @param pattern String
     *       #:表示有数字则输出数字，没有则空，如果输出位数多于＃的位数，
     *          则超长输入
     *       0:有数字则输出数字，没有补0
     *          对于小数，有几个＃或0，就保留几位的小数；
     *       例如： "###.00" -->表示输出的数值保留两位小数，不足两位的
     *                          补0，多于两位的四舍五入
     *              "###.0#" -->表示输出的数值可以保留一位或两位小数；
     *                               整数显示为有一位小数，一位或两位小数
     *                               的按原样显示，多于两位的四舍五入；
     *              "###" --->表示为整数，小数部分四舍五入
     *              ".###" -->12.234显示为.234
     *              "#,###.0#" -->表示整数每隔3位加一个"，";
     * @param l Locale
     * @return String
     */
    public static String formatNumber(double d,String pattern,Locale l){
        String s = "";
        try{
            DecimalFormat nf = (DecimalFormat) NumberFormat.getInstance(l);
            nf.applyPattern(pattern);
            s = nf.format(d);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(" formatNumber is error!");
        }
        return s;
    }
    /**
     * 按缺省的区域输出数字形式
     * @param d double
     * @param pattern String
     * @return String
     */
    public static String formatNumber(double d,String pattern){
        return formatNumber(d,pattern,Locale.getDefault());
    }
    public static String formatNumber(String s, int len){
        if (StringUtils.isEmpty(s) || "-".equals(s)) return s;
        double d = cvtDouble(s);
        String format = "#0.00";
        if (len == 4) format = "#0.0000";
        if (len == -4) d = d/10000;

        return formatNumber(d,format,Locale.getDefault());
    }
    public static String formatNumber(String s){
        return formatNumber(s,2);
    }
    /**
     * 格式化货币
     * @param d double
     * @param pattern String
     *        "\u00A4#,###.00" :显示为 ￥1，234，234.10
     * @param l Locale
     * @return String
     */
    public static String formatCurrency(double d,String pattern,Locale l){
        String s = "";
        try{
            DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance(l);
            nf.applyPattern(pattern);
            s = nf.format(d);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(" formatNumber is error!");
        }
        return s;
    }
    /**
     * 使用默认区域的指定方式显示货币
     * @param d double
     * @param pattern String
     * @return String
     */
    public static String formatCurrency(double d,String pattern){
        return formatCurrency(d,pattern,Locale.getDefault());
    }
    /**
     * 使用默认方式显示货币：
     *     例如:￥12,345.46 默认保留2位小数，四舍五入
     * @param d double
     * @return String
     */
    public static String formatCurrency(double d){
        String s = "";
        try{
            DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance();
            s = nf.format(d);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(" formatNumber is error!");
        }
        return s;
    }
    /**
     * 按指定区域格式化百分数
     * @param d
     * @param pattern :"##,##.000%"-->不要忘记“%”
     * @param l
     * @return
     */
    public static String formatPercent(double d,String pattern,Locale l){
        String s = "";
        try{
            DecimalFormat df = (DecimalFormat)NumberFormat.getPercentInstance(l);
            df.applyPattern(pattern);
            s = df.format(d);
        }catch(Exception e){
            e.printStackTrace();
            System.out.print("formatPercent is error!");
        }
        return s;
    }
    /**
     * 使用默认区域格式化百分数
     * @param d
     * @param pattern
     * @return
     */
    public static String formatPercent(double d,String pattern){
        return formatPercent(d, pattern, Locale.getDefault());
    }
    /**
     * 格式化百分数
     * @param d
     * @return
     */
    public static String formatPercent(double d){
        String s = "";
        try{
            DecimalFormat df = (DecimalFormat)NumberFormat.getPercentInstance();
            s = df.format(d);
        }catch(Exception e){
            System.out.print("formatPercent is error!");
        }
        return s;
    }
    /**
     * 输出数字的格式,如:1,234,567.89
     * @param bd BigDecimal 要格式华的数字
     * @param format String 格式 "###,##0"
     * @return String
     */
    public static String numberFormat(BigDecimal bd, String format) {
        if (bd == null) {
            return "";
        }
        if ("0".equals(bd.toString())) {
            return "0";
        }
        DecimalFormat bf = new DecimalFormat(format);
        return bf.format(bd);
    }

    public static double cvtDouble(String s) {
        if (StringUtils.isEmpty(s)) return 0;

        double d;
        try {
            d = Double.parseDouble(s);
        } catch (Exception e) {
            return 0;
        }

        return d;
    }

    public static String cvtBillion(String s) {
        if (StringUtils.isEmpty(s)) return "";

        double d = cvtDouble(s);
        if (d == 0) {
            return "0";
        }

        return formatNumber(d/100000000, NUMBER_FORMAT) + "亿";
    }

    public static String cvtMillion(String s) {
        if (StringUtils.isEmpty(s)) return "";

        double d = cvtDouble(s);
        if (d == 0) {
            return "0";
        }

        return formatNumber(d/10000, NUMBER_FORMAT) + "亿";
    }

    public static String calcWidth(String s0,String s1,String s2) {
        double zs = cvtDouble(s0);
        if (zs == 0) return "0";
        double zg = cvtDouble(s1);
        double zd = cvtDouble(s2);
        //String.format("%.2f", calcWidth(dq, zs, zs));
        return calcWidth(zs, zg, zd);
    }

    public static String calcWidth(double zs,double zg,double zd) {
        if (zs == 0) return "0";
        double zf = Math.round((zg - zd) / zs * 100000) / 1000.0;

        return formatNumber(zf, NUMBER_FORMAT);
    }

    public static String subWidth(double zg,double zd) {
        double zf = Math.round((zg - zd) * 1000) / 1000.0;

        return formatNumber(zf, NUMBER_FORMAT);
    }

    public static String getLimit(String s0) {
        double zs = cvtDouble(s0);
        if (zs == 0) return "0";
        double tmp = Math.round(zs*1.1 * 100) / 100.0;
        return formatNumber(tmp, NUMBER_FORMAT);
    }

    // 判断一个字符串是否都为数字
    public static boolean isDigit(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }

    //截取数字
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    public static void main(String[] args) {
        /*String s = formatCurrency(11123.89343,"$##,##.000");
        System.out.println(s);*/
        System.out.print(cvtBillion("1111142445530"));
    }
}