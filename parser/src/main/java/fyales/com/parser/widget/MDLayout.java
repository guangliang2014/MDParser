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
public class MDLayout extends LinearLayout {

    MDParser mParser;
    private String mMdData;

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

    public void addData(String s) {
        this.mMdData = s;
    }


    public void drawData(Context context) {
        mParser = new MDParser(this.mMdData);
        List<List<Tag>> tags = mParser.parseAll();


        //正式开始绘制文件
        int i = 0;
        int j = 0;
        for (i = 0; i < tags.size(); i++) {
            ListIterator<Tag> it = tags.get(i).listIterator();
            while (it.hasNext()) {
                Tag tag = it.next();
                int type = tag.getName();
                String content = tag.getContent();
                switch (type) {
                    case Tag.TAG_H1:
                        TextView h1TextView = new TextView(context);
                        ViewGroup.LayoutParams h1Params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        h1TextView.setLayoutParams(h1Params);
                        h1TextView.setText(content);
                        h1TextView.setTextSize(24);
                        h1TextView.setSingleLine();
                        h1TextView.setTextColor(getResources().getColor(R.color.title));
                        h1TextView.setPadding(0, 70, 0, 30);
                        h1TextView.setGravity(Gravity.CENTER);
                        this.addView(h1TextView);
                        break;
                    case Tag.TAG_H2:
                        TextView h2TextView = new TextView(context);
                        ViewGroup.LayoutParams h2Params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        h2TextView.setLayoutParams(h2Params);
                        h2TextView.setText(content);
                        h2TextView.setTextSize(22);
                        h2TextView.setSingleLine();
                        h2TextView.setTextColor(getResources().getColor(R.color.title));
                        h2TextView.setPadding(0, 70, 0, 30);
                        h2TextView.setGravity(Gravity.CENTER);
                        this.addView(h2TextView);
                        break;
                    case Tag.TAG_H3:
                        TextView h3TextView = new TextView(context);
                        ViewGroup.LayoutParams h3Params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        h3TextView.setLayoutParams(h3Params);
                        h3TextView.setText(content);
                        h3TextView.setTextSize(20);
                        h3TextView.setSingleLine();
                        h3TextView.setTextColor(getResources().getColor(R.color.title));
                        h3TextView.setPadding(0, 70, 0, 30);
                        h3TextView.setGravity(Gravity.CENTER);
                        this.addView(h3TextView);
                        break;
                    case Tag.TAG_H4:
                        TextView h4TextView = new TextView(context);
                        ViewGroup.LayoutParams h4Params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        h4TextView.setLayoutParams(h4Params);
                        h4TextView.setText(content);
                        h4TextView.setTextSize(18);
                        h4TextView.setSingleLine();
                        h4TextView.setTextColor(getResources().getColor(R.color.title));
                        h4TextView.setPadding(0, 70, 0, 30);
                        h4TextView.setGravity(Gravity.CENTER);
                        this.addView(h4TextView);
                        break;
                    case Tag.TAG_H5:
                        TextView h5TextView = new TextView(context);
                        ViewGroup.LayoutParams h5Params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        h5TextView.setLayoutParams(h5Params);
                        h5TextView.setText(content);
                        h5TextView.setTextSize(16);
                        h5TextView.setSingleLine();
                        h5TextView.setTextColor(getResources().getColor(R.color.title));
                        h5TextView.setPadding(0, 70, 0, 30);
                        h5TextView.setGravity(Gravity.CENTER);
                        this.addView(h5TextView);
                        break;
                    case Tag.TAG_H6:
                        TextView h6TextView = new TextView(context);
                        ViewGroup.LayoutParams h6Params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        h6TextView.setLayoutParams(h6Params);
                        h6TextView.setText(content);
                        h6TextView.setTextSize(14);
                        h6TextView.setSingleLine();
                        h6TextView.setTextColor(getResources().getColor(R.color.title));
                        h6TextView.setPadding(0, 70, 0, 30);
                        h6TextView.setGravity(Gravity.CENTER);
                        this.addView(h6TextView);
                        break;
                    case Tag.TAG_IMG:
                        ImageView imageView = new ImageView(context);
                        ViewGroup.LayoutParams imgParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        imageView.setLayoutParams(imgParams);
                        imageView.setPadding(0, 0, 0, 0);
                        FyalesImageLoader.displayImage(content, imageView);
                        this.addView(imageView);

                        TextView imgTextView = new TextView(context);
                        imgTextView.setLayoutParams(imgParams);
                        imgTextView.setTextSize(16);
                        imgTextView.setLineSpacing(0.0f, 1.0f);
                        imgTextView.setGravity(Gravity.CENTER);
                        imgTextView.setTextColor(getResources().getColor(R.color.secondary_text));
                        imgTextView.setText(tag.getAttributes().get(Tag.ATTRIBUTE_IMG_DESC).trim());
                        imgTextView.setPadding(0, -10, 0, 40);
                        this.addView(imgTextView);
                        break;
                    case Tag.TAG_P:
                        boolean hasPNext = true;
                        while (it.hasNext() && hasPNext) {
                            Tag temp = it.next();
                            if (temp.getName() == Tag.TAG_P) {
                                content += "!" + temp.getContent();
                            } else {
                                it.previous();
                                hasPNext = false;
                            }
                        }
                        TextView pTextView = new TextView(context);
                        ViewGroup.LayoutParams pParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        pTextView.setLayoutParams(pParams);
                        pTextView.setTextSize(18);
                        pTextView.setLineSpacing(0.0f, 1.4f);
                        pTextView.setTextColor(getResources().getColor(R.color.secondary_text));
                        pTextView.setText(content.trim());
                        pTextView.setPadding(0, 0, 0, 0);
                        this.addView(pTextView);
                        break;

                    default:
                }

            }

        }

    }

}
