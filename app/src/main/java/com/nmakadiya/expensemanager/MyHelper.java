package com.nmakadiya.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyHelper extends SQLiteOpenHelper {

    private static final String dbname = "mydb";
    private static final String tablename1 = "EXPENSE_TABLE";
    private static final String tablename2 = "INCOME_TABLE";
    private static final String tablename3 = "TRANSFER";
    private static final String tablename4 = "BALANCE";
    private static final int version = 1;

    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public MyHelper(Context context) {
        super(context, dbname, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
        String sql_create1 = "CREATE TABLE IF NOT EXISTS " + tablename1 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE INTEGER,MONTH INTEGER,YEAR INTEGER,TIME TEXT,UNIQUE_ID INTEGER,TYPE TEXT,METHOD_ID INTEGER,METHOD TEXT,AMOUNT REAL,DESCRIPTION TEXT )";
        sqLiteDatabase.execSQL(sql_create1);
        String sql_create2 = "CREATE TABLE IF NOT EXISTS " + tablename2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE INTEGER,MONTH INTEGER,YEAR INTEGER,TIME TEXT,UNIQUE_ID INTEGER,TYPE TEXT,METHOD_ID INTEGER,METHOD TEXT,AMOUNT REAL,DESCRIPTION TEXT )";
        sqLiteDatabase.execSQL(sql_create2);
        String sql_create3 = "CREATE TABLE IF NOT EXISTS " + tablename3 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE INTEGER,MONTH INTEGER,YEAR INTEGER,TIME TEXT,SOURCE_ID INTEGER,SOURCE TEXT,TARGET_ID INTEGER,TARGET TEXT,AMOUNT REAl,DESCRIPTION TEXT)";
        sqLiteDatabase.execSQL(sql_create3);
        String sql_create4 = "CREATE TABLE IF NOT EXISTS " + tablename4 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,ACCOUNT REAL,WALLET REAL,CASH REAL)";
        sqLiteDatabase.execSQL(sql_create4);
    }

    /*public SQLiteDatabase getDatabase(){
        return this.sqLiteDatabase;
    }
*/

    public void insertdata(int date, int month, int year, String time, int unique_id, String type, int method_id, String method, String amount, String description, int key) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues value;

        try {
            value = new ContentValues();
            value.put("DATE", date);
            value.put("MONTH", month);
            value.put("YEAR", year);
            value.put("TIME", time);
            value.put("UNIQUE_ID", unique_id);
            value.put("TYPE", type);
            value.put("METHOD_ID", method_id);
            value.put("METHOD", method);
            value.put("AMOUNT", amount);
            value.put("DESCRIPTION", description);

            long i;
            if (key == 1) {
                i = db.insert(tablename1, null, value);
                Log.i("INSERT in Expense", i + "");
                db.setTransactionSuccessful();
            } else {
                i = db.insert(tablename2, null, value);
                Log.i("INSERT in Income", i + "");
                db.setTransactionSuccessful();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void insertInto3(int date, int month, int year, String time, int source_id, int target_id, String source, String target, String amount, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues value;

        try {
            value = new ContentValues();
            value.put("DATE", date);
            value.put("MONTH", month);
            value.put("YEAR", year);
            value.put("TIME", time);
            value.put("SOURCE_ID", source_id);
            value.put("TARGET_ID", target_id);
            value.put("SOURCE", source);
            value.put("TARGET", target);
            value.put("AMOUNT", amount);
            value.put("DESCRIPTION", description);

            long i;
            i = db.insert(tablename3, null, value);
            Log.i("INSERT in Transfer", i + "");
            db.setTransactionSuccessful();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in table 3");
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void insertInto4(String account, String wallet, String cash) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues value;

        try {
            value = new ContentValues();
            value.put("ACCOUNT", account);
            value.put("WALLET", wallet);
            value.put("CASH", cash);
            long i;
            i = db.insert(tablename4, null, value);
            Log.i("INSERT in Balance", i + "");
            db.setTransactionSuccessful();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in table 4");
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public double getTotal(int key) {
        double total = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        if (key == 1) {
            String sql_sum = "SELECT sum(AMOUNT) FROM " + tablename1;
            Cursor cursor = db.rawQuery(sql_sum, null);
            if (cursor.moveToFirst()) {
                total = cursor.getInt(0);
            }
            return total;
        } else {
            String sql_sum = "SELECT sum(AMOUNT) FROM " + tablename2;
            Cursor cursor = db.rawQuery(sql_sum, null);
            if (cursor.moveToFirst()) {
                total = cursor.getInt(0);
            }
            return total;
        }
    }

    public double getTotalAccount() {
        SQLiteDatabase db = this.getWritableDatabase();
        double Total = 0;
        String sql = "SELECT sum(ACCOUNT) FROM " + tablename4;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            Total = cursor.getInt(0);
        }
        return Total;
    }

    public double getTotalWallet() {
        SQLiteDatabase db = this.getWritableDatabase();
        double Total = 0;
        String sql = "SELECT sum(WALLET) FROM " + tablename4;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            Total = cursor.getInt(0);
        }
        return Total;
    }

    public double getTotalCash() {
        SQLiteDatabase db = this.getWritableDatabase();
        double Total = 0;
        String sql = "SELECT sum(CASH) FROM " + tablename4;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            Total = cursor.getInt(0);
            System.out.println("Yes........");
        }

        return Total;
    }


    public double getCustomTotal(int sday, int smonth, int syear, int eday, int emonth, int eyear, int key) {
        double m_total = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        if (key == 1) {
            if (eyear == syear && emonth > smonth) {
                double m_total1 = 0, m_total2 = 0, m_total3 = 0;
                String sql1 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE>=%d", tablename1, syear, smonth, sday);
                Cursor cursor1 = db.rawQuery(sql1, null);
                if (cursor1.moveToFirst()) {
                    m_total1 = cursor1.getInt(0);
                }
                String sql2 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH>%d AND MONTH<%d", tablename1, syear, smonth, emonth);
                Cursor cursor2 = db.rawQuery(sql2, null);
                if (cursor2.moveToFirst()) {
                    m_total2 = cursor2.getInt(0);
                }
                String sql3 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE<=%d", tablename1, syear, emonth, eday);
                Cursor cursor3 = db.rawQuery(sql3, null);
                if (cursor3.moveToFirst()) {
                    m_total3 = cursor3.getInt(0);
                }

                m_total = m_total1 + m_total2 + m_total3;
            } else if (eyear > syear) {
                double m_total1 = 0, m_total2 = 0, m_total3 = 0, m_total4 = 0, m_total5 = 0;
                String sql1 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE>=%d", tablename1, syear, smonth, sday);
                Cursor cursor1 = db.rawQuery(sql1, null);
                if (cursor1.moveToFirst()) {
                    m_total1 = cursor1.getInt(0);
                }
                String sql2 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH>%d ", tablename1, syear, smonth);
                Cursor cursor2 = db.rawQuery(sql2, null);
                if (cursor2.moveToFirst()) {
                    m_total2 = cursor2.getInt(0);
                }
                String sql3 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR>%d AND YEAR<%d", tablename1, syear, eyear);
                Cursor cursor3 = db.rawQuery(sql3, null);
                if (cursor3.moveToFirst()) {
                    m_total3 = cursor3.getInt(0);
                }
                String sql4 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH<%d", tablename1, eyear, emonth);
                Cursor cursor4 = db.rawQuery(sql4, null);
                if (cursor4.moveToFirst()) {
                    m_total4 = cursor4.getInt(0);
                }
                String sql5 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE<=%d", tablename1, eyear, emonth, eday);
                Cursor cursor5 = db.rawQuery(sql5, null);
                if (cursor5.moveToFirst()) {
                    m_total5 = cursor5.getInt(0);
                }
                m_total = m_total1 + m_total2 + m_total3 + m_total4 + m_total5;
            } else if (eyear == syear && emonth == smonth) {
                String sql = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE>=%d AND DATE<=%d", tablename1, syear, smonth, sday, eday);
                Cursor cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    m_total = cursor.getInt(0);
                }
            }
            return m_total;
        } else {

            if (eyear == syear && emonth > smonth) {
                int m_total1 = 0, m_total2 = 0, m_total3 = 0;
                String sql1 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE>=%d", tablename2, syear, smonth, sday);
                Cursor cursor1 = db.rawQuery(sql1, null);
                if (cursor1.moveToFirst()) {
                    m_total1 = cursor1.getInt(0);
                }
                String sql2 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH>%d AND MONTH<%d", tablename2, syear, smonth, emonth);
                Cursor cursor2 = db.rawQuery(sql2, null);
                if (cursor2.moveToFirst()) {
                    m_total2 = cursor2.getInt(0);
                }
                String sql3 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE<=%d", tablename2, syear, emonth, eday);
                Cursor cursor3 = db.rawQuery(sql3, null);
                if (cursor3.moveToFirst()) {
                    m_total3 = cursor3.getInt(0);
                }

                m_total = m_total1 + m_total2 + m_total3;
            } else if (eyear > syear) {
                int m_total1 = 0, m_total2 = 0, m_total3 = 0, m_total4 = 0, m_total5 = 0;
                String sql1 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE>=%d", tablename2, syear, smonth, sday);
                Cursor cursor1 = db.rawQuery(sql1, null);
                if (cursor1.moveToFirst()) {
                    m_total1 = cursor1.getInt(0);
                }
                String sql2 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH>%d ", tablename2, syear, smonth);
                Cursor cursor2 = db.rawQuery(sql2, null);
                if (cursor2.moveToFirst()) {
                    m_total2 = cursor2.getInt(0);
                }
                String sql3 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR>%d AND YEAR<%d", tablename2, syear, eyear);
                Cursor cursor3 = db.rawQuery(sql3, null);
                if (cursor3.moveToFirst()) {
                    m_total3 = cursor3.getInt(0);
                }
                String sql4 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH<%d", tablename2, eyear, emonth);
                Cursor cursor4 = db.rawQuery(sql4, null);
                if (cursor4.moveToFirst()) {
                    m_total4 = cursor4.getInt(0);
                }
                String sql5 = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE<=%d", tablename2, eyear, emonth, eday);
                Cursor cursor5 = db.rawQuery(sql5, null);
                if (cursor5.moveToFirst()) {
                    m_total5 = cursor5.getInt(0);
                }
                m_total = m_total1 + m_total2 + m_total3 + m_total4 + m_total5;
            } else if (eyear == syear && emonth == smonth) {
                String sql = String.format("SELECT sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE>=%d AND DATE<=%d", tablename2, syear, smonth, sday, eday);
                Cursor cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    m_total = cursor.getInt(0);
                }
            }
            return m_total;
        }
    }

    public ArrayList<String> categoryTotal(int sday, int smonth, int syear, int eday, int emonth, int eyear, int key) {
        ArrayList<String> list1 = new ArrayList<String>(20);
        SQLiteDatabase db = this.getWritableDatabase();

        if (key == 1) {
            double[] m_total = new double[15];
            if (eyear == syear && emonth > smonth) {
                double[] m_total1 = new double[15];
                double[] m_total2 = new double[15];
                double[] m_total3 = new double[15];
                String sql1 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE>=%d GROUP BY UNIQUE_ID", tablename1, syear, smonth, sday);
                Cursor cursor1 = db.rawQuery(sql1, null);
                if (cursor1 != null && cursor1.moveToFirst()) {
                    do {
                        int i = cursor1.getInt(0);
                        m_total1[i] = cursor1.getInt(1);
                    } while (cursor1.moveToNext());
                }


                String sql2 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH>%d AND MONTH<%d GROUP BY UNIQUE_ID", tablename1, syear, smonth, emonth);
                Cursor cursor2 = db.rawQuery(sql2, null);
                if (cursor2 != null && cursor2.moveToFirst()) {
                    do {
                        int i = cursor2.getInt(0);
                        m_total2[i] = cursor2.getInt(1);
                    } while (cursor2.moveToNext());
                }

                String sql3 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE<=%d GROUP BY UNIQUE_ID", tablename1, syear, emonth, eday);
                Cursor cursor3 = db.rawQuery(sql3, null);
                if (cursor3 != null && cursor3.moveToFirst()) {
                    do {
                        int i = cursor3.getInt(0);
                        m_total3[i] = cursor3.getInt(1);
                    } while (cursor3.moveToNext());
                }


                for (int j = 0; j < 15; j++) {
                    m_total[j] = m_total1[j] + m_total2[j] + m_total3[j];
                }

            } else if (eyear > syear) {
                double[] m_total1 = new double[15];
                double[] m_total2 = new double[15];
                double[] m_total3 = new double[15];
                double[] m_total4 = new double[15];
                double[] m_total5 = new double[15];

                String sql1 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE>=%d GROUP BY UNIQUE_ID", tablename1, syear, smonth, sday);
                Cursor cursor1 = db.rawQuery(sql1, null);
                if (cursor1 != null && cursor1.moveToFirst()) {
                    do {
                        int i = cursor1.getInt(0);
                        m_total1[i] = cursor1.getInt(1);
                    } while (cursor1.moveToNext());
                }


                String sql2 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH>%d GROUP BY UNIQUE_ID ", tablename1, syear, smonth);
                Cursor cursor2 = db.rawQuery(sql2, null);
                if (cursor2 != null && cursor2.moveToFirst()) {
                    do {
                        int i = cursor2.getInt(0);
                        m_total2[i] = cursor2.getInt(1);
                    } while (cursor2.moveToNext());
                }


                String sql3 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR>%d AND YEAR<%d GROUP BY UNIQUE_ID", tablename1, syear, eyear);
                Cursor cursor3 = db.rawQuery(sql3, null);
                if (cursor3 != null && cursor3.moveToFirst()) {
                    do {
                        int i = cursor3.getInt(0);
                        m_total3[i] = cursor3.getInt(1);
                    } while (cursor3.moveToNext());
                }


                String sql4 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH<%d GROUP BY UNIQUE_ID", tablename1, eyear, emonth);
                Cursor cursor4 = db.rawQuery(sql4, null);
                if (cursor4 != null && cursor4.moveToFirst()) {
                    do {
                        int i = cursor4.getInt(0);
                        m_total4[i] = cursor4.getInt(1);
                    } while (cursor4.moveToNext());
                }


                String sql5 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE<=%d GROUP BY UNIQUE_ID", tablename1, eyear, emonth, eday);
                Cursor cursor5 = db.rawQuery(sql5, null);
                if (cursor5 != null && cursor5.moveToFirst()) {
                    do {
                        int i = cursor5.getInt(0);
                        m_total5[i] = cursor5.getInt(1);
                    } while (cursor5.moveToNext());
                }


                for (int j = 0; j < 15; j++) {
                    m_total[j] = m_total1[j] + m_total2[j] + m_total3[j] + m_total4[j] + m_total5[j];
                }
            } else if (eyear == syear && emonth == smonth) {
                String sql = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE>=%d AND DATE<=%d GROUP BY UNIQUE_ID", tablename1, syear, smonth, sday, eday);
                Cursor cursor1 = db.rawQuery(sql, null);
                if (cursor1 != null && cursor1.moveToFirst()) {
                    do {
                        int i = cursor1.getInt(0);
                        m_total[i] = cursor1.getInt(1);
                    } while (cursor1.moveToNext());
                }

            }

            for (int k = 0; k < 15; k++) {
                list1.add(Double.toString(m_total[k]));
            }
        } else {
            double[] m_total = new double[15];
            if (eyear == syear && emonth > smonth) {
                double[] m_total1 = new double[15];
                double[] m_total2 = new double[15];
                double[] m_total3 = new double[15];
                String sql1 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE>=%d GROUP BY UNIQUE_ID", tablename2, syear, smonth, sday);
                Cursor cursor1 = db.rawQuery(sql1, null);
                if (cursor1 != null && cursor1.moveToFirst()) {
                    do {
                        int i = cursor1.getInt(0);
                        m_total1[i] = cursor1.getInt(1);
                    } while (cursor1.moveToNext());
                }


                String sql2 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH>%d AND MONTH<%d GROUP BY UNIQUE_ID", tablename2, syear, smonth, emonth);
                Cursor cursor2 = db.rawQuery(sql2, null);
                if (cursor2 != null && cursor2.moveToFirst()) {
                    do {
                        int i = cursor2.getInt(0);
                        m_total2[i] = cursor2.getInt(1);
                    } while (cursor2.moveToNext());
                }

                String sql3 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE<=%d GROUP BY UNIQUE_ID", tablename2, syear, emonth, eday);
                Cursor cursor3 = db.rawQuery(sql3, null);
                if (cursor3 != null && cursor3.moveToFirst()) {
                    do {
                        int i = cursor3.getInt(0);
                        m_total3[i] = cursor3.getInt(1);
                    } while (cursor3.moveToNext());
                }


                for (int j = 0; j < 15; j++) {
                    m_total[j] = m_total1[j] + m_total2[j] + m_total3[j];
                }

            } else if (eyear > syear) {
                double[] m_total1 = new double[15];
                double[] m_total2 = new double[15];
                double[] m_total3 = new double[15];
                double[] m_total4 = new double[15];
                double[] m_total5 = new double[15];

                String sql1 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE>=%d GROUP BY UNIQUE_ID", tablename2, syear, smonth, sday);
                Cursor cursor1 = db.rawQuery(sql1, null);
                if (cursor1 != null && cursor1.moveToFirst()) {
                    do {
                        int i = cursor1.getInt(0);
                        m_total1[i] = cursor1.getInt(1);
                    } while (cursor1.moveToNext());
                }


                String sql2 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH>%d GROUP BY UNIQUE_ID ", tablename2, syear, smonth);
                Cursor cursor2 = db.rawQuery(sql2, null);
                if (cursor2 != null && cursor2.moveToFirst()) {
                    do {
                        int i = cursor2.getInt(0);
                        m_total2[i] = cursor2.getInt(1);
                    } while (cursor2.moveToNext());
                }


                String sql3 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR>%d AND YEAR<%d GROUP BY UNIQUE_ID", tablename2, syear, eyear);
                Cursor cursor3 = db.rawQuery(sql3, null);
                if (cursor3 != null && cursor3.moveToFirst()) {
                    do {
                        int i = cursor3.getInt(0);
                        m_total3[i] = cursor3.getInt(1);
                    } while (cursor3.moveToNext());
                }


                String sql4 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH<%d GROUP BY UNIQUE_ID", tablename2, eyear, emonth);
                Cursor cursor4 = db.rawQuery(sql4, null);
                if (cursor4 != null && cursor4.moveToFirst()) {
                    do {
                        int i = cursor4.getInt(0);
                        m_total4[i] = cursor4.getInt(1);
                    } while (cursor4.moveToNext());
                }


                String sql5 = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE<=%d GROUP BY UNIQUE_ID", tablename2, eyear, emonth, eday);
                Cursor cursor5 = db.rawQuery(sql5, null);
                if (cursor5 != null && cursor5.moveToFirst()) {
                    do {
                        int i = cursor5.getInt(0);
                        m_total5[i] = cursor5.getInt(1);
                    } while (cursor5.moveToNext());
                }


                for (int j = 0; j < 15; j++) {
                    m_total[j] = m_total1[j] + m_total2[j] + m_total3[j] + m_total4[j] + m_total5[j];
                }
            } else if (eyear == syear && emonth == smonth) {
                String sql = String.format("SELECT UNIQUE_ID,sum(AMOUNT) FROM %s WHERE YEAR=%d AND MONTH=%d AND DATE>=%d AND DATE<=%d GROUP BY UNIQUE_ID", tablename2, syear, smonth, sday, eday);
                Cursor cursor1 = db.rawQuery(sql, null);
                if (cursor1 != null && cursor1.moveToFirst()) {
                    do {
                        int i = cursor1.getInt(0);
                        m_total[i] = cursor1.getInt(1);
                    } while (cursor1.moveToNext());
                }

            }

            for (int k = 0; k < 15; k++) {
                list1.add(Double.toString(m_total[k]));
            }
        }

        return list1;
    }

    public void setBalance(String account, String wallet, String cash) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tablename4);
        insertInto4(account, wallet, cash);
    }

    public ArrayList<ArrayList<String>> getDetail(int day, int month, int year, int key1, int key2) {
        ArrayList<ArrayList<String>> detail = new ArrayList();

        ArrayList<String> UniqueIdList = new ArrayList();
        ArrayList<String> DateList = new ArrayList();
        ArrayList<String> TimeList = new ArrayList();
        ArrayList<String> AmountList = new ArrayList();
        ArrayList<String> CategoryList = new ArrayList();
        ArrayList<String> MethodList = new ArrayList();
        ArrayList<String> DescriptionList = new ArrayList();
        ArrayList<String> Method_IdList = new ArrayList();
        ArrayList<String> Type_IdList = new ArrayList();


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        if (key1 == 1 && key2 == 1) {
            cursor = db.rawQuery("SELECT * FROM " + tablename1 + " WHERE DATE=" + day + " AND MONTH=" + month + " AND YEAR=" + year, null);
        } else if (key1 == 1 && key2 == 2) {
            cursor = db.rawQuery("SELECT * FROM " + tablename1 + " WHERE MONTH=" + month + " AND YEAR=" + year + " ORDER BY DATE ASC ", null);
        } else if (key1 == 2 && key2 == 1) {
            cursor = db.rawQuery("SELECT * FROM " + tablename2 + " WHERE DATE=" + day + " AND MONTH=" + month + " AND YEAR=" + year, null);
        } else if (key1 == 2 && key2 == 2) {
            cursor = db.rawQuery("SELECT * FROM " + tablename2 + " WHERE MONTH=" + month + " AND YEAR=" + year + " ORDER BY DATE ASC ", null);
        } else if (key1 == 3 && key2 == 1) {
            cursor = db.rawQuery("SELECT * FROM " + tablename3 + " WHERE DATE=" + day + " AND MONTH=" + month + " AND YEAR=" + year, null);
        } else if (key1 == 3 && key2 == 2) {
            cursor = db.rawQuery("SELECT * FROM " + tablename3 + " WHERE MONTH=" + month + " AND YEAR=" + year + " ORDER BY DATE ASC ", null);
        }


        if (cursor != null && cursor.moveToFirst()) {
            do {
                UniqueIdList.add(Integer.toString(cursor.getInt(0)));
                String dateinfo = Integer.toString(cursor.getInt(1)) + "/" + Integer.toString(cursor.getInt(2)) + "/" + Integer.toString(cursor.getInt(3));
                DateList.add(dateinfo);
                TimeList.add(cursor.getString(4));
                CategoryList.add(cursor.getString(6));
                MethodList.add(cursor.getString(8));
                AmountList.add(Double.toString(cursor.getDouble(9)));
                DescriptionList.add(cursor.getString(10));
                Method_IdList.add(Integer.toString(cursor.getInt(7)));
                Type_IdList.add(Integer.toString(cursor.getInt(5)));
            } while (cursor.moveToNext());
        }

        detail.add(DateList);
        detail.add(TimeList);
        detail.add(CategoryList);
        detail.add(MethodList);
        detail.add(AmountList);
        detail.add(DescriptionList);
        detail.add(UniqueIdList);
        detail.add(Method_IdList);
        detail.add(Type_IdList);

        return detail;
    }

    public void delete(int Id, int table) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "";
        switch (table) {
            case 1:
                sql = "DELETE FROM " + tablename1 + " WHERE ID= " + Id;
                break;
            case 2:
                sql = "DELETE FROM " + tablename2 + " WHERE ID= " + Id;
                break;
            case 3:
                sql = "DELETE FROM " + tablename3 + " WHERE ID= " + Id;
                break;
        }
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql_delete1 = " DROP TABLE IF EXISTS " + tablename1;
        String sql_delete2 = " DROP TABLE IF EXISTS " + tablename2;
        String sql_delete3 = " DROP TABLE IF EXISTS " + tablename3;
        String sql_delete4 = " DROP TABLE IF EXISTS " + tablename4;
        sqLiteDatabase.execSQL(sql_delete1);
        sqLiteDatabase.execSQL(sql_delete2);
        sqLiteDatabase.execSQL(sql_delete3);
        sqLiteDatabase.execSQL(sql_delete4);
        onCreate(sqLiteDatabase);
    }
}

