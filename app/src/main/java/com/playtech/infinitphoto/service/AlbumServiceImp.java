package com.playtech.infinitphoto.service;

import com.playtech.infinitphoto.intercepter.AddCookiesInterceptor;
import com.playtech.infinitphoto.model.RawAlbumData;
import com.playtech.infinitphoto.service.interfaces.AlbumService;

import java.net.HttpCookie;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


public class AlbumServiceImp implements AlbumService {

    private final AlbumService albumService;

    public AlbumServiceImp(HttpCookie cookie) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient build =  new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(cookie))
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://core-graphql.staging.waldo.photos/")
                .client(build)
                .build();

        albumService = retrofit.create(AlbumService.class);
    }

    @Override
    public Observable<RawAlbumData> getAlbums(int offset) {
        return getAlbums(getStringQuery(offset));
    }

    @Override
    public Observable<RawAlbumData> getAlbums(String query) {
        return albumService.getAlbums(query);
    }

    private String getStringQuery(int offset) {
        return "{album(id: \"YWxidW06YTQwYzc5ODEtMzE1Zi00MWIyLTk5NjktMTI5NjIyZDAzNjA5\"){photos(slice:{limit:50,offset:"
                +offset
                +"}){total,count,records{id,urls{size_code,url}}}}}";
    }
}
