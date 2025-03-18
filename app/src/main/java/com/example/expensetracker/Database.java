package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Database(Context context) {
        super(context, "ExpenseTracker.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //for user
       String qry1 = "create table users(username text, password text, monthlyIncome text, currentBalance text)";
       db.execSQL(qry1);

       //for expenses
        String qry2 = "CREATE TABLE expenses(type TEXT, amount TEXT, description TEXT)";
        db.execSQL(qry2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists expenses");
    }

    public Boolean addExp(String type, String amount, String description)
    {
        ContentValues cv = new ContentValues();
        cv.put("type", type);
        cv.put("amount", amount);
        cv.put("description", description);
        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert("expenses", null, cv);
        db.close();

        if(result==-1)
        {
            return false;
        }else{
            return true;
        }

    }

//    public Boolean UpdateExp(int id, String type, String amount, String description) {
//        ContentValues cv = new ContentValues();
//        cv.put("type", type);
//        cv.put("amount", amount);
//        cv.put("description", description);
//
//        SQLiteDatabase db = getWritableDatabase();
//        long result = db.update("expenses", cv, "id = ?", new String[]{String.valueOf(id)});
//        db.close();
//        return result != -1;
//    }
    public Boolean UpdateExp(String type, String amount, String description)
    {
        ContentValues cv = new ContentValues();
        cv.put("type", type);
        cv.put("amount", amount);
        cv.put("description", description);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from expenses where description = ?", new String[]{description});

        if (cursor.getCount()>0) {
            long result = db.update("expenses", cv, "description = ?", new String[]{description});
            return result != -1;
        }else
        {
            return false;
        }

    }

    public Boolean DeleteExp(String description)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from expenses where description = ?", new String[]{description});

        if (cursor.getCount()>0) {
            long result = db.delete("expenses", "description = ?", new String[]{description});
            return result != -1;
        }else
        {
            return false;
        }

    }

    public Cursor getData(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from expenses", null);
        return cursor;
    }

    public void register(String username, String password, String monthlyIncome, String currentBalance)
    {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("monthlyIncome", monthlyIncome);
        cv.put("currentBalance", currentBalance);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }

    public int login(String username, String password)
    {
        int result = 0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where username=? and password=?", str);
        if (c.moveToFirst())
        {
            result=1;
        }
        return result;
    }

    public Cursor getExpenseTypesCount() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT type, COUNT(*) FROM expenses GROUP BY type";
        return db.rawQuery(query, null);
    }


    // Method to get user data
    public Cursor getUserData() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users", null);
        return cursor;
    }

    // Method to update user data
    public boolean updateUserData(String name, String income, String savings) {
        ContentValues cv = new ContentValues();
        cv.put("username", name);
        cv.put("monthlyIncome", income);
        cv.put("currentBalance", savings);

        SQLiteDatabase db = getWritableDatabase();
        long result = db.update("users", cv, "username = ?", new String[]{name});
        return result != -1;



    }


    public double getTotalExpenses() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM expenses", null);
        double total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    public String getMonthlyIncome() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT monthlyIncome FROM users", null);
        String monthlyIncome = "0"; // Default value
        if (cursor.moveToFirst()) {
            monthlyIncome = cursor.getString(0);
        }
        cursor.close();
        return monthlyIncome;
    }

    public String getCurrentBalance() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT currentBalance FROM users", null);
        String currentBalance = "0"; // Default value
        if (cursor.moveToFirst()) {
            currentBalance = cursor.getString(0);
        }
        cursor.close();
        return currentBalance;
    }



}
