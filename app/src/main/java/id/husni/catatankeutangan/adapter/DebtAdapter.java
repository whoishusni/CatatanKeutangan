package id.husni.catatankeutangan.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import id.husni.catatankeutangan.EditDataActivity;
import id.husni.catatankeutangan.R;
import id.husni.catatankeutangan.custom.CustomItemClick;
import id.husni.catatankeutangan.model.Debt;
import id.husni.catatankeutangan.utilities.AppUtilities;

public class DebtAdapter extends RecyclerView.Adapter<DebtAdapter.ViewHolder> {
    private ArrayList<Debt> debtArrayListFull = new ArrayList<>();
    private Activity activity;

    public DebtAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Debt> getDebtArrayListFull() {
        return debtArrayListFull;
    }

    public void setDebtArrayListFull(ArrayList<Debt> items) {
        if (debtArrayListFull != null) {
            if (debtArrayListFull.size() > 0) {
                debtArrayListFull.clear();
            }
            debtArrayListFull.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void insertData(Debt debt) {
        debtArrayListFull.add(debt);
        notifyItemInserted(debtArrayListFull.size() - 1);
    }

    public void updateData(int position, Debt debt) {
        debtArrayListFull.set(position, debt);
        notifyItemChanged(position, debt);
    }

    public void deleteData(int position) {
        debtArrayListFull.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, debtArrayListFull.size());
    }

    @NonNull
    @Override
    public DebtAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.debt_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtAdapter.ViewHolder holder, int position) {
        holder.tvDate.setText(debtArrayListFull.get(position).getDate());
        holder.tvName.setText(debtArrayListFull.get(position).getName());
        Locale localeID = new Locale("in", "ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        holder.tvValue.setText(String.valueOf(numberFormat.format(debtArrayListFull.get(position).getValue())));
        holder.cvItem.setOnClickListener(new CustomItemClick(position, new CustomItemClick.OnCustomItemClick() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, EditDataActivity.class);
                intent.putExtra(EditDataActivity.EXTRA_DEBT, debtArrayListFull.get(position));
                intent.putExtra(EditDataActivity.EXTRA_POSITION, position);
                activity.startActivityForResult(intent, AppUtilities.REQUEST_CODE_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return debtArrayListFull.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvItem;
        private TextView tvDate;
        private TextView tvName;
        private TextView tvValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDatedebt);
            tvName = itemView.findViewById(R.id.tvNameDebt);
            tvValue = itemView.findViewById(R.id.tvValueDebt);
            cvItem = itemView.findViewById(R.id.cvDebtItem);
        }
    }
}
