package fyales.com.parser.entity;


import java.util.Map;

/**
 * 标签的实体类
 *
 * @author fyales
 * @since date 10/02/15
 */
public class Tag {
    public static final int TAG_H = 0;
    public static final int TAG_H1 = 1;
    public static final int TAG_H2 = 2;
    public static final int TAG_H3 = 3;
    public static final int TAG_H4 = 4;
    public static final int TAG_H5 = 5;
    public static final int TAG_H6 = 6;
    public static final int TAG_A = 7;
    public static final int TAG_IMG = 8;
    public static final int TAG_P = 9;
    public static final String ATTRIBUTE_IMG_DESC = "image_desc";
    public static final String ATTRIBUTE_HEADER_SIZE = "header_size";
    public int name;
    public String content;
    public Map<String,? extends Object> attributes;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, ? extends Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, ? extends Object> attributes) {
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
