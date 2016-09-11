package com.example.dsingh1.zopa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    ImageView iv;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        iv=(ImageView)findViewById(R.id.imageView);
        iv.setImageBitmap(Form.currentSite.image);
        tv=(TextView)findViewById(R.id.message);
        tv.setText(getIntent().getExtras().getString("message"));

    }
}
