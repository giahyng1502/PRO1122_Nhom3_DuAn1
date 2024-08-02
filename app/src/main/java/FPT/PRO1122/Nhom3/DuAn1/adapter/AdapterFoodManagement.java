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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;

public class AdapterFoodManagement extends RecyclerView.Adapter<AdapterFoodManagement.ViewHolder> {
    Context context;
    List<MonAnByThien> list;

    public AdapterFoodManagement(Context context, List<MonAnByThien> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterFoodManagement.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_admin,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFoodManagement.ViewHolder holder, int position) {
        MonAnByThien food = list.get(position);
        holder.tvTitle.setText(food.getTitle());
        holder.tvDecr.setText(food.getDescription());
        holder.tvRate.setText(food.getStar()+"");
        holder.tvPrice.setText(food.getPrice() +"");

        FirebaseDatabase.getInstance()
                        .getReference("Category")
                        .child(food.getCategoryID()+"")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            String categoryName = snapshot.child("Name").getValue(String.class);
                                            holder.tvCatagory.setText(categoryName);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        holder.tvCatagory.setText("");
                                    }
                                });

        Glide.with(context).load(food.getImagePath()).into(holder.ivFoodAvatar);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvPrice,tvDecr,tvRate,tvCatagory;
        ImageView ivFoodAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleFood);
            tvPrice = itemView.findViewById(R.id.tvPriceFood);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvDecr = itemView.findViewById(R.id.tvMota);
            tvCatagory = itemView.findViewById(R.id.tvCategoryTitle);
            ivFoodAvatar = itemView.findViewById(R.id.ivAvatarFood);
        }
    }
}
