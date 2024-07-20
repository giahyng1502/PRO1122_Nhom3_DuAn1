package FPT.PRO1122.Nhom3.DuAn1;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationBarView;

import FPT.PRO1122.Nhom3.DuAn1.Fragment.Favorite;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Home;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.MyOrder;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Profile;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ràng buộc để trỏ đến id của layout thông qua bind
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        bind.bottomNavigationView.setBackground(null);

        // Chuyển đến fragment Giỏ hàng
        bind.btnCart.setOnClickListener(v -> replaceFragment(new MyOrder()));

        // Vào fragment Home ngay khi đăng nhập
        replaceFragment(new Home());
        setBottomNavigation();
    }

    private void setBottomNavigation() {
        // Khi click vào từng icon sẽ mở fragment tương ứng
        bind.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (R.id.home == item.getItemId()) {
                    replaceFragment(new Home());
                } else if (R.id.favorite == item.getItemId()) {
                    replaceFragment(new Favorite());
                } else if (R.id.myOrder == item.getItemId()) {
                    replaceFragment(new MyOrder());
                } else if (R.id.profile == item.getItemId()) {
                    replaceFragment(new Profile());
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        // Hàm thay thế fragment
        // Đưa fragment vào frameLayout
        getSupportFragmentManager().beginTransaction().
                replace(R.id.frameLayout, fragment).
                commit();
    }
}