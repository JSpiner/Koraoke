package net.jspiner.koraoke.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import net.jspiner.koraoke.Model.MusicModel;
import net.jspiner.koraoke.R;
import net.jspiner.koraoke.View.LyricView;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public class CameraPlayActivity extends Activity {

    //로그에 쓰일 tag
    public static final String TAG = CameraPlayActivity.class.getSimpleName();

    @Bind(R.id.surface_camera)
    SurfaceView surfaceCamera;

    @Bind(R.id.lyric_cameraplay)
    LyricView lyricView;

    SurfaceHolder previewHolder=null;
    Camera camera=null;
    boolean inPreview=false;
    boolean cameraConfigured=false;

    MusicModel musicModel;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameraplay);

        init();
    }

    void init(){
        ButterKnife.bind(this);

        Intent intent = getIntent();
        musicModel = new Gson().fromJson(intent.getStringExtra("json"), MusicModel.class);

        Log.d("asdf", Environment.getExternalStorageDirectory().getAbsoluteFile().toString());
        mediaPlayer = MediaPlayer.create(getBaseContext(), Uri.fromFile(new File(
                Environment.getExternalStorageDirectory().getAbsoluteFile()
                        + "/test.mp3")));

        initSurface();
    }

    void initSurface() {


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        previewHolder = surfaceCamera.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    @Override
    public void onResume() {
        super.onResume();

        camera=Camera.open(1);

        setCameraRotation();

        startPreview();
    }


    void setCameraRotation(){
        Camera.Parameters param = camera.getParameters();

        switch(this.getWindowManager().getDefaultDisplay().getRotation()){
            case Surface.ROTATION_0:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO){
                    camera.setDisplayOrientation(90);
                    Log.d("Rotation_0", "whatever");
                }
                else{
                    Log.d("Rotation_0", "whatever");
                    param.setRotation(90);
                    camera.setParameters(param);
                }
                break;
            case Surface.ROTATION_90:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO){
                    camera.setDisplayOrientation(0);
                    Log.d("Rotation_0", "whatever");
                }
                else{
                    Log.d("Rotation_90", "whatever");
                    param.setRotation(0);
                    camera.setParameters(param);
                }
                break;
        }
    }

    @Override
    public void onPause() {
        if (inPreview) {
            camera.stopPreview();
        }

        camera.release();
        camera=null;
        inPreview=false;

        super.onPause();
    }

    private Camera.Size getBestPreviewSize(int width, int height,
                                           Camera.Parameters parameters) {
        Camera.Size result=null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width<=width && size.height<=height) {
                if (result==null) {
                    result=size;
                }
                else {
                    int resultArea=result.width*result.height;
                    int newArea=size.width*size.height;

                    if (newArea>resultArea) {
                        result=size;
                    }
                }
            }
        }

        return(result);
    }

    private void initPreview(int width, int height) {
        if (camera!=null && previewHolder.getSurface()!=null) {
            try {
                camera.setPreviewDisplay(previewHolder);
            }
            catch (Throwable t) {
                Toast.makeText(CameraPlayActivity.this, t.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }

            if (!cameraConfigured) {
                Camera.Parameters parameters=camera.getParameters();
                Camera.Size size=getBestPreviewSize(width, height,
                        parameters);

                if (size!=null) {
                    parameters.setPreviewSize(size.width, size.height);
                    camera.setParameters(parameters);
                    cameraConfigured=true;
                }
            }
        }
    }

    private void startPreview() {
        if (cameraConfigured && camera!=null) {
            camera.startPreview();
            inPreview=true;
        }

        lyricView.setBaseInfo(musicModel, mediaPlayer);
        lyricView.start();
        mediaPlayer.start();

    }

    SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {

        }

        public void surfaceChanged(SurfaceHolder holder,
                                   int format, int width,
                                   int height) {
            initPreview(width, height);
            startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

}