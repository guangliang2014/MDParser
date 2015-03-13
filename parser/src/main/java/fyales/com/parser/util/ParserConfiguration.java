package fyales.com.parser.util;

/**
 * 应用建造者模式进行配置，可能在这里运用不正确
 * @author fyales
 * @version 1.1 2015-03-13
 */
public class ParserConfiguration {

    private ParserConfiguration(Builder builder){

    }

    public static class Builder{

        public ParserConfiguration build(){
            return new ParserConfiguration(this);
        }
    }
}
