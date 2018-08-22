package hzyj.come.p2p.global;

import android.os.Environment;

/**
 * Created by EverGlow on 2018/3/14 16:52
 */

public    class Constants {
    
    public static  int SUCCESSCODE = 0;
    public static  int ERRORCODE = 200;
    public static final String DATA = "DATA";
    public static final String TITLE = "TITLE";
    public static final String ORDER_ID = "order_id";
    public static final String SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DEFAULT_CACHE_DIR = SDCARD_DIR + "/PLDroidPlayer";
}
