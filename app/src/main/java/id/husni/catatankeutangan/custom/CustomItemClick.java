package id.husni.catatankeutangan.custom;

import android.view.View;

public class CustomItemClick  implements View.OnClickListener{
    private int position;
    private OnCustomItemClick onCustomItemClick;

    public CustomItemClick(int position, OnCustomItemClick onCustomItemClick) {
        this.position = position;
        this.onCustomItemClick = onCustomItemClick;
    }

    @Override
    public void onClick(View view) {
        onCustomItemClick.onItemClicked(position);
    }


    public interface OnCustomItemClick {
        void onItemClicked(int position);
    }
}
