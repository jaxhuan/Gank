package com.skyzone.gank;

import com.skyzone.gank.Data.Bean.Info;
import com.skyzone.gank.Data.Bean.MeiZi;
import com.skyzone.gank.Data.Bean.Video;
import com.skyzone.gank.Data.Result;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Skyzone on 11/24/2016.
 */
public interface GankApi {

    @GET("data/Android/" + RetrofitWrapper.page_size + "/{page}")
    Observable<Result<List<Info>>> getInfoAndroid(@Path("page") int page);

    @GET("data/iOS/" + RetrofitWrapper.page_size + "/{page}")
    Observable<Result<List<Info>>> getInfoIOS(@Path("page") int page);

    @GET("data/前端/" + RetrofitWrapper.page_size + "/{page}")
    Observable<Result<List<Info>>> getInfoWeb(@Path("page") int page);

    @GET("data/福利/" + RetrofitWrapper.page_size + "/{page}")
    Observable<Result<List<MeiZi>>> getMeiZi(@Path("page") int page);

    @GET("data/休息视频/" + RetrofitWrapper.page_size + "/{page}")
    Observable<Result<List<Video>>> getVideo(@Path("page") int page);

}
