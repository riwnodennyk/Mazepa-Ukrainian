package ua.kulku.mazepaukrainian.db;

import java.sql.SQLException;

import ua.kulku.mazepaukrainian.data.Phrase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DbHelper extends OrmLiteSqliteOpenHelper {
	private static volatile DbHelper instance = null;

	private static String DATABASE_NAME = "phrases.db";
	private static int DATABASE_VERSION = 1;

	private final Class<?> sTableList[] = new Class<?>[] { Phrase.class };

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static DbHelper getInstance(final Context context) {
		if (instance == null) {
			synchronized (DbHelper.class) {
				if (instance == null) {
					instance = new DbHelper(context);
				}
			}
		}
		return instance;
	}

	public static DbHelper getInstance() {
		if (instance == null) {
			throw new IllegalStateException(
					"Should be initialized beforehand by calling #getInstance(final Context context).");
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connection) {
		try {
			for (Class<?> table : sTableList) {
				TableUtils.createTable(connection, table);

				Dao<Phrase, ?> phraseDao = DaoManager.createDao(connection,
						Phrase.class);

				phraseDao
						.createIfNotExists(new Phrase(
								"��������",
								"say",
								null,
								"�������� ������ ������� ����� ����������� ����� ������",
								"Could you <b>say</b> how to get there?", null));

				phraseDao
						.createIfNotExists(new Phrase(
								"�����",
								"life",
								null,
								"���� ����� �� ���� ����� ���� ����� �� ���������� ������",
								"<b>Life</b> is good", null));

				phraseDao
						.createIfNotExists(new Phrase(
								"����� ���",
								"God bless you",
								null,
								"�������� �������� ������������ ������ �� ������ �� �?.",
								"Die young", null));

				phraseDao.createIfNotExists(new Phrase("����� ó����", "chair",
						null, "��� ���� ������� ����������� �����",
						"This <b>chair</b> is not comfortable enough", null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connection,
			int arg2, int arg3) {
		try {
			for (Class<?> table : sTableList) {
				TableUtils.dropTable(connection, table, true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		onCreate(db, connection);
	}

}
