package net.jspiner.koraoke.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import net.jspiner.koraoke.Model.LyricObject;
import net.jspiner.koraoke.Model.MusicModel;

import java.util.ArrayList;

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

    long startTimeStamp;

    MusicModel musicModel;

    Paint paintText;
    Paint paintColorText;

    MediaPlayer  mediaPlayer;

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
    }

    public void setBaseInfo(MusicModel musicModel, MediaPlayer mediaPlayer){
        this.musicModel = musicModel;
        this.startTimeStamp = System.currentTimeMillis();
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    protected void onDraw(Canvas canvas) {


        if(isStarted){
            int i;

            for(i=0;i<musicModel.lineList.size();i++){
                if(musicModel.lineList.get(i).lyricList.get(0).time >
                        mediaPlayer.getCurrentPosition()){ break;
                }
            }

            if(i==musicModel.lineList.size()){
                i=-1;
            }

            drawLyricLine(canvas, i - 1, 0);
            drawColorLyricLine(canvas, i - 1, 0);
            drawLyricLine(canvas, i, 1);


            canvas.drawText("position : " + mediaPlayer.getCurrentPosition(),
                    100, 100,
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

        Canvas canvas1 = new Canvas();


        canvas.drawText(stringBuilder.toString(),
                100,300 + 200*type,
                paintColorText);


    }

    void drawLyricLine(Canvas canvas, int line, int type){

        if(line>=musicModel.lineList.size() || line<0) return;

        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<LyricObject> lyricList = (ArrayList<LyricObject>) musicModel.lineList.get(line).lyricList;

        for(int i=0;i<lyricList.size();i++){
            stringBuilder.append(lyricList.get(i).gasa);
        }

        canvas.drawText(stringBuilder.toString(),
                100,300 + 200*type,
                paintText);


    }

}
