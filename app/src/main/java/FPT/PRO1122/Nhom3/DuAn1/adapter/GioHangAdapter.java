package FPT.PRO1122.Nhom3.DuAn1.adapter;


import android.content.Context;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.DAO.ChangeNumberItemsListener;
import FPT.PRO1122.Nhom3.DuAn1.DAO.QuanLyGioHang;
import FPT.PRO1122.Nhom3.DuAn1.model.GioHang;
import FPT.PRO1122.Nhom3.DuAn1.R;


public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder>{

    ArrayList<GioHang> list = new ArrayList<>();
    private QuanLyGioHang quanLyGioHang;
    ChangeNumberItemsListener changeNumberItemsListener;
    int num;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Carts");


    public GioHangAdapter(ArrayList<GioHang> list, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.changeNumberItemsListener = changeNumberItemsListener;
        quanLyGioHang = new QuanLyGioHang(context, MainActivity.id);
        this.list = list;
        this.changeNumberItemsListener = changeNumberItemsListener;

    }

    @NonNull
    @Override
    public GioHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_item_cart, parent, false);
        return new ViewHolder(inflate);
    }


    private void updateItemInCart(String itemId, int newQuantity) {
        // Lấy giá của món ăn
        databaseReference.child(itemId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Double price = task.getResult().getValue(Double.class);
                if (price != null) {
                    // Tính toán tổng tiền mới
                    double newTotal = price * newQuantity;

                    // Tạo bản đồ chứa các cập nhật
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("quantity", newQuantity);
                    updates.put("total", newTotal);

                    // Cập nhật dữ liệu trong database
                    databaseReference.child(MainActivity.id).child(itemId).updateChildren(updates)
                            .addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    Log.d("Update", "Item updated successfully");
                                    notifyDataSetChanged();
                                } else {
                                    Log.e("Update", "Failed to update item", updateTask.getException());
                                }
                            });
                } else {
                    Log.e("Update", "Price is null");
                }
            } else {
                Log.e("Update", "Failed to get price", task.getException());
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.ViewHolder holder, int position) {
        holder.titleTxtInCart.setText(list.get(position).getTitle());
        holder.priceItemTxtInCart.setText(list.get(position).getPrice() + " VND");
        holder.numTxtInCart.setText(list.get(position).getQuantity() + "");
        holder.totalPriceTxtInCart.setText(list.get(position).getTotal() + " VND");

        GioHang item = list.get(position);

        holder.minusBtnInCart.setOnClickListener(v -> {
            num = item.getQuantity();
            if (num > 1) { // Đảm bảo số lượng không giảm xuống dưới 1
                num--;
                item.setQuantity(num);
                holder.numTxtInCart.setText(String.valueOf(num));
                String itemId = String.valueOf(item.getId());
                int newQuantity = num;
                double price = item.getPrice();
                double newTotal = newQuantity * price;

                Map<String, Object> updates = new HashMap<>();
                updates.put("quantity", newQuantity);
                updates.put("total", newTotal);
                databaseReference.child(MainActivity.id).child(itemId).updateChildren(updates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Cập nhật thành công
                                Log.d("Update", "Quantity updated successfully");
                            } else {
                                // Cập nhật không thành công
                                Log.e("Update", "Failed to update quantity", task.getException());
                            }
                            notifyDataSetChanged();
                        });
            } else {
                // Nếu số lượng là 1, xóa sản phẩm khỏi giỏ hàng
                String itemId = String.valueOf(item.getId());
                databaseReference.child(MainActivity.id).child(itemId).removeValue()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("Delete", "Item removed successfully");
                                list.clear();
                                notifyDataSetChanged();
                                // Gọi phương thức để làm mới RecyclerView
                            } else {
                                Log.e("Delete", "Failed to remove item", task.getException());
                            }
                        });
                notifyDataSetChanged();
            }
        });

        holder.plusBtnInCart.setOnClickListener(v -> {
            int num = item.getQuantity();
            num++;
            item.setQuantity(num);
            holder.numTxtInCart.setText(String.valueOf(num));

            String itemId = String.valueOf(item.getId());
            int newQuantity = num;
            double price = item.getPrice();
            double newTotal = newQuantity * price;

            Map<String, Object> updates = new HashMap<>();
            updates.put("quantity", newQuantity);
            updates.put("total", newTotal);
            databaseReference.child(MainActivity.id).child(itemId).updateChildren(updates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Cập nhật thành công
                            Log.d("Update", "Quantity updated successfully");
                            notifyDataSetChanged();
                        } else {
                            // Cập nhật không thành công
                            Log.e("Update", "Failed to update quantity", task.getException());
                        }
                    });
            notifyDataSetChanged();

        });

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxtInCart, priceItemTxtInCart, totalPriceTxtInCart, minusBtnInCart, numTxtInCart, plusBtnInCart ;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxtInCart = itemView.findViewById(R.id.titleTxtInCart);
            priceItemTxtInCart = itemView.findViewById(R.id.priceItemTxtInCart);
            totalPriceTxtInCart = itemView.findViewById(R.id.totalPriceTxtInCart);
            minusBtnInCart = itemView.findViewById(R.id.minusBtnInCart);
            numTxtInCart = itemView.findViewById(R.id.numTxtInCart);
            plusBtnInCart = itemView.findViewById(R.id.plusBtnInCart);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
