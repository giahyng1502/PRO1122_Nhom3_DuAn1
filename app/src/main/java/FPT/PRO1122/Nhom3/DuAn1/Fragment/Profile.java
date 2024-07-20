package FPT.PRO1122.Nhom3.DuAn1.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import FPT.PRO1122.Nhom3.DuAn1.Activity.LoginActivity;
import FPT.PRO1122.Nhom3.DuAn1.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.databinding.FragmentProfileBinding;

public class Profile extends Fragment {
    private FragmentProfileBinding bind;
    private FragmentActivity activity;
    private GoogleSignInClient signInClient;
    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = FragmentProfileBinding.inflate(inflater, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();
        FirebaseApp.initializeApp(activity);
        auth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(activity);
        signInClient = GoogleSignIn.getClient(activity, GoogleSignInOptions.DEFAULT_SIGN_IN);

        // Đăng xuất
        bind.logOutBtn.setOnClickListener(v -> {
            signOut();
        });
    }

    public void signOut() {
        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() == null) {
                signInClient.signOut().addOnSuccessListener(unused -> {
                    Toast.makeText(activity, "Log out successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(activity, LoginActivity.class));
                });
            }
        });
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }
}