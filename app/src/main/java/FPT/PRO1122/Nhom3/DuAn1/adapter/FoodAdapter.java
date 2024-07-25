//package FPT.PRO1122.Nhom3.DuAn1.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//import FPT.PRO1122.Nhom3.DuAn1.R;
//
//public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
//    Context context;
//    List<MonAn> list;
//
//    public FoodAdapter(Context context, List<MonAn> list) {
//        this.context = context;
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_food,parent,false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
////        Food food = list.get(position);
////        Glide.with(context).load(food.getImageMon()).into(holder.ivFood);
////        holder.tvName.setText(food.getTenMon());
////        holder.tvPrice.setText(food.getGiaMon());
////        holder.tvRate.setText("5");
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView ivFood;
//        TextView tvName,tvPrice,tvRate;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            ivFood = itemView.findViewById(R.id.ivFoodHome);
//            tvName = itemView.findViewById(R.id.tvNameFoodHome);
//            tvPrice = itemView.findViewById(R.id.tvPriceHome);
//            tvRate = itemView.findViewById(R.id.tvRateHome);
//        }
//    }
//}
