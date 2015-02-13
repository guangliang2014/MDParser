import android.app.Application;

import fyales.com.mdparser.image.FyalesImageLoader;

/**
 * @author fyales
 * @date 13/02/15
 */
public class MDParserApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FyalesImageLoader.init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        FyalesImageLoader.clearMemoryCache();
    }
}
