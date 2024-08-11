package FPT.PRO1122.Nhom3.DuAn1.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.model.Cart;

@SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    ArrayList<Cart> list;
    int num;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Carts");

    public GioHangAdapter(ArrayList<Cart> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public GioHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_item_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.ViewHolder holder, int position) {
        NumberFormat vietnameseCurrencyFormat = NumberFormat.getCurrencyInstance();
        vietnameseCurrencyFormat.setMaximumFractionDigits(0);
        vietnameseCurrencyFormat.setCurrency(Currency.getInstance("VND"));
        Cart item = list.get(position);

        holder.titleTxtInCart.setText(item.getTitle());
        double priceFormated = item.getPrice();
        String formattedNumber = vietnameseCurrencyFormat.format(priceFormated);
        holder.priceItemTxtInCart.setText(formattedNumber);
        holder.numTxtInCart.setText(item.getQuantity() + "");
        double totalPriceFormated = item.getTotal();
        String totalPriceFormatted = vietnameseCurrencyFormat.format(totalPriceFormated);
        holder.totalPriceTxtInCart.setText(totalPriceFormatted);

        holder.minusBtnInCart.setOnClickListener(v -> {
            num = item.getQuantity();

            if (num > 1) { // Đảm bảo số lượng không giảm xuống dưới 1
                num--;
                item.setQuantity(num);
                holder.numTxtInCart.setText(String.valueOf(num));
                String itemId = String.valueOf(item.getCartId());
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
            } else {
                // Nếu số lượng là 1, xóa sản phẩm khỏi giỏ hàng
                String itemId = String.valueOf(item.getCartId());
                databaseReference.child(MainActivity.id).child(itemId).removeValue()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                if (list.size() == 1) {
                                    list.clear();
                                }
                                notifyDataSetChanged();
                                Log.d("Delete", "Item removed successfully");
                            } else {
                                Log.e("Delete", "Failed to remove item", task.getException());
                            }
                        });
            }
        });

        holder.plusBtnInCart.setOnClickListener(v -> {
            int num = item.getQuantity();
            num++;
            item.setQuantity(num);
            holder.numTxtInCart.setText(String.valueOf(num));

            String itemId = String.valueOf(item.getCartId());
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
                .load(item.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .thumbnail(Glide.with(holder.itemView.getContext()).load(R.drawable.loading_image))
                .fitCenter()
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxtInCart, priceItemTxtInCart, totalPriceTxtInCart, minusBtnInCart, numTxtInCart, plusBtnInCart;
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
