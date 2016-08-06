package net.jspiner.koraoke.Model;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public interface HttpService {

    @FormUrlEncoded
    @POST("/koraoke/signup.php")
    void signup();

    @GET("/koraoke/api/GetSongList.php")
    void GetSongList(Callback<Response> ret);

    @GET("/koraoke/api/GetSongHit.php")
    void GetSongHit(@Query("songId") int songId,
                    Callback<HitModel> ret);

    @GET("/koraoke/api/GetSongRank.php")
    void GetSongRank(@Query("songId") int songId,
                    Callback<HitModel> ret);

    @FormUrlEncoded
    @POST("/koraoke/api/ScoreAdd.php")
    void PostAddScore(@Field("userId") int userId, @Field("songId") int songId,
                            @Field("score") int score,Callback<ScoreAddModel> ret);

}
