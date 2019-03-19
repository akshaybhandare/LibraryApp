package com.example.libraryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.card.payment.CardIOActivity;

public class MainActivity extends AppCompatActivity {

    private Button regbutton1,savebtn1;
    private TextView savetxt1;

    String data;

    protected static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_SCAN = 100;
    private static final int REQUEST_AUTOTEST = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regbutton1 = (Button) findViewById(R.id.regbutton);



        Button regbutton;
        regbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, code_detect.class);
                startActivity(intent);


            }
        });



    }
}
