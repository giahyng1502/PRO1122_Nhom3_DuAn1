package FPT.PRO1122.Nhom3.DuAn1.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import FPT.PRO1122.Nhom3.DuAn1.ChangePassword;
import FPT.PRO1122.Nhom3.DuAn1.DeliveryAddress;
import FPT.PRO1122.Nhom3.DuAn1.EditProfile;
import FPT.PRO1122.Nhom3.DuAn1.OrderHistory;
import FPT.PRO1122.Nhom3.DuAn1.Payment;
import FPT.PRO1122.Nhom3.DuAn1.R;

public class Profile extends Fragment {
    LinearLayout payment_layout,order_layout,changepass_layout,delivery_layout;
    Button btn_logout,btn_editprofile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        //anh xa
        payment_layout=view.findViewById(R.id.payment_layout);
        order_layout=view.findViewById(R.id.orderhistory_layout);
        delivery_layout=view.findViewById(R.id.delivery_layout);
        changepass_layout=view.findViewById(R.id.changepass_layout);
        btn_logout=view.findViewById(R.id.btn_logout);
        btn_editprofile=view.findViewById(R.id.btn_editprofile);
        //

        //payment
        payment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Payment.class);
                startActivity(intent);
            }
        });

        //order
        order_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderHistory.class);
                startActivity(intent);
            }
        });

        //delivery address
        delivery_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DeliveryAddress.class);
                startActivity(intent);
            }
        });

        //changepass
        changepass_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePassword.class);
                startActivity(intent);
            }
        });

        //edit profile
        btn_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        //logout
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
        //

    }
    //

}