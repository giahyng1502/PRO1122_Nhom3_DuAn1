package FPT.PRO1122.Nhom3.DuAn1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.adapter.AdapterFavorite;
import FPT.PRO1122.Nhom3.DuAn1.model.DanhMucMonAn;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;
import FPT.PRO1122.Nhom3.DuAn1.databinding.ActivityListMonAnBinding;

public class ListMonAn extends AppCompatActivity {
    ActivityListMonAnBinding binding;
    private RecyclerView.Adapter adapterFoodListView;
    DanhMucMonAn danhMucMonAn;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListMonAnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        getIntentExtra();
        initList();
        setVariable();
    }

    private void setVariable() {
    }


    private void initList() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBarFoods.setVisibility(View.VISIBLE);
        ArrayList<MonAnByThien> list = new ArrayList<>();

        Query query;
            query = myRef.orderByChild("CategoryId").equalTo(danhMucMonAn.getId());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue : snapshot.getChildren()){
                        list.add(issue.getValue(MonAnByThien.class));
                    }
                    if (list.size()>0){
                        binding.foodListView.setLayoutManager(new GridLayoutManager(ListMonAn.this, 1));
                        adapterFoodListView = new AdapterFavorite(list);
                        binding.foodListView.setAdapter(adapterFoodListView);
                    }
                    binding.progressBarFoods.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        danhMucMonAn = (DanhMucMonAn) intent.getSerializableExtra("Category");

        binding.titleCategoryTxt.setText(danhMucMonAn.getName());

        binding.backBtn.setOnClickListener(v -> startActivity(new Intent(ListMonAn.this, MainActivity.class)));
    }
}