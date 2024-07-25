package FPT.PRO1122.Nhom3.DuAn1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.Activity.ChiTietMonAn;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.Model.MonAnByThien;

public class DoAnBanChayAdapter extends RecyclerView.Adapter<DoAnBanChayAdapter.ViewHolder> {
    ArrayList<MonAnByThien> items;
    Context context;


    public DoAnBanChayAdapter(ArrayList<MonAnByThien> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public DoAnBanChayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_best_deal, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DoAnBanChayAdapter.ViewHolder holder, int position) {
        holder.tittleTxt.setText(items.get(position).getTitle());
        holder.priceTxt.setText(items.get(position).getPrice() + "VND");
        holder.timeTxt.setText(items.get(position).getTimeValue() + "phút");
        holder.starTxt.setText("" + items.get(position).getStar());

        Glide.with(context).load(items.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.foodsImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChiTietMonAn.class);
            intent.putExtra("object", items.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tittleTxt, priceTxt, starTxt, timeTxt;
        ImageView foodsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tittleTxt = itemView.findViewById(R.id.titleTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            starTxt = itemView.findViewById(R.id.starTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            foodsImage = itemView.findViewById(R.id.foodsImage);

        }
    }
}
