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
        final Button find = (Button) findViewById(R.id.FindPurchase);
        final TextView milkT = (TextView) findViewById(R.id.milk);
        final TextView qtyT = (TextView) findViewById(R.id.qty);
        final TextView amtT = (TextView) findViewById(R.id.tot);
        final TextView dateT = (TextView) findViewById(R.id.date);
        final DatabaseHelper db = new DatabaseHelper(this);


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = db.findCustomer(cid.getText().toString());
                Integer nameInd, amtInd, milkInd, qtyInd, dateInd;
                nameInd = c.getColumnIndex("cname");


                if (c.getCount() > 0) {
                    c.moveToFirst();
                    String tablename = "T" + cid.getText().toString();
                    Cursor purCursor = db.PurchaseTable(tablename);


                    if (purCursor.getCount() > 0) {
                        milkInd = purCursor.getColumnIndex("Milk");
                        qtyInd = purCursor.getColumnIndex("qty");
                        amtInd = purCursor.getColumnIndex("Amount");
                        dateInd = purCursor.getColumnIndex("Date");

                        String milkC = "Milk\n";
                        String QuantityC = "Quantity\n";
                        String PriceC = "Price\n";
                        String DateC = "Date\n";

                        for (purCursor.moveToFirst(); !purCursor.isAfterLast(); purCursor.moveToNext()) {

                            milkC = milkC + "\n" + purCursor.getString(milkInd);
                            DateC = DateC + "\n" + purCursor.getString(dateInd);
                            QuantityC = QuantityC + "\n" + purCursor.getString(qtyInd);
                            PriceC =PriceC +"\n" + purCursor.getString(amtInd);

                        }
                        milkT.setText(milkC);
                        qtyT.setText(QuantityC);
                        amtT.setText(PriceC);
                        dateT.setText(DateC);

                        //disp.setText(PurTable);
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "No Customer Found :(", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
