package com.example.gmldbd.smart_city;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class scanTest extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Main";

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private Button btn_Connect;
    private TextView txt_Result;

    private BluetoothService btService = null;

    private final Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_test);

        btn_Connect = (Button)findViewById(R.id.btn_connect);
        txt_Result = (TextView)findViewById(R.id.txt_result);

        btn_Connect.setOnClickListener((View.OnClickListener) this);

        if(btService==null){
            btService = new BluetoothService(this, mHandler);

        }
    }

    @Override
    public void onClick(View v){
        if(btService.getDeviceState()){
            //블루투스가 지원 가능한 기기일때
            btService.enableBluetooth();
            txt_Result.setText("블루투스가 가능한 기기입니다.");
        }
        else{
            txt_Result.setText("블루투스가 불가능한 기기입니다ㅠㅠ");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d(TAG, "onActivityResult" + resultCode);

        switch (requestCode){
            //scanning 부분
            case REQUEST_CONNECT_DEVICE:
                if(resultCode == AppCompatActivity.RESULT_OK){
                    btService.getDeviceInfo(data);
                }
                break;

            case REQUEST_ENABLE_BT:
                if(resultCode == AppCompatActivity.RESULT_OK){
                    btService.scanDevice();
                }
                else{
                    Log.d(TAG, "Bluetooth is not enabled");
                }

                break;
        }
    }
}
