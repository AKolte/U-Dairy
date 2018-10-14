package com.example.kolte.testproject;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FindUser extends AppCompatActivity {

    TableLayout table;
    EditText cid;
    TextView cname;
    Button find;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

    cid = (EditText) findViewById(R.id.cid);
    cname = (TextView) findViewById(R.id.cname);
    find = (Button) findViewById(R.id.findButton);
    db = new DatabaseHelper(this);



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
                    for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                        cname.setText(c.getString(nameInd) + " " + c.getString(cidInd) + " " + c.getString(milkInd) + " " + c.getString(qtyInd));

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"No Customer Found :(",Toast.LENGTH_SHORT).show();
                }

            }
        }
    });
    }


}
