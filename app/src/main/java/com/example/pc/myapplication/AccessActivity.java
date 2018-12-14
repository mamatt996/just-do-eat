package com.example.pc.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AccessActivity extends AppCompatActivity implements View.OnClickListener {

    TextView emailWL;
    String email;
    String openedEmail;

    LinearLayout linearLL;

    TextView textvwM;
    TextView priceM;
    TextView quantityM;

    TextView textvwB;
    TextView priceB;
    TextView quantityB;

    TextView textvwT;
    TextView priceT;
    TextView quanityT;

    TextView totals;

    Button minusM;
    Button plusM;

    Button minusB;
    Button plusB;

    Button minusT;
    Button plusT;

    Button buyY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

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

        linearLL = findViewById(R.id.linearL);

        textvwM = findViewById(R.id.textVW);
        priceM = findViewById(R.id.prize);
        quantityM = findViewById(R.id.qnt);

        textvwB = findViewById(R.id.textVW2);
        priceB = findViewById(R.id.prize2);
        quantityB = findViewById(R.id.qnt2);

        textvwT = findViewById(R.id.textVW3);
        priceT = findViewById(R.id.prize3);
        quanityT = findViewById(R.id.qnt3);

        totals = findViewById(R.id.tot);

        minusM = findViewById(R.id.minus);
        plusM = findViewById(R.id.plus);

        minusB = findViewById(R.id.minus2);
        plusB = findViewById(R.id.plus2);

        minusT = findViewById(R.id.minus3);
        plusT = findViewById(R.id.plus3);

        buyY = findViewById(R.id.buy);

        linearLL.setBackgroundColor(getResources().getColor(R.color.colorBGAA));

    }

    public void onClick(View view){
        if (view.getId() == R.id.email_wl) {
            Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", getIntent().getStringExtra("message"), null));
            startActivity(Intent.createChooser(i, "Choose an Email client :"));
        }
    }
}
