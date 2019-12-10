package id.husni.catatankeutangan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.husni.catatankeutangan.database.table.DebtHelper;
import id.husni.catatankeutangan.model.Debt;
import id.husni.catatankeutangan.utilities.AppUtilities;

import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.DEBT_DATE;
import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.DEBT_NAME;
import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.DEBT_VALUE;

public class AddDataActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_DEBT = "extraDebt" ;
    private TextInputEditText edtName, edtValue;
    private DebtHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.add));
        }

        helper = DebtHelper.getInstance(this);
        helper.open();

        edtName = findViewById(R.id.textAddName);
        edtValue = findViewById(R.id.textAddValue);
        Button btnSubmit = findViewById(R.id.submitButton);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = edtName.getText().toString().trim();
        String value = edtValue.getText().toString().trim();

        Debt debt = new Debt();

        debt.setDate(getformattedDate());
        debt.setName(name);
        debt.setValue(Integer.parseInt(value));

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DEBT, debt);

        ContentValues values = new ContentValues();
        values.put(DEBT_DATE, getformattedDate());
        values.put(DEBT_NAME, debt.getName());
        values.put(DEBT_VALUE, debt.getValue());

        long result = helper.insert(values);
        if (result > 0) {
            debt.setId((int) result);
            setResult(AppUtilities.RESULT_CODE_ADD,intent);
            finish();
        } else {
            Toast.makeText(this, "Gagal Added", Toast.LENGTH_SHORT).show();
        }
    }

    private String getformattedDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd/MM/yyyy - HH:mm zzzz", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }
}
