package com.nmakadiya.expensemanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class Edit extends AppCompatActivity {

    private static final String TAG = "Edit";
    private static final int SPEECH_REQUEST_CODE = 0;
    MyHelper helper = new MyHelper(Edit.this);
    int day, month, year, KEY = 1;
    String timeinfo;
    String amount, type, description, method;
    int spinkey, methodkey;
    String cash, wallet, account;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //displaySpeechRecognizer();

      /*  String query = "";
        if (getIntent().getAction() != null && getIntent().getAction().equals("com.google.android.gms.actions.SEARCH_ACTION")) {
            query = getIntent().getStringExtra(SearchManager.QUERY);

            ArrayList<String> list=new ArrayList<>();
            String name="";
            for(int i=0;i<query.length();i++){
                if(query.charAt(i)!=' '){
                    name=name+query.charAt(i);
                }
                else if(query.charAt(i)==' '){
                    list.add(name);
                    name="";
                }
            }

            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        }*/

        MobileAds.initialize(this, "ca-app-pub-2995472604015192~4868453885");
        mAdView = findViewById(R.id.edit_adView);
        AdRequest adRequest = new AdRequest.Builder()
                // .addTestDevice("42573E8A1DA18361D7D8AA95D20B1B00")
                .build();
        mAdView.loadAd(adRequest);


        final TextView expense = findViewById(R.id.tv1);
        final TextView income = findViewById(R.id.tv2);
        final TextView transfer = findViewById(R.id.tv3);
        final EditText et1 = findViewById(R.id.editText1);
        final EditText et2 = findViewById(R.id.editText2);
        final EditText et3 = findViewById(R.id.editText3);
        final EditText et4 = findViewById(R.id.editText4);
        final TextView tb1 = findViewById(R.id.tb1);
        final TextView tb2 = findViewById(R.id.tb2);
        final TextView tb3 = findViewById(R.id.tb3);
        final TextView tb4 = findViewById(R.id.tb4);
        final Spinner sp1 = findViewById(R.id.spinner1);
        final Spinner sp2 = findViewById(R.id.spinner2);
        final Spinner sp3 = findViewById(R.id.spinner3);
        final Spinner sp4 = findViewById(R.id.spinner4);
        Button button = findViewById(R.id.btn);


        final Calendar myCalenar = Calendar.getInstance();
        day = myCalenar.get(Calendar.DAY_OF_MONTH);
        month = myCalenar.get(Calendar.MONTH) + 1;
        year = myCalenar.get(Calendar.YEAR);
        timeinfo = Integer.toString(myCalenar.get(Calendar.HOUR)) + ":" + Integer.toString(myCalenar.get(Calendar.MINUTE));

        et1.setText(Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year));
        et2.setText(timeinfo);


        et1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int monthofYear, int dayofMonth) {
                        myCalenar.set(Calendar.YEAR, Year);
                        myCalenar.set(Calendar.MONTH, monthofYear);
                        myCalenar.set(Calendar.DAY_OF_MONTH, dayofMonth);
                        day = dayofMonth;
                        month = monthofYear + 1;
                        year = Year;
                        et1.setText(dayofMonth + "/" + (monthofYear + 1) + "/" + Year);
                    }
                };
                DatePickerDialog picker = new DatePickerDialog(Edit.this, date, myCalenar.get(Calendar.YEAR), myCalenar.get(Calendar.MONTH), myCalenar.get(Calendar.DAY_OF_MONTH));
                picker.show();
            }
        });

        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        myCalenar.set(Calendar.HOUR_OF_DAY, hour);
                        myCalenar.set(Calendar.MINUTE, minute);
                        et2.setText(hour + ":" + minute);
                        timeinfo = Integer.toString(hour) + ":" + Integer.toString(minute);
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(Edit.this, time, myCalenar.get(Calendar.HOUR_OF_DAY), myCalenar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });

        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tb2.setVisibility(view.GONE);
                tb4.setVisibility(view.GONE);
                sp3.setVisibility(view.GONE);
                sp4.setVisibility(view.GONE);

                tb1.setVisibility(view.getVisibility());
                tb3.setVisibility(view.getVisibility());
                sp2.setVisibility(view.getVisibility());

                KEY = 1;

                expense.setBackgroundColor(Color.parseColor("#EA1E44"));
                income.setBackgroundColor(Color.WHITE);
                transfer.setBackgroundColor(Color.WHITE);

            }
        });

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tb2.setVisibility(view.GONE);
                tb4.setVisibility(view.GONE);
                sp2.setVisibility(view.GONE);
                sp4.setVisibility(view.GONE);

                tb1.setVisibility(view.getVisibility());
                tb3.setVisibility(view.getVisibility());
                sp3.setVisibility(view.getVisibility());

                KEY = 2;

                income.setBackgroundColor(Color.parseColor("#EA1E44"));
                expense.setBackgroundColor(Color.WHITE);
                transfer.setBackgroundColor(Color.WHITE);

            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tb1.setVisibility(view.GONE);
                tb3.setVisibility(view.GONE);
                sp2.setVisibility(view.GONE);
                sp3.setVisibility(view.GONE);

                tb2.setVisibility(view.getVisibility());
                tb4.setVisibility(view.getVisibility());
                sp4.setVisibility(view.getVisibility());

                KEY = 3;

                transfer.setBackgroundColor(Color.parseColor("#EA1E44"));
                income.setBackgroundColor(Color.WHITE);
                expense.setBackgroundColor(Color.WHITE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(KEY);

                if (KEY == 1) {

                    amount = et3.getText().toString();
                    type = sp2.getSelectedItem().toString();
                    spinkey = sp2.getSelectedItemPosition();
                    method = sp1.getSelectedItem().toString();
                    methodkey = sp1.getSelectedItemPosition();
                    description = et4.getText().toString();

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
                    helper.insertdata(day, month, year, timeinfo, spinkey, type, methodkey, method, amount, description, 1);
                    Toast.makeText(Edit.this, amount + " INR added....", Toast.LENGTH_SHORT).show();
                } else if (KEY == 2) {
                    amount = et3.getText().toString();
                    type = sp3.getSelectedItem().toString();
                    spinkey = sp3.getSelectedItemPosition();
                    method = sp1.getSelectedItem().toString();
                    methodkey = sp1.getSelectedItemPosition();
                    description = et4.getText().toString();

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
                    helper.insertdata(day, month, year, timeinfo, spinkey, type, methodkey, method, amount, description, 2);
                    Toast.makeText(Edit.this, amount + "INR added....", Toast.LENGTH_SHORT).show();

                } else if (KEY == 3) {
                    amount = et3.getText().toString();
                    int target_id = sp4.getSelectedItemPosition();
                    String target = sp4.getSelectedItem().toString();
                    int source_id = sp1.getSelectedItemPosition();
                    String source = sp1.getSelectedItem().toString();
                    description = et4.getText().toString();

                    if (source_id != target_id) {
                        switch (source_id) {
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
                        switch (target_id) {
                            case 0:
                                cash = amount;
                                break;
                            case 1:
                                wallet = amount;
                                break;
                            case 2:
                                account = amount;
                                break;
                        }
                        helper.insertInto4(account, wallet, cash);
                        helper.insertInto3(day, month, year, timeinfo, source_id, target_id, source, target, amount, description);
                        Toast.makeText(Edit.this, amount + " INR Transfer....", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Edit.this, "Source And Target are same....", Toast.LENGTH_SHORT).show();
                    }
                }

                System.out.println(helper.getTotal(1));
                System.out.println(helper.getTotal(2));

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                if (acct != null) {
                    String personId = acct.getId();
                    MainActivity.upload(Edit.this, personId);
                }

                Intent i = getIntent();
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Edit.this, MainActivity.class);
        finish();
        startActivity(i);
    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText
            Toast.makeText(this, spokenText, Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
