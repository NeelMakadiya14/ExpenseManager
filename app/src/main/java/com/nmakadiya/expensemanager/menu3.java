package com.nmakadiya.expensemanager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class menu3 extends AppCompatActivity {

    ArrayList<String> years = new ArrayList();
    ArrayList<String> Temp = new ArrayList();
    double temp;
    int thisyear = Calendar.getInstance().get(Calendar.YEAR);
    int month, year;
    int KEY, ExpenseCategory, IncomeCategory, ChartKey = 1, TypeKey = 1;
    String ExpenseCategoryName, IncomeCategoryName;
    ArrayAdapter<String> sp4adapter, sp5adapter;
    MyHelper helper = new MyHelper(menu3.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu3);

        final Spinner sp1 = findViewById(R.id.m3sp1);
        final Spinner sp2 = findViewById(R.id.m3sp2);
        final Spinner sp3 = findViewById(R.id.m3sp3);
        final Spinner sp4 = findViewById(R.id.m3sp4);
        final Spinner sp5 = findViewById(R.id.m3sp5);
        final TextView tv1 = findViewById(R.id.m3tv1);
        final TextView tv2 = findViewById(R.id.m3tv2);
        final TextView tv3 = findViewById(R.id.m3tv3);
        final TextView tv4 = findViewById(R.id.m3tv4);

        final HorizontalBarChart barChart1 = findViewById(R.id.m3barchart1);
        final HorizontalBarChart barChart2 = findViewById(R.id.m3barchart2);
        final PieChart pieChart1 = findViewById(R.id.m3pie1);
        final PieChart pieChart2 = findViewById(R.id.m3pie2);

        sp1.setSelection(3);
        sp2.setSelection(Calendar.getInstance().get(Calendar.MONTH));
        for (int i = thisyear; i >= 2011; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> spinlist = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years);
        sp3.setAdapter(spinlist);

        String[] sp4item = getResources().getStringArray(R.array.m4_types);
        String[] sp5item = getResources().getStringArray(R.array.m4_types2);
        sp4adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sp4item);
        sp5adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sp5item);
        sp4.setAdapter(sp4adapter);
        sp5.setAdapter(sp5adapter);
        sp4.setSelection(0);
        sp5.setSelection(1);

        afterchange1();

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
                Sp4itemupdate();
                afterchange1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                afterchange1();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                afterchange1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                afterchange1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                afterchange1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pieChart1.setVisibility(view.GONE);
                pieChart2.setVisibility(view.GONE);
                barChart1.setVisibility(view.getVisibility());
                barChart2.setVisibility(view.getVisibility());
                tv3.setBackgroundColor(Color.parseColor("#EA1E44"));
                tv4.setBackgroundColor(Color.WHITE);
                ChartKey = 1;
                afterchange1();
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barChart1.setVisibility(view.GONE);
                barChart2.setVisibility(view.GONE);
                pieChart1.setVisibility(view.getVisibility());
                pieChart2.setVisibility(view.getVisibility());
                tv4.setBackgroundColor(Color.parseColor("#EA1E44"));
                tv3.setBackgroundColor(Color.WHITE);
                ChartKey = 2;
                afterchange1();
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setBackgroundColor(Color.parseColor("#EA1E44"));
                tv2.setBackgroundColor(Color.WHITE);
                TypeKey = 1;
                sp5.setVisibility(view.getVisibility());
                Sp4itemupdate();
                afterchange1();

            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv2.setBackgroundColor(Color.parseColor("#EA1E44"));
                tv1.setBackgroundColor(Color.WHITE);
                TypeKey = 2;
                sp5.setVisibility(view.GONE);
                Sp4itemupdate();
                afterchange1();
            }
        });
    }

    public void afterchange1() {
        if (TypeKey == 1) {
            ByDate();
        } else if (TypeKey == 2) {
            ByCategory();
        }
    }

    public void ByDate() {
        Spinner sp1 = findViewById(R.id.m3sp1);
        Spinner sp2 = findViewById(R.id.m3sp2);
        Spinner sp3 = findViewById(R.id.m3sp3);
        Spinner sp4 = findViewById(R.id.m3sp4);
        Spinner sp5 = findViewById(R.id.m3sp5);

        String[] month1 = getResources().getStringArray(R.array.month);

        KEY = sp1.getSelectedItemPosition();
        month = sp2.getSelectedItemPosition() + 1;
        year = Integer.parseInt(sp3.getSelectedItem().toString());
        ExpenseCategory = sp4.getSelectedItemPosition();
        ExpenseCategoryName = sp4.getSelectedItem().toString();
        IncomeCategory = sp5.getSelectedItemPosition();
        IncomeCategoryName = sp5.getSelectedItem().toString();
        ArrayList<String> Yaxis_chart1 = new ArrayList();
        ArrayList<String> Yaxis_chart2 = new ArrayList();
        ArrayList<String> Xaxis = new ArrayList();
        double Expense, Income;

        if (KEY == 0) {
            if (ExpenseCategory != 14) {
                Expense = Double.parseDouble(helper.categoryTotal(1, month, year, 31, month, year, 1).get(ExpenseCategory));
            } else {
                Expense = helper.getCustomTotal(1, month, year, 31, month, year, 1);
            }
            if (IncomeCategory != 10) {
                Income = Double.parseDouble(helper.categoryTotal(1, month, year, 31, month, year, 2).get(IncomeCategory));
            } else {
                Income = helper.getCustomTotal(1, month, year, 31, month, year, 2);
            }
        } else if (KEY == 4) {

            if (ExpenseCategory != 14) {
                Expense = Double.parseDouble(helper.categoryTotal(1, 1, 2000, 31, 12, thisyear, 1).get(ExpenseCategory));
            } else {
                Expense = helper.getTotal(1);
            }
            if (IncomeCategory != 10) {
                Income = Double.parseDouble(helper.categoryTotal(1, 1, 2000, 31, 12, thisyear, 2).get(IncomeCategory));
            } else {
                Income = helper.getTotal(2);
            }

        } else {

            if (ExpenseCategory != 14) {
                Expense = Double.parseDouble(helper.categoryTotal(1, 1, year, 31, 12, year, 1).get(ExpenseCategory));
            } else {
                Expense = helper.getCustomTotal(1, 1, year, 31, 12, year, 1);
            }
            if (IncomeCategory != 10) {
                Income = Double.parseDouble(helper.categoryTotal(1, 1, year, 31, 12, year, 2).get(IncomeCategory));
            } else {
                Income = helper.getCustomTotal(1, 1, year, 31, 12, year, 2);
            }

        }


        if (KEY == 0) {

            for (int i = 1; i <= 31; i++) {
                Xaxis.add(Integer.toString(i) + "-" + month1[month - 1]);
                if (ExpenseCategory != 14) {
                    Temp = helper.categoryTotal(i, month, year, i, month, year, 1);
                    Yaxis_chart1.add(Temp.get(ExpenseCategory));
                } else if (ExpenseCategory == 14) {
                    temp = helper.getCustomTotal(i, month, year, i, month, year, 1);
                    Yaxis_chart1.add(Double.toString(temp));
                }
                if (IncomeCategory != 10) {
                    Temp = helper.categoryTotal(i, month, year, i, month, year, 2);
                    Yaxis_chart2.add(Temp.get(IncomeCategory));
                } else if (IncomeCategory == 10) {
                    temp = helper.getCustomTotal(i, month, year, i, month, year, 2);
                    Yaxis_chart2.add(Double.toString(temp));
                }
            }
        }

        if (KEY == 1) {
            for (int i = 1; i <= 12; i++) {
                for (int j = 1; j <= 31; j = j + 7) {
                    Xaxis.add(Integer.toString(j) + " to " + Integer.toString(j + 6) + " " + month1[i - 1]);
                    if (ExpenseCategory != 14) {
                        Temp = helper.categoryTotal(j, i, year, j + 6, i, year, 1);
                        Yaxis_chart1.add(Temp.get(ExpenseCategory));
                    } else if (ExpenseCategory == 14) {
                        temp = helper.getCustomTotal(j, i, year, j + 6, i, year, 1);
                        Yaxis_chart1.add(Double.toString(temp));
                    }
                    if (IncomeCategory != 10) {
                        Temp = helper.categoryTotal(j, i, year, j + 6, i, year, 2);
                        Yaxis_chart2.add(Temp.get(IncomeCategory));
                    } else if (IncomeCategory == 10) {
                        temp = helper.getCustomTotal(j, i, year, j + 6, i, year, 2);
                        Yaxis_chart2.add(Double.toString(temp));
                    }
                }
            }
        }

        if (KEY == 2) {
            for (int i = 1; i <= 12; i++) {
                Xaxis.add(Integer.toString(1) + " to " + Integer.toString(15) + " " + month1[i - 1]);
                if (ExpenseCategory != 14) {
                    Temp = helper.categoryTotal(1, i, year, 15, i, year, 1);
                    Yaxis_chart1.add(Temp.get(ExpenseCategory));
                } else if (ExpenseCategory == 14) {
                    temp = helper.getCustomTotal(1, i, year, 15, i, year, 1);
                    Yaxis_chart1.add(Double.toString(temp));
                }
                if (IncomeCategory != 10) {
                    Temp = helper.categoryTotal(1, i, year, 15, i, year, 2);
                    Yaxis_chart2.add(Temp.get(IncomeCategory));
                } else if (IncomeCategory == 10) {
                    temp = helper.getCustomTotal(1, i, year, 15, i, year, 2);
                    Yaxis_chart2.add(Double.toString(temp));
                }

                Xaxis.add(Integer.toString(16) + " to " + Integer.toString(31) + " " + month1[i - 1]);
                if (ExpenseCategory != 14) {
                    Temp = helper.categoryTotal(16, i, year, 31, i, year, 1);
                    Yaxis_chart1.add(Temp.get(ExpenseCategory));
                } else if (ExpenseCategory == 14) {
                    temp = helper.getCustomTotal(16, i, year, 31, i, year, 1);
                    Yaxis_chart1.add(Double.toString(temp));
                }
                if (IncomeCategory != 10) {
                    Temp = helper.categoryTotal(16, i, year, 31, i, year, 2);
                    Yaxis_chart2.add(Temp.get(IncomeCategory));
                } else if (IncomeCategory == 10) {
                    temp = helper.getCustomTotal(16, i, year, 31, i, year, 2);
                    Yaxis_chart2.add(Double.toString(temp));
                }
            }
        }

        if (KEY == 3) {
            for (int i = 1; i <= 12; i++) {
                Xaxis.add(month1[i - 1]);
                if (ExpenseCategory != 14) {
                    Temp = helper.categoryTotal(1, i, year, 31, i, year, 1);
                    Yaxis_chart1.add(Temp.get(ExpenseCategory));
                } else if (ExpenseCategory == 14) {
                    temp = helper.getCustomTotal(1, i, year, 31, i, year, 1);
                    Yaxis_chart1.add(Double.toString(temp));
                }
                if (IncomeCategory != 10) {
                    Temp = helper.categoryTotal(1, i, year, 31, i, year, 2);
                    Yaxis_chart2.add(Temp.get(IncomeCategory));
                } else if (IncomeCategory == 10) {
                    temp = helper.getCustomTotal(1, i, year, 31, i, year, 2);
                    Yaxis_chart2.add(Double.toString(temp));
                }
            }
        }

        if (KEY == 4) {
            for (int i = thisyear; i >= 2011; i--) {
                Xaxis.add(Integer.toString(i));
                if (ExpenseCategory != 14) {
                    Temp = helper.categoryTotal(1, 1, i, 31, 12, i, 1);
                    Yaxis_chart1.add(Temp.get(ExpenseCategory));
                } else if (ExpenseCategory == 14) {
                    temp = helper.getCustomTotal(1, 1, i, 31, 12, i, 1);
                    Yaxis_chart1.add(Double.toString(temp));
                }
                if (IncomeCategory != 10) {
                    Temp = helper.categoryTotal(1, 1, i, 31, 12, i, 2);
                    Yaxis_chart2.add(Temp.get(IncomeCategory));
                } else if (IncomeCategory == 10) {
                    temp = helper.getCustomTotal(1, 1, i, 31, 12, i, 2);
                    Yaxis_chart2.add(Double.toString(temp));
                }
            }
        }

        if (ChartKey == 1) {
            HorizontalBarChart barChart1 = findViewById(R.id.m3barchart1);
            HorizontalBarChart barChart2 = findViewById(R.id.m3barchart2);

            ArrayList<BarEntry> Ybar1 = new ArrayList();
            ArrayList<BarEntry> Ybar2 = new ArrayList();

            for (int i = 0; i < Yaxis_chart1.size(); i++) {
                Ybar1.add(new BarEntry(Float.parseFloat(Yaxis_chart1.get(i)), i));
            }

            for (int i = 0; i < Yaxis_chart2.size(); i++) {
                Ybar2.add(new BarEntry(Float.parseFloat(Yaxis_chart2.get(i)), i));
            }

            BarDataSet barDataSet1 = new BarDataSet(Ybar1, "Date Wise Expense of " + ExpenseCategoryName);
            BarData barData1 = new BarData(Xaxis, barDataSet1);
            barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
            barChart1.setData(barData1);
            barChart1.animateY(500);
            barChart1.setDescription("Total Expense by " + ExpenseCategoryName + " in this Period=" + Expense);

            BarDataSet barDataSet2 = new BarDataSet(Ybar2, "Date Wise Income of " + IncomeCategoryName);
            BarData barData2 = new BarData(Xaxis, barDataSet2);
            barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
            barChart2.setData(barData2);
            barChart2.animateY(500);
            barChart2.setDescription("Total Income by " + IncomeCategoryName + " in this Period=" + Income);
        } else if (ChartKey == 2) {
            PieChart pieChart1 = findViewById(R.id.m3pie1);
            PieChart pieChart2 = findViewById(R.id.m3pie2);

            ArrayList<Entry> FinalY1 = new ArrayList();
            ArrayList<Entry> FinalY2 = new ArrayList();
            ArrayList<String> FinalX1 = new ArrayList();
            ArrayList<String> FinalX2 = new ArrayList();

            for (int i = 0; i < Yaxis_chart1.size(); i++) {
                if (Double.parseDouble(Yaxis_chart1.get(i)) != 0.0) {
                    FinalY1.add(new Entry(Float.parseFloat(Yaxis_chart1.get(i)), i));
                    FinalX1.add(Xaxis.get(i));
                }
            }
            for (int i = 0; i < Yaxis_chart2.size(); i++) {
                if (Double.parseDouble(Yaxis_chart2.get(i)) != 0.0) {
                    FinalY2.add(new Entry(Float.parseFloat(Yaxis_chart2.get(i)), i));
                    FinalX2.add(Xaxis.get(i));
                }
            }

            PieDataSet dataSet1 = new PieDataSet(FinalY1, " ");
            PieData Data1 = new PieData(FinalX1, dataSet1);
            Data1.setValueFormatter(new PercentFormatter());
            dataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
            pieChart1.setData(Data1);
            pieChart1.animateXY(500, 500);
            pieChart1.setCenterText("Date Wise Expense of " + ExpenseCategoryName + "\r\rTotal=" + Expense);
            pieChart1.setDragDecelerationEnabled(true);
            pieChart1.setUsePercentValues(true);
            pieChart1.setDescription("Total Expense by " + ExpenseCategoryName + " in this Period=" + Expense);


            PieDataSet dataSet2 = new PieDataSet(FinalY2, " ");
            PieData Data2 = new PieData(FinalX2, dataSet2);
            Data2.setValueFormatter(new PercentFormatter());
            dataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
            pieChart2.setData(Data2);
            pieChart2.animateXY(500, 500);
            pieChart2.setCenterText("Date Wise Income of " + IncomeCategoryName + "\r\rTotal=" + Income);
            pieChart2.setDragDecelerationEnabled(true);
            pieChart2.setUsePercentValues(true);
            pieChart2.setDescription("Total Income by " + IncomeCategoryName + " in this Period=" + Income);
        }
    }

    public void ByCategory() {
        Spinner sp1 = findViewById(R.id.m3sp1);
        Spinner sp2 = findViewById(R.id.m3sp2);
        Spinner sp3 = findViewById(R.id.m3sp3);
        Spinner sp4 = findViewById(R.id.m3sp4);

        String[] month1 = getResources().getStringArray(R.array.month);

        KEY = sp1.getSelectedItemPosition();
        month = sp2.getSelectedItemPosition() + 1;
        year = Integer.parseInt(sp3.getSelectedItem().toString());
        int sp4key = sp4.getSelectedItemPosition();

        if (KEY == 0) {
            showType2Chart(sp4key + 1, month, year, sp4key + 1, month, year, helper);
        } else if (KEY == 1) {
            int Month = sp4key / 5;
            Month = Month + 1;
            int sday = 1, eday = 1;

            switch (sp4key % 5) {
                case 0:
                    sday = 1;
                    eday = 7;
                    break;
                case 1:
                    sday = 8;
                    eday = 14;
                    break;
                case 2:
                    sday = 15;
                    eday = 21;
                    break;
                case 3:
                    sday = 22;
                    eday = 28;
                    break;
                case 4:
                    sday = 29;
                    eday = 31;
                    break;
            }
            showType2Chart(sday, Month, year, eday, Month, year, helper);
        } else if (KEY == 2) {
            int Month = sp4key / 2;
            Month = Month + 1;
            int sday = 1, eday = 1;

            if (sp4key % 2 == 0) {
                sday = 1;
                eday = 15;
            } else {
                sday = 16;
                eday = 31;
            }
            showType2Chart(sday, Month, year, eday, Month, year, helper);
        } else if (KEY == 3) {
            int Month = sp4key + 1;
            showType2Chart(1, Month, year, 31, Month, year, helper);
        } else if (KEY == 4) {
            int YEAR = 2019 - sp4key;
            showType2Chart(1, 1, YEAR, 31, 12, YEAR, helper);
        }
    }

    public void Sp4itemupdate() {

        Spinner sp4 = findViewById(R.id.m3sp4);
        Spinner sp5 = findViewById(R.id.m3sp5);

        ArrayList<String> spinData = new ArrayList();
        String[] month1 = getResources().getStringArray(R.array.month);

        if (KEY == 0) {
            for (int i = 1; i <= 31; i++) {
                spinData.add(Integer.toString(i) + "-" + month1[month - 1]);
            }
        }
        if (KEY == 1) {
            for (int i = 1; i <= 12; i++) {
                for (int j = 1; j <= 31; j = j + 7) {
                    spinData.add(Integer.toString(j) + " to " + Integer.toString(j + 6) + " " + month1[i - 1]);
                }
            }
        }
        if (KEY == 2) {
            for (int i = 1; i <= 12; i++) {
                spinData.add(Integer.toString(1) + " to " + Integer.toString(15) + " " + month1[i - 1]);
                spinData.add(Integer.toString(16) + " to " + Integer.toString(31) + " " + month1[i - 1]);
            }
        }
        if (KEY == 3) {
            for (int i = 1; i <= 12; i++) {
                spinData.add(month1[i - 1]);
            }
        }
        if (KEY == 4) {
            for (int i = thisyear; i >= 2011; i--) {
                spinData.add(Integer.toString(i));
            }
        }

        ArrayAdapter<String> type2sp4 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinData);

        if (TypeKey == 1) {
            sp4.setAdapter(sp4adapter);
            sp5.setAdapter(sp5adapter);
        } else if (TypeKey == 2) {
            sp4.setAdapter(type2sp4);
        }
    }

    public void showType2Chart(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, MyHelper myHelper) {

        Spinner spinner = findViewById(R.id.m3sp4);
        String DateName = spinner.getSelectedItem().toString();

        double customExpense = 0;
        double customIncome = 0;

        customExpense = myHelper.getCustomTotal(startDay, startMonth, startYear, endDay, endMonth, endYear, 1);
        customIncome = myHelper.getCustomTotal(startDay, startMonth, startYear, endDay, endMonth, endYear, 2);
        ArrayList<String> listExpense = myHelper.categoryTotal(startDay, startMonth, startYear, endDay, endMonth, endYear, 1);
        ArrayList<String> listIncome = myHelper.categoryTotal(startDay, startMonth, startYear, endDay, endMonth, endYear, 2);

        ArrayList<String> ExpenseName = new ArrayList<String>();
        ArrayList<String> IncomeName = new ArrayList<String>();

        ExpenseName.add("Clothing");
        ExpenseName.add("Drinks");
        ExpenseName.add("Food");
        ExpenseName.add("Fuel");
        ExpenseName.add("Health");
        ExpenseName.add("Fun");
        ExpenseName.add("Transport");
        ExpenseName.add("Restaurant");
        ExpenseName.add("Give to Someone");
        ExpenseName.add("Gift");
        ExpenseName.add("Other1");
        ExpenseName.add("Other2");
        ExpenseName.add("Other3");
        ExpenseName.add("Other4");

        IncomeName.add("Loan");
        IncomeName.add("Salary");
        IncomeName.add("Sales");
        IncomeName.add("Owe From Other");
        IncomeName.add("Winning Price");
        IncomeName.add("Gift");
        IncomeName.add("Other1");
        IncomeName.add("Other2");
        IncomeName.add("Other3");
        IncomeName.add("Other4");

        if (ChartKey == 1) {
            HorizontalBarChart barChart1 = findViewById(R.id.m3barchart1);
            HorizontalBarChart barChart2 = findViewById(R.id.m3barchart2);

            ArrayList<BarEntry> BarY1 = new ArrayList();
            ArrayList<BarEntry> BarY2 = new ArrayList();

            for (int i = 0; i < ExpenseName.size(); i++) {
                BarY1.add(new BarEntry(Float.parseFloat(listExpense.get(i)), i));
            }
            for (int i = 0; i < IncomeName.size(); i++) {
                BarY2.add(new BarEntry(Float.parseFloat(listIncome.get(i)), i));
            }
            BarDataSet barDataSet1 = new BarDataSet(BarY1, "Category Wise Expense of " + DateName);
            BarData barData1 = new BarData(ExpenseName, barDataSet1);
            barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
            barChart1.setData(barData1);
            barChart1.animateY(500);
            barChart1.setDescription("Total Expense in this Period=" + customExpense);

            BarDataSet barDataSet2 = new BarDataSet(BarY2, "Category Wise Income of " + DateName);
            BarData barData2 = new BarData(IncomeName, barDataSet2);
            barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
            barChart2.setData(barData2);
            barChart2.animateY(500);
            barChart2.setDescription("Total Income in this Period=" + customIncome);

        } else if (ChartKey == 2) {

            PieChart pieChart = findViewById(R.id.m3pie1);
            PieChart pieChart1 = findViewById(R.id.m3pie2);

            ArrayList<String> FinalExpenseName = new ArrayList<String>();
            ArrayList<String> FinalIncomeName = new ArrayList<String>();
            ArrayList<String> FinalListIncome = new ArrayList<String>();
            ArrayList<String> FinalListExpense = new ArrayList<String>();


            for (int i = 0; i < 14; i++) {
                if (Double.parseDouble(listExpense.get(i)) != 0) {
                    FinalExpenseName.add(ExpenseName.get(i));
                    FinalListExpense.add(listExpense.get(i));
                }
            }
            int Expense_count = FinalExpenseName.size();

            for (int i = 0; i < 10; i++) {
                if (Double.parseDouble(listIncome.get(i)) != 0) {
                    FinalIncomeName.add(IncomeName.get(i));
                    FinalListIncome.add(listIncome.get(i));
                }
            }
            int Income_count = FinalIncomeName.size();

            ArrayList<Entry> Yaxis = new ArrayList();
            ArrayList<String> Xaxis = new ArrayList();

            for (int i = 0; i < Expense_count; i++) {
                Yaxis.add(new Entry(Float.valueOf(FinalListExpense.get(i)), i));
                Xaxis.add(FinalExpenseName.get(i));
            }

            PieDataSet dataSet = new PieDataSet(Yaxis, " ");
            PieData Data = new PieData(Xaxis, dataSet);
            Data.setValueFormatter(new PercentFormatter());
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieChart.setData(Data);
            pieChart.animateXY(500, 500);
            pieChart.setCenterText("Category Wise Expense of " + DateName + "\r\nTotal Expense=" + customExpense);
            pieChart.setDragDecelerationEnabled(true);
            pieChart.setUsePercentValues(true);
            pieChart.setDescription("Total Expense in this Period=" + customExpense);


            ArrayList<Entry> Yaxis1 = new ArrayList();
            ArrayList<String> Xaxis1 = new ArrayList();
            for (int i = 0; i < Income_count; i++) {
                Yaxis1.add(new Entry(Float.valueOf(FinalListIncome.get(i)), i));
                Xaxis1.add(FinalIncomeName.get(i));
            }

            PieDataSet dataSet1 = new PieDataSet(Yaxis1, " ");
            PieData Data1 = new PieData(Xaxis1, dataSet1);
            Data1.setValueFormatter(new PercentFormatter());
            dataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
            pieChart1.setData(Data1);
            pieChart1.animateXY(500, 500);
            pieChart1.setCenterText("Category Wise Income of " + DateName + "\r\nTotal Income=" + customIncome);
            pieChart1.setDragDecelerationEnabled(true);
            pieChart1.setUsePercentValues(true);
            pieChart1.setDescription("Total Income in this Period=" + customIncome);
        }


    }

}

