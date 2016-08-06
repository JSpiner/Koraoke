package net.jspiner.koraoke.Model;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

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

}
