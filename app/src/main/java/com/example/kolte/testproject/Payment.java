package com.example.kolte.testproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Payment extends AppCompatActivity {
    DatabaseHelper db = new DatabaseHelper(this);
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Bundle b = getIntent().getExtras();
        value = b.getInt("cid");

        final Button addPayment = (Button) findViewById(R.id.addPayment);
        TextView info = findViewById(R.id.info);
        final EditText amt = (EditText) findViewById(R.id.amount);

        final String cid = String.valueOf(value);
        final Cursor c = db.findCustomer(cid);
        c.moveToFirst();

        final Integer nameInd = c.getColumnIndex("cname");
        final Integer balInd = c.getColumnIndex("Balance");

        String text = "Pending Amount for " + c.getString(nameInd) + " is ₹ " + c.getString(balInd);
        info.setText(text);

        addPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),amt.getText().toString(),Toast.LENGTH_SHORT).show();
                if (amt.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter amount", Toast.LENGTH_SHORT).show();
                } else {
                    popup(c.getString(nameInd), amt.getText().toString());
                }
            }
        });
    }

    public void popup(String name, String amt) {
        ExampleDialog exampleDialog = new ExampleDialog();

        Bundle b = new Bundle();
        b.putString("name", name);
        b.putString("amt", amt);
        b.putString("cid", String.valueOf(value));
        exampleDialog.setArguments(b);
        exampleDialog.show(getSupportFragmentManager(), "Example Dialog");

    }
}