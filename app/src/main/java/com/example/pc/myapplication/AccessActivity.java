package com.example.pc.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AccessActivity extends AppCompatActivity implements View.OnClickListener, FoodAdapter.OnQuantityChange {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    FoodAdapter adapter;

    double tot = 0;

    TextView emailWL;
    String email;
    String openedEmail;

    RelativeLayout relativeL;

    TextView item_TextVW;
    TextView totalS;

    Button buyY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        item_TextVW = findViewById(R.id.item_textVW);


        recyclerView = findViewById(R.id.food_rv);

        layoutManager = new LinearLayoutManager(this);


        adapter = new FoodAdapter(this, getProducts());
        adapter.setOnQuantityChange(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        emailWL = findViewById(R.id.email_wl);

        emailWL.setText(getIntent().getStringExtra("message"));

        emailWL.setOnClickListener(this);

        if (getIntent().getStringExtra("message") != null){
            email = getIntent().getStringExtra("message");
            emailWL.setText(email);
        }
        else{
            openedEmail=getIntent().getData().toString().substring(7);
            openedEmail=Uri.decode(openedEmail);
            emailWL.setText(openedEmail);
        }

        relativeL = findViewById(R.id.relative_L);

        totalS = findViewById(R.id.totals);
        buyY = findViewById(R.id.buy);

        relativeL.setBackgroundColor(getResources().getColor(R.color.colorBGAA));
    }



    public void onClick(View view){
        if (view.getId() == R.id.email_wl) {
            Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", getIntent().getStringExtra("message"), null));
            startActivity(Intent.createChooser(i, "Choose an Email Client"));
        }

    }

    public void enableButt() {
        buyY.setEnabled(tot >= 5);
    }

    private ArrayList<Food> getProducts() {
        ArrayList<Food> foodList = new ArrayList<>();
        foodList.add(new Food("Milk", 1.50F));
        foodList.add(new Food("Bread", 1.00F));
        foodList.add(new Food("Tea", 2.50F));
        foodList.add(new Food("Water", 1.00F));
        foodList.add(new Food("Snack", 1.50F));
        foodList.add(new Food("Pizza", 2.00F));
        return foodList;
    }

    @Override
    public void onItemAdded(float price) {
        tot += price;
        totalS.setText("Total "+ tot + " €");
        enableButt();

    }

    @Override
    public void onItemRemoved(float price) {
        if (tot-price<0) return;
        tot -= price;
        totalS.setText("Total : " + tot + " €");
        enableButt();
    }
}
