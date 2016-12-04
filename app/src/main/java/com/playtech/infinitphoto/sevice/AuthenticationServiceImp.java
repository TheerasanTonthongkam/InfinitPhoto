package com.playtech.infinitphoto.sevice;

import android.content.Context;

import com.playtech.infinitphoto.cookie.PersistentCookieStore;
import com.playtech.infinitphoto.sevice.interfaces.AuthenticationService;

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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AuthenticationServiceImp implements AuthenticationService {

    private static AuthenticationServiceImp instance;
    private Retrofit authRetrofit;

    AuthenticationService authService;
    PersistentCookieStore store;

    private AuthenticationServiceImp(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        store = new PersistentCookieStore(context);
        CookieHandler cookieHandler = new CookieManager(
                store, CookiePolicy.ACCEPT_ALL);
        JavaNetCookieJar cookieJar = new JavaNetCookieJar(cookieHandler);

        builder.cookieJar(cookieJar);
        builder.interceptors().add(logging);
        OkHttpClient okHttpClient = builder.build();

        authRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://auth.staging.waldo.photos/")
                .client(okHttpClient)
                .build();

        authService = authRetrofit.create(AuthenticationService.class);
    }

    public static AuthenticationServiceImp getInstance(Context context) {
        if (instance == null) {
            instance = new AuthenticationServiceImp(context);
        }
        return instance;
    }

    @Override
    public Observable<Response<ResponseBody>> login(String username, String password) {
        return authService.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public HttpCookie getTokenCookie() {
        return store.getTokenCookie();
    }
}
