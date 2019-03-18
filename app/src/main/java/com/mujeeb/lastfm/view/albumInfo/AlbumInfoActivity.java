package com.mujeeb.lastfm.view.albumInfo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mujeeb.lastfm.R;
import com.mujeeb.lastfm.databinding.ActivityAlbumInfoBinding;
import com.mujeeb.lastfm.viewmodel.AlbumInfoViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.mujeeb.lastfm.ConstantString.ALBUM_NAME;
import static com.mujeeb.lastfm.ConstantString.ARTIST;

public class AlbumInfoActivity extends AppCompatActivity {

    @Inject
    AlbumInfoViewModel albumInfoViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        ActivityAlbumInfoBinding albumInfoBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_album_info);
        albumInfoBinding.setProgressBarVisibility(albumInfoViewModel.getProgressObservable());

        albumInfoViewModel.getAlbumInfoObservable().observe(this,
                albumInfoBinding::setAlbumInfo);

        albumInfoViewModel.getAlbumInfo(getIntent().getStringExtra(ARTIST), getIntent().getStringExtra(ALBUM_NAME));

    }
}
