package com.mujeeb.lastfm.repository;

import com.mujeeb.lastfm.model.AlbumResults;
import com.mujeeb.lastfm.model.albumInfo.AlbumDetails;

import io.reactivex.Maybe;

public interface AlbumDataSource {

    Maybe<AlbumDetails> getAlbumDetails(String album, String artist);

    Maybe<AlbumResults> getAlbumResults(String album, int pageNumber);


}
