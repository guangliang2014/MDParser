package fyales.com.mdparser.image;

import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fyales
 * @date 13/02/15
 */
public class FyalesImageLoader {
    public static void init(Context context){
        int cpuNumbers = Runtime.getRuntime().availableProcessors();
        if (cpuNumbers > 8){
            cpuNumbers = 8;
        }

        ExecutorService executorService = Executors.newFixedThreadPool(cpuNumbers + 1);

        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .taskExecutor(executorService);
    }
}
