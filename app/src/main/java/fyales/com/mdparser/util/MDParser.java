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
    private final String REGEX_PARA ="#+.+\\s+.+\\s+";
    private final String REGEX_TITLE = "(#+.+\n+)(.+\\s+)";
    private final String REGEX_H = "(^#+)(.+)";

    public MDParser(String s){
        this.data = s;
        this.list = new ArrayList<String>();
        this.tags = new ArrayList<List<Tag>>();
    }

    public void parseAll(){
        Pattern pattern = Pattern.compile(REGEX_PARA);
        Matcher m = pattern.matcher(data);
        while (m.find()){
            list.add(m.group().toString());
        }
        parseContent();
        Log.e("fyale", tags.toString());
    }

    private void parseContent(){
        for (int i = 0;i< list.size();i++){
            Pattern pattern = Pattern.compile(REGEX_TITLE);
            Matcher m = pattern.matcher(list.get(i));
            while(m.find()){
//                for (int j= 1;j<=m.groupCount();j++){
//                    Log.e("fyales",m.group(j).toString());
//                    parseTitle(m.group())
//                }
                List<Tag> tagSub = new ArrayList<>();
                List<Tag> tagPara = new ArrayList<>();
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

    private ArrayList<Tag> parsePara(String s){
        ArrayList<Tag> pTags= new ArrayList<>();
        Tag tag = new Tag();
        tag.setName(Tag.TAG_P);
        tag.setContent(s);
        pTags.add(tag);
        return pTags;
    }


}
