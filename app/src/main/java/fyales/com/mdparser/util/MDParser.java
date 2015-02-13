package fyales.com.mdparser.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fyales.com.mdparser.entity.Tag;

/**
 * 解析类
 * <p>目前支持两种标签</p>
 * <ul>
 * <li>#标签</li>
 * <li>图片标签</li>
 * </ul>
 *
 * @author fyales
 * @date 10/02/15
 */
public class MDParser {
    private String data;
    private List<String> list;
    private List<List<Tag>> tags;
    private final String REGEX_PARA = "#+.+\n+[^#]+";
    private final String REGEX_TITLE = "(#+.+\n+)([^#]+)";
    private final String REGEX_H = "(^#+)(.+)";
    private final String REGEX_IMG = "([^!]+)(!\\[[^\\]]+\\]\\([^\\)]+\\))?";

    public MDParser(String s) {
        this.data = s;
        this.list = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public List<List<Tag>> parseAll() {
        Pattern pattern = Pattern.compile(REGEX_PARA);
        Matcher m = pattern.matcher(data);
        while (m.find()) {
            list.add(m.group().toString());
            Log.d("fyales", "the para is " + m.group().toString());
        }
        parseContent();
        return tags;
    }

    private void parseContent() {
        for (int i = 0; i < list.size(); i++) {
            Pattern pattern = Pattern.compile(REGEX_TITLE);
            Matcher m = pattern.matcher(list.get(i));
            while (m.find()) {
                List<Tag> tagSub = new ArrayList<>();
                Log.d("fyales", "the title of para is " + m.group(1));
                Log.d("fyales", "the content of para is " + m.group(2));
                tagSub.add(parseTitle(m.group(1)));
                tagSub.addAll(parsePara(m.group(2)));
                tags.add(tagSub);
            }
        }
    }

    /**
     * 解析标题
     *
     * @param s 形式举例 ## Title
     * @return
     */
    private Tag parseTitle(String s) {
        Pattern pattern = Pattern.compile(REGEX_H);
        Matcher m = pattern.matcher(s);
        Tag tag = new Tag();
        while (m.find()) {
            try {
                tag.setName(Tag.TAG_H3);
                tag.setContent(m.group(2).trim());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
        return tag;
    }

    /**
     * 解析除标题外的其他内容
     *
     * @param s
     * @return
     */
    private ArrayList<Tag> parsePara(String s) {
        ArrayList<Tag> pTags = new ArrayList<>();
        Pattern pattern = Pattern.compile(REGEX_IMG);
        Matcher m = pattern.matcher(s);
        while (m.find()) {

            try {
                int count = m.groupCount();
                Tag contentTag = new Tag();
                contentTag.setName(Tag.TAG_P);
                contentTag.setContent(m.group(1));

                pTags.add(contentTag);
                Log.d("fyales", "the text of content is " + m.group(1));

                if (m.group(2) != null && ImageParser.getURL(m.group(2)) != null) {
                    Tag imgTag = new Tag();
                    imgTag.setName(Tag.TAG_IMG);
                    imgTag.setContent(ImageParser.getURL(m.group(2)));
                    pTags.add(imgTag);
                    Log.d("fyales", "the image of content is " + m.group(2));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
        return pTags;
    }


}
