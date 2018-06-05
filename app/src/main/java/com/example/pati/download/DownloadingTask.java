package com.example.pati.download;

import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Pati on 02.06.2018.
 */

public class DownloadingTask extends AsyncTask<String, Void, Wrapper> {


    @Override
    protected Wrapper doInBackground(String... objects) {

        HttpURLConnection polaczenie = null;
        try {

            URL url = new URL(objects[0]);

            polaczenie = (HttpURLConnection) url.openConnection();

            polaczenie.setRequestMethod("GET");
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (polaczenie != null) {
                polaczenie.disconnect();
                Log.d("PT", "roz³¹czone");
            }
        }
        String type = polaczenie.getContentType();
        int size = polaczenie.getContentLength();
        Wrapper wrapper = new Wrapper();
        wrapper.type = type;
        wrapper.size = size;
        return wrapper;

    }

    protected void onPostExecute(Wrapper wrapper){
        MainActivity.sizeEditText.setText(String.valueOf(wrapper.size));
        MainActivity.typeEditText.setText(wrapper.type);
    }
}