package cn.edu.tju.willitsunnytomorrow.util;

import android.util.Log;

/**
 * Created by N550 on 2016/3/6.
 */
public class LogUtil {

    private static final String TAG = "WIST";
    private static boolean mbDebug = true;

    public static boolean isDebug() {
        return mbDebug;
    }

    public static void setDebugable(boolean debugable) {
        mbDebug = debugable;
    }

    public static void logv(String msg) {
        if (mbDebug) {
            Log.v(TAG, msg);
        }
    }

    public static void logv(String tag, String msg) {
        logv(tag, msg);
    }

    public static void logd(String msg) {
        if (mbDebug) {
            Log.d(TAG, msg);
        }
    }

    public static void loge(String msg) {
        if (mbDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void logw(String msg) {
        if (mbDebug) {
            Log.w(TAG, msg);
        }
    }
}
