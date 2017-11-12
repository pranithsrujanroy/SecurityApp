package com.example.android.securityapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by ramana on 10/8/2017.
 */

public class ComplaintActivity extends AppCompatActivity {
    TextView Title,Content,Status,Time,Roll;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_view);

        Title = (TextView) findViewById(R.id.title_view);
        Spinner s = (Spinner)findViewById(R.id.spinner1);
        Status = (TextView) findViewById(R.id.status_view);
        Content=(TextView) findViewById(R.id.content_view);
        Roll=(TextView) findViewById(R.id.roll_number_view);
        Title.setText(getIntent().getStringExtra("title"));
        Content.setText(getIntent().getStringExtra("content"));
       // Status.setText(getIntent().getStringExtra("status"));
        Roll.setText(getIntent().getStringExtra("roll"));
    }

}
