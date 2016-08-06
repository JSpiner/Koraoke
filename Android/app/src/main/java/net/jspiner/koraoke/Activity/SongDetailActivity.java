package net.jspiner.koraoke.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import net.jspiner.koraoke.Model.HitModel;
import net.jspiner.koraoke.Model.MusicModel;
import net.jspiner.koraoke.R;
import net.jspiner.koraoke.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public class SongDetailActivity extends AppCompatActivity {

    //로그에 쓰일 tag
    public static final String TAG = SongDetailActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tv_song_rank)
    TextView tvRank;

    @Bind(R.id.tv_song_playcount)
    TextView tvPlayCount;

    @Bind(R.id.tv_song_title)
    TextView tvTitle;

    @Bind(R.id.tv_song_content)
    TextView tvContent;

    @Bind(R.id.imv_song_album)
    ImageView imvAlbum;

    MusicModel musicModel;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songdetail);

        init();
    }

    void init(){
        ButterKnife.bind(this);

        Intent intent = getIntent();
        musicModel = new Gson().fromJson(intent.getStringExtra("json"), MusicModel.class);

        Log.d(TAG,"JSON : " + new Gson().toJson(musicModel));

        tvTitle.setText(musicModel.songName);
        tvContent.setText(musicModel.songFirstline+"..");
        Picasso.with(getBaseContext())
                .load(getString(R.string.API_SERVER)+"/koraoke/image/"+musicModel.songId+".png")
                .fit()
                .into(imvAlbum);

        initToolbar();

        Util.getHttpSerivce().GetSongHit(musicModel.songId,
                new Callback<HitModel>() {

                    @Override
                    public void success(HitModel response, Response response2) {
                        tvPlayCount.setText("" + response.message);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });


        // instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(SongDetailActivity.this);
        mProgressDialog.setMessage("곡을 다운로드중입니다.");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

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

    @OnClick(R.id.btn_song_start)
    void onStartClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SongDetailActivity.this);
        builder.setTitle("음악을 다운로드");
        builder.setMessage("네트워크를 통해 음악을 다운로드 받으시겠습니까?");
        builder.setPositiveButton("다운로드", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // execute this when the downloader must be fired
                final DownloadTask downloadTask = new DownloadTask(SongDetailActivity.this);

//                startActivity();

                downloadTask.execute("http://somabob.azurewebsites.net/koraoke/mp3/"+musicModel.songId+".mp3");

                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        downloadTask.cancel(true);
                    }
                });
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/test.mp3");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {

                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                Log.e(TAG,"error : "+e.getMessage());
                e.printStackTrace();
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();


                startActivity();
            }
        }
    }

    void startActivity(){

        Intent intent = new Intent(SongDetailActivity.this, CameraPlayActivity.class);
        intent.putExtra("json", new Gson().toJson(musicModel));
        startActivity(intent);
    }

}
