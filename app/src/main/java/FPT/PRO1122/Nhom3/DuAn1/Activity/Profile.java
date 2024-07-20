package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import FPT.PRO1122.Nhom3.DuAn1.R;

public class Profile extends AppCompatActivity {
    LinearLayout payment_layout,order_layout,changepass_layout,delivery_layout;
    Button btn_logout,btn_editprofile;
    ImageView ivbackProfile;
    private GoogleSignInClient signInClient;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
                Intent intent = new Intent(Profile.this, OrderHistory.class);
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

    private void anhxa() {
        payment_layout=findViewById(R.id.payment_layout);
        order_layout=findViewById(R.id.orderhistory_layout);
        delivery_layout=findViewById(R.id.delivery_layout);
        changepass_layout=findViewById(R.id.changepass_layout);
        btn_logout=findViewById(R.id.btn_logout);
        btn_editprofile=findViewById(R.id.btn_editprofile);
        ivbackProfile= findViewById(R.id.ivbackProfile);
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