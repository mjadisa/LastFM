package com.mujeeb.lastfm.repository;

import com.mujeeb.lastfm.model.AlbumResults;
import com.mujeeb.lastfm.model.albumInfo.AlbumDetails;

import io.reactivex.Maybe;

public class AlbumRepository implements AlbumDataSource {

    private final AlbumDataSource albumRemoteDataSource;

    public AlbumRepository(AlbumDataSource albumRemoteDataSource) {
        this.albumRemoteDataSource = albumRemoteDataSource;
    }

    @Override
    public Maybe<AlbumDetails> getAlbumDetails(String album, String artist) {
        return albumRemoteDataSource.getAlbumDetails(album, artist);
    }

    @Override
    public Maybe<AlbumResults> getAlbumResults(String album, int pageNumber) {
        return albumRemoteDataSource.getAlbumResults(album, pageNumber);
    }


}
