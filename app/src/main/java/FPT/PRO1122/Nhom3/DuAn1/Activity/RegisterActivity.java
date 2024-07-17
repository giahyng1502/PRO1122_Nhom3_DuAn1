package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
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

        bind.signUpBtn.setOnClickListener(v -> {
            // Kiểm tra xem các trường nhập liệu có trống không
            if (!validateUsername() || !validatePassword() || !validatePhoneNumber()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Kiểm tra xem checkbox đã được chọn chưa
                boolean isAgree = bind.ckbAgree.isChecked();
                if (isAgree) {
                    // Nếu checkbox được chọn, kiểm tra xem số điện thoại đã tồn tại trong Firebase và thêm tài khoản vào Firebase
                    checkIfPhoneNumberExists();
                } else {
                    // Nếu checkbox chưa được chọn, thông báo và focus vào checkbox
                    Toast.makeText(RegisterActivity.this, "Please agree to all terms and conditions", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bind.tvSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    // Hàm kiểm tra xem số điện thoại có trống không
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

    // Hàm kiểm tra xem username có trống không
    private Boolean validateUsername() {
        String val = Objects.requireNonNull(bind.edtUsername.getText()).toString();
        if (val.isEmpty()) {
            bind.edtUsername.setError("Email is required");
            return false;
        } else {
            bind.edtUsername.setError(null);
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

    //     Hàm kiểm tra xem số điện thoại đã tồn tại trong Firebase chưa
    private void checkIfPhoneNumberExists() {
        reference = FirebaseDatabase.getInstance().getReference("users");
        String phoneNumber = Objects.requireNonNull(bind.edtPhonenumber.getText()).toString();
        String username = Objects.requireNonNull(bind.edtUsername.getText()).toString();
        String password = Objects.requireNonNull(bind.edtPassword.getText()).toString();

        // Kiểm tra xem số điện thoại đã tồn tại trong Firebase chưa
        Query checkPhoneNumberDB = reference.orderByChild("phoneNumber").equalTo(phoneNumber);

        checkPhoneNumberDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Nếu số điện thoại đã tồn tại, thông báo và focus vào số điện thoại
                if (snapshot.exists()) {
                    String phoneNumberDB = snapshot.child(username).child("phoneNumber").getValue(String.class);
                    assert phoneNumberDB != null;
                    if (phoneNumberDB.equals(phoneNumber)) {
                        bind.edtPhonenumber.setError("This phone number already exists");
                        bind.edtPhonenumber.requestFocus();
                    }
                } else {
                    // Nếu số điện thoại mới, đăng ký người dùng vào Firebase
                    User user = new User(phoneNumber, username, password);
                    reference.child(username).setValue(user);
                    Toast.makeText(RegisterActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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