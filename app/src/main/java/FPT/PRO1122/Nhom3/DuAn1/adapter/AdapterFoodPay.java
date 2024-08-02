package FPT.PRO1122.Nhom3.DuAn1.adapter;

import android.content.Context;
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

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.model.GioHang;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;

public class AdapterFoodPay extends RecyclerView.Adapter<AdapterFoodPay.FoodPayViewHolder> {

    private Context context;
    private ArrayList<GioHang> arrayList;

    public AdapterFoodPay(Context context, ArrayList<GioHang> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public FoodPayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_paysuccess,parent,false);
        return new FoodPayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodPayViewHolder holder, int position) {
        holder.tvNameFood.setText(arrayList.get(position).getTitle());
        holder.tvPriceFood.setText(String.valueOf(arrayList.get(position).getPrice()));
        holder.tvNoteFood.setText(" Món ăn được ưa thích nhất !!!");

        Glide.with(holder.itemView.getContext())
                .load(arrayList.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.ivAvatarFood);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FoodPayViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNameFood,tvNoteFood,tvPriceFood;
        private ImageView ivAvatarFood;

        public FoodPayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameFood = itemView.findViewById(R.id.tvNameFoodPay);
            tvNoteFood = itemView.findViewById(R.id.tvNoteFoodPay);
            tvPriceFood = itemView.findViewById(R.id.tvPriceFoodPay);
            ivAvatarFood = itemView.findViewById(R.id.ivAvatarFoodPay);
        }
    }
}
