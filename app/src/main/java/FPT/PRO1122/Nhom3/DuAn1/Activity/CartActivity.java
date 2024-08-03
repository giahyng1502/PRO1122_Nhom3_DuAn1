package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.DAO.QuanLyGioHang;
import FPT.PRO1122.Nhom3.DuAn1.model.GioHang;
import FPT.PRO1122.Nhom3.DuAn1.adapter.GioHangAdapter;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private QuanLyGioHang quanLyGioHang;
    private  double tax;
    Context context;
    double tongTienDonHang;

    private ArrayList<GioHang> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        quanLyGioHang = new QuanLyGioHang(this, MainActivity.id);

        setVariable();
        initList();
        tinhTongGioHang();
        DatHang();
        binding.backBtn.setOnClickListener(v-> startActivity(new Intent(
                CartActivity.this,MainActivity.class)));
    }

    private void DatHang() {
        binding.DatHangbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ThanhToan_BottomSheetFragment bottomSheet = new ThanhToan_BottomSheetFragment();
                bottomSheet.setArguments(bundle);
                bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            }
        });
    }

    private void initList() {
        DatabaseReference myRef = database.getReference("Carts").child(MainActivity.id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    cartList.clear();
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        GioHang gioHang = issue.getValue(GioHang.class);
                        cartList.add(gioHang);
                    }
                    if (!cartList.isEmpty()) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
                        binding.cartRec.setLayoutManager(linearLayoutManager);
                        adapter = new GioHangAdapter(cartList, context, () -> tinhTongGioHang());
                        binding.cartRec.setAdapter(adapter);
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
                            tongGiaMonAn += gioHang.getPrice() * gioHang.getQuantity();
                        }
                    }
                }

                // Tính thuế
                double tax = Math.round((tongGiaMonAn * percentTax) * 100) / 100;

                // Tính tổng số tiền cần trả (giá sản phẩm + thuế + phí vận chuyển)
                double totalAmount = Math.round((tongGiaMonAn + tax + deliveryFee) * 100) / 100;

                // Cập nhật giao diện
                binding.totalFeeTxt.setText(tongGiaMonAn + " VND");
                binding.taxTxt.setText(tax + " VND");
                binding.deliveryFeeTxt.setText(deliveryFee + " VND");
                binding.totalTxt.setText(totalAmount + " VND");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());
    }

}