package FPT.PRO1122.Nhom3.DuAn1.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import FPT.PRO1122.Nhom3.DuAn1.Activity.MainActivity;
import FPT.PRO1122.Nhom3.DuAn1.R;
import FPT.PRO1122.Nhom3.DuAn1.model.Cart;
import FPT.PRO1122.Nhom3.DuAn1.model.Order;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    private ArrayList<Order> orderList;
    private Context context;

    public OrderHistoryAdapter(Context context, ArrayList<Order> orderList) {
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
        Order order = orderList.get(position);
        holder.orderDateTxt.setText(order.getOrderDate());
        holder.nameTxt.setText(order.getUser());
        holder.totalAmountTxt.setText(order.getTotalAmount() + " VND");
        holder.phoneTxt.setText(order.getPhone());
        holder.addressTxt.setText(order.getAddress());
        holder.statusTxt.setText(getStatusString(order.getStatus()));

        StringBuilder productListBuilder = new StringBuilder();
        int size = order.getOrderDetails().size();

        for (int i = 0; i < size; i++) {
            Cart product = order.getOrderDetails().get(i);
            productListBuilder.append(product.getTitle())
                    .append(" x ")
                    .append(product.getQuantity());

            // Kiểm tra nếu không phải phần tử cuối cùng thì thêm dấu ", "
            if (i < size - 1) {
                productListBuilder.append(", ");
            } else {
                // Phần tử cuối cùng thì thêm dấu "."
                productListBuilder.append(".");
            }
        }
        holder.productListTxt.setText(productListBuilder.toString());
        if (order.getStatus() == 3) {
            holder.btnHuy.setVisibility(View.VISIBLE);
            holder.btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    huyDonHang(order,holder);
                }
            });
        } else {
            holder.btnHuy.setVisibility(View.GONE);

        }
        if (MainActivity.role == 0) {
            //admin
            if (order.getStatus() == 3) {
                holder.btnXacNhan.setVisibility(View.VISIBLE);
                holder.btnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        xacNhanDonHang(order,holder);
                    }
                });

            }
            if (order.getStatus() == 2) {

                holder.btnDangGiao.setVisibility(View.VISIBLE);

                holder.btnDangGiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        giaoDonHang(order,holder);
                    }
                });
            }
            if (order.getStatus() == 1) {
                holder.btnThanhCong.setVisibility(View.VISIBLE);
                holder.btnThanhCong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hoanThanhDonHang(order,holder);
                    }
                });
            }
        }
    }

    private void xacNhanDonHang(Order order, ViewHolder holder) {
        order.setStatus(2);
        FirebaseDatabase.getInstance().getReference("Orders")
                .child(order.getUserID()).child(order.getOrderId())
                .setValue(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        holder.btnXacNhan.setVisibility(View.GONE);
                        holder.btnHuy.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    private void huyDonHang(Order order, ViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác Nhận");
        builder.setMessage("Bạn có chắc chắn muốn huỷ đơn hàng này không ?");
        builder.setPositiveButton("Có",(dialog, which) -> {
            FirebaseDatabase.getInstance().getReference("Orders")
                    .child(order.getUserID()).child(order.getOrderId())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Huỷ thành công đơn hàng", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        });
        builder.setNegativeButton("Không",(dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }
    private void giaoDonHang(Order order, ViewHolder holder) {
        order.setStatus(1);
        FirebaseDatabase.getInstance().getReference("Orders")
                .child(order.getUserID()).child(order.getOrderId())
                .setValue(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        holder.btnDangGiao.setVisibility(View.GONE);
                        holder.btnHuy.setVisibility(View.GONE);
                        Toast.makeText(context, "Đơn hàng đang được vận chuyển", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    private void hoanThanhDonHang(Order order, ViewHolder holder) {
        order.setStatus(0);
        FirebaseDatabase.getInstance().getReference("Orders")
                .child(order.getUserID()).child(order.getOrderId())
                .setValue(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        holder.btnThanhCong.setVisibility(View.GONE);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Đơn hàng đã hoàn thành", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnXacNhan,btnHuy,btnDangGiao,btnThanhCong;
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
            btnXacNhan = itemView.findViewById(R.id.btnXacNhan);
            btnHuy = itemView.findViewById(R.id.btnHuy);
            btnDangGiao = itemView.findViewById(R.id.btnDangGiao);
            btnThanhCong = itemView.findViewById(R.id.btnHoanThanh);


        }
    }

    private String getStatusString(int status) {
        switch (status) {
            case 3:
                return "Chờ Xác Nhận";
            case 2:
                return "Đang Chuẩn Bị";
            case 1:
                return "Đang giao";
            case 0:
                return "Hoàn thành";
            default:
                return "Không xác định";
        }
    }
}
