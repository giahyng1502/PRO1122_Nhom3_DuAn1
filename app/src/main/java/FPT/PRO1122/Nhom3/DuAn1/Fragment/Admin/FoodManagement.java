package FPT.PRO1122.Nhom3.DuAn1.Fragment.Admin;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.Fragment.Home;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.AdapterFoodManagement;
import FPT.PRO1122.Nhom3.DuAn1.adapter.AdapterSpinner;
import FPT.PRO1122.Nhom3.DuAn1.databinding.DialogAddFoodBinding;
import FPT.PRO1122.Nhom3.DuAn1.databinding.FragmentFoodManagerBinding;
import FPT.PRO1122.Nhom3.DuAn1.implement.SwipeToDeleteCallback;
import FPT.PRO1122.Nhom3.DuAn1.model.DanhMucMonAn;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;
import FPT.PRO1122.Nhom3.DuAn1.model.User;


public class FoodManagement extends Fragment {
    FragmentFoodManagerBinding binding;
    List<MonAnByThien> list;
    DatabaseReference databaseReference;
    AdapterFoodManagement adapterFoodManagement;
    DialogAddFoodBinding dialogAddFoodBinding;
    Uri foodUri;
    Dialog dialog;

    ActivityResultLauncher<Intent>activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            foodUri = result.getData().getData();
            dialogAddFoodBinding.ivFood.setImageURI(foodUri);
        }
    });
    public ActivityResultLauncher<Intent> activityResultLauncherUpdate = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Uri uriFoodUpdate = result.getData().getData();
            adapterFoodManagement.setImageUri(uriFoodUpdate);

        }
    });



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFoodManagerBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("Foods");
        if (MainActivity.role == 0) {
            binding.backtoHome.setVisibility(View.GONE);
        }
        binding.backtoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home targetFragment = new Home();

                // Sử dụng FragmentManager và FragmentTransaction để chuyển đổi
                FragmentManager fragmentManager = getParentFragmentManager(); // Hoặc getActivity().getSupportFragmentManager() nếu dùng trong Activity
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Thay thế Fragment hiện tại bằng Fragment đích
                fragmentTransaction.replace(R.id.frameLayout, targetFragment);

                // Thêm vào back stack để có thể quay lại Fragment trước đó
                fragmentTransaction.addToBackStack(null);

                // Hoàn thành giao dịch
                fragmentTransaction.commit();
            }
        });
        getFoodFromFireBase();
        if(MainActivity.role == 0) {
            // Admin = 0
            deleteFood();
            AddFood();
        } else {
            binding.btnAdd.setVisibility(View.GONE);
        }

        searchView();
        binding.searchPrice.setOnClickListener(v -> showTextViewDialog());
    }

    private void searchView() {
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchData(newText);
                return true;
            }
        });
    }
    private void SearchData(String query) {
        ArrayList<MonAnByThien> listSearch = new ArrayList<>();
        for (MonAnByThien monAnByThien : list) {
            if (monAnByThien.getTitle().toLowerCase().contains(query)
            ) {
                listSearch.add(monAnByThien);
            }
        }
        adapterFoodManagement.listFillter(listSearch);
    }
    private void AddFood() {
        binding.btnAdd.setOnClickListener(v -> {
            showdialogAdd();
        });
    }
    private void showTextViewDialog() {
        final String[] textViewOptions = {"0 - 50k", "50k - 100k", "100k ->"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Tìm kiếm theo giá tiền")
                .setItems(textViewOptions, (dialog, which) -> {
                    String selectedOption = textViewOptions[which];
                    double minPrice = 0, maxPrice = Double.MAX_VALUE;

                    switch (selectedOption) {
                        case "0 - 50k":
                            maxPrice = 50000;
                            break;
                        case "50k - 100k":
                            minPrice = 50001;
                            maxPrice = 100000;
                            break;
                        case "100k ->":
                            minPrice = 100001;
                            break;
                    }

                    searchByPrice(minPrice, maxPrice);
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void searchByPrice(double minPrice, double maxPrice) {
        ArrayList<MonAnByThien> monAnByThiens = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MonAnByThien monAn = snapshot.getValue(MonAnByThien.class);

                        // Kiểm tra nếu monAn không phải null và có giá nằm trong khoảng
                        if (monAn != null ) {
                            double price = monAn.getPrice();
                            if (price >= minPrice && price <= maxPrice) {
                                monAnByThiens.add(monAn);
                            }
                        }
                    }
                    // Chỉ cập nhật adapter nếu danh sách không rỗng
                    if (!monAnByThiens.isEmpty()) {
                        adapterFoodManagement.listFillter(monAnByThiens);
                    } else {
                        // Xử lý trường hợp không tìm thấy món ăn nào phù hợp
                        Toast.makeText(requireContext(), "Không tìm thấy món ăn nào trong khoảng giá này.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi
                Toast.makeText(requireContext(), "Lỗi khi truy vấn dữ liệu.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showdialogAdd() {
        List<DanhMucMonAn> danhMucMonAns = new ArrayList<>();
        dialog = new Dialog(requireContext());
        dialogAddFoodBinding = DialogAddFoodBinding.inflate(LayoutInflater.from(requireContext()));
        dialog.setContentView(dialogAddFoodBinding.getRoot());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setStatusBarColor(Color.BLACK);
        dialogAddFoodBinding.ivFood.setOnClickListener(v-> openMedia());

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
                                            AdapterSpinner adapter = new AdapterSpinner(requireContext(), danhMucMonAns);
                                            dialogAddFoodBinding.spnCategory.setAdapter(adapter);
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
            int timestamp = (int) System.currentTimeMillis();
            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Food Name not empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (price.isEmpty()) {
                Toast.makeText(requireContext(), "Price not empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (describe.isEmpty()) {
                Toast.makeText(requireContext(), "Describe not empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (categoryId == -1) {
                Toast.makeText(requireContext(), "CategoryId not empty", Toast.LENGTH_SHORT).show();
                return;
            }
            monAnByThien.setId(timestamp);
            monAnByThien.setPrice(Double.parseDouble(price));
            monAnByThien.setCategoryId(categoryId);
            monAnByThien.setTitle(name);
            monAnByThien.setDescription(describe);
            
            putFoodAvatar(monAnByThien);
        });

        dialog.show();
    }

    private void putFoodAvatar(MonAnByThien monAnByThien) {
        if (foodUri == null) {
            Toast.makeText(requireContext(), "You must upload a picture of the dish.", Toast.LENGTH_SHORT).show();
        } else {
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
    }

    private void uploadFood(MonAnByThien monAnByThien) {
        FirebaseDatabase.getInstance().getReference("Foods").child(monAnByThien.getId()+"")
                .setValue(monAnByThien).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dialog.dismiss();
                        Toast.makeText(requireContext(), "Add food successfuly", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Fail"+e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openMedia() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        activityResultLauncher.launch(intent);
    }

    private void deleteFood() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(requireContext(),binding.recyclerView,200) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                buffer.add(new MyButton(requireContext(),"",13,R.drawable.delete, Color.parseColor("#FF3C30"), pos-> {
                    showBottomSheetDialog(pos);
                }));
            }
        };
    }

    private void showBottomSheetDialog(int pos) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        View bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_layout, null);

        Button btnCancel = bottomSheetView.findViewById(R.id.btnCancel);
        TextView btnConfirm = bottomSheetView.findViewById(R.id.btnConfirm);
        TextView tvMessage = bottomSheetView.findViewById(R.id.tvMessage);
        tvMessage.setText("This food will be permanently deleted from this application");
        btnCancel.setOnClickListener(v -> {
            adapterFoodManagement.notifyDataSetChanged();
            bottomSheetDialog.dismiss();
        });

        btnConfirm.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();

            MonAnByThien monAnByThien = list.get(pos);
            // Xóa user khỏi Firebase Database
            FirebaseDatabase.getInstance().getReference("Foods")
                    .child(monAnByThien.getId()+"").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(requireContext(), "Delete Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(requireContext(), "Error" + e, Toast.LENGTH_SHORT).show();
                        }
                    });
            // Xóa user khỏi danh sách và cập nhật giao diện
            adapterFoodManagement.notifyItemRemoved(pos);
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.getWindow().setStatusBarColor(Color.BLACK);
        bottomSheetDialog.show();
    }

    private void getFoodFromFireBase() {
        list = new ArrayList<>();
        databaseReference.orderByChild("categoryId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        list.add(dataSnapshot.getValue(MonAnByThien.class));
                    }
                    if (!list.isEmpty()) {
                        initReclerView();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Fail"+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initReclerView() {
        adapterFoodManagement = new AdapterFoodManagement(requireContext(),list,activityResultLauncherUpdate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(adapterFoodManagement);
    }


}