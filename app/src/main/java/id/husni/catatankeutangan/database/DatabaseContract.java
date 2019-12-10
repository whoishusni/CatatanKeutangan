package id.husni.catatankeutangan.database;

import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final class DebtColoumn implements BaseColumns {
        public static final String TABLE_NAME = "debt";
        public static final String DEBT_DATE = "date";
        public static final String DEBT_NAME = "name";
        public static final String DEBT_VALUE = "value";
    }
}
