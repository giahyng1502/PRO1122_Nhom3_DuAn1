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
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.ViewHolder> {

    ArrayList<MonAnByThien> items;
    Context context;

    public AdapterFavorite(ArrayList<MonAnByThien> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_list_mon_an, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        TextView tittleTxt, priceTxt, starTxt, timeTxt,plusBtn;
        ImageView foodsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tittleTxt = itemView.findViewById(R.id.titleListMonTxt);
            timeTxt = itemView.findViewById(R.id.timeListMonTxt);
            starTxt = itemView.findViewById(R.id.starListMonTxt);
            priceTxt = itemView.findViewById(R.id.priceListMonTxt);
            foodsImage = itemView.findViewById(R.id.foodsListMonImage);
            plusBtn = itemView.findViewById(R.id.plusBtn);

        }
    }

}
