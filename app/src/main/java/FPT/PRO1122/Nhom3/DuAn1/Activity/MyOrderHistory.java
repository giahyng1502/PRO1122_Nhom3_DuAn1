package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.MyOrderPagerAdapter;
import FPT.PRO1122.Nhom3.DuAn1.adapter.OrderHistoryAdapter;
import FPT.PRO1122.Nhom3.DuAn1.model.OrderHistory;

public class MyOrderHistory extends AppCompatActivity {
    private OrderHistoryAdapter adapter;
    private ArrayList<OrderHistory> orderHistoryList = new ArrayList<>();
    private DatabaseReference ordersRef;
    private RecyclerView recyclerViewOrderHistory;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerViewOrderHistory = findViewById(R.id.recycler_viewUser);
        setupRecyclerView();
    }
    private void setupRecyclerView() {
        recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(MyOrderHistory.this));
        adapter = new OrderHistoryAdapter(MyOrderHistory.this, orderHistoryList);
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
                        if (orderHistory.getStatus() == 0) {
                            orderHistoryList.add(orderHistory);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyOrderHistory.this, "Lỗi khi tải đơn hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}