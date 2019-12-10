package id.husni.catatankeutangan.database.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.husni.catatankeutangan.database.DatabaseHelper;

import static android.provider.BaseColumns._ID;
import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.DEBT_VALUE;
import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.TABLE_NAME;

public class DebtHelper {
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private static DebtHelper INSTANCE;

    public DebtHelper(Context context) {
        helper = new DatabaseHelper(context);
    }

    public static DebtHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DebtHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open()throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor getAllDebtData() {
        return database.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getDebtDataById(String id) {
        return database.query(TABLE_NAME,
                null,
                _ID +"= ?",new String[]{id},
                null,
                null,
                null);
    }

    public int getAllValue() {
        int values = 0;
        String getDataQuery = String.format("SELECT SUM (%s) as total FROM %s", DEBT_VALUE, TABLE_NAME);
        Cursor cursor = database.rawQuery(getDataQuery, null);

        if (cursor.moveToFirst()) {
            values = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
        }
        return values;
    }

    public long insert(ContentValues values) {
        return database.insert(TABLE_NAME, null, values);
    }

    public int update(String id, ContentValues values) {
        return database.update(TABLE_NAME, values, _ID + "=?", new String[]{id});
    }

    public int delete(String id) {
        return database.delete(TABLE_NAME, _ID + "=?", new String[]{id});
    }

}
