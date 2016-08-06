package net.jspiner.koraoke.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import net.jspiner.koraoke.Activity.CameraPlayActivity;
import net.jspiner.koraoke.AudioRecord;
import net.jspiner.koraoke.Model.LyricObject;
import net.jspiner.koraoke.Model.MusicModel;
import net.jspiner.koraoke.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public class LyricView extends View {

    //로그에 쓰일 tag
    public static final String TAG = LyricView.class.getSimpleName();

    boolean isStarted = false;
    boolean startFlag = false;

    long startTimeStamp;

    MusicModel musicModel;

    Paint paintText;
    Paint paintColorText;
    Paint paintGrayText;
    Paint paintAlpha;

    MediaPlayer  mediaPlayer;

    CameraPlayActivity.StartCallBack callBack;

    /////////
    AudioRecord audio;
    int now = 0;
    int dontcome = 0;

    ///////

    public LyricView(Context context) {
        super(context);
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LyricView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void start(){
        isStarted = true;
        paintText = new Paint();
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(100);
        paintColorText = new Paint();
        paintColorText.setColor(Color.YELLOW);
        paintColorText.setTextSize(100);
        paintGrayText = new Paint();
        paintGrayText.setColor(Color.GRAY);
        paintGrayText.setTextSize(80);
        paintAlpha = new Paint();
        paintAlpha.setAlpha(200);
    }

    public void setBaseInfo(MusicModel musicModel, MediaPlayer mediaPlayer, CameraPlayActivity.StartCallBack callBack){
        this.musicModel = musicModel;
        this.startTimeStamp = System.currentTimeMillis();
        this.mediaPlayer = mediaPlayer;
        this.callBack = callBack;
    }

    boolean eof = false;

    @Override
    protected void onDraw(Canvas canvas) {

        if(isStarted){
            int i;

            //라인돌아다니면서 현재 재생할 라인 찾음
            for(i=0;i<musicModel.lineList.size();i++){
                if(musicModel.lineList.get(i).lyricList.get(0).time >
                        mediaPlayer.getCurrentPosition()){


                 //////////////////
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String currentDateandTime = sdf.format(new Date());
                    if(i == 5 && dontcome == 0){
                        audio = new AudioRecord();
                        audio.startRecord(currentDateandTime);
                        dontcome = 1;
                    }
                    if(i == 6 && dontcome == 1){
                        audio.stopRecording();
                        dontcome = 2;
                    }
                    //포에버의 경우 "우리의 함성은 신화가 되리라 부분임.
                ////////////////////


                        break;
                }
            }

            if(!startFlag){
                if(musicModel.lineList.get(0).lyricList.get(0).time
                        - mediaPlayer.getCurrentPosition() < 4000){
                    startFlag = true;
                    callBack.startCallBack();
                }
            }

            if(!eof) {
                if (i == musicModel.lineList.size()) {
                    callBack.endCallBack();
                    eof = true;
                }
            }

            //흰색으로 첫재줄(활성화된 글자 적음)
            drawLyricLine(canvas, i - 1, 0);
            //노란색으로 첫재줄(활성화된 글자 덮어씌움);
            drawColorLyricLine(canvas, i - 1, 0);
            //흰색으로 둘재줄 채움
            drawLyricLine(canvas, i, 1);



/*
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_album_1);

            canvas.drawBitmap(bitmap,
                    new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                    new Rect(0, 0, 500, 500),
                    paintAlpha);*/

            int lastTime = (mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition()) / 1000;


            canvas.drawText("-" + (lastTime / 60) + ":" + (lastTime % 60),
                    1200, 330,
                    paintText);
        }
        super.onDraw(canvas);

        invalidate();
    }

    void drawColorLyricLine(Canvas canvas, int line, int type){

        if(line>=musicModel.lineList.size() || line<0) return;

        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<LyricObject> lyricList = (ArrayList<LyricObject>) musicModel.lineList.get(line).lyricList;

        for(int i=0;i<lyricList.size();i++){
            stringBuilder.append(lyricList.get(i).gasa);
            if(lyricList.get(i).time>
                    mediaPlayer.getCurrentPosition()) break;
        }


        canvas.drawText(stringBuilder.toString(),
                100,600 + 200*type,
                paintColorText);


    }

    void drawLyricLine(Canvas canvas, int line, int type){

        if(line>=musicModel.lineList.size() ) return;

        if(line<0){

            canvas.drawText("( 간.주.중 )",
                    600,600 + 200*type,
                    paintColorText);
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<LyricObject> lyricList = (ArrayList<LyricObject>) musicModel.lineList.get(line).lyricList;

        for(int i=0;i<lyricList.size();i++){
            stringBuilder.append(lyricList.get(i).gasa);
        }

        canvas.drawText(stringBuilder.toString(),
                100,600 + 200*type,
                type==1?paintGrayText:paintText);


    }

}
