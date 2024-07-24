package FPT.PRO1122.Nhom3.DuAn1.Fragment;

<<<<<<< HEAD
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;
=======
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
>>>>>>> c340bd93e9e032a8109f2b0566eeb37c36be2702

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

<<<<<<< HEAD
=======
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
>>>>>>> c340bd93e9e032a8109f2b0566eeb37c36be2702
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
<<<<<<< HEAD
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.AdapterBanner;
import FPT.PRO1122.Nhom3.DuAn1.adapter.DoAnBanChayAdapter;
import FPT.PRO1122.Nhom3.DuAn1.adapter.FoodAdapter;
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
    FoodAdapter foodAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

//        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        getDataFromFirebase();
        // set reclerview
        setReclerFood();
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
    private void setReclerFood() {
        foodAdapter = new FoodAdapter(getContext(),monAnList);
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerViewFood.setLayoutManager(linearLayoutManager);
        recyclerViewFood.setAdapter(foodAdapter);
    }

    private void getDataFromFirebase() {
        mfood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                monAnList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MonAn monAn = dataSnapshot.getValue(MonAn.class);
                    monAnList.add(monAn);
=======
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.AdapterBanner;
import FPT.PRO1122.Nhom3.DuAn1.adapter.FoodAdapter;
import FPT.PRO1122.Nhom3.DuAn1.databinding.FragmentHomeBinding;
import FPT.PRO1122.Nhom3.DuAn1.model.Food;
import FPT.PRO1122.Nhom3.DuAn1.model.User;

@SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
public class Home extends Fragment {
    private FragmentHomeBinding bind;
    private GoogleSignInClient signInClient;
    private FirebaseAuth auth;
    private List<Integer> arrayImg;
    int index;
    DatabaseReference mFood;
    List<Food> foodList;
    FoodAdapter foodAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = FragmentHomeBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FacebookSdk.sdkInitialize(requireActivity());
        FirebaseApp.initializeApp(requireActivity());
        auth = FirebaseAuth.getInstance();
        getUserCurrent();
        if (signInClient == null) {
            signInClient = GoogleSignIn.getClient(requireActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);
        }
        // Đăng nhập facebook
        if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()) {
            getProfileFacebook();
        } else {
            LoginManager.getInstance().logOut();
        }

        // Khởi tạo các view
        mFood = FirebaseDatabase.getInstance().getReference("foods");
        foodList = new ArrayList<>();
        setSlider();
        // set recyclerview
        setRecyclerFood();
        // lay data ve list
        getDataFromFirebase();

    }

    private void setRecyclerFood() {
        foodAdapter = new FoodAdapter(requireActivity(), foodList);
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        bind.recyclerFood.setLayoutManager(linearLayoutManager);
        bind.recyclerFood.setAdapter(foodAdapter);
    }

    private void getDataFromFirebase() {
        mFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food food = dataSnapshot.getValue(Food.class);
                    foodList.add(food);
>>>>>>> c340bd93e9e032a8109f2b0566eeb37c36be2702
                }
                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
<<<<<<< HEAD
                Toast.makeText(getContext(), error+"fdfghh", Toast.LENGTH_SHORT).show();
=======
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
>>>>>>> c340bd93e9e032a8109f2b0566eeb37c36be2702
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
<<<<<<< HEAD
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
=======
        bind.viewPage2.setAdapter(new AdapterBanner(requireActivity(), arrayImg));

        bind.viewPage2.setClipToPadding(false);
        bind.viewPage2.setClipChildren(false);
        bind.viewPage2.setOffscreenPageLimit(5);
        bind.viewPage2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        // tạo hiệu ứng khi vuốt
        bind.viewPage2.setPageTransformer((page, position) -> {
            Math.abs(position);
            page.setScaleY(0.85f + Math.abs(position) * 0.15f); // thu nhỏ theo trục y tạo hiệu ứng trượt
>>>>>>> c340bd93e9e032a8109f2b0566eeb37c36be2702
        });

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                next();
<<<<<<< HEAD
                handler.postDelayed(this,3000);
=======
                handler.postDelayed(this, 3000);
>>>>>>> c340bd93e9e032a8109f2b0566eeb37c36be2702
            }
        };
        handler.post(runnable);
    }
<<<<<<< HEAD
    public void next () {
        if (index < arrayImg.size()-1) {
            index ++;
            viewPage2.setCurrentItem(index);
//            Log.d("giahyng",index+"");
=======

    public void next() {
        if (index < arrayImg.size() - 1) {
            index++;
            bind.viewPage2.setCurrentItem(index);
>>>>>>> c340bd93e9e032a8109f2b0566eeb37c36be2702
        } else {
            index = -1;
        }
    }
<<<<<<< HEAD
=======

    // Hàm lấy thông tin khi đăng nhập bằng tài khoản Facebook
    private void getProfileFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            String name = object.getString("name");
                            String url = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            bind.tvNameUserHome.setText("Hello,\n" + name + " \uD83C\uDF3F");
                            Glide.with(requireActivity()).load(url).into(bind.ivAvatarUserHome);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        // Application code
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }

//    // Hàm lấy thông tin khi đăng nhập bằng tài khoản Google

    // Hàm lấy thông tin khi đăng nhập bằng tài khoản đăng kí
    public void getProfileUser(User user) {
        bind.tvNameUserHome.setText("Hello,\n" +""+ user.getName() + " \uD83C\uDF3F");
        Glide.with(this).load(user.getImageAvatar()).error(R.drawable.avatar).into(bind.ivAvatarUserHome);
    }
    private void getUserCurrent(){
        FirebaseDatabase.getInstance().getReference("users").child(MainActivity.id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    getProfileUser(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

>>>>>>> c340bd93e9e032a8109f2b0566eeb37c36be2702
}

