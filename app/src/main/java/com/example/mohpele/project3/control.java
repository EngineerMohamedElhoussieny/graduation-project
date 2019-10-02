package com.example.mohpele.project3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Moh Pele on 07/04/2017.
 */

public class control extends AppCompatActivity {
    Button auto,manula;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control);
        auto=(Button)findViewById(R.id.button);
        manula=(Button)findViewById(R.id.button2);
        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  e=new Intent(control.this,auto.class);
                startActivity(e);
            }
        });


        manula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  e=new Intent(control.this,manual.class);
                startActivity(e);
            }
        });


    }

}
