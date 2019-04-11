package com.example.gmldbd.smart_city;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        구조자버튼
        Button search_Bt = (Button)findViewById(R.id.search_bt);
//        조난자버튼
        Button SOS_bt = (Button)findViewById(R.id.SOS_bt);

        ImageView banner = (ImageView)findViewById(R.id.imageView);


        search_Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Search_intent = new Intent(MainActivity.this ,SearchActivity.class);
                startActivity(Search_intent);
            }
        });

        SOS_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SOS_intent = new Intent(MainActivity.this, sosActivity.class);
                startActivity(SOS_intent);
            }
        });

    }
}
