package com.example.kolte.testproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button register, login, sale_entry, purchase, table, stock, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);

        // table = (Button) findViewById(R.id.TableButton);
        register = (Button) findViewById(R.id.Register);
        login = (Button) findViewById(R.id.Login);
        sale_entry = (Button) findViewById(R.id.Sale_entry);
        purchase = (Button) findViewById(R.id.PurchaseTableButton);
        stock = (Button) findViewById(R.id.stock);
        settings = (Button) findViewById(R.id.settings);
        TextView stockBanner = (TextView) findViewById(R.id.StockBanner);
        TextView AmountBanner = (TextView) findViewById(R.id.amountBanner);


        String Banner = "Remaining milk Stock is "+db.totalStock()+" Litres";
        stockBanner.setText(Banner);

        Banner = "Total pending Bill of all Customers is ₹"+db.shopTotal();
        AmountBanner.setText(Banner);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MilkStock.class);
                startActivity(i);
            }
        });

        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Stock.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterUser.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FindUser.class);
                startActivity(i);
            }
        });

        sale_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SaleEntry.class);
                startActivity(i);
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Purchase.class);
                startActivity(i);
            }
        });


    }
}
