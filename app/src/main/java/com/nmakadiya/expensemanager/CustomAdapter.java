package com.nmakadiya.expensemanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private int KEY;
    private ArrayList<String> UniqueIdList = new ArrayList();
    private ArrayList<String> DateList = new ArrayList();
    private ArrayList<String> TimeList = new ArrayList();
    private ArrayList<String> AmountList = new ArrayList();
    private ArrayList<String> CategoryList = new ArrayList();
    private ArrayList<String> MethodList = new ArrayList();
    private ArrayList<String> DescriptionList = new ArrayList();
    private ArrayList<String> Category_Source_IdList = new ArrayList();
    private ArrayList<String> Method_IdList = new ArrayList();
    private Context context;


    public CustomAdapter(Context context, ArrayList<String> UniqueIdList, ArrayList<String> DateList, ArrayList<String> TimeList, ArrayList<String> AmountList, ArrayList<String> CategoryList, ArrayList<String> MethodList, ArrayList<String> DescriptionList, ArrayList<String> Category_IdList, ArrayList<String> Method_IdList, int KEY) {
        this.context = context;
        this.UniqueIdList = UniqueIdList;
        this.DateList = DateList;
        this.TimeList = TimeList;
        this.AmountList = AmountList;
        this.CategoryList = CategoryList;
        this.DescriptionList = DescriptionList;
        this.MethodList = MethodList;
        this.Method_IdList = Method_IdList;
        this.Category_Source_IdList = Category_IdList;
        this.KEY = KEY;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.textView1.setText(DateList.get(position));
        holder.textView2.setText(TimeList.get(position));
        switch (KEY) {
            case 1:
                holder.textView3.setText("EXPENSE");
                holder.textView4.setText("Category: " + CategoryList.get(position));
                holder.textView5.setText("Method: " + MethodList.get(position));
                break;
            case 2:
                holder.textView3.setText("INCOME");
                holder.textView4.setText("Category: " + CategoryList.get(position));
                holder.textView5.setText("Method: " + MethodList.get(position));
                break;
            case 3:
                holder.textView3.setText("TRANSFER");
                holder.textView4.setText("Source: " + CategoryList.get(position));
                holder.textView5.setText("Target: " + MethodList.get(position));
                break;
        }

        holder.textView6.setText("Amount: " + AmountList.get(position));
        holder.textView7.setText("Description: " + DescriptionList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete this record?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyHelper helper = new MyHelper(context);
                        helper.delete(Integer.parseInt(UniqueIdList.get(position)), KEY);
                        String cash = "0", wallet = "0", account = "0";
                        String amount = AmountList.get(position);

                        if (KEY == 1) {
                            int methodkey = Integer.parseInt(Method_IdList.get(position));
                            switch (methodkey) {
                                case 0:
                                    cash = amount;
                                    wallet = "0";
                                    account = "0";
                                    break;
                                case 1:
                                    cash = "0";
                                    wallet = amount;
                                    account = "0";
                                    break;
                                case 2:
                                    cash = "0";
                                    wallet = "0";
                                    account = amount;
                                    break;
                            }
                            helper.insertInto4(account, wallet, cash);
                        } else if (KEY == 2) {
                            int methodkey = Integer.parseInt(Method_IdList.get(position));
                            switch (methodkey) {
                                case 0:
                                    cash = "-" + amount;
                                    wallet = "0";
                                    account = "0";
                                    break;
                                case 1:
                                    cash = "0";
                                    wallet = "-" + amount;
                                    account = "0";
                                    break;
                                case 2:
                                    cash = "0";
                                    wallet = "0";
                                    account = "-" + amount;
                                    break;
                            }
                            helper.insertInto4(account, wallet, cash);
                        } else if (KEY == 3) {
                            int target_id = Integer.parseInt(Method_IdList.get(position));
                            int source_id = Integer.parseInt(Category_Source_IdList.get(position));

                            switch (source_id) {
                                case 0:
                                    cash = amount;
                                    wallet = "0";
                                    account = "0";
                                    break;
                                case 1:
                                    cash = "0";
                                    wallet = amount;
                                    account = "0";
                                    break;
                                case 2:
                                    cash = "0";
                                    wallet = "0";
                                    account = amount;
                                    break;
                            }
                            switch (target_id) {

                                case 0:
                                    cash = "-" + amount;
                                    wallet = "0";
                                    account = "0";
                                    break;
                                case 1:
                                    cash = "0";
                                    wallet = "-" + amount;
                                    account = "0";
                                    break;
                                case 2:
                                    cash = "0";
                                    wallet = "0";
                                    account = "-" + amount;
                                    break;
                            }
                            helper.insertInto4(account, wallet, cash);
                        }

                        UniqueIdList.remove(position);
                        DateList.remove(position);
                        TimeList.remove(position);
                        AmountList.remove(position);
                        Category_Source_IdList.remove(position);
                        CategoryList.remove(position);
                        Method_IdList.remove(position);
                        MethodList.remove(position);
                        DescriptionList.remove(position);


                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, getItemCount());
                       /* notifyItemChanged(position);
                        notifyDataSetChanged();
                        notifyItemInserted(position);*/
                        Toast.makeText(context, "Record Deleted Successfully.. ", Toast.LENGTH_LONG).show();

                        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context.getApplicationContext());
                        if (acct != null) {
                            String personId = acct.getId();
                            MainActivity.upload(context, personId);
                        }

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Okay..", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Conform Delete");
                alertDialog.show();

            }
        });


    }


    @Override
    public int getItemCount() {
        return AmountList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        TextView textView7;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.rvtv1);
            textView2 = itemView.findViewById(R.id.rvtv2);
            textView3 = itemView.findViewById(R.id.rvtv3);
            textView4 = itemView.findViewById(R.id.rvtv4);
            textView5 = itemView.findViewById(R.id.rvtv5);
            textView6 = itemView.findViewById(R.id.rvtv6);
            textView7 = itemView.findViewById(R.id.rvtv7);
        }


    }

}
