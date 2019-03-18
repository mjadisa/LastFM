package com.mujeeb.lastfm.view.home;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mujeeb.lastfm.R;
import com.mujeeb.lastfm.databinding.AlbumItemBinding;
import com.mujeeb.lastfm.model.albumResponse.Album;
import com.mujeeb.lastfm.model.albumResponse.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AlbumListRecyclerViewAdapter extends RecyclerView.Adapter<AlbumListRecyclerViewAdapter.ViewHolder> {

    private static AlbumSelectedInterface albumSelectedInterface = null;

    private final List<Album> albumList;

    public AlbumListRecyclerViewAdapter(AlbumSelectedInterface albumSelectedInterface) {
        this.albumList = new ArrayList<>();
        AlbumListRecyclerViewAdapter.albumSelectedInterface = albumSelectedInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(AlbumItemBinding.inflate(layoutInflater, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(albumList.get(position));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public void updateData(List<Album> albums) {
        albumList.clear();
        albumList.addAll(albums);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AlbumItemBinding albumItemBinding;

        public ViewHolder(@NonNull AlbumItemBinding albumItemBinding) {
            super(albumItemBinding.getRoot());
            this.albumItemBinding = albumItemBinding;
        }

        @BindingAdapter("imageUrl")
        public static void getAlbumImage(ImageView imageView, List<Image> images) {
            Image image = images.get(images.size() - 1);
            if (image != null && !image.getText().isEmpty()) {
                Picasso.get()
                        .load(image.getText())
                        .error(R.mipmap.ic_launcher_round)
                        .into(imageView);
            } else {
                Picasso.get().load(R.mipmap.ic_launcher_round).into(imageView);
            }
        }

        void bind(Album album) {
            albumItemBinding.setAlbum(album);
            if (albumSelectedInterface != null) {
                albumItemBinding.setListener(albumSelectedInterface);
            }
            albumItemBinding.executePendingBindings();
        }
    }

}
