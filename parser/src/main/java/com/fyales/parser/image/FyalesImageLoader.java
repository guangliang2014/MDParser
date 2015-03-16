package com.fyales.parser.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fyales
 * @since date 13/02/15
 */
public class FyalesImageLoader {

    /**
     * 建议在Application里初始化
     *
     * @param context
     */
    public static void init(Context context) {
        int cpuNumbers = Runtime.getRuntime().availableProcessors();

        if (cpuNumbers > 8) {
            cpuNumbers = 8;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(cpuNumbers + 1);
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .defaultDisplayImageOptions(DisplayImageOptionsFactory.createOrdinaryImageOptions())
                .taskExecutor(executorService);

        //配置磁盘缓存
        int availbleSize = (int) (SDCardUtil.getAvailableSize() / 8);
        if (availbleSize > 0) {
            builder = builder.diskCacheFileCount(100)
                    .diskCacheSize(availbleSize)
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator());
        }
        ImageLoaderConfiguration config = builder.build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 判断ImageLoader是否初始化
     *
     * @return
     */
    public static boolean isInit() {
        return ImageLoader.getInstance().isInited();
    }

    /**
     * 显示图片
     *
     * @param imageUri  图片地址
     * @param imageView 显示图片的ImageView控件
     */
    public static void displayImage(String imageUri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(imageUri, imageView);
    }

    public static void displayImage(String imageUri, ImageView imageView, DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage(imageUri, imageView, options);
    }

    public static void displayImage(String imageUri, ImageView imageView, ImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(imageUri, imageView, listener);
    }

    public static void displayImage(String imageUri, ImageView imageView, DisplayImageOptions options,
                                    ImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(imageUri, imageView, options, listener);
    }

    /**
     * 需要在图片加载前后使用不同的ScaleType时使用
     *
     * @param imageUri
     * @param imageView
     */
    public static void displayImageWithLoadingListener(String imageUri, ImageView imageView) {
        imageView.setTag(imageUri);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageLoader.getInstance().displayImage(imageUri, imageView, createLoadingListener(imageView));
    }

    /**
     * 需要在图片加载前后使用不同的ScaleType时使用
     *
     * @param imageUri
     * @param imageView
     * @param defaultResId 图片加载前默认图
     */
    public static void displayImageWithLoadingListener(String imageUri, ImageView imageView, int defaultResId) {
        imageView.setTag(imageUri);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageLoader.getInstance().displayImage(imageUri, imageView, DisplayImageOptionsFactory
                        .createcreateBasicImageOptionsByImageResource(defaultResId),
                createLoadingListener(imageView));
    }

    private static SimpleImageLoadingListener createLoadingListener(final ImageView imageView) {
        return new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedBitmap) {
                if (imageView.getTag() != null && imageView.getTag().equals(imageUri) && loadedBitmap != null) {
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
        };
    }

    /**
     * 取消图片加载
     *
     * @param imageView
     */
    public static void cancelDisplayTask(ImageView imageView) {
        ImageLoader.getInstance().cancelDisplayTask(imageView);
    }

    /**
     * 获取ImageLoader的内存缓存大小
     */
    public static void getMemoryCache() {
        ImageLoader.getInstance().getMemoryCache();
    }

    /**
     * 获取ImageLoader磁盘缓存大小
     */
    public static void getDiskCache() {
        ImageLoader.getInstance().getDiskCache();
    }

    /**
     * 清空内存缓存
     */
    public static void clearMemoryCache() {
        if (isInit()) {
            ImageLoader.getInstance().clearMemoryCache();
        }
    }

    /**
     * 清空磁盘缓存
     */
    public static void clearDiskCache() {
        if (isInit()) {
            ImageLoader.getInstance().clearDiskCache();
        }
    }

    public static void destory() {
        ImageLoader.getInstance().destroy();
    }
}
