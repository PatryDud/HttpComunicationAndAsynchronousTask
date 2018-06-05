package com.example.pati.download;

/**
 * Created by Pati on 03.06.2018.
 */
import android.os.Environment;

public class CheckForSDCard {
    //Check If SD Card is present or not method
    public boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(

                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}