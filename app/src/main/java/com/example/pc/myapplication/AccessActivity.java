package com.example.pc.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AccessActivity extends AppCompatActivity implements View.OnClickListener, FoodAdapter.OnQuantityChange {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    FoodAdapter adapter;

    ProgressBar p_bar;
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

        getProducts();

        adapter = new FoodAdapter(this);

        adapter.setOnQuantityChange(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        emailWL = findViewById(R.id.email_wl);

        emailWL.setText(getIntent().getStringExtra("message"));

        emailWL.setOnClickListener(this);

        p_bar = findViewById(R.id.pbar);

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

    private void getProducts() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://5ba19290ee710f0014dd764c.mockapi.io/Food";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Success", response);
                        try {
                            JSONObject responseJSON = new JSONObject(response);
                            JSONArray jsonArray = responseJSON.getJSONArray("foods");

                            ArrayList<Food> foodArrayList = new ArrayList<>();

                            for (int i=0; i < jsonArray.length(); i++) {

                                Food food = new Food(jsonArray.getJSONObject(i));
                                foodArrayList.add(food);
                                }

                                adapter.setData(foodArrayList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Display the first 500 characters of the response string.
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    @Override
    public void onItemAdded(float price) {
        tot += price;
        p_bar.setProgress((int) tot);
        totalS.setText("Total : "+ tot + " €");
        enableButt();

    }

    @Override
    public void onItemRemoved(float price) {
        if (tot-price<0) return;
        tot -= price;
        p_bar.setProgress((int) tot);
        totalS.setText("Total : " + tot + " €");
        enableButt();
    }
}
