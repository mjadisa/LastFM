package com.mujeeb.lastfm;

import com.mujeeb.lastfm.model.AlbumResults;
import com.mujeeb.lastfm.model.albumResponse.Album;

import java.util.Collections;
import java.util.List;

public class UtilsTest {
    public static AlbumResults getAlbumResults() {
        List<Album> albums = Collections.singletonList(new Album());
        return new AlbumResults(0, 30, 30, albums);
    }
}
