package com.example.kolte.testproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button register, login, sale_entry, purchase,table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = (Button) findViewById(R.id.TableButton);
        register = (Button) findViewById(R.id.Register);
        login = (Button) findViewById(R.id.Login);
        sale_entry = (Button) findViewById(R.id.Sale_entry);
        purchase = (Button) findViewById(R.id.PurchaseTableButton);

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

        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, tableActivity.class);
                startActivity(i);
            }
        });


    }
}
