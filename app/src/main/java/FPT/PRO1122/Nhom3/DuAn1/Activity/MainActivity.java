package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import FPT.PRO1122.Nhom3.DuAn1.Fragment.Favorite;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Home;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.MyOrder;
import FPT.PRO1122.Nhom3.DuAn1.R;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    BottomNavigationView bottomNavigationItemView;
    FloatingActionButton btnCart;
    public static String id;
    public static int idFrament = R.id.home;
    public static int role = 1;

    private boolean doubleBackToExitPressedOnce = false;
    private Handler handler = new Handler();
    private Runnable resetDoubleBackFlag = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            // neu true thi thoat
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit the application", Toast.LENGTH_SHORT).show();

        handler.postDelayed(resetDoubleBackFlag, 2000); // Đặt lại cờ sau 2 giây
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(resetDoubleBackFlag); // Khi Activity bị hủy, xóa callback để tránh rò rỉ bộ nhớ.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = getIDCurrentAccout();
        getUserCurrent();
        anhXa();
        setBottonNavition();
        //
        bottomNavigationItemView.setBackground(null);
        //
//        bottomNavigationItemView.getMenu().getItem(2).isEnabled();
        //
        if (idFrament == R.id.home) {
            bottomNavigationItemView.setSelectedItemId(idFrament);
        } else {
            bottomNavigationItemView.setSelectedItemId(idFrament);
            idFrament = R.id.home;
        }

    }
    public String getIDCurrentAccout() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserID", MODE_PRIVATE);
        return sharedPreferences.getString("id", "");
    }

    private void getUserCurrent() {
        FirebaseDatabase.getInstance().getReference("users")
                .child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            getRole(snapshot.child("role").getValue(Integer.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this, "get role fail "+error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void getRole(int role) {
        this.role = role;
    }

    private void setBottonNavition() {
        // chuyen đến fragment giỏ hàng
        btnCart.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CartActivity.class)));


        // vào fragment home ngay khi đăng nhập
//            getSupportFragmentManager().beginTransaction().
//                    replace(R.id.frameLayout,new Home()).
//                    commit();

        // khi click sẽ mở fragment tương ứng
        bottomNavigationItemView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (R.id.home == item.getItemId()) {
                    fragment = new Home();
                } else if (R.id.favorite == item.getItemId()) {
                    fragment = new Favorite();
                }else if (R.id.myOrder == item.getItemId()) {
                    fragment = new MyOrder();
                }else if (R.id.profile == item.getItemId()) {
                    startActivity(new Intent(MainActivity.this, Profile.class));
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.frameLayout,fragment).
                            commit();

                }
                return true;
            }
        });
    }

    private void anhXa() {
        bottomNavigationItemView = findViewById(R.id.bottomNavigationView);
        btnCart = findViewById(R.id.btnCart);
    }
}