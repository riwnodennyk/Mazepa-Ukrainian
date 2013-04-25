package ua.kulku.mazepaukrainian.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Arrays;

import ua.kulku.mazepaukrainian.BuildConfig;
import ua.kulku.mazepaukrainian.log.MazLog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public class DbHelper extends OrmLiteSqliteOpenHelper {
	private static final String LOG_TAG = DbHelper.class.getSimpleName();

	private static volatile DbHelper sInstance = null;

	private static String DATABASE_NAME = "phrases.db";
	private static int PRODUCTION_DATABASE_VERSION = 1;
	private static int DEVELOPMENT_DATABASE_VERSION = 7;
	private static int DATABASE_VERSION = BuildConfig.DEBUG ? PRODUCTION_DATABASE_VERSION
			+ DEVELOPMENT_DATABASE_VERSION
			: PRODUCTION_DATABASE_VERSION;

	public static DbHelper getInstance() {
		if (sInstance == null) {
			throw new IllegalStateException(
					"Should be initialized beforehand by calling #getInstance(final Context context).");
		}
		return sInstance;
	}

	public static DbHelper getInstance(final Context context) {
		if (sInstance == null) {
			synchronized (DbHelper.class) {
				if (sInstance == null) {
					sInstance = new DbHelper(context);
					sInstance.copyDbFromAssetsIfNeeded();
				}
			}
		}
		return sInstance;
	}

	static String intToString(int num, int digits) {
		assert digits > 0 : "Invalid number of digits";

		// create variable length array of zeros
		char[] zeros = new char[digits];
		Arrays.fill(zeros, '0');
		// format number as String
		DecimalFormat df = new DecimalFormat(String.valueOf(zeros));

		return df.format(num);
	}

	private final Context mContext;

	private boolean mDbCreatedOrUpdated;

	private DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
	}

	/**
	 * Copies your database from your local assets-folder if it didn't exist or
	 * our DATABASE_VERSION was updated.
	 * */
	public void copyDbFromAssetsIfNeeded() {
		this.getReadableDatabase(); // to force onCreate() or onUpgrade()
		if (!mDbCreatedOrUpdated) {
			return;
		}
		try {
			InputStream assetsDbStream = mContext.getAssets().open(
					DATABASE_NAME);

			File systemDb = mContext.getDatabasePath(DATABASE_NAME);

			OutputStream systemDbStream = new FileOutputStream(systemDb);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = assetsDbStream.read(buffer)) > 0) {
				systemDbStream.write(buffer, 0, length);
			}
			systemDbStream.flush();
			systemDbStream.close();
			assetsDbStream.close();

			getWritableDatabase().setVersion(DATABASE_VERSION);
			mDbCreatedOrUpdated = false;
		} catch (IOException e) {
			MazLog.e(LOG_TAG, "DB was not copied from assets.", e);
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connection) {
		mDbCreatedOrUpdated = true;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connection,
			int arg2, int arg3) {
		onCreate(db, connection);
	}

}
