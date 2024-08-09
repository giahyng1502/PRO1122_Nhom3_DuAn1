package FPT.PRO1122.Nhom3.DuAn1.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Admin.FoodManagement;
import FPT.PRO1122.Nhom3.DuAn1.adapter.AdapterBanner;
import FPT.PRO1122.Nhom3.DuAn1.adapter.DoAnBanChayAdapter;

import FPT.PRO1122.Nhom3.DuAn1.adapter.MenuMonAnAdapter;
import FPT.PRO1122.Nhom3.DuAn1.R;

import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityMainBinding;

import FPT.PRO1122.Nhom3.DuAn1.model.Catagory;
import FPT.PRO1122.Nhom3.DuAn1.model.Foods;
import FPT.PRO1122.Nhom3.DuAn1.model.User;

public class Home extends Fragment {
    private ActivityMainBinding binding;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    List<String> banners;
    ImageView avatar;
    TextView tvNameUserHome;
    private ViewPager2 viewPage2;
    private List<Integer> arrayImg;
    AdapterBanner adapterBanner;
    int index = -1;
    RecyclerView recyclerViewFood, recMenuMonAn;
    DatabaseReference mfood;
    ImageView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }
    // ababc

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Khởi tạo các view
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mfood = FirebaseDatabase.getInstance().getReference("foods");
        viewPage2 = view.findViewById(R.id.viewPage2);
        recyclerViewFood = view.findViewById(R.id.recyclerFood);
        recMenuMonAn = view.findViewById(R.id.recMenuMonAn);
        avatar = view.findViewById(R.id.ivAvatarUserHome);
        tvNameUserHome = view.findViewById(R.id.tvNameUserHome);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnClickListener(e -> goToTargetFragment());
        getBanner();
        setInforCurrentUser();
        MonAnBanChayRecyclerview();
        MenuMonAn();
    }
    private void goToTargetFragment() {
        // Tạo instance của Fragment đích
        FoodManagement targetFragment = new FoodManagement();

        // Sử dụng FragmentManager và FragmentTransaction để chuyển đổi
        FragmentManager fragmentManager = getParentFragmentManager(); // Hoặc getActivity().getSupportFragmentManager() nếu dùng trong Activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Thay thế Fragment hiện tại bằng Fragment đích
        fragmentTransaction.replace(R.id.frameLayout, targetFragment);

        // Thêm vào back stack để có thể quay lại Fragment trước đó
        fragmentTransaction.addToBackStack(null);

        // Hoàn thành giao dịch
        fragmentTransaction.commit();
    }

    private void getBanner() {
        banners = new ArrayList<>();
        database.getReference("Banner").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    banners.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        banners.add(snapshot1.getValue(String.class));

                    }
                    if (!banners.isEmpty()) {
                        setSlider();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "error"+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void MenuMonAn() {
        DatabaseReference myRef = database.getReference("Category");
//        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Catagory> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue : snapshot.getChildren()){
                        Catagory catagory = issue.getValue(Catagory.class);
                        list.add(catagory);

                    }
                    if (!list.isEmpty()){
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
        ArrayList<Foods> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(Foods.class));
                    }
                    if (list.size() > 0){
                        //sap xep
                        Collections.sort(list, (item1, item2) -> Double.compare(item2.getStar(), item1.getStar()));
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),1,LinearLayoutManager.HORIZONTAL,false);
                        recyclerViewFood.setLayoutManager(gridLayoutManager);
                        // top 5
                        List<Foods> top5Items = list.subList(0, Math.min(5, list.size()));
                        RecyclerView.Adapter adapter = new DoAnBanChayAdapter(top5Items);
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
                .child(MainActivity.id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            try {
                                User user = snapshot.getValue(User.class);
                                Glide.with(requireContext()).load(user.getImageAvatar())
                                        .error(R.drawable.none_avatar)
                                        .into(avatar);

                                tvNameUserHome.setText(user.getName() + " \uD83C\uDF3F");
                            }catch (Exception e) {
                                Log.d("home 168",e+"");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setSlider() {

        adapterBanner = new AdapterBanner(getContext(),banners);

        viewPage2.setAdapter(adapterBanner);

        viewPage2.setClipToPadding(false);
        viewPage2.setClipChildren(false);
        viewPage2.setOffscreenPageLimit(3);
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
                handler.postDelayed(this,5000);
            }
        };
        handler.post(runnable);
    }
    public void next () {
        if (index < 3) {
            index ++;
            viewPage2.setCurrentItem(index);
        } else {
            index = -1;
        }
    }

}