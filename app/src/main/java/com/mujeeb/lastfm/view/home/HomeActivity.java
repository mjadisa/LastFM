package com.mujeeb.lastfm.view.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.mujeeb.lastfm.PaginationScrollListener;
import com.mujeeb.lastfm.R;
import com.mujeeb.lastfm.databinding.ActivityHomeBinding;
import com.mujeeb.lastfm.model.albumResponse.Album;
import com.mujeeb.lastfm.view.albumInfo.AlbumInfoActivity;
import com.mujeeb.lastfm.viewmodel.HomeViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.mujeeb.lastfm.ConstantString.ALBUM_NAME;
import static com.mujeeb.lastfm.ConstantString.ARTIST;

public class HomeActivity extends AppCompatActivity implements AlbumSelectedInterface,
        PaginationScrollListener.PaginationStateListener {

    @Inject
    HomeViewModel homeViewModel;

    @Inject
    AlbumListRecyclerViewAdapter albumsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        ActivityHomeBinding activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        activityHomeBinding.setProgressBarVisibility(homeViewModel.getProgressObservable());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.rv_albums);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(albumsAdapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager, this));
        homeViewModel.getAlbumsObservable().observe(this, albumsAdapter::updateData);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_SEARCH)) {
            homeViewModel.getAlbums(intent.getStringExtra(SearchManager.QUERY));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return true;
    }

    @Override
    public boolean isLoading() {
        return homeViewModel.isLoading();
    }

    @Override
    public void loadMoreItems() {
        homeViewModel.loadMoreAlbums();
    }


    @Override
    public boolean isLastPage() {
        return homeViewModel.isLastPage();
    }


    @Override
    public void onAlbumSelected(Album album) {
        Intent intent = new Intent(this,
                AlbumInfoActivity.class);
        intent.putExtra(ALBUM_NAME, album.getName());
        intent.putExtra(ARTIST, album.getArtist());
        startActivity(intent);
    }


}
