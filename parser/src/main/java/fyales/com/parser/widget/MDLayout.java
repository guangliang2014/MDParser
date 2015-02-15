package fyales.com.parser.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ListIterator;

import fyales.com.parser.R;
import fyales.com.parser.entity.Tag;
import fyales.com.parser.image.FyalesImageLoader;
import fyales.com.parser.util.InputFileUtil;
import fyales.com.parser.util.MDParser;


/**
 * @author fyales
 * @date 10/02/15
 */
public class MDLayout extends LinearLayout{
    private String mMdData;
    MDParser mParser;


    public MDLayout(Context context) {
        super(context);
    }

    public MDLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MDLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addData(InputStream stream) throws IOException {
        this.mMdData = InputFileUtil.read(stream);
    }

    public void addData(String s){
        this.mMdData = s;
    }


    public void drawData(Context context){
        mParser = new MDParser(this.mMdData);
        List<List<Tag>> tags = mParser.parseAll();


        //正式开始绘制文件
        int i = 0;
        int j = 0;
        for (i = 0;i < tags.size() ;i++){
            ListIterator<Tag> it = tags.get(i).listIterator();
            while (it.hasNext()){
                Tag tag = it.next();
                String type = tag.getName();
                String content =tag.getContent();
                if (type.equals(Tag.TAG_H3)){
                    TextView textView = new TextView(context);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    textView.setText(content);
                    textView.setTextSize(20);
                    textView.setSingleLine();
                    textView.setTextColor(getResources().getColor(R.color.title));
                    textView.setPadding(0, 70, 0, 30);
                    textView.setGravity(Gravity.CENTER);
                    this.addView(textView);
                }else if (type.equals(Tag.TAG_IMG)){
                    ImageView imageView = new ImageView(context);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    imageView.setLayoutParams(params);
                    imageView.setPadding(0,0,0,0);
                    FyalesImageLoader.displayImage(content, imageView);
                    this.addView(imageView);

                    TextView textView = new TextView(context);
                    textView.setLayoutParams(params);
                    textView.setTextSize(16);
                    textView.setLineSpacing(0.0f,1.0f);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextColor(getResources().getColor(R.color.secondary_text));
                    textView.setText(tag.getAttributes().get(Tag.ATTRIBUTE_IMG_DESC).trim());
                    textView.setPadding(0,-10,0,40);
                    this.addView(textView);

                }else if (type.equals(Tag.TAG_P)){
                    boolean hasPNext = true;
                    while (it.hasNext() && hasPNext){
                        Tag temp = it.next();
                        if (temp.getName().equals(Tag.TAG_P)){
                            content += "!" + temp.getContent();
                        }else{
                            it.previous();
                            hasPNext = false;
                        }
                    }
                    TextView textView = new TextView(context);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    textView.setTextSize(18);
                    textView.setLineSpacing(0.0f,1.4f);
                    textView.setTextColor(getResources().getColor(R.color.secondary_text));
                    textView.setText(content.trim());
                    textView.setPadding(0,0,0,0);
                    this.addView(textView);
                }
            }

        }

    }

}
