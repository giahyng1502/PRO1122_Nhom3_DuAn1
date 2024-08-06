package FPT.PRO1122.Nhom3.DuAn1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.model.GioHang;
import FPT.PRO1122.Nhom3.DuAn1.model.MonAnByThien;
import FPT.PRO1122.Nhom3.DuAn1.model.OrderHistory;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    private ArrayList<OrderHistory> orderList;
    private Context context;

    public OrderHistoryAdapter(Context context, ArrayList<OrderHistory> orderList) {
        this.context = context;
        this.orderList = orderList;
    }


    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_orderhistory,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.ViewHolder holder, int position) {
        OrderHistory order = orderList.get(position);
        holder.orderDateTxt.setText(order.getOrderDate());
        holder.nameTxt.setText(order.getUser());
        holder.totalAmountTxt.setText(order.getTotalAmount() + " VND");
        holder.phoneTxt.setText(order.getPhone());
        holder.addressTxt.setText(order.getAddress());
        holder.statusTxt.setText(getStatusString(order.getStatus()));

        StringBuilder productListBuilder = new StringBuilder("Product List: ");
        for (GioHang product : order.getProductList()) {
            productListBuilder.append(product.getTitle()).append(" x ").append(product.getQuantity()).append("), ").append("\n");;
        }
        holder.productListTxt.setText(productListBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTxt, orderDateTxt, nameTxt, phoneTxt, addressTxt, productListTxt, statusTxt, totalAmountTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            totalAmountTxt = itemView.findViewById(R.id.totalAmountTxt);
            orderDateTxt = itemView.findViewById(R.id.orderDateTxt);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            phoneTxt = itemView.findViewById(R.id.phoneTxt);
            addressTxt = itemView.findViewById(R.id.addressTxt);
            productListTxt = itemView.findViewById(R.id.productListTxt);
            statusTxt = itemView.findViewById(R.id.statusTxt);


        }
    }

    private String getStatusString(int status) {
        switch (status) {
            case 0:
                return "Đang chờ";
            case 1:
                return "Đã xác nhận";
            case 2:
                return "Đang giao";
            case 3:
                return "Hoàn thành";
            default:
                return "Không xác định";
        }
    }
}
