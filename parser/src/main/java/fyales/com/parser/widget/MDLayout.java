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
                    case Tag.TAG_H:
                        TextView hTextView = new TextView(context);
                        ViewGroup.LayoutParams hParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        hTextView.setLayoutParams(hParams);
                        hTextView.setText(content);
                        switch ((Integer) tag.getAttributes().get(Tag.ATTRIBUTE_HEADER_SIZE)) {
                            case Tag.TAG_H1:
                                hTextView.setTextSize(24);
                                break;
                            case Tag.TAG_H2:
                                hTextView.setTextSize(22);
                                break;

                            case Tag.TAG_H3:
                                hTextView.setTextSize(20);
                                break;

                            case Tag.TAG_H4:
                                hTextView.setTextSize(18);
                                break;

                            case Tag.TAG_H5:
                                hTextView.setTextSize(16);
                                break;

                            case Tag.TAG_H6:
                                hTextView.setTextSize(14);
                                break;

                        }
                        hTextView.setSingleLine();
                        hTextView.setTextColor(getResources().getColor(R.color.title));
                        hTextView.setPadding(0, 70, 0, 30);
                        hTextView.setGravity(Gravity.CENTER);
                        this.addView(hTextView);
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
                        imgTextView.setText(((String) tag.getAttributes().get(Tag.ATTRIBUTE_IMG_DESC)).trim());
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
