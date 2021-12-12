package com.yovix.tp05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NextActivity extends AppCompatActivity {
    private static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_task);
        Intent i = getIntent();
        String passedValued = i.getStringExtra("Selected Value");

        TextView res = (TextView) findViewById(R.id.res);
        res.setText(passedValued);
    }


}