package fyales.com.mdparser.image;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import fyales.com.mdparser.R;

/**
 * @author fyales
 * @date 13/02/15
 */
public class DisplayImageOptionsFactory {
    public static DisplayImageOptions createBasicImageOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        return options;
    }

    public static DisplayImageOptions createcreateBasicImageOptionsByImageResource(int imageRes) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cloneFrom(createBasicImageOptions())
                .showImageOnLoading(imageRes)
                .showImageOnFail(imageRes)
                .showImageForEmptyUri(imageRes)
                .build();

        return options;
    }

    /**
     * 创建普通的DisplayImageOptions，用于列表、大图等模块的图片显示
     *
     * @return
     */
    public static DisplayImageOptions createOrdinaryImageOptions() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cloneFrom(createBasicImageOptions())
                .showImageOnLoading(R.drawable.loading)
                .showImageOnFail(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading)
                .build();

        return options;
    }

    /**
     * 创建普通的DisplayImageOptions，用于列表、大图等模块的图片显示
     *
     * @param delayInMillis 延迟加载时间
     * @return
     */
    public static DisplayImageOptions createOrdinaryImageOptions(int delayInMillis) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cloneFrom(createOrdinaryImageOptions())
                .delayBeforeLoading(delayInMillis)
                .build();
        return options;
    }
}
