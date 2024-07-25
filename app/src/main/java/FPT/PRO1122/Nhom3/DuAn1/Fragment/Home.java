package FPT.PRO1122.Nhom3.DuAn1.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import FPT.PRO1122.Nhom3.DuAn1.adapter.FoodAdapter;
import FPT.PRO1122.Nhom3.DuAn1.adapter.MenuMonAnAdapter;
import FPT.PRO1122.Nhom3.DuAn1.Model.DanhMucMonAn;
import FPT.PRO1122.Nhom3.DuAn1.R;




import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityMainBinding;
import FPT.PRO1122.Nhom3.DuAn1.Model.MonAn;
import FPT.PRO1122.Nhom3.DuAn1.Model.MonAnByThien;

public class Home extends Fragment {
    private ActivityMainBinding binding;
    FirebaseDatabase database;
    FirebaseAuth mAuth;

    private ViewPager2 viewPage2;
    private List<Integer> arrayImg;
    AdapterBanner adapterBanner;
    int index;
    RecyclerView recyclerViewFood, recMenuMonAn;
    DatabaseReference mfood;
    List<MonAn> monAnList;
//    FoodAdapter foodAdapter;
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
        monAnList = new ArrayList<>();
        setSlider();
        // lay data ve list
//        getDataFromFirebase();
        // set reclerview
//        setReclerFood();
        MonAnBanChayRecyclerview();
        MenuMonAn();

//        MonAn monAn = new MonAn("4","Thit Bo Nhap Tu Nhan Ban"
//                ,"Bánh mì Sài Gòn chỉ đơn giản là bánh mì nóng giòn vẫn thường được dùng để kẹp thịt nguội, chả lụa, pate hoặc dùng kèm với các món mặn như bò kho, ragu..."
//        ,"120000","https://firebasestorage.googleapis.com/v0/b/duan1-2e5d9.appspot.com/o/th%E1%BB%8Bt%20b%C3%B2.png?alt=media&token=4c367d17-0462-4833-bf15-9a72154697ef");
//        mfood.child("4").setValue(monAn);
//        MonAn monAn2 = new MonAn("5","Suon Xao Chua Ngot","Bánh mì Sài Gòn chỉ đơn giản là bánh mì nóng giòn vẫn thường được dùng để kẹp thịt nguội, chả lụa, pate hoặc dùng kèm với các món mặn như bò kho, ragu..."
//        ,"170000","https://firebasestorage.googleapis.com/v0/b/duan1-2e5d9.appspot.com/o/x%C6%B0%C6%A1ng.png?alt=media&token=7af3f311-eee3-445e-a363-4fcab1b93483");
//        mfood.child("5").setValue(monAn2);
//       MonAn monAn3 = new MonAn("6","Hamburger Beef","Bánh mì Sài Gòn chỉ đơn giản là bánh mì nóng giòn vẫn thường được dùng để kẹp thịt nguội, chả lụa, pate hoặc dùng kèm với các món mặn như bò kho, ragu..."
//        ,"450000","https://firebasestorage.googleapis.com/v0/b/duan1-2e5d9.appspot.com/o/hamberge.png?alt=media&token=48f08f02-833b-494e-81b1-a67a7b153c0d");
//        mfood.child("6").setValue(monAn3);

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
//    private void setReclerFood() {
//        foodAdapter = new FoodAdapter(getContext(),monAnList);
//        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        recyclerViewFood.setLayoutManager(linearLayoutManager);
//        recyclerViewFood.setAdapter(foodAdapter);
//    }

//    private void getDataFromFirebase() {
//        mfood.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                monAnList.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    MonAn monAn = dataSnapshot.getValue(MonAn.class);
//                    monAnList.add(monAn);
//                }
//                foodAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), error+"fdfghh", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

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
//            Log.d("giahyng",index+"");
        } else {
            index = -1;
        }
    }
}

