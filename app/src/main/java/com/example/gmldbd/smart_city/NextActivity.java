package com.example.gmldbd.smart_city;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Intent;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.UUID;

public class NextActivity extends AppCompatActivity {

    TextView textView;

    public static final String KEY_SIMPLE_DATA = "data";

    BluetoothManager bleManager;
    BluetoothAdapter bleAdapter;
    BluetoothLeAdvertiser bleAdvertiser;
    AdvertiseSettings bleAdvertiseSettings;
    AdvertiseData bleAdvertiseData;

    boolean advFlag = true;

    AdvertiseCallback myAdvertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            Log.v("TAG", "Advertise start succeeds: " + settingsInEffect.toString());
            //myTV.append("\nAdvertisement restarted successfully with new data.");
            textView.invalidate();
        }

        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            Log.v("Tag", "Advertise start failed: " + errorCode);
            textView.append("\nAdvertisement restart failed: code = " + errorCode);
            textView.invalidate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        textView = findViewById(R.id.textView);
        bleManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        bleAdapter = bleManager.getAdapter();
        bleAdvertiser = bleAdapter.getBluetoothLeAdvertiser();

        bleAdvertiseSettings = new AdvertiseSettings.Builder()
                .setAdvertiseMode( AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY )
                .setTxPowerLevel( AdvertiseSettings.ADVERTISE_TX_POWER_HIGH )
                .setConnectable( false )
                .setTimeout(0)
                .build();

        if(!bleAdapter.isMultipleAdvertisementSupported()){
            textView.setText("Device does not support BLE advertisement.");
            textView.invalidate();
            return;
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", "mike");
                setResult(RESULT_OK, intent);

                finish();
            }
        });

        Intent intent = getIntent();
        processIntent(intent);
    }

    private void processIntent(Intent intent){
        if(intent != null){
            Bundle bundle = intent.getExtras();
            UserInfo uInfo = bundle.getParcelable(KEY_SIMPLE_DATA);
            if(intent != null){
                final String advData = uInfo.name + uInfo.birth + uInfo.gender + uInfo.num;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ParcelUuid pUuid = new ParcelUuid( UUID.fromString( getString( R.string.ble_uuid )));
                        advFlag = true;
                        for(int i = 0; i < 1000000 && advFlag == true; i++) {
                            final String advInfo = advData;
                            final String advNum = String.valueOf(i);

                            bleAdvertiseData = new AdvertiseData.Builder()
                                    //.addServiceUuid(pUuid)
                                    .addServiceData(pUuid, advInfo.getBytes(Charset.forName("UTF-8")))
                                    .setIncludeDeviceName(false)
                                    .setIncludeTxPowerLevel(false)
                                    .build();

                            try {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        textView.setText("Service UUID: " + R.string.ble_uuid + "\n"
                                                + "Service Data: " + advInfo
                                                + "\n시도 횟수: " + advNum);
                                        textView.invalidate();
                                    }
                                });

                                bleAdvertiser.startAdvertising(bleAdvertiseSettings, bleAdvertiseData, myAdvertiseCallback);
                                Thread.sleep(1000);
                                bleAdvertiser.stopAdvertising(myAdvertiseCallback);
                                Log.v("Tag", "Advertise: " + advInfo);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            }
        }
    }
}
