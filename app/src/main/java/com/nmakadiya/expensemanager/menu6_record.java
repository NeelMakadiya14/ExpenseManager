package com.nmakadiya.expensemanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class menu6_record extends AppCompatActivity {

    MyHelper helper = new MyHelper(menu6_record.this);
    ArrayList<ArrayList<String>> detail = new ArrayList();
    int KEY1 = 1;
    int KEY2 = 1;
    int getDay, getMonth, getYear;
    ArrayList<String> month = new ArrayList();

    ArrayList<String> UniqueIdList = new ArrayList();
    ArrayList<String> DateList = new ArrayList();
    ArrayList<String> TimeList = new ArrayList();
    ArrayList<String> AmountList = new ArrayList();
    ArrayList<String> CategoryList_Source = new ArrayList();
    ArrayList<String> MethodList_Target = new ArrayList();
    ArrayList<String> DescriptionList = new ArrayList();
    ArrayList<String> Method_IdList = new ArrayList();
    ArrayList<String> Category_Source_IdList = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu6_record);

        month.add("Jan");
        month.add("Feb");
        month.add("Mar");
        month.add("Apr");
        month.add("May");
        month.add("Jun");
        month.add("Jul");
        month.add("Aug");
        month.add("Sep");
        month.add("Oct");
        month.add("Nov");
        month.add("Dec");
        final Calendar myCalenar = Calendar.getInstance();
        getDay = myCalenar.get(Calendar.DAY_OF_MONTH);
        getMonth = myCalenar.get(Calendar.MONTH) + 1;
        getYear = myCalenar.get(Calendar.YEAR);


        final TextView tv1 = findViewById(R.id.m6tv1);
        final TextView tv2 = findViewById(R.id.m6tv2);
        final TextView tv3 = findViewById(R.id.m6tv3);
        final TextView tv4 = findViewById(R.id.m6tv4);
        final TextView tv5 = findViewById(R.id.m6tv5);
        final EditText et1 = findViewById(R.id.m6et1);

        et1.setText(Integer.toString(getDay) + "/" + Integer.toString(getMonth) + "/" + Integer.toString(getYear));
        afterCahnge();

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setBackgroundColor(Color.parseColor("#EA1E44"));
                tv2.setBackgroundColor(Color.WHITE);
                tv3.setBackgroundColor(Color.WHITE);
                KEY1 = 1;

                afterCahnge();
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv2.setBackgroundColor(Color.parseColor("#EA1E44"));
                tv1.setBackgroundColor(Color.WHITE);
                tv3.setBackgroundColor(Color.WHITE);
                KEY1 = 2;
                afterCahnge();
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv3.setBackgroundColor(Color.parseColor("#EA1E44"));
                tv1.setBackgroundColor(Color.WHITE);
                tv2.setBackgroundColor(Color.WHITE);
                KEY1 = 3;
                afterCahnge();
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv4.setBackgroundColor(Color.parseColor("#EA1E44"));
                tv5.setBackgroundColor(Color.WHITE);
                KEY2 = 1;
                et1.setText(Integer.toString(getDay) + "/" + Integer.toString(getMonth) + "/" + Integer.toString(getYear));
                afterCahnge();

            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv5.setBackgroundColor(Color.parseColor("#EA1E44"));
                tv4.setBackgroundColor(Color.WHITE);
                KEY2 = 2;
                et1.setText(month.get(getMonth - 1) + "-" + Integer.toString(getYear));
                afterCahnge();
            }
        });

        et1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (KEY2 == 2) {
                    final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int Year, int monthofYear, int dayofMonth) {
                            myCalenar.set(Calendar.YEAR, Year);
                            myCalenar.set(Calendar.MONTH, monthofYear);
                            myCalenar.set(Calendar.DAY_OF_MONTH, dayofMonth);
                            getDay = dayofMonth;
                            getMonth = monthofYear + 1;
                            getYear = Year;
                            et1.setText(month.get(monthofYear) + "/" + Year);
                        }
                    };
                    DatePickerDialog picker = new DatePickerDialog(menu6_record.this, date2, myCalenar.get(Calendar.YEAR), myCalenar.get(Calendar.MONTH), myCalenar.get(Calendar.DAY_OF_MONTH));
                    picker.setTitle("Calender");
                    picker.setMessage("Choose Month and Year");
                    picker.show();
                } else if (KEY2 == 1) {
                    final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int Year, int monthofYear, int dayofMonth) {
                            myCalenar.set(Calendar.YEAR, Year);
                            myCalenar.set(Calendar.MONTH, monthofYear);
                            myCalenar.set(Calendar.DAY_OF_MONTH, dayofMonth);
                            getDay = dayofMonth;
                            getMonth = monthofYear + 1;
                            getYear = Year;
                            et1.setText(dayofMonth + "/" + (monthofYear + 1) + "/" + Year);
                        }
                    };
                    DatePickerDialog picker = new DatePickerDialog(menu6_record.this, date2, myCalenar.get(Calendar.YEAR), myCalenar.get(Calendar.MONTH), myCalenar.get(Calendar.DAY_OF_MONTH));
                    picker.setTitle("Calender");
                    picker.setMessage("Choose A Date");
                    picker.show();
                }
            }
        });

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                afterCahnge();
            }
        });


    }

    public void afterCahnge() {

        detail = helper.getDetail(getDay, getMonth, getYear, KEY1, KEY2);

        DateList = detail.get(0);
        TimeList = detail.get(1);
        CategoryList_Source = detail.get(2);
        MethodList_Target = detail.get(3);
        AmountList = detail.get(4);
        DescriptionList = detail.get(5);
        UniqueIdList = detail.get(6);
        Method_IdList = detail.get(7);
        Category_Source_IdList = detail.get(8);

        RecyclerView recyclerView = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(menu6_record.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(new CustomAdapter(menu6_record.this, UniqueIdList, DateList, TimeList, AmountList, CategoryList_Source, MethodList_Target, DescriptionList, Category_Source_IdList, Method_IdList, KEY1));

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(menu6_record.this, MainActivity.class);
        finish();
        startActivity(i);
    }

}


