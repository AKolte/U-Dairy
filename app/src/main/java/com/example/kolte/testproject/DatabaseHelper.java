package com.example.kolte.testproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper{


    public DatabaseHelper(Context context) {
        super(context, "Udairy.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Customers(cid Integer primary key,cname text, pmilk text, pqty Decimal(4,2))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Customers");
    }

    public Boolean registerUser(String cname, String cid, String pmilk, String pqty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("cid",cid);
        contentValues.put("cname",cname);
        contentValues.put("pmilk",pmilk);
        contentValues.put("pqty",pqty);

        long ins = db.insert("Customers", null,contentValues);

        if(ins==-1) return null;

        else {
            String query = "CREATE TABLE "+ cname+cid  +" (Milk text,qty Decimal(4,2),Amount Decimal(7,2))";

            db.execSQL(query);
            return true;
            }
        }

    public Boolean chkcid (String cid){
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM Customers WHERE cid = "+ cid;
        Cursor cur = db.rawQuery(q,new String[] {});
        if(cur.getCount()>0)
            return false;
        else return true;
    }

    public Cursor findCustomer(String cid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Customers WHERE cid = ?",new String[] {cid});
        return  c;
    }

    public Boolean SaleEntry(String cid,String tablename, String milk,String qty,String amt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

       // cv.put("Date",Date);
        cv.put("Milk",milk);
        cv.put("qty",qty);
        cv.put("Amount",amt);

       // String tableName=cname+cid;
        long ins = db.insert(tablename,null,cv);

        if(ins==-1){
            return false;
        }
        else return true;

    }

    public Cursor PurchaseTable(String tablename){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+ tablename ;
        Cursor c;
        c = db.rawQuery(query,new String[] {});
        return c;

    }
}
