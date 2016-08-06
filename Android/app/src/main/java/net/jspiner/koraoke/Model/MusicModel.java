package net.jspiner.koraoke.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public class MusicModel {

    @SerializedName("songId")
    public int songId;

    @SerializedName("songName")
    public String songName;

    @SerializedName("songFirstline")
    public String songFirstline;

    @SerializedName("songState")
    public int songState;

    @SerializedName("songReleaseYear")
    public int songReleaseYear;

    public List<LineObject> lineList;

    public MusicModel(){
        lineList = new ArrayList<>();
    }

}
