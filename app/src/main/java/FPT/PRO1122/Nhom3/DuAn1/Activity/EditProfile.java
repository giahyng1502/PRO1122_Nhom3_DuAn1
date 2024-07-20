package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityEditProfileBinding;
import FPT.PRO1122.Nhom3.DuAn1.model.User;

public class EditProfile extends AppCompatActivity {
    ImageView btn_back;
    ActivityEditProfileBinding binding;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot() );

        //
        binding.btnBackEditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData();
        binding.btnComfirm.setOnClickListener(v-> {
            String name = binding.edtFullName.getText().toString();
            String mail = binding.edtMail.getText().toString();
            String phone = binding.edtPhoneNumber.getText().toString();
            String homtown = binding.edtHomeTown.getText().toString();
            String pass = user.getPassword();
            // can fix avatar
            String avatar = user.getImageAvatar();
            User user1 = new User(MainActivity.id,phone,pass,name,mail,homtown,avatar);

            FirebaseDatabase.getInstance().getReference("users").child(MainActivity.id).setValue(user1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(EditProfile.this, "Update succesfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfile.this, "Update Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        binding.btnCencel.setOnClickListener(v-> {
            finish();
        });
    }
    private void getData(){
        FirebaseDatabase.getInstance().getReference("users").child(MainActivity.id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    user = snapshot.getValue(User.class);
                    binding.edtFullName.setText(user.getName());
                    binding.edtMail.setText(user.getEmail());
                    Glide.with(EditProfile.this).load(user.getImageAvatar()).error(R.drawable.ic_username).into(binding.ivCurrentUsr);
                    binding.edtPhoneNumber.setText(user.getPhoneNumber());
                    binding.edtHomeTown.setText(user.getAddress());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}