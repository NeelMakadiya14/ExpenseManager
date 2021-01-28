package com.nmakadiya.expensemanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter_menu5 extends RecyclerView.Adapter<CustomAdapter_menu5.MyViewHolder> {

    ArrayList<String> List1 = new ArrayList();
    ArrayList<String> List2 = new ArrayList();
    ArrayList<ArrayList<String>> List3_CatEx = new ArrayList();
    ArrayList<ArrayList<String>> List4_CatIn = new ArrayList();
    ArrayList<String> NameList = new ArrayList();
    Context context;

    public CustomAdapter_menu5(Context context, ArrayList<String> List1, ArrayList<String> List2, ArrayList<String> NameList, ArrayList<ArrayList<String>> List3, ArrayList<ArrayList<String>> List4) {
        this.List1 = List1;
        this.List2 = List2;
        this.NameList = NameList;
        this.List3_CatEx = List3;
        this.List4_CatIn = List4;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.menu5_recycle_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv1.setText(NameList.get(position));
        holder.tv2.setText("Expense: " + List1.get(position));
        holder.tv4.setText("Income: " + List2.get(position));
        double Net = Double.parseDouble(List2.get(position)) - Double.parseDouble(List1.get(position));
        holder.tv3.setText("Balance: " + Double.toString(Net));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> Item = new ArrayList();
                Item.add("EXPENSE..");
                Item.add("Clothing: " + List3_CatEx.get(position).get(0));
                Item.add("Drinks: " + List3_CatEx.get(position).get(1));
                Item.add("Food: " + List3_CatEx.get(position).get(2));
                Item.add("Fuel: " + List3_CatEx.get(position).get(3));
                Item.add("Health: " + List3_CatEx.get(position).get(4));
                Item.add("Fun: " + List3_CatEx.get(position).get(5));
                Item.add("Transport: " + List3_CatEx.get(position).get(6));
                Item.add("Restaurant: " + List3_CatEx.get(position).get(7));
                Item.add("Give to someone: " + List3_CatEx.get(position).get(8));
                Item.add("Gift: " + List3_CatEx.get(position).get(9));
                Item.add("Other1: " + List3_CatEx.get(position).get(10));
                Item.add("Other2: " + List3_CatEx.get(position).get(11));
                Item.add("Other3: " + List3_CatEx.get(position).get(12));
                Item.add("Other4: " + List3_CatEx.get(position).get(13));
                Item.add("INCOME");
                Item.add("Loan: " + List4_CatIn.get(position).get(0));
                Item.add("Salary: " + List4_CatIn.get(position).get(1));
                Item.add("Sales: " + List4_CatIn.get(position).get(2));
                Item.add("Owe From Other: " + List4_CatIn.get(position).get(3));
                Item.add("Winning Price: " + List4_CatIn.get(position).get(4));
                Item.add("Gift: " + List4_CatIn.get(position).get(5));
                Item.add("Other1: " + List4_CatIn.get(position).get(6));
                Item.add("Other2: " + List4_CatIn.get(position).get(7));
                Item.add("Other3: " + List4_CatIn.get(position).get(8));
                Item.add("Other4: " + List4_CatIn.get(position).get(9));

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, Item) {
                    @Override
                    public View getView(int Position, View convertView, ViewGroup parent) {
                        // Cast list view each item as text view
                        TextView text_view = (TextView) super.getView(Position, convertView, parent);

                        // Set text size
                        text_view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

                        // Get the list view odd and even position items
                        if (Position == 0 || Position == 15) {
                            text_view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                            // Set the list view one by one items (row) background color
                            text_view.setBackgroundColor(Color.parseColor("#5f9457"));

                            // Set the list view one by one items text color
                            text_view.setTextColor(Color.parseColor("#101c4c"));
                        }
                        // Finally, return the modified items
                        return text_view;
                    }
                };

                //String[] item={"Expense...","Clothing: "+List3_CatEx.get(position).get(0),"Drinks: "+List3_CatEx.get(position).get(1),"Food: "+List3_CatEx.get(position).get(2),"Fuel: "+List3_CatEx.get(position).get(3),"Health: "+List3_CatEx.get(position).get(4),"Fun: "+List3_CatEx.get(position).get(5),"Transport: "+List3_CatEx.get(position).get(6),"Restaurant: "+List3_CatEx.get(position).get(7),"Give to someone: "+List3_CatEx.get(position).get(8),"Gift: "+List3_CatEx.get(position).get(9),"Other1: "+List3_CatEx.get(position).get(10),"Other2: "+List3_CatEx.get(position).get(11),"Other3: "+List3_CatEx.get(position).get(12),"Other4: "+List3_CatEx.get(position).get(13)};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Category Wise Expense and Income");
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                /*builder.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });*/
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return List1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.m5rvtv1);
            tv2 = itemView.findViewById(R.id.m5rvtv2);
            tv3 = itemView.findViewById(R.id.m5rvtv3);
            tv4 = itemView.findViewById(R.id.m5rvtv4);
        }
    }
}
