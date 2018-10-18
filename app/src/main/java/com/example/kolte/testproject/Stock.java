package com.example.kolte.testproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Stock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        final Spinner pmilk = (Spinner) findViewById(R.id.milk);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Milk_brands, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        pmilk.setAdapter(adapter);

        TextView msg = (TextView) findViewById(R.id.message);
        final Button addStock = (Button) findViewById(R.id.addStock);
        final Button newAdd = (Button) findViewById(R.id.newAdd);
        final EditText qty = (EditText) findViewById(R.id.qty);



        final DatabaseHelper db = new DatabaseHelper(this);
        String stock = db.totalStock();

        String message = "Your total Stock is " + stock + " Litres.";
        msg.setText(message);

        addStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStock.setVisibility(View.INVISIBLE);
                newAdd.setVisibility(View.VISIBLE);
                pmilk.setVisibility(View.VISIBLE);
                qty.setVisibility(View.VISIBLE);
            }
        });

        newAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addStock(pmilk.getSelectedItem().toString(),qty.getText().toString());
                Toast.makeText(getApplicationContext(),"Stock Updated!",Toast.LENGTH_SHORT).show();
                newAdd.setVisibility(View.INVISIBLE);
                qty.setVisibility(View.INVISIBLE);
                pmilk.setVisibility(View.INVISIBLE);
                addStock.setVisibility(View.VISIBLE);
                update();
            }
        });



        ListView resultsListView = (ListView) findViewById(R.id.results_listview);

        HashMap<String, String> nameAddresses = new HashMap<>();
        Cursor c;
        Integer i;
        c = db.stock();
        for (c.moveToFirst(), i = 0; !c.isAfterLast(); c.moveToNext(), i++) {
            String mil = c.getString(c.getColumnIndex("Stocks")) + " Litres";
            nameAddresses.put(c.getString(c.getColumnIndex("Name")), mil);

        }
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter1 = new SimpleAdapter(this, listItems, R.layout.list_item,
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

        resultsListView.setAdapter(adapter1);

    }
    public void update(){
        DatabaseHelper db = new DatabaseHelper(this);

        ListView resultsListView = (ListView) findViewById(R.id.results_listview);

        HashMap<String, String> nameAddresses = new HashMap<>();
        Cursor c;
        Integer i;

        c = db.stock();
        for (c.moveToFirst(), i = 0; !c.isAfterLast(); c.moveToNext(), i++) {
            String mil = c.getString(c.getColumnIndex("Stocks")) + " Litres";
            nameAddresses.put(c.getString(c.getColumnIndex("Name")), mil);

        }
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter1 = new SimpleAdapter(this, listItems, R.layout.list_item,
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

        resultsListView.setAdapter(adapter1);

    }

}
