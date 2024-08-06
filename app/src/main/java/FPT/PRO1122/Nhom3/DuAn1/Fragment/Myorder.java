package FPT.PRO1122.Nhom3.DuAn1.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.Activity.MyOrder;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.OrderHistoryAdapter;
import FPT.PRO1122.Nhom3.DuAn1.model.OrderHistory;


public class Myorder extends Fragment {
    ImageView btn_back;
    private OrderHistoryAdapter adapter;
    private ArrayList<OrderHistory> orderHistoryList = new ArrayList<>();
    private DatabaseReference ordersRef;
    RecyclerView recyclerViewOrderHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myorder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_back= view.findViewById(R.id.btn_back_order);
        recyclerViewOrderHistory = view.findViewById(R.id.recyclerViewOrderHistory);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderHistoryAdapter(getContext(), orderHistoryList);
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
                        orderHistoryList.add(orderHistory);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi khi tải đơn hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}