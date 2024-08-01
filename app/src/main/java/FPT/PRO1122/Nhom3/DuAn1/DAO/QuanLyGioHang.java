package FPT.PRO1122.Nhom3.DuAn1.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.model.GioHang;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;




public class QuanLyGioHang implements ChangeNumberItemsListener{
    FirebaseDatabase database;
    private Context context;
    private DatabaseReference cartRef;
    private String userId;
    private ArrayList<GioHang> listItem = new ArrayList<>();

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

    public Double getTotalFee(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Carts").child(MainActivity.id);
        final double[] totalAmount = {0}; // Sử dụng mảng để lưu tổng số tiền, vì biến trong hàm không thể thay đổi giá trị của biến bên ngoài

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    listItem.clear(); // Giả sử listItem là một ArrayList<GiỏHàng> hoặc tương tự
                    totalAmount[0] = 0; // Reset tổng số tiền

                    for (DataSnapshot issue : snapshot.getChildren()) {
                        GioHang gioHang = issue.getValue(GioHang.class);
                        listItem.add(gioHang);
                        totalAmount[0] += gioHang.getTotal(); // Tính tổng số tiền
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
            }
        });

        // Trả về tổng số tiền sau khi tính toán xong
        return totalAmount[0];
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
