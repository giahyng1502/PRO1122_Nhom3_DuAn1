package FPT.PRO1122.Nhom3.DuAn1.Fragment;

import android.media.Image;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.adapter.AdapterBanner;
import FPT.PRO1122.Nhom3.DuAn1.adapter.DoAnBanChayAdapter;

import FPT.PRO1122.Nhom3.DuAn1.adapter.MenuMonAnAdapter;
import FPT.PRO1122.Nhom3.DuAn1.model.DanhMucMonAn;
import FPT.PRO1122.Nhom3.DuAn1.R;

import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityMainBinding;

import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;
import FPT.PRO1122.Nhom3.DuAn1.model.User;

public class Home extends Fragment {
    private ActivityMainBinding binding;
    FirebaseDatabase database;
    FirebaseAuth mAuth;

    ImageView avatar;
    TextView tvNameUserHome;
    private ViewPager2 viewPage2;
    private List<Integer> arrayImg;
    AdapterBanner adapterBanner;
    int index;
    RecyclerView recyclerViewFood, recMenuMonAn;
    DatabaseReference mfood;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Khởi tạo các view
        mfood = FirebaseDatabase.getInstance().getReference("foods");
        viewPage2 = view.findViewById(R.id.viewPage2);
        recyclerViewFood = view.findViewById(R.id.recyclerFood);
        recMenuMonAn = view.findViewById(R.id.recMenuMonAn);
        avatar = view.findViewById(R.id.ivAvatarUserHome);
        tvNameUserHome = view.findViewById(R.id.tvNameUserHome);
        setInforCurrentUser();
        setSlider();
        MonAnBanChayRecyclerview();
        MenuMonAn();

    }

    private void MenuMonAn() {
        DatabaseReference myRef = database.getReference("Category");
//        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<DanhMucMonAn> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(DanhMucMonAn.class));
                    }
                    if (list.size() > 0){
                        recMenuMonAn.setLayoutManager(new GridLayoutManager(getContext(), 4));
                        RecyclerView.Adapter adapter = new MenuMonAnAdapter(list);
                        recMenuMonAn.setAdapter(adapter);
                    }
//                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void MonAnBanChayRecyclerview() {
        DatabaseReference myRef = database.getReference("Foods");
//        binding.progressBarCategories.setVisibility(View.VISIBLE);
        ArrayList<MonAnByThien> list = new ArrayList<>();
        Query query = myRef.orderByChild("BestFood").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(MonAnByThien.class));
                    }
                    if (list.size() > 0){
                        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter adapter = new DoAnBanChayAdapter(list);
                        recyclerViewFood.setAdapter(adapter);
                    }
//                    binding.progressBarCategories.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setInforCurrentUser() {
        FirebaseDatabase.getInstance().getReference("users")
                .child(MainActivity.id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            User user = snapshot.getValue(User.class);
                            Glide.with(getContext()).load(user.getImageAvatar())
                                    .error(R.drawable.none_avatar)
                                    .into(avatar);

                            tvNameUserHome.setText(user.getName() + " \uD83C\uDF3F");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setSlider() {
        arrayImg = new ArrayList<>();
        arrayImg.add(R.drawable.banner1);
        arrayImg.add(R.drawable.banner2);
        arrayImg.add(R.drawable.banner3);
        arrayImg.add(R.drawable.banner4);
        arrayImg.add(R.drawable.banner5);
        adapterBanner = new AdapterBanner(getContext(),arrayImg);

        viewPage2.setAdapter(adapterBanner);

        viewPage2.setClipToPadding(false);
        viewPage2.setClipChildren(false);
        viewPage2.setOffscreenPageLimit(5);
        viewPage2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        // tạo hiệu ứng khi vuốt
        viewPage2.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                Math.abs(position);
                page.setScaleY(0.85f + Math.abs(position)*0.15f); // thu nhỏ theo trục y tạo hiệu ứng trượt

            }
        });

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                next();
                handler.postDelayed(this,3000);
            }
        };
        handler.post(runnable);
    }
    public void next () {
        if (index < arrayImg.size()-1) {
            index ++;
            viewPage2.setCurrentItem(index);
        } else {
            index = -1;
        }
    }
}