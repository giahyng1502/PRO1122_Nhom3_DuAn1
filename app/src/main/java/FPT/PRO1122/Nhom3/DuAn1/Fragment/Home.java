package FPT.PRO1122.Nhom3.DuAn1.Fragment;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import FPT.PRO1122.Nhom3.DuAn1.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.AdapterBanner;
import FPT.PRO1122.Nhom3.DuAn1.adapter.FoodAdapter;
import FPT.PRO1122.Nhom3.DuAn1.databinding.FragmentHomeBinding;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAn;

@SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
public class Home extends Fragment {
    private FragmentHomeBinding bind;
    private GoogleSignInClient signInClient;
    private FirebaseAuth auth;
    private List<Integer> arrayImg;
    int index;
    DatabaseReference mFood;
    List<MonAn> monAnList;
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
        if (signInClient == null) {
            signInClient = GoogleSignIn.getClient(requireActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);
        }
        // Đăng nhập facebook
        if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()) {
            getProfileFacebook();
        } else {
            LoginManager.getInstance().logOut();
        }
        getProfileUser();
        getProfileGoogle();

        // Khởi tạo các view
        mFood = FirebaseDatabase.getInstance().getReference("foods");
        monAnList = new ArrayList<>();
        setSlider();
        // set recyclerview
        setRecyclerFood();
        // lay data ve list
        getDataFromFirebase();
    }

    private void setRecyclerFood() {
        foodAdapter = new FoodAdapter(requireActivity(), monAnList);
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        bind.recyclerFood.setLayoutManager(linearLayoutManager);
        bind.recyclerFood.setAdapter(foodAdapter);
    }

    private void getDataFromFirebase() {
        mFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                monAnList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MonAn monAn = dataSnapshot.getValue(MonAn.class);
                    monAnList.add(monAn);
                }
                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
        bind.viewPage2.setAdapter(new AdapterBanner(requireActivity(), arrayImg));

        bind.viewPage2.setClipToPadding(false);
        bind.viewPage2.setClipChildren(false);
        bind.viewPage2.setOffscreenPageLimit(5);
        bind.viewPage2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        // tạo hiệu ứng khi vuốt
        bind.viewPage2.setPageTransformer((page, position) -> {
            Math.abs(position);
            page.setScaleY(0.85f + Math.abs(position) * 0.15f); // thu nhỏ theo trục y tạo hiệu ứng trượt
        });

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                next();
                handler.postDelayed(this, 3000);
            }
        };
        handler.post(runnable);
    }

    public void next() {
        if (index < arrayImg.size() - 1) {
            index++;
            bind.viewPage2.setCurrentItem(index);
        } else {
            index = -1;
        }
    }

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

    // Hàm lấy thông tin khi đăng nhập bằng tài khoản Google
    private void getProfileGoogle() {
        if (auth.getCurrentUser() != null) {
            Glide.with(requireActivity()).load(Objects.requireNonNull(auth.getCurrentUser()).getPhotoUrl()).into(bind.ivAvatarUserHome);
            bind.tvNameUserHome.setText("Hello,\n" + auth.getCurrentUser().getDisplayName() + " \uD83C\uDF3F");
        }
    }

    // Hàm lấy thông tin khi đăng nhập bằng tài khoản đăng kí
    public void getProfileUser() {
        Intent intent = requireActivity().getIntent();
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        bind.tvNameUserHome.setText("Hello,\n" + firstName + " " + lastName + " \uD83C\uDF3F");
        Glide.with(this).load(R.drawable.avatar).into(bind.ivAvatarUserHome);
    }
}

