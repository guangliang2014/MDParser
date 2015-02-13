package fyales.com.mdparser.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import fyales.com.mdparser.entity.Tag;
import fyales.com.mdparser.image.FyalesImageLoader;
import fyales.com.mdparser.util.InputFileUtil;
import fyales.com.mdparser.util.MDParser;

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
            for (j = 0;j < tags.get(i).size();j++){
                String type = tags.get(i).get(j).getName();
                String content =tags.get(i).get(j).getContent();
                if (type.equals(Tag.TAG_H3)){
                    TextView textView = new TextView(context);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    textView.setText(content);
                    textView.setTextSize(18);
                    this.addView(textView);
                }else if (type.equals(Tag.TAG_IMG)){
                    ImageView imageView = new ImageView(context);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    imageView.setLayoutParams(params);
                    FyalesImageLoader.displayImage(content,imageView);
                    this.addView(imageView);

                }else if (type.equals(Tag.TAG_P)){
                    TextView textView = new TextView(context);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(params);
                    textView.setTextSize(16);
                    textView.setText(content);
                    this.addView(textView);
                }
                Log.e("fyales","the tag is " + tags.get(i).get(j).toString());
            }
        }

    }

}
