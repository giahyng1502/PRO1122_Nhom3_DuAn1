package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import FPT.PRO1122.Nhom3.DuAn1.adapter.SpinnerAdapter;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityCreateProfileBinding;
import FPT.PRO1122.Nhom3.DuAn1.model.Profile;

public class CreateProfileActivity extends AppCompatActivity {
    private ActivityCreateProfileBinding bind;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCreateProfileBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.btnBack.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        SpinnerAdapter adapter = new SpinnerAdapter(this, getItems());
        bind.spnGender.setAdapter(adapter);

        bind.createBtn.setOnClickListener(v -> {
            if (!validateFirstName() || !validateLastName() ||
                    !validateEmailAddress() || !validateAddress()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            createProfile();
        });

    }

    private List<String[]> getItems() {
        List<String[]> items = new ArrayList<>();
        items.add(new String[]{"Gender"});
        items.add(new String[]{"Male"});
        items.add(new String[]{"Female"});
        return items;
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
        String firstName = Objects.requireNonNull(bind.edtFirstName.getText()).toString();
        String lastName = Objects.requireNonNull(bind.edtLastName.getText()).toString();
        String emailAddress = Objects.requireNonNull(bind.edtEmailAddress.getText()).toString();
        String address = Objects.requireNonNull(bind.edtAddress.getText()).toString();
        String gender = getItems().get(bind.spnGender.getSelectedItemPosition())[0];
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        profile = new Profile(firstName, lastName, emailAddress, address, gender);
        assert phoneNumber != null;
        reference.child(phoneNumber).child("profile").setValue(profile);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
    }
}