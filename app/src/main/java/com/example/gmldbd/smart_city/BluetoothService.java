package com.example.gmldbd.smart_city;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.UUID;

import static android.support.constraint.Constraints.TAG;

public class BluetoothService {
    //Debugging
    private static final String Tag = "BluetoothService";

    //Intent request code
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    //RFCOMM Protocol(뭔지 모르겠다 공부해야함!)
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothAdapter btAdapter;

    private AppCompatActivity mActivity;
    private Handler mHandler;

//    private ConnectThread mConnectThread;
//    private ConnectedThread mConnectedThread;
    //Constructors
    public BluetoothService(AppCompatActivity ac, Handler h){
        mActivity = ac;
        mHandler = h;

        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean getDeviceState(){
        Log.i(TAG, "check the Bluetooth support");

        if(btAdapter == null){
            Log.d(TAG, "Bluetooth is not available");

            return false;
        }
        else{
            Log.d(TAG, "Bluetooth is available");

            return true;
        }
    }

    public void enableBluetooth(){
        Log.i(TAG, "Check the enabled Bluetooth");
        if(btAdapter.isEnabled()){
            // 기기의 블루투스 상태가 On인 경
            Log.d(TAG, "Bluetooth Enable Now");
            scanDevice();
        }

        else{
            Log.d(TAG, "Bluetooth Enable Request");
            //블루트스 실행의 유무를 묻는 확인창이 뜬다.
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            mActivity.startActivityForResult(i, REQUEST_ENABLE_BT);


        }
    }

    public void scanDevice(){
        Log.d(TAG, "Scan Device");

        Intent serverIntent = new Intent(mActivity, DeviceListActivity.class);
        mActivity.startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }


    public void getDeviceInfo(Intent data) {
        // Get the device MAC address
        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
        Log.d(TAG, "Get Device Info \n" + "address : " + address);
        //connect(device);
    }




}
