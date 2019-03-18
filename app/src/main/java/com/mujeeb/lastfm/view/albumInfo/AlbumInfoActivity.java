package com.mujeeb.lastfm.view.albumInfo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mujeeb.lastfm.R;
import com.mujeeb.lastfm.mvp.albumInfo.AlbumInfoContract;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

import static com.mujeeb.lastfm.ConstantString.ALBUM_KEY;
import static com.mujeeb.lastfm.ConstantString.ARTIST_KEY;


public class AlbumInfoActivity extends AppCompatActivity implements AlbumInfoContract.View {

    @BindView(R.id.tv_album_name)
    TextView tvAlbumName;
    @BindView(R.id.tv_artist)
    TextView tvAlbumArtist;
    @BindView(R.id.tv_wiki)
    TextView tvWiki;
    @BindView(R.id.pb_progressBar)
    ProgressBar progressBar;

    @Inject
    AlbumInfoContract.Presenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_info);
        ButterKnife.bind(this);
        final String album = getIntent().getStringExtra(ALBUM_KEY);
        final String artist = getIntent().getStringExtra(ARTIST_KEY);
        presenter.getData(album, artist);
    }

    @Override
    public void showArtistName(@NonNull String artistName) {
        tvAlbumArtist.setText(getString(R.string.artist_name_label, artistName));
    }

    @Override
    public void showWiki(@NonNull String wiki) {
        tvWiki.setText(getString(R.string.wiki_label, wiki));
    }

    @Override
    public void showAlbumName(@NonNull String albumName) {
        tvAlbumName.setText(getString(R.string.album_name_label, albumName));
    }


    @Override
    public void showError(@NonNull String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }
}
