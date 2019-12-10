package id.husni.catatankeutangan.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.DEBT_DATE;
import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.DEBT_NAME;
import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.DEBT_VALUE;
import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "debt_database";
    private static final int DATABASE_VERSION = 1;

    private String QUERY_TABLE = String.format("CREATE TABLE %s " +
                    "(%s INTEGER PRIMARY KEY," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s INTEGER NOT NULL)",
            TABLE_NAME,
            _ID,
            DEBT_DATE,
            DEBT_NAME,
            DEBT_VALUE);
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(QUERY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
