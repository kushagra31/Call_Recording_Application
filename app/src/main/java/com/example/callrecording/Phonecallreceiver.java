package com.example.callrecording;


import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class Phonecallreceiver  {


    Context ctx;


    public class s extends PhoneStateListener {
        @Override
        public void onCallStateChanged ( int state, String phoneNumber){
        switch (state) {
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Intent i = new Intent(ctx, Dialog1.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ctx.startActivity(i);
                break;

            }

        }
    }

    public Phonecallreceiver(Context ctx) {

        this.ctx = ctx;
        callStateListener = new s();

    }


    public void start(){

        tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    }

    public void stop() {
        tm.listen(callStateListener, PhoneStateListener.LISTEN_NONE);

    }

    private TelephonyManager tm;
    private s callStateListener;


}