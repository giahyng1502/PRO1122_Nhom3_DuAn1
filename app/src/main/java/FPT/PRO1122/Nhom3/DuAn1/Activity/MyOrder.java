package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.OrderHistoryAdapter;
import FPT.PRO1122.Nhom3.DuAn1.databinding.FragmentMyorderBinding;
import FPT.PRO1122.Nhom3.DuAn1.model.OrderHistory;

public class MyOrder extends BaseActivity {
    ImageView btn_back;
    private OrderHistoryAdapter adapter;
    private ArrayList<OrderHistory> orderHistoryList = new ArrayList<>();
    private DatabaseReference ordersRef;
    RecyclerView recyclerViewOrderHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //
        btn_back=findViewById(R.id.btn_back_order);
        recyclerViewOrderHistory = findViewById(R.id.recyclerViewOrderHistory);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OrderHistoryAdapter(this, orderHistoryList);
        recyclerViewOrderHistory.setAdapter(adapter);

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
                        orderHistoryList.add(orderHistory);
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