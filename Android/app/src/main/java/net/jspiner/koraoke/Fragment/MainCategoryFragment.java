package net.jspiner.koraoke.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import net.jspiner.koraoke.Adapter.MainMusicAdapter;
import net.jspiner.koraoke.Model.MusicObject;
import net.jspiner.koraoke.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public class MainCategoryFragment extends Fragment {

    @Bind(R.id.lv_main)
    ListView lvMain;

    public MainCategoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        init(view);

        return view;
    }

    void init(View view){
        ButterKnife.bind(this, view);

        ArrayList<MusicObject> musicList = new ArrayList<>();
        musicList.add(null);
        musicList.add(null);
        musicList.add(null);
        musicList.add(null);
        musicList.add(null);
        musicList.add(null);
        musicList.add(null);
        musicList.add(null);
        musicList.add(null);
        musicList.add(null);

        MainMusicAdapter adapter = new MainMusicAdapter(getContext(), musicList);

        lvMain.setAdapter(adapter);
    }


}
