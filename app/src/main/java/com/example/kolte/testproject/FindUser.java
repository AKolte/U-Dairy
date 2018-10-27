package com.example.kolte.testproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FindUser extends AppCompatActivity {

    TableLayout table;
    EditText cid;
    TextView cname, TotalBanner;
    Button find, Payment;
    DatabaseHelper db;
    String CCid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find_user);


        cid = (EditText) findViewById(R.id.cid);
        cname = (TextView) findViewById(R.id.cname);
        find = (Button) findViewById(R.id.findButton);
        TotalBanner = (TextView) findViewById(R.id.Total);
        Payment = (Button) findViewById(R.id.pay);

        db = new DatabaseHelper(this);

        String text = "Outstanding Balance of all Customers is ₹ " + db.shopTotal();
        TotalBanner.setText(text);

        find.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {


                if (cid.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Enter Customer ID", Toast.LENGTH_SHORT).show();
                else {

                    if (!db.chkcid(cid.getText().toString())) {
                        CCid = cid.getText().toString();
                        Cursor c = db.findCustomer(cid.getText().toString());
                        Float total = db.total(cid.getText().toString());
                        Integer nameInd, cidInd, milkInd, qtyInd;
                        nameInd = c.getColumnIndex("cname");
                        cidInd = c.getColumnIndex("cid");

                        c.moveToFirst();
                        if (c.getCount() > 0) {
                            String tex = "Balance of " + c.getString(nameInd) + " is ₹ " + total.toString();
                            cname.setText(tex);
                            Payment.setVisibility(1);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Customer Found :(", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FindUser.this, com.example.kolte.testproject.Payment.class);
                Bundle b = new Bundle();
                b.putInt("cid", Integer.parseInt(CCid)); //Your id
                i.putExtras(b); //Put your id to your next Intent
                startActivity(i);
                //finish();
                // startActivity(i);
            }
        });
    }


}
