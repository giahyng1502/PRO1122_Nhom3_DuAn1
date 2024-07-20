package FPT.PRO1122.Nhom3.DuAn1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import FPT.PRO1122.Nhom3.DuAn1.R;

@SuppressLint("ResourceType")
public class SpinnerAdapter extends BaseAdapter {
    private final Context context;
    private final List<String[]> items;

    public SpinnerAdapter(Context context, List<String[]> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        if (items != null) return items.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (items != null) {
            String[] item = items.get(position);
            view = LayoutInflater.from(context).inflate(R.layout.dropdown_gender_item, parent, false);

            TextView tvGender = view.findViewById(R.id.tvGender);
            tvGender.setText(item[0]);
            if (position == 0) {
                tvGender.setTextColor(Color.GRAY);
            } else {
                tvGender.setTextColor(Color.BLACK);
            }
        }
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        // Disable the first item from Spinner
        // First item will be use for hint
        return position != 0;
    }
}
