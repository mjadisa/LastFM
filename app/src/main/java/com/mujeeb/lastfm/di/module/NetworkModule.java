package com.mujeeb.lastfm.di.module;

import android.app.Application;

import com.mujeeb.lastfm.api.LastApiCall;
import com.mujeeb.lastfm.di.qualifier.HttpLoggingInterceptor;
import com.mujeeb.lastfm.di.qualifier.LastApiInterceptor;
import com.mujeeb.lastfm.di.qualifier.Remote;
import com.mujeeb.lastfm.di.scope.ApplicationScope;
import com.mujeeb.lastfm.repository.AlbumDataSource;
import com.mujeeb.lastfm.repository.AlbumRemoteDataSource;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mujeeb.lastfm.ConstantString.API_KEY;
import static com.mujeeb.lastfm.ConstantString.BASE_URL;
import static com.mujeeb.lastfm.ConstantString.CACHE_SIZE;
import static com.mujeeb.lastfm.ConstantString.TIMEOUT_REQUEST;

@Module
public class NetworkModule {

    @Provides
    @ApplicationScope
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @ApplicationScope
    public OkHttpClient provideOkHttpClient(Cache cache,
                                            @HttpLoggingInterceptor Interceptor httpLoggingInterceptor,
                                            @LastApiInterceptor Interceptor apiInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(apiInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    @Provides
    @HttpLoggingInterceptor
    @ApplicationScope
    public Interceptor provideHttpLoggingInterceptor() {
        return new okhttp3.logging.HttpLoggingInterceptor().setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @LastApiInterceptor
    @ApplicationScope
    public Interceptor provideApiInterceptor() {
        return chain -> {
            HttpUrl originalUrl = chain.request().url();
            HttpUrl newUrl = originalUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build();
            Request request = chain.request().newBuilder()
                    .url(newUrl)
                    .build();
            return chain.proceed(request);
        };
    }


    @Provides
    @ApplicationScope
    public Cache provideCache(Application application) {
        return new Cache(application.getCacheDir(), CACHE_SIZE);
    }


    @Provides
    @ApplicationScope
    public LastApiCall provideLastFMService(Retrofit retrofit) {
        return retrofit.create(LastApiCall.class);
    }

    @Provides
    @Remote
    @ApplicationScope
    public AlbumDataSource provideRemoteDataSource(LastApiCall lastFMApiCall) {
        return new AlbumRemoteDataSource(lastFMApiCall);
    }

}
