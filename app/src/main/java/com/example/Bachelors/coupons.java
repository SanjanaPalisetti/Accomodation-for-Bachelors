package com.example.Bachelors;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class coupons extends Fragment {

    Button button1;
//    TextView text1;
//    String temp;
    Button button2;
    Button button3;
    Button button4;
    Button button5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.coupons, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button1 = (Button)view.findViewById(R.id.button_coupon1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Coupon is applied!!!!!!", Toast.LENGTH_LONG).show();
            }
        });

        button2 = (Button)view.findViewById(R.id.button_coupon2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Coupon is applied!!!!!!", Toast.LENGTH_LONG).show();
            }
        });

        button3 = (Button)view.findViewById(R.id.button_coupon3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Coupon is applied!!!!!!", Toast.LENGTH_LONG).show();
            }
        });

        button4 = (Button)view.findViewById(R.id.button_coupon4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Coupon is applied!!!!!!", Toast.LENGTH_LONG).show();
            }
        });

        button5 = (Button)view.findViewById(R.id.button_coupon5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Coupon is applied!!!!!!", Toast.LENGTH_LONG).show();
            }
        });

    }
}
