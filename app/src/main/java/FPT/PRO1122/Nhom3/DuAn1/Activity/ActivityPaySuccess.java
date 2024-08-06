package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.AdapterFoodPay;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityPaySuccessBinding;
import FPT.PRO1122.Nhom3.DuAn1.model.GioHang;

public class ActivityPaySuccess extends AppCompatActivity {
    ActivityPaySuccessBinding binding;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        binding = ActivityPaySuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initList();
        binding.ivBack.setOnClickListener(v -> startActivity(new Intent(ActivityPaySuccess.this, MainActivity.class)));
    }

    private void initList() {
        ArrayList<GioHang> cartList = new ArrayList<>();
        Intent intent = getIntent();
        cartList = intent.getParcelableArrayListExtra("listFoods");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityPaySuccess.this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerPaySuccess.setLayoutManager(linearLayoutManager);
        adapter = new AdapterFoodPay(this, cartList);
        binding.recyclerPaySuccess.setAdapter(adapter);
    }
}

