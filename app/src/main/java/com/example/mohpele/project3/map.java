package com.example.mohpele.project3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Moh Pele on 06/05/2017.
 */

public class map extends Activity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        imageView=(ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.mapimage);
    }

}
