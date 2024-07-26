package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import FPT.PRO1122.Nhom3.DuAn1.DAO.QuanLyGioHang;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityChiTietMonAnBinding;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;

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
        binding.numTxt.setText(num+"");
        getIntentExtra();
        setVariable();
        // add data favorite Food
        favoriteFood();
    }
    private void favoriteFood () {
        binding.favBtn.setOnClickListener(new View.OnClickListener() {
            private boolean isFavorite = false;
            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Foods")
                    .child(object.getId()+"").child("BestFood");
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    databaseReference.setValue(false).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            binding.favBtn.setImageResource(R.drawable.favorite);
                        }
                    });
                    // đổi lại thành trái tim trắng
                } else {
                    databaseReference.setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            binding.favBtn.setImageResource(R.drawable.favorite_select);
                        }
                    });
                     // đổi thành trái tim đỏ
                }
                isFavorite = !isFavorite; // cập nhật trạng thái
            }
        });
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

            if (num <= 1) {
               return;
            } else {
                num = num - 1;
                binding.minusBtn.setEnabled(true);
            }
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