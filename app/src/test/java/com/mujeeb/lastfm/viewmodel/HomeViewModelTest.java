package com.mujeeb.lastfm.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.mujeeb.lastfm.UtilsTest;
import com.mujeeb.lastfm.model.AlbumResults;
import com.mujeeb.lastfm.model.albumResponse.Album;
import com.mujeeb.lastfm.repository.AlbumRepository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class HomeViewModelTest {

    private final AlbumResults albumResults = UtilsTest.getAlbumResults();
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private Observer<List<Album>> albumsObserver;
    private HomeViewModel homeViewModel;

    @BeforeClass
    public static void setupRxSchedulers() {
        Scheduler scheduler = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run, false);
            }
        };
        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> scheduler);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> scheduler);
    }

    @Before
    public void setup() {
        homeViewModel = new HomeViewModel(albumRepository);
    }

    @Test
    public void testAlbumSuccess() {
        when(albumRepository.getAlbumResults(anyString(), anyInt()))
                .thenReturn(Maybe.just(albumResults));


        homeViewModel.getAlbumsObservable().observeForever(albumsObserver);
        homeViewModel.getAlbums("Mockito");

        verify(albumsObserver).onChanged(albumResults.getAlbumList());
    }


}