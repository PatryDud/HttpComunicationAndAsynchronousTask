package com.example.pati.download;

/**
 * Created by Pati on 05.06.2018.
 */

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        int currentPosition= 0;
        int total =MainActivity.sizeoffile;
        while (currentPosition<total) {
            try {
                Thread.sleep(1000);
                currentPosition= MainActivity.currentPosition;
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }
            MainActivity.progressBar.setProgress(currentPosition);
        }
    }

}
