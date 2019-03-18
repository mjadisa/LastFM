package com.mujeeb.lastfm.model;

import com.mujeeb.lastfm.model.albumResponse.Album;

import java.util.List;

public class AlbumResults {
    private final int startIndex;
    private final int itemsPerPage;
    private final int totalResults;
    private final List<Album> albumList;

    public AlbumResults(int startIndex, int itemsPerPage, int totalResults, List<Album> albumList) {
        this.startIndex = startIndex;
        this.itemsPerPage = itemsPerPage;
        this.totalResults = totalResults;
        this.albumList = albumList;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }
}
