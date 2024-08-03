package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.CheckOutAdapter;
import FPT.PRO1122.Nhom3.DuAn1.adapter.GioHangAdapter;
import FPT.PRO1122.Nhom3.DuAn1.model.GioHang;

public class ThanhToan_BottomSheetFragment extends BottomSheetDialogFragment {
    TextView tongTientxt;
    EditText ten, sdt, diaChi;
    RecyclerView rcThanhToan;
    Button datHangBtn;
    private ArrayList<GioHang> checkOutList = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tongTientxt = view.findViewById(R.id.tongTientxt);
        ten = view.findViewById(R.id.tenEdt);
        sdt = view.findViewById(R.id.soDienThoaiEdt);
        diaChi = view.findViewById(R.id.diaChiGiaoHang);
        rcThanhToan = view.findViewById(R.id.rcThanhToan);
        datHangBtn = view.findViewById(R.id.datHangBtn);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
        userRef.child(MainActivity.id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get user information from the snapshot
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String phone = dataSnapshot.child("phoneNumber").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);

                    // Set the information to EditText fields
                    ten.setText(name);
                    sdt.setText(phone);
                    diaChi.setText(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        initRec();

        datHangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initRec() {
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Carts").child(MainActivity.id);
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    checkOutList.clear();
                    if (snapshot.exists()){
                        for (DataSnapshot issue : snapshot.getChildren()){
                            GioHang gioHang = issue.getValue(GioHang.class);
                            checkOutList.add(gioHang);
                        }
                    }
                    if (!checkOutList.isEmpty()){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rcThanhToan.setLayoutManager(linearLayoutManager);
                        adapter = new CheckOutAdapter(checkOutList, context , () -> tinhTongGioHang());
                        rcThanhToan.setAdapter(adapter);
                        tinhTongGioHang();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void tinhTongGioHang() {
        double percentTax = 0.02;
        double deliveryFee = 10000;

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Carts").child(MainActivity.id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double tongGiaMonAn = 0.0; // Khởi tạo biến lưu tổng giá sản phẩm


                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        GioHang gioHang = itemSnapshot.getValue(GioHang.class);

                        if (gioHang != null) {
                            // Cộng dồn giá sản phẩm (giả sử getPrice() và getQuantity() là các phương thức của GioHang)
                            tongGiaMonAn += gioHang.getTotal();
                        }
                    }
                }

                // Tính thuế
                double tax = Math.round((tongGiaMonAn * percentTax) * 100) / 100;

                // Tính tổng số tiền cần trả (giá sản phẩm + thuế + phí vận chuyển)
                double totalAmount = Math.round((tongGiaMonAn + tax + deliveryFee) * 100) / 100;

                // Cập nhật giao diện
                tongTientxt.setText("Tổng tiền: " + totalAmount + " VND");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
