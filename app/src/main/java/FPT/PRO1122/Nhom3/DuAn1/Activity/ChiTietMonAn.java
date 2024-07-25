package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import FPT.PRO1122.Nhom3.DuAn1.DAO.QuanLyGioHang;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityChiTietMonAnBinding;
import FPT.PRO1122.Nhom3.DuAn1.Model.MonAnByThien;

public class ChiTietMonAn extends AppCompatActivity {
    ActivityChiTietMonAnBinding binding;
    private MonAnByThien object;
    private  int num = 1;
    private QuanLyGioHang quanLyGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_mon_an);
        binding = ActivityChiTietMonAnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        quanLyGioHang = new QuanLyGioHang(this);

        binding.backBtn.setOnClickListener(v -> finish());

        Glide.with(ChiTietMonAn.this).load(object.getImagePath()).into(binding.pic);

        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText(object.getPrice() + " VND");
        binding.rateTxt.setText("" + object.getStar());
        binding.descriptionTxt.setText(object.getDescription());
        binding.timeTxt.setText(object.getTimeValue() + " phút");
        binding.totalTxt.setText(num + object.getPrice()+" VND");

        binding.plusBtn.setOnClickListener(v -> {
            num = num + 1;
            binding.numTxt.setText(num + "");
            binding.totalTxt.setText((num * object.getPrice()) + " VND");
        });
        binding.minusBtn.setOnClickListener(v -> {
            num = num - 1;
            binding.numTxt.setText(num + "");
            binding.totalTxt.setText((num * object.getPrice()) + " VND");
        });
        binding.AddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberInCart(num);
                quanLyGioHang.insertFood(object);
            }
        });
    }

    private void getIntentExtra() {
        object = (MonAnByThien) getIntent().getSerializableExtra("object");
    }
}