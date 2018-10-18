package com.example.kolte.testproject;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
        DatabaseHelper db = new DatabaseHelper(this);

        ListView resultsListView = (ListView) findViewById(R.id.results_listview);

        HashMap<String, String> nameAddresses = new HashMap<>();
        Cursor c;
        Integer i;
        c = db.stock();
        for(c.moveToFirst(),i=0;!c.isAfterLast();c.moveToNext(),i++)
        {
            String mil=c.getString(c.getColumnIndex("Stocks"))+" Litres";
            nameAddresses.put(c.getString(c.getColumnIndex("Name")),mil);

        }
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});


        Iterator it = nameAddresses.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        resultsListView.setAdapter(adapter);

    }
}
