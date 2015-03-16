package com.fyales.parser.util;

import com.fyales.parser.entity.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 解析标题为标准格式
 * @author fyales
 * @since date 26/2/15
 */
public class HeaderParser {
    private static final String REGEX_H = "(^#+)(.+)";
    private static final int H1 = 1;
    private static final int H2 = 2;
    private static final int H3 = 3;
    private static final int H4 = 4;
    private static final int H5 = 5;
    private static final int H6 = 6;

    public static Tag parse(String s){
        Pattern pattern = Pattern.compile(REGEX_H);
        Matcher m = pattern.matcher(s);

        Tag tag = new Tag();
        while (m.find()){
            if (m.group(1) != null && m.group(2) != null){
                tag.setContent(m.group(2));
                tag.setName(Tag.TAG_H);
                Map<String,Integer> map = new HashMap<>();
                switch (m.group(1).length()){
                    case H1:
                        map.put(Tag.ATTRIBUTE_HEADER_SIZE,Tag.TAG_H1);
                        break;
                    case H2:
                        map.put(Tag.ATTRIBUTE_HEADER_SIZE, Tag.TAG_H2);
                        break;
                    case H3:
                        map.put(Tag.ATTRIBUTE_HEADER_SIZE, Tag.TAG_H3);
                        break;
                    case H4:
                        map.put(Tag.ATTRIBUTE_HEADER_SIZE, Tag.TAG_H4);
                        break;
                    case H5:
                        map.put(Tag.ATTRIBUTE_HEADER_SIZE, Tag.TAG_H5);
                        break;
                    case H6:
                        map.put(Tag.ATTRIBUTE_HEADER_SIZE, Tag.TAG_H6);
                        break;
                    default:
                        map.put(Tag.ATTRIBUTE_HEADER_SIZE, Tag.TAG_H3);
                }
                tag.setAttributes(map);
            }
        }

        return tag;
    }
}
