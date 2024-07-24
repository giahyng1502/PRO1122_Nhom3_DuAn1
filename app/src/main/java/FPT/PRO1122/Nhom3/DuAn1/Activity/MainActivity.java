package FPT.PRO1122.Nhom3.DuAn1.Activity;

<<<<<<< HEAD
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
=======
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import java.util.ArrayList;
import java.util.List;
>>>>>>> c340bd93e9e032a8109f2b0566eeb37c36be2702

import FPT.PRO1122.Nhom3.DuAn1.Fragment.Favorite;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Home;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Myorder;
<<<<<<< HEAD
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Profile;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;

    Fragment fragment;
    BottomNavigationView bottomNavigationItemView;
    FloatingActionButton btnCart;


=======
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.model.User;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    BottomNavigationView bottomNavigationItemView;
    FloatingActionButton btnCart;
    public static String id;
>>>>>>> c340bd93e9e032a8109f2b0566eeb37c36be2702
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        setBottonNavition();
        //
        bottomNavigationItemView.setBackground(null);
        //
<<<<<<< HEAD
        bottomNavigationItemView.getMenu().getItem(2).isEnabled(

        );
    }
=======
        bottomNavigationItemView.getMenu().getItem(2).isEnabled();
        //
        id = getIDCurrentAccout();
    }
    public String getIDCurrentAccout() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserID", MODE_PRIVATE);
        return sharedPreferences.getString("id", "");
    }

>>>>>>> c340bd93e9e032a8109f2b0566eeb37c36be2702



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
<<<<<<< HEAD
                    fragment = new Profile();
=======
                    startActivity(new Intent(MainActivity.this, Profile.class));
>>>>>>> c340bd93e9e032a8109f2b0566eeb37c36be2702
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