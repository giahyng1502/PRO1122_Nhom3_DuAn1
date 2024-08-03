package FPT.PRO1122.Nhom3.DuAn1.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.model.DanhMucMonAn;

public class AdapterSpinner implements SpinnerAdapter {

    private Context context;
    private List<DanhMucMonAn> items;

    public AdapterSpinner(@NonNull Context context, @NonNull List<DanhMucMonAn> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // Item ID can be its position
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // We have only one type of view
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_spinner_category, parent, false);
        }
        TextView tvId = convertView.findViewById(R.id.tvIdCategory);
        TextView tvName = convertView.findViewById(R.id.tvNameCategory);
        tvId.setText(items.get(position).getId()+". ");
        tvName.setText(items.get(position).getName());
        return convertView;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_spinner_category, parent, false);
        }
        TextView tvId = convertView.findViewById(R.id.tvIdCategory);
        TextView tvName = convertView.findViewById(R.id.tvNameCategory);
        tvId.setText(items.get(position).getId()+"");
        tvName.setText(items.get(position).getName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        // Implement if needed
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        // Implement if needed
    }
}
