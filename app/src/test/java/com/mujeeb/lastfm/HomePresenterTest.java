package com.mujeeb.lastfm;

import com.mujeeb.lastfm.api.LastApiCall;
import com.mujeeb.lastfm.model.albumResponse.Album;
import com.mujeeb.lastfm.model.albumResponse.AlbumResultsResponse;
import com.mujeeb.lastfm.model.albumResponse.Albummatches;
import com.mujeeb.lastfm.model.albumResponse.Results;
import com.mujeeb.lastfm.mvp.home.HomeContract;
import com.mujeeb.lastfm.mvp.home.HomePresenter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static com.mujeeb.lastfm.ConstantString.ALBUM_SEARCH;
import static com.mujeeb.lastfm.ConstantString.API_KEY;
import static com.mujeeb.lastfm.ConstantString.FORMAT;
import static com.mujeeb.lastfm.ConstantString.RESULTS_PER_PAGE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {

    private static final String ARTIST = "testArtist";
    private static final String SEARCH_TERM = "test";
    private static final String ALBUM_NAME = "testAlbum";


    @Mock
    private Album album;
    @Mock
    private Results results;
    @Mock
    private Albummatches albumMatches;
    @Mock
    private HomeContract.View view;
    @Mock
    private AlbumResultsResponse albumResultsResponse;
    @Mock
    private LastApiCall lastApiCall;


    private InOrder inOrder;
    private HomePresenter homePresenter;
    private List<Album> albumList;


    @BeforeClass
    public static void setupRxSchedulers() {
        Scheduler scheduler = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };
        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> scheduler);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> scheduler);
    }

    @Before
    public void setup() {
        inOrder = inOrder(view, lastApiCall);
        homePresenter = new HomePresenter(view, lastApiCall);
        albumList = Collections.singletonList(album);

        when(albumResultsResponse.getResults()).thenReturn(results);
        when(results.getAlbummatches()).thenReturn(albumMatches);
        when(albumMatches.getAlbum()).thenReturn(albumList);

        when(lastApiCall.getAlbumResults(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(Observable.just(albumResultsResponse));

    }

    @Test
    public void getResults_IsNewQuery_ClearsDataAndShowsResult() {
        homePresenter.getResults(SEARCH_TERM, true);

        inOrder.verify(view).clearResults();
        inOrder.verify(lastApiCall).getAlbumResults(ALBUM_SEARCH, SEARCH_TERM,
                1, RESULTS_PER_PAGE, API_KEY, FORMAT);
        inOrder.verify(view).showProgress();
        inOrder.verify(view).showResults(albumList);
        inOrder.verify(view).hideProgress();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void getResults_IsNotNewQuery_IncrementsPageAndShowsResult() {
        homePresenter.getResults(SEARCH_TERM, false);

        inOrder.verify(lastApiCall).getAlbumResults(ALBUM_SEARCH, SEARCH_TERM,
                1, RESULTS_PER_PAGE, API_KEY, FORMAT);
        inOrder.verify(view).showProgress();
        inOrder.verify(view).showResults(albumList);
        inOrder.verify(view).hideProgress();

        homePresenter.getResults(SEARCH_TERM, false);

        inOrder.verify(lastApiCall).getAlbumResults(ALBUM_SEARCH, SEARCH_TERM,
                2, RESULTS_PER_PAGE, API_KEY, FORMAT);
        inOrder.verify(view).showProgress();
        inOrder.verify(view).showResults(albumList);
        inOrder.verify(view).hideProgress();

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void isLastPage_WithAvailableEntries_ReturnsFalse() {
        when(results.getOpensearchStartIndex()).thenReturn(1);
        when(results.getOpensearchItemsPerPage()).thenReturn(50);
        when(results.getOpensearchTotalResults()).thenReturn(100);
        homePresenter.getResults(SEARCH_TERM, true);
        assertFalse(homePresenter.isLastPage());
    }

    @Test
    public void isLastPage_WithLastPage_ReturnsTrue() {
        when(results.getOpensearchStartIndex()).thenReturn(60);
        when(results.getOpensearchItemsPerPage()).thenReturn(50);
        when(results.getOpensearchTotalResults()).thenReturn(100);
        homePresenter.getResults(SEARCH_TERM, true);
        assertTrue(homePresenter.isLastPage());
    }

    @Test
    public void handleSearchResultSelection_WithAValidPosition_NavigatesToDetails() {
        when(album.getName()).thenReturn(ALBUM_NAME);
        when(album.getArtist()).thenReturn(ARTIST);
        homePresenter.getResults(SEARCH_TERM, true);
        homePresenter.handleSearchResultSelection(0);
        verify(view).navigateToDetails(ALBUM_NAME, ARTIST);
    }


}
