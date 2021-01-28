package com.nmakadiya.expensemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter_menu4 extends RecyclerView.Adapter<CustomAdapter_menu4.MyViewHolder> {

    ArrayList<String> list = new ArrayList();
    ArrayList<String> NameList = new ArrayList();
    int key;
    Context context;

    public CustomAdapter_menu4(Context context, ArrayList<String> list, ArrayList<String> NameList, int key) {
        this.list = list;
        this.context = context;
        this.NameList = NameList;
        this.key = key;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.menu4_recycle_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv2.setText(list.get(position));
        holder.tv1.setText(NameList.get(position));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv1;
        TextView tv2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.m4rvtv1);
            tv2 = itemView.findViewById(R.id.m4rvtv2);
        }
    }
}
