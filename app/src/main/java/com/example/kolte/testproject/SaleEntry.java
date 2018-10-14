package com.example.kolte.testproject;

import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class SaleEntry extends AppCompatActivity {

    EditText cid,qty;
    TextView cname,customerID;
    DatabaseHelper db;
    Button find,makeSaleEntry;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_entry);

        final Spinner pmilk = (Spinner) findViewById(R.id.milk);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Milk_brands, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        pmilk.setAdapter(adapter);

        cid = (EditText) findViewById(R.id.cid);
        qty= (EditText) findViewById(R.id.qty);

        customerID = (TextView) findViewById(R.id.customerID);
        cname = (TextView) findViewById(R.id.Cname);

        find = (Button) findViewById(R.id.FindButton);
        makeSaleEntry = (Button) findViewById(R.id.saleEntry);
        db = new DatabaseHelper(this);

        qty.setEnabled(false);
        makeSaleEntry.setEnabled(false);
        pmilk.setEnabled(false);
        cname.setEnabled(false);
        customerID.setEnabled(false);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cid.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Enter Customer ID",Toast.LENGTH_SHORT).show();
                else {
                    Cursor c = db.findCustomer(cid.getText().toString());
                    Integer nameInd, cidInd, milkInd, qtyInd;
                    nameInd = c.getColumnIndex("cname");
                    cidInd = c.getColumnIndex("cid");
                    milkInd = c.getColumnIndex("pmilk");
                    qtyInd = c.getColumnIndex("pqty");

                    if (c.getCount() > 0) {

                        qty.setEnabled(true);
                        makeSaleEntry.setEnabled(true);
                        pmilk.setEnabled(true);
                        cname.setEnabled(true);
                        customerID.setEnabled(true);

                        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                            qty.setText(c.getString(qtyInd));
                            cname.setText(c.getString(nameInd));
                            customerID.setText(c.getString(cidInd));
                            pmilk.setSelection(getIndex(pmilk, c.getString(milkInd)));
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(),"No Customer found :(",Toast.LENGTH_SHORT).show();

                }
            }
        });

        makeSaleEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String cnameS,cidS,qtyS,milkS;

              cnameS = cname.getText().toString();
              cidS = customerID.getText().toString();
              qtyS = qty.getText().toString();
              milkS = pmilk.getSelectedItem().toString();

              Float amt = Float.valueOf(qty.getText().toString());
              amt= amt*50;

              //Toast.makeText(getApplicationContext(),amt.toString(),Toast.LENGTH_SHORT).show();
                String quantity = qty.getText().toString();
                String Amount = amt.toString();
                String tablename = cnameS+cidS;

                if(db.SaleEntry(cidS,tablename,milkS,quantity,Amount))
                    Toast.makeText(getApplicationContext(),"Sale Entry Sucessful!!!",Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(getApplicationContext(),"Something went wronng :(",Toast.LENGTH_SHORT).show();

                }
                cname.setText("");
                cid.setText("");
                customerID.setText("");
                qty.setText("");
                pmilk.setSelection(1,true);
            }
        });

    }

    private int getIndex (Spinner spinner, String myString){
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;

    }
}
