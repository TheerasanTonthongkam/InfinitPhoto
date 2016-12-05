package com.playtech.infinitphoto.service;

import com.playtech.infinitphoto.cookie.PersistentCookieStore;
import com.playtech.infinitphoto.service.interfaces.AuthenticationService;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;

public class AuthenticationServiceImp implements AuthenticationService {

    private final AuthenticationService authService;
    private PersistentCookieStore store;

    public AuthenticationServiceImp(PersistentCookieStore store) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        this.store = store;
        CookieHandler cookieHandler = new CookieManager(
                store, CookiePolicy.ACCEPT_ALL);
        JavaNetCookieJar cookieJar = new JavaNetCookieJar(cookieHandler);

        builder.cookieJar(cookieJar);
        builder.interceptors().add(logging);
        OkHttpClient okHttpClient = builder.build();

        Retrofit authRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://auth.staging.waldo.photos/")
                .client(okHttpClient)
                .build();

        authService = authRetrofit.create(AuthenticationService.class);
    }

    @Override
    public Observable<Response<ResponseBody>> login(String username, String password) {
        return authService.login(username, password);
    }

    @Override
    public HttpCookie getTokenCookie() {
        return store.getTokenCookie();
    }

    @Override
    public boolean isTokenExpire() {
        return getTokenCookie().hasExpired();
    }

    @Override
    public void setPersistentCookieStore(PersistentCookieStore store) {
        this.store = store;
    }

    @Override
    public Single<Integer> getLoginResponseCode(String username, String password) {
        Observable<Integer> map = login(username, password)
                .map(res -> res.code());
        return map.toSingle();
    }
}
