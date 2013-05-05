
package ua.kulku.mazepaukrainian.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ua.kulku.mazepaukrainian.BuildConfig;
import ua.kulku.mazepaukrainian.log.MazLog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Options: Usual queries are: - for main; 1. usual: SELECT * from table, where
 * state = 0; 2. more pressed: UPDATE limit 3 set as state = 1; then usual 3.
 * full-screen: 4. see count, -> can use cursor.getCount() for this - for
 * learned - for declinedF
 * 
 * @author aindrias
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {
    private static final String LOG_TAG = DbHelper.class.getSimpleName();

    private static volatile DbHelper sInstance = null;

    private static String DATABASE_NAME = "phrases.db";
    private static int PRODUCTION_DATABASE_VERSION = 1;
    private static int DEVELOPMENT_DATABASE_VERSION = 0;
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

    private final Context mContext;

    private boolean mDbJustCreated;

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    /**
     * Copies your database from your local assets-folder if it didn't exist.
     */
    public void copyDbFromAssetsIfNeeded() {
        this.getReadableDatabase(); // to force onCreate() if needed

        if (!mDbJustCreated) {
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
            mDbJustCreated = false;
        } catch (IOException e) {
            MazLog.e(LOG_TAG, "DB was not copied from assets.", e);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connection) {
        MazLog.d(LOG_TAG, "onCreate() ");
        mDbJustCreated = true;
        // nothing more here to do. Db will be copied from the assets folder in
        // #copyDbFromAssetsIfNeeded()
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connection,
            int oldVersion, int newVersion) {
        MazLog.d(LOG_TAG, "onUpgrade() ");

        for (int version = oldVersion + 1; version <= newVersion; version++) {
            MazLog.d(LOG_TAG, "update " + version);
            // TODO run sql update script from the assets
        }

    }

}
