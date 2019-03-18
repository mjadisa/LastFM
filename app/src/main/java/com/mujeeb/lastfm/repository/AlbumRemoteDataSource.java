package com.mujeeb.lastfm.repository;

import android.util.Log;

import com.mujeeb.lastfm.api.LastApiCall;
import com.mujeeb.lastfm.model.AlbumResults;
import com.mujeeb.lastfm.model.albumInfo.AlbumDetails;

import io.reactivex.Maybe;

import static com.mujeeb.lastfm.ConstantString.ALBUM_GET_INFO;
import static com.mujeeb.lastfm.ConstantString.ALBUM_SEARCH;
import static com.mujeeb.lastfm.ConstantString.FORMAT;
import static com.mujeeb.lastfm.ConstantString.RESULTS_PER_PAGE;

public class AlbumRemoteDataSource implements AlbumDataSource {

    private final LastApiCall lastFMApiCall;

    public AlbumRemoteDataSource(LastApiCall lastFMApiCall) {
        this.lastFMApiCall = lastFMApiCall;
    }

    @Override
    public Maybe<AlbumDetails> getAlbumDetails(String album, String artist) {
        return lastFMApiCall.getAlbumDetails(ALBUM_GET_INFO,
                album,
                artist,
                FORMAT)
                .flatMapMaybe(Maybe::just);
    }


    @Override
    public Maybe<AlbumResults> getAlbumResults(String album, int pageNumber) {
        return lastFMApiCall.getAlbumResults(ALBUM_SEARCH,
                album,
                pageNumber,
                RESULTS_PER_PAGE,
                FORMAT)
                .flatMapMaybe(response -> Maybe.just(new AlbumResults(
                        ConvertStringToInt(response.getResults().getOpensearchStartIndex()),
                        ConvertStringToInt(response.getResults().getOpensearchItemsPerPage()),
                        ConvertStringToInt(response.getResults().getOpensearchTotalResults()),
                        response.getResults().getAlbummatches().getAlbum())));
    }


    public int ConvertStringToInt(String number) {
        int num;
        try {
            int newNumber = Integer.parseInt(number);
            num = newNumber;
        } catch (NumberFormatException ex) {
            num = 0;
            Log.i("ConvertStringToInt", ex.getMessage());
        }

        return num;
    }


}
