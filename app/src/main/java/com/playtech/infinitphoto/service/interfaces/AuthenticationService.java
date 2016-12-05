package com.playtech.infinitphoto.service.interfaces;

import com.playtech.infinitphoto.cookie.PersistentCookieStore;

import java.net.HttpCookie;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.Single;

public interface AuthenticationService {
    @FormUrlEncoded
    @POST("/")
    Observable<Response<ResponseBody>> login(@Field("username") String username, @Field("password") String password);

    HttpCookie getTokenCookie();

    boolean isTokenExpire();

    void setPersistentCookieStore(PersistentCookieStore store);

    Single<Integer> getLoginResponseCode(String username, String password);
}
