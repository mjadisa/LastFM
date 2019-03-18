package com.mujeeb.lastfm.api;

import com.mujeeb.lastfm.model.albumInfo.AlbumDetails;
import com.mujeeb.lastfm.model.albumResponse.AlbumResultsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastApiCall {

    @GET(".")
    Observable<AlbumDetails> getAlbumDetails(@Query("method") String operation,
                                             @Query("album") String album,
                                             @Query("artist") String artist,
                                             @Query("api_key") String apiKey,
                                             @Query("format") String responseFormat);

    @GET(".")
    Observable<AlbumResultsResponse> getAlbumResults(@Query("method") String operation,
                                                     @Query("album") String searchQuery,
                                                     @Query("page") int page,
                                                     @Query("limit") int limit,
                                                     @Query("api_key") String apiKey,
                                                     @Query("format") String responseFormat);


}
