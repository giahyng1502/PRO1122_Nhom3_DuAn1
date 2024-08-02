package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.AdapterFoodPay;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityPaySuccessBinding;
import FPT.PRO1122.Nhom3.DuAn1.model.GioHang;

public class ActivityPaySuccess extends AppCompatActivity {
    ActivityPaySuccessBinding binding;
    RecyclerView.Adapter adapter;
    private ArrayList<GioHang> cartList = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay_success);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding = ActivityPaySuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initList();
        binding.ivBack.setOnClickListener(v-> startActivity(new Intent(ActivityPaySuccess.this,MainActivity.class)));
    }


    private  void  initList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityPaySuccess.this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerPaySuccess.setLayoutManager(linearLayoutManager);
        adapter = new AdapterFoodPay(context,cartList);
        binding.recyclerPaySuccess.setAdapter(adapter);
    }
}

