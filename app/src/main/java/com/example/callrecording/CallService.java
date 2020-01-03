package com.example.callrecording;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class CallService extends Service {

    public Phonecallreceiver phonecallreceiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        phonecallreceiver = new Phonecallreceiver(this);
        int res = super.onStartCommand(intent, flags, startId);
        phonecallreceiver.start();
        return res;

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        phonecallreceiver.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
