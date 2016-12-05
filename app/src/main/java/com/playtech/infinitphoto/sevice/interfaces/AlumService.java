package com.playtech.infinitphoto.sevice.interfaces;

import com.playtech.infinitphoto.model.AlbumsData;
import com.playtech.infinitphoto.model.RawAlbumData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface AlumService {
    Observable<RawAlbumData> getAlbums(int offset);

    @GET("/gql")
    Observable<RawAlbumData> getAlbums(@Query("query") String query);
}
