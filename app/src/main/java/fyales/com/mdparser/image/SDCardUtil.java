package fyales.com.mdparser.image;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

/**
 * 获取SD卡的信息
 *
 * @author fyales
 * @date 13/02/15
 */
public class SDCardUtil {

    /**
     * 计算SD卡的可用空间
     *
     * @return 单位KB
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getAvailableSize() {
        String path = Environment.getExternalStorageDirectory().getPath();
        StatFs stat = new StatFs(path);
        long availableBlocks;
        long blockSize;
        if (Build.VERSION.SDK_INT < 18) {
            availableBlocks = stat.getAvailableBlocks();
            blockSize = stat.getBlockSize();
        } else {
            availableBlocks = stat.getAvailableBlocksLong();
            blockSize = stat.getBlockSizeLong();
        }
        return availableBlocks * blockSize / 1024;
    }

    /**
     * 计算SD总空间的大小
     *
     * @return KB
     */

    public static long getAllSize() {
        String path = Environment.getExternalStorageDirectory().getPath();
        StatFs stat = new StatFs(path);
        long blocks;
        long blockSize;
        if (Build.VERSION.SDK_INT < 18) {
            blocks = stat.getAvailableBlocks();
            blockSize = stat.getBlockSize();
        } else {
            blocks = stat.getAvailableBlocksLong();
            blockSize = stat.getBlockSizeLong();
        }

        return blocks * blockSize / 1024;
    }
}
