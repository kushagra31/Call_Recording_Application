package com.example.callrecording;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Dialog1 extends Activity  {
    static Dialog1 newInstance() {
        return new Dialog1();
    }

    private MediaRecorder mediaRecorder;
    private AudioManager audioManager;
    public static final int RECORD_AUDIO = 0;
    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4,
            AUDIO_RECORDER_FILE_EXT_3GP };
    private int currentFormat =0;






    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Record call")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setMode(AudioManager.MODE_IN_CALL);
                        audioManager.setSpeakerphoneOn(true);
                        mediaRecorder = new MediaRecorder();

                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.RECORD_AUDIO},
                                    Dialog1.RECORD_AUDIO);

                        } else {
                            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Dialog1.RECORD_AUDIO);
                            }
                            mediaRecorder.setOutputFile(getFilename());
                            mediaRecorder.setOnErrorListener(errorListener);
                            mediaRecorder.setOnInfoListener(infoListener);
                            try {
                                mediaRecorder.prepare();
                                mediaRecorder.start();
                            } catch (IllegalStateException e) {
                                Log.e("RECORDING :: ", e.getMessage());
                                e.printStackTrace();
                            } catch (IOException e) {
                                Log.e("RECORDING :: ", e.getMessage());
                                e.printStackTrace();
                            }
                        }


                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getParent().finish();
                    }
                });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

    }


    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Now you can call all your fragments method here
        }
    };

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);

        if (!file.exists()) {
            Log.d("unav","fffff");
            boolean h=file.mkdirs();
            System.out.println(h);
        }

        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".mp4");
    }


    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {

        }
    };

    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {


        }
    };
}