package ua.kulku.mazepaukrainian.log;

import ua.kulku.mazepaukrainian.BuildConfig;
import android.util.Log;

public class MazLog {
	private final static int MINIMUM_LOG_LEVEL = BuildConfig.DEBUG ? Log.VERBOSE
			: Log.INFO;

	public static void e(String tag, String msg, Throwable tr) {
		if (Log.ERROR > MINIMUM_LOG_LEVEL) {
			Log.e(tag, msg, tr);
		}
	}

	public static void e(String tag, String msg) {
		if (Log.ERROR > MINIMUM_LOG_LEVEL) {
			Log.e(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (Log.DEBUG > MINIMUM_LOG_LEVEL) {
			Log.d(tag, msg);
		}
	}
}
