package FPT.PRO1122.Nhom3.DuAn1.adapter;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.Activity.EditProfile;
import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.Activity.Profile;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.databinding.DialogUpdateUserBinding;
import FPT.PRO1122.Nhom3.DuAn1.model.User;

public class AdapterUserManager extends RecyclerView.Adapter<AdapterUserManager.ViewHolder> {
    Context context;
    ArrayList<User> list;


    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri userImageUri;
    private DialogUpdateUserBinding binding;
    private Dialog dialog;

    public AdapterUserManager(Context context, ArrayList<User> list, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.context = context;
        this.list = list;
        this.activityResultLauncher = activityResultLauncher;
    }
    public void listFillter(ArrayList<User>list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_admin, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = list.get(position);
        String name;
        holder.tvUserManagementName.setText(user.getName());
        holder.tvUserManagementPhone.setText(user.getPhoneNumber());
        holder.tvUserManagementPassWord.setText(user.getPassword());
        holder.tvUserManagementAddress.setText(user.getAddress());
        holder.tvUserManagementEmail.setText(user.getEmail());
        holder.tvAcount.setText(user.getUserId());

        if (user.getRole() == 0) {
            name = user.getName()+" \uD83D\uDD11";
            holder.tvUserManagementName.setText(name);
        } else {
            name = user.getName();
            holder.tvUserManagementName.setText(name);
        }

        Glide.with(context).load(user.getImageAvatar())
                .error(R.drawable.none_avatar)
                .into(holder.ivAvatarUserManagement);
        holder.itemView.setOnLongClickListener(v -> {
            updateUserManagement(user);
            return true;
        });
    }

    private void updateUserManagement(User user) {
        dialog = new Dialog(context);
        binding = DialogUpdateUserBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());
        binding.tvAccoutID.setText("ID : "+""+user.getUserId()+"");
        binding.edtFullNameUserManager.setText(user.getName());
        binding.edtEmailUserManagement.setText(user.getEmail());
        binding.edtAddressUserManagement.setText(user.getAddress());
        binding.edtPasswordUserManagement.setText(user.getPassword());
        binding.edtPhoneNumberUserManagement.setText(user.getPhoneNumber());
        if (user.getRole() == 1) {
            binding.rdoUser.setChecked(true);
        } else {
            binding.rdoAdmin.setChecked(true);
        }

        if (userImageUri == null) {
            Glide.with(context).load(user.getImageAvatar()).error(R.drawable.none_avatar).into(binding.ivUser);
        }

        binding.ivCameraIcon.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            activityResultLauncher.launch(intent);
        });

        binding.btnCancelUserManagement.setOnClickListener(v -> dialog.dismiss());
        binding.btnSubMitUserManagement.setOnClickListener(v -> {
            if (binding.edtPasswordUserManagement.getText().toString().isEmpty())
                binding.edtPasswordUserManagement.setError("Not be empty");
            else
                putAvatarUser(userImageUri, user);
        });
        dialog.show();
        // set Size according to phone
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);
    }

    private void putAvatarUser(Uri userImageUri, User user) {
        if (userImageUri == null) {
            updateProfile(user.getImageAvatar(), user);
        } else {
            FirebaseStorage.getInstance().getReference("Image User")
                    .child(user.getName() + user.getUserId()).putFile(userImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            FirebaseStorage.getInstance().getReference("Image User")
                                    .child(user.getName() + user.getUserId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageLink = uri.toString();
                                            updateProfile(imageLink, user);
                                        }
                                    }).addOnFailureListener(erro -> Toast.makeText(context, "UpLoad User Fail", Toast.LENGTH_SHORT).show());
                        }
                    }).addOnFailureListener(erro -> Toast.makeText(context, "UpLoad Image Fail", Toast.LENGTH_SHORT).show());
        }
    }
    private void updateProfile(String imageLink, User user) {
        String name = binding.edtFullNameUserManager.getText().toString();
        String mail = binding.edtEmailUserManagement.getText().toString();
        String phone = binding.edtPhoneNumberUserManagement.getText().toString();
        String homtown = binding.edtAddressUserManagement.getText().toString();
        String pass = binding.edtPasswordUserManagement.getText().toString();
        int role = binding.rdoUser.isChecked() ? 1 : 0;
        String avatar = imageLink;
        User user1 = new User(user.getUserId(), phone, pass, name, mail, homtown, avatar);
        user1.setRole(role);

        // Lưu thông tin cập nhật vào Firebase Realtime Database
        FirebaseDatabase.getInstance().getReference("users")
                .child(user1.getUserId())
                .setValue(user1)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(context, "Update successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }).addOnFailureListener(e -> Toast.makeText(context, "Update Fail", Toast.LENGTH_SHORT).show());

    }

    public void setImageUri(Uri imageUri) {
        this.userImageUri = imageUri;
        if (userImageUri!= null) {
            binding.ivUser.setImageURI(userImageUri);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserManagementName, tvUserManagementEmail,
                tvUserManagementPhone, tvUserManagementPassWord, tvUserManagementAddress,tvAcount;
        ImageView ivAvatarUserManagement;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserManagementName = itemView.findViewById(R.id.tvUserManagementName);
            tvUserManagementEmail = itemView.findViewById(R.id.tvUserManagementEmail);
            tvUserManagementPhone = itemView.findViewById(R.id.tvUserManagementPhone);
            tvUserManagementPassWord = itemView.findViewById(R.id.tvUserManagementPassWord);
            tvUserManagementAddress = itemView.findViewById(R.id.tvUserManagementAddress);
            ivAvatarUserManagement = itemView.findViewById(R.id.ivAvatarUserManagement);
            tvAcount = itemView.findViewById(R.id.tvUserManagemenUserID);
            cardView = itemView.findViewById(R.id.itemUser);
        }
    }
}
