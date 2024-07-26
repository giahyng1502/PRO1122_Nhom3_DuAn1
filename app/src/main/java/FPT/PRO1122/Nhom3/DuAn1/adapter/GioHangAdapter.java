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

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.DAO.ChangeNumberItemsListener;
import FPT.PRO1122.Nhom3.DuAn1.DAO.QuanLyGioHang;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;
import FPT.PRO1122.Nhom3.DuAn1.R;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    ArrayList<MonAnByThien> list = new ArrayList<>();
    private QuanLyGioHang quanLyGioHang;
    ChangeNumberItemsListener changeNumberItemsListener;

    public GioHangAdapter(ArrayList<MonAnByThien> list, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.changeNumberItemsListener = changeNumberItemsListener;
        quanLyGioHang = new QuanLyGioHang(context);
        this.list = list;
    }

    @NonNull
    @Override
    public GioHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_item_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.ViewHolder holder, int position) {
        holder.titleTxtInCart.setText(list.get(position).getTitle());
        holder.priceItemTxtInCart.setText(list.get(position).getPrice() + " VND");
        holder.totalPriceTxtInCart.setText(list.get(position).getPrice() * list.get(position).getNumberInCart() + " VND");
        holder.numTxtInCart.setText(list.get(position).getNumberInCart() + "");

        holder.minusBtnInCart.setOnClickListener(v -> {
            quanLyGioHang.minusNumberItem(list, position, () -> changeNumberItemsListener.change());
//            holder.numTxtInCart.setText();
        });

        holder.plusBtnInCart.setOnClickListener(v -> {
            quanLyGioHang.plusNumberItem(list, position, () -> changeNumberItemsListener.change());
        });

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxtInCart, priceItemTxtInCart, totalPriceTxtInCart, minusBtnInCart, numTxtInCart, plusBtnInCart ;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxtInCart = itemView.findViewById(R.id.titleTxtInCart);
            priceItemTxtInCart = itemView.findViewById(R.id.priceItemTxtInCart);
            totalPriceTxtInCart = itemView.findViewById(R.id.totalPriceTxtInCart);
            minusBtnInCart = itemView.findViewById(R.id.minusBtnInCart);
            numTxtInCart = itemView.findViewById(R.id.numTxtInCart);
            plusBtnInCart = itemView.findViewById(R.id.plusBtnInCart);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
