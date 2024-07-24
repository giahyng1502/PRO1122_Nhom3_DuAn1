package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import FPT.PRO1122.Nhom3.DuAn1.Fragment.Favorite;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Home;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Myorder;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Profile;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;

    Fragment fragment;
    BottomNavigationView bottomNavigationItemView;
    FloatingActionButton btnCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        setBottonNavition();
        //
        bottomNavigationItemView.setBackground(null);
        //
        bottomNavigationItemView.getMenu().getItem(2).isEnabled(

        );
    }



    private void setBottonNavition() {
        // chuyen đến fragment giỏ hàng
        btnCart.setOnClickListener(v ->
                getSupportFragmentManager().beginTransaction().
                replace(R.id.frameLayout,new Myorder()).
                commit());


        // vào fragment home ngay khi đăng nhập
        getSupportFragmentManager().beginTransaction().
                replace(R.id.frameLayout,new Home()).
                commit();
        // khi click sẽ mở fragment tương ứng
        bottomNavigationItemView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (R.id.home == item.getItemId()) {
                    fragment = new Home();
                } else if (R.id.favorite == item.getItemId()) {
                    fragment = new Favorite();
                }else if (R.id.myOrder == item.getItemId()) {
                    fragment = new Myorder();
                }else if (R.id.profile == item.getItemId()) {
                    fragment = new Profile();
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