package com.pitaka.app.pitaka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.widget.Toast.makeText(MainActivity.this, "hsjddkd", Toast.LENGTH_SHORT).show();
    }
}
