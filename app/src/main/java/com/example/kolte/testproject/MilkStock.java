package com.example.kolte.testproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MilkStock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_stock);
        final DatabaseHelper db = new DatabaseHelper(this);

        ListView resultsListView = (ListView) findViewById(R.id.results_listview);

        HashMap<String, String> nameAddresses = new HashMap<>();
        Cursor c;
        Integer i;
        final Button editPrice = (Button)findViewById(R.id.editPrice);
        final EditText price = (EditText)findViewById(R.id.newPrice);
        final Spinner milk = (Spinner)findViewById(R.id.milk);
        Switch ep =(Switch) findViewById(R.id.ep);

        ep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editPrice.setVisibility(View.VISIBLE);
                    price.setVisibility(View.VISIBLE);
                    milk.setVisibility(View.VISIBLE);
                }
                else{
                    editPrice.setVisibility(View.INVISIBLE);
                    price.setVisibility(View.INVISIBLE);
                    milk.setVisibility(View.INVISIBLE);
                }
            }
        });

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Milk_brands, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        milk.setAdapter(adapter1);

        editPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(price.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter Price",Toast.LENGTH_SHORT).show();
                }

                else{
                   db.setPrice(milk.getSelectedItem().toString(),price.getText().toString());
                   Toast.makeText(getApplicationContext(),"Price Updated Sucessfully",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MilkStock.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }

            }
        });

        c = db.stock();
        for (c.moveToFirst(), i = 0; !c.isAfterLast(); c.moveToNext(), i++) {
            String mil = "₹"+c.getString(c.getColumnIndex("Price"));
            nameAddresses.put(c.getString(c.getColumnIndex("Name")), mil);

        }
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});


        Iterator it = nameAddresses.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        resultsListView.setAdapter(adapter);

    }
}
