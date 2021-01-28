package com.nmakadiya.expensemanager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class menu5 extends AppCompatActivity {
    ArrayList<String> years = new ArrayList();
    ArrayList<String> Temp = new ArrayList();
    double temp;
    int thisyear = Calendar.getInstance().get(Calendar.YEAR);
    int month, year;
    int KEY;
    MyHelper helper = new MyHelper(menu5.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu5);

        final Spinner sp1 = findViewById(R.id.m5sp1);
        final Spinner sp2 = findViewById(R.id.m5sp2);
        final Spinner sp3 = findViewById(R.id.m5sp3);

        sp1.setSelection(3);
        sp2.setSelection(Calendar.getInstance().get(Calendar.MONTH));
        for (int i = thisyear; i >= 2011; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> spinlist = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years);
        sp3.setAdapter(spinlist);

        afterchange();

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                KEY = sp1.getSelectedItemPosition();
                if (KEY == 0) {
                    sp2.setVisibility(view.getVisibility());
                    sp3.setVisibility(view.getVisibility());
                } else if (KEY == 4) {
                    sp2.setVisibility(view.GONE);
                    sp3.setVisibility(view.GONE);
                } else {
                    sp2.setVisibility(view.GONE);
                    sp3.setVisibility(view.getVisibility());
                }

                afterchange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                afterchange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                afterchange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void afterchange() {
        String[] month1 = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Spinner sp1 = findViewById(R.id.m5sp1);
        Spinner sp2 = findViewById(R.id.m5sp2);
        Spinner sp3 = findViewById(R.id.m5sp3);
        KEY = sp1.getSelectedItemPosition();
        month = sp2.getSelectedItemPosition() + 1;
        year = Integer.parseInt(sp3.getSelectedItem().toString());
        ArrayList<String> m5rvList1 = new ArrayList();
        ArrayList<String> m5rvList2 = new ArrayList();
        ArrayList<ArrayList<String>> m5rvList4_Expense = new ArrayList();
        ArrayList<ArrayList<String>> m5rvList5_Income = new ArrayList();
        ArrayList<String> m5rvList3_Name = new ArrayList();

        if (KEY == 0) {
            for (int i = 1; i <= 31; i++) {
                m5rvList3_Name.add(Integer.toString(i) + "-" + month1[month - 1]);
                Temp = helper.categoryTotal(i, month, year, i, month, year, 1);
                m5rvList4_Expense.add(Temp);
                Temp = helper.categoryTotal(i, month, year, i, month, year, 2);
                m5rvList5_Income.add(Temp);
                temp = helper.getCustomTotal(i, month, year, i, month, year, 1);
                m5rvList1.add(Double.toString(temp));
                temp = helper.getCustomTotal(i, month, year, i, month, year, 2);
                m5rvList2.add(Double.toString(temp));

            }
        }

        if (KEY == 1) {
            for (int i = 1; i <= 12; i++) {
                for (int j = 1; j <= 31; j = j + 7) {
                    m5rvList3_Name.add(Integer.toString(j) + " to " + Integer.toString(j + 6) + " " + month1[i - 1]);
                    Temp = helper.categoryTotal(j, i, year, j + 6, i, year, 1);
                    m5rvList4_Expense.add(Temp);
                    Temp = helper.categoryTotal(j, i, year, j + 6, i, year, 2);
                    m5rvList5_Income.add(Temp);
                    temp = helper.getCustomTotal(j, i, year, j + 6, i, year, 1);
                    m5rvList1.add(Double.toString(temp));
                    temp = helper.getCustomTotal(j, i, year, j + 6, i, year, 2);
                    m5rvList2.add(Double.toString(temp));
                }
            }
        }

        if (KEY == 2) {
            for (int i = 1; i <= 12; i++) {
                m5rvList3_Name.add(Integer.toString(1) + " to " + Integer.toString(15) + " " + month1[i - 1]);
                Temp = helper.categoryTotal(1, i, year, 15, i, year, 1);
                m5rvList4_Expense.add(Temp);
                Temp = helper.categoryTotal(1, i, year, 15, i, year, 2);
                m5rvList5_Income.add(Temp);
                temp = helper.getCustomTotal(1, i, year, 15, i, year, 1);
                m5rvList1.add(Double.toString(temp));
                temp = helper.getCustomTotal(1, i, year, 15, i, year, 2);
                m5rvList2.add(Double.toString(temp));

                m5rvList3_Name.add(Integer.toString(16) + " to " + Integer.toString(31) + " " + month1[i - 1]);
                Temp = helper.categoryTotal(16, i, year, 31, i, year, 1);
                m5rvList4_Expense.add(Temp);
                Temp = helper.categoryTotal(16, i, year, 31, i, year, 2);
                m5rvList5_Income.add(Temp);
                temp = helper.getCustomTotal(16, i, year, 31, i, year, 1);
                m5rvList1.add(Double.toString(temp));
                temp = helper.getCustomTotal(16, i, year, 31, i, year, 2);
                m5rvList2.add(Double.toString(temp));

            }
        }

        if (KEY == 3) {
            for (int i = 1; i <= 12; i++) {
                m5rvList3_Name.add(month1[i - 1]);
                Temp = helper.categoryTotal(1, i, year, 31, i, year, 1);
                m5rvList4_Expense.add(Temp);
                Temp = helper.categoryTotal(1, i, year, 31, i, year, 2);
                m5rvList5_Income.add(Temp);
                temp = helper.getCustomTotal(1, i, year, 31, i, year, 1);
                m5rvList1.add(Double.toString(temp));
                temp = helper.getCustomTotal(1, i, year, 31, i, year, 2);
                m5rvList2.add(Double.toString(temp));
            }
        }

        if (KEY == 4) {
            for (int i = thisyear; i >= 2011; i--) {
                m5rvList3_Name.add(Integer.toString(i));
                Temp = helper.categoryTotal(1, 1, i, 31, 12, i, 1);
                m5rvList4_Expense.add(Temp);
                Temp = helper.categoryTotal(1, 1, i, 31, 12, i, 2);
                m5rvList5_Income.add(Temp);
                temp = helper.getCustomTotal(1, 1, i, 31, 12, i, 1);
                m5rvList1.add(Double.toString(temp));
                temp = helper.getCustomTotal(1, 1, i, 31, 12, i, 2);
                m5rvList2.add(Double.toString(temp));
            }
        }

        RecyclerView rv = findViewById(R.id.m5rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(menu5.this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(new CustomAdapter_menu5(menu5.this, m5rvList1, m5rvList2, m5rvList3_Name, m5rvList4_Expense, m5rvList5_Income));

    }


}
