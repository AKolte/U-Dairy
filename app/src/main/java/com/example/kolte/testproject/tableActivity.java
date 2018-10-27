package com.example.kolte.testproject;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class tableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);


        final EditText cid = (EditText) findViewById(R.id.cid);
        final Button find = (Button) findViewById(R.id.FindPurchase);
        //  final TextView disp = (TextView) findViewById(R.id.Disp);
        final DatabaseHelper db = new DatabaseHelper(this);


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = db.findCustomer(cid.getText().toString());
                Integer nameInd, amtInd, milkInd, qtyInd;
                nameInd = c.getColumnIndex("cname");


                if (c.getCount() > 0) {
                    c.moveToFirst();
                    String tablename = "T" + cid.getText().toString();
                    Cursor purCursor = db.PurchaseTable(tablename);

                    if (purCursor.getCount() > 0) {
                        makeTable(purCursor);
                        //disp.setText(PurTable);
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();

                    }
                } else
                    Toast.makeText(getApplicationContext(), "No Customer Found :(", Toast.LENGTH_SHORT).show();
            }

        });


    }

    public void makeTable(Cursor PurCur) {
        final TableLayout tableLayout = (TableLayout) findViewById(R.id.table);

        Integer nameInd, amtInd, milkInd, qtyInd;
        milkInd = PurCur.getColumnIndex("Milk");
        qtyInd = PurCur.getColumnIndex("qty");
        amtInd = PurCur.getColumnIndex("Amount");
        String PurTable = "";

        for (PurCur.moveToFirst(); !PurCur.isAfterLast(); PurCur.moveToNext()) {
            // PurTable=PurTable+purCursor.getString(milkInd) + " " + purCursor.getString(qtyInd) + " " + purCursor.getString(amtInd)+"\n";


            // Creation row
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

            // Creation textView
            TextView milk = new TextView(this);

            TextView qty = new TextView(this);
            TextView amt = new TextView(this);


            milk.setText(PurCur.getString(milkInd));
            milk.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

            qty.setText(PurCur.getString(qtyInd));
            qty.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

            amt.setText(PurCur.getString(amtInd));
            amt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

            tableRow.addView(milk);
            tableRow.addView(qty);
            tableRow.addView(amt);
            // tableRow.addView(milk);

            //tableLayout.setGravity();
            tableLayout.addView(tableRow);


        }
    }
}