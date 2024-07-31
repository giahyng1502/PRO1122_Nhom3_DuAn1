package FPT.PRO1122.Nhom3.DuAn1.DAO;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import FPT.PRO1122.Nhom3.DuAn1.model.GioHang;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;



public class QuanLyGioHang implements ChangeNumberItemsListener{
    private Context context;
    private DatabaseReference cartRef;
    private String userId;

    public QuanLyGioHang(Context context, String userId) {
        this.context = context;
        this.userId = userId;
        this.cartRef = FirebaseDatabase.getInstance().getReference("Carts").child(userId);
    }


    public void addFoodToCart(MonAnByThien item) {
        String itemId = String.valueOf(item.getId());  // Assuming 'item.getId()' returns a unique ID for each item
        double total = item.getPrice() * item.getNumberInCart();

        Map<String, Object> cartItem = new HashMap<>();
        cartItem.put("Title", item.getTitle());
        cartItem.put("Price", item.getPrice());
        cartItem.put("Quantity", item.getNumberInCart());
        cartItem.put("Total", total);

        cartRef.child(itemId).setValue(cartItem)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    public void getListCart(final CartCallback callback) {
        cartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<GioHang> list = new ArrayList<>();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    GioHang item = snapshot.getValue(GioHang.class);
                    if (item != null) {
                        list.add(item);
                    }
                }
                callback.onSuccess(list);
            } else {
                callback.onFailure(task.getException());
            }
        });
    }


    @Override
    public void change() {
        // Implement any logic needed when cart changes
    }

    public interface CartCallback {
        void onSuccess(ArrayList<GioHang> list);
        void onFailure(Exception e);
    }


}
