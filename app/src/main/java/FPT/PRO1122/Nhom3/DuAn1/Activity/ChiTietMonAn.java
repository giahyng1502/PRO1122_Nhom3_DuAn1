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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private void favoriteFood() {
        // Đảm bảo rằng object không bị null
        if (object == null) {
            return;
        }

        // Khởi tạo DatabaseReference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("Favorite").child(MainActivity.id)
                .child(object.getId() + "");

        // Thiết lập OnClickListener cho nút yêu thích
        binding.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object.getBestFood()) {
                    // Nếu món ăn là món yêu thích -> bỏ yêu thích
                    object.setBestFood(false);
                    databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // Đặt lại hình trái tim màu đỏ
                            binding.favBtn.setImageResource(R.drawable.favorite);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý lỗi nếu cần
                        }
                    });
                } else {
                    // Nếu món ăn chưa được yêu thích -> yêu thích
                    object.setBestFood(true);
                    databaseReference.setValue(object).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // Đặt lại hình trái tim màu trắng đầy
                            binding.favBtn.setImageResource(R.drawable.favorite_select);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý lỗi nếu cần
                        }
                    });
                }
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

        //set status favorite
        getFavoriteUser(MainActivity.id, object.getId());


        binding.plusBtn.setOnClickListener(v -> {
            num = num + 1;
            binding.numTxt.setText(num + "");

            double total = (num* object.getPrice());
            String totalString = String.format("%.2f",total) + "VND";
            binding.totalTxt.setText(totalString);
        });
        binding.minusBtn.setOnClickListener(v -> {

            if (num <= 1) {
               return;
            } else {
                num = num - 1;
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

    private void getFavoriteUser(String userID,int foodID) {
        try {
            FirebaseDatabase.getInstance().getReference("Favorite")
                    .child(userID).child(foodID+"")
                    .orderByChild("bestFood")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                binding.favBtn.setImageResource(R.drawable.favorite_select);
                            } else {
                                binding.favBtn.setImageResource(R.drawable.favorite);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }catch (Exception e) {
            Toast.makeText(this, "Error" + e, Toast.LENGTH_SHORT).show();
        }

    }

    private void getIntentExtra() {
        object = (MonAnByThien) getIntent().getSerializableExtra("object");
    }
}