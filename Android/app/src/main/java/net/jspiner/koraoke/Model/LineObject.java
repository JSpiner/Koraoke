package net.jspiner.koraoke.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public class LineObject {
    public List<LyricObject> lyricList;

    public LineObject(){
        lyricList = new ArrayList<>();
    }
}
