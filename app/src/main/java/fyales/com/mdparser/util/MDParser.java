package fyales.com.mdparser.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fyales.com.mdparser.entity.Tag;

/**
 * @author fyales
 * @date 10/02/15
 */
public class MDParser {
    private String data;
    private List<String> list;
    private List<List<Tag>> tags;
    private final String REGEX_PARA ="#+.+\n+[\\w+|\\s+\\.+\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]+";
    private final String REGEX_TITLE = "(#+.+\n+)(.+\\s+)";
    private final String REGEX_H = "(^#+)(.+)";
    private final String REGEX_IMG = "(.+\\s+.+)(!\\[.+\\]\\(.+\\))?";

    public MDParser(String s){
        this.data = s;
        this.list = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public void parseAll(){
        Pattern pattern = Pattern.compile(REGEX_PARA);
        Matcher m = pattern.matcher(data);
        while (m.find()){
            list.add(m.group().toString());
        }
        parseContent();
        Log.e("fyales",list.toString());
    }

    private void parseContent(){
        for (int i = 0;i< list.size();i++){
            Pattern pattern = Pattern.compile(REGEX_TITLE);
            Matcher m = pattern.matcher(list.get(i));
            while(m.find()){
                List<Tag> tagSub = new ArrayList<>();
                tagSub.add(parseTitle(m.group(1)));
                tagSub.addAll(parsePara(m.group(2)));
                tags.add(tagSub);
            }
        }
    }

    /**
     * 解析标题
     * @param s 形式举例 ## Title
     * @return
     */
    private Tag parseTitle(String s){
        Pattern pattern = Pattern.compile(REGEX_H);
        Matcher m = pattern.matcher(s);
        Tag tag = new Tag();
        while(m.find()){
            try{
                tag.setName(Tag.TAG_H3);
                tag.setContent(m.group(2).trim());
            }catch(Exception e){
                e.printStackTrace();
            }finally {
            }
        }
        return tag;
    }

    /**
     * 解析除标题外的其他内容
     * @param s
     * @return
     */
    private ArrayList<Tag> parsePara(String s){
        ArrayList<Tag> pTags= new ArrayList<>();
        Pattern pattern = Pattern.compile(REGEX_IMG);
        Matcher m = pattern.matcher(s);
        while(m.find()){

            try{
                int count = m.groupCount();
                if (m.groupCount() == 1){
                    Tag contentTag = new Tag();
                    contentTag.setName(Tag.TAG_P);
                    contentTag.setContent(m.group(1));
                }else{
                    Tag contentTag = new Tag();
                    contentTag.setName(Tag.TAG_P);
                    contentTag.setContent(m.group(1));
                    Tag imgTag = new Tag();
                    imgTag.setName(Tag.TAG_IMG);
                    imgTag.setContent(m.group(2));
                    pTags.add(contentTag);
                    pTags.add(imgTag);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{

            }
        }
        return pTags;
    }


}
