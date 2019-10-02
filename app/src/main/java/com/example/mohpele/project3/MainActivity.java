package com.example.mohpele.project3;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button control,about,map,connect,nav,hist;
    private final String DEVICE_ADDRESS = "98:D3:32:70:A4:17"; //MAC Address of Bluetooth Module
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    BroadcastReceiver mReceiver;
    String command; //string variable that will store value to be transmitted to the bluetooth module
     private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
connect=(Button)findViewById(R.id.button5);
        hist=(Button)findViewById(R.id.button9);
        nav=(Button)findViewById(R.id.button8);
        control=(Button) findViewById(R.id.button11);
        about=(Button) findViewById(R.id.button10);
        initViews();
        initReciver();
        map=(Button)findViewById(R.id.button6);
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,control.class);
                startActivity(i);
            }
        });
        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "no history found ", Toast.LENGTH_LONG).show();
            }
        });
nav.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this,
                "it will be future work ", Toast.LENGTH_LONG).show();
    }
});


        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,about.class);
                startActivity(i);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,map.class);
                startActivity(i);
            }
        });


    }


    private void initReciver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(mReceiver, filter);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (device.ACTION_FOUND.equals(action)) {
                    //Device found
                } else if (device.ACTION_ACL_CONNECTED.equals(action)) {
                    //Device is now connected

                    btConnected();
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    //Done searching
                } else if (device.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                    //Device is about to disconnect
                    btnDisConnected();
                } else if (device.ACTION_ACL_DISCONNECTED.equals(action)) {
                    //Device has disconnected
                    btnDisConnected();
                }
            }
        };
    }

    private void btnDisConnected() {
        connect.setBackgroundColor(Color.parseColor("#F00"));
        connect.setText("Disconnected");
    }

    private void btConnected() {
        connect.setBackgroundColor(Color.parseColor("#7CFC00"));
        connect.setText("Connected");
    }

    private void initViews() {

        connect = (Button) findViewById(R.id.connect);

    }

    public void onConnect(View view) {
        if (BTinit()) {
            BTconnect();
        }
    }

    public boolean BTinit() {
        boolean found = false;

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) //Checks if the device supports bluetooth
        {
            Toast.makeText(getApplicationContext(), "Device doesn't support bluetooth", Toast.LENGTH_SHORT).show();
        }

        if (!bluetoothAdapter.isEnabled()) //Checks if bluetooth is enabled. If not, the program will ask permission from the user to enable it
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if (bondedDevices.isEmpty()) //Checks for paired bluetooth devices
        {
            Toast.makeText(getApplicationContext(), "Please pair the device first", Toast.LENGTH_SHORT).show();
        } else {
            for (BluetoothDevice iterator : bondedDevices) {
                if (iterator.getAddress().equals(DEVICE_ADDRESS)) {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }

        return found;
    }

    public boolean BTconnect() {
        boolean connected = true;

        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID); //Creates a socket to handle the outgoing connection
            socket.connect();

            Toast.makeText(getApplicationContext(),
                    "Connection to bluetooth device successful", Toast.LENGTH_LONG).show();
            btConnected();
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
            btnDisConnected();
        }

        if (connected) {
            try {
                outputStream = socket.getOutputStream(); //gets the output stream of the socket
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return connected;
    }
}
