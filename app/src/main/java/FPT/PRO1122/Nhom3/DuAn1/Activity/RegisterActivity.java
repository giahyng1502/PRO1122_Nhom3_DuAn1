package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

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

        bind.continueBtn.setOnClickListener(v -> {
//             Kiểm tra xem các trường nhập liệu có trống không
            if (!validatePhoneNumber() || !validatePassword()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!validateConfirmPassword()) {
                // Nếu mật khẩu và mật khẩu xác nhận không khớp, thông báo và focus vào mật khẩu xác nhận
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
                return;
            }
            checkIfPhoneNumberExists();
        });

        bind.tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    // Hàm kiểm tra xem số điện thoại có trống không
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

    // Hàm kiểm tra xem mật khẩu và mật khẩu xác nhận có trống không
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

    // Hàm kiểm tra xem mật khẩu và mật khẩu xác nhận có trùng nhau không
    private Boolean validateConfirmPassword() {
        String val = Objects.requireNonNull(bind.edtPassword.getText()).toString();
        String valConfirm = Objects.requireNonNull(bind.edtConfirmPassword.getText()).toString();

        // Kiểm tra xem mật khẩu và mật khẩu xác nhận có trùng nhau không
        if (!val.equals(valConfirm)) {
            bind.edtConfirmPassword.setError("Password does not match");
            return false;
        } else {
            bind.edtConfirmPassword.setError(null);
            return true;
        }
    }

    // Hàm kiểm tra xem số điện thoại đã tồn tại trong Firebase chưa
    private void checkIfPhoneNumberExists() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        String phoneNumber = Objects.requireNonNull(bind.edtPhoneNumber.getText()).toString();
        String password = Objects.requireNonNull(bind.edtPassword.getText()).toString();

        // Kiểm tra xem số điện thoại đã tồn tại trong Firebase chưa
        Query checkPhoneNumberDB = reference.orderByChild("phoneNumber").equalTo(phoneNumber);

        checkPhoneNumberDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Nếu số điện thoại đã tồn tại, thông báo và focus vào số điện thoại
                if (snapshot.exists()) {
                    String phoneNumberDB = snapshot.child(phoneNumber).child("phoneNumber").getValue(String.class);
                    assert phoneNumberDB != null;
                    if (phoneNumberDB.equals(phoneNumber)) {
                        bind.edtPhoneNumber.setError("This phone number already exists");
                        bind.edtPhoneNumber.requestFocus();
                    }
                } else {
                    Intent intent = new Intent(RegisterActivity.this, CreateProfileActivity.class);
                    intent.putExtra("phoneNumber", phoneNumber);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý trường hợp có lỗi xảy ra
                Toast.makeText(RegisterActivity.this, "Something went wrong" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}