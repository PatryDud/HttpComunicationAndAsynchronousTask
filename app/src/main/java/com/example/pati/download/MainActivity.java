package com.example.pati.download;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
protected static Button downloadInfoButton, downloadButton;
protected static EditText adresUrlEditText, sizeEditText, typeEditText, amountOfBytesEditText;
    public static final String ACTION_NEW_MSG = "pl.froger.hello.broadcastreceiver.NEW_MSG";
    public static final String MSG_FIELD = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialisationOfViews();
        settingListeners();
    }

    public static  Broadcastreceiver broadcastreceiver =new Broadcastreceiver(){
        public void onReceive(Context context, Intent intent) {
            ProgressInfo progressInfo = intent.getExtras().getParcelable(DownloadingFileService.INFO);

           amountOfBytesEditText.setText(String.valueOf(progressInfo.mDownloadedBytes));
        }

        };

    @Override //zarejestrowanie odbiorcy
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastreceiver, new IntentFilter(
                DownloadingFileService.POWIADOMIENIE));
    }
    @Override //wyrejestrowanie odbiorcy
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastreceiver);
    }


    private void initialisationOfViews(){
        downloadInfoButton= (Button) findViewById(R.id.button_info);
        downloadButton= (Button) findViewById(R.id.button_download);
        adresUrlEditText= (EditText) findViewById(R.id.adres);
        sizeEditText= (EditText) findViewById(R.id.size_of_file);
        typeEditText = (EditText) findViewById(R.id.type_of_file);
        amountOfBytesEditText= (EditText) findViewById(R.id.amount_of_bytes);

    }

    private void settingListeners() {
        downloadInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadingTask downloadingTask = new DownloadingTask();
                downloadingTask.execute("http://androhub.com/demo/demo.pdf");
            }
        });
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadingFileService.startService(MainActivity.this,0);
                Toast.makeText(getApplicationContext(),"dziala", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isConnectionToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
