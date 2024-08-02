package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

    private ArrayList<GioHang> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        quanLyGioHang = new QuanLyGioHang(this, MainActivity.id);

        setVariable();
        caculateCart();
        initList();
        binding.backBtn.setOnClickListener(v-> startActivity(new Intent(
                CartActivity.this,MainActivity.class)));
        binding.button3.setOnClickListener(v-> startActivity(new Intent(
                CartActivity.this,ActivityPaySuccess.class)));
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
                        adapter = new GioHangAdapter(cartList, context, () -> caculateCart());
                        binding.cartRec.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void caculateCart() {
        double percentTax = 0.08;
        double delivery = 10000;
        double totalFee = 0;

//        tax = Math.round((quanLyGioHang.getTotalFee() * percentTax) * 100) / 100;
//        double total = Math.round((quanLyGioHang.getTotalFee() + tax + delivery) * 100) / 100;
//        double itemTotal = Math.round(quanLyGioHang.getTotalFee() * 100) / 100;
//
//        binding.totalFeeTxt.setText(itemTotal + " VND");
//        binding.taxTxt.setText(tax + " VND");
//        binding.deliveryFeeTxt.setText(delivery + " VND");
//        binding.totalTxt.setText(total + " VND");
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());
    }
}