package com.example.kolte.testproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterUser extends AppCompatActivity {

    EditText cname, cid, qty;
    Button register;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        final Spinner pmilk = (Spinner) findViewById(R.id.Pref_milk);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Milk_brands, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        pmilk.setAdapter(adapter);

        db = new DatabaseHelper(this);


        cname = (EditText) findViewById(R.id.Cname);
        cid = (EditText) findViewById(R.id.Cid);
        qty = (EditText) findViewById(R.id.Pref_Qty);


        register = (Button) findViewById(R.id.RegisterCustomer);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cname.getText().toString().equals("") || cid.getText().toString().equals("") || qty.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "Please complete all the fields", Toast.LENGTH_SHORT).show();
                else {
                    if (db.chkcid(cid.getText().toString())) {
                        if (db.registerUser(cname.getText().toString(), cid.getText().toString(), pmilk.getSelectedItem().toString(), qty.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Registered!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterUser.this,MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Customer ID already used", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
