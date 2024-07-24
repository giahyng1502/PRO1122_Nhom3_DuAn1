package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import FPT.PRO1122.Nhom3.DuAn1.R;

public class ChangePassword extends AppCompatActivity {
    ImageView btn_back;
    Button btnChangePass;
    EditText edtNewPass, edtReNewPass, edtOldPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        anhXa();

        //1
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePassword.this, Profile.class));
            }
        });
        btnChangePass.setOnClickListener(e -> {
            changePassword();
        });


    }

    private void anhXa() {
        btn_back = findViewById(R.id.btn_back_changepass);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtReNewPass = findViewById(R.id.edtReNewPass);
        edtOldPass = findViewById(R.id.edtOldPass);
        btnChangePass = findViewById(R.id.btnChangePass);
    }

    private void changePassword() {
        String userId = MainActivity.id;
        if (userId == null || userId.isEmpty()) {
            // Handle the error when userId is null or empty
            Log.e("ProfileActivity", "User ID is null or empty");
            return;
        }

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String pass = snapshot.child("password").getValue(String.class);
                    if (pass != null && pass.equalsIgnoreCase(edtOldPass.getText().toString())) {
                        String newPass = edtNewPass.getText().toString();
                        String reNewPass = edtReNewPass.getText().toString();
                        if (checkPass(newPass, reNewPass)) {
                            userRef.child("password").setValue(newPass)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            startActivity(new Intent(ChangePassword.this, Profile.class));
                                            // Notify that the password has been changed successfully
                                            Toast.makeText(ChangePassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle error when password update fails
                                            Log.e("ProfileActivity", "Error updating password", e);
                                            Toast.makeText(ChangePassword.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // New password and confirm password do not match
                            Toast.makeText(ChangePassword.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Old password is incorrect
                        Toast.makeText(ChangePassword.this, "Old password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // User does not exist
                    Toast.makeText(ChangePassword.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error when Firebase query is cancelled
                Log.e("ProfileActivity", "Error querying user", error.toException());
            }
        });
    }

    private boolean checkPass(String newPass, String reNewPass) {
        boolean isValid = true;
        if (newPass.isEmpty()) {
            Toast.makeText(this, "NewPass Empty", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if (reNewPass.isEmpty()) {
            Toast.makeText(this, "RePass Empty", Toast.LENGTH_SHORT).show();

            isValid = false;
        }
        if (!newPass.equals(reNewPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

}