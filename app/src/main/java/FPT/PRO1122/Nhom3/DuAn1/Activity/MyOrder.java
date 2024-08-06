package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.OrderHistoryAdapter;
import FPT.PRO1122.Nhom3.DuAn1.model.OrderHistory;

public class MyOrder extends AppCompatActivity {
    private OrderHistoryAdapter adapter;
    private ArrayList<OrderHistory> orderHistoryList = new ArrayList<>();
    private DatabaseReference ordersRef;
    private RecyclerView recyclerViewOrderHistory;
    private ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_myorder);

        recyclerViewOrderHistory = findViewById(R.id.recyclerViewOrderHistory);
        ivBack = findViewById(R.id.backButton);
        ivBack.setOnClickListener(v->finish());
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderHistoryAdapter(this, orderHistoryList);
        recyclerViewOrderHistory.setAdapter(adapter);

        // Khởi tạo tham chiếu đến cơ sở dữ liệu và tải dữ liệu
        ordersRef = FirebaseDatabase.getInstance().getReference("Orders").child(MainActivity.id);
        loadOrderHistory();
    }

    private void loadOrderHistory() {
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderHistoryList.clear();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    OrderHistory orderHistory = orderSnapshot.getValue(OrderHistory.class);
                    if (orderHistory != null) {
                        if (orderHistory.getStatus() != 0) {
                            orderHistoryList.add(orderHistory);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyOrder.this, "Lỗi khi tải đơn hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
