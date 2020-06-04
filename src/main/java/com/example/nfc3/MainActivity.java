package com.example.nfc3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter[] writeTagFilters;
    boolean writeMode;
    Tag myTag;
    Context context;

    public static final String EXTRA_TEXT = "com.example.application.nfc3.EXTRA_TEXT";
    TextView tvNFCContent;
    TextView message;
    TextView date_now;
    Button btnWrite;
    private Button clock_button;
    public TextView wake_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        Intent intent = getIntent();
        String timetext = intent.getStringExtra(MainActivity.EXTRA_TEXT);


        tvNFCContent = (TextView) findViewById(R.id.nfc_contents);
        message = (TextView) findViewById(R.id.edit_message);
        btnWrite = (Button) findViewById(R.id.button);
        clock_button = (Button) findViewById(R.id.clk_button);
        clock_button.setText("Current Wake Up Time: " + timetext);
        clock_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClockActivity();
            }
        });

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                date_now = (TextView) findViewById(R.id.date_textView);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
                                String dateString = sdf.format(date);
                                date_now.setText(dateString);
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

    }
    public void openClockActivity(){
        Intent intent = new Intent(this, Clock.class);
        startActivity(intent);
    }

}