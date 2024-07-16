package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityRegisterBinding;
import FPT.PRO1122.Nhom3.DuAn1.model.User;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding bind;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.signUpBtn.setOnClickListener(v -> {
            reference = FirebaseDatabase.getInstance().getReference("users");
            String phoneNumber = Objects.requireNonNull(bind.edtPhonenumber.getText()).toString();
            String username = Objects.requireNonNull(bind.edtUsername.getText()).toString();
            String password = Objects.requireNonNull(bind.edtPassword.getText()).toString();

            User user = new User(phoneNumber, username, password);
            reference.child(phoneNumber).setValue(user);

            if (!validateEmail() || !validatePassword() || !validatePhoneNumber()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        });

        bind.tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private Boolean validatePhoneNumber() {
        String val = Objects.requireNonNull(bind.edtPhonenumber.getText()).toString();
        if (val.isEmpty()) {
            bind.edtPhonenumber.setError("Name is required");
            return false;
        } else {
            bind.edtPhonenumber.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = Objects.requireNonNull(bind.edtUsername.getText()).toString();
        if (val.isEmpty()) {
            bind.edtUsername.setError("Email is required");
            return false;
        } else {
            bind.edtUsername.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = Objects.requireNonNull(bind.edtPassword.getText()).toString();
        String valConfirm = Objects.requireNonNull(bind.edtConfirmPassword.getText()).toString();
        if (val.isEmpty() || valConfirm.isEmpty()) {
            bind.edtPassword.setError("Password is required");
            bind.edtConfirmPassword.setError("Password is required");
            return false;
        } else {
            bind.edtPassword.setError(null);
            bind.edtConfirmPassword.setError(null);
            return true;
        }
    }
}