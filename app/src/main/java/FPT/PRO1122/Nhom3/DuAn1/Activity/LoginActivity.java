package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import FPT.PRO1122.Nhom3.DuAn1.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding bind;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth auth;
    private CallbackManager mCallbackManager;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    auth.signInWithCredential(credential).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            auth = FirebaseAuth.getInstance();
                            Toast.makeText(LoginActivity.this, "Signed in successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed to sign in: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token != null && !token.isExpired()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        FirebaseApp.initializeApp(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        auth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        signInFacebook();

        bind.btnFB.setOnClickListener(v -> {
            if (v.getId() == R.id.btnFB) {
                bind.facebookBtn.performClick();
            }
        });

        bind.googleBtn.setOnClickListener(v -> {
            // Đăng nhập bằng Google
            Intent intent = googleSignInClient.getSignInIntent();
            activityResultLauncher.launch(intent);
        });

        bind.signInBtn.setOnClickListener(v -> {
            // Kiểm tra xem các trường nhập liệu có trống không
            if (!validatePhoneNumber() || !validatePassword()) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Nếu các trường nhập liệu không trống, kiểm tra người dùng có tồn tại trong Firebase hay không
                checkUser();
            }
        });

        bind.tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // Hàm này dùng để kiểm tra xem phone number có trống không
    private Boolean validatePhoneNumber() {
        String val = Objects.requireNonNull(bind.edtPhoneNumber.getText()).toString();
        if (val.isEmpty()) {
            bind.edtPhoneNumber.setError("Phone number is required");
            return false;
        } else {
            bind.edtPhoneNumber.setError(null);
            return true;
        }
    }

    // Hàm này dùng để kiểm tra xem mật khẩu có trống không
    private Boolean validatePassword() {
        String val = Objects.requireNonNull(bind.edtPassword.getText()).toString();
        if (val.isEmpty()) {
            bind.edtPassword.setError("Password is required");
            return false;
        } else {
            bind.edtPassword.setError(null);
            return true;
        }
    }

//  Hàm này dùng để kiểm tra xem người dùng có tồn tại trong Firebase hay không
    private void checkUser() {
        String phoneNumber = Objects.requireNonNull(bind.edtPhoneNumber.getText()).toString().trim();
        String password = Objects.requireNonNull(bind.edtPassword.getText()).toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        // Kiểm tra xem người dùng có tồn tại trong Firebase hay không
        Query checkUserDB = reference.orderByChild("phoneNumber").equalTo(phoneNumber);

        checkUserDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Nếu người dùng tồn tại, kiểm tra mật khẩu
                if (snapshot.exists()) {
                    bind.edtPhoneNumber.setError(null);
                    String passwordDB = snapshot.child(phoneNumber).child("password").getValue(String.class);

                    assert passwordDB != null;
                    // Nếu mật khẩu chính xác, chuyển sang màn hình chính
                    if (passwordDB.equals(password)) {
                        bind.edtPhoneNumber.setError(null);
                        String firstNameFromDB = snapshot.child(phoneNumber).child("firstName").getValue(String.class);
                        String lastNameFromDB = snapshot.child(phoneNumber).child("lastName").getValue(String.class);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("firstName", firstNameFromDB);
                        intent.putExtra("lastName", lastNameFromDB);
                        startActivity(intent);
                    } else {
                        // Nếu mật khẩu không chính xác, thông báo và focus vào mật khẩu
                        bind.edtPassword.setError("Invalid Credentials");
                        bind.edtPassword.requestFocus();
                    }
                } else {
                    // Nếu người dùng không tồn tại, thông báo và focus vào người dùng
                    bind.edtPhoneNumber.setError("User does not exist");
                    bind.edtPhoneNumber.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý trường hợp có lỗi xảy ra
                Toast.makeText(LoginActivity.this, "Something went wrong" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInFacebook() {
        bind.facebookBtn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login Facebook canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull FacebookException error) {
                Toast.makeText(LoginActivity.this, "Login Facebook failed" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}