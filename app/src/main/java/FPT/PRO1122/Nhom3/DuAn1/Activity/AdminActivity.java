package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import FPT.PRO1122.Nhom3.DuAn1.Fragment.Admin.FoodManagement;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Admin.OrderManagement;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Admin.RevenuaManager;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Admin.VorcherManagement;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Admin.UserManagement;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityAdminBinding;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityLoginBinding;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment,new FoodManagement()).commit();

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.nav_user_management) {
                    fragment = new UserManagement();
                } else if (itemId == R.id.nav_food_management) {
                    fragment = new FoodManagement();
                } else if (itemId == R.id.nav_revenue_management) {
                    fragment = new RevenuaManager();
                } else if (itemId == R.id.nav_voucher_management) {
                    fragment = new VorcherManagement();
                } else if (itemId == R.id.nav_order_management) {
                    fragment = new OrderManagement();
                }else if (itemId == R.id.nav_BackHome) {
                    startActivity(new Intent(AdminActivity.this, MainActivity.class));
                }
                // Thay thế Fragment hiện tại bằng Fragment mới
                if (fragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

                // Đóng Drawer sau khi chọn một mục
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}