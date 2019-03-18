package com.mujeeb.lastfm.api;

import com.mujeeb.lastfm.model.albumInfo.AlbumDetails;
import com.mujeeb.lastfm.model.albumResponse.AlbumResultsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastApiCall {

    @GET(".")
    Single<AlbumDetails> getAlbumDetails(@Query("method") String method,
                                         @Query("album") String album,
                                         @Query("artist") String artist,
                                         @Query("format") String format);

    @GET(".")
    Single<AlbumResultsResponse> getAlbumResults(@Query("method") String method,
                                                 @Query("album") String album,
                                                 @Query("page") int page,
                                                 @Query("limit") int limit,
                                                 @Query("format") String format);


}
