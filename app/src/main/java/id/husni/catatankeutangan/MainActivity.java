package id.husni.catatankeutangan;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import id.husni.catatankeutangan.adapter.DebtAdapter;
import id.husni.catatankeutangan.callback.DebtCallback;
import id.husni.catatankeutangan.database.MappingHelper;
import id.husni.catatankeutangan.database.table.DebtHelper;
import id.husni.catatankeutangan.model.Debt;
import id.husni.catatankeutangan.utilities.AppUtilities;

public class MainActivity extends AppCompatActivity implements DebtCallback {

    private static final String EXTRA_STATE = "extraState" ;
    private DebtAdapter debtAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle("Husni :: MicroSystem");
        }
        debtAdapter = new DebtAdapter(this);

        recyclerView = findViewById(R.id.recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(debtAdapter);

        DebtHelper debtHelper = DebtHelper.getInstance(this);
        debtHelper.open();

        if (savedInstanceState == null) {
            new MyAsync(debtHelper, this).execute();
        } else {
            ArrayList<Debt> debtArrayList = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (debtArrayList != null) {
                debtAdapter.setDebtArrayListFull(debtArrayList);
            }
        }

        TextView allDebt = findViewById(R.id.tvJumlahUtang);
        Locale localeID = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        String textJumlah = getResources().getString(R.string.jumlah_seluruh_utang);

        allDebt.setText(textJumlah +" "+ String.valueOf(numberFormat.format(debtHelper.getAllValue())));

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingAdd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDataActivity.class);
                startActivityForResult(intent, AppUtilities.REQUEST_CODE_ADD);
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, debtAdapter.getDebtArrayListFull());
    }

    @Override
    public void onPostExecute(ArrayList<Debt> debts) {
        debtAdapter.setDebtArrayListFull(debts);
    }

    private static class MyAsync extends AsyncTask<Void, Void, ArrayList<Debt>> {
        WeakReference<DebtHelper> debtHelperWeakReference;
        WeakReference<DebtCallback> debtCallbackWeakReference;

        MyAsync(DebtHelper debtHelper, DebtCallback debtCallback) {
            debtHelperWeakReference = new WeakReference<>(debtHelper);
            debtCallbackWeakReference = new WeakReference<>(debtCallback);
        }

        @Override
        protected ArrayList<Debt> doInBackground(Void... voids) {
            Cursor cursor = debtHelperWeakReference.get().getAllDebtData();
            return MappingHelper.mapToArray(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Debt> debts) {
            super.onPostExecute(debts);
            debtCallbackWeakReference.get().onPostExecute(debts);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppUtilities.REQUEST_CODE_ADD) {
            if (resultCode == AppUtilities.RESULT_CODE_ADD) {
                if (data != null) {
                    Debt debt = data.getParcelableExtra(AddDataActivity.EXTRA_DEBT);
                    debtAdapter.insertData(debt);
                    showSnackbar(getResources().getString(R.string.data_added));
                }
            }
        }
        if (requestCode == AppUtilities.REQUEST_CODE_UPDATE) {
            if (resultCode == AppUtilities.RESULT_CODE_UPDATE) {
                if (data != null) {
                    Debt debt = data.getParcelableExtra(EditDataActivity.EXTRA_DEBT);
                    int position = data.getIntExtra(EditDataActivity.EXTRA_POSITION, 0);
                    debtAdapter.updateData(position, debt);
                    showSnackbar(getResources().getString(R.string.data_updated));
                }
            } else if (resultCode == AppUtilities.RESULT_CODE_DELETE) {
                if (data != null) {
                    int position = data.getIntExtra(EditDataActivity.EXTRA_POSITION, 0);
                    debtAdapter.deleteData(position);
                    showSnackbar(getResources().getString(R.string.data_deleted));
                }
            }
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actRefresh) {
            finish();
            startActivity(getIntent());
        }
        return super.onOptionsItemSelected(item);
    }
}
