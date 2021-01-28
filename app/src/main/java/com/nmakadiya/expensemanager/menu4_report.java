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

public class menu4_report extends AppCompatActivity {

    ArrayList<String> years = new ArrayList();
    ArrayList<String> Temp = new ArrayList();
    double temp;
    int thisyear = Calendar.getInstance().get(Calendar.YEAR);
    int month, year;
    int ExpenseCategory;
    int IncomeCategory;
    int KEY;
    MyHelper helper = new MyHelper(menu4_report.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu4_report);

        final Spinner sp1 = findViewById(R.id.m4sp1);
        final Spinner sp2 = findViewById(R.id.m4sp2);
        final Spinner sp3 = findViewById(R.id.m4sp3);
        final Spinner sp4 = findViewById(R.id.m4sp4);
        Spinner sp5 = findViewById(R.id.m4sp5);

        sp1.setSelection(3);
        sp2.setSelection(Calendar.getInstance().get(Calendar.MONTH));
        for (int i = thisyear; i >= 2011; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> spinlist = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years);
        sp3.setAdapter(spinlist);
        sp3.setSelection(0);
        sp4.setSelection(0);
        sp5.setSelection(1);

        afterchange_exchange();
        afterchange_income();


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

                afterchange_exchange();
                afterchange_income();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                afterchange_exchange();
                afterchange_income();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                afterchange_exchange();
                afterchange_income();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                afterchange_exchange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                afterchange_income();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void afterchange_exchange() {
        String[] month1 = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Spinner sp1 = findViewById(R.id.m4sp1);
        Spinner sp2 = findViewById(R.id.m4sp2);
        Spinner sp3 = findViewById(R.id.m4sp3);
        Spinner sp4 = findViewById(R.id.m4sp4);


        KEY = sp1.getSelectedItemPosition();
        month = sp2.getSelectedItemPosition() + 1;
        year = Integer.parseInt(sp3.getSelectedItem().toString());
        ExpenseCategory = sp4.getSelectedItemPosition();
        ArrayList<String> rv1List = new ArrayList();
        ArrayList<String> rv1NameList = new ArrayList();

        if (KEY == 0) {
            for (int i = 1; i <= 31; i++) {
                rv1NameList.add(Integer.toString(i) + "-" + month1[month - 1]);
                if (ExpenseCategory != 14) {
                    Temp = helper.categoryTotal(i, month, year, i, month, year, 1);
                    rv1List.add(Temp.get(ExpenseCategory));
                } else if (ExpenseCategory == 14) {
                    temp = helper.getCustomTotal(i, month, year, i, month, year, 1);
                    rv1List.add(Double.toString(temp));
                }
            }
        }

        if (KEY == 1) {
            for (int i = 1; i <= 12; i++) {
                for (int j = 1; j <= 31; j = j + 7) {
                    rv1NameList.add(Integer.toString(j) + " to " + Integer.toString(j + 6) + " " + month1[i - 1]);
                    if (ExpenseCategory != 14) {
                        Temp = helper.categoryTotal(j, i, year, j + 6, i, year, 1);
                        rv1List.add(Temp.get(ExpenseCategory));
                    } else if (ExpenseCategory == 14) {
                        temp = helper.getCustomTotal(j, i, year, j + 6, i, year, 1);
                        rv1List.add(Double.toString(temp));
                    }
                }
            }
        }

        if (KEY == 2) {
            for (int i = 1; i <= 12; i++) {
                rv1NameList.add(Integer.toString(1) + " to " + Integer.toString(15) + " " + month1[i - 1]);
                if (ExpenseCategory != 14) {
                    Temp = helper.categoryTotal(1, i, year, 15, i, year, 1);
                    rv1List.add(Temp.get(ExpenseCategory));
                } else if (ExpenseCategory == 14) {
                    temp = helper.getCustomTotal(1, i, year, 15, i, year, 1);
                    rv1List.add(Double.toString(temp));
                }

                rv1NameList.add(Integer.toString(16) + " to " + Integer.toString(31) + " " + month1[i - 1]);
                if (ExpenseCategory != 14) {
                    Temp = helper.categoryTotal(16, i, year, 31, i, year, 1);
                    rv1List.add(Temp.get(ExpenseCategory));
                } else if (ExpenseCategory == 14) {
                    temp = helper.getCustomTotal(16, i, year, 31, i, year, 1);
                    rv1List.add(Double.toString(temp));
                }
            }
        }

        if (KEY == 3) {
            for (int i = 1; i <= 12; i++) {
                rv1NameList.add(month1[i - 1]);
                if (ExpenseCategory != 14) {
                    Temp = helper.categoryTotal(1, i, year, 31, i, year, 1);
                    rv1List.add(Temp.get(ExpenseCategory));
                } else if (ExpenseCategory == 14) {
                    temp = helper.getCustomTotal(1, i, year, 31, i, year, 1);
                    rv1List.add(Double.toString(temp));
                }
            }
        }

        if (KEY == 4) {
            for (int i = thisyear; i >= 2011; i--) {
                rv1NameList.add(Integer.toString(i));
                if (ExpenseCategory != 14) {
                    Temp = helper.categoryTotal(1, 1, i, 31, 12, i, 1);
                    rv1List.add(Temp.get(ExpenseCategory));
                } else if (ExpenseCategory == 14) {
                    temp = helper.getCustomTotal(1, 1, i, 31, 12, i, 1);
                    rv1List.add(Double.toString(temp));
                }
            }
        }
        RecyclerView rv1 = findViewById(R.id.m4rv1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(menu4_report.this);
        rv1.setLayoutManager(layoutManager);
        rv1.setAdapter(new CustomAdapter_menu4(menu4_report.this, rv1List, rv1NameList, KEY));
    }

    public void afterchange_income() {
        String[] month1 = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Spinner sp1 = findViewById(R.id.m4sp1);
        Spinner sp2 = findViewById(R.id.m4sp2);
        Spinner sp3 = findViewById(R.id.m4sp3);
        Spinner sp5 = findViewById(R.id.m4sp5);


        KEY = sp1.getSelectedItemPosition();
        month = sp2.getSelectedItemPosition() + 1;
        year = Integer.parseInt(sp3.getSelectedItem().toString());
        IncomeCategory = sp5.getSelectedItemPosition();
        ArrayList<String> rv2List = new ArrayList();
        ArrayList<String> rv2NameList = new ArrayList();

        if (KEY == 0) {
            for (int i = 1; i <= 31; i++) {
                rv2NameList.add(Integer.toString(i) + "-" + month1[month - 1]);
                if (IncomeCategory != 10) {
                    Temp = helper.categoryTotal(i, month, year, i, month, year, 2);
                    rv2List.add(Temp.get(IncomeCategory));
                } else if (IncomeCategory == 10) {
                    temp = helper.getCustomTotal(i, month, year, i, month, year, 2);
                    rv2List.add(Double.toString(temp));
                }

            }
        }

        if (KEY == 1) {
            for (int i = 1; i <= 12; i++) {
                for (int j = 1; j <= 31; j = j + 7) {
                    if (j < 25) {
                        rv2NameList.add(Integer.toString(j) + " to " + Integer.toString(j + 6) + " " + month1[i - 1]);
                    } else {
                        rv2NameList.add(Integer.toString(j) + " to 31 " + month1[i - 1]);
                    }
                    if (IncomeCategory != 10) {
                        Temp = helper.categoryTotal(j, i, year, j + 6, i, year, 2);
                        rv2List.add(Temp.get(IncomeCategory));
                    } else if (IncomeCategory == 10) {
                        temp = helper.getCustomTotal(j, i, year, j + 6, i, year, 2);
                        rv2List.add(Double.toString(temp));
                    }
                }
            }
        }

        if (KEY == 2) {
            for (int i = 1; i <= 12; i++) {
                rv2NameList.add(Integer.toString(1) + " to " + Integer.toString(15) + " " + month1[i - 1]);
                if (IncomeCategory != 10) {
                    Temp = helper.categoryTotal(1, i, year, 15, i, year, 2);
                    rv2List.add(Temp.get(IncomeCategory));
                } else if (IncomeCategory == 10) {
                    temp = helper.getCustomTotal(1, i, year, 15, i, year, 2);
                    rv2List.add(Double.toString(temp));
                }

                rv2NameList.add(Integer.toString(16) + " to " + Integer.toString(31) + " " + month1[i - 1]);
                if (IncomeCategory != 10) {
                    Temp = helper.categoryTotal(16, i, year, 31, i, year, 2);
                    rv2List.add(Temp.get(IncomeCategory));
                } else if (IncomeCategory == 10) {
                    temp = helper.getCustomTotal(16, i, year, 31, i, year, 2);
                    rv2List.add(Double.toString(temp));
                }
            }
        }

        if (KEY == 3) {
            for (int i = 1; i <= 12; i++) {
                rv2NameList.add(month1[i - 1]);
                if (IncomeCategory != 10) {
                    Temp = helper.categoryTotal(1, i, year, 31, i, year, 2);
                    rv2List.add(Temp.get(IncomeCategory));
                } else if (IncomeCategory == 10) {
                    temp = helper.getCustomTotal(1, i, year, 31, i, year, 2);
                    rv2List.add(Double.toString(temp));
                }
            }
        }

        if (KEY == 4) {
            for (int i = thisyear; i >= 2011; i--) {
                rv2NameList.add(Integer.toString(i));
                if (IncomeCategory != 10) {
                    Temp = helper.categoryTotal(1, 1, i, 31, 12, i, 2);
                    rv2List.add(Temp.get(IncomeCategory));
                } else if (IncomeCategory == 10) {
                    temp = helper.getCustomTotal(1, 1, i, 31, 12, i, 2);
                    rv2List.add(Double.toString(temp));
                }
            }
        }
        RecyclerView rv1 = findViewById(R.id.m4rv2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(menu4_report.this);
        rv1.setLayoutManager(layoutManager);
        rv1.setAdapter(new CustomAdapter_menu4(menu4_report.this, rv2List, rv2NameList, KEY));
    }

  /*  @Override
    public void onBackPressed(){
        Intent i=new Intent(menu4_report.this,MainActivity.class);
        finish();
        startActivity(i);
    }*/


}
