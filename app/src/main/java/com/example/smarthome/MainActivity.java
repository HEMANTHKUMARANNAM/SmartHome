package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button GetDevBtn, ConnectBtn;

    Button setautomation,setmanual;

    Button D1ON,D1OFF,D2ON,D2OFF,D3ON,D3OFF,D4ON,D4OFF,D5ON,D5OFF;

    TextView ResultTxt , data;


    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice bluetoothDevice;
    BluetoothSocket bluetoothSocket;

    IntentFilter intentFilter;

    InputStream inputStream;
    OutputStream outputStream;

    Rxthread rxthread;

    String Rxdata = "";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetDevBtn = findViewById(R.id.button);
        ConnectBtn = findViewById(R.id.button8);

        data =  findViewById(R.id.textView5);







        D1ON = findViewById(R.id.button2);
        D1OFF = findViewById(R.id.button3);
        D2ON = findViewById(R.id.button4);
        D2OFF = findViewById(R.id.button5);
        D3ON = findViewById(R.id.button6);
        D3OFF = findViewById(R.id.button7);
        D4ON = findViewById(R.id.button9);
        D4OFF = findViewById(R.id.button10);
        D5ON = findViewById(R.id.button11);
        D5OFF = findViewById(R.id.button12);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter() ;
        intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        rxthread = new Rxthread();




        registerReceiver(Btreceiver, intentFilter );

        ConnectBtn.setEnabled(false);



        GetDevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String a = "";
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();

                for (BluetoothDevice dev : devices) {
                    if (dev.getName().equals("HC-05")) {
                        a = a + dev.getName();
                        bluetoothDevice = dev;
                        bluetoothAdapter.cancelDiscovery();
                        break;
                    }
                }

                data.setText(a);

            }
        });

        ConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                    bluetoothSocket.connect();
                    inputStream = bluetoothSocket.getInputStream();
                    outputStream = bluetoothSocket.getOutputStream();
                    rxthread.start();


                    Toast.makeText(getApplicationContext() , "CONNECTED" , Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(getApplicationContext() , "ERROR" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        D1ON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    outputStream.write(("D1ON"+"\r\n").getBytes() );
                }catch (Exception e){
                    Toast.makeText(getApplicationContext() , "OUTPUT GOING  ERROR" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        D1OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    outputStream.write(("D1OFF"+"\r\n").getBytes() );
                }catch (Exception e){
                    Toast.makeText(getApplicationContext() , "OUTPUT GOING  ERROR" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        D2ON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    outputStream.write(("D2ON"+"\r\n").getBytes() );
                }catch (Exception e){
                    Toast.makeText(getApplicationContext() , "OUTPUT GOING  ERROR" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        D2OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    outputStream.write(("D2OFF"+"\r\n").getBytes() );
                }catch (Exception e){
                    Toast.makeText(getApplicationContext() , "OUTPUT GOING  ERROR" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        D3ON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    outputStream.write(("D3ON"+"\r\n").getBytes() );
                }catch (Exception e){
                    Toast.makeText(getApplicationContext() , "OUTPUT GOING  ERROR" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        D3OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    outputStream.write(("D3OFF"+"\r\n").getBytes() );
                }catch (Exception e){
                    Toast.makeText(getApplicationContext() , "OUTPUT GOING  ERROR" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        D4OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    outputStream.write(("D4OFF"+"\r\n").getBytes() );
                }catch (Exception e){
                    Toast.makeText(getApplicationContext() , "OUTPUT GOING  ERROR" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        D4ON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    outputStream.write(("D4ON"+"\r\n").getBytes() );
                }catch (Exception e){
                    Toast.makeText(getApplicationContext() , "OUTPUT GOING  ERROR" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        D5ON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    outputStream.write(("D5ON"+"\r\n").getBytes() );
                }catch (Exception e){
                    Toast.makeText(getApplicationContext() , "OUTPUT GOING  ERROR" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        D5OFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    outputStream.write(("D5OFF"+"\r\n").getBytes() );
                }catch (Exception e){
                    Toast.makeText(getApplicationContext() , "OUTPUT GOING  ERROR" , Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    class Rxthread extends Thread{

        public boolean isRunning;
        byte[] rx;
        Rxthread(){
            isRunning = true;
            rx = new byte[10];
        }
        @Override
        public void run()
        {
            while( isRunning )
            {
                try{
                    if( inputStream.available() >2 )
                    {
                        inputStream.read(rx , 0 , 50);
                        Rxdata = new String(rx);
                        Log.d("meow" , Rxdata );
                    }

                    Thread.sleep(10);

                }catch (Exception e){
                    Log.d("meow" , "data not got" );
                }
            }
        }




    }


    BroadcastReceiver Btreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ConnectBtn.setEnabled(true);
                        }
                    });
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    rxthread.isRunning = false;
                    break;
                default:
                    break;
            }
        }
    };


}