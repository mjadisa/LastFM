package com.mujeeb.lastfm.mvp.albumInfo;

import android.support.annotation.NonNull;

public interface AlbumInfoContract {
    interface View {
        void showAlbumName(@NonNull String albumName);

        void showArtistName(@NonNull String artistName);

        void showWiki(@NonNull String wiki);

        void showError(@NonNull String errorMessage);

        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void getData(String album, String artist);

        void stop();
    }
}
