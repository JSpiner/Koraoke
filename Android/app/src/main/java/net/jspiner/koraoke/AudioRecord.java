package net.jspiner.koraoke;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public class AudioRecord {

    private static String mFileName = null;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;



    public AudioRecord(){
        Log.e("asdf", "init");
        mRecorder = new MediaRecorder();
        //mRecorder.setOutputFormat();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }

    public void startRecord(String fileName){
        Log.e("asdf", "start");
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/"+fileName+".3gp";
        mRecorder.setOutputFile(mFileName);


        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("asdf", "prepare() failed");
        }

        mRecorder.start();
    }

    public String stopRecording() {
        Log.e("asdf", "stop");
        mRecorder.stop();
        mRecorder.release();
        //mRecorder = null;

        return this.mFileName;


    }


    private void startPlaying(String fileName) {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("asdf", "prepare() failed");
        }
    }


}
