package com.lunz.data.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 常用工具类
 * @Author: chenxiaojun
 * @CreateDate: 2020/2/25 8:21
 * @Version: 1.0
 */
public class Tools {

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /** 下划线转驼峰 */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * @Description: 驼峰转下划线
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * @Description: 下划线转大驼峰
     */
    public static String lineToBigHump(String str){
        String hump = lineToHump(str);
        return hump.substring(0, 1).toUpperCase() + hump.substring(1);
    }
}
