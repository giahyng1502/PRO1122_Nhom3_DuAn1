package FPT.PRO1122.Nhom3.DuAn1.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.databinding.DialogAddFoodBinding;
import FPT.PRO1122.Nhom3.DuAn1.model.DanhMucMonAn;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;

public class AdapterFoodManagement extends RecyclerView.Adapter<AdapterFoodManagement.ViewHolder> {
    Context context;
    List<MonAnByThien> list;
    private Dialog dialog;
    private DialogAddFoodBinding dialogAddFoodBinding;
    public Uri foodUri;
    ActivityResultLauncher<Intent> activityResultLauncherUpdate;

    public AdapterFoodManagement(Context context, List<MonAnByThien> list, ActivityResultLauncher<Intent> activityResultLauncherUpdate) {
        this.context = context;
        this.list = list;
        this.activityResultLauncherUpdate = activityResultLauncherUpdate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_admin,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonAnByThien food = list.get(position);
        holder.tvTitle.setText(food.getTitle());
        holder.tvDecr.setText(food.getDescription());
        holder.tvRate.setText(food.getStar()+"");
        holder.tvPrice.setText(food.getPrice() +"");

        FirebaseDatabase.getInstance()
                        .getReference("Category")
                        .child(food.getCategoryId()+"")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            String categoryName = snapshot.child("Name").getValue(String.class);
                                            holder.tvCatagory.setText(categoryName);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        holder.tvCatagory.setText("");
                                    }
                                });

        Glide.with(context).load(food.getImagePath()).into(holder.ivFoodAvatar);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showdialogAdd(food);
                return true;
            }
        });
    }

    private void showdialogAdd(MonAnByThien food) {
        List<DanhMucMonAn> danhMucMonAns = new ArrayList<>();
        dialog = new Dialog(context);
        dialogAddFoodBinding = DialogAddFoodBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(dialogAddFoodBinding.getRoot());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setStatusBarColor(Color.BLACK);
        dialogAddFoodBinding.ivFood.setOnClickListener(v-> openMedia());

        dialogAddFoodBinding.edtDescribe.setText(food.getDescription());
        dialogAddFoodBinding.edtPrice.setText(food.getPrice()+"");
        dialogAddFoodBinding.tvFoodName.setText(food.getTitle());
        dialogAddFoodBinding.tvFoodID.setText(food.getId()+"");

        if (foodUri == null)
            Glide.with(context).load(food.getImagePath()).into(dialogAddFoodBinding.ivFood);
        else dialogAddFoodBinding.ivFood.setImageURI(foodUri);
        FirebaseDatabase.getInstance()
                .getReference("Category")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                danhMucMonAns.add(dataSnapshot.getValue(DanhMucMonAn.class));
                            }
                        }
                        if (!danhMucMonAns.isEmpty()) {
                            AdapterSpinner adapter = new AdapterSpinner(context, danhMucMonAns);
                            dialogAddFoodBinding.spnCategory.setAdapter(adapter);
                            for (int i = 0 ; i < danhMucMonAns.size(); i++) {
                                if (danhMucMonAns.get(i).getId() == food.getCategoryId()) {
                                    dialogAddFoodBinding.spnCategory.setSelection(i);
                                    break;
                                }
                            }  
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




        dialogAddFoodBinding.btnCancelUserManagement.setOnClickListener(v->dialog.dismiss());
        dialogAddFoodBinding.btnSubMitUserManagement.setOnClickListener(v-> {
            String name = dialogAddFoodBinding.tvFoodName.getText().toString();
            String price = dialogAddFoodBinding.edtPrice.getText().toString();
            String describe = dialogAddFoodBinding.edtDescribe.getText().toString();

            int categoryId = danhMucMonAns.get(dialogAddFoodBinding.spnCategory.getSelectedItemPosition()).getId();
            MonAnByThien monAnByThien = new MonAnByThien();

            monAnByThien.setId(food.getId());
            monAnByThien.setPrice(Double.parseDouble(price));
            monAnByThien.setCategoryId(categoryId);
            monAnByThien.setTitle(name);
            monAnByThien.setDescription(describe);
            if (foodUri == null) {
                monAnByThien.setImagePath(food.getImagePath());
                uploadFood(monAnByThien);
            } else {
                putFoodAvatar(monAnByThien);
            }
        });

        dialog.show();
    }

    private void putFoodAvatar(MonAnByThien monAnByThien) {

            FirebaseStorage.getInstance().getReference("Image Food").child(monAnByThien.getId()+"")
                    .putFile(foodUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            FirebaseStorage.getInstance().getReference("Image Food")
                                    .child(monAnByThien.getId()+"")
                                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageFood = uri.toString();
                                            monAnByThien.setImagePath(imageFood);
                                            uploadFood(monAnByThien);
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
    }

    private void uploadFood(MonAnByThien monAnByThien) {
        FirebaseDatabase.getInstance().getReference("Foods").child(monAnByThien.getId()+"")
                .setValue(monAnByThien).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dialog.dismiss();
                        Toast.makeText(context, "Update successfuly", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Fail"+e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openMedia() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        activityResultLauncherUpdate.launch(intent);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void listFillter(ArrayList<MonAnByThien> listSearch) {
        this.list = listSearch;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvPrice,tvDecr,tvRate,tvCatagory;
        ImageView ivFoodAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleFood);
            tvPrice = itemView.findViewById(R.id.tvPriceFood);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvDecr = itemView.findViewById(R.id.tvMota);
            tvCatagory = itemView.findViewById(R.id.tvCategoryTitle);
            ivFoodAvatar = itemView.findViewById(R.id.ivAvatarFood);
        }
    }
}
