package com.playtech.infinitphoto.service.interfaces;

import com.playtech.infinitphoto.model.RawAlbumData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface AlumService {
    Observable<RawAlbumData> getAlbums(int offset);

    @GET("/gql")
    Observable<RawAlbumData> getAlbums(@Query("query") String query);
}
