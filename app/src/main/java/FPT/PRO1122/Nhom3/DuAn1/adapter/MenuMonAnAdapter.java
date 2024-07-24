package FPT.PRO1122.Nhom3.DuAn1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.Model.DanhMucMonAn;

public class MenuMonAnAdapter extends RecyclerView.Adapter<MenuMonAnAdapter.ViewHolder> {
    ArrayList<DanhMucMonAn> items;
    Context context;

    public MenuMonAnAdapter(ArrayList<DanhMucMonAn> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MenuMonAnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_holder_categories, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuMonAnAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView catNameTxt;
        ImageView imgCat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCat = itemView.findViewById(R.id.imgCat);
            catNameTxt = itemView.findViewById(R.id.catNameTxt);
        }
    }
}
