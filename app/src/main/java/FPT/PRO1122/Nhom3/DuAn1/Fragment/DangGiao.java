package FPT.PRO1122.Nhom3.DuAn1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.OrderHistoryAdapter;
import FPT.PRO1122.Nhom3.DuAn1.model.OrderHistory;

public class DangGiao extends Fragment {
    private OrderHistoryAdapter adapter;
    private ArrayList<OrderHistory> orderHistoryList;
    private DatabaseReference ordersRef;
    private RecyclerView recyclerViewOrderHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dang_g_iao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewOrderHistory = view.findViewById(R.id.recycler_viewDangGiao);
        orderHistoryList = new ArrayList<>();
        setupRecyclerView();
        loadOrderHistory();
    }

    private void setupRecyclerView() {
        recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new OrderHistoryAdapter(requireActivity(), orderHistoryList);
        recyclerViewOrderHistory.setAdapter(adapter);
    }

    private void loadOrderHistory() {
        ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderHistoryList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    // Duyệt qua từng đơn hàng
                    for (DataSnapshot orderSnapshot : userSnapshot.getChildren()) {
                        // Chuyển đổi thành đối tượng OrderHistory
                        OrderHistory order = orderSnapshot.getValue(OrderHistory.class);
                        if (order != null && order.getStatus() == 1) {
                            orderHistoryList.add(order);
                        }
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(), "Lỗi khi tải đơn hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
