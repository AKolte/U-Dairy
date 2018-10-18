package com.example.kolte.testproject;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Purchase extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
         final EditText cid = (EditText) findViewById(R.id.cid);
        final Button find =  (Button) findViewById(R.id.FindPurchase);
       final TextView disp = (TextView) findViewById(R.id.Disp);
       final TextView total = (TextView) findViewById(R.id.total);
        final DatabaseHelper db = new DatabaseHelper(this);


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = db.findCustomer(cid.getText().toString());
                Integer nameInd, amtInd, milkInd, qtyInd,balInd;
                nameInd = c.getColumnIndex("cname");


                if (c.getCount() > 0) {
                    c.moveToFirst();
                    String tablename= "T"+cid.getText().toString();
                    Cursor purCursor = db.PurchaseTable(tablename);

                    Float tot=  db.total(cid.getText().toString());
                    total.setText(tot.toString());

                    if(purCursor.getCount()>0){
                        milkInd = purCursor.getColumnIndex("Milk");
                        qtyInd = purCursor.getColumnIndex("qty");
                        amtInd = purCursor.getColumnIndex("Amount");

                        String PurTable="";



                        for (purCursor.moveToFirst(); !purCursor.isAfterLast(); purCursor.moveToNext()) {
                             PurTable=PurTable+purCursor.getString(milkInd) + " " + purCursor.getString(qtyInd) + " " + purCursor.getString(amtInd)+"\n";
                        }

                        disp.setText(PurTable);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Something went wrong :(",Toast.LENGTH_SHORT).show();
                    }
                }

                else
                    Toast.makeText(getApplicationContext(),"No Customer Found :(",Toast.LENGTH_SHORT).show();

                }
        });


    }
}
