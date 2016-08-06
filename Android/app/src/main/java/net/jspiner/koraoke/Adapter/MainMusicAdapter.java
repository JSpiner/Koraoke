package net.jspiner.koraoke.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.jspiner.koraoke.Model.LyricObject;
import net.jspiner.koraoke.Model.MusicModel;
import net.jspiner.koraoke.R;

import java.lang.ref.WeakReference;
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
public class MainMusicAdapter extends BaseAdapter{

    //로그에 쓰일 tag
    public static final String TAG = MainMusicAdapter.class.getSimpleName();

    LayoutInflater inflater;

    private SparseArray<WeakReference<View>> viewArray;

    public ArrayList<MusicModel> musicList;
    Context context;

    ProgressDialog mProgressDialog;

    public MainMusicAdapter(Context context, ArrayList<MusicModel> musicList){
        this.context = context;
        this.musicList = musicList;
        this.viewArray = new SparseArray<WeakReference<View>>(musicList.size());
        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    int[] albums = {
      R.drawable.img_album_1, R.drawable.img_album_2, R.drawable.img_album_3
    };

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(viewArray != null && viewArray.get(position) != null) {
            convertView = viewArray.get(position).get();
            if(convertView != null)
                return convertView;
        }

        try {

            MusicModel musicObject = musicList.get(position);

            convertView = inflater.inflate(R.layout.item_main_music, null);

            ViewBinder binder = new ViewBinder(convertView, position);

            Picasso.with(context)
                    .load(context.getString(R.string.API_SERVER)+"/koraoke/image/"+musicObject.songId+".png")
                    .fit()
                    .into(binder.imvAlbum);
            binder.tvTitle.setText(musicObject.songName);
            binder.tvLyric.setText(musicObject.songFirstline+"..");
            binder.tvYear.setText(""+musicObject.songReleaseYear);

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
