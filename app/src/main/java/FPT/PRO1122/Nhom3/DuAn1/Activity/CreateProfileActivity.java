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

import FPT.PRO1122.Nhom3.DuAn1.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityCreateProfileBinding;
import FPT.PRO1122.Nhom3.DuAn1.model.User;

public class CreateProfileActivity extends AppCompatActivity {
    private ActivityCreateProfileBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCreateProfileBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.btnBack.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        bind.createBtn.setOnClickListener(v -> {
            if (!validateFirstName() || !validateLastName() ||
                    !validateEmailAddress() || !validateAddress()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            createProfile();
        });

    }

    // Hàm kiểm tra xem số first name có trống không
    private Boolean validateFirstName() {
        String val = Objects.requireNonNull(bind.edtFirstName.getText()).toString();
        if (val.isEmpty()) {
            bind.edtFirstName.setError("First name is required");
            return false;
        } else {
            bind.edtFirstName.setError(null);
            return true;
        }
    }

    // Hàm kiểm tra xem số last name có trống không
    private Boolean validateLastName() {
        String val = Objects.requireNonNull(bind.edtLastName.getText()).toString();
        if (val.isEmpty()) {
            bind.edtLastName.setError("Last name is required");
            return false;
        } else {
            bind.edtLastName.setError(null);
            return true;
        }
    }

    // Hàm kiểm tra xem email address có trống không
    private Boolean validateEmailAddress() {
        String val = Objects.requireNonNull(bind.edtEmailAddress.getText()).toString();
        if (val.isEmpty()) {
            bind.edtEmailAddress.setError("Email address is required");
            return false;
        } else {
            bind.edtEmailAddress.setError(null);
            return true;
        }
    }

    // Hàm kiểm tra xem address có trống không
    private Boolean validateAddress() {
        String val = Objects.requireNonNull(bind.edtAddress.getText()).toString();
        if (val.isEmpty()) {
            bind.edtAddress.setError("Address is required");
            return false;
        } else {
            bind.edtAddress.setError(null);
            return true;
        }
    }

    private void createProfile() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String password = getIntent().getStringExtra("password");
        String firstName = Objects.requireNonNull(bind.edtFirstName.getText()).toString();
        String lastName = Objects.requireNonNull(bind.edtLastName.getText()).toString();
        String emailAddress = Objects.requireNonNull(bind.edtEmailAddress.getText()).toString();
        String address = Objects.requireNonNull(bind.edtAddress.getText()).toString();
        User user = new User(phoneNumber, password, firstName, lastName, emailAddress, address);
        assert phoneNumber != null;
        reference.child(phoneNumber).setValue(user);
        Query phoneNumberFromDB = reference.orderByChild("phoneNumber").equalTo(phoneNumber);
        phoneNumberFromDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstNameFromDB = snapshot.child(phoneNumber).child("firstName").getValue(String.class);
                String lastNameFromDB = snapshot.child(phoneNumber).child("lastName").getValue(String.class);
                Intent intent = new Intent(CreateProfileActivity.this, MainActivity.class);
                intent.putExtra("firstName", firstNameFromDB);
                intent.putExtra("lastName", lastNameFromDB);
                startActivity(intent);
                Toast.makeText(CreateProfileActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}