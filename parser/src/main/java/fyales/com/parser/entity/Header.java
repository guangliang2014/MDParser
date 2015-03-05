package fyales.com.parser.entity;

/**
 * @author fyales
 * @since date 10/02/15
 */
public class Header extends Tag {

    public static final String ATTR_SIZE = "attr_size";
    public static final int ATTR_SIZE_H1 = 0;
    public static final int ATTR_SIZE_H2 = 1;
    public static final int ATTR_SIZE_H3 = 2;
    public static final int ATTR_SIZE_H4 = 3;
    private int size;


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
