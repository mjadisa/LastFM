package com.mujeeb.lastfm.mvp.home;

import android.support.annotation.Nullable;

import com.mujeeb.lastfm.api.LastApiCall;
import com.mujeeb.lastfm.model.albumResponse.Album;
import com.mujeeb.lastfm.model.albumResponse.AlbumResultsResponse;
import com.mujeeb.lastfm.model.albumResponse.Results;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.mujeeb.lastfm.ConstantString.ALBUM_SEARCH;
import static com.mujeeb.lastfm.ConstantString.API_KEY;
import static com.mujeeb.lastfm.ConstantString.FORMAT;
import static com.mujeeb.lastfm.ConstantString.RESULTS_PER_PAGE;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View view;
    private final LastApiCall lastApiCall;
    private final CompositeDisposable compositeDisposable;
    private final List<Album> albums;

    private boolean isLoading;
    private boolean isLastPage;
    private int currentPage = 1;


    public HomePresenter(HomeContract.View view, LastApiCall lastApiCall) {
        this.view = view;
        this.lastApiCall = lastApiCall;
        compositeDisposable = new CompositeDisposable();
        albums = new ArrayList<>();

        isLastPage = false;
        isLoading = false;
    }

    @Override
    public void getResults(@Nullable String searchTerm, boolean isNewQuery) {
        if (isNewQuery) {
            view.clearResults();
            albums.clear();
            currentPage = 1;
            isLastPage = isLoading = false;
        }
        if (searchTerm != null && !isLoading) {
            compositeDisposable.add(lastApiCall.getAlbumResults(ALBUM_SEARCH,
                    searchTerm, currentPage, RESULTS_PER_PAGE, API_KEY,
                    FORMAT)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> handleSubscriptionChange(true))
                    .doOnTerminate(() -> handleSubscriptionChange(false))
                    .subscribe(this::handleResult, this::handleError));
        } else {
            view.showError("Please try again");
        }
    }

    @Override
    public void handleSearchResultSelection(int position) {
        if (position >= 0 && position < albums.size()) {
            view.navigateToDetails(albums.get(position).getName(), albums.get(position).getArtist());
        }
    }

    private void handleSubscriptionChange(boolean isSubscribed) {
        isLoading = isSubscribed;
        if (isSubscribed) {
            view.showProgress();
        } else {
            view.hideProgress();
        }
    }

    private void handleResult(@Nullable AlbumResultsResponse albumResultsResponse) {
        List<Album> albums = null;
        if (albumResultsResponse != null) {
            final Results results = albumResultsResponse.getResults();
            isLastPage = results.getOpensearchStartIndex() + results.getOpensearchItemsPerPage() >= results.getOpensearchTotalResults();
            currentPage++;
            albums = results.getAlbummatches().getAlbum();
        }
        if (albums != null || albums.isEmpty()) {
            this.albums.addAll(albums);
            view.showResults(albums);
        } else {
            view.showError("An unexpected error happened while getting your search results, please try again");
        }
    }

    private void handleError(Throwable throwable) {
        if (throwable.getMessage() != null) {
            view.showError(throwable.getMessage());
        }
    }

    @Override
    public void stop() {
        if (isLoading) {
            handleSubscriptionChange(false);
        }
        compositeDisposable.clear();
    }

    @Override
    public boolean getLoadingState() {
        return isLoading;
    }

    @Override
    public boolean isLastPage() {
        return isLastPage;
    }
}
