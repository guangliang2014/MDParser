package fyales.com.parser.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将图片格式里面的image地址提取出来的工具类
 *
 * @author fyales
 * @date 13/02/15
 */
public class ImageParser {

    private static final String REGEX_IMG = "!\\[([^\\]]+)\\]\\(([^\\)]+)\\)";

    public static String getURL(String s){
        Pattern pattern = Pattern.compile(REGEX_IMG);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()){
            return matcher.group(2);
        }else {
            return null;
        }
    }

    public static String getDesc(String s){
        Pattern pattern = Pattern.compile(REGEX_IMG);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()){
            return matcher.group(1);
        }else {
            return null;
        }
    }
}
