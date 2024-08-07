package FPT.PRO1122.Nhom3.DuAn1.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.adapter.AdapterFavorite;
import FPT.PRO1122.Nhom3.DuAn1.adapter.DoAnBanChayAdapter;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;


public class Favorite extends Fragment {
    RecyclerView recyclerView;
    AdapterFavorite adtFavorite;
    ArrayList<MonAnByThien> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_Favorite);
        list = new ArrayList<>();
        getBestFood();
        setBestFoodToView();
    }

    private void setBestFoodToView() {
        adtFavorite = new AdapterFavorite(list,requireActivity());
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(1
                , StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adtFavorite);
        adtFavorite.notifyDataSetChanged();
    }

    private void getBestFood() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Favorite").child(MainActivity.id);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        MonAnByThien monAn = issue.getValue(MonAnByThien.class);
                        if (monAn != null) {
                            list.add(monAn);
                        }
                    }
                }
                // Always notify the adapter about data changes
                adtFavorite.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "fail"+error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}