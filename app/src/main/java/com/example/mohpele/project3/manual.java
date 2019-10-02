package com.example.mohpele.project3;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.io.OutputStream;

/**
 * Created by Moh Pele on 07/04/2017.
 */

public class manual extends AppCompatActivity {
    private final String DEVICE_ADDRESS = "98:D3:32:70:A4:17"; //MAC Address of Bluetooth Module
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    BroadcastReceiver mReceiver;
    String command; //string variable that will store value to be transmitted to the bluetooth module
    Button up_forward, up_left, up_right, down_backward, down_right, down_left, connect;
     String s;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual);
        initViews();
        initReciver();
        ConfigureButtons(s);
    }



    private void ConfigureButtons(String s) {




       up_forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) //MotionEvent.ACTION_DOWN is when you hold a button down
                {

                    Toast.makeText(getBaseContext(), "Test", Toast.LENGTH_LONG).show();
                    command = "1";

                    try {
                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "0";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                return false;
            }
        });

        down_backward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    command = "2";

                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    command = "0";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {

                    }

                }
                return false;
            }
        });


        up_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    command = "3";

                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    command = "0";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {

                    }

                }
                return false;
            }
        });

        up_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    command = "6";

                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    command = "0";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {

                    }

                }
                return false;
            }
        });

        down_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    command = "8";

                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    command = "0";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {

                    }

                }
                return false;
            }
        });

        down_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    command = "9";

                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    command = "0";
                    try {
                        outputStream.write(command.getBytes());
                    } catch (IOException e) {

                    }

                }
                return false;
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
        up_forward = (Button) findViewById(R.id.up_forward);
        up_right = (Button) findViewById(R.id.up_right);
        up_left = (Button) findViewById(R.id.up_left);
        down_right = (Button) findViewById(R.id.down_right);
        down_left = (Button) findViewById(R.id.down_left);
        down_backward = (Button) findViewById(R.id.down_backward);
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



                /*
                * //byte[] buffer = new byte[256];  // buffer store for the stream
                //int bytes; // bytes returned from read()


                outputStream = socket.getOutputStream(); //gets the output stream of the socket
              /*  inputStream=socket.getInputStream();
                DataInputStream mmInStream = new DataInputStream(inputStream);
                DataOutputStream mmOutStream = new DataOutputStream(outputStream);
                bytes = mmInStream.read(buffer);
                String readMessage = new String(buffer, 0, bytes);
                Toast.makeText(getApplicationContext(),
                        readMessage, Toast.LENGTH_LONG).show();*/

                *
                *
                *
                *
                *
                *
                *
                * */

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return connected;
    }
}