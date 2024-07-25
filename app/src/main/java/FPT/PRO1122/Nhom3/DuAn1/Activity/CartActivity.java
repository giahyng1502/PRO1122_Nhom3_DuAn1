package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import FPT.PRO1122.Nhom3.DuAn1.DAO.QuanLyGioHang;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.GioHangAdapter;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private QuanLyGioHang quanLyGioHang;
    private  double tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        quanLyGioHang = new QuanLyGioHang(this);

        setVariable();
        caculataCart();
        initList();
    }

    private void initList() {
        if (quanLyGioHang.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);
        }else{
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cartRec.setLayoutManager(linearLayoutManager);
        adapter = new GioHangAdapter(quanLyGioHang.getListCart(), this, () -> caculataCart());

        binding.cartRec.setAdapter(adapter);
    }

    private void caculataCart() {
        double percentTax = 0.08;
        double delivery = 10000;

        tax = Math.round((quanLyGioHang.getTotalFee() * percentTax) * 100) / 100;
        double total = Math.round((quanLyGioHang.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(quanLyGioHang.getTotalFee() * 100) / 100;

        binding.totalFeeTxt.setText(itemTotal + " VND");
        binding.taxTxt.setText(tax + " VND");
        binding.deliveryFeeTxt.setText(delivery + " VND");
        binding.totalTxt.setText(total + " VND");
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());
    }
}