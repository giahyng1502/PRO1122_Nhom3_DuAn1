package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.MyOrderPagerAdapter;

public class MyOrderHistory extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        MyOrderPagerAdapter adapter = new MyOrderPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Chờ Xác Nhận");
                            break;
                        case 1:
                            tab.setText("Đang Giao");
                            break;
                        case 2:
                            tab.setText("Hoàn Thành");
                            break;
                    }
                }).attach();
    }
}
