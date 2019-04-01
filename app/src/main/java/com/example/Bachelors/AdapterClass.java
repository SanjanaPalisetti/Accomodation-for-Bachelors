package com.example.Bachelors;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder>  {
    ArrayList<property> list;
    public AdapterClass(ArrayList<property>list){
        this.list=list;
    }
    @NonNull
    @Override
    public AdapterClass.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.MyViewHolder myViewHolder, int i) {
        myViewHolder.ID.setText(list.get(i).getpropertyID());
        myViewHolder.desc.setText(list.get(i).getLocation());
//        myViewHolder.price.setText(list.get(i).getcost());
//        myViewHolder.name.setText(list.get(i).getOwnername());
//        myViewHolder.rooms.setText(list.get(i).getAvl_rooms());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ID,desc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ID=itemView.findViewById((R.id.Pproperty));
            desc=itemView.findViewById(R.id.userid);
//            rooms=itemView.findViewById(R.id.rooms);
//            price=itemView.findViewById(R.id.price);
//            name=itemView.findViewById(R.id.name);
        }
    }
}