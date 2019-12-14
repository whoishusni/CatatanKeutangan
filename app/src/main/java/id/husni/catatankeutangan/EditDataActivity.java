package id.husni.catatankeutangan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import id.husni.catatankeutangan.database.table.DebtHelper;
import id.husni.catatankeutangan.model.Debt;
import id.husni.catatankeutangan.utilities.AppUtilities;

import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.DEBT_NAME;
import static id.husni.catatankeutangan.database.DatabaseContract.DebtColoumn.DEBT_VALUE;

public class EditDataActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_DEBT = "extraDebt" ;
    public static final String EXTRA_POSITION = "extraPosition" ;

    private TextInputEditText edtName, edtValue;
    private Debt debt;
    private int position;
    private DebtHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.update));
        }

        helper = DebtHelper.getInstance(this);
        helper.open();

        debt = getIntent().getParcelableExtra(EXTRA_DEBT);
        if (debt != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        } else {
            debt = new Debt();
        }

        edtName = findViewById(R.id.textEditName);
        edtValue = findViewById(R.id.textEditValue);
        Button btnUpdate = findViewById(R.id.editButton);

        if (debt != null) {
            edtName.setText(debt.getName());
            edtValue.setText(String.valueOf(debt.getValue()));
        }

        btnUpdate.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String name = edtName.getText().toString();
        String value = edtValue.getText().toString();

        if (name.isEmpty()) {
            edtName.setError(getResources().getString(R.string.nama_wajib_diisi));
            return;
        }
        if (value.isEmpty()) {
            edtValue.setError(getResources().getString(R.string.nilai_wajib_diisi));
            return;
        }

        debt.setName(name);
        debt.setValue(Integer.parseInt(value));

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DEBT, debt);
        intent.putExtra(EXTRA_POSITION, position);

        ContentValues values = new ContentValues();
        values.put(DEBT_NAME, debt.getName());
        values.put(DEBT_VALUE, debt.getValue());

        long updated = helper.update(String.valueOf(debt.getId()), values);
        if (updated > 0) {
            setResult(AppUtilities.RESULT_CODE_UPDATE, intent);
            finish();
        } else {
            Toast.makeText(this, getResources().getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.actDelete:
                long result = helper.delete(String.valueOf(debt.getId()));
                if (result > 0) {
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_POSITION, position);
                    setResult(AppUtilities.RESULT_CODE_DELETE, intent);
                    finish();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
