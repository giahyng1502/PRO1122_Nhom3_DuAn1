package FPT.PRO1122.Nhom3.DuAn1.Fragment.Admin;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.AdapterFoodManagement;
import FPT.PRO1122.Nhom3.DuAn1.databinding.FragmentFoodManagerBinding;
import FPT.PRO1122.Nhom3.DuAn1.implement.SwipeToDeleteCallback;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;
import FPT.PRO1122.Nhom3.DuAn1.model.User;


public class FoodManagement extends Fragment {
    FragmentFoodManagerBinding binding;
    List<MonAnByThien> list;
    DatabaseReference databaseReference;
    AdapterFoodManagement adapterFoodManagement;
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
        getFoodFromFireBase();
        deleteFood();
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
                            list.remove(pos);
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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
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
        adapterFoodManagement = new AdapterFoodManagement(requireContext(),list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(adapterFoodManagement);
    }


}