package ua.kulku.mazepaukrainian.app;

import ua.kulku.mazepaukrainian.db.DbHelper;
import android.app.Application;

public class MazepaApplication extends Application {
	public DbHelper getDb() {
		return DbHelper.getInstance(this);
	}
}
