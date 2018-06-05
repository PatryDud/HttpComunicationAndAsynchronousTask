package com.example.pati.download;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

/**
 * Created by Pati on 03.06.2018.
 */

public class DownloadingFileService extends IntentService {
    File apkStorage = null;
    File outputFile = null;
    private Context context;
    private String adres ="http://androhub.com/demo/demo.pdf";
    private String downloadFileName = "coscos";
    private int downloadedBytes=0;

    public DownloadingFileService(){
        super("DownloadingFileService");

    }

    private static final String AKCJA_ZADANIE1 =
            "com.example.pati.download.action.zadanie1";
    //tekstowe identyfikatory parametrów potrzebnych do
    //wykonania akji (może być więcej niż jeden)
    private static final String PARAMETR1 =
            "com.example.intent_service.extra.parametr1";
    //statyczna metoda pomocnicza uruchamiająca zadanie (oczywiście parametrów może być
    //więcej)
    public static void startService (Context context, int parametr) {
        Intent zamiar = new Intent(context, DownloadingFileService.class);
        zamiar.setAction(AKCJA_ZADANIE1);
        zamiar.putExtra(PARAMETR1, parametr);
        context.startService(zamiar);
    }

    //metoda wykonująca zadanie


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            //sprawdzenie o jaką akcję chodzi
            if (AKCJA_ZADANIE1.equals(action)) {
                final int param1 = intent.getIntExtra(PARAMETR1,0);
                //wykonanie zadania
                wykonajZadanie(param1);
            } else {
                Log.e("intent_service","nieznana akcja");
            }
        }
        Log.d("intent_service","usługa wykonała zadanie");
    }
    private void wykonajZadanie(int parametr) {
        //kod faktycznie wykonujący zadanie...


        try {
            URL url = new URL(adres);//Create Download URl
            HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
            c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
            c.connect();//connect the URL Connection

            //If Connection response is not OK then show Logs
            if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                        + " " + c.getResponseMessage());

            }


            //Get File if SD card is present
            if (new CheckForSDCard().isSDCardPresent()) {

                apkStorage = new File(
                        Environment.getExternalStorageDirectory() + "/DirecotryDownload");
            } else
                Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

            //If File is not present create directory
            if (!apkStorage.exists()) {
                apkStorage.mkdir();
                Log.e(TAG, "Directory Created.");
            }

            outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

            //Create New File if not present
            if (!outputFile.exists()) {
                outputFile.createNewFile();
                Log.e(TAG, "File Created");
            }

            FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

            InputStream is = c.getInputStream();//Get InputStream for connection
            Intent zamiar = new Intent(POWIADOMIENIE);
            String result ="pobierania trwa";
            int downloadedBytes=0;
            ProgressInfo progressInfo = new ProgressInfo(0,c.getContentLength(), result);
            byte[] buffer = new byte[1024];//Set buffer type
            int len1 = 0;//init length

            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);//Write new file
                downloadedBytes+=len1;

                    progressInfo.mDownloadedBytes = downloadedBytes;
                    progressInfo.mSize = c.getContentLength();
                    zamiar.putExtra(INFO, progressInfo);
                    sendBroadcast(zamiar);

            }

            progressInfo.mResult="pobieranie zakonczono";
            zamiar.putExtra(INFO,progressInfo );
            sendBroadcast(zamiar);

            //Close all connection after doing task
            fos.close();
            is.close();

        } catch (Exception e) {

            //Read exception if something went wrong
            e.printStackTrace();
            outputFile = null;
            Log.e(TAG, "Download Error Exception " + e.getMessage());
        }


    }
    public final static String POWIADOMIENIE = "com.example.intent_service.odbiornik";
    public final static String INFO = "info";

}



