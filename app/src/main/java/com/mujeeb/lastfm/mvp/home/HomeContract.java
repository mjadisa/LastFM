package com.mujeeb.lastfm.mvp.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mujeeb.lastfm.model.albumResponse.Album;

import java.util.List;

public interface HomeContract {
    interface View {
        void showResults(@NonNull List<Album> albums);

        void showError(@NonNull String message);

        void navigateToDetails(@NonNull String album, @NonNull String artist);

        void showProgress();

        void hideProgress();

        void clearResults();
    }

    interface Presenter {
        void getResults(@Nullable String searchTerm, boolean isNewQuery);

        void handleSearchResultSelection(int position);

        void stop();

        boolean getLoadingState();

        boolean isLastPage();
    }
}
