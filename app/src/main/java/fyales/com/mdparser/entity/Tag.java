package fyales.com.mdparser.entity;


import java.util.Map;

/**
 * 标签的实体类
 *
 * @author fyales
 * @date 10/02/15
 */
public class Tag {
    public static final String TAG_H1 = "h1";
    public static final String TAG_H2 = "h2";
    public static final String TAG_H3 = "h3";
    public static final String TAG_H4 = "h4";
    public static final String TAG_H5 = "h5";
    public static final String TAG_H6 = "h6";
    public static final String TAG_A = "a";
    public static final String TAG_IMG = "img";
    public static final String TAG_P = "p";
    public static final String ATTRIBUTE_IMG_DESC = "image_desc";
    public String name;
    public String content;
    public Map<String,String> attributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
