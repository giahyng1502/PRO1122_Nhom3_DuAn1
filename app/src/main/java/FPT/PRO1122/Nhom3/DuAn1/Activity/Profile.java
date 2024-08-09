package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.model.User;

public class Profile extends AppCompatActivity {
    LinearLayout payment_layout,order_layout,changepass_layout,delivery_layout,admin_layout;
    Button btn_logout,btn_editprofile;
    ImageView ivbackProfile,ivAvatar;
    TextView tvName,tvMail;
    private GoogleSignInClient signInClient;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getData();
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(this);
        signInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        anhxa();

        //payment
        payment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Payment.class);
                startActivity(intent);
            }
        });

        //order
        order_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, MyOrderHistory.class);
                startActivity(intent);
            }
        });

        //delivery address
        delivery_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, DeliveryAddress.class);
                startActivity(intent);
            }
        });
        if (MainActivity.role == 0) {
            admin_layout.setVisibility(View.VISIBLE);
            admin_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Profile.this, AdminActivity.class);
                    startActivity(intent);
                }
            });
        }


        //changepass
        changepass_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        //edit profile
        btn_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, EditProfile.class);
                startActivity(intent);
            }
        });

        //logout
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        ivbackProfile.setOnClickListener(view -> startActivity(new Intent(Profile.this,MainActivity.class)));
    }
    private void getData(){
        FirebaseDatabase.getInstance().getReference("users").child(MainActivity.id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    tvMail.setText(user.getEmail());
                    tvName.setText(user.getName());
                    Glide.with(Profile.this).load(user.getImageAvatar()).error(R.drawable.none_avatar).into(ivAvatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void anhxa() {
        payment_layout=findViewById(R.id.payment_layout);
        order_layout=findViewById(R.id.orderhistory_layout);
        delivery_layout=findViewById(R.id.delivery_layout);
        changepass_layout=findViewById(R.id.changepass_layout);
        btn_logout=findViewById(R.id.btn_logout);
        btn_editprofile=findViewById(R.id.btn_editprofile);
        ivbackProfile= findViewById(R.id.ivbackProfile);
        tvMail = findViewById(R.id.tvMailProFile);
        tvName = findViewById(R.id.tvNameProFile);
        ivAvatar = findViewById(R.id.ivProfileAvatar);
        admin_layout = findViewById(R.id.admin_layout);
    }

    public void signOut() {
        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() == null) {
                signInClient.signOut().addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Log out successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                });
            }
        });
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

}