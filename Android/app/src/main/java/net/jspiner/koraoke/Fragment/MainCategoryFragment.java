package net.jspiner.koraoke.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.jspiner.koraoke.Activity.SongDetailActivity;
import net.jspiner.koraoke.Adapter.MainMusicAdapter;
import net.jspiner.koraoke.Model.LineObject;
import net.jspiner.koraoke.Model.LyricObject;
import net.jspiner.koraoke.Model.MusicModel;
import net.jspiner.koraoke.R;
import net.jspiner.koraoke.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
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
public class MainCategoryFragment extends Fragment {

    //로그에 쓰일 tag
    public static final String TAG = MainCategoryFragment.class.getSimpleName();

    @Bind(R.id.lv_main)
    ListView lvMain;

    MainMusicAdapter adapter;

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


        Util.getHttpSerivce().GetSongList(
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        try {

                            ArrayList<MusicModel> musicList = new ArrayList<MusicModel>();
                            String responseString = Util.getStringfromReponse(response);

                            JSONArray jsonArray = new JSONArray(responseString);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                MusicModel musicModel = new MusicModel();

                                musicModel.songId = jsonObject.getInt("songId");
                                musicModel.songReleaseYear = jsonObject.getInt("songReleaseYear");
                                musicModel.songName = jsonObject.getString("songName");
                                musicModel.songFirstline = jsonObject.getString("songFirstline");

                                JSONArray baseArray = new JSONArray(jsonObject.getString("songLyrics"));


                                for (int j = 0; j < baseArray.length(); j++) {
                                    LineObject lineObject = new LineObject();
                                    JSONArray lineArray = new JSONArray(baseArray.getString(j));
                                    for (int k = 0; k < lineArray.length(); k++) {
                                        JSONObject lyricJson = lineArray.getJSONObject(k);
                                        LyricObject lyricObject = new LyricObject();
                                        lyricObject.gasa = lyricJson.getString("gasa");
                                        lyricObject.time = lyricJson.getLong("time");
                                        lineObject.lyricList.add(lyricObject);
                                    }
                                    musicModel.lineList.add(lineObject);
                                }

                                musicList.add(musicModel);
                            }


                            adapter = new MainMusicAdapter(getContext(), musicList);

                            lvMain.setAdapter(adapter);

                        } catch (Exception e) {
                            Toast.makeText(getContext(), "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "error : " + e.getMessage());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getContext(), "에러가 발생하였습니다.", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @OnItemClick(R.id.lv_main)
    void onListItemClick(int position){
        Intent intent = new Intent(getContext(), SongDetailActivity.class);
        intent.putExtra("json", new Gson().toJson(adapter.musicList.get(position)));
        startActivity(intent);
    }


}
