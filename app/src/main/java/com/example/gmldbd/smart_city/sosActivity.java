package com.example.gmldbd.smart_city;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class sosActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU = 101;
    public static final String KEY_SIMPLE_DATA = "data";

    EditText editName;
    EditText editBirth;
    EditText editGender;
    EditText editNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        editName = findViewById(R.id.editName);
        editBirth = findViewById(R.id.editBirth);
        editGender = findViewById(R.id.editGender);
        editNum = findViewById(R.id.editNum);

        Button advertising_btn = findViewById(R.id.advertising_btn);
        advertising_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String birth = editBirth.getText().toString();
                String gender = editGender.getText().toString();
                String num = editNum.getText().toString();

                Intent intent = new Intent(getApplicationContext(), NextActivity.class);
                UserInfo uInfo = new UserInfo(name, birth, gender, num);
                intent.putExtra(KEY_SIMPLE_DATA, uInfo);
                startActivityForResult(intent, REQUEST_CODE_MENU);
            }
        });
    }
}
