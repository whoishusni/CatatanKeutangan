package id.husni.catatankeutangan.database;

import android.database.Cursor;

import java.util.ArrayList;

import id.husni.catatankeutangan.model.Debt;

import static android.provider.BaseColumns._ID;
import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.DEBT_DATE;
import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.DEBT_NAME;
import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.DEBT_VALUE;

public class MappingHelper {
    public static ArrayList<Debt> mapToArray(Cursor cursor) {
        ArrayList<Debt> debtArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DEBT_DATE));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DEBT_NAME));
            int value = cursor.getInt(cursor.getColumnIndexOrThrow(DEBT_VALUE));
            debtArrayList.add(new Debt(id, date, name, value));
        }
        return debtArrayList;
    }
}
