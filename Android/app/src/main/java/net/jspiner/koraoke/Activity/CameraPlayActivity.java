package net.jspiner.koraoke.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
public class CameraPlayActivity extends AppCompatActivity {

    //로그에 쓰일 tag
    public static final String TAG = CameraPlayActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.surface_camera)
    SurfaceView surfaceCamera;

    @Bind(R.id.lyric_cameraplay)
    LyricView lyricView;

    @Bind(R.id.tv_song_count)
    TextView tvCount;

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

        initToolbar();
    }

    void initToolbar(){

        setSupportActionBar(toolbar);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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

        lyricView.setBaseInfo(musicModel, mediaPlayer, new StartCallBack() {

            @Override
            public void startCallBack() {
                animHandler.sendEmptyMessageDelayed(0,0);
            }

            @Override
            public void endCallBack() {
                endHandler.sendEmptyMessageDelayed(0,6000);
            }
        });
        lyricView.start();
        mediaPlayer.start();

    }

    Handler endHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            AlertDialog.Builder builder = new AlertDialog.Builder(CameraPlayActivity.this);
            builder.setTitle("축하합니다.");
            builder.setMessage("72점을 기록하셨습니다. 동영상을 저장하거나 다시 응원하실 수 있습니다!");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNeutralButton("재도전", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.create().show();

        }
    };

    int animCount = 3;

    Handler animHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(animCount==0){
                tvCount.setVisibility(View.GONE);
                return;
            }
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(""+animCount);


            AnimationSet animSet = new AnimationSet(true);
            Animation anim;
            Animation anim2;
            animSet.setFillEnabled(true);
            animSet.setFillAfter(true);

            anim = new AlphaAnimation(1.0f, 0.0f);
            anim.setDuration(1300);
            anim.setStartOffset(0);
            anim.setFillEnabled(true);
            anim.setFillAfter(true);

            anim2 = new ScaleAnimation(
                    1f, 0f, 1f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            anim2.setDuration(1300);
            anim2.setStartOffset(0);
            anim2.setFillEnabled(true);
            anim2.setFillAfter(true);

            animSet.addAnimation(anim);
            animSet.addAnimation(anim2);

            tvCount.startAnimation(animSet);

            animCount--;

            animHandler.sendEmptyMessageDelayed(0,1300);
        }
    };

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

    public interface StartCallBack{
        public void startCallBack();
        public void endCallBack();
    }

}
