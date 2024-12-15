package com.example.smarthome;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
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






    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private TextView textView;
    private Button btnSpeak;

    private static final int NEARBY_DEVICES_PERMISSION_REQUEST_CODE = 101;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            checkAndRequestNearbyDevicesPermission();
//        } else {
//            // Nearby Devices permission not required, proceed
//            proceedToApp();
//        }

        GetDevBtn = findViewById(R.id.button);
        ConnectBtn = findViewById(R.id.button8);

        data =  findViewById(R.id.textView5);

        btnSpeak = findViewById(R.id.button13);







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


        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechToText();
            }
        });



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
                    Toast.makeText(getApplicationContext() , "D1 IS TURNED ON" , Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext() , "D1 IS TURNED OFF" , Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext() , "D2 IS TURNED ON" , Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext() , "D2 IS TURNED OFF" , Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext() , "D3 IS TURNED ON" , Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext() , "D3 IS TURNED OFF" , Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext() , "D4 IS TURNED ON" , Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext() , "D4 IS TURNED OFF" , Toast.LENGTH_SHORT).show();

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

                    Toast.makeText(getApplicationContext() , "D5 IS TURNED ON" , Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext() , "D5 IS TURNED OFF" , Toast.LENGTH_SHORT).show();

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










    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "En-US");
//        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true); // Prefer offline recognition
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "Your device does not support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null && !result.isEmpty()) {

                    Log.d("speaktest" , String.valueOf(result));

                    String command = String.valueOf(result.get(0)).trim().toUpperCase().replace(" " , "");

                    if( checkcommand(command)  )
                    {

                        char number = command.charAt(1);

                        String operation = command.substring(2);

                        if( operation.equals("OF") )
                        {
                            operation = "OFF";
                        }




                        try{
                            outputStream.write(( "D" + String.valueOf(number) + operation+"\r\n").getBytes() );
                            Toast.makeText(getApplicationContext() , "D" + String.valueOf(number) + "IS TURNED" + operation , Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext() , "OUTPUT GOING  ERROR" , Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext() , "Invalid command" , Toast.LENGTH_SHORT).show();
                    }




                }
            }
        }
    }


    public static boolean checkcommand(String command )
    {
        Log.d("testing" , command);

        if(  !(command.length()== 4 || command.length() == 5 || command.length() == 3 ) )
        {
            Log.d("testing" , "length fail");

            return  false;
        }

        String d = command.substring(0,1) ;

        if( !d.equals("M") )
        {
            return false;
        }
        char number = command.charAt(1);

        String operation;


            operation = command.substring(2);
            if(!(operation.equals("OFF") || operation.equals("OF") || operation.equals("ON") ) )
            {
                Log.d("testing" , "off fail");

                return false;
            }




        return true;

    }


    @RequiresApi(api = Build.VERSION_CODES.S)
    private void checkAndRequestNearbyDevicesPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

            // Request the permissions
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_CONNECT
            }, NEARBY_DEVICES_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted
            proceedToApp();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NEARBY_DEVICES_PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                proceedToApp();
            } else {
                closeApp();
            }
        }
    }

    private void proceedToApp() {
        // Permission granted or not required, set content view or start your app's main functionality
        setContentView(R.layout.activity_main);
    }

    private void closeApp() {
        // Close the app as permission was denied
        finishAffinity(); // Close all app activities
        System.exit(0);    // Terminate the process
    }


}