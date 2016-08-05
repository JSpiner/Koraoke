package net.jspiner.koraoke.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.jspiner.koraoke.Model.MusicObject;
import net.jspiner.koraoke.R;
import net.jspiner.koraoke.Util;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public class MainMusicAdapter extends BaseAdapter{

    //로그에 쓰일 tag
    public static final String TAG = MainMusicAdapter.class.getSimpleName();

    LayoutInflater inflater;

    private SparseArray<WeakReference<View>> viewArray;

    public ArrayList<MusicObject> musicList;
    Context context;

    ProgressDialog mProgressDialog;

    public MainMusicAdapter(Context context, ArrayList<MusicObject> musicList){
        this.context = context;
        this.musicList = musicList;
        this.viewArray = new SparseArray<WeakReference<View>>(musicList.size());
        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    int[] albums = {
      R.drawable.img_album_1, R.drawable.img_album_2, R.drawable.img_album_3
    };

    String[] titles = {
            "민족의 아리아",
            "뱃노래",
            "Forever"
    };

    String[] lyrics = {
            "타오르는 자유 나아가는 정의 솟구치는 진리 민족의...",
            "즐거운 고연전 날에 연대생 우는 소리 지고 가는 ...",
            "워-워-워워워 워워워워 우리의 함성은 신화가 되리라.."
    };

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(viewArray != null && viewArray.get(position) != null) {
            convertView = viewArray.get(position).get();
            if(convertView != null)
                return convertView;
        }

        try {

            MusicObject musicObject = musicList.get(position);

            convertView = inflater.inflate(R.layout.item_main_music, null);

            ViewBinder binder = new ViewBinder(convertView, position);

            Picasso.with(context)
                    .load(albums[position%3])
                    .fit()
                    .into(binder.imvAlbum);
            binder.tvTitle.setText(titles[position%3]);
            binder.tvLyric.setText(lyrics[position%3]);

        } finally {
            viewArray.put(position, new WeakReference<View>(convertView));
        }
        return convertView;
    }

    class ViewBinder {

        @Bind(R.id.imv_music_album)
        ImageView imvAlbum;

        @Bind(R.id.tv_music_year)
        TextView tvYear;

        @Bind(R.id.tv_music_title)
        TextView tvTitle;

        @Bind(R.id.tv_music_lyric)
        TextView tvLyric;

        int position;

        public ViewBinder(View view, int position){
            this.position = position;
            ButterKnife.bind(this, view);
        }


    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int position) {
        return musicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void update() {
        viewArray.clear();
        notifyDataSetChanged();
    }

}
