package FPT.PRO1122.Nhom3.DuAn1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.DAO.ChangeNumberItemsListener;
import FPT.PRO1122.Nhom3.DuAn1.DAO.QuanLyGioHang;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.model.GioHang;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.ViewHolder>{

    ArrayList<GioHang> list = new ArrayList<>();
    private QuanLyGioHang quanLyGioHang;

    ChangeNumberItemsListener changeNumberItemsListener;

    public CheckOutAdapter(ArrayList<GioHang> list, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.list = list;
        quanLyGioHang = new QuanLyGioHang(context, MainActivity.id);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    public CheckOutAdapter(ArrayList<GioHang> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CheckOutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_thanhtoan_rec, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutAdapter.ViewHolder holder, int position) {
        holder.titleTxtCheckout.setText(list.get(position).getTitle());
        holder.numTxtInCheckOut.setText(list.get(position).getQuantity() + "");
        holder.totalPriceTxtInCheckout.setText(list.get(position).getTotal() + " VND");

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.picCheckout);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxtCheckout, totalPriceTxtInCheckout, numTxtInCheckOut ;
        ImageView picCheckout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxtCheckout = itemView.findViewById(R.id.titleTxtCheckout);
            numTxtInCheckOut = itemView.findViewById(R.id.numTxtInCheckOut);
            totalPriceTxtInCheckout = itemView.findViewById(R.id.totalPriceTxtInCheckout);
            picCheckout = itemView.findViewById(R.id.picCheckout);

        }
    }
}
