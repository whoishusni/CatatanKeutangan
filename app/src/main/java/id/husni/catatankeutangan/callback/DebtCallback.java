package id.husni.catatankeutangan.callback;

import java.util.ArrayList;

import id.husni.catatankeutangan.model.Debt;

public interface DebtCallback {
    void onPostExecute(ArrayList<Debt> debts);
}
