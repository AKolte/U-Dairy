package com.example.kolte.testproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, "Udairy.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Customers(cid Integer primary key,cname text, pmilk text, pqty Decimal(4,2),Balance Decimal(6,2))");

        db.execSQL("CREATE TABLE Stock(Ind Integer primary key,Name text,Price Float(4,2),Stocks Integer)");
        db.execSQL("INSERT INTO Stock (Ind,Name,Price,Stocks) Values(0,'Gokul',50,50)");
        db.execSQL("INSERT INTO Stock (Ind,Name,Price,Stocks) Values(1,'Katraj',40,50)");
        db.execSQL("INSERT INTO Stock (Ind,Name,Price,Stocks) Values(2,'Chitale',44,50)");
        db.execSQL("INSERT INTO Stock (Ind,Name,Price,Stocks) Values(3,'Patanjali',40,50)");
        db.execSQL("INSERT INTO Stock (Ind,Name,Price,Stocks) Values(4,'Mother Dairy',40,50)");
        db.execSQL("INSERT INTO Stock (Ind,Name,Price,Stocks) Values(5,'Nestle',73,50)");
        db.execSQL("INSERT INTO Stock (Ind,Name,Price,Stocks) Values(6,'AMUL',39,50)");

        db.execSQL("CREATE TABLE Sales(Date Date,Qty Float(6,2),Amount Float(8,2))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Customers");
    }

    public String shopTotal() {
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT SUM(Balance) AS BAL FROM Customers";
        Cursor c = db.rawQuery(q, new String[]{});
        c.moveToFirst();
        String total = c.getString(c.getColumnIndex("BAL"));
        return total;
    }

    public Float total(String cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT Balance FROM Customers WHERE cid = " + cid;
        Cursor c = db.rawQuery(q, new String[]{});
        c.moveToFirst();
        String Total = c.getString(c.getColumnIndex("Balance"));
        return Float.parseFloat(Total);
    }

    public Boolean registerUser(String cname, String cid, String pmilk, String pqty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("cid", cid);
        contentValues.put("cname", cname);
        contentValues.put("pmilk", pmilk);
        contentValues.put("pqty", pqty);
        contentValues.put("Balance", "0");
        long ins = db.insert("Customers", null, contentValues);

        if (ins == -1) return null;

        else {
            String query = "CREATE TABLE T" + cid + " (Milk text,qty Decimal(4,2),Amount Decimal(7,2))";

            db.execSQL(query);
            return true;
        }
    }

    public Boolean chkcid(String cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM Customers WHERE cid = " + cid;
        Cursor cur = db.rawQuery(q, new String[]{});
        if (cur.getCount() > 0)
            return false;
        else return true;
    }

    public Cursor findCustomer(String cid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Customers WHERE cid = ?", new String[]{cid});
        return c;
    }

    public Boolean SaleEntry(String cid, String tablename, String milk, String qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // cv.put("Date",Date);
        cv.put("Milk", milk);
        cv.put("qty", qty);
        String amt = getPrice(milk);
        cv.put("Amount", amt);

        String tableName = "T" + cid;
        long ins = db.insert(tablename, null, cv);

        if (ins == -1) {
            return false;
        } else {
            db.execSQL("UPDATE Customers SET Balance=Balance +" + amt + " WHERE cid = " + cid);
            return true;
        }

    }

    public void addPayment(String cid, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Customers SET Balance = Balance - " + amount + " WHERE cid = " + cid;
        db.execSQL(query);

    }

    public Cursor PurchaseTable(String tablename) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tablename;
        Cursor c;
        c = db.rawQuery(query, new String[]{});
        return c;
    }

    public String totalStock() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(Stocks) AS total FROM Stock", new String[]{});
        c.moveToFirst();
        c.getColumnIndex("total");
        return c.getString(c.getColumnIndex("total"));
    }

    public Cursor stock() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Stock";
        Cursor c = db.rawQuery(query, new String[]{});
        return c;
    }

    public void updateStock(String milk, String qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Stock SET Stocks=Stocks-" + qty + " WHERE Name = '" + milk + "'";
        db.execSQL(query);

    }

    public void addStock(String milk, String qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Stock SET Stocks=Stocks+" + qty + " WHERE Name = '" + milk + "'";
        db.execSQL(query);

    }

    public String getPrice(String Milk) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT Price FROM Stock WHERE Name = '"+Milk+"'";
        Cursor c = db. rawQuery(query,new String[]{});
        c.moveToFirst();
        return  c.getString(c.getColumnIndex("Price"));

    }
}
