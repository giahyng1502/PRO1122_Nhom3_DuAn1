package FPT.PRO1122.Nhom3.DuAn1;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import FPT.PRO1122.Nhom3.DuAn1.Fragment.Favorite;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Home;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Myorder;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Profile;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    BottomNavigationView bottomNavigationItemView;
    CardView btnCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        setBottonNavition();

    }

    private void setBottonNavition() {
        // chuyen đến fragment giỏ hàng
        btnCart.setOnClickListener(v->
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.frameLayout,new Home()).
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