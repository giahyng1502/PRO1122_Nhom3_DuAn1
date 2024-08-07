package FPT.PRO1122.Nhom3.DuAn1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import FPT.PRO1122.Nhom3.DuAn1.Activity.ChiTietMonAn;
import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.model.GioHang;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.ViewHolder> {
    List<MonAnByThien> items;
    Context context;


    public AdapterFavorite(List<MonAnByThien> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public AdapterFavorite.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_list_mon_an, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFavorite.ViewHolder holder, int position) {
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
        holder.btnAddCart.setOnClickListener(v -> {
            addToCart(MainActivity.id,items.get(position));
        });
    }
    private void addToCart(String userId, MonAnByThien object) {
        // Khởi tạo FirebaseDatabase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Tham chiếu đến nút 'Carts' trong Realtime Database
        DatabaseReference cartRef = database.getReference("Carts").child(userId);

        // Tạo một key duy nhất cho mỗi món ăn trong giỏ hàng
        String itemId = String.valueOf(object.getId());

        // Tính toán tổng tiền
        double total = object.getPrice() * object.getNumberInCart();

        // Sử dụng addListenerForSingleValueEvent để đọc dữ liệu một lần
        cartRef.child(itemId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Nếu món ăn đã tồn tại trong giỏ hàng, cập nhật số lượng
                    GioHang existingCartItem = dataSnapshot.getValue(GioHang.class);
                    if (existingCartItem != null) {
                        int currentQuantity = existingCartItem.getQuantity();
                        int newQuantity = currentQuantity + 1;
                        double newTotal = existingCartItem.getPrice() * newQuantity;

                        // Cập nhật số lượng và tổng tiền
                        cartRef.child(itemId).child("quantity").setValue(newQuantity);
                        cartRef.child(itemId).child("total").setValue(newTotal)
                                .addOnSuccessListener(aVoid -> {
                                    // Xử lý khi cập nhật thành công
                                    Toast.makeText(context, "Đã cập nhật giỏ hàng", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    // Xử lý khi có lỗi
                                    Toast.makeText(context, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }
                } else {
                    // Nếu món ăn chưa tồn tại trong giỏ hàng, thêm mới
                    GioHang cartItem = new GioHang();
                    cartItem.setTitle(object.getTitle());
                    cartItem.setId(object.getId() + "");
                    cartItem.setPrice(object.getPrice());
                    cartItem.setQuantity(1);
                    cartItem.setTotal(total);
                    cartItem.setImagePath(object.getImagePath()); // Nếu cần lưu ImagePath

                    cartRef.child(itemId).setValue(cartItem)
                            .addOnSuccessListener(aVoid -> {
                                // Xử lý khi thêm thành công
                                Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                // Xử lý khi có lỗi
                                Toast.makeText(context, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi truy vấn dữ liệu
                Toast.makeText(context, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tittleTxt, priceTxt, starTxt, timeTxt,btnAddCart;
        ImageView foodsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tittleTxt = itemView.findViewById(R.id.titleListMonTxt);
            timeTxt = itemView.findViewById(R.id.timeListMonTxt);
            starTxt = itemView.findViewById(R.id.starListMonTxt);
            priceTxt = itemView.findViewById(R.id.priceListMonTxt);
            foodsImage = itemView.findViewById(R.id.foodsListMonImage);
            btnAddCart = itemView.findViewById(R.id.btnAddCart);

        }
    }
}
