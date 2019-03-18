package com.mujeeb.lastfm.model.albumResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results {

    @SerializedName("opensearch:Query")
    @Expose
    private OpensearchQuery opensearchQuery;
    @SerializedName("opensearch:totalResults")
    @Expose
    private int opensearchTotalResults;
    @SerializedName("opensearch:startIndex")
    @Expose
    private int opensearchStartIndex;
    @SerializedName("opensearch:itemsPerPage")
    @Expose
    private int opensearchItemsPerPage;
    @SerializedName("albummatches")
    @Expose
    private Albummatches albummatches;
    @SerializedName("@attr")
    @Expose
    private Attr attr;

    public OpensearchQuery getOpensearchQuery() {
        return opensearchQuery;
    }


    public int getOpensearchTotalResults() {
        return opensearchTotalResults;
    }


    public int getOpensearchStartIndex() {
        return opensearchStartIndex;
    }


    public int getOpensearchItemsPerPage() {
        return opensearchItemsPerPage;
    }


    public Albummatches getAlbummatches() {
        return albummatches;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

}
