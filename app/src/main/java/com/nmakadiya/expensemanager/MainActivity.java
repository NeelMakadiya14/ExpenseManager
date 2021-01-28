package com.nmakadiya.expensemanager;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static int SignInKey = 0;
    int startDay, startMonth, startYear, endDay, endMonth, endYear;
    GoogleSignInClient mGoogleSignInClient;
    String id = "123";
    String name = "Expense Manager";
    Boolean SignOutShow;
    private MyHelper myHelper = new MyHelper(MainActivity.this);
    private AdView mAdView;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN = 2;
    private String TAG = "MainActivity";

    public static void upload(Context context, String personId) {

        MyHelper myHelper = new MyHelper(context);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(personId);
        myRef.keepSynced(true);
        myRef.setValue(null);
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM EXPENSE_TABLE", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, Object> result = new HashMap<>();
                result.put("Day", cursor.getInt(1));
                result.put("Month", cursor.getInt(2));
                result.put("Year", cursor.getInt(3));
                result.put("Time", cursor.getString(4));
                result.put("TypeId", cursor.getInt(5));
                result.put("TypeName", cursor.getString(6));
                result.put("MethodId", cursor.getInt(7));
                result.put("MethodName", cursor.getString(8));
                result.put("Amount", cursor.getDouble(9));
                result.put("Description", cursor.getString(10));

                String key = Integer.toString(cursor.getInt(0));
                HashMap<String, Object> childUpdate = new HashMap<>();
                childUpdate.put("raw" + key, result);
                myRef.child("Table-1").updateChildren(childUpdate);
            } while (cursor.moveToNext());
        }

        Cursor cursor1 = db.rawQuery("SELECT * FROM INCOME_TABLE", null);
        if (cursor1.moveToFirst()) {
            do {
                HashMap<String, Object> result = new HashMap<>();
                result.put("Day", cursor1.getInt(1));
                result.put("Month", cursor1.getInt(2));
                result.put("Year", cursor1.getInt(3));
                result.put("Time", cursor1.getString(4));
                result.put("TypeId", cursor1.getInt(5));
                result.put("TypeName", cursor1.getString(6));
                result.put("MethodId", cursor1.getInt(7));
                result.put("MethodName", cursor1.getString(8));
                result.put("Amount", cursor1.getDouble(9));
                result.put("Description", cursor1.getString(10));

                String key = Integer.toString(cursor1.getInt(0));
                HashMap<String, Object> childUpdate = new HashMap<>();
                childUpdate.put("raw" + key, result);
                myRef.child("Table-2").updateChildren(childUpdate);
            } while (cursor1.moveToNext());
        }

        Cursor cursor2 = db.rawQuery("SELECT * FROM TRANSFER", null);
        if (cursor2.moveToFirst()) {
            do {
                HashMap<String, Object> result = new HashMap<>();
                result.put("Day", cursor2.getInt(1));
                result.put("Month", cursor2.getInt(2));
                result.put("Year", cursor2.getInt(3));
                result.put("Time", cursor2.getString(4));
                result.put("SourceId", cursor2.getInt(5));
                result.put("SourceName", cursor2.getString(6));
                result.put("TargetId", cursor2.getInt(7));
                result.put("TargetName", cursor2.getString(8));
                result.put("Amount", cursor2.getDouble(9));
                result.put("Description", cursor2.getString(10));

                String key = Integer.toString(cursor2.getInt(0));
                HashMap<String, Object> childUpdate = new HashMap<>();
                childUpdate.put("raw" + key, result);
                myRef.child("Table-3").updateChildren(childUpdate);
            } while (cursor2.moveToNext());
        }

        Cursor cursor3 = db.rawQuery("SELECT * FROM BALANCE", null);
        if (cursor3.moveToFirst()) {
            do {
                HashMap<String, Object> result = new HashMap<>();
                result.put("Account", cursor3.getInt(1));
                result.put("Wallet", cursor3.getInt(2));
                result.put("Cash", cursor3.getInt(3));

                String key = Integer.toString(cursor3.getInt(0));
                HashMap<String, Object> childUpdate = new HashMap<>();
                childUpdate.put("raw" + key, result);
                myRef.child("Table-4").updateChildren(childUpdate);
            } while (cursor3.moveToNext());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String query = "";
        if (getIntent().getAction() != null && getIntent().getAction().equals("com.google.android.gms.actions.SEARCH_ACTION")) {
            query = getIntent().getStringExtra(SearchManager.QUERY);

            if (query.charAt(0) == 'c' || query.charAt(0) == 'C') {
                Intent i = new Intent(MainActivity.this, menu3.class);
                startActivity(i);
            } else if ((query.charAt(0) == 'a' || query.charAt(0) == 'A') && (query.charAt(1) == 'n' || query.charAt(1) == 'N')) {
                Intent i = new Intent(MainActivity.this, menu4_report.class);
                startActivity(i);
            } else if ((query.charAt(0) == 'r' || query.charAt(0) == 'R') && (query.charAt(1) == 'e' || query.charAt(1) == 'E') && (query.charAt(2) == 'p' || query.charAt(2) == 'P')) {
                Intent i = new Intent(MainActivity.this, menu5.class);
                startActivity(i);
            } else if ((query.charAt(0) == 'r' || query.charAt(0) == 'R') && (query.charAt(1) == 'e' || query.charAt(1) == 'E') && (query.charAt(2) == 'c' || query.charAt(2) == 'C')) {
                Intent i = new Intent(MainActivity.this, menu6_record.class);
                finish();
                startActivity(i);
            } else if ((query.charAt(0) == 'a' || query.charAt(0) == 'A') && (query.charAt(1) == 'd' || query.charAt(1) == 'D')) {
                Intent i = new Intent(MainActivity.this, Edit.class);
                // i.putExtra("query",query);
                startActivity(i);
            }
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        //1)Open google-services.json file -> client -> oauth_client -> client_id
        //2) Copy this client ID and hardcode this .requestIdToken("your ID")
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("138037404034-dk0i7dlr1p6hsc4gi496ogdsl425aths.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent i = new Intent(MainActivity.this, Edit.class);
                finish();
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View HeaderView = navigationView.getHeaderView(0);

        SignInButton signInButton = HeaderView.findViewById(R.id.NHGSignIN);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        TextView tv1 = findViewById(R.id.maintv1);
        TextView tv2 = findViewById(R.id.maintv2);
        TextView tv3 = findViewById(R.id.maintv3);
        TextView tv4 = findViewById(R.id.maintv4);
        TextView tv5 = findViewById(R.id.maintv5);
        final EditText et6 = findViewById(R.id.et6);
        final EditText et7 = findViewById(R.id.et7);
        final EditText et8 = findViewById(R.id.et8);
        final EditText et9 = findViewById(R.id.et9);
        final PieChart pieChart = findViewById(R.id.pie);
        final PieChart pieChart1 = findViewById(R.id.pie1);


        double TotalExpense = myHelper.getTotal(1);
        double TotalIncome = myHelper.getTotal(2);
        double TotalWallet = myHelper.getTotalWallet();
        double TotalAccount = myHelper.getTotalAccount();
        double TotalCash = myHelper.getTotalCash();


        tv1.setText(Double.toString(TotalExpense));
        tv2.setText(Double.toString(TotalIncome));
        tv3.setText(Double.toString(TotalAccount));
        tv4.setText(Double.toString(TotalCash));
        tv5.setText(Double.toString(TotalWallet));


        final Calendar myCalenar = Calendar.getInstance();
        startDay = myCalenar.get(Calendar.DAY_OF_MONTH);
        startMonth = myCalenar.get(Calendar.MONTH);
        startYear = myCalenar.get(Calendar.YEAR);
        endMonth = startMonth + 1;
        endDay = startDay;
        endYear = startYear;


        et6.setText(Integer.toString(startDay) + "/" + Integer.toString(startMonth) + "/" + Integer.toString(startYear));
        et7.setText(Integer.toString(endDay) + "/" + Integer.toString(endMonth) + "/" + Integer.toString(endYear));


        et6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int monthofYear, int dayofMonth) {
                        myCalenar.set(Calendar.YEAR, Year);
                        myCalenar.set(Calendar.MONTH, monthofYear);
                        myCalenar.set(Calendar.DAY_OF_MONTH, dayofMonth);
                        startDay = dayofMonth;
                        startMonth = monthofYear + 1;
                        startYear = Year;
                        et6.setText(dayofMonth + "/" + (monthofYear + 1) + "/" + Year);
                    }

                };
                DatePickerDialog picker = new DatePickerDialog(MainActivity.this, date1, myCalenar.get(Calendar.YEAR), myCalenar.get(Calendar.MONTH), myCalenar.get(Calendar.DAY_OF_MONTH));
                picker.setTitle("Calender");
                picker.setMessage("Choose A starting Date");
                picker.show();

            }
        });

        et7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int monthofYear, int dayofMonth) {
                        myCalenar.set(Calendar.YEAR, Year);
                        myCalenar.set(Calendar.MONTH, monthofYear);
                        myCalenar.set(Calendar.DAY_OF_MONTH, dayofMonth);
                        endDay = dayofMonth;
                        endMonth = monthofYear + 1;
                        endYear = Year;
                        et7.setText(dayofMonth + "/" + (monthofYear + 1) + "/" + Year);
                    }
                };
                DatePickerDialog picker = new DatePickerDialog(MainActivity.this, date2, myCalenar.get(Calendar.YEAR), myCalenar.get(Calendar.MONTH), myCalenar.get(Calendar.DAY_OF_MONTH));
                picker.setTitle("Calender");
                picker.setMessage("Choose A Ending Date");
                picker.show();
            }
        });


        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                showchart(startDay, startMonth, startYear, endDay, endMonth, endYear, myHelper, et8, et9, pieChart, pieChart1);
            }
        });

        et7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                showchart(startDay, startMonth, startYear, endDay, endMonth, endYear, myHelper, et8, et9, pieChart, pieChart1);
            }
        });


        showchart(startDay, startMonth, startYear, endDay, endMonth, endYear, myHelper, et8, et9, pieChart, pieChart1);

        MobileAds.initialize(this, "ca-app-pub-2995472604015192~4868453885");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //   .addTestDevice("42573E8A1DA18361D7D8AA95D20B1B00")
                .build();
        mAdView.loadAd(adRequest);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            backButtonHandler();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // signOut=findViewById(R.id.signOut);
        //  signIn=findViewById(R.id.signIn);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


     /*   //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
      /*  GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(acct!=null){
            item.setVisible(true);
        }
        else {
            item.setVisible(false);
        }
*/
        /*if (id == R.id.m1) {
            // Handle the camera action
        } else if (id == R.id.m2) {

        } else*/
        if (id == R.id.m3) {
            Intent i = new Intent(MainActivity.this, menu3.class);
            startActivity(i);
        } else if (id == R.id.m4) {
            Intent i = new Intent(MainActivity.this, menu4_report.class);
            startActivity(i);
        } else if (id == R.id.m5) {
            Intent i = new Intent(MainActivity.this, menu5.class);
            startActivity(i);
        } else if (id == R.id.m6) {
            Intent i = new Intent(MainActivity.this, menu6_record.class);
            finish();
            startActivity(i);
        } /*else if (id == R.id.m7) {

        } */ else if (id == R.id.m8) {
            Context context = MainActivity.this;
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText account = new EditText(context);
            account.setHint("Enter the new Balance for Account");
            account.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            layout.addView(account);

            final EditText wallet = new EditText(context);
            wallet.setHint("Enter the new Balance for Wallet");
            wallet.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            layout.addView(wallet);

            final EditText cash = new EditText(context);
            cash.setHint("Enter the new Balance for Cash");
            cash.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            layout.addView(cash);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(layout);
            builder.setTitle("Set Balance");
            builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    myHelper.setBalance(account.getText().toString(), wallet.getText().toString(), cash.getText().toString());
                    finish();
                    startActivity(getIntent());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();


           /* Intent i=new Intent(MainActivity.this,menu8_setbalance.class);
            finish();
            startActivity(i);*/

        } else if (id == R.id.m9) {
            SQLiteDatabase db = myHelper.getReadableDatabase();
            myHelper.onUpgrade(db, 1, 2);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            if (acct != null) {
                String personId = acct.getId();
                upload(MainActivity.this, personId);
            }
            Toast.makeText(this, "New DataBase Started...", Toast.LENGTH_SHORT).show();
            Intent i = getIntent();
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        } else if (id == R.id.m10) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Expense Manager");
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }

        } else if (id == R.id.m11) {
            Intent i = new Intent(MainActivity.this, aboutus.class);
            startActivity(i);

        } else if (id == R.id.m12) {
            signOut();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void backButtonHandler() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Confirm Exit");
        alertDialog.show();
    }

    public void showchart(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, MyHelper myHelper, EditText ed1, EditText ed2, PieChart pieChart, PieChart pieChart1) {
        System.out.println("Start Day= " + startDay + "/" + startMonth + "/" + startYear);
        System.out.println("End Day= " + endDay + "/" + endMonth + "/" + endYear);
        double customExpense = 0;
        double customIncome = 0;

        customExpense = myHelper.getCustomTotal(startDay, startMonth, startYear, endDay, endMonth, endYear, 1);
        customIncome = myHelper.getCustomTotal(startDay, startMonth, startYear, endDay, endMonth, endYear, 2);
        ArrayList<String> listExpense = myHelper.categoryTotal(startDay, startMonth, startYear, endDay, endMonth, endYear, 1);
        ArrayList<String> listIncome = myHelper.categoryTotal(startDay, startMonth, startYear, endDay, endMonth, endYear, 2);

        ArrayList<String> ExpenseName = new ArrayList<String>();
        ArrayList<String> IncomeName = new ArrayList<String>();

        ArrayList<String> FinalExpenseName = new ArrayList<String>();
        ArrayList<String> FinalIncomeName = new ArrayList<String>();
        ArrayList<String> FinalListIncome = new ArrayList<String>();
        ArrayList<String> FinalListExpense = new ArrayList<String>();

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

        String ExpenseAnalysis = "";
        String IncomeAnalysis = "";
        for (int i = 0; i < 14; i++) {
            if (Double.parseDouble(listExpense.get(i)) != 0) {
                FinalExpenseName.add(ExpenseName.get(i));
                FinalListExpense.add(listExpense.get(i));
                ExpenseAnalysis = ExpenseAnalysis + ExpenseName.get(i) + ": " + listExpense.get(i) + ".   ";
            }
        }

        int Expense_count = FinalExpenseName.size();

        for (int i = 0; i < 10; i++) {
            if (Double.parseDouble(listIncome.get(i)) != 0) {
                FinalIncomeName.add(IncomeName.get(i));
                FinalListIncome.add(listIncome.get(i));
                IncomeAnalysis = IncomeAnalysis + IncomeName.get(i) + ": " + listIncome.get(i) + ".   ";
            }
        }

        int Income_count = FinalIncomeName.size();


        ed1.setText(ExpenseAnalysis);
        ed2.setText(IncomeAnalysis);

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
        pieChart.animateXY(5000, 5000);
        pieChart.setDescription(ExpenseAnalysis);
        pieChart.setCenterText("Category Wise Expense \r\nTotal Expense=" + customExpense);
        pieChart.setDragDecelerationEnabled(true);
        pieChart.setUsePercentValues(true);


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
        pieChart1.animateXY(5000, 5000);
        pieChart1.setDescription(IncomeAnalysis);

        pieChart1.setCenterText("Category Wise Income \r\nTotal Income=" + customIncome);
        pieChart1.setDragDecelerationEnabled(true);
        pieChart1.setUsePercentValues(true);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        SignInKey = 1;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void signOut() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            String personId = acct.getId();
            upload(MainActivity.this, personId);
        }
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                updateUI(null);
            }
        });
        Toast.makeText(this, "Successfully Signed Out", Toast.LENGTH_SHORT).show();
    }

    private void updateUI(FirebaseUser user) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View HeaderView = navigationView.getHeaderView(0);

        TextView tv1 = HeaderView.findViewById(R.id.NHtv1);
        /*  TextView tv2=HeaderView.findViewById(R.id.NHtv2);*/
        ImageView img = HeaderView.findViewById(R.id.NHimageView);
        SignInButton button = HeaderView.findViewById(R.id.NHGSignIN);
        LinearLayout linearLayout = HeaderView.findViewById(R.id.NHL1);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            final String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Toast.makeText(this, "Welcome" + personName, Toast.LENGTH_SHORT).show();

            linearLayout.setVisibility(HeaderView.getVisibility());
            tv1.setText(personName);
            /*     tv2.setText(personEmail);*/
            Picasso.with(this).load(personPhoto).into(img);
            button.setVisibility(HeaderView.GONE);

            if (SignInKey == 1) {
                SignInKey = 0;
                final SQLiteDatabase db = myHelper.getWritableDatabase();

                Cursor cursor = db.rawQuery("SELECT * FROM EXPENSE_TABLE", null);
                Cursor cursor1 = db.rawQuery("SELECT * FROM INCOME_TABLE", null);
                Cursor cursor2 = db.rawQuery("SELECT * FROM TRANSFER", null);
                Cursor cursor3 = db.rawQuery("SELECT * FROM BALANCE", null);

                if (!(cursor.moveToFirst() || cursor1.moveToFirst() || cursor2.moveToFirst() || cursor3.moveToFirst())) {
                    //When all table is empty..
                    download(personId, MainActivity.this);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Are you want to load previous data?");
                    builder.setPositiveButton("OverWrite Current Data", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            myHelper.onUpgrade(db, 1, 2);
                            download(personId, MainActivity.this);
                        }
                    });
                    builder.setNeutralButton("Load with Current Data", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            download(personId, MainActivity.this);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            upload(MainActivity.this, personId);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.setTitle("Load Data");
                    dialog.show();
                }
            }
        } else {
            tv1.setText("");
            /* tv2.setText("");*/
            img.setImageBitmap(null);
            linearLayout.setVisibility(HeaderView.GONE);
            button.setVisibility(HeaderView.getVisibility());
        }
    }

    public void download(String personId, final Context context) {

        final MyHelper myHelper = new MyHelper(context);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(personId);
        myRef.keepSynced(true);
        if (myRef != null) {
            DatabaseReference Table1 = myRef.child("Table-1");
            Table1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        int Day = ds.child("Day").getValue(Integer.class);
                        int Month = ds.child("Month").getValue(Integer.class);
                        int Year = ds.child("Year").getValue(Integer.class);
                        int TypeId = ds.child("TypeId").getValue(Integer.class);
                        int MethodId = ds.child("MethodId").getValue(Integer.class);
                        Long Amount = ds.child("Amount").getValue(Long.class);
                        String amount = Long.toString(Amount);
                        String Type = ds.child("TypeName").getValue(String.class);
                        String Method = ds.child("MethodName").getValue(String.class);
                        String Description = ds.child("Description").getValue(String.class);
                        String Time = ds.child("Time").getValue(String.class);

                        myHelper.insertdata(Day, Month, Year, Time, TypeId, Type, MethodId, Method, amount, Description, 1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, "Could not Read Data", Toast.LENGTH_SHORT).show();
                }
            });

            DatabaseReference Table2 = myRef.child("Table-2");
            Table2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        int Day = ds.child("Day").getValue(Integer.class);
                        int Month = ds.child("Month").getValue(Integer.class);
                        int Year = ds.child("Year").getValue(Integer.class);
                        int TypeId = ds.child("TypeId").getValue(Integer.class);
                        int MethodId = ds.child("MethodId").getValue(Integer.class);
                        Long Amount = ds.child("Amount").getValue(Long.class);
                        String amount = Long.toString(Amount);
                        String Type = ds.child("TypeName").getValue(String.class);
                        String Method = ds.child("MethodName").getValue(String.class);
                        String Description = ds.child("Description").getValue(String.class);
                        String Time = ds.child("Time").getValue(String.class);

                        myHelper.insertdata(Day, Month, Year, Time, TypeId, Type, MethodId, Method, amount, Description, 2);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, "Could not Read Data", Toast.LENGTH_SHORT).show();
                }
            });

            DatabaseReference Table3 = myRef.child("Table-3");
            Table3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        int Day = ds.child("Day").getValue(Integer.class);
                        int Month = ds.child("Month").getValue(Integer.class);
                        int Year = ds.child("Year").getValue(Integer.class);
                        int SourceId = ds.child("SourceId").getValue(Integer.class);
                        int TargetId = ds.child("TargetId").getValue(Integer.class);
                        Long Amount = ds.child("Amount").getValue(Long.class);
                        String amount = Long.toString(Amount);
                        String Source = ds.child("SourceName").getValue(String.class);
                        String Target = ds.child("TargetName").getValue(String.class);
                        String Description = ds.child("Description").getValue(String.class);
                        String Time = ds.child("Time").getValue(String.class);

                        myHelper.insertInto3(Day, Month, Year, Time, SourceId, TargetId, Source, Target, amount, Description);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, "Could not Read Data", Toast.LENGTH_SHORT).show();
                }
            });

            DatabaseReference Table4 = myRef.child("Table-4");
            Table4.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Long account = ds.child("Account").getValue(Long.class);
                        String Account = Long.toString(account);
                        Long wallet = ds.child("Wallet").getValue(Long.class);
                        String Wallet = Long.toString(wallet);
                        Long cash = ds.child("Cash").getValue(Long.class);
                        String Cash = Long.toString(cash);

                        myHelper.insertInto4(Account, Wallet, Cash);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, "Could not Read Data", Toast.LENGTH_SHORT).show();
                }
            });
            Intent i = getIntent();
            finish();
            startActivity(i);
            Toast.makeText(context, "If data could not loaded properly then restart app..", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(context, "Sorry, You Don't Have any Previous data", Toast.LENGTH_SHORT).show();
        }

    }

}

