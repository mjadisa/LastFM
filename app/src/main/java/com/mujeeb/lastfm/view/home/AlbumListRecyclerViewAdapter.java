package com.mujeeb.lastfm.view.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mujeeb.lastfm.R;
import com.mujeeb.lastfm.model.albumResponse.Album;
import com.mujeeb.lastfm.model.albumResponse.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlbumListRecyclerViewAdapter extends RecyclerView.Adapter<AlbumListRecyclerViewAdapter.AlbumResultViewHolder> {
    private static AlbumSelectedInterface listener;
    private final List<Album> albums;

    public AlbumListRecyclerViewAdapter(@NonNull AlbumSelectedInterface listener) {
        albums = new ArrayList<>();
        AlbumListRecyclerViewAdapter.listener = listener;
    }

    public void setData(@NonNull List<Album> data) {
        Objects.requireNonNull(data);
        albums.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        albums.clear();
    }

    @NonNull
    @Override
    public AlbumResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.album_item, viewGroup, false);
        return new AlbumResultViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumResultViewHolder albumResultViewHolder, int position) {
        albumResultViewHolder.bind(albums.get(position));
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    static class AlbumResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_album_art)
        ImageView ivAlbumArt;
        @BindView(R.id.tv_album_title)
        TextView tvAlbumTitle;
        @BindView(R.id.tv_album_artist)
        TextView tvAlbumArtist;
        @BindView(R.id.search_result_container)
        CardView cardView;

        public AlbumResultViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Album album) {
            tvAlbumTitle.setText(album.getName());
            tvAlbumArtist.setText(album.getArtist());
            if (album.getImage() != null && !album.getImage().isEmpty()) {
                final int largePosition = album.getImage().size() - 1;
                final Image image = album.getImage().get(largePosition);
                if (image != null && !image.getText().isEmpty()) {
                    Picasso.get()
                            .load(image.getText())
                            .error(R.mipmap.ic_launcher_round)
                            .into(ivAlbumArt);
                } else {
                    Picasso.get().load(R.mipmap.ic_launcher_round).into(ivAlbumArt);
                }
            }
        }

        @OnClick(R.id.search_result_container)
        void handleSearchResultSelected() {
            listener.onResultSelected(this.getLayoutPosition());
        }
    }
}
