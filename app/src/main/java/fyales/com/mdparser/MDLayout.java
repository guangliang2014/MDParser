package fyales.com.mdparser;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;

import fyales.com.mdparser.util.InputFileUtil;

/**
 * @author fyales
 * @date 10/02/15
 */
public class MDLayout extends LinearLayout{
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

    public void addData(String s){
        this.mMdData = s;
    }


}
